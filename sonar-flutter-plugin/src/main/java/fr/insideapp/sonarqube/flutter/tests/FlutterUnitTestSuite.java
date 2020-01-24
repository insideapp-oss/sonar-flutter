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

import java.util.ArrayList;
import java.util.List;

public class FlutterUnitTestSuite {

    private Long id;
    private String path;
    private List<FlutterUnitTest> tests;

    public FlutterUnitTestSuite() {
        this.tests = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<FlutterUnitTest> getTests() {
        return tests;
    }

    public long getSkippedCount() {
        return this.tests.stream().filter(t -> t.isSkipped()).count();
    }

    public long getErrorCount() {
        return this.tests.stream().filter(t -> t.getResult().equals(FlutterUnitTest.STATUS_ERROR)).count();
    }

    public long getFailureCount() {
        return this.tests.stream().filter(t -> t.getResult().equals(FlutterUnitTest.STATUS_FAILURE)).count();
    }

    public long getDurationMilliseconds() {
        return  this.tests.stream().mapToLong(t -> t.getTime()).sum();
    }


}
