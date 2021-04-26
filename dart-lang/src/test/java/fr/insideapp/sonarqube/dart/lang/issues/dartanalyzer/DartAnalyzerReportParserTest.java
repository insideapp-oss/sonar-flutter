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

public class DartAnalyzerReportParserTest {

	private static final String FILE_PATH = "lib/main.dart";
	private static final String RULE_ID_UNUSED_LOCAL_VARIABLE = "unused_local_variable";
	
	private DartAnalyzerReportParser parser;

	public DartAnalyzerReportParserTest() {
		parser = new DartAnalyzerReportParser();
	}

	@Test
	public void parseWithCircles() {

		String input = "  lint • Close instances of `dart.core.Sink`. • lib/main.dart:63:9 • close_sinks\n"
				+ "  hint • The value of the local variable '_controller' isn't used. • lib/main.dart:63:9 • unused_local_variable";

		List<DartAnalyzerReportIssue> issues = parser.parse(input);
		assertThat(issues.size()).isEqualTo(2);
		
		assertFilePath(issues.get(0), FILE_PATH);
		assertLineNumber(issues.get(0), 63);
		assertRuleId(issues.get(0), "close_sinks");
		assertMessage(issues.get(0), "Close instances of `dart.core.Sink`.");
	
		assertFilePath(issues.get(1), FILE_PATH);
		assertLineNumber(issues.get(1), 63);
		assertRuleId(issues.get(1), RULE_ID_UNUSED_LOCAL_VARIABLE);
		assertMessage(issues.get(1), "The value of the local variable '_controller' isn't used.");

	}

	@Test
    public void parseWithTraces() {
    	String input = "Analyzing D:\\workspace\\samples-master\\platform_design\\lib\\main.dart, D:\\workspace\\samples-master\\platform_design\\lib\\utils.dart, D:\\workspace\\samples-master\\platform_design\\lib\\songs_tab.dart, D:\\workspace\\samples-master\\platform_design\\lib\\news_tab.dart, D:\\workspace\\samples-master\\platform_design\\lib\\song_detail_tab.dart...\r\n" + 
    			"  error - Target of URI doesn't exist: 'package:flutter/material.dart'. - lib/main.dart:1:8 - uri_does_not_exist\r\n" + 
    			"  hint - The value of the local variable 'a' isn't used. - lib/main.dart:7:7 - unused_local_variable\r\n" +
    			"  hint - The value of the local variable 'b' isn't used. - lib/main.dart:8:7 - unused_local_variable\r\n" +
    			"  hint - The value of the local variable 'c' isn't used. - lib/main.dart:9:7 - unused_local_variable\r\n" +
    			"  hint - The value of the local variable 'd' isn't used. - lib/main.dart:10:7 - unused_local_variable\r\n" +
    			"  hint - The value of the local variable 'e' isn't used. - lib/main.dart:11:7 - unused_local_variable\r\n" +
    			"  hint - The value of the local variable 'f' isn't used. - lib/main.dart:12:7 - unused_local_variable\r\n" +
    			"  hint - The value of the local variable 'g' isn't used. - lib/main.dart:13:7 - unused_local_variable\r\n" +
    			"  hint - The value of the local variable 'h' isn't used. - lib/main.dart:14:7 - unused_local_variable\r\n" +
    			"  hint - The value of the local variable 'i' isn't used. - lib/main.dart:15:7 - unused_local_variable\r\n" +
    			"  hint - The value of the local variable 'j' isn't used. - lib/main.dart:16:7 - unused_local_variable\r\n" +
    			"  hint - The value of the local variable 'k' isn't used. - lib/main.dart:17:7 - unused_local_variable\r\n" +
    			"1 errors and 11 hints found.";
    	
    	List<DartAnalyzerReportIssue> issues = parser.parse(input);

    	assertThat(issues.size()).isEqualTo(12);   
    	
    	assertFilePath(issues.get(0), FILE_PATH); 
    	assertLineNumber(issues.get(0), 1);
    	assertRuleId(issues.get(0), "uri_does_not_exist");
    	assertMessage(issues.get(0), "Target of URI doesn't exist: 'package:flutter/material.dart'.");
    	
    	
    	assertFilePath(issues.get(10), FILE_PATH); 
    	assertLineNumber(issues.get(10), 16);
    	assertRuleId(issues.get(10), RULE_ID_UNUSED_LOCAL_VARIABLE);
    	assertMessage(issues.get(10), "The value of the local variable 'j' isn't used.");
    }

	@Test
	public void parseFlutterReport() {
		String input = "   info • The value of the local variable 'chocolate' isn't used • lib/api/icecream/icecream_api.dart:110:29 • unused_local_variable\n" +
				"   info • 'nothing' is deprecated and shouldn't be used. Do not use this anymore, please use [Nothing] instead. • lib/src/path/to/very_cool_widget.dart:32:59 • deprecated_member_use_from_same_package\n" +
				"   info • The parameter 'foo' is required • lib/src/path/to/this_file.dart:9:5 • missing_required_param\n" +
				"   info • Unnecessary await keyword in return • lib/path/to/other/file.dart:37:27 • unnecessary_await_in_return";

		List<DartAnalyzerReportIssue> issues = parser.parse(input);
		assertThat(issues).hasSize(4);
		assertThat(issues.stream().map(DartAnalyzerReportIssue::getRuleId)).containsExactly(
				"unused_local_variable",
				"deprecated_member_use_from_same_package",
				"missing_required_param",
				"unnecessary_await_in_return"
		);
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