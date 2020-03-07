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
import groovy.json.JsonBuilder

/**
 * Assign severity to a rule based on its properties
 * @param category
 * @param key
 * @param description
 * @return
 */
def assignSeverity(category, key, description) {

    // Error category
    if (category.contains('Error')) {
        if (!key.contains('avoid') && !key.contains('comment') && !key.contains('empty')) {
            return "CRITICAL"
        } else {
            return "MAJOR"
        }

        // Style category
    } else if (category.contains('Style')) {
        if (key.contains('camel_case')) {
            return "MAJOR"
        } else {
            return "MINOR"
        }
    }

    // Specific
    if (key == 'avoid_print' || key == 'null_closures') {
        return "CRITICAL"
    }
    if (key == 'library_prefixes' || key == 'library_names' ||
            key == 'file_names' || key == 'empty_catches' || key == 'constant_identifier_names') {
        return "MAJOR"
    }

    // Default
    return "MINOR"
}

/**
 * Assign type (BUG, CODE_SMELL, VULNERABILITY) to a rule based on SQALE file
 * @param key
 * @return
 */
def assignType(key) {

    File sqaleXml = new File('../dart-lang/src/main/resources/fr/insideapp/sonarqube/dart/dartanalyzer/sqale-model.xml')

    /*
    Code Smell (Maintainability domain)
    Bug (Reliability domain)
    Vulnerability (Security domain)
    Security Hotspot (Security domain)
     */

    def sqale = new XmlSlurper().parseText(sqaleXml.text)
    def ruleNode = sqale.'**'.find { chc ->
        chc.'rule-key' == key
    }
    if (ruleNode) {

        parentNode = ruleNode.parent()
        grandParentNode = parentNode.parent()

        if (parentNode.name.text() == 'Security features') {
            return "VULNERABILITY"
        }

        if (grandParentNode.name.text() == 'Reliability') {
            return "BUG"
        }
    }

    // Default type
    return "CODE_SMELL"
}

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
                if (currentKey == 'Flutter') {
                    currentKey = ''
                    started = true
                }
                if (currentKey != '' && started) {
                    def entry = [:]
                    entry.key = currentKey
                    entry.name = currentKey.replace("_", " ").capitalize()
                    entry.description = currentDescription
                    entry.severity = assignSeverity(currentCategory, currentKey, currentDescription)
                    entry.type = assignType(currentKey)
                    result.add(entry)
                }
                currentKey = tag.text()
                break
            case 'H2':
                currentCategory = tag.text()
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
                    def entry = [:]
                    entry.key = currentKey
                    entry.name = currentKey.replace("_", " ").capitalize()
                    entry.description = currentDescription += "\n @see https://dart.dev/tools/diagnostic-messages#$currentKey"
                    entry.severity = "MINOR"
                    entry.type = "CODE_SMELL"
                    result.add(entry)
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
 * Write rules to a JSON file
 * @param rules
 * @param file
 * @return
 */
def writeRules(rules, file) {

    def builder = new JsonBuilder()
    builder(rules)

    file.text = builder.toPrettyString()

}

def writeProfileDartAnalyzer(rls, file) {
    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)
    xml.profile() {
        name "dartanalyzer"
        language "dart"
        rules {
            rls.each { r ->
                rule {
                    repositoryKey "dartanalyzer"
                    key r.key
                }
            }
        }
    }

    file.text = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + writer.toString()

}

def writeAnalysisOptions(rls, file) {
    text = "linter:\n"
    text += "  rules:\n"
    rls.forEach { r ->
        text += "    - ${r.key}\n"
    }
    file.text = text
}

def rulesUrl = "https://dart-lang.github.io/linter/lints/"
def diagnosticRulesUrl = "https://dart.dev/tools/diagnostic-messages"
File rulesJson = new File('../dart-lang/src/main/resources/fr/insideapp/sonarqube/dart/dartanalyzer/rules.json')
File profileXml = new File('../dart-lang/src/main/resources/fr/insideapp/sonarqube/dart/dartanalyzer/profile-dartanalyzer.xml')
File analysisOptionsYaml = new File('../dart-lang/src/main/resources/fr/insideapp/sonarqube/dart/dartanalyzer/analysis_options.yaml')
def rules = parseRules(rulesUrl)
rules.addAll(parseRulesFromDiagnostic(diagnosticRulesUrl))
writeRules(rules, rulesJson)
writeProfileDartAnalyzer(rules, profileXml)
writeAnalysisOptions(rules, analysisOptionsYaml)