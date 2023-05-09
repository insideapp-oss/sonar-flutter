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

import fr.insideapp.sonarqube.dart.lang.Dart;
import fr.insideapp.sonarqube.dart.lang.DartSensor;
import fr.insideapp.sonarqube.dart.lang.PubSpecSensor;
import fr.insideapp.sonarqube.dart.lang.issues.DartProfile;
import fr.insideapp.sonarqube.dart.lang.issues.DartProfilePedantic190;
import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.executable.AnalyzerExecutable;
import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.AnalyzerOutput;
import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.DartAnalyzerRulesDefinition;
import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.DartAnalyzerSensor;
import fr.insideapp.sonarqube.flutter.coverage.FlutterCoverageSensor;
import fr.insideapp.sonarqube.flutter.tests.FlutterTestSensor;
import org.sonar.api.Plugin;
import org.sonar.api.PropertyType;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

import java.util.stream.Collectors;

public class FlutterPlugin implements Plugin {

    public static final String DART_CATEGORY = "Dart";
    public static final String FLUTTER_CATEGORY = "Flutter";
    public static final String ANALYSIS_SUBCATEGORY = "Analysis";
    public static final String TESTS_SUBCATEGORY = "Tests";

    public static final String FLUTTER_TESTS_REPORT_PATH_KEY = "sonar.flutter.tests.reportPath";
    public static final String FLUTTER_LCOV_REPORT_PATH_KEY = "sonar.flutter.coverage.reportPath";

    public void define(Context context) {

        // Language support
        context.addExtensions(Dart.class, DartSensor.class, DartProfile.class, DartProfilePedantic190.class);

        // Add pubspec support
        context.addExtension(PubSpecSensor.class);

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

        context.addExtension(
                PropertyDefinition.builder(DartAnalyzerSensor.ANALYZER_OPTIONS_OVERRIDE)
                        .name("Override existing analysis_options file")
                        .description("The SonarQube plugin for Flutter/Dart uses its own analysis_options file, even if it exists under the project/module root. If you want to use the existing analysis_options file instead, set the value to false.")
                        .onQualifiers(Qualifiers.MODULE, Qualifiers.PROJECT)
                        .category(DART_CATEGORY)
                        .subCategory(ANALYSIS_SUBCATEGORY)
                        .defaultValue(DartAnalyzerSensor.ANALYZER_OPTIONS_OVERRIDE_DEFAULT)
                        .build());

        context.addExtension(
                PropertyDefinition.builder(DartAnalyzerSensor.ANALYZER_MODE)
                        .name("Analyzer")
                        .description("Which analyzer to use. If Dart 2.12+ is used, it is safe to leave on DETECT, otherwise manual configuration may be needed.")
                        .onQualifiers(Qualifiers.MODULE, Qualifiers.PROJECT)
                        .category(DART_CATEGORY)
                        .subCategory(ANALYSIS_SUBCATEGORY)
                        .options(DartAnalyzerSensor.ANALYZER_MODE_OPTIONS.stream().map(Enum::name).collect(Collectors.toList()))
                        .defaultValue(AnalyzerExecutable.Mode.defaultMode.name())
                        .type(PropertyType.SINGLE_SELECT_LIST)
                        .build());

        context.addExtension(
                PropertyDefinition.builder(DartAnalyzerSensor.ANALYZER_REPORT_PATH)
                        .name("Use existing report file for analysis")
                        .description("Path to analysis report file. Only evaluated when the analyzer is set to MANUAL.")
                        .onQualifiers(Qualifiers.MODULE, Qualifiers.PROJECT)
                        .category(DART_CATEGORY)
                        .subCategory(ANALYSIS_SUBCATEGORY)
                        .build());


        context.addExtension(
                PropertyDefinition.builder(DartAnalyzerSensor.ANALYZER_OUTPUT_MODE)
                        .name("Analyzer output format")
                        .description("This needs to be configured if the analyzer is set to MANUAL. If Dart 2.12+ is used, it is safe to leave on DETECT, otherwise manual configuration may also be needed.")
                        .onQualifiers(Qualifiers.MODULE, Qualifiers.PROJECT)
                        .category(DART_CATEGORY)
                        .subCategory(ANALYSIS_SUBCATEGORY)
                        .options(DartAnalyzerSensor.ANALYZER_OUTPUT_MODE_OPTIONS.stream().map(Enum::name).collect(Collectors.toList()))
                        .defaultValue(AnalyzerOutput.Mode.defaultMode.name())
                        .type(PropertyType.SINGLE_SELECT_LIST)
                        .build());

        // Tests
        context.addExtension(FlutterTestSensor.class);

        // Coverage
        context.addExtension(FlutterCoverageSensor.class);
    }
}
