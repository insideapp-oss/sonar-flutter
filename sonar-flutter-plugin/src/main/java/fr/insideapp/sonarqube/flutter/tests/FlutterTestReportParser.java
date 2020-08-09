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
package fr.insideapp.sonarqube.flutter.tests;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Parses Flutter/Dart JSON test output from a file or string source.
 *
 * Test event types are based on https://github.com/dart-lang/test/blob/master/pkgs/test/doc/json_reporter.md
 */
public class FlutterTestReportParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlutterTestReportParser.class);

    List<FlutterUnitTestSuite> parse(File reportFile) throws IOException {
        try (Scanner scanner = new Scanner(reportFile, StandardCharsets.UTF_8.name())) {
            return parse(scanner);
        }
    }

    List<FlutterUnitTestSuite> parse(String input) {
        try (Scanner scanner = new Scanner(input)) {
            return parse(scanner);
        }
    }

    private List<FlutterUnitTestSuite> parse(Scanner scanner) {
        final List<FlutterUnitTestSuite> results = new ArrayList<>();

        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            if (!line.matches("^\\{.*}$")) {
                LOGGER.debug("Skipping line as it does not match expected pattern: {}", line);
                continue;
            }

            final JSONObject obj = (JSONObject) JSONValue.parse(line);
            if (!obj.containsKey("type")) {
                LOGGER.debug("Skipping line as it does not contain a type: {}", line);
                continue;
            }

            switch ((String) obj.get("type")) {
                case "suite":
                    JSONObject suiteObj = (JSONObject) obj.get("suite");
                    FlutterUnitTestSuite suite = new FlutterUnitTestSuite();
                    suite.setId((Long) suiteObj.get("id"));
                    suite.setPath((String) suiteObj.get("path"));
                    results.add(suite);
                    continue;
                case "testStart":
                    JSONObject testObj = (JSONObject) obj.get("test");
                    FlutterUnitTest testResult = new FlutterUnitTest();
                    testResult.setName((String) testObj.get("name"));
                    testResult.setId((Long) testObj.get("id"));
                    testResult.setStartTime((Long) obj.get("time"));

                    results.stream()
                            .filter(s -> s.getId().equals(testObj.get("suiteID")))
                            .findFirst()
                            .ifPresent(s -> s.getTests().add(testResult));
                    continue;
                case "testDone":
                    results.stream()
                            .flatMap(s -> s.getTests().stream())
                            .filter(t -> t.getId().equals(obj.get("testID")))
                            .findFirst()
                            .ifPresent(test -> {
                                test.setHidden((boolean) obj.get("hidden"));
                                test.setResult((String) obj.get("result"));
                                test.setEndTime((Long) obj.get("time"));
                                test.setSkipped((Boolean) obj.get("skipped"));
                            });
                    continue;
                case "error":
                    // TODO this probably needs to be handled
                    // An ErrorEvent indicates that a test encountered an uncaught error.
                    // Note that this may happen even after the test has completed,
                    // in which case it should be considered to have failed.
                    // If a test is asynchronous, it may encounter multiple errors,
                    // which will result in multiple ErrorEvents.
                    continue;
                case "start":
                case "allSuites":
                case "debug":
                case "group":
                case "print":
                case "done":
                default:
                    // these are ignored for now
            }


        }
        return results;
    }
}