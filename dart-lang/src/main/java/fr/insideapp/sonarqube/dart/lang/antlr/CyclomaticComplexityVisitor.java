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
package fr.insideapp.sonarqube.dart.lang.antlr;

import static java.lang.String.format;

import fr.insideapp.sonarqube.dart.lang.antlr.generated.Dart2Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class CyclomaticComplexityVisitor implements ParseTreeItemVisitor {

    private static final Logger LOGGER = Loggers.get(CyclomaticComplexityVisitor.class);

    private int complexity = 0;

    @Override
    public void apply(ParseTree tree) {

        final Class<? extends ParseTree> classz = tree.getClass();

        if (Dart2Parser.IfStatementContext.class.equals(classz) ||
                Dart2Parser.ForStatementContext.class.equals(classz) ||
                Dart2Parser.WhileStatementContext.class.equals(classz) ||
                Dart2Parser.SwitchCaseContext.class.equals(classz) ||
                Dart2Parser.DefaultCaseContext.class.equals(classz) ||
                Dart2Parser.DoStatementContext.class.equals(classz) ||
                Dart2Parser.FunctionBodyContext.class.equals(classz)
        ) {
            complexity++;
        }

        if (Dart2Parser.ConditionalExpressionContext.class.equals(classz)) {
            Dart2Parser.ConditionalExpressionContext conditionalExpressionContext = (Dart2Parser.ConditionalExpressionContext) tree;

            int countOr = ( tree.getText().split("\\|\\|", -1).length ) - 1;
            int countAnd = ( tree.getText().split("&&", -1).length ) - 1;
            complexity = complexity + countOr + countAnd;
        }


    }

    @Override
    public void fillContext(SensorContext context, AntlrContext antlrContext) {
        final InputFile file = antlrContext.getFile();
        synchronized (context) {
            try {
                context.<Integer>newMeasure().on(file).forMetric(CoreMetrics.COMPLEXITY).withValue(complexity)
                        .save();
            } catch (final Throwable e) {
                LOGGER.warn(format("Unexpected adding complexity measures on file %s", file.key()), e);
            }
        }
    }

    public int getComplexity() {
        return complexity;
    }
}
