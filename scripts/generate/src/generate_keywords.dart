import 'dart:io';

import 'package:analyzer/dart/ast/token.dart';

void generateKeywords() {
  final file = File('../../dart-lang/src/main/resources/dart.keywords');
  final keywords = Keyword.keywords.keys.toList();
  file.writeAsStringSync(keywords.join('\n'));
  print('Generated ${keywords.length} keywords.');
}
