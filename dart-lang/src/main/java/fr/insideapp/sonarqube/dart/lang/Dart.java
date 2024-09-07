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
package fr.insideapp.sonarqube.dart.lang;

import org.apache.commons.lang3.StringUtils;
import org.sonar.api.config.Configuration;
import org.sonar.api.resources.AbstractLanguage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Dart extends AbstractLanguage {
    public static final String KEY = "dart";
    private final Configuration config;

    public static final List<String> FILE_SUFFIXES = List.of(".dart");

    public static final String FILE_SUFFIXES_KEY = "sonar.dart.file.suffixes";

    public Dart(Configuration config) {
        super(KEY, "Dart");
        this.config = config;
    }

    public String[] getFileSuffixes() {
        final List<String> providedFilesSuffixes = Arrays.stream(config.getStringArray(FILE_SUFFIXES_KEY))
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList());
        final List<String> filesSuffixes = providedFilesSuffixes.isEmpty() ? FILE_SUFFIXES : providedFilesSuffixes;
        return filesSuffixes.toArray(new String[0]);
    }
}
