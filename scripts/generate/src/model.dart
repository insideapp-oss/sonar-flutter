class Rule {
  /// snake case key
  final String key;

  final String name;

  final RuleType type;

  final RuleStatus status;

  final RuleSeverity severity;

  /// html content
  final String description;

  Rule({
    required this.key,
    required this.name,
    required this.type,
    required this.status,
    required this.severity,
    required this.description,
  });

  @override
  String toString() {
    return 'Rule{key: $key}';
  }

  Map<String, dynamic> toJson() => {
        'key': key,
        'name': name,
        'type': type.name,
        'status': status.name,
        'severity': severity.name,
        'description': description,
      };

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Rule &&
          runtimeType == other.runtimeType &&
          key == other.key &&
          name == other.name &&
          type == other.type &&
          status == other.status &&
          severity == other.severity;

  @override
  int get hashCode => key.hashCode ^ name.hashCode ^ type.hashCode ^ status.hashCode ^ severity.hashCode;
}

class RuleType {
  final String name;

  const RuleType._(this.name);

  static const CODE_SMELL = RuleType._('CODE_SMELL');
  static const BUG = RuleType._('BUG');
  static const VULNERABILITY = RuleType._('VULNERABILITY');
}

class RuleStatus {
  final String name;

  const RuleStatus._(this.name);

  static const BETA = RuleStatus._('BETA');
  static const DEPRECATED = RuleStatus._('DEPRECATED');
  static const READY = RuleStatus._('READY');
}

class RuleSeverity {
  final String name;

  const RuleSeverity._(this.name);

  static const INFO = RuleSeverity._('INFO');
  static const MINOR = RuleSeverity._('MINOR');
  static const MAJOR = RuleSeverity._('MAJOR');
  static const CRITICAL = RuleSeverity._('CRITICAL');
  static const BLOCKER = RuleSeverity._('BLOCKER');
}
