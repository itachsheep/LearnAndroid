import 'package:flutter/material.dart';

void main()=> runApp(new MyApp());
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {

    return new MaterialApp(
      title: 'This is title',
      home: new Scaffold(
        appBar: new AppBar(
          title: const Text("this appbar title"),
        ),
        body: const Center(
          child: const Text("this is body center---Hello World"),
        ),
      ),
    );
  }

}