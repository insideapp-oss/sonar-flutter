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

import junit.framework.TestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DartAnalyzerReportIssueTest {

    @Test
    public void equals() {
        DartAnalyzerReportIssue issue1 = new DartAnalyzerReportIssue("1", "msg", "/test/path", 20);
        DartAnalyzerReportIssue issue2 = new DartAnalyzerReportIssue("1", "msg", "/test/path", 20);

        assertThat(issue1).isEqualTo(issue2);
    }

    @Test
    public void notEqual() {
        DartAnalyzerReportIssue issue1 = new DartAnalyzerReportIssue("1", "msg", "/test/path", 20);
        DartAnalyzerReportIssue issue2 = new DartAnalyzerReportIssue("2", "msg", "/test/path", 20);

        assertThat(issue1).isNotEqualTo(issue2);
    }
}