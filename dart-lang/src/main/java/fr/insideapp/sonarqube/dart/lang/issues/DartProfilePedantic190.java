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

public class DartProfilePedantic190 implements BuiltInQualityProfilesDefinition {

    private static final Logger LOGGER = LoggerFactory.getLogger(DartProfile.class);
    public static final String DARTANALYZER_PROFILE_PATH = "fr/insideapp/sonarqube/dart/dartanalyzer/profile-pedantic.1.9.0.xml";

    @Override
    public void define(BuiltInQualityProfilesDefinition.Context context) {

        // dartanalyzer profile
        LOGGER.info("Creating dartanalyzer pedantic 1.9.0 Profile");
        NewBuiltInQualityProfile nbiqp = context.createBuiltInQualityProfile("dartanalyzer pedantic 1.9.0", Dart.KEY);
        XmlProfileParser.parse(DARTANALYZER_PROFILE_PATH, nbiqp);
        nbiqp.done();
    }
}
