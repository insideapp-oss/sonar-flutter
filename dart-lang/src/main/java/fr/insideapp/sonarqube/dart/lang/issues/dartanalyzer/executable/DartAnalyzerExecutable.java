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
import org.sonar.api.batch.sensor.SensorContext;

class DartAnalyzerExecutable extends AnalyzerExecutable {

    protected DartAnalyzerExecutable(SensorContext sensorContext, AnalyzerOutput.Mode mode) {
        super(sensorContext, mode);
    }

    @Override
    public String getCommand() {
        return System.getProperty("os.name").toUpperCase().contains("WINDOWS")
                ? "dart.bat"
                : "dart";
    }

    @Override
    public String[] getArgs() {
        if (outputMode.equals(AnalyzerOutput.Mode.MACHINE)) {
            return new String[]{"analyze", "--no-fatal-warnings", "--format=machine"};
        }
        return new String[]{"analyze", "--no-fatal-warnings"};
    }

    @Override
    protected Mode getMode() {
        return Mode.DART;
    }
}
