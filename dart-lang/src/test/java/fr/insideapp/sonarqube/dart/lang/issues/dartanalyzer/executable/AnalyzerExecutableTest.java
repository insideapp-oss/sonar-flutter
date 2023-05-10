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
import org.junit.Test;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.internal.SensorContextTester;

import java.io.File;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class AnalyzerExecutableTest {

    private static final String BASE_DIR = "executable";

    private static final String ANALYSIS_OPTIONS = "analysis_options.yaml";

    private class DummyAnalyzerExecutable extends AnalyzerExecutable {

        public DummyAnalyzerExecutable(SensorContext sensorContext) {
            super(sensorContext, AnalyzerOutput.Mode.DETECT);
        }

        @Override
        protected String getCommand() {
            return "echo";
        }

        @Override
        protected String[] getArgs() {
            return new String[]{"hello"};
        }
    }

    @Test
    public void analyzeShouldRunCommandAndReturnAnalyzerOutput() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        File baseDir = new File(classLoader.getResource(BASE_DIR).getFile());
        SensorContext context = SensorContextTester.create(baseDir);
        AnalyzerExecutable executable = new DummyAnalyzerExecutable(context);

        AnalyzerOutput output = executable.analyze();

        assertThat(output.getMode().name()).isEqualTo(AnalyzerExecutable.Mode.defaultMode.name());
        assertThat(output.getContent()).isEqualTo("hello\n");
        assertThat(new File(baseDir, ANALYSIS_OPTIONS)).exists();
    }

}

