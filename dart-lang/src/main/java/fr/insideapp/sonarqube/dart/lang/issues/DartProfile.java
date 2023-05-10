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

import fr.insideapp.sonarqube.dart.lang.Dart;
import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.DartAnalyzerRulesDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;

import java.io.IOException;
import java.util.List;

public class DartProfile implements BuiltInQualityProfilesDefinition {

    private static final Logger LOGGER = LoggerFactory.getLogger(DartProfile.class);

    public DartProfile() {

    }

    @Override
    public void define(BuiltInQualityProfilesDefinition.Context context) {

        NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(DartAnalyzerRulesDefinition.REPOSITORY_KEY, Dart.KEY);
        RepositoryRuleParser repositoryRuleParser = new RepositoryRuleParser();

        // dartanalyzer rules
        try {
            List<RepositoryRule> rules = repositoryRuleParser.parse(DartAnalyzerRulesDefinition.RULES_FILE);
            for (RepositoryRule r: rules) {
                NewBuiltInActiveRule rule1 = profile.activateRule("dartanalyzer", r.key);
                rule1.overrideSeverity(r.severity.name());
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load dartanalyzer rules", e);
        }

        profile.setDefault(true);
        profile.done();
    }
}
