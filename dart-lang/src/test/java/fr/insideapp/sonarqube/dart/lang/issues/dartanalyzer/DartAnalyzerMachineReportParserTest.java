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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DartAnalyzerMachineReportParserTest {

	private static final String FILE_PATH = "/some/path/lib/main.dart";
	private static final String RULE_ID_UNUSED_LOCAL_VARIABLE = "unused_local_variable";

	private final DartAnalyzerReportParser parser;

	public DartAnalyzerMachineReportParserTest() {
		parser = new DartAnalyzerMachineReportParser();
	}

	@Test
	public void parse() {
		String input = "INFO|LINT|CLOSE_SINKS|/some/path/lib/main.dart|63|9|10|Close instances of `dart.core.Sink`.\n"
				+ "INFO|HINT|UNUSED_LOCAL_VARIABLE|/some/path/lib/main.dart|69|42|17|The value of the local variable '_controller' isn't used.\n"
				+ "INFO|HINT|DUMMY_RULE|/some/path/with/pipe/_\\\\|_/lib/main.dart|2|3|4|Some message with pipe _\\\\|_.";

		List<DartAnalyzerReportIssue> issues = parser.parse(input);
		assertThat(issues.size()).isEqualTo(3);

		assertFilePath(issues.get(0), FILE_PATH);
		assertLineNumber(issues.get(0), 63);
		assertColNumber(issues.get(0), 9);
		assertLength(issues.get(0), 10);
		assertRuleId(issues.get(0), "close_sinks");
		assertMessage(issues.get(0), "Close instances of `dart.core.Sink`.");

		assertFilePath(issues.get(1), FILE_PATH);
		assertLineNumber(issues.get(1), 69);
		assertColNumber(issues.get(1), 42);
		assertLength(issues.get(1), 17);
		assertRuleId(issues.get(1), RULE_ID_UNUSED_LOCAL_VARIABLE);
		assertMessage(issues.get(1), "The value of the local variable '_controller' isn't used.");

		assertFilePath(issues.get(2), "/some/path/with/pipe/_|_/lib/main.dart");
		assertLineNumber(issues.get(2), 2);
		assertColNumber(issues.get(2), 3);
		assertLength(issues.get(2), 4);
		assertRuleId(issues.get(2), "dummy_rule");
		assertMessage(issues.get(2), "Some message with pipe _|_.");

	}

	private void assertFilePath(DartAnalyzerReportIssue issue, String expectedPath) {
		assertThat(issue.getFilePath()).isEqualTo(expectedPath);
	}

	private void assertLineNumber(DartAnalyzerReportIssue issue, Integer expectedLine) {
		assertThat(issue.getLineNumber()).isEqualTo(expectedLine);
	}

	private void assertColNumber(DartAnalyzerReportIssue issue, Integer expectedCol) {
		assertThat(issue.getColNumber()).isEqualTo(expectedCol);
	}

	private void assertLength(DartAnalyzerReportIssue issue, Integer expectedLength) {
		assertThat(issue.getLength()).isEqualTo(expectedLength);
	}

	private void assertRuleId(DartAnalyzerReportIssue issue, String expectedRuleId) {
		assertThat(issue.getRuleId()).isEqualTo(expectedRuleId);
	}

	private void assertMessage(DartAnalyzerReportIssue issue, String expectedMessage) {
		assertThat(issue.getMessage()).isEqualTo(expectedMessage);
	}
}