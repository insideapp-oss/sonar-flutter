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

import org.junit.Test;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.issue.internal.DefaultIssueLocation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;

public class DartAnalyzerReportIssueTest {

    @Test
    public void equals() {
        DartAnalyzerReportIssue issue1 = new DartAnalyzerReportIssue("1", "msg", "/test/path", 20);
        DartAnalyzerReportIssue issue2 = new DartAnalyzerReportIssue("1", "msg", "/test/path", 20);
        DartAnalyzerReportIssue issue3 = new DartAnalyzerReportIssue("1", "msg", "/test/path", 20, 5, 4);
        DartAnalyzerReportIssue issue4 = new DartAnalyzerReportIssue("1", "msg", "/test/path", 20, 5, 4);

        assertThat(issue1).isEqualTo(issue2);
        assertThat(issue3).isEqualTo(issue4);
    }

    @Test
    public void notEqual() {
        DartAnalyzerReportIssue issue1 = new DartAnalyzerReportIssue("1", "msg", "/test/path", 20);
        DartAnalyzerReportIssue issue2 = new DartAnalyzerReportIssue("2", "msg", "/test/path", 20);
        DartAnalyzerReportIssue issue3 = new DartAnalyzerReportIssue("1", "msg", "/test/path", 20, 5, 4);
        DartAnalyzerReportIssue issue4 = new DartAnalyzerReportIssue("2", "msg", "/test/path", 20, 5, 4);

        assertThat(issue1).isNotEqualTo(issue2);
        assertThat(issue3).isNotEqualTo(issue4);
    }

    @Test
    public void validIssueLocationWithoutColumn() {
        DartAnalyzerReportIssue issue = new DartAnalyzerReportIssue("1", "msg", "/test/path", 1);
        issue.toNewIssueLocationFor(testFile());
    }

    @Test
    public void validTestFile() throws IOException {
        DefaultInputFile inputFile = testFile();

        assertThat(inputFile.contents()).hasLineCount(5);
        assertThat(inputFile.contents().split("\\R")[0]).hasSize(19);
        assertThat(inputFile.newRange(1, 0, 1, 19)).isNotNull();
        assertThat(inputFile.newRange(1, 0, 1, 1)).isNotNull();
        assertThat(inputFile.newRange(1, 10, 1, 19)).isNotNull();

        try {
            // the endLineOffset is too long
            inputFile.newRange(1, 0, 1, 20);
            fail("Did not throw exception!");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    @Test
    public void validIssueLocationWithColumnMaxLength() throws IOException {
        DartAnalyzerReportIssue issue = new DartAnalyzerReportIssue("1", "msg", "/test/path", 1, 10, 10);
        DefaultInputFile inputFile = testFile();
        DefaultIssueLocation location = (DefaultIssueLocation) issue.toNewIssueLocationFor(inputFile);

        assertThat(location.textRange().start().lineOffset()).isEqualTo(9);
        assertThat(location.textRange().end().lineOffset()).isEqualTo(19);
    }

    @Test
    public void validIssueLocationWithColumnFullLine() {
        DartAnalyzerReportIssue issue = new DartAnalyzerReportIssue("1", "msg", "/test/path", 1, 1, 19);
        DefaultIssueLocation location = (DefaultIssueLocation) issue.toNewIssueLocationFor(testFile());

        assertThat(location.textRange().start().lineOffset()).isEqualTo(0);
        assertThat(location.textRange().end().lineOffset()).isEqualTo(19);
    }

    @Test
    public void validIssueLocationWithColumnLengthOne() {
        DartAnalyzerReportIssue issue = new DartAnalyzerReportIssue("1", "msg", "/test/path", 1, 10, 1);
        DefaultIssueLocation location = (DefaultIssueLocation) issue.toNewIssueLocationFor(testFile());
        assertThat(location.textRange().start().lineOffset()).isEqualTo(9);
        assertThat(location.textRange().end().lineOffset()).isEqualTo(10);
    }

    @Test
    public void validIssueLocationWithColumnButNoLength() {
        DartAnalyzerReportIssue issue = new DartAnalyzerReportIssue("1", "msg", "/test/path", 1, 10, null);
        DefaultIssueLocation location = (DefaultIssueLocation) issue.toNewIssueLocationFor(testFile());
        assertThat(location.textRange().start().lineOffset()).isEqualTo(9);
        assertThat(location.textRange().end().lineOffset()).isEqualTo(19);
    }

    private DefaultInputFile testFile() {
        // first line has a length of 19
        String content = "import 'dart:core';\n\nmain() {\n  print('test');\n}";
        return new TestInputFileBuilder("test", "test.dart")
                .setContents(content)
                .setCharset(StandardCharsets.UTF_8)
                .build();
    }
}
