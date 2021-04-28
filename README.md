![CI](https://github.com/insideapp-oss/sonar-flutter/workflows/CI/badge.svg)
![Coverage](https://codecov.io/gh/insideapp-oss/sonar-flutter/branch/master/graph/badge.svg)

# SonarQube plugin for Flutter / Dart

A plugin to enable analysis of Dart and Flutter projects into SonarQube.

<p style="text-align: center">
	<img src="screenshots/main.png" alt="main.png" width="80%"/>
</p>

## Features

| Feature    | Supported              |
|------------|------------------------|
| Size       | YES                    |
| Issues     | YES ([dartanalyzer](https://dart.dev/tools/dartanalyzer) rules)|
| Tests      | YES                    |
| Coverage   | YES                    |
| Complexity | YES                    |
| Syntax     | YES                    |

The plugin is compatible with sonarQube 6.7+.

## Download

Checkout the [Releases](https://github.com/insideapp-oss/sonar-flutter/releases) page.

Changelog is available [here](https://github.com/insideapp-oss/sonar-flutter/blob/develop/CHANGELOG.md).

## Prerequisites

### Flutter SDK (when analyzing a Flutter project)

Install Flutter as explained on the official documentation page.

[Flutter Installation instructions](https://flutter.dev/docs/get-started/install)

### Dart

Dart is downloaded by the Flutter SDK in **$FLUTTER_HOME/bin/cache/dart-sdk**, however command lines are not on the path by default (dart must be on the path).

It is recommended to install Dart SDK separately as explained here : [Install the Dart SDK](https://dart.dev/get-dart) for more reliability in a CI/CD environment.

### sonar-scanner (requires Java)

Install sonar-scanner as explained in the official documentation.

[Installation instructions](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/)


## Installation (on the server)

- Download the plugin binary into the **$SONARQUBE_HOME/extensions/plugins** directory.
- Restart the server.

## Project configuration

Create a **sonar-project.properties** file at the root with this content :

```properties
# Project identification
sonar.projectKey=flutter_rocks
sonar.projectName=Flutter Rocks
sonar.projectVersion=1.0
	
# Source code location.
# Path is relative to the sonar-project.properties file. Defaults to .
# Use commas to specify more than one file/folder.
# It is good practice to add pubspec.yaml to the sources as the analyzer
# may produce warnings for this file as well.
sonar.sources=lib,pubspec.yaml
sonar.tests=test

# Encoding of the source code. Default is default system encoding.
sonar.sourceEncoding=UTF-8

# Analyzer mode
# Can be:
# - DETECT (attempt to detect automatically) - default
# - MANUAL (an existing report needs to be provided)
# - FLUTTER (flutter analyze)
# - DART (dart analyze)
# - DARTANALYZER (dartanalyzer)
# sonar.dart.analyzer.mode= 

# Allows reuse of an existing analyzer report when mode is MANUAL
# sonar.dart.analyzer.report.path=

# Analyzer report output mode
# Can be:
# - DETECT (attempt to detect automatically, requires Dart SDK on the PATH) - default
# - MACHINE (a new machine readable output that is available for Dart 2.12+)
# - LEGACY (attempts to parse human readable output from dart/flutter) - default
# sonar.dart.analyzer.report.mode= 
```

*For a complete list of available options, please refer to the [SonarQube documentation](https://docs.sonarqube.org/latest/analysis/analysis-parameters/).*

### Use existing analysis options

The plugin uses its own analysis options file.
If `analysis_options.yaml` file already exists under the project root, it will be saved during the analysis and then restored to its initial state.

To disable this behavior and use the existing`analysis_options.yaml` file instead, add the following line to `sonar-project.properties` file :

```
# Use existing options to perform dartanalyzer analysis
sonar.dart.analyzer.options.override=true
```

### Multi-module sample

It is possible to analyze a project with multiple modules. For example a Dart generator library with an example.

```properties
sonar.projectKey=mylib
sonar.sourceEncoding=UTF-8

sonar.modules=mylib,mylib_generator,example

# mylib
mylib.sonar.sources=lib,pubspec.yaml
mylib.sonar.dart.analyzer.mode=MANUAL
mylib.sonar.dart.analyzer.report.mode=LEGACY
mylib.sonar.dart.analyzer.report.path=build/reports/analysis-results.txt

# mylib_generator
mylib_generator.sonar.sources=lib,pubspec.yaml
mylib_generator.sonar.dart.analyzer.options.override=false
mylib_generator.sonar.dart.analyzer.mode=MANUAL
mylib_generator.sonar.dart.analyzer.report.mode=MACHINE
mylib_generator.sonar.dart.analyzer.report.path=build/reports/analysis-results.txt

# example
example.sonar.sources=lib,pubspec.yaml
example.sonar.tests=test
example.sonar.exclusions=**/*/*.g.dart
example.sonar.dart.analyzer.options.override=false
example.sonar.dart.analyzer.mode=MANUAL
example.sonar.dart.analyzer.report.mode=LEGACY
example.sonar.dart.analyzer.report.path=build/reports/analysis-results.txt
```

## Run analysis

Use the following commands from the root folder to start an analysis: 

```console
# Download dependencies 
flutter pub get 
# Run tests with User feedback (in case some test are failing)
flutter test
# Run tests without user feedback regeneration tests.output and coverage/lcov.info
flutter test --machine --coverage > tests.output 

# Run the analysis and publish to the SonarQube server
sonar-scanner
```

## Using `dart_test.yaml`

Dart/Flutter support the use of a `dart_test.yaml` file to configure test behavior.
This file can be used to configure the test output format and location. More information can be found [here](https://github.com/dart-lang/test/blob/master/pkgs/test/doc/configuration.md).

```yaml
file_reporters:
  json: build/reports/test-results.json
```

With this, it is no longer use the `--machine` switch or redirect the output.
It is currently not possible to configure the coverage out in this file.
You may follow and upvote these related issue if interested:
* Configuration option - https://github.com/dart-lang/test/issues/1361
* Dart coverage support - https://github.com/dart-lang/test/issues/1265


## Available options

All options are configurable in the SonarQube UI, via `sonar-project.properties` or `-D` parameters.

|Name|Options|Default|Description
|---|---|---|---|
|`sonar.dart.analyzer.mode`|<code>DETECT&#124;DART&#124;FLUTTER&#124;MANUAL&#124;DARTANALYZER</code>|`DETECT`|By default the plugin attempts to detect a fitting analyzer (`flutter analyze` or `dart analyze`) by parsing the `environment` from `pubspec.yaml`. This can be set to `MANUAL` to provide and existing report file. For compatibility with older Dart versions, this can be set to `DARTANALYZER`.|
|`sonar.dart.analyzer.options.override`|<code>true&#124;false</code>|`true`|By default any local `analysis_options.yaml` will be replaced for the analysis. This can be prevented by setting this to `false`.
|`sonar.dart.analyzer.report.mode`|<code>DETECT&#124;MACHINE&#124;LEGACY</code>|`DETECT`|The new machine readable output can be automatically detected if Dart SDK is available on the $PATH.|
|`sonar.dart.analyzer.report.path`|A file path|-|This is required if the analyzer mode is set to `MANUAL`.|
|`sonar.flutter.tests.reportPath`|A file path|-|The path to the test report JSON file.|
|`sonar.flutter.coverage.reportPath`|A file path|-|The path to the test coverage file in LCOV format.|


## Contributing

Any help is welcome, and PRs will be greatly appreciated!


## License

SonarQube Plugin for Flutter / Dart is released under the GNU LGPL v3 license. See the [LICENSE](https://github.com/insideapp-oss/sonar-flutter/blob/develop/LICENSE) file for more information.
