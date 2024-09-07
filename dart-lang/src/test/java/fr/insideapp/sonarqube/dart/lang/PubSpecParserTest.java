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
package fr.insideapp.sonarqube.dart.lang;

import org.junit.Test;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.internal.SensorContextTester;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PubSpecParserTest {

    @Test
    public void parse() throws IOException {
        SensorContext context = SensorContextTester.create(new File("src/test/resources/pubspec"));
        final PubSpec pubSpec = PubSpecParser.parse(context);
        assertThat(pubSpec.getProjectVersion()).isEqualTo("1.0.0");
        assertTrue(pubSpec.isFlutter());

    }

    @Test
    public void parseNoDeps() throws IOException {
        SensorContext context = SensorContextTester.create(new File("src/test/resources/pubspec_nodeps"));
        final PubSpec pubSpec = PubSpecParser.parse(context);
        assertThat(pubSpec.getProjectVersion()).isEqualTo("1.0.0");
        assertFalse(pubSpec.isFlutter());
    }

    @Test
    public void parseMissingPubSpecFile() {
        SensorContext context = SensorContextTester.create(new File("."));

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            PubSpecParser.parse(context);
        });
    }

}
