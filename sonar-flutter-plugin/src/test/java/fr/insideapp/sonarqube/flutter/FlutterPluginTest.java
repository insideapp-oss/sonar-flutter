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
package fr.insideapp.sonarqube.flutter;

import org.junit.Test;
import org.sonar.api.Plugin;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.utils.Version;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FlutterPluginTest {

    @Test
    public void define() {

        SonarRuntime sonarRuntime = SonarRuntimeImpl.forSonarQube(Version.create(7, 9), SonarQubeSide.SERVER, SonarEdition.COMMUNITY);
        Plugin.Context context = new Plugin.Context(sonarRuntime);


        FlutterPlugin plugin = new FlutterPlugin();
        plugin.define(context);

        List<?> extensions = context.getExtensions();
        assertThat(extensions).hasSize(15);


    }
}
