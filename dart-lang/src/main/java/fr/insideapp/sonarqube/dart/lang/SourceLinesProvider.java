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
package fr.insideapp.sonarqube.dart.lang;

import org.apache.commons.io.input.BOMInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SourceLinesProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(SourceLinesProvider.class);

    public SourceLine[] getLines(final InputStream inputStream, final Charset charset) {
        if (inputStream == null) {
            return new SourceLine[0];
        }
        final List<SourceLine> sourceLines = new ArrayList<>();

        try (final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new BOMInputStream(inputStream, false), charset))) {
            int totalLines = 1;
            int global = 0;
            int count = 0;

            int currentChar;
            while ((currentChar = bufferedReader.read()) != -1) {

                global++;
                count++;
                if (currentChar == 10) {
                    sourceLines.add(new SourceLine(totalLines, count, global - count, global));
                    totalLines++;
                    count = 0;
                }

            }
            sourceLines.add(new SourceLine(totalLines, count, global - count, global));
        } catch (final Throwable e) {
            LOGGER.warn("Error occured reading file", e);
        }

        return sourceLines.toArray(new SourceLine[0]);
    }

}
