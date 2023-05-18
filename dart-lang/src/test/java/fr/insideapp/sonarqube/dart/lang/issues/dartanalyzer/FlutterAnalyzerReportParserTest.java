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
package fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FlutterAnalyzerReportParserTest {

    private static final String FILE_PATH = "lib/main.dart";
    private static final String RULE_ID_UNUSED_LOCAL_VARIABLE = "unused_local_variable";

    private final FlutterAnalyzerReportParser parser;

    public FlutterAnalyzerReportParserTest() {
        parser = new FlutterAnalyzerReportParser();
    }

    @Test
    public void parseWithCircles() {

        String input = "  info • Don't use `final` for local variables • lib/main.dart:21:5 • unnecessary_final\n" +
                "   info • Specify type annotations • lib/main.dart:22:5 • always_specify_types\n" +
                "   info • Don't use `final` for local variables • lib/main.dart:23:5 • unnecessary_final";

        List<DartAnalyzerReportIssue> issues = parser.parse(input);
        assertThat(issues).hasSize(3);

        assertFilePath(issues.get(0), FILE_PATH);
        assertLineNumber(issues.get(0), 21);
        assertRuleId(issues.get(0), "unnecessary_final");
        assertMessage(issues.get(0), "Don't use `final` for local variables");

        assertFilePath(issues.get(1), FILE_PATH);
        assertLineNumber(issues.get(1), 22);
        assertRuleId(issues.get(1), "always_specify_types");
        assertMessage(issues.get(1), "Specify type annotations");

        assertFilePath(issues.get(2), FILE_PATH);
        assertLineNumber(issues.get(2), 23);
        assertRuleId(issues.get(2), "unnecessary_final");
        assertMessage(issues.get(2), "Don't use `final` for local variables");

    }
    
    private void assertFilePath(DartAnalyzerReportIssue issue, String expectedPath) {
        assertThat(issue.getFilePath()).isEqualTo(expectedPath);
    }

    private void assertLineNumber(DartAnalyzerReportIssue issue, Integer expectedLine) {
        assertThat(issue.getLineNumber()).isEqualTo(expectedLine);
    }

    private void assertRuleId(DartAnalyzerReportIssue issue, String expectedRuleId) {
        assertThat(issue.getRuleId()).isEqualTo(expectedRuleId);
    }

    private void assertMessage(DartAnalyzerReportIssue issue, String expectedMessage) {
        assertThat(issue.getMessage()).isEqualTo(expectedMessage);
    }
}
