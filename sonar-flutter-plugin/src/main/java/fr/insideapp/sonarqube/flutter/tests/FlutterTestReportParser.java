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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlutterTestReportParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlutterTestReportParser.class);

    List<FlutterUnitTestSuite> parse(File reportFile) throws IOException {
    	LOGGER.info("Log reportFile: {}", reportFile);
        StringBuilder contentBuilder = new StringBuilder();
        Path path = Paths.get(reportFile.toString());
        
        Boolean fileNotExists = Files.notExists(path.toAbsolutePath());
        
        LOGGER.info("File exists? {}", fileNotExists);
        
        if(fileNotExists) {
        	Files.createFile(path.toAbsolutePath());
        }
        
        LOGGER.info("Log absolut path: {}", path.toAbsolutePath());
        
        Files.lines(path.toAbsolutePath()).forEach(s -> contentBuilder.append(s).append("\n"));
        return this.parse(contentBuilder.toString());

    }
    List<FlutterUnitTestSuite> parse(String input) {

        List<FlutterUnitTest> tests = new ArrayList<>();
        List<FlutterUnitTestSuite> results = new ArrayList<>();

        // Each line is an event, encoded as JSON
        String[] lines = input.split(System.getProperty("line.separator"));
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            JSONObject obj = (JSONObject)JSONValue.parse(line);

            // Suites
            if (obj.containsKey("suite")) {
                JSONObject suiteObj = (JSONObject) obj.get("suite");
                FlutterUnitTestSuite suite = new FlutterUnitTestSuite();
                suite.setId((Long)suiteObj.get("id"));
                suite.setPath((String)suiteObj.get("path"));
                results.add(suite);
            }

            // Tests
            if (obj.containsKey("test")) {
                JSONObject testObj = (JSONObject) obj.get("test");
                FlutterUnitTest testResult = new FlutterUnitTest();
                testResult.setName((String)testObj.get("name"));
                testResult.setId((Long)testObj.get("id"));
                tests.add(testResult);

                Long suiteId = (Long)testObj.get("suiteID");
                Optional<FlutterUnitTestSuite> suite = results.stream().filter(s -> s.getId().equals(suiteId)).findFirst();
                if (suite.isPresent()) {
                    suite.get().getTests().add(testResult);
                }
            }

            // Test finished
            if (obj.containsKey("testID") && obj.containsKey("result")) {
                Long testId = (Long)obj.get("testID");
                Optional<FlutterUnitTest> test = tests.stream().filter(t -> t.getId().equals(testId)).findFirst();
                if (test.isPresent()) {
                    test.get().setResult((String)obj.get("result"));
                    test.get().setTime((Long)obj.get("time"));
                    test.get().setSkipped((Boolean) obj.get("skipped"));
                }
            }


        }

        return results;
    }
}
