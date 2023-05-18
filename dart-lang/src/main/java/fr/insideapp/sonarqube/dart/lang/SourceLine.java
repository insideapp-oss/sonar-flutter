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

public final class SourceLine {

    private final int count;
    private final int start;
    private final int end;
    private final int line;

    public SourceLine(final int line, final int count, final int start, final int end) {
        this.line = line;
        this.count = count;
        this.start = start;
        this.end = end;

    }

    @Override
    public String toString() {
        return "SourceLine [line=" + line + ", count=" + count + ", start=" + start + ", end=" + end + "]";
    }

    public int getLine() {
        return line;
    }

    public int getCount() {
        return count;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

}
