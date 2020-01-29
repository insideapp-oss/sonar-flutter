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

import fr.insideapp.sonarqube.dart.lang.antlr.generated.Dart2Lexer;
import fr.insideapp.sonarqube.dart.lang.antlr.generated.Dart2Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

public class CyclomaticComplexityVisitorTest {

    @Test
    public void complexity() throws IOException {
        final CharStream charStream = CharStreams.fromStream(this.getClass().getResourceAsStream("/dart/complexity.dart"));
        final Dart2Lexer lexer = new Dart2Lexer(charStream);
        lexer.removeErrorListeners();
        final CommonTokenStream stream = new CommonTokenStream(lexer);
        stream.fill();
        final Dart2Parser parser = new Dart2Parser(stream);
        parser.removeErrorListeners();
        final ParseTree root = parser.compilationUnit();

        CyclomaticComplexityVisitor visitor = new CyclomaticComplexityVisitor();
        CustomTreeVisitor customTreeVisitor = new CustomTreeVisitor(visitor);
        customTreeVisitor.visit(root);
        assertThat(visitor.getComplexity()).isEqualTo(5);
    }

}