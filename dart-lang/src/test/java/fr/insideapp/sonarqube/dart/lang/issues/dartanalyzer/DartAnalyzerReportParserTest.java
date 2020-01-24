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

import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.DartAnalyzerReportIssue;
import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.DartAnalyzerReportParser;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DartAnalyzerReportParserTest {

    @Test
    public void parse() {

        String input =
                "  lint • Close instances of `dart.core.Sink`. • lib/main.dart:63:9 • close_sinks\n" +
                "  hint • The value of the local variable '_controller' isn't used. • lib/main.dart:63:9 • unused_local_variable";

        DartAnalyzerReportParser parser = new DartAnalyzerReportParser();

        List<DartAnalyzerReportIssue> issues = parser.parse(input);
        assertThat(issues.size()).isEqualTo(2);
        assertThat(issues.get(0).getFilePath()).isEqualTo("lib/main.dart");
        assertThat(issues.get(0).getLineNumber()).isEqualTo(63);
        assertThat(issues.get(0).getRuleId()).isEqualTo("close_sinks");
        assertThat(issues.get(0).getMessage()).isEqualTo("Close instances of `dart.core.Sink`.");
        assertThat(issues.get(1).getFilePath()).isEqualTo("lib/main.dart");
        assertThat(issues.get(1).getLineNumber()).isEqualTo(63);
        assertThat(issues.get(1).getRuleId()).isEqualTo("unused_local_variable");
        assertThat(issues.get(1).getMessage()).isEqualTo("The value of the local variable '_controller' isn't used.");

    }
}