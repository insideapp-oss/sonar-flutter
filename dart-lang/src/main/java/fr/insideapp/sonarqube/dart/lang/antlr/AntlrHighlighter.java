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

import fr.insideapp.sonarqube.dart.lang.KeywordsProvider;
import fr.insideapp.sonarqube.dart.lang.antlr.generated.Dart2Parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.TextRange;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.cpd.NewCpdTokens;
import org.sonar.api.batch.sensor.highlighting.NewHighlighting;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;

import static java.lang.String.format;

public class AntlrHighlighter implements ParseTreeItemVisitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AntlrHighlighter.class);
    private final KeywordsProvider keywordsProvider = new KeywordsProvider();

    @Override
    public void fillContext(final SensorContext context, final AntlrContext antrlFile) {

        final InputFile file = antrlFile.getFile();
        if (file == null) {
            return;
        }
        final NewCpdTokens cpdTokens = context.newCpdTokens().onFile(file);

        final NewHighlighting newHighlightning = context.newHighlighting().onFile(file);
        final Token[] alltokens = antrlFile.getTokens();
        for (final Token token : alltokens) {
            final int startLine = token.getLine();
            final int startLineOffset = token.getCharPositionInLine();
            final int[] endDetails = antrlFile.getLineAndColumn(token.getStopIndex());

            if (endDetails == null || endDetails.length != 2 || token.getType() == Dart2Parser.EOF
                    || token.getStartIndex() >= token.getStopIndex()) {
                continue;
            }

            final int endLine = endDetails[0];
            final int endLineOffset = endDetails[1] + 1;

            try {
                final TextRange range = file.newRange(startLine, startLineOffset, endLine, endLineOffset);

                addHighlighting(newHighlightning, token, file, range);

                addCpdToken(cpdTokens, file, token, range);

            } catch (final Throwable e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(format(
                            "Unexpected error creating text range on file %s for token %s on (%s, %s) -  (%s, %s)",
                            file.absolutePath(), token.getText(), startLine, startLineOffset, endLine, endLineOffset),
                            e);
                }

            }
        }
        synchronized (context) {

            try {
                newHighlightning.save();
            } catch (final Throwable e) {
                LOGGER.warn(format("Unexpected error saving highlightings on file %s", file.absolutePath()), e);
            }
            try {
                cpdTokens.save();
            } catch (final Throwable e) {
                LOGGER.warn(format("Unexpected error saving cpd tokens on file %s", file.absolutePath()), e);
            }
        }
    }

    private static void addCpdToken(final NewCpdTokens cpdTokens, InputFile file, final Token token,
                                    final TextRange range) {
        try {
            cpdTokens.addToken(range, token.getText());
        } catch (Throwable e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(format("Unexpected error adding cpd tokens on file %s", file.absolutePath()), e);
            }

        }
    }

    private void addHighlighting(final NewHighlighting newHighlightning, final Token token, final InputFile file,
                                 final TextRange range) {

        // Tokens on a single char are ignored
        if (range.start().lineOffset() == range.end().lineOffset()) {
            return;
        }

        try {

            // Comment
            if (token.getType() == Dart2Parser.MULTI_LINE_COMMENT || token.getType() == Dart2Parser.SINGLE_LINE_COMMENT) {
                newHighlightning.highlight(range, TypeOfText.COMMENT);
                return;
            }

            // String
            if (token.getType() == Dart2Parser.SingleLineString || token.getType() == Dart2Parser.MultiLineString) {
                newHighlightning.highlight(range, TypeOfText.STRING);
                return;
            }

            // Keyword
            if (this.keywordsProvider.isKeyword(token.getText())) {
                newHighlightning.highlight(range, TypeOfText.KEYWORD);
                return;
            }


        } catch (final Throwable e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(format("Unexpected error adding highlighting on file %s", file.absolutePath()), e);
            }
        }
    }

    @Override
    public void apply(ParseTree tree) {

    }
}
