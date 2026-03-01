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
package fr.insideapp.sonarqube.dart.lang.issues;

import fr.insideapp.sonarqube.dart.lang.Dart;
import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.DartAnalyzerRulesDefinition;
import fr.insideapp.sonarqube.dart.lang.issues.statemanagement.FlutterStateManagementRulesDefinition;
import org.sonar.api.server.profile.BuiltInQualityProfilesDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class DartProfile implements BuiltInQualityProfilesDefinition {

    private static final Logger LOGGER = LoggerFactory.getLogger(DartProfile.class);

    @Override
    public void define(BuiltInQualityProfilesDefinition.Context context) {

        NewBuiltInQualityProfile profile = context.createBuiltInQualityProfile(DartAnalyzerRulesDefinition.REPOSITORY_KEY, Dart.KEY);
        profile.setDefault(true);
        RepositoryRuleParser repositoryRuleParser = new RepositoryRuleParser();

        // dartanalyzer rules
        loadRulesIntoProfile(repositoryRuleParser, profile, DartAnalyzerRulesDefinition.RULES_FILE, DartAnalyzerRulesDefinition.REPOSITORY_KEY);

        // state management rules
        loadRulesIntoProfile(repositoryRuleParser, profile, FlutterStateManagementRulesDefinition.RULES_FILE, FlutterStateManagementRulesDefinition.REPOSITORY_KEY);

        profile.done();
    }

    private void loadRulesIntoProfile(RepositoryRuleParser parser, NewBuiltInQualityProfile profile, String rulesFile, String repositoryKey) {
        try {
            List<RepositoryRule> rules = parser.parse(rulesFile);
            for (RepositoryRule r : rules) {
                if (r.name == null || r.severity == null || r.type == null || r.description == null) {
                    LOGGER.warn("Cannot add {} rule to {} profile, rule data is missing in rules.json", r.key, repositoryKey);
                } else {
                    if (r.active) {
                        NewBuiltInActiveRule rule = profile.activateRule(repositoryKey, r.key);
                        rule.overrideSeverity(r.severity.name());
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load {} rules", repositoryKey, e);
        }
    }
}
