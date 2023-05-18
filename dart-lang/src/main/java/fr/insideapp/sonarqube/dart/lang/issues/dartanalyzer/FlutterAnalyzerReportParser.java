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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FlutterAnalyzerReportParser implements DartAnalyzerReportParser {
    @Override
    public List<DartAnalyzerReportIssue> parse(String input) {

        List<DartAnalyzerReportIssue> issues = new ArrayList<>();

        String[] lines = input.split(System.getProperty("line.separator"));
        Pattern pattern = Pattern.compile("(hint|lint|info|warning|error)(.*)([-•])(.*)([-•])(.*):(.*):(.*)([-•])(.*)");
        for (int i = 0; i < lines.length; i++) {
            Matcher matcher = pattern.matcher(lines[i]);
            while (matcher.find()) {
                String filePath = matcher.group(6).trim();
                int lineNum = Integer.parseInt(matcher.group(7).trim());
                String message = matcher.group(4).trim();
                String ruleId = matcher.group(10).trim();

                issues.add(new DartAnalyzerReportIssue(ruleId, message, filePath, lineNum));
            }
        }
        return issues;
    }
}
