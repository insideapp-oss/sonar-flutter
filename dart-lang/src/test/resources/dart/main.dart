import 'dart:async';

import 'package:flutter/material.dart';
import 'repository.dart';
import 'package:http/http.dart' as http;

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or simply save your changes to "hot reload" in a Flutter IDE).
        // Notice that the counter didn't reset back to zero; the application
        // is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {

  Future<List<Repository>> _repositories;

  void initState() {
    _repositories = getRepositories();
    super.initState();
  }

  Future<List<Repository>> getRepositories() async {

    var url = 'http://192.168.0.26:3000';

    var _controller = StreamController<String>();

    var response = await http.get(url);

    if (response.statusCode == 200) {
      print("yo");
    } else {

    }

    if (response.statusCode == 200) {

      // Return scaled  back results
      var result = repositoryFromJson(response.body);
      return result;

    } else {
      throw Exception('Error: ${response.statusCode}');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[

            FutureBuilder<List<Repository>>(
              future: _repositories,
              builder: (BuildContext context,
                  AsyncSnapshot<List<Repository>> snapshot) {
                if (snapshot.connectionState == ConnectionState.done) {
                  if (!snapshot.hasError) {
                    return Text('Result: ${snapshot.data.length}');
                  } else {
                    return Text('Result: Error');
                  }
                } else {
                  return Text(
                    'Result: 0',
                  );
                }
              },
            ),
          ],
        ),
      ),
    );
  }
}
