// FIXME : this is a temporary script to merge Sqale data with rules JSON file
// Should be deleted when all linters will be migrated

import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

def rulesFile = new File('dart-lang/src/main/resources/fr/insideapp/sonarqube/dart/dartanalyzer/rules.json')
def sqaleFile = new File('dart-lang/src/main/resources/fr/insideapp/sonarqube/dart/dartanalyzer/sqale-model.xml')

def outFile = new File('dart-lang/src/main/resources/dartanalyzer-rules.json')

def jsonSlurper = new JsonSlurper()
def rules = jsonSlurper.parseText(rulesFile.getText('UTF-8'))

def sqale = new XmlSlurper().parseText(sqaleFile.getText('UTF-8'))

def outputRules = []

rules.each { r ->
    println r.name
    def outRule = [key: r.key, name: r.name, severity: r.severity, description: r.description]
    def debtInfo = sqale.'**'.find {it.name() == 'rule-key' && it.text() == r.key}
    if (debtInfo != null) {
        def fct = debtInfo.parent().prop[0].txt.text()
        def offset = debtInfo.parent().prop[1].val.text() + debtInfo.parent().prop[1].txt.text()
        println('>> ' + fct + ' - ' + offset)
        outRule.debt = [function: fct, offset: offset]
    } else {
        println '>> No debt info here !'
    }
    outputRules.add(outRule)
}

def builder = new JsonBuilder()
builder(outputRules)
outFile.text = builder.toPrettyString()