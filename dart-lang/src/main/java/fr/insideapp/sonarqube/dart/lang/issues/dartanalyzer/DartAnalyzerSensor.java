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

import fr.insideapp.sonarqube.dart.lang.Dart;
import fr.insideapp.sonarqube.dart.lang.PubSpec;
import fr.insideapp.sonarqube.dart.lang.PubSpecParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.rule.RuleKey;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.util.Arrays.asList;

public class DartAnalyzerSensor implements Sensor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DartAnalyzerSensor.class);

    public static final String ANALYZER_MODE = "sonar.dart.analyzer.mode";
    public static final List<AnalyzerExecutable.Mode> ANALYZER_MODE_OPTIONS = asList(AnalyzerExecutable.Mode.values());

    public static final String ANALYZER_OPTIONS_OVERRIDE = "sonar.dart.analyzer.options.override";
    public static final String ANALYZER_OPTIONS_OVERRIDE_DEFAULT = "true";

    public static final String ANALYZER_REPORT_PATH = "sonar.dart.analyzer.report.path";

    public static final String ANALYZER_OUTPUT_MODE = "sonar.dart.analyzer.report.mode";
    public static final List<AnalyzerOutput.Mode> ANALYZER_OUTPUT_MODE_OPTIONS = asList(AnalyzerOutput.Mode.values());

    @Override
    public void describe(SensorDescriptor sensorDescriptor) {
        sensorDescriptor.onlyOnLanguage(Dart.KEY).name("Dart analysis sensor").onlyOnFileType(Type.MAIN);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void execute(SensorContext sensorContext) {
        final PubSpec pubSpec = PubSpecParser.parse(sensorContext);

        final AnalyzerOutput output = AnalyzerExecutable.create(sensorContext, pubSpec).analyze();

        final DartAnalyzerReportParser parser = output.getMode().equals(AnalyzerOutput.Mode.MACHINE)
                ? new DartAnalyzerMachineReportParser() : new DartAnalyzerLegacyReportParser();

        final List<DartAnalyzerReportIssue> issues = parser.parse(output.getContent());

        LOGGER.info("Recording {} issues", issues.size());

        recordIssues(sensorContext, issues);
    }

    private void recordIssues(SensorContext sensorContext, List<DartAnalyzerReportIssue> issues) {
        issues.forEach(issue -> {
            File file = sensorContext.fileSystem().resolvePath(issue.getFilePath());
            LOGGER.debug("Recording issue for {}", file.getAbsolutePath());

            FilePredicate fp = sensorContext.fileSystem().predicates().hasAbsolutePath(file.getAbsolutePath());
            if (!sensorContext.fileSystem().hasFiles(fp)) {
                LOGGER.warn("File not included in SonarQube {}", file.getAbsoluteFile());
            } else {
                final InputFile inputFile = Objects.requireNonNull(sensorContext.fileSystem().inputFile(fp));
                sensorContext.newIssue()
                        .forRule(RuleKey.of(DartAnalyzerRulesDefinition.REPOSITORY_KEY, issue.getRuleId().toLowerCase(Locale.ROOT)))
                        .at(issue.toNewIssueLocationFor(inputFile))
                        .save();
            }
        });
    }
}
