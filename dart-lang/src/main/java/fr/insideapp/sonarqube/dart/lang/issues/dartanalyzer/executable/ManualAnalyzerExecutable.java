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
package fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.executable;

import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.AnalyzerOutput;
import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.DartAnalyzerSensor;
import org.sonar.api.batch.sensor.SensorContext;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

class ManualAnalyzerExecutable extends AnalyzerExecutable {

    protected ManualAnalyzerExecutable(SensorContext sensorContext, AnalyzerOutput.Mode mode) {
        super(sensorContext, mode);
    }

    @Override
    public String getCommand() {
        return "unused";
    }

    @Override
    public String[] getArgs() {
        return new String[]{};
    }

    @Override
    public AnalyzerOutput analyze() throws IOException {
        final String path = sensorContext.config().get(DartAnalyzerSensor.ANALYZER_REPORT_PATH)
                .orElseThrow(() -> new IllegalStateException("MANUAL analyzer mode is configured but not report path is set!"));
        LOGGER.info("Analysing report from {}", path);

        final File report = sensorContext.fileSystem().resolvePath(path);
        if (report != null && report.exists() && report.canRead()) {
            final String output = new String(Files.readAllBytes(report.toPath()), StandardCharsets.UTF_8);
            return new AnalyzerOutput(outputMode, getMode(), output);
        } else {
            throw new IllegalStateException(String.format("File '%s' does not exist or could not be read!", path));
        }
    }

    @Override
    protected Mode getMode() {
        return Mode.MANUAL;
    }
}
