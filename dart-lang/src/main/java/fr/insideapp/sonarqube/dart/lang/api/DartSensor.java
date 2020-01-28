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
package fr.insideapp.sonarqube.dart.lang.api;

import fr.insideapp.sonarqube.dart.lang.Dart;
import fr.insideapp.sonarqube.dart.lang.antlr.AntlrContext;
import fr.insideapp.sonarqube.dart.lang.antlr.AntlrHighlighter;
import fr.insideapp.sonarqube.dart.lang.antlr.CustomTreeVisitor;
import fr.insideapp.sonarqube.dart.lang.antlr.ParseTreeItemVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.batch.sensor.issue.internal.DefaultIssueLocation;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.scan.filesystem.PathResolver;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.api.CheckMessage;
import org.sonar.squidbridge.api.SourceCode;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.SquidCheck;
import org.sonar.squidbridge.indexer.QueryByType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class DartSensor implements Sensor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DartSensor.class);
    private final Checks<SquidCheck<DartGrammar>> checks;
    private final SensorContext sensorContext;
    private final PathResolver pathResolver;

    public DartSensor(CheckFactory checkFactory, SensorContext sensorContext, PathResolver pathResolver) {
        this.sensorContext = sensorContext;
        this.pathResolver = pathResolver;
        this.checks = checkFactory.<SquidCheck<DartGrammar>>create(CheckList.REPOSITORY_KEY).addAnnotatedChecks((Iterable<Class>)CheckList.getChecks());
    }

    @Override
    public void describe(SensorDescriptor sensorDescriptor) {
        sensorDescriptor
                .onlyOnLanguage(Dart.KEY)
                .name("Dart sensor")
                .onlyOnFileType(InputFile.Type.MAIN);
    }

    @Override
    public void execute(SensorContext sensorContext) {

        FilePredicate hasDart = sensorContext.fileSystem().predicates().hasLanguage(Dart.KEY);
        FilePredicate isMain = sensorContext.fileSystem().predicates().hasType(InputFile.Type.MAIN);
        FilePredicate and = sensorContext.fileSystem().predicates().and(hasDart, isMain);
        List<File> files = new ArrayList<>();
        final Charset charset = sensorContext.fileSystem().encoding();
        for(InputFile inf : sensorContext.fileSystem().inputFiles(and)){
            files.add(new File(sensorContext.fileSystem().baseDir(), inf.toString()));

            // Visit source file to collect data
            try {
                final AntlrContext antlrContext = AntlrContext.fromInputFile(inf, charset);
                ParseTreeItemVisitor visitor = new CustomTreeVisitor(new AntlrHighlighter());
                visitor.fillContext(sensorContext, antlrContext);
            } catch (IOException e) {
                LOGGER.warn("Unexpected error while analyzing file " + inf.filename(), e);
            }

        }

        List<SquidAstVisitor<DartGrammar>> visitors = new ArrayList<>(checks.all());
        AstScanner<DartGrammar> scanner = DartAstScanner.create(createConfiguration(), visitors.toArray(new SquidAstVisitor[visitors.size()]));
        scanner.scanFiles(files);

        Collection<SourceCode> squidSourceFiles = scanner.getIndex().search(new QueryByType(SourceFile.class));
        save(squidSourceFiles);


    }

    private DartConfiguration createConfiguration() {
        return new DartConfiguration(sensorContext.fileSystem().encoding());
    }

    private void save(Collection<SourceCode> squidSourceFiles) {
        for (SourceCode squidSourceFile : squidSourceFiles) {
            SourceFile squidFile = (SourceFile) squidSourceFile;
            String relativePath = pathResolver.relativePath(sensorContext.fileSystem().baseDir(), new File(squidFile.getKey()));
            InputFile inputFile = sensorContext.fileSystem().inputFile(sensorContext.fileSystem().predicates().hasRelativePath(relativePath));
            saveMeasures(inputFile, squidFile);
            saveIssues(inputFile, squidFile);
        }
    }

    private void saveMeasures(InputFile inputFile, SourceFile squidFile) {
        MeasureUtil.saveMeasure(sensorContext, inputFile, CoreMetrics.NCLOC, squidFile.getInt(DartMetric.LINES_OF_CODE));
        MeasureUtil.saveMeasure(sensorContext, inputFile, CoreMetrics.STATEMENTS, squidFile.getInt(DartMetric.STATEMENTS));
        MeasureUtil.saveMeasure(sensorContext, inputFile, CoreMetrics.COMMENT_LINES, squidFile.getInt(DartMetric.COMMENT_LINES));
    }

    private void saveIssues(InputFile inputFile, SourceFile squidFile) {
        Collection<CheckMessage> messages = squidFile.getCheckMessages();

        if (inputFile != null) {
            for (CheckMessage message : messages) {
                RuleKey ruleKey = checks.ruleKey((SquidCheck<DartGrammar>) message.getCheck());
                NewIssue issue = sensorContext.newIssue()
                        .forRule(ruleKey);
                NewIssueLocation dil = new DefaultIssueLocation()
                        .on(inputFile)
                        .at(inputFile.selectLine(message.getLine()))
                        .message(message.getText(Locale.ENGLISH));
                issue.at(dil);
                if (message.getCost() != null) {
                    issue.gap(message.getCost());
                }
                issue.save();
            }
        }
    }
}
