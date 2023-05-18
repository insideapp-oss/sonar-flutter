# Contributing

This documentation is a guide for plugin developers / contributors.

## Requirements

- Java JDK 11+
- Maven 3.8 or later
- A local SonarQube instance for local testing

## Main commands

#### Add missing license headers on source files

```bash
$ mvn license:format
```

#### Local plugin deployment

In order to start the plugin, SONARQUBE_HOME environment variable must be set

If variable is already set, use:
```bash
$ mvn install
```

If variable is not set, it can be set inline:
```bash
$ SONARQUBE_HOME=~/path/to/sonarqube mvn install
```

When started locally SonarQube UI is available at http://localhost:9000

#### Packaging

```bash
$ mvn package
```

## Updating rules

The plugin rules are based on external linters such as dartanalyzer.

When a new version of the linter (with new rules) is released, the matching repositoryRule repository must be updated as well in order to include the new rules.

This update is mostly automatic with the execution of a Groovy script.

### dartanalyzer update

Use the following command to update the SwiftLint rules file:

```bash
$ mvn groovy:execute -Dsource=scripts/updateDartAnalyzerRules.groovy
```

### Filling rules information

Eventhough updating rule repository file is mostly automatic, some data related to rules cannot be automatically computed.

This is the case for :
- severity (blocker, critical, major, minor, info)
- type (code smell, bug, vulnerability)
- debt (remediation effort estimate, optional)

At the end of a rule update script, rules are scanned, and you are asked to fill missing information:

```console
Missing information on rule always_use_package_imports
Avoid relative imports for files in lib/.

Severity? (1 = BLOCKER, 2 = CRITICAL, 3 = MAJOR, 4 = MINOR, 5 = INFO)
3
MAJOR
Type? (1 = CODE_SMELL, 2 = BUG, 3 = VULNERABILITY, 4 = SECURITY_HOTSPOT)
1
CODE_SMELL
Remediation time? (in minutes)
5
5min
```
> **Warning**
> By default, rules manually processed are limited to 20 (defined in the `scripts.max-manual`property of the `pom.xml`) per run.
> If there are more than 20 rules to fill manually, launch the script again to process next rules.

## Releasing

### Snapshots

Every push to `develop` branch updates the matching snapshot release.

For example: if project version is set to `0.1-SNAPSHOT` on`develop`. A `0.1-SNAPSHOT` will be released (or release will be updated if it already exists) as soon as the `maven.yml` GitHub actions workflow finishes.

### Stable

A new stable release is pushed on GitHub, on every new tag creation (performed by the `release.yml` GitHub Actions workflow).

The steps to issue a new stable release are:
1. Merge `develop` branch to `main`branch (do not squash in order to keep commit history).
2. Create a new tag from `main` branch with a naming matching the release number. Example:
```bash
$ git tag -a 0.1.0
```
3. Push the new tag with:
```bash
$ git push --tags
```

Once released, remember to update the project version on the `develop`branch to a new snapshot number:
```bash
$ mvn versions:set -DnewVersion=0.2-SNAPSHOT
``` 

