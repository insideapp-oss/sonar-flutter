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
 * Rule updater: performs new rules retrieval, checks and update.
 */
class RuleUpdater {

    private File file
    private Closure fetchClosure

    /**
     * Constructor.
     * @param filePath Path to the current rules JSON file
     * @param fetchClosure Closure used to fetch rules from tool / repository
     */
    RuleUpdater(String filePath, Closure fetchClosure) {
        this.file = new File(filePath)
        this.fetchClosure = fetchClosure
    }

    /**
     * Read rules from file.
     * @return List of rules
     */
    def private Set<Rule> readRules() {
        def jsonSlurper = new groovy.json.JsonSlurper()
        return jsonSlurper.parseText(file.getText('UTF-8')) as Set<Rule>
    }

    /**
     * Write rules to the rule file.
     * @param rules List of rules
     */
    def private writeRules(ArrayList<Rule> rules) {
        def generator = new groovy.json.JsonGenerator.Options().excludeNulls().build()
        def json = new groovy.json.JsonBuilder(generator)
        json(rules) { rule ->
            key rule.key
            name rule.name
            severity rule.severity
            description rule.description
            type rule.type
            debt rule.debt
            active rule.active
        }
        file.text = groovy.json.JsonOutput.prettyPrint(json.toString())
    }

    /**
     * Merge new rules with existing rules.
     * @param existingRules List of existing rules
     * @param newRules List of new rules
     * @return List of merged rules
     */
    def private ArrayList<Rule> mergeRules(ArrayList<Rule> existingRules, ArrayList<Rule> newRules) {

        def allRules = [] as ArrayList<Rule>

        use(ConsoleString) {
            newRules.each { r ->
                def alreadyProcessed = allRules.find { er -> er.key == r.key }
                if (alreadyProcessed) {
                    println "Warning: rule already processed, skipping ${alreadyProcessed.key.style(ConsoleString.Color.DEFAULT_BOLD)}"
                } else {
                    def exists = existingRules.find { er -> er.key == r.key }
                    // Add new rules, if they don't exist yet
                    if (!exists) {
                        println "Adding new rule ${r.key.style(ConsoleString.Color.DEFAULT_BOLD)}"
                        allRules.add(r)
                        // Update if they already exist
                    } else {
                        println "Updating existing rule ${exists.key.style(ConsoleString.Color.DEFAULT_BOLD)}"
                        // Description
                        exists.description = r.description
                        // Active
                        exists.active = r.active
                        allRules.add(exists)
                    }
                }
            }
        }

        return allRules
    }

    /**
     * Scan rules for missing manual info an prompt when missing
     * @param rules List of rules to check
     * @param max Max number of rules to manually process. No limit if set to 0.
     * @return List of rules (with all data completed)
     */
    def private requestManualInfo(ArrayList<Rule> rules, int max = 0) {

        def rulesCompleted = [] as ArrayList<Rule>
        def manuallyProcessedCount = 0;

        use(ConsoleString) {
            rules.each { r ->

                if (r.severity == null || r.debt == null || r.type == null) {
                    if (max == 0 || manuallyProcessedCount < max) {
                        println ""
                        println "Missing information on rule ${r.key}".style(ConsoleString.Color.YELLOW)
                        println "${r.description}".style(ConsoleString.Color.DEFAULT)
                        println ""
                        if (r.name == null) {
                            r.name = new Prompt("Name?", null).promptText()
                            println r.name.style(ConsoleString.Color.DEFAULT_BOLD)
                        }
                        if (r.severity == null) {
                            List<String> severities = Arrays.asList(Rule.Severity.values().each { s -> s.name() });
                            def choice = new Prompt("Severity?", severities).promptChoice() as String
                            r.severity = Enum.valueOf(Rule.Severity.class, choice);
                            println r.severity.name().style(ConsoleString.Color.DEFAULT_BOLD)
                        }
                        if (r.type == null) {
                            List<String> types = Arrays.asList(Rule.Type.values().each { s -> s.name() });
                            def choice = new Prompt("Type?", types).promptChoice() as String
                            r.type = Enum.valueOf(Rule.Type.class, choice);
                            println r.type.name().style(ConsoleString.Color.DEFAULT_BOLD)
                        }
                        if (r.debt == null) {
                            def offset = new Prompt("Remediation time?", null).promptDuration()
                            println offset.style(ConsoleString.Color.DEFAULT_BOLD)
                            r.debt = offset
                        }

                        manuallyProcessedCount++
                    }

                }

                rulesCompleted.add(r)

            }
        }

        return rulesCompleted
    }

    /**
     * Performs rule update.
     * @param max Max number of rules to manually process. No limit if set to 0.
     */
    def update(int max = 0) {

        def existingRules = readRules() as ArrayList<Rule>
        println "Read ${existingRules.size()} existing rule(s) from file"

        println "Processing rules from website"
        def rules = fetchClosure.call() as ArrayList<Rule>
        println "Read ${rules.size()} rule(s) from website"

        def allRules = mergeRules(existingRules, rules) as ArrayList<Rule>
        println "${allRules.size()} rule(s) merged"

        // Fill missing info
        def completedRules = requestManualInfo(allRules, max) as ArrayList<Rule>
        println "${completedRules.size()} rule(s) about to be saved"

        writeRules(completedRules)
    }
}