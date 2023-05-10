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

/**
 * Utility class to handle stdin prompts.
 */
class Prompt {

    private String question
    private List<String> choices

    Prompt(String question, List<String> choices) {
        this.question = question
        this.choices = choices
    }

    /**
     * Prompts for choice.
     * @return Selected choice
     */
    def promptChoice() {
        use(ConsoleString) {
            def display = "${question} ("
            this.choices.eachWithIndex { c, i ->
                display += "${i + 1} = ${c}"
                if (i < choices.size() - 1) {
                    display += ", "
                }
            }
            display += ")"
            println display

            def answer = System.in.newReader().readLine()
            def error = false
            try {
                def intAnswer = Integer.parseInt(answer)
                if (intAnswer < 1 || intAnswer > choices.size()) {
                    error = true
                } else {
                    return choices[intAnswer - 1]
                }
            } catch (e) {
                error = true
            }

            if (error) {
                println "Invalid choice".style(ConsoleString.Color.RED)
                return this.promptChoice()
            }
        }
    }

    /**
     * Prompts for duration.
     * @return Duration formatted as {X}min
     */
    def promptDuration() {
        use(ConsoleString) {
            println "${question} (in minutes)"
        }

        def answer = System.in.newReader().readLine()
        def error = false
        try {
            def intAnswer = Integer.parseInt(answer)
            if (intAnswer < 0) {
                error = true
            } else {
                return "${intAnswer}min"
            }
        } catch (e) {
            error = true
        }

        if (error) {
            println "Invalid value".style(ConsoleString.Color.RED)
            return this.promptDuration()
        }
    }

    /**
     * Prompts for text.
     * @return Text
     */
    def promptText() {
        use(ConsoleString) {
            println "${question}"
        }

        def answer = System.in.newReader().readLine()
        return answer
    }
}