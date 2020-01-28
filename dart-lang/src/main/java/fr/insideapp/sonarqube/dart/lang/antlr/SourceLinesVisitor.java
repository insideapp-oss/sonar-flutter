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
package fr.insideapp.sonarqube.dart.lang.antlr;

import fr.insideapp.sonarqube.dart.lang.SourceLine;
import fr.insideapp.sonarqube.dart.lang.SourceLinesProvider;
import fr.insideapp.sonarqube.dart.lang.antlr.generated.Dart2Parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.measures.CoreMetrics;

import java.io.InputStream;

public class SourceLinesVisitor implements ParseTreeItemVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceLinesVisitor.class);
    private final SourceLinesProvider linesProvider = new SourceLinesProvider();

    private static int[] getLineAndColumn(final SourceLine[] lines, final int global) {

        for (final SourceLine line : lines) {
            if (line.getEnd() > global) {
                return new int[] { line.getLine(), global - line.getStart() };
            }
        }
        return new int[0];
    }

    @Override
    public void apply(ParseTree tree) {

    }

    @Override
    public void fillContext(SensorContext context, AntlrContext antlrContext) {
        try (InputStream stream = antlrContext.getFile().inputStream()) {
            final SourceLine[] lines = antlrContext.getLines();
            final Token[] alltokens = antlrContext.getTokens();
            final int[] total = new int[lines.length];
            final InputFile file = antlrContext.getFile();

            for (final Token token : alltokens) {
                int startLine = token.getLine();
                int[] endLines = getLineAndColumn(lines, token.getStopIndex());
                if (endLines == null || endLines.length == 0 || token.getStartIndex() >= token.getStopIndex()) {
                    continue;
                }
                if (token.getType() == Dart2Parser.EOF || token.getType() == Dart2Parser.SINGLE_LINE_COMMENT
                        || token.getType() == Dart2Parser.MULTI_LINE_COMMENT) {
                    continue;
                }
                for (int i = startLine - 1; i < endLines[0]; i++) {
                    total[i] = 1; // Line of code
                }

            }

            for (final Token token : alltokens) {
                int startLine = token.getLine();
                int[] endLines = getLineAndColumn(lines, token.getStopIndex());
                if (token.getType() == Dart2Parser.EOF || endLines == null || endLines.length == 0
                        || token.getStartIndex() >= token.getStopIndex()) {
                    continue;
                }
                if (token.getType() == Dart2Parser.SINGLE_LINE_COMMENT || token.getType() == Dart2Parser.MULTI_LINE_COMMENT) {
                    for (int i = startLine - 1; i < endLines[0]; i++) {
                        if (total[i] == 0) {
                            total[i] = 2; // Comment
                        }

                    }
                }

            }
            int comments = 0;
            int locs = 0;
            for (final int lineType : total) {
                if (lineType == 1) {
                    locs++;
                    continue;
                }
                if (lineType == 2) {
                    comments++;
                }
            }

            synchronized (context) {

                try {
                    context.<Integer>newMeasure().on(file).forMetric(CoreMetrics.NCLOC).withValue(locs).save();
                } catch (final Throwable e) {
                    LOGGER.warn(String.format("Unexpected adding nloc measures on file %s", file.absolutePath()), e);
                }

                try {
                    context.<Integer>newMeasure().on(file).forMetric(CoreMetrics.COMMENT_LINES)
                            .withValue(comments).save();
                } catch (final Throwable e) {
                    LOGGER.warn(String.format("Unexpected error while adding comment_lines measures on file %s",
                            file.absolutePath()), e);
                }
            }

        } catch (Throwable e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
