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
package commons

class Rule {

    enum Severity {
        BLOCKER,
        CRITICAL,
        MAJOR,
        MINOR,
        INFO
    }

    enum Type {
        CODE_SMELL,
        BUG,
        VULNERABILITY,
        SECURITY_HOTSPOT
    }

    public String key;
    public String description;
    public Severity severity;
    public Type type;
    public String name;
    public String debt;
    public boolean active;

    Rule(
            String key,
            String description,
            Severity severity,
            Type type,
            String name,
            String debt,
            boolean active
    ) {
        this.key = key
        this.description = description
        this.severity = severity
        this.type = type
        this.name = name
        this.debt = debt
        this.active = active
    }

}