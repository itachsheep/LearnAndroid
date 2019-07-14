import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';
import 'package:flutter_app/RandomWordsState.dart';
import 'LearnLayout.dart';
import 'Simpe.dart';

//void main()=> runApp(new MyApp());
//void main() {
//  runApp(new FlutterLayout());
//}

//void main() => runApp(new SimpleText("test"));
/*void main() => runApp(new Text(
  "hello text",
  textDirection: TextDirection.ltr,
));*/

void main() => runApp(
    new Center(
      child: new SimpleText(
        "simple Text",
        textDirection: TextDirection.ltr,
      ),
    ),
);

class MyApp extends StatelessWidget {
  @override

    Widget build(BuildContext context) {
      return new MaterialApp(
        title: "MaterialApp title：Startup Name Generator",
        theme: new ThemeData(
          primaryColor: Colors.amberAccent,
        ),
        home:new RandomWords(),
      );
  }
}