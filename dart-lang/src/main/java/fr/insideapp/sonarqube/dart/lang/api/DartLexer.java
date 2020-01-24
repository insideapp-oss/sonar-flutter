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
package fr.insideapp.sonarqube.dart.lang.api;

import com.sonar.sslr.impl.Lexer;
import com.sonar.sslr.impl.channel.BlackHoleChannel;

import static com.sonar.sslr.api.GenericTokenType.LITERAL;
import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.regexp;
import static com.sonar.sslr.impl.channel.RegexpChannelBuilder.commentRegexp;


public class DartLexer {

    public static Lexer create() {
        return create(new DartConfiguration());
    }

    public static Lexer create(DartConfiguration conf) {
        return Lexer.builder()
                .withCharset(conf.getCharset())

                .withFailIfNoChannelToConsumeOneCharacter(false)

                // Comments
                .withChannel(commentRegexp("//[^\\n\\r]*+"))
                .withChannel(commentRegexp("///[^\\n\\r]*+"))
                .withChannel(commentRegexp("/\\*[\\s\\S]*?\\*/"))

                // All other tokens
                .withChannel(regexp(LITERAL, "[^\r\n\\s/]+"))

                .withChannel(new BlackHoleChannel("[\\s]"))

                .build();
    }

}
