/*
 * SonarQube Flutter Plugin - Enables analysis of Dart and Flutter projects into SonarQube.
 * Copyright © 2020 inside|app (contact@insideapp.fr)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insideapp.sonarqube.flutter.tests;

import fr.insideapp.sonarqube.dart.lang.Dart;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.measure.Metric;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class FlutterTestSensor implements Sensor {
    private static final Logger LOGGER = Loggers.get(FlutterTestSensor.class);
    public static final String REPORT_PATH_KEY = "sonar.flutter.tests.reportPath";
    public static final String DEFAULT_REPORT_PATH = "tests.output";

    @Override
    public void describe(SensorDescriptor sensorDescriptor) {
        sensorDescriptor
                .name("Flutter unit tests")
                .onlyOnLanguage(Dart.KEY);
    }

    @Override
    public void execute(SensorContext sensorContext) {

        FlutterTestReportParser parser = new FlutterTestReportParser();
        for (String reportPath : reportPaths(sensorContext)) {
            File reportFile = new File(reportPath);
            LOGGER.debug("Parsing test report: {}", reportPath);
            try {
                List<FlutterUnitTestSuite> suites = parser.parse(reportFile);
                suites.forEach(s -> saveSuite(s, sensorContext));
            } catch (IOException e) {
                throw new IllegalStateException("Failed to parse test report", e);
            }

        }

    }

    private String[] reportPaths(SensorContext sensorContext) {
        return sensorContext.config()
                .get(REPORT_PATH_KEY)
                .orElse(DEFAULT_REPORT_PATH).split(",");
    }

    private void saveSuite(FlutterUnitTestSuite suite, SensorContext sensorContext) {

        InputFile inputFile = sensorContext.fileSystem().inputFile(sensorContext.fileSystem().predicates().hasAbsolutePath(suite.getPath()));

        if (inputFile == null) {
            LOGGER.error("Unable to find test file {}", suite.getPath());
            return;
        }

        LOGGER.debug("Parsing tests from {}, ({} test(s))", suite.getPath(), suite.getCount());

        saveMeasure(sensorContext, inputFile, CoreMetrics.SKIPPED_TESTS, (int) suite.getSkippedCount());
        saveMeasure(sensorContext, inputFile, CoreMetrics.TESTS, (int) (suite.getCount()));
        saveMeasure(sensorContext, inputFile, CoreMetrics.TEST_ERRORS, (int) suite.getErrorCount());
        saveMeasure(sensorContext, inputFile, CoreMetrics.TEST_FAILURES, (int) suite.getFailureCount());
        saveMeasure(sensorContext, inputFile, CoreMetrics.TEST_EXECUTION_TIME, suite.getDurationMilliseconds());
    }

    private static <T extends Serializable> void saveMeasure(SensorContext context, InputFile inputFile, Metric<T> metric, T value) {
        context.<T>newMeasure().forMetric(metric).on(inputFile).withValue(value).save();
    }
}
