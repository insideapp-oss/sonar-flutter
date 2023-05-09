import 'dart:convert';

import 'package:analyzer/error/error.dart';
import 'package:analyzer/src/dart/error/hint_codes.dart';
import 'package:analyzer/src/dart/error/lint_codes.dart';
import 'package:analyzer/src/error/codes.dart';
import 'package:analyzer/src/pubspec/pubspec_warning_code.dart';
import 'package:http/http.dart' as http;
import 'package:linter/src/analyzer.dart';
import 'package:markdown/markdown.dart';

import 'model.dart';

const url = 'https://raw.githubusercontent.com/dart-lang/sdk/master/pkg/analyzer/tool/diagnostics/diagnostics.md';

Future<Set<Rule>> generateErrors() async {
  final Set<Rule> rules = {};

  final content = (await http.get(Uri.parse(url))).body;

  final markdown = Document().parseLines(LineSplitter().convert(content));

  final codes = errorCodeValues
      .where((code) =>
          code is HintCode ||
          code is LintCode ||
          code is PubspecWarningCode ||
          code is StaticWarningCode ||
          code is TodoCode ||
          code is CompileTimeErrorCode)
      .toList();

  final markdownContent = markdown.skipWhile((node) {
    final isDiagnosticsNode = node is Element && node.tag == 'h2' && node.textContent == 'Diagnostics';
    return !isDiagnosticsNode;
  }).skip(2);

  final List<MarkdownRule> mdRules = [];
  for (final node in markdownContent) {
    if (node is Element && node.tag == 'h3') {
      mdRules.add(MarkdownRule(node.textContent));
    } else {
      mdRules.last.append(node);
    }
  }

  for (final code in codes) {
    final key = code.name.toLowerCase();
    final name = '${key[0].toUpperCase()}${key.substring(1)}'.replaceAll('_', ' ');

    String description;
    if (mdRules.any((rule) => rule.key.toUpperCase() == code.name)) {
      description = HtmlRenderer().render(mdRules.firstWhere((rule) => rule.key.toUpperCase() == code.name).content);
    } else {
      description = '${code.message}\n\n${code.correction}';
    }

    if (code.hasPublishedDocs) {
      description += '\n\n@see ${code.url}';
    }

    // fix up a couple links
    description = description.replaceAll('href="#', 'href="https://dart.dev/tools/diagnostic-messages#');

    rules.add(Rule(
      key: key,
      name: name,
      type: _getRuleType(code),
      status: RuleStatus.READY,
      severity: _getRuleSeverity(code),
      description: description,
    ));
  }

  print('Generated ${rules.length} rules for error codes.');
  return rules;
}

RuleType _getRuleType(ErrorCode code) {
  if (code is StaticWarningCode || code is CompileTimeErrorCode) {
    return RuleType.BUG;
  }
  return RuleType.CODE_SMELL;
}

RuleSeverity _getRuleSeverity(ErrorCode code) {
  if (code is StaticWarningCode || code is CompileTimeErrorCode) {
    return RuleSeverity.MAJOR;
  }
  return RuleSeverity.MINOR;
}

class MarkdownRule {
  final String key;

  List<Node> _content = [];

  List<Node> get content => _content;

  MarkdownRule(this.key);

  void append(Node node) => _content.add(node);
}
