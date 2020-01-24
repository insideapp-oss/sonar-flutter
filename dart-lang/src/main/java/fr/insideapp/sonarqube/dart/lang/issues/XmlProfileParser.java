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
package fr.insideapp.sonarqube.dart.lang.issues;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition.NewBuiltInActiveRule;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition.NewBuiltInQualityProfile;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static java.nio.charset.StandardCharsets.UTF_8;

public final class XmlProfileParser {

    public XmlProfileParser() {
    }

    public static void parse(String path, NewBuiltInQualityProfile profile) {
        try {
            String content = toString(path);
            XmlFile xmlFile = XmlFile.create(content);

            NodeList ruleNodeList = xmlFile.getDocument().getElementsByTagName("rule");
            XmlFile.asList(ruleNodeList).forEach(ruleNode -> {
                Element ruleElement = (Element) ruleNode;
                String repositoryKey = getChildContent(ruleElement, "repositoryKey");
                String key = getChildContent(ruleElement, "key");

                NewBuiltInActiveRule newActiveRule = profile.activateRule(repositoryKey, key);

                NodeList parameterNodeList = ruleElement.getElementsByTagName("parameter");
                XmlFile.asList(parameterNodeList).forEach(parameter -> {
                    Element parameterElement = (Element) parameter;
                    String paramKey = getChildContent(parameterElement, "key");
                    String paramValue = getChildContent(parameterElement, "value");
                    newActiveRule.overrideParam(paramKey, paramValue);
                });

                Optional<String> priority = getOptionalChildContent(ruleElement, "priority");
                priority.ifPresent(newActiveRule::overrideSeverity);
            });

        } catch (IOException e) {
            throw new IllegalStateException("Failed to create profile", e);
        }
    }

    private static String getChildContent(Element element, String tagName) {
        Optional<String> optionalChildContent = getOptionalChildContent(element, tagName);
        return optionalChildContent.
                orElseThrow(() -> new IllegalStateException("Failed to get content of " + tagName));
    }

    private static Optional<String> getOptionalChildContent(Element element, String tagName) {
        NodeList elementsByTagName = element.getElementsByTagName(tagName);
        if (elementsByTagName.getLength() > 0) {
            Node child = elementsByTagName.item(0);
            return Optional.of(child.getTextContent());
        }
        return Optional.empty();
    }

    private static String toString(String path) throws IOException {
        try (InputStream input = XmlProfileParser.class.getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                throw new IOException("Resource not found in the classpath: " + path);
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[4_096];
            for (int read = input.read(buffer); read != -1; read = input.read(buffer)) {
                out.write(buffer, 0, read);
            }
            return new String(out.toByteArray(), UTF_8);
        }
    }
}