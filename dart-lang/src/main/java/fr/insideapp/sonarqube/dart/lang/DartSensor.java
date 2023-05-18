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
package fr.insideapp.sonarqube.dart.lang;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;

import fr.insideapp.sonarqube.dart.lang.antlr.AntlrContext;
import fr.insideapp.sonarqube.dart.lang.antlr.CustomTreeVisitor;
import fr.insideapp.sonarqube.dart.lang.antlr.CyclomaticComplexityVisitor;
import fr.insideapp.sonarqube.dart.lang.antlr.HighlighterVisitor;
import fr.insideapp.sonarqube.dart.lang.antlr.ParseTreeItemVisitor;
import fr.insideapp.sonarqube.dart.lang.antlr.SourceLinesVisitor;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

import javax.annotation.ParametersAreNonnullByDefault;

public class DartSensor implements Sensor {

    private static final Logger LOGGER = Loggers.get(DartSensor.class);
    private static final int EXECUTOR_TIMEOUT = 10000;

    @Override
    public void describe(SensorDescriptor sensorDescriptor) {
        sensorDescriptor
                .onlyOnLanguage(Dart.KEY)
                .name("Dart sensor")
                .onlyOnFileType(InputFile.Type.MAIN);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void execute(SensorContext sensorContext) {
        FilePredicate hasDart = sensorContext.fileSystem().predicates().hasLanguage(Dart.KEY);
        FilePredicate isMain = sensorContext.fileSystem().predicates().hasType(InputFile.Type.MAIN);
        FilePredicate isTest = sensorContext.fileSystem().predicates().hasType(InputFile.Type.TEST);
        FilePredicate dartAndMain = sensorContext.fileSystem().predicates().and(hasDart, isMain);
        FilePredicate dartAndTest = sensorContext.fileSystem().predicates().and(hasDart, isTest);
        final Charset charset = sensorContext.fileSystem().encoding();

        final ExecutorService executorService = Executors.newWorkStealingPool();

        for(InputFile inf : sensorContext.fileSystem().inputFiles(dartAndMain)){

            executorService.execute(() -> {
                // Visit source files
                try {
                    final AntlrContext antlrContext = AntlrContext.fromInputFile(inf, charset);
                    ParseTreeItemVisitor visitor = new CustomTreeVisitor(new HighlighterVisitor(),
                            new SourceLinesVisitor(), new CyclomaticComplexityVisitor());
                    visitor.fillContext(sensorContext, antlrContext);
                } catch (IOException e) {
                    LOGGER.warn("Unexpected error while analyzing file " + inf.filename(), e);
                }
            });

        }

        for(InputFile inf : sensorContext.fileSystem().inputFiles(dartAndTest)){

            executorService.execute(() -> {
                // Visit test files (for syntax highlighting only)
                try {
                    final AntlrContext antlrContext = AntlrContext.fromInputFile(inf, charset);
                    ParseTreeItemVisitor visitor = new CustomTreeVisitor(new HighlighterVisitor());
                    visitor.fillContext(sensorContext, antlrContext);
                } catch (IOException e) {
                    LOGGER.warn("Unexpected error while analyzing file " + inf.filename(), e);
                }
            });

        }

        try {
            executorService.shutdown();
            executorService.awaitTermination(EXECUTOR_TIMEOUT, TimeUnit.SECONDS);
            executorService.shutdownNow();
        } catch (final InterruptedException e) {
            LOGGER.warn("Unexpected error while running waiting for executor service to finish", e);
            Thread.currentThread().interrupt();
        }
    }
}
