void main() {

  var test = true;
  var test2 = false;

  // A comment

  if (test) {
    print('TOTO');
    if (test2 && false) {
      print('TUTU');
    }

  } else {
    print('TATA');
  }

}

void func() {
  print('hello');
}

class TestClass {
  var test = 'test';

  TestClass() {
    // Constructor
  }

  void method() {
    return 'ok';
  }
}