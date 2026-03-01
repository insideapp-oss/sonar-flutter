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
package fr.insideapp.sonarqube.dart.lang;

public class PubSpec {

    public static final String NAME = "pubspec.yaml";

    private final boolean flutter;

    private final String version;

    private final boolean usesBloc;

    private final boolean usesRiverpod;

    private final boolean usesProvider;

    PubSpec(boolean flutter, String version, boolean usesBloc, boolean usesRiverpod, boolean usesProvider) {
        this.flutter = flutter;
        this.version = version;
        this.usesBloc = usesBloc;
        this.usesRiverpod = usesRiverpod;
        this.usesProvider = usesProvider;
    }

    public boolean isFlutter() {
        return flutter;
    }

    public String getProjectVersion() {
        return version;
    }

    public boolean usesBloc() {
        return usesBloc;
    }

    public boolean usesRiverpod() {
        return usesRiverpod;
    }

    public boolean usesProvider() {
        return usesProvider;
    }

    public boolean usesStateManagement() {
        return usesBloc || usesRiverpod || usesProvider;
    }
}