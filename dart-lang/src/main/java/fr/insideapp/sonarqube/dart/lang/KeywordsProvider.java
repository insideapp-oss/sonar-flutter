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

import fr.insideapp.sonarqube.dart.lang.antlr.AntlrHighlighter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class KeywordsProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeywordsProvider.class);
    private final List<String> keywords = new ArrayList<String>();

    public KeywordsProvider() {
        this("/dart.keywords");
    }

    public KeywordsProvider(final String... files) {
        for (final String file : files) {
            init(file);
        }
    }

    private void init(final String file) {
        try {
            final InputStream stream = this.getClass().getResourceAsStream(file);

            final BufferedReader br = new BufferedReader(new InputStreamReader(stream));

            for (String line; (line = br.readLine()) != null;) {
                if (!StringUtils.isEmpty(line)) {
                    final String keyword = line.trim();
                    if (!this.keywords.contains(keyword)) {
                        this.keywords.add(keyword);
                    }

                }
            }
        } catch (final Throwable e) {
            LOGGER.warn(String.format("Error reading keywords file: %s", file), e);
        }

    }

    public boolean isKeyword(final String name) {
        return this.keywords.contains(name);
    }
}
