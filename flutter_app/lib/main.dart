import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'package:flutter_app/RandomWordsState.dart';
void main()=> runApp(new MyApp());
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final wordPair = new WordPair.random();
    return new MaterialApp(
      title: 'This is title',
      home: new Scaffold(
        appBar: new AppBar(
          title: const Text("this appbar title"),
        ),
        body: Center(
          //child: const Text("this is body center---Hello World"),
          //child: new Text(wordPair.asPascalCase),
          child: RandomWords(),
        ),
      ),
    );
  }

}