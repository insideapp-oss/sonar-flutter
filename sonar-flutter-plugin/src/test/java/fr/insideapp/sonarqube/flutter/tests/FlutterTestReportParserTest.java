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

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FlutterTestReportParserTest {

    @Test
    public void parse() {

        String input = "{\"protocolVersion\":\"0.1.1\",\"runnerVersion\":null,\"pid\":79619,\"type\":\"start\",\"time\":0}\n" +
                "{\"suite\":{\"id\":0,\"platform\":\"vm\",\"path\":\"/Users/ggrousset/Desktop/inside_face/test/home_view_model_test.dart\"},\"type\":\"suite\",\"time\":1}\n" +
                "{\"test\":{\"id\":1,\"name\":\"loading /Users/ggrousset/Desktop/inside_face/test/home_view_model_test.dart\",\"suiteID\":0,\"groupIDs\":[],\"metadata\":{\"skip\":false,\"skipReason\":null},\"line\":null,\"column\":null,\"url\":null},\"type\":\"testStart\",\"time\":2}\n" +
                "{\"count\":1,\"type\":\"allSuites\",\"time\":6}\n" +
                "{\"testID\":1,\"result\":\"success\",\"skipped\":false,\"hidden\":true,\"type\":\"testDone\",\"time\":3658}\n" +
                "{\"group\":{\"id\":2,\"suiteID\":0,\"parentID\":null,\"name\":null,\"metadata\":{\"skip\":false,\"skipReason\":null},\"testCount\":1,\"line\":null,\"column\":null,\"url\":null},\"type\":\"group\",\"time\":3668}\n" +
                "{\"group\":{\"id\":3,\"suiteID\":0,\"parentID\":2,\"name\":\"home_view_model\",\"metadata\":{\"skip\":false,\"skipReason\":null},\"testCount\":1,\"line\":29,\"column\":3,\"url\":\"file:///Users/ggrousset/Desktop/inside_face/test/home_view_model_test.dart\"},\"type\":\"group\",\"time\":3668}\n" +
                "{\"test\":{\"id\":4,\"name\":\"home_view_model Giving an image as input produce and output result\",\"suiteID\":0,\"groupIDs\":[2,3],\"metadata\":{\"skip\":false,\"skipReason\":null},\"line\":31,\"column\":5,\"url\":\"file:///Users/ggrousset/Desktop/inside_face/test/home_view_model_test.dart\"},\"type\":\"testStart\",\"time\":3669}\n" +
                "{\"testID\":1,\"messageType\":\"print\",\"message\":\"{\\\"faces\\\":[]}\",\"type\":\"print\",\"time\":5109}\n" +
                "{\"testID\":4,\"messageType\":\"print\",\"message\":\"received\",\"type\":\"print\",\"time\":5110}\n" +
                "{\"testID\":4,\"result\":\"success\",\"skipped\":false,\"hidden\":false,\"type\":\"testDone\",\"time\":5119}\n" +
                "{\"success\":true,\"type\":\"done\",\"time\":5172}";

        FlutterTestReportParser parser = new FlutterTestReportParser();
        List<FlutterUnitTestSuite> results = parser.parse(input);

        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getTests().size()).isEqualTo(2);

        assertThat(results.get(0).getCount()).isEqualTo(1);
        assertThat(results.get(0).getActualTests().size()).isEqualTo(1);

        assertThat(results.get(0).getSkippedCount()).isEqualTo(0);
        assertThat(results.get(0).getFailureCount()).isEqualTo(0);
        assertThat(results.get(0).getErrorCount()).isEqualTo(0);

        assertThat(results.get(0).getTests().get(0).getId()).isEqualTo(1);
        assertThat(results.get(0).getTests().get(0).getName()).isEqualTo("loading /Users/ggrousset/Desktop/inside_face/test/home_view_model_test.dart");
        assertThat(results.get(0).getTests().get(0).getTime()).isEqualTo(3658 - 2); // end - start
        assertThat(results.get(0).getTests().get(0).getResult()).isEqualTo("success");
        assertThat(results.get(0).getTests().get(0).isSkipped()).isEqualTo(false);
        assertThat(results.get(0).getTests().get(0).isHidden()).isEqualTo(true);

        assertThat(results.get(0).getActualTests().get(0).getId()).isEqualTo(4);
        assertThat(results.get(0).getActualTests().get(0).getName()).isEqualTo("home_view_model Giving an image as input produce and output result");
        assertThat(results.get(0).getActualTests().get(0).getTime()).isEqualTo(5119 - 3669); // end - start
        assertThat(results.get(0).getActualTests().get(0).getResult()).isEqualTo("success");
        assertThat(results.get(0).getActualTests().get(0).isSkipped()).isEqualTo(false);
        assertThat(results.get(0).getActualTests().get(0).isHidden()).isEqualTo(false);
    }

}
