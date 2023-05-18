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
package fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer;

import org.junit.Test;
import org.sonar.api.server.rule.RulesDefinition;

import static org.assertj.core.api.Assertions.assertThat;

public class DartAnalyzerRulesDefinitionTest {

    @Test
    public void define() {

        DartAnalyzerRulesDefinition rulesDefinition = new DartAnalyzerRulesDefinition();
        RulesDefinition.Context context = new RulesDefinition.Context();
        rulesDefinition.define(context);

        RulesDefinition.Repository dartAnalyzerRepository = context.repository("dartanalyzer");
        assertThat(dartAnalyzerRepository).isNotNull();
        assertThat(dartAnalyzerRepository.name()).isEqualTo("dartanalyzer");
        assertThat(dartAnalyzerRepository.language()).isEqualTo("dart");
        assertThat(dartAnalyzerRepository.rules()).isNotEmpty();


    }
}