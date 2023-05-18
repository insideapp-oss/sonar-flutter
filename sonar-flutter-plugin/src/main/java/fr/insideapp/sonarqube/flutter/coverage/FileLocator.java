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
package fr.insideapp.sonarqube.flutter.coverage;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.utils.PathUtils;

import javax.annotation.CheckForNull;

class FileLocator {
    private final ReversePathTree tree = new ReversePathTree();

    FileLocator(Iterable<InputFile> inputFiles) {
        inputFiles.forEach(inputFile -> {
            String[] path = inputFile.key().split("/");
            tree.index(inputFile, path);
        });
    }

    @CheckForNull
    InputFile getInputFile(String filePath) {
        String sanitizedPath = PathUtils.sanitize(filePath);
        if (sanitizedPath == null) {
            return null;
        }
        String[] pathElements = sanitizedPath.split("/");
        return tree.getFileWithSuffix(pathElements);
    }
}
