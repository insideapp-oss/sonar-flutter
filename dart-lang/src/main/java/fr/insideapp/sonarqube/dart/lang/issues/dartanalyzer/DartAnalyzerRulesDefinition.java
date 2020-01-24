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
package fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer;

import fr.insideapp.sonarqube.dart.lang.Dart;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.SonarRuntime;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.squidbridge.rules.SqaleXmlLoader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;


public class DartAnalyzerRulesDefinition implements RulesDefinition {
    private static final Logger LOGGER = LoggerFactory.getLogger(DartAnalyzerRulesDefinition.class);
    public static final String REPOSITORY_KEY = "dartanalyzer";
    public static final String REPOSITORY_NAME = REPOSITORY_KEY;
    public static final String RULES_FILE = "/fr/insideapp/sonarqube/dart/dartanalyzer/rules.json";

    private final SonarRuntime sonarRuntime;

    public DartAnalyzerRulesDefinition(SonarRuntime sonarRuntime) {
        this.sonarRuntime = sonarRuntime;
    }

    @Override
    public void define(Context context) {
        NewRepository repository = context.createRepository(REPOSITORY_KEY, Dart.KEY).setName(REPOSITORY_NAME);

        try(Reader reader = new InputStreamReader(getClass().getResourceAsStream(RULES_FILE), Charset.forName("UTF-8"))){
            JSONArray slRules = (JSONArray) JSONValue.parse(reader);
            if(slRules != null){
                for (Object obj : slRules) {
                    JSONObject slRule = (JSONObject) obj;
                    repository.createRule((String) slRule.get("key"))
                            .setName((String) slRule.get("name"))
                            .setSeverity((String) slRule.get("severity"))
                            .setType(RuleType.valueOf((String) slRule.get("type")))
                            .setHtmlDescription((String) slRule.get("description"));
                }
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load dartanalyzer rules", e);
        }

        SqaleXmlLoader.load(repository, "/fr/insideapp/sonarqube/dart/dartanalyzer/sqale-model.xml");
        repository.done();
    }
}
