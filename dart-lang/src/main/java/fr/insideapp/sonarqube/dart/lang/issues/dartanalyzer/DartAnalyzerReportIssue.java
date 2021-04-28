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

import java.util.Objects;

public class DartAnalyzerReportIssue {

    private final String ruleId;
    private final String message;
    private final String filePath;
    private final Integer lineNumber;
    private final Integer colNumber;
    private final Integer length;

    public DartAnalyzerReportIssue(String ruleId, String message, String filePath, Integer lineNumber) {
        this.ruleId = ruleId;
        this.message = message;
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.colNumber = null;
        this.length = null;
    }

    public DartAnalyzerReportIssue(String ruleId, String message, String filePath,
                                   Integer lineNumber,Integer colNumber, Integer length) {
        this.ruleId = ruleId;
        this.message = message;
        this.filePath = filePath;
        this.lineNumber = lineNumber;
        this.colNumber = colNumber;
        this.length = length;
    }

    public String getRuleId() {
        return ruleId;
    }

    public String getMessage() {
        return message;
    }

    public String getFilePath() {
        return filePath;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public Integer getColNumber() {
        return colNumber;
    }

    public Integer getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DartAnalyzerReportIssue that = (DartAnalyzerReportIssue) o;
        return Objects.equals(lineNumber, that.lineNumber) &&
                Objects.equals(colNumber, that.colNumber) &&
                Objects.equals(length, that.length) &&
                Objects.equals(ruleId, that.ruleId) &&
                Objects.equals(message, that.message) &&
                Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleId, message, filePath, lineNumber, colNumber, length);
    }
}
