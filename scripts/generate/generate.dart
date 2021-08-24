import 'dart:convert';
import 'dart:io';

import 'package:xml/xml.dart';

import 'src/generate_errors.dart';
import 'src/generate_keywords.dart';
import 'src/generate_lints.dart';
import 'src/model.dart';

Future<void> main() async {
  generateKeywords();

  final Set<Rule> rules = {};

  rules.addAll(generateLints());
  rules.addAll(await generateErrors());

  final sorted = rules.toList()..sort((a, b) => a.key.compareTo(b.key));

  writeRulesJson(sorted);
  writeProfileXml(sorted);
  writeAnalysisOptions(sorted);
}

void writeRulesJson(List<Rule> rules) {
  final json = JsonEncoder.withIndent('    ').convert(rules);
  File('../../dart-lang/src/main/resources/fr/insideapp/sonarqube/dart/dartanalyzer/rules.json').writeAsStringSync(json);
  print('Updated rules.json');
}

void writeProfileXml(List<Rule> rules) {
  final xb = XmlBuilder()..declaration(encoding: 'UTF-8', attributes: {'standalone': 'no'});
  xb.element('profile', nest: () {
    xb.element('name', nest: () => xb.text('dartanalyzer'));
    xb.element('language', nest: () => xb.text('dart'));
    xb.element('rules', nest: () {
      rules.forEach((rule) {
        xb.element('rule', nest: () {
          xb.element('repositoryKey', nest: () => xb.text('dartanalyzer'));
          xb.element('key', nest: () => xb.text(rule.key));
        });
      });
    });
  });

  final xml = xb.buildDocument().toXmlString(pretty: true);
  File('../../dart-lang/src/main/resources/fr/insideapp/sonarqube/dart/dartanalyzer/profile-dartanalyzer.xml').writeAsStringSync(xml);
  print('Updated profile-dartanalyzer.xml');
}

void writeAnalysisOptions(List<Rule> rules) {
  final sb = StringBuffer();
  sb.writeln('linter:');
  sb.writeln('  rules:');
  rules.forEach((rule) => sb.writeln('    - ${rule.key}'));

  File('../../dart-lang/src/main/resources/fr/insideapp/sonarqube/dart/dartanalyzer/analysis_options.yaml').writeAsStringSync(sb.toString());
  print('Updated analysis_options.yaml');
}
