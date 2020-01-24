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

import com.sonar.sslr.impl.Parser;
import org.sonar.squidbridge.AstScanner;
import org.sonar.squidbridge.CommentAnalyser;
import org.sonar.squidbridge.SquidAstVisitor;
import org.sonar.squidbridge.SquidAstVisitorContextImpl;
import org.sonar.squidbridge.api.SourceCode;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.api.SourceProject;
import org.sonar.squidbridge.indexer.QueryByType;
import org.sonar.squidbridge.metrics.CommentsVisitor;
import org.sonar.squidbridge.metrics.LinesOfCodeVisitor;
import org.sonar.squidbridge.metrics.LinesVisitor;

import java.io.File;
import java.util.Collection;

public class DartAstScanner {

    /**
     * Helper method for testing checks without having to deploy them on a Sonar instance.
     */
    public static SourceFile scanSingleFile(File file, SquidAstVisitor<DartGrammar>... visitors) {
        if (!file.isFile()) {
            throw new IllegalArgumentException("File '" + file + "' not found.");
        }
        AstScanner<DartGrammar> scanner = create(new DartConfiguration(), visitors);
        scanner.scanFile(file);
        Collection<SourceCode> sources = scanner.getIndex().search(new QueryByType(SourceFile.class));

        if (sources.size() != 1) {
            throw new IllegalStateException("Only one source file was expected whereas " + sources.size() + " has been returned.");
        }
        return (SourceFile) sources.iterator().next();
    }

    public static AstScanner<DartGrammar> create(DartConfiguration conf, SquidAstVisitor<DartGrammar>... visitors) {
        final SquidAstVisitorContextImpl<DartGrammar> context = new SquidAstVisitorContextImpl<DartGrammar>(new SourceProject("Objective-C Project"));
        final Parser<DartGrammar> parser = DartParser.create(conf);

        AstScanner.Builder<DartGrammar> builder = AstScanner.builder(context).setBaseParser(parser);

        /* Metrics */
        builder.withMetrics(DartMetric.values());

        /* Comments */
        builder.setCommentAnalyser(
                new CommentAnalyser() {
                    @Override
                    public boolean isBlank(String line) {
                        for (int i = 0; i < line.length(); i++) {
                            if (Character.isLetterOrDigit(line.charAt(i))) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override
                    public String getContents(String comment) {
                        return comment.startsWith("//") ? comment.substring(2) : comment.substring(2, comment.length() - 2);
                    }
                });

        /* Files */
        builder.setFilesMetric(DartMetric.FILES);
        if(visitors != null && visitors.length > 0) {
            for (SquidAstVisitor<DartGrammar> sv : visitors) {
                builder.withSquidAstVisitor(sv);
            }
        } else {
            /* Metrics */
            builder.withSquidAstVisitor(new LinesVisitor(DartMetric.LINES));
            builder.withSquidAstVisitor(new LinesOfCodeVisitor(DartMetric.LINES_OF_CODE));
            builder.withSquidAstVisitor(CommentsVisitor.<DartGrammar>builder().withCommentMetric(DartMetric.COMMENT_LINES)
                    .withNoSonar(true)
                    .withIgnoreHeaderComment(conf.getIgnoreHeaderComments())
                    .build());
        }
        return builder.build();
    }
}
