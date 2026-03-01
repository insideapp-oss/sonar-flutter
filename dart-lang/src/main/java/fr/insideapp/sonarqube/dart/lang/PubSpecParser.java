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

import org.sonar.api.batch.sensor.SensorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static boolean containsAnyKey(Map<String, Object> map, String... keys) {
        if (map == null) return false;
        for (String key : keys) {
            if (map.containsKey(key)) return true;
        }
        return false;
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public static PubSpec parse(SensorContext sensorContext) throws IOException {
        final File pubSpec = sensorContext.fileSystem().resolvePath(PubSpec.NAME);
        final Path pubSpecPath = pubSpec.toPath();

        LOGGER.debug("Attempting to parse {}", pubSpecPath);

        if (!Files.exists(pubSpecPath)) {
            throw new IllegalArgumentException(pubSpecPath + " does not exist");
        }
        if (!Files.isReadable(pubSpecPath)) {
            throw new IllegalArgumentException(pubSpecPath + " can not be read");
        }
        final String pubSpecContent = new String(Files.readAllBytes(pubSpecPath), StandardCharsets.UTF_8);

        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(pubSpecContent);

        final String projectVersion = ((String) data.get("version"));
        final Map<String, Object> dependencies = ((Map<String, Object>) data.get("dependencies"));
        final Map<String, Object> devDependencies = ((Map<String, Object>) data.get("dev_dependencies"));
        final boolean flutter = dependencies != null && dependencies.containsKey("flutter");

        // Detect state management libraries
        final boolean usesBloc = containsAnyKey(dependencies, "flutter_bloc", "bloc") ||
                containsAnyKey(devDependencies, "bloc_lint");
        final boolean usesRiverpod = containsAnyKey(dependencies, "flutter_riverpod", "hooks_riverpod", "riverpod") ||
                containsAnyKey(devDependencies, "riverpod_lint");
        final boolean usesProvider = !usesRiverpod && containsAnyKey(dependencies, "provider");

        LOGGER.info("Detected {} project with version {}", flutter ? "Flutter" : "Dart", projectVersion);
        if (usesBloc) LOGGER.info("Detected BLoC state management");
        if (usesRiverpod) LOGGER.info("Detected Riverpod state management");
        if (usesProvider) LOGGER.info("Detected Provider state management");

        return new PubSpec(flutter, projectVersion, usesBloc, usesRiverpod, usesProvider);
    }
}
