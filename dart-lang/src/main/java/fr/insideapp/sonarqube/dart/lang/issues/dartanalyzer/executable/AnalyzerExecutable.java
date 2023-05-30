/*
 * SonarQube Flutter Plugin - Enables analysis of Dart and Flutter projects into SonarQube.
 * Copyright © 2020 inside|app (contact@insideapp.fr)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.executable;

import com.google.common.io.Resources;
import com.vdurmont.semver4j.Semver;
import fr.insideapp.sonarqube.dart.lang.PubSpec;
import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.AnalyzerOutput;
import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.DartAnalyzerSensor;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AnalyzerExecutable {

    public enum Mode {
        DETECT,
        MANUAL,
        DARTANALYZER,
        DART,
        FLUTTER;

        public static final Mode defaultMode = DETECT;
    }

    protected static final Logger LOGGER = Loggers.get(AnalyzerExecutable.class);

    private static final int ANALYZER_TIMEOUT = 10 * 60 * 1000;
    private static final String ANALYSIS_OPTIONS_FILENAME = "analysis_options.yaml";
    private static final String ANALYSIS_OPTIONS_FILE = "/dartanalyzer/analysis_options.yaml";

    protected final SensorContext sensorContext;

    protected final AnalyzerOutput.Mode outputMode;

    protected AnalyzerExecutable(SensorContext sensorContext, AnalyzerOutput.Mode outputMode) {
        this.sensorContext = sensorContext;
        this.outputMode = outputMode;
    }

    protected abstract String getCommand();

    protected abstract String[] getArgs();

    protected abstract Mode getMode();

    /**
     * Run the analyzer executable, using the existing
     * analysis_options.yaml unless the override is configured.
     */
    public AnalyzerOutput analyze() throws IOException {
        final boolean optionsCreated = configureAnalysisOptionsFile(sensorContext);
        final String command = getCommand();
        final String[] args = getArgs();
        try {
            LOGGER.info("Running '{} {}'", command, args);
            final ProcResult result = new ProcBuilder(command, args)
                    .withWorkingDirectory(sensorContext.fileSystem().baseDir())
                    .withTimeoutMillis(ANALYZER_TIMEOUT)
                    .ignoreExitStatus()
                    .run();

            LOGGER.info("Command '{}' finished (exit {})", result.getProcString(), result.getExitValue());
            maybeThrowException(result);

            return new AnalyzerOutput(outputMode, getMode(), result.getOutputString());
        } finally {
            if (optionsCreated) {
                restoreAnalysisOptionsFile(sensorContext);
            }
        }
    }

    private void maybeThrowException(ProcResult result) {
        if (result.getExitValue() != 0 && result.getOutputString().isEmpty()) {
            throw new IllegalStateException(String.format("Error while running '%s' (exit %s): %s", result.getProcString(), result.getExitValue(), result.getErrorString()));
        }
    }

    private boolean configureAnalysisOptionsFile(SensorContext sensorContext) throws IOException {
        final boolean exists = existsAnalysisOptionsFile(sensorContext);
        if (exists && !shouldOverrideAnalysisOptionsFile(sensorContext)) {
            LOGGER.info("Using existing {} since override is disabled", ANALYSIS_OPTIONS_FILENAME);
            return false;
        }
        if (exists) {
            saveCurrentAnalysisOptionsFile(sensorContext);
        }
        createAnalysisOptionsFile(sensorContext);
        return true;
    }

    private boolean shouldOverrideAnalysisOptionsFile(SensorContext sensorContext) {
        return sensorContext.config().getBoolean(DartAnalyzerSensor.ANALYZER_OPTIONS_OVERRIDE)
                .orElse(Boolean.valueOf(DartAnalyzerSensor.ANALYZER_OPTIONS_OVERRIDE_DEFAULT));
    }

    private boolean existsAnalysisOptionsFile(SensorContext sensorContext) {
        return sensorContext.fileSystem().resolvePath(ANALYSIS_OPTIONS_FILENAME).exists();
    }

    private void saveCurrentAnalysisOptionsFile(SensorContext sensorContext) {
        File analysisOptionsFile = sensorContext.fileSystem().resolvePath(ANALYSIS_OPTIONS_FILENAME);
        String backup = ANALYSIS_OPTIONS_FILENAME + ".sonar";
        if (analysisOptionsFile.renameTo(sensorContext.fileSystem().resolvePath(backup))) {
            LOGGER.info("Backup of original {} file to {}", ANALYSIS_OPTIONS_FILENAME, backup);
        }
    }

    private void createAnalysisOptionsFile(SensorContext sensorContext) throws IOException {
        File analysisOptionsFile = sensorContext.fileSystem().resolvePath(ANALYSIS_OPTIONS_FILENAME);
        URL inputUrl = DartAnalyzerSensor.class.getResource(ANALYSIS_OPTIONS_FILE);
        assert inputUrl != null;
        Resources.asByteSource(inputUrl).copyTo(com.google.common.io.Files.asByteSink(analysisOptionsFile));
    }

    private void restoreAnalysisOptionsFile(SensorContext sensorContext) throws IOException {
        File analysisOptionsFile = sensorContext.fileSystem().resolvePath(ANALYSIS_OPTIONS_FILENAME);
        File backupAnalysisOptionsFile = sensorContext.fileSystem().resolvePath(ANALYSIS_OPTIONS_FILENAME + ".sonar");
        if (backupAnalysisOptionsFile.exists() && backupAnalysisOptionsFile.renameTo(analysisOptionsFile)) {
            LOGGER.info("Restored original {} file", ANALYSIS_OPTIONS_FILENAME);
        } else {
            Files.delete(analysisOptionsFile.toPath());
            LOGGER.debug("Cleaned up temporary {} file", ANALYSIS_OPTIONS_FILENAME);
        }
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public static AnalyzerExecutable create(SensorContext sensorContext, PubSpec pubSpec) {
        AnalyzerExecutable.Mode mode = getAnalyzerMode(sensorContext);
        LOGGER.debug("Analyzer configured for mode: {}", mode);
        if (mode.equals(AnalyzerExecutable.Mode.DETECT)) {
            mode = detectMode(pubSpec);
            LOGGER.debug("Automatically detected Analyzer mode: {}", mode);
        }
        AnalyzerOutput.Mode outputMode = getAnalyzerOutputMode(sensorContext);
        LOGGER.debug("Analyzer configured for output mode: {}", outputMode);
        if (outputMode.equals(AnalyzerOutput.Mode.DETECT)) {
            outputMode = detectOutputMode(sensorContext, mode);
            LOGGER.debug("Automatically detected Analyzer output mode: {}", outputMode);
        }
        switch (mode) {
            case MANUAL:
                if (outputMode.equals(AnalyzerOutput.Mode.DETECT)) {
                    LOGGER.warn("Manual test report is configured without output mode, this may not work! Defaulting to legacy output.");
                    outputMode = AnalyzerOutput.Mode.LEGACY;
                }
                return new ManualAnalyzerExecutable(sensorContext, outputMode);
            case DART:
                return new DartAnalyzerExecutable(sensorContext, outputMode);
            case DARTANALYZER:
                return new DartanalyzerAnalyzerExecutable(sensorContext, outputMode);
            case FLUTTER:
                return new FlutterAnalyzerExecutable(sensorContext, outputMode);
            default:
                throw new IllegalArgumentException("Could not determine a valid analyzer executable");
        }
    }

    private static AnalyzerExecutable.Mode detectMode(PubSpec pubSpec) {
        return pubSpec.isFlutter() ? AnalyzerExecutable.Mode.FLUTTER : AnalyzerExecutable.Mode.DART;
    }

    /**
     * The function attempts to figure out the best output mode
     * for the given analyzer mode.
     * If Dart SDK >=2.12 is found, and the executable is not Flutter
     * then it will use machine readable output.
     * Otherwise it will fall back to legacy output which is not guaranteed
     * to be parsable.
     *
     * @return the output mode
     */
    private static AnalyzerOutput.Mode detectOutputMode(SensorContext sensorContext, AnalyzerExecutable.Mode mode) {
        if (mode.equals(AnalyzerExecutable.Mode.FLUTTER)) {
            // Flutter does not yet support --format=machine
            LOGGER.debug("Flutter project does not support machine mode yet, defaulting to legacy mode.");
            return AnalyzerOutput.Mode.LEGACY;
        }

        // Figure out Dart version and use machine output when >=2.12
        final String command = System.getProperty("os.name").toUpperCase().contains("WINDOWS")
                ? "dart.bat"
                : "dart";
        final ProcResult result = new ProcBuilder(command, "--version")
                .withWorkingDirectory(sensorContext.fileSystem().baseDir())
                .run();

        final int exitCode = result.getExitValue();

        String logMessage = String.format("Command '%s' finished (exit %d)", result.getProcString(), exitCode);
        if (exitCode != 0) {
            LOGGER.warn(logMessage);
        } else {
            LOGGER.debug(logMessage);
        }

        final String output = new String(result.getErrorBytes(), StandardCharsets.UTF_8).split("\\R", 2)[0];

        // The version always written to stderr - https://github.com/dart-lang/sdk/issues/19704
        // Need to use exit code to determine success
        if (exitCode != 0) {
            LOGGER.warn("Could not determine Dart version from output: '{}' - defaulting to legacy mode", output);
            return AnalyzerOutput.Mode.LEGACY;
        }

        final Matcher matcher = Pattern.compile("^Dart SDK version: (.*) \\((.*)\\) \\((.*)\\) on (.*)$").matcher(output);
        if (!matcher.matches()) {
            LOGGER.warn("Could not determine Dart version from output: '{}' - defaulting to legacy mode", output);
            return AnalyzerOutput.Mode.LEGACY;
        }
        final String version = matcher.group(1);
        try {
            final Semver semver = new Semver(version);
            boolean isDart212 = semver.isGreaterThanOrEqualTo("2.12.0");
            if (isDart212) {
                LOGGER.info("Detected Dart SDK 2.12.0+ - using MACHINE mode for analyzer output.");
                return AnalyzerOutput.Mode.MACHINE;
            }
            LOGGER.info("Detected Dart SDK <2.12.0 - using LEGACY mode for analyzer output.");
            return AnalyzerOutput.Mode.LEGACY;
        } catch (Exception e) {
            LOGGER.warn("Could not detect Dart version from output: '{}' - defaulting to legacy mode", output, e);
        }
        return AnalyzerOutput.Mode.LEGACY;
    }

    private static AnalyzerExecutable.Mode getAnalyzerMode(SensorContext sensorContext) {
        return sensorContext.config()
                .get(DartAnalyzerSensor.ANALYZER_MODE)
                .map(AnalyzerExecutable.Mode::valueOf)
                .orElse(AnalyzerExecutable.Mode.defaultMode);
    }

    private static AnalyzerOutput.Mode getAnalyzerOutputMode(SensorContext sensorContext) {
        return sensorContext.config()
                .get(DartAnalyzerSensor.ANALYZER_OUTPUT_MODE)
                .map(AnalyzerOutput.Mode::valueOf)
                .orElse(AnalyzerOutput.Mode.defaultMode);
    }
}
