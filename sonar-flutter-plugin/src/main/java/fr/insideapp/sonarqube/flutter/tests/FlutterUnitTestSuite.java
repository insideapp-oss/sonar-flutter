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
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class FlutterUnitTestSuite {

    private Long id;
    private String path;
    private final List<FlutterUnitTest> tests;

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

    /**
     * Returns all tests that are not skipped or hidden.
     */
    public List<FlutterUnitTest> getActualTests() {
        return tests.stream().filter(t -> !t.isHidden()).filter(t -> !t.isSkipped()).collect(Collectors.toList());
    }

    public long getCount() {
        return getActualTests().size();
    }

    public long getSkippedCount() {
        return tests.stream().filter(t -> !t.isHidden()).filter(FlutterUnitTest::isSkipped).count();
    }

    public long getErrorCount() {
        return getActualTests().stream().filter(t -> t.getResult().equals(FlutterUnitTest.STATUS_ERROR)).count();
    }

    public long getFailureCount() {
        return getActualTests().stream().filter(t -> t.getResult().equals(FlutterUnitTest.STATUS_FAILURE)).count();
    }

    public long getDurationMilliseconds() {
        return getActualTests().stream().mapToLong(FlutterUnitTest::getTime).sum();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FlutterUnitTestSuite.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("path='" + path + "'")
                .add("tests=" + tests)
                .add("count=" + getCount())
                .add("skipped=" + getSkippedCount())
                .add("failures=" + getFailureCount())
                .add("errors=" + getErrorCount())
                .toString();
    }
}
