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

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RepositoryRuleParserTest {

    private RepositoryRuleParser parser;

    @Before
    public void prepare() {
        parser = new RepositoryRuleParser();
    }

    @Test
    public void parseFailed() throws Throwable {
        assertThatExceptionOfType(IOException.class).isThrownBy(() -> {
            parser.parse("/not/existing/file.json");
        });
    }

    @Test
    public void parseSuccess() throws Throwable {
        // test
        List<RepositoryRule> repositoryRules = parser.parse("/rules/rules.json");
        // assert
        assertThat(repositoryRules).hasSize(2);

        RepositoryRule repositoryRule1 = repositoryRules.get(0);
        assertThat(repositoryRule1.key).isEqualTo("rule1");
        assertThat(repositoryRule1.name).isEqualTo("Rule 1");
        assertThat(repositoryRule1.description).isEqualTo("This is rule 1.");
        assertThat(repositoryRule1.severity).isEqualTo(RepositoryRule.Severity.MINOR);
        assertThat(repositoryRule1.type).isEqualTo(RepositoryRule.Type.CODE_SMELL);
        assertThat(repositoryRule1.debt).isEqualTo("5min");

        RepositoryRule repositoryRule2 = repositoryRules.get(1);
        assertThat(repositoryRule2.key).isEqualTo("rule2");
        assertThat(repositoryRule2.name).isEqualTo("Rule 2");
        assertThat(repositoryRule2.description).isEqualTo("This is rule 2.");
        assertThat(repositoryRule2.severity).isEqualTo(RepositoryRule.Severity.MAJOR);
        assertThat(repositoryRule2.type).isEqualTo(RepositoryRule.Type.VULNERABILITY);
        assertThat(repositoryRule2.debt).isEqualTo("15min");
    }
}
