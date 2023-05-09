## Please check the Github releases for newer changelogs!

---

#### Breaking

- Removed the following rules. If you use any of these rules in custom quality profiles, please remove them from the profile before the update!
  - `super_goes_last (deprecated)` -> replaced by `super_goes_last`
  - `invalid_use_of_covariant_in_extension` -> replaced by `invalid_use_of_covariant`
  - `extension_declares_abstract_member`
  - `extension_declares_constructor`
  - `extension_declares_instance_field`
  - `undefined_super_method` -> replaced by `undefined_super_member`
  - `avoid_web_libraries_in_flutter (experimental)` -> replaced by `avoid_web_libraries_in_flutter`
  - `prefer_bool_in_asserts (deprecated)` -> replaced by `prefer_bool_in_asserts`
  - `invariant_booleans (experimental)` -> replaced by `invariant_booleans`
- Removed `dartanalyzer` implementation that scans each file individual, this may affect projects running on a (pretty) old Dart SDK.
  The replacement runs legacy `dartanalyzer .` instead which is much faster.
- Renamed `sonar.flutter.analyzer.mode` to `sonar.dart.analyzer.mode` and reworked implementation.
  - Available options `DETECT|DART|FLUTTER|MANUAL|DARTANALYZER`
  - Defaults to automatic detection by parsing the `pubspec.yaml`.
  - Uses `flutter analyze` when `environment.flutter` is configured in `pubspec.yaml`.
  - Uses `dart analyze` when `environment.flutter` is NOT configured in `pubspec.yaml`.
  - Allows to be configured for legacy `dartanalyzer` if required (`DARTANALYZER`)
  - Allows to be configured for pre-existing analysis reports (`MANUAL`)
- Renamed `sonar.dart.analysis.useExistingOptions` to `sonar.dart.analyzer.options.override`, now defaults to `true`
- Renamed `sonar.dart.analysis.reportPath` to `sonar.dart.analyzer.report.path` - this is only evaluated when `sonar.dart.analyzer.mode`is set to `MANUAL`
- Added `sonar.dart.analyzer.report.mode` option
  - Available options `DETECT|MACHINE|LEGACY`
  - Defaults to automatic detection by parsing the Dart SDK version and defaults to `--format=machine` for Dart when SDK >= 2.12
  - Can be set to legacy mode when using pre-existing analysis report or an older Dart SDK

#### Experimental

- None.

#### Enhancements

- Add a `pubspec.yaml` sensor which adds the file to SonarQube and allow showing lints for this file
- Add support for multi-module projects (`sonar.modules=mylib,mylib_generator,example`)
- Added missing dart keywords and corrected some
  - `try`
  - `hide`
  - `show`
  - `late`
  - `of`
  - `continue`
  - `covariant`
  - `out`
  - `do`
  - `dynamic`
  - `native`

#### Bug Fixes

- None.

## 0.3.2

#### Breaking

- None.

#### Experimental

- None.

#### Enhancements

- Allow re-using an existing dartanalyzer report with `sonar.dart.analysis.reportPath` (thanks to [Peter Leibiger](https://github.com/kuhnroyal))
- Add missing dart keywords `extension`, `on`, `mixin` (thanks to [Peter Leibiger](https://github.com/kuhnroyal))
- Add pedantic 1.9.0 profile (thanks to [Daniel Morawetz](https://github.com/dmorawetz))
- Faster analysis with 'flutter analyze' and support for different analysis modes with `sonar.flutter.analyzer.mode` (thanks to [Marc Reichelt](https://github.com/mreichelt))

#### Bug Fixes

- Ensure analyzer encoding is UTF-8 (thanks to [Daniel Morawetz](https://github.com/dmorawetz))


## 0.3.1

#### Breaking

- None.

#### Experimental

- None.

#### Enhancements

- Better handling of missing test report file.
- Faster analysis on large projects (increased analyser page size to 50).

#### Bug Fixes

- Test report parsing fix for Windows (thanks to [victorgilc](https://github.com/victorgilc)).
- Removed duplicated issues on the same file.

## 0.3.0

#### Breaking

- None.

#### Experimental

- None.

#### Enhancements

- Add Flutter 1.20.0 test report compatibility (thanks to [Peter Leibiger](https://github.com/kuhnroyal)), fixes [#19](https://github.com/insideapp-oss/sonar-flutter/issues/19)
- Improve test report parsing (thanks to [Peter Leibiger](https://github.com/kuhnroyal)), fixes [#13](https://github.com/insideapp-oss/sonar-flutter/issues/13)
- Ability to use existing analysis options (configurable behavior) (thanks to [Stephane Janicaud](https://github.com/stephanecodes])), fixes [#23](https://github.com/insideapp-oss/sonar-flutter/issues/23), [#18](https://github.com/insideapp-oss/sonar-flutter/issues/18)
- Paginated analysis with dartanalyzer (thanks to [victorgilc](https://github.com/victorgilc))

#### Bug Fixes

- Report the correct test case count (thanks to [Peter Leibiger](https://github.com/kuhnroyal))
- Better test report parsing (thanks to [victorgilc](https://github.com/victorgilc))
- Restore Java 8 compatibility required by the sonar-scanner (thanks to [Peter Leibiger](https://github.com/kuhnroyal))
- Restore the original analysis options file when analysis crashes (thanks to [amond](https://github.com/amondnet))
- Windows support (thanks to [victorgilc](https://github.com/victorgilc)), fixes [#12](https://github.com/insideapp-oss/sonar-flutter/issues/12), [#24](https://github.com/insideapp-oss/sonar-flutter/issues/24)
- Running sonar-scanner does not result no 0 exit code anymore when issues are found by dartanalyzer (thanks to [amond](https://github.com/amondnet)), fixes [#11](https://github.com/insideapp-oss/sonar-flutter/issues/11)

## 0.2.1

#### Breaking

- None.

#### Experimental

- None.

#### Enhancements

- Added diagnostic rules (thanks to [etiennecadicidean](https://github.com/etiennecadicidean))

#### Bug Fixes

- None

## 0.2.0

#### Breaking

- None.

#### Experimental

- None.

#### Enhancements

- Syntax coloration
- Cyclomatic complexity computation
- More accurate code line count 

#### Bug Fixes

- None

## 0.1.0

#### Breaking

- Initial release.

#### Experimental

- None.

#### Enhancements

- None.

#### Bug Fixes

- None.
