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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.buildobjects.process.ProcBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputFile.Type;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.batch.sensor.issue.internal.DefaultIssueLocation;
import org.sonar.api.rule.RuleKey;

import com.google.common.io.Files;
import com.google.common.io.Resources;

import fr.insideapp.sonarqube.dart.lang.Dart;

public class DartAnalyzerSensor implements Sensor {
	private static final Logger LOGGER = LoggerFactory.getLogger(DartAnalyzerSensor.class);
	private static final String ANALYZER_COMMAND = System.getProperty("os.name").toUpperCase().contains("WINDOWS")
			? "dartanalyzer.bat"
			: "dartanalyzer";
	private static final String ANALYSIS_OPTIONS_FILENAME = "analysis_options.yaml";
	private static final String ANALYSIS_OPTIONS_FILE = "/fr/insideapp/sonarqube/dart/dartanalyzer/analysis_options.yaml";
	private static final Integer PAGE_SIZE = 10;

	@Override
	public void describe(SensorDescriptor sensorDescriptor) {
		sensorDescriptor.onlyOnLanguage(Dart.KEY).name("dartanalyzer sensor").onlyOnFileType(Type.MAIN);
	}

	@Override
	public void execute(SensorContext sensorContext) {
		try {
			this.verifyDartAnalyzerExists();

			this.saveCurrentAnalysisOptionsFile(sensorContext);

			this.createAnalysisOptionsFile(sensorContext);

			recordIssues(sensorContext, buildIssues(getFilesWithAbsolutePath(sensorContext)));

			this.restoreCurrentAnalysisOptionsFile(sensorContext);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private List<DartAnalyzerReportIssue> buildIssues(List<String> filesWithAbsolutePath)
			throws IOException, InterruptedException {
		List<DartAnalyzerReportIssue> issues = new ArrayList<DartAnalyzerReportIssue>();

		for (String paginatedFileBatch : getPaginatedFilesPaths(filesWithAbsolutePath)) {

			LOGGER.debug("Current file batch: {}", paginatedFileBatch);
			Process process = null;
			try {
				String command = ANALYZER_COMMAND + " " + paginatedFileBatch.toString();
				process = new ProcessBuilder(ANALYZER_COMMAND, " " + paginatedFileBatch.toString()).start();
				String output = null;
				int exitCode = process.waitFor();

				LOGGER.debug("ExitCode: {}", exitCode);

				if (exitCode == 0 || exitCode == 1) {
					output = IOUtils.toString(process.getErrorStream(), Charset.defaultCharset());
					LOGGER.info("Error output: {}", output);
					throw new RuntimeException("Error while executing the command: ".concat(command));
				}

				output = IOUtils.toString(process.getInputStream(), Charset.defaultCharset());

				LOGGER.debug("Output: {}", output);

				issues.addAll(new DartAnalyzerReportParser().parse(output));
			} finally {
				if (process != null) {
					process.destroyForcibly();
				}
			}
		}
		LOGGER.info("Found issues: {}", issues.size());
		return issues;
	}

	private List<String> getPaginatedFilesPaths(List<String> filesWithAbsolutePath) {
		List<String> paginated = new ArrayList<String>();

		LOGGER.debug("Paging the files to execute the analyzer...");

		Integer pagingStart = 0;
		Integer pagingRemainder = getPaginationRemainder(filesWithAbsolutePath);
		Integer totalPages = getPaginationTotalPages(filesWithAbsolutePath, PAGE_SIZE, pagingRemainder);
		
		for (int i = 0; i < totalPages; i++) {
			LOGGER.debug("Current index: {}", i);
			
			LOGGER.debug("Current paging start: {}", pagingStart);

			Integer pagingEnd = getPagingEnd(pagingStart, pagingRemainder, totalPages, i);
			
			paginated.add(getFilesPathsSplitBySpace(filesWithAbsolutePath, pagingStart, pagingEnd));
			
			pagingStart += PAGE_SIZE;
		}
		return paginated;
	}

	private Integer getPagingEnd(Integer pagingStart, Integer pagingRemainder, Integer totalPages, int i) {
		Integer pagingEnd = pagingStart + PAGE_SIZE;
		if (isLastPage(pagingRemainder, totalPages, i)) {
			pagingEnd = pagingStart + pagingRemainder;
		}
		LOGGER.debug("Current paging end: {}", pagingEnd);
		return pagingEnd;
	}

	private String getFilesPathsSplitBySpace(List<String> filesWithAbsolutePath, Integer pagingStart, Integer pagingEnd) {
		return filesWithAbsolutePath.subList(pagingStart, pagingEnd).stream()
				.collect(Collectors.joining(" "));
	}

	private boolean isLastPage(Integer pagingRemainder, Integer totalPages, int i) {
		return pagingRemainder != 0 && i + 1 == totalPages;
	}

	private Integer getPaginationRemainder(List<String> filesWithAbsolutePath) {
		Integer remainder = filesWithAbsolutePath.size() % PAGE_SIZE;

		LOGGER.debug("Paging remainder: {}", remainder);

		return remainder;
	}

	private Integer getPaginationTotalPages(List<String> filesWithAbsolutePath, final Integer PAGE_SIZE,
			Integer remainder) {
		Integer total = filesWithAbsolutePath.size() / PAGE_SIZE;

		if (remainder != 0) {
			total++;
		}
		LOGGER.debug("Paging total items: {}", total);
		return total;
	}

	private List<String> getFilesWithAbsolutePath(SensorContext sensorContext) {
		List<String> filesWithAbsolutePath = new ArrayList<>();

		FileSystem fileSystem = getFileSystem(sensorContext);

		FilePredicate mainFilePredicate = getFilesFilter(fileSystem);

		String absolutePath = fileSystem.baseDir().getAbsolutePath();

		LOGGER.debug("Files absolut path: {}", absolutePath);

		fileSystem.inputFiles(mainFilePredicate).forEach(s -> {
			LOGGER.debug("Input file path: {}", s.toString());

			String fullPath = new StringBuilder(absolutePath).append("\\").append(s.toString().replace("/", "\\"))
					.toString();

			LOGGER.debug("Current file full path: {}", fullPath);

			filesWithAbsolutePath.add(fullPath);
		});

		return filesWithAbsolutePath;
	}

	private FilePredicate getFilesFilter(FileSystem fileSystem) {
		FilePredicate mainFilePredicate = fileSystem.predicates().and(
				fileSystem.predicates().hasType(InputFile.Type.MAIN), fileSystem.predicates().hasLanguage(Dart.KEY));
		return mainFilePredicate;
	}

	private FileSystem getFileSystem(SensorContext sensorContext) {
		FileSystem fileSystem = sensorContext.fileSystem();
		return fileSystem;
	}

	private void recordIssues(SensorContext sensorContext, List<DartAnalyzerReportIssue> issues) {
		// Record issues
		issues.forEach(i -> {
			File file = new File(sensorContext.fileSystem().baseDir(), i.getFilePath());
			LOGGER.debug("Inside issue forEach, file absolute path: {}", file.getAbsolutePath());

			FilePredicate fp = sensorContext.fileSystem().predicates().hasAbsolutePath(file.getAbsolutePath());
			if (!sensorContext.fileSystem().hasFiles(fp)) {
				LOGGER.warn("File not included in SonarQube {}", file.getAbsoluteFile());
			} else {
				InputFile inputFile = sensorContext.fileSystem().inputFile(fp);
				NewIssueLocation nil = new DefaultIssueLocation().on(inputFile)
						.at(inputFile.selectLine(i.getLineNumber())).message(i.getMessage());
				sensorContext.newIssue().forRule(RuleKey.of(DartAnalyzerRulesDefinition.REPOSITORY_KEY, i.getRuleId()))
						.at(nil).save();
			}
		});
	}

	private void verifyDartAnalyzerExists() {
		LOGGER.debug("Verify dart analyser...");
		new ProcBuilder(ANALYZER_COMMAND).withArg("-h").run();
		LOGGER.debug("Verify dart analyser done");
	}

	private void saveCurrentAnalysisOptionsFile(SensorContext sensorContext) {
		File analysisOptionsFile = new File(sensorContext.fileSystem().baseDir(), ANALYSIS_OPTIONS_FILENAME);
		if (analysisOptionsFile.exists()) {
			analysisOptionsFile
					.renameTo(new File(sensorContext.fileSystem().baseDir(), ANALYSIS_OPTIONS_FILENAME + ".sonar"));
			LOGGER.info("Backup of original analysis_options.yaml file to {}", ANALYSIS_OPTIONS_FILENAME + ".sonar");
		}
	}

	private void createAnalysisOptionsFile(SensorContext sensorContext) throws IOException {
		File analysisOptionsFile = new File(sensorContext.fileSystem().baseDir(), ANALYSIS_OPTIONS_FILENAME);
		URL inputUrl = getClass().getResource(ANALYSIS_OPTIONS_FILE);
		Resources.asByteSource(inputUrl).copyTo(Files.asByteSink(analysisOptionsFile));
	}

	private void restoreCurrentAnalysisOptionsFile(SensorContext sensorContext) {
		File analysisOptionsFile = new File(sensorContext.fileSystem().baseDir(), ANALYSIS_OPTIONS_FILENAME);
		File currentAnalysisOptionsFile = new File(sensorContext.fileSystem().baseDir(),
				ANALYSIS_OPTIONS_FILENAME + ".sonar");
		if (currentAnalysisOptionsFile.exists()) {
			currentAnalysisOptionsFile.renameTo(analysisOptionsFile);
			LOGGER.info("Restored original analysis_options.yaml file");
		} else {
			// If no current analysis options file : remove existing one
			analysisOptionsFile.delete();
		}
	}
}
