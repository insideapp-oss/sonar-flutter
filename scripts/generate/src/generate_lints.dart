import 'dart:convert';

import 'package:linter/src/analyzer.dart';
import 'package:linter/src/rules.dart';
import 'package:markdown/markdown.dart';

import 'model.dart';

Set<Rule> generateLints() {
  registerLintRules();
  final Set<Rule> rules = Analyzer.facade.registeredRules.map((lint) {
    final markdown = Document().parseLines(LineSplitter().convert(lint.details));
    final html = HtmlRenderer().render(markdown);
    return Rule(
      key: lint.name,
      name: lint.description,
      type: _getRuleType(lint),
      status: _getRuleStatus(lint),
      severity: _getRuleSeverity(lint),
      description: '${html}\n\n@see https://dart-lang.github.io/linter/lints/${lint.name}.html',
    );
  }).toSet();
  print('Generated ${rules.length} rules for lints.');
  return rules;
}

RuleStatus _getRuleStatus(LintRule lint) {
  switch (lint.maturity) {
    case Maturity.deprecated:
      return RuleStatus.DEPRECATED;
    case Maturity.experimental:
      return RuleStatus.BETA;
    default:
      return RuleStatus.READY;
  }
}

RuleType _getRuleType(LintRule lint) {
  switch (lint.group) {
    case Group.errors:
      return RuleType.BUG;
    case Group.pub:
    case Group.style:
    default:
      return RuleType.CODE_SMELL;
  }
}

RuleSeverity _getRuleSeverity(LintRule lint) {
  if (lint.group == Group.errors) {
    if (!lint.name.contains('avoid') && !lint.name.contains('comment') && !lint.name.contains('empty')) {
      return RuleSeverity.CRITICAL;
    } else {
      return RuleSeverity.MAJOR;
    }

    // Style category
  } else if (lint.group == Group.style) {
    if (lint.name.contains('camel_case')) {
      return RuleSeverity.MAJOR;
    } else {
      return RuleSeverity.MINOR;
    }
  }

  // Specific
  if (lint.name == 'avoid_print' || lint.name == 'null_closures') {
    return RuleSeverity.CRITICAL;
  }
  if (lint.name == 'library_prefixes' ||
      lint.name == 'library_names' ||
      lint.name == 'file_names' ||
      lint.name == 'empty_catches' ||
      lint.name == 'constant_identifier_names') {
    return RuleSeverity.MAJOR;
  }

  return RuleSeverity.MINOR;
}
