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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.squidbridge.api.AnalysisException;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class PubSpecParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(PubSpecParser.class);

    private PubSpecParser() {
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public static PubSpec parse(SensorContext sensorContext) {
        final File pubSpec = sensorContext.fileSystem().resolvePath(PubSpec.NAME);
        final Path pubSpecPath = pubSpec.toPath();

        LOGGER.debug("Attempting to parse {}", pubSpecPath);

        if (!Files.exists(pubSpecPath)) {
            throw new AnalysisException(pubSpecPath + " does not exist");
        }
        if (!Files.isReadable(pubSpecPath)) {
            throw new AnalysisException(pubSpecPath + " can not be read");
        }
        final String pubSpecContent;
        try {
            pubSpecContent = new String(Files.readAllBytes(pubSpecPath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new AnalysisException(pubSpecPath + " can not be read", e);
        }

        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(pubSpecContent);

        final String projectVersion = ((String) data.get("version"));
        final boolean flutter = ((Map<String, Object>) data.get("environment")).containsKey("flutter");
        LOGGER.info("Detected {} project with version {}", flutter ? "Flutter" : "Dart", projectVersion);

        return new PubSpec(flutter, projectVersion);
    }
}
