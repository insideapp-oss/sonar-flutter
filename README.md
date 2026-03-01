# SonarQube Plugin for Flutter / Dart

> Static code analysis for Dart and Flutter projects in SonarQube — with Dart 3.x support, 329 rules, and state management linting.


<p align="center">
  <img src="screenshots/main.png" alt="SonarQube Flutter Plugin" width="80%"/>
</p>

---

## What Does It Do?

| Feature | Status | Details |
|---------|--------|---------|
| **Issues** | Supported | 305 dartanalyzer rules + 24 state management rules |
| **Dart 3.x** | Supported | sealed classes, records, patterns, switch expressions |
| **State Management** | Supported | BLoC (9 rules), Riverpod (15 rules) auto-detected from `pubspec.yaml` |
| **Coverage** | Supported | LCOV format |
| **Tests** | Supported | JSON test reports |
| **Complexity** | Supported | Cyclomatic complexity |
| **Syntax** | Supported | Highlighting & CPD tokens |
| **MQR Mode** | Supported | Clean Code attributes & software quality impacts (SonarQube v26+) |

**Compatible with:** SonarQube 7.9+ (MQR mode requires v26+)

---

## Quick Start

### 1. Prerequisites

| Tool | Required For | Installation |
|------|-------------|-------------|
| **Flutter SDK** | Flutter projects | [flutter.dev/docs/get-started/install](https://flutter.dev/docs/get-started/install) |
| **Dart SDK** | All projects | [dart.dev/get-dart](https://dart.dev/get-dart) |
| **sonar-scanner** | Running analysis | [docs.sonarqube.org](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner/) |
| **Java** | sonar-scanner | Bundled with scanner or install separately |

### 2. Install the Plugin

1. Download the latest JAR from [Releases](https://github.com/insideapp-oss/sonar-flutter/releases)
2. Copy it to `$SONARQUBE_HOME/extensions/plugins/`
3. Restart SonarQube

### 3. Configure Your Project

Create `sonar-project.properties` in your project root:

```properties
sonar.projectKey=my_flutter_app
sonar.projectName=My Flutter App
sonar.projectVersion=1.0.0

sonar.sources=lib,pubspec.yaml
sonar.tests=test
sonar.sourceEncoding=UTF-8
```

### 4. Run Analysis

```bash
# Install dependencies
flutter pub get

# Run tests with coverage
flutter test --machine --coverage > tests.output

# Analyze and publish to SonarQube
sonar-scanner
```

That's it! Open your SonarQube dashboard to see the results.

---

## Configuration Options

All options can be set in `sonar-project.properties`, SonarQube UI, or via `-D` parameters.

| Option | Values | Default | Description |
|--------|--------|---------|-------------|
| `sonar.dart.analyzer.mode` | `DETECT` `DART` `FLUTTER` `MANUAL` | `DETECT` | Which analyzer to use. `DETECT` picks automatically based on `pubspec.yaml`. Use `MANUAL` for pre-existing reports. |
| `sonar.dart.analyzer.options.override` | `true` / `false` | `true` | Replace local `analysis_options.yaml` with the plugin's version during analysis. Set to `false` to keep yours. |
| `sonar.dart.analyzer.report.mode` | `DETECT` `MACHINE` `LEGACY` | `DETECT` | Output format. Auto-detected from Dart SDK version when set to `DETECT`. |
| `sonar.dart.analyzer.report.path` | file path | - | Path to existing report file. Only used with `MANUAL` mode. |
| `sonar.flutter.tests.reportPath` | file path(s) | `tests.output` | Path to test report JSON file(s). Comma-separated, no wildcards. |
| `sonar.flutter.coverage.reportPath` | file path | `coverage/lcov.info` | Path to LCOV coverage report. |

> **Note:** The legacy `DARTANALYZER` mode has been removed. It was deprecated in Dart 2.18 and no longer exists in Dart 3.x. Use `DART` or `FLUTTER` mode instead.

---

## Rules

### Dart Analyzer Rules (305)

The plugin bundles 305 rules from `dart analyze` / `flutter analyze`:
- Lint rules (style, best practices, error prevention)
- Analyzer diagnostics (type errors, null safety, etc.)
- 14 new Dart 3.x rules: `avoid_futureor_void`, `switch_on_type`, `unnecessary_async`, `unsafe_variance`, and more

### State Management Rules (24)

Automatically activated when BLoC or Riverpod is detected in `pubspec.yaml`:

**BLoC (9 rules)** — `flutter_bloc` / `bloc` dependency detected:
- `bloc_avoid_flutter_imports` — Keep BLoC platform-independent
- `bloc_avoid_public_methods` — Use events, not public methods
- `bloc_avoid_mutable_state` — State classes must be immutable
- `bloc_close_bloc` — Prevent memory leaks
- and 5 more...

**Riverpod (15 rules)** — `flutter_riverpod` / `riverpod` dependency detected:
- `riverpod_missing_provider_scope` — ProviderScope required at root
- `riverpod_avoid_read_inside_build` — Use ref.watch in build
- `riverpod_avoid_watch_outside_build` — Use ref.read in callbacks
- `riverpod_avoid_build_context_in_providers` — Keep providers decoupled
- and 11 more...

---

## Dart 3.x Grammar Support

The ANTLR parser understands Dart 3 syntax:

- **Class modifiers:** `sealed`, `base`, `final`, `interface`, `mixin class`
- **Records:** `(int, String)` types and `(1, 'hello')` literals
- **Patterns:** `switch` expressions, `if-case`, variable/type/wildcard patterns
- **Guards:** `when` clause in switch cases and if-case
- **Nullable types:** `String?`, `int?` in type positions

---

## Advanced Usage

### Use Your Own `analysis_options.yaml`

```properties
sonar.dart.analyzer.options.override=false
```

### Multi-Module Projects

```properties
sonar.projectKey=mylib
sonar.sourceEncoding=UTF-8
sonar.modules=mylib,mylib_generator,example

mylib.sonar.sources=lib,pubspec.yaml
mylib.sonar.dart.analyzer.mode=MANUAL
mylib.sonar.dart.analyzer.report.mode=LEGACY
mylib.sonar.dart.analyzer.report.path=build/reports/analysis-results.txt

mylib_generator.sonar.sources=lib,pubspec.yaml
mylib_generator.sonar.dart.analyzer.options.override=false
mylib_generator.sonar.dart.analyzer.mode=MANUAL
mylib_generator.sonar.dart.analyzer.report.mode=MACHINE
mylib_generator.sonar.dart.analyzer.report.path=build/reports/analysis-results.txt

example.sonar.sources=lib,pubspec.yaml
example.sonar.tests=test
example.sonar.exclusions=**/*/*.g.dart
example.sonar.dart.analyzer.options.override=false
example.sonar.dart.analyzer.mode=MANUAL
example.sonar.dart.analyzer.report.mode=LEGACY
example.sonar.dart.analyzer.report.path=build/reports/analysis-results.txt
```

### Using `dart_test.yaml`

Configure test output in `dart_test.yaml` instead of `--machine` flag:

```yaml
file_reporters:
  json: build/reports/test-results.json
```

Then point `sonar.flutter.tests.reportPath` to `build/reports/test-results.json`.

---

## Building from Source

```bash
# Compile
mvn clean compile

# Run tests
mvn test

# Build plugin JAR
mvn clean package
# Output: sonar-flutter-plugin/target/sonar-flutter-plugin-*.jar
```

**Requirements:** Java 17+, Maven 3.8+

---

## Contributing

PRs are welcome! Please read [CONTRIBUTING](CONTRIBUTING.md) for guidelines.

## License

GNU LGPL v3 — see [LICENSE](LICENSE) for details.

---

*Forked from [insideapp-oss/sonar-flutter](https://github.com/insideapp-oss/sonar-flutter). Extended with Dart 3.x support, state management rules, and MQR mode.*
