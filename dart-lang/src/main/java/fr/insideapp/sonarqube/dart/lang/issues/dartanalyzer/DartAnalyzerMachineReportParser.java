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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * A machine readable output. The format is:
 * 'SEVERITY|TYPE|ERROR_CODE|FILE_PATH|LINE|COLUMN|LENGTH|ERROR_MESSAGE'
 * Note that the pipe character is escaped with backslashes for the file path and error message fields.
 */
public class DartAnalyzerMachineReportParser implements DartAnalyzerReportParser {

    private static final Pattern PATTERN = Pattern.compile("^([A-Z]*)\\|([A-Z_]*)\\|([A-Z_]*)\\|(.*)\\|([0-9]*)\\|([0-9]*)\\|([0-9]*)\\|(.*)$");

    @Override
    public List<DartAnalyzerReportIssue> parse(String input) {
        return Arrays.stream(input.split(System.getProperty("line.separator")))
                .map(PATTERN::matcher)
                .filter(Matcher::matches)
                .map(matcher -> {
                    String ruleId = matcher.group(3).trim().toLowerCase(Locale.ROOT);
                    String filePath = matcher.group(4).trim().replace("\\\\|", "|");
                    int lineNum = Integer.parseInt(matcher.group(5).trim());
                    int columnNum = Integer.parseInt(matcher.group(6).trim());
                    int length = Integer.parseInt(matcher.group(7).trim());
                    String message = matcher.group(8).trim().replace("\\\\|", "|");

                    return new DartAnalyzerReportIssue(ruleId, message, filePath, lineNum, columnNum, length);
                }).collect(Collectors.toList());
    }
}
