/*
 Update rules.json and profile-dartanalyzer.xml from Dart Linter rules website.
 Severity is assigned based on category and rule naming
 */

import groovy.xml.MarkupBuilder
@Grab(group = 'org.codehaus.groovy.modules.http-builder',
        module = 'http-builder', version = '0.7')

import groovyx.net.http.*
@Grab(group = 'org.codehaus.groovy.modules.http-builder',
        module = 'http-builder', version = '0.7')

import groovyx.net.http.*
@Grab(group = 'org.codehaus.groovy.modules.http-builder',
        module = 'http-builder', version = '0.7')

import commons.RuleUpdater
import commons.ConsoleString
import commons.Rule

String.mixin(ConsoleString)

/**
 * Parse rules from the lint website
 * @param url
 * @return
 */
def parseRules(url) {

    def result = []

    def http = new HTTPBuilder(url)
    def html = http.get([:])

    def root = html."**".find { it.name() == "SECTION" }

    def currentCategory = ''
    def currentKey = ''
    def currentDescription = ''
    def started = false
    root."**".findAll { it.name() == "STRONG" || it.name() == "H2" || it.name() == "P" }.each { tag ->

        switch (tag.name()) {
            case 'STRONG':
                if (currentKey != '' && started) {
                    def rule = new Rule(
                            currentKey,
                            currentDescription,
                            null, // no severity
                            null, // no type
                            currentKey.replace("_", " ").capitalize(),
                            null // no debt
                    )
                    result.add(rule)
                }
                currentKey = tag.text()
                break
            case 'H2':
                currentCategory = tag.text()
                currentKey = ''
                started = true
                break
            case 'P':
                currentDescription = tag.text()
                break
        }

    }

    result
}

/**
 * Parse rules from the diagnostic messages website
 * @param url
 * @return
 */

def parseRulesFromDiagnostic(url) {
    def result = []

    def http = new HTTPBuilder(url)
    def html = http.get([:])

    def root = html."**".find { it.name() == "ARTICLE" }

    def currentKey = ''
    def currentDescription = ''
    def descriptionOver = false

    root."**".findAll { it.name() == "H3" || it.name() == "H4" || it.name() == "P" }.each { tag ->
        switch (tag.name()) {
            case 'H3':
                currentKey = tag.text()
                break
            case 'H4':
                if (tag.text() == "Description") {
                    //We might have several lines of entries
                    descriptionOver = false
                    currentDescription = ""
                } else if (tag.text() == "Examples") {
                    descriptionOver = true
                    //When reaching examples, we consider the description over
                    //todo but we might want to provider nicer description with examples later !
                    def rule = new Rule(
                            currentKey,
                            currentDescription += "\n @see https://dart.dev/tools/diagnostic-messages#$currentKey",
                            null, // no severity
                            null, // no type
                            currentKey.replace("_", " ").capitalize(),
                            null // no debt
                    )
                    result.add(rule)
                }
                break
            case 'P':
                if (!descriptionOver) {
                    currentDescription += tag.text()
                }
                break
        }
    }
    result
}

/**
 * Write default analysis_options.yaml
 * @param rls
 * @param file
 * @return
 */
def writeAnalysisOptions(rls, file) {
    text = "linter:\n"
    text += "  rules:\n"
    rls.forEach { r ->
        text += "    - ${r.key}\n"
    }
    file.text = text
}


def updater = new RuleUpdater('dart-lang/src/main/resources/dartanalyzer/rules.json', {

    def rules = parseRules('https://dart-lang.github.io/linter/lints/')
    rules.addAll(parseRulesFromDiagnostic('https://dart.dev/tools/diagnostic-messages'))

    writeAnalysisOptions(rules, new File('dart-lang/src/main/resources/dartanalyzer/analysis_options.yaml'))

    return rules
})

updater.update(Integer.parseInt(project.properties['scripts.max-manual']))