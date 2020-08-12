# SonarQube Plugin for Flutter / Dart

## 0.3.1

#### Breaking

- None.

#### Experimental

- None.

#### Enhancements

- None.

#### Bug Fixes

- Test report parsing fix for Windows (thanks to [victorgilc](https://github.com/victorgilc)).
- Removed duplicated issues on the same file.
- Better handling of missing test report file.
- Faster analysis on large projects (increased analyser page size to 50).

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
