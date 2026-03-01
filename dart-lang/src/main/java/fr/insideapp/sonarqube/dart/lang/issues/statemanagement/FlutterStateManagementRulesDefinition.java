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
package fr.insideapp.sonarqube.dart.lang.issues.statemanagement;

import fr.insideapp.sonarqube.dart.lang.Dart;
import fr.insideapp.sonarqube.dart.lang.issues.RepositoryRule;
import fr.insideapp.sonarqube.dart.lang.issues.RepositoryRuleParser;
import org.sonar.api.issue.impact.Severity;
import org.sonar.api.issue.impact.SoftwareQuality;
import org.sonar.api.rules.CleanCodeAttribute;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.rule.RulesDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class FlutterStateManagementRulesDefinition implements RulesDefinition {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlutterStateManagementRulesDefinition.class);
    public static final String REPOSITORY_KEY = "flutter-state-management";
    public static final String REPOSITORY_NAME = "Flutter State Management";
    public static final String RULES_FILE = "/flutter-state-management/rules.json";

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, Dart.KEY).setName(REPOSITORY_NAME);
        RepositoryRuleParser repositoryRuleParser = new RepositoryRuleParser();

        try {
            List<RepositoryRule> rules = repositoryRuleParser.parse(RULES_FILE);
            for (RepositoryRule rule : rules) {
                if (rule.name == null || rule.severity == null || rule.type == null || rule.description == null) {
                    LOGGER.warn("Cannot load {} rule from {}, rule data is missing", rule.key, REPOSITORY_KEY);
                } else {
                    RulesDefinition.NewRule newRule = repository.createRule(rule.key)
                            .setName(rule.name)
                            .setSeverity(rule.severity.name())
                            .setType(RuleType.valueOf(rule.type.name()))
                            .setActivatedByDefault(rule.active)
                            .setHtmlDescription(rule.description);
                    newRule.setDebtRemediationFunction(newRule.debtRemediationFunctions().constantPerIssue(rule.debt));

                    if (rule.cleanCodeAttribute != null) {
                        try {
                            newRule.setCleanCodeAttribute(CleanCodeAttribute.valueOf(rule.cleanCodeAttribute));
                        } catch (IllegalArgumentException e) {
                            LOGGER.warn("Unknown CleanCodeAttribute '{}' for rule {}", rule.cleanCodeAttribute, rule.key);
                        }
                    }

                    if (rule.impacts != null && !rule.impacts.isEmpty()) {
                        for (RepositoryRule.Impact impact : rule.impacts) {
                            try {
                                newRule.addDefaultImpact(
                                        SoftwareQuality.valueOf(impact.softwareQuality),
                                        Severity.valueOf(impact.severity)
                                );
                            } catch (IllegalArgumentException e) {
                                LOGGER.warn("Invalid impact for rule {}: {} / {}", rule.key, impact.softwareQuality, impact.severity);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load state management rules", e);
        }

        repository.done();
    }
}
