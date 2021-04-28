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
package fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer;

import com.google.common.io.Resources;
import com.vdurmont.semver4j.Semver;
import fr.insideapp.sonarqube.dart.lang.PubSpec;
import fr.insideapp.sonarqube.dart.lang.PubSpecParser;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.squidbridge.api.AnalysisException;

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

    protected static final Logger LOGGER = LoggerFactory.getLogger(AnalyzerExecutable.class);

    private static final int ANALYZER_TIMEOUT = 10 * 60 * 1000;
    private static final String ANALYSIS_OPTIONS_FILENAME = "analysis_options.yaml";
    private static final String ANALYSIS_OPTIONS_FILE = "/fr/insideapp/sonarqube/dart/dartanalyzer/analysis_options.yaml";

    protected final SensorContext sensorContext;

    protected final AnalyzerOutput.Mode mode;

    protected AnalyzerExecutable(SensorContext sensorContext, AnalyzerOutput.Mode mode) {
        this.sensorContext = sensorContext;
        this.mode = mode;
    }

    protected abstract String getCommand();

    protected abstract String[] getArgs();

    /**
     * Run the analyzer executable, using the existing
     * analysis_options.yaml unless the override is configured.
     */
    public AnalyzerOutput analyze() {
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
            maybeThrowAnalysisException(result);

            return new AnalyzerOutput(mode, result.getOutputString());
        } catch (Exception e) {
            throw new AnalysisException(e);
        } finally {
            if (optionsCreated) {
                restoreAnalysisOptionsFile(sensorContext);
            }
        }
    }

    private void maybeThrowAnalysisException(ProcResult result) {
        final String errorString = result.getErrorString();
        if (errorString != null && !errorString.isEmpty()) {
            throw new AnalysisException(String.format("Error while running '%s' (exit %s): %s", result.getProcString(), result.getExitValue(), errorString));
        }
    }

    private boolean configureAnalysisOptionsFile(SensorContext sensorContext) {
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

    private void createAnalysisOptionsFile(SensorContext sensorContext) {
        File analysisOptionsFile = sensorContext.fileSystem().resolvePath(ANALYSIS_OPTIONS_FILENAME);
        URL inputUrl = DartAnalyzerSensor.class.getResource(ANALYSIS_OPTIONS_FILE);
        try {
            Resources.asByteSource(inputUrl).copyTo(com.google.common.io.Files.asByteSink(analysisOptionsFile));
        } catch (IOException e) {
            throw new AnalysisException(e);
        }
    }

    private void restoreAnalysisOptionsFile(SensorContext sensorContext) {
        File analysisOptionsFile = sensorContext.fileSystem().resolvePath(ANALYSIS_OPTIONS_FILENAME);
        File backupAnalysisOptionsFile = sensorContext.fileSystem().resolvePath(ANALYSIS_OPTIONS_FILENAME + ".sonar");
        if (backupAnalysisOptionsFile.exists() && backupAnalysisOptionsFile.renameTo(analysisOptionsFile)) {
            LOGGER.info("Restored original {} file", ANALYSIS_OPTIONS_FILENAME);
        } else {
            try {
                Files.delete(analysisOptionsFile.toPath());
                LOGGER.debug("Cleaned up temporary {} file", ANALYSIS_OPTIONS_FILENAME);
            } catch (IOException e) {
                throw new AnalysisException(e);
            }
        }
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    static AnalyzerExecutable create(SensorContext sensorContext, PubSpec pubSpec) {
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
                throw new AnalysisException("Could not determine a valid analyzer executable");
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
     *
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

        if (exitCode != 0) {
            LOGGER.warn("Command '{}' finished (exit {})", result.getProcString(), exitCode);
        } else {
            LOGGER.debug("Command '{}' finished (exit {})", result.getProcString(), exitCode);
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

class DartanalyzerAnalyzerExecutable extends AnalyzerExecutable {

    protected DartanalyzerAnalyzerExecutable(SensorContext sensorContext, AnalyzerOutput.Mode mode) {
        super(sensorContext, mode);
    }

    @Override
    public String getCommand() {
        return System.getProperty("os.name").toUpperCase().contains("WINDOWS")
                ? "dartanalyzer.bat"
                : "dartanalyzer";
    }

    @Override
    public String[] getArgs() {
        if (mode.equals(AnalyzerOutput.Mode.MACHINE)) {
            return new String[]{"--format=machine", "."};
        }
        return new String[]{"."};
    }
}

class DartAnalyzerExecutable extends AnalyzerExecutable {

    protected DartAnalyzerExecutable(SensorContext sensorContext, AnalyzerOutput.Mode mode) {
        super(sensorContext, mode);
    }

    @Override
    public String getCommand() {
        return System.getProperty("os.name").toUpperCase().contains("WINDOWS")
                ? "dart.bat"
                : "dart";
    }

    @Override
    public String[] getArgs() {
        if (mode.equals(AnalyzerOutput.Mode.MACHINE)) {
            return new String[]{"analyze", "--format=machine"};
        }
        return new String[]{"analyze"};
    }
}

class FlutterAnalyzerExecutable extends AnalyzerExecutable {

    protected FlutterAnalyzerExecutable(SensorContext sensorContext, AnalyzerOutput.Mode mode) {
        super(sensorContext, mode);
    }

    @Override
    public String getCommand() {
        return System.getProperty("os.name").toUpperCase().contains("WINDOWS")
                ? "flutter.bat"
                : "flutter";
    }

    @Override
    public String[] getArgs() {
        return new String[]{"analyze"};
    }
}

class ManualAnalyzerExecutable extends AnalyzerExecutable {

    protected ManualAnalyzerExecutable(SensorContext sensorContext, AnalyzerOutput.Mode mode) {
        super(sensorContext, mode);
    }

    @Override
    public String getCommand() {
        return "unused";
    }

    @Override
    public String[] getArgs() {
        return new String[]{};
    }

    @Override
    public AnalyzerOutput analyze() {
        final String path = sensorContext.config().get(DartAnalyzerSensor.ANALYZER_REPORT_PATH)
                .orElseThrow(() -> new AnalysisException("MANUAL analyzer mode is configured but not report path is set!"));
        LOGGER.info("Analysing report from {}", path);

        final File report = sensorContext.fileSystem().resolvePath(path);
        if (report != null && report.exists() && report.canRead()) {
            final String output;
            try {
                output = new String(Files.readAllBytes(report.toPath()), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new AnalysisException(e);
            }
            return new AnalyzerOutput(mode, output);
        } else {
            throw new AnalysisException(String.format("File '%s' does not exist or could not be read!", path));
        }
    }
}
