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

import java.util.LinkedHashMap;
import java.util.Map;

class ReversePathTree {
    private Node root = new Node();

    void index(InputFile inputFile, String[] path) {
        Node currentNode = root;
        for (int i = path.length - 1; i >= 0; i--) {
            currentNode = currentNode.children.computeIfAbsent(path[i], e -> new Node());
        }
        currentNode.file = inputFile;
    }

    InputFile getFileWithSuffix(String[] path) {
        Node currentNode = root;

        for (int i = path.length - 1; i >= 0; i--) {
            currentNode = currentNode.children.get(path[i]);
            if (currentNode == null) {
                return null;
            }
        }
        return getFirstLeaf(currentNode);
    }

    private static InputFile getFirstLeaf(Node node) {
        while (!node.children.isEmpty()) {
            node = node.children.values().iterator().next();
        }
        return node.file;
    }

    static class Node {
        final Map<String, Node> children = new LinkedHashMap<>();
        InputFile file = null;
    }
}
