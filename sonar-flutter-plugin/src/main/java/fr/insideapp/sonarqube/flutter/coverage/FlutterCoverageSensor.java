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
package fr.insideapp.sonarqube.flutter.coverage;

import fr.insideapp.sonarqube.dart.lang.Dart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.coverage.NewCoverage;

import javax.annotation.CheckForNull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FlutterCoverageSensor implements Sensor {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlutterCoverageSensor.class);
    public static final String REPORT_PATH_KEY = "sonar.flutter.coverage.reportPath";
    public static final String DEFAULT_REPORT_PATH = "coverage/lcov.info";

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
                .onlyOnLanguage(Dart.KEY)
                .name("Flutter Coverage")
                .onlyOnFileType(InputFile.Type.MAIN);
    }

    @Override
    public void execute(SensorContext context) {

        List<File> lcovFiles = new ArrayList<>();
        lcovFiles.add(getIOFile(context.fileSystem().baseDir(), reportPath(context)));
        saveCoverageFromLcovFiles(context, lcovFiles);
    }

    private String reportPath(SensorContext sensorContext) {
        return sensorContext.config()
                .get(REPORT_PATH_KEY)
                .orElse(DEFAULT_REPORT_PATH);
    }

    private static void saveCoverageFromLcovFiles(SensorContext context, List<File> lcovFiles) {
        LOGGER.info("Analysing {}", lcovFiles);
        
        if(lcovFiles==null)
        	return;
        

        FileSystem fileSystem = context.fileSystem();
        FilePredicate mainFilePredicate = fileSystem.predicates().and(
                fileSystem.predicates().hasType(InputFile.Type.MAIN),
                fileSystem.predicates().hasLanguage(Dart.KEY));
        FileLocator fileLocator = new FileLocator(fileSystem.inputFiles(mainFilePredicate));

        LCOVParser parser = LCOVParser.create(context, lcovFiles, fileLocator);
        Map<InputFile, NewCoverage> coveredFiles = parser.coverageByFile();

        for (InputFile inputFile : fileSystem.inputFiles(mainFilePredicate)) {
            NewCoverage fileCoverage = coveredFiles.get(inputFile);

            if (fileCoverage != null) {
                fileCoverage.save();
            }
        }

        List<String> unresolvedPaths = parser.unresolvedPaths();
        if (!unresolvedPaths.isEmpty()) {
            LOGGER.warn(String.format("Could not resolve %d file paths in %s", unresolvedPaths.size(), lcovFiles));
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Unresolved paths:\n" + String.join("\n", unresolvedPaths));
            } else {
                LOGGER.warn("First unresolved path: " + unresolvedPaths.get(0) + " (Run in DEBUG mode to get full list of unresolved paths)");
            }
        }

        int inconsistenciesNumber = parser.inconsistenciesNumber();
        if (inconsistenciesNumber > 0) {
            LOGGER.warn("Found {} inconsistencies in coverage report. Re-run analyse in debug mode to see details.", inconsistenciesNumber);
        }
    }

    /**
     * Returns a java.io.File for the given path.
     * If path is not absolute, returns a File with module base directory as parent path.
     */
    @CheckForNull
    private static File getIOFile(File baseDir, String path) {
        File file = new File(path);
        if (!file.isAbsolute()) {
            file = new File(baseDir, path);
        }
        if (!file.isFile()) {
            LOGGER.warn("No coverage information will be saved because LCOV file cannot be found.");
            LOGGER.warn("Provided LCOV file path: {}. Seek file with path: {}", path, file.getAbsolutePath());
            return null;
        }
        return file;
    }
}
