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

class FlutterAnalyzerExecutable extends AnalyzerExecutable {

    protected FlutterAnalyzerExecutable(SensorContext sensorContext, AnalyzerOutput.Mode mode) {
        super(sensorContext, mode);
    }

    @Override
    public String getCommand() {
        return System.getProperty("os.name").toUpperCase().contains("WINDOWS")
                ? "flutter.bat"
                : "flutter";
    }

    @Override
    public String[] getArgs() {
        return new String[]{"analyze"};
    }
}
