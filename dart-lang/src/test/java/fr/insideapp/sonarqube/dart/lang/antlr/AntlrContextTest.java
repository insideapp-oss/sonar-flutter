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

import com.google.common.io.Files;
import fr.insideapp.sonarqube.dart.lang.Dart;
import org.antlr.v4.runtime.Token;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.sonar.api.batch.fs.internal.DefaultInputFile;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;

public class AntlrContextTest {

    @Test
    public void linesDetection() throws Throwable {
        String s = "var test = 'test';";
        AntlrContext result = AntlrUtils.getRequest(s);
        for (Token t : result.getStream().getTokens()) {
            if (t.getType() == Token.EOF) {
                continue;
            }
            int[] start = result.getLineAndColumn(t.getStartIndex());
            int[] end = result.getLineAndColumn(t.getStopIndex());
            assertThat(start).isNotNull();
            assertThat(end).isNotNull();
            assertThat(t.getLine()).isEqualTo(start[0]);
            assertThat(t.getCharPositionInLine()).isEqualTo(start[1]);
        }
    }

}