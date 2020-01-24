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
package fr.insideapp.sonarqube.flutter;

import fr.insideapp.sonarqube.dart.lang.api.DartSensor;
import fr.insideapp.sonarqube.dart.lang.issues.DartProfile;
import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.DartAnalyzerRulesDefinition;
import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.DartAnalyzerSensor;
import fr.insideapp.sonarqube.flutter.coverage.FlutterCoverageSensor;
import fr.insideapp.sonarqube.flutter.tests.FlutterTestSensor;
import org.sonar.api.Plugin;
import fr.insideapp.sonarqube.dart.lang.Dart;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

public class FlutterPlugin implements Plugin {

    public static final String FLUTTER_CATEGORY = "Flutter";
    public static final String GENERAL_SUBCATEGORY = "General";
    public static final String TESTS_SUBCATEGORY = "Tests";

    public static final String FLUTTER_TESTS_REPORT_PATH_KEY = "sonar.flutter.tests.reportPath";
    public static final String FLUTTER_LCOV_REPORT_PATH_KEY = "sonar.flutter.coverage.reportPath";

    public void define(Context context) {

        // Language support
        context.addExtensions(Dart.class, DartSensor.class, DartProfile.class);

        // dartanalyzer Sensor
        context.addExtensions(DartAnalyzerSensor.class, DartAnalyzerRulesDefinition.class);

        context.addExtension(
                PropertyDefinition.builder(FLUTTER_TESTS_REPORT_PATH_KEY)
                        .name("Unit Test Report")
                        .description("Path to Flutter unit test execution report file. The path may be either absolute or relative to the project base directory.")
                        .onQualifiers(Qualifiers.MODULE, Qualifiers.PROJECT)
                        .category(FLUTTER_CATEGORY)
                        .subCategory(TESTS_SUBCATEGORY)
                        .build());

        context.addExtension(
                PropertyDefinition.builder(FLUTTER_LCOV_REPORT_PATH_KEY)
                        .name("Coverage Report")
                        .description("Path to Flutter coverage report file. The path may be either absolute or relative to the project base directory.")
                        .onQualifiers(Qualifiers.MODULE, Qualifiers.PROJECT)
                        .category(FLUTTER_CATEGORY)
                        .subCategory(TESTS_SUBCATEGORY)
                        .build());

        // Tests
        context.addExtension(FlutterTestSensor.class);

        // Coverage
        context.addExtension(FlutterCoverageSensor.class);
    }
}
