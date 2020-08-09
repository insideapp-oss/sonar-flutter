/*
 * SonarQube Flutter Plugin
 * Copyright (C) 2020 inside|app
 * contact@insideapp.fr
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer;

import com.google.common.io.Files;
import com.google.common.io.Resources;
import fr.insideapp.sonarqube.dart.lang.Dart;
import fr.insideapp.sonarqube.dart.lang.DartSensor;
import org.buildobjects.process.ExternalProcessFailureException;
import org.buildobjects.process.ProcBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.batch.sensor.issue.internal.DefaultIssueLocation;
import org.sonar.api.rule.RuleKey;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DartAnalyzerSensor implements Sensor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DartAnalyzerSensor.class);
    private static final String ANALYZER_COMMAND = "dartanalyzer";
    private static final int ANALYZER_TIMEOUT = 10 * 60 * 1000;
    private static final String ANALYSIS_OPTIONS_FILENAME = "analysis_options.yaml";
    private static final String ANALYSIS_OPTIONS_FILE = "/fr/insideapp/sonarqube/dart/dartanalyzer/analysis_options.yaml";


    @Override
    public void describe(SensorDescriptor sensorDescriptor) {
        sensorDescriptor
                .onlyOnLanguage(Dart.KEY)
                .name("dartanalyzer sensor")
                .onlyOnFileType(Type.MAIN);
    }

    @Override
    public void execute(SensorContext sensorContext) {

        boolean useExistingAnalysisOptions = sensorContext.config().getBoolean(DartSensor.DART_ANALYSIS_USE_EXISTING_OPTIONS_KEY).orElse(false);
        boolean useDefaultAnalysisOptions = !useExistingAnalysisOptions;

        // If option is set to true and analysis-options.yaml already exists, use it
        if(useExistingAnalysisOptions) {
            LOGGER.info("Use existing {} file", ANALYSIS_OPTIONS_FILENAME);
            if(!this.analysisOptionsFileExists(sensorContext)) {
                LOGGER.warn("File {} does not exists", ANALYSIS_OPTIONS_FILENAME);
                // Force default analysis-options.yaml file
                useDefaultAnalysisOptions = true;
            }
        }
        if(useDefaultAnalysisOptions) {
            LOGGER.info("Either {} option is not set to true or {} file does not exists, use default analysis options instead", DartSensor.DART_ANALYSIS_USE_EXISTING_OPTIONS_KEY, ANALYSIS_OPTIONS_FILENAME);
            // Setup a analysis-options.yaml file
            this.saveCurrentAnalysisOptionsFile(sensorContext);
            try {
                this.createAnalysisOptionsFile(sensorContext);
            } catch (IOException e) {
                LOGGER.error("Failed to create {} file", ANALYSIS_OPTIONS_FILENAME, e);
            }
        }

        // Retrieve source files to analyze
        FileSystem fileSystem = sensorContext.fileSystem();

        FilePredicate mainFilePredicate = fileSystem.predicates().and(
                fileSystem.predicates().hasType(InputFile.Type.MAIN),
                fileSystem.predicates().hasLanguage(Dart.KEY));

        List<InputFile> inputFiles = new ArrayList<>();
        fileSystem.inputFiles(mainFilePredicate).forEach(inputFiles::add);

        try {
            if (this.verifyCommand()) {

                DartAnalyzerReportParser reportParser = new DartAnalyzerReportParser();

                // TODO : consider splitting analysis every X files (a command line length limit might occur !)
                List<String> fileNames = inputFiles.stream().map(i -> i.toString()).collect(Collectors.toList());
                // Run analysis command
                String output = new ProcBuilder(ANALYZER_COMMAND)
                        .withArgs(fileNames.toArray(new String[0]))
                        .withTimeoutMillis(ANALYZER_TIMEOUT)
                        .run()
                        .getOutputString();

                // Parse output
                List<DartAnalyzerReportIssue> issues = reportParser.parse(output);

                // Record issues
                issues.forEach(i -> {
                    File file = new File(sensorContext.fileSystem().baseDir(), i.getFilePath());
                    FilePredicate fp = sensorContext.fileSystem().predicates().hasAbsolutePath(file.getAbsolutePath());
                    if (!sensorContext.fileSystem().hasFiles(fp)) {
                        LOGGER.warn("File not included in SonarQube {}", file.getAbsoluteFile());
                    } else {
                        InputFile inputFile = sensorContext.fileSystem().inputFile(fp);
                        NewIssueLocation nil = new DefaultIssueLocation()
                                .on(inputFile)
                                .at(inputFile.selectLine(i.getLineNumber()))
                                .message(i.getMessage());
                        sensorContext.newIssue()
                                .forRule(RuleKey.of(DartAnalyzerRulesDefinition.REPOSITORY_KEY, i.getRuleId()))
                                .at(nil)
                                .save();
                    }
                });
            }
        } finally {
            if(useDefaultAnalysisOptions) {
                // Remove analysis-options.yaml file
                this.restoreCurrentAnalysisOptionsFile(sensorContext);
            }
        }

    }

    private boolean verifyCommand() {
        ProcBuilder builder = new ProcBuilder(ANALYZER_COMMAND)
                .withArg("-h");
        try {
            builder.run();
            return true;
        } catch (ExternalProcessFailureException ex) {
            LOGGER.error("Unable to run " + ANALYZER_COMMAND + ", make sure it is installed and on the path");
            return false;
        }
    }

    private boolean analysisOptionsFileExists(SensorContext sensorContext) {
        File analysisOptionsFile = new File(sensorContext.fileSystem().baseDir(), ANALYSIS_OPTIONS_FILENAME);
        return analysisOptionsFile.exists();
    }

    private void saveCurrentAnalysisOptionsFile(SensorContext sensorContext) {
        File analysisOptionsFile = new File(sensorContext.fileSystem().baseDir(), ANALYSIS_OPTIONS_FILENAME);
        if (analysisOptionsFile.exists()) {
            analysisOptionsFile.renameTo(new File(sensorContext.fileSystem().baseDir(), ANALYSIS_OPTIONS_FILENAME + ".sonar"));
            LOGGER.info("Backup of original analysis_options.yaml file to {}", ANALYSIS_OPTIONS_FILENAME + ".sonar");
        }
    }

    private void createAnalysisOptionsFile(SensorContext sensorContext) throws IOException {
        File analysisOptionsFile = new File(sensorContext.fileSystem().baseDir(), ANALYSIS_OPTIONS_FILENAME);
        URL inputUrl = getClass().getResource(ANALYSIS_OPTIONS_FILE);
        Resources.asByteSource(inputUrl).copyTo(Files.asByteSink(analysisOptionsFile));
    }

    private void restoreCurrentAnalysisOptionsFile(SensorContext sensorContext) {
        File analysisOptionsFile = new File(sensorContext.fileSystem().baseDir(), ANALYSIS_OPTIONS_FILENAME);
        File currentAnalysisOptionsFile = new File(sensorContext.fileSystem().baseDir(), ANALYSIS_OPTIONS_FILENAME + ".sonar");
        if (currentAnalysisOptionsFile.exists()) {
            currentAnalysisOptionsFile.renameTo(analysisOptionsFile);
            LOGGER.info("Restored original analysis_options.yaml file");
        } else {
            // If no current analysis options file : remove existing one
            analysisOptionsFile.delete();
        }
    }
}
