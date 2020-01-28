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

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.ParseTree;
import org.sonar.api.batch.sensor.SensorContext;

public class CustomTreeVisitor extends AbstractParseTreeVisitor implements ParseTreeItemVisitor {

    private final ParseTreeItemVisitor[] visitors;

    public CustomTreeVisitor(final ParseTreeItemVisitor... visitors) {
        this.visitors = visitors;
    }

    @Override
    public Object visit(final ParseTree tree) {

        final int n = tree.getChildCount();

        for (int i = 0; i < n; i++) {
            final ParseTree c = tree.getChild(i);
            visit(c);
        }
        for (final ParseTreeItemVisitor visitor : this.visitors) {
            visitor.apply(tree);
        }

        return null;
    }

    @Override
    public void fillContext(SensorContext context, AntlrContext antlrContext) {
        this.apply(antlrContext.getRoot());
        for (final ParseTreeItemVisitor visitor : this.visitors) {
            visitor.fillContext(context, antlrContext);
        }
    }

    @Override
    public void apply(ParseTree tree) {
        this.visit(tree);
    }
}
