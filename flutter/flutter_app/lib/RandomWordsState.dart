import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';

class RandomWords extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new RandomWordsState();
  }
}

class RandomWordsState extends State<RandomWords> {
  final List<WordPair> _suggestions = <WordPair>[];
  final TextStyle _biggerFont = const TextStyle(fontSize: 18.0);
  final Set<WordPair> _saved = new Set<WordPair>();

  Widget _buildSuggestions() {
    return new  ListView.builder(
        padding: const EdgeInsets.all(16.0),
        itemBuilder: (BuildContext _context, int i){
          if(i.isOdd){
            return new Divider();
          }
          final int index = i ~/2 ;
          if(index >= _suggestions.length){
            _suggestions.addAll(generateWordPairs().take(10));
          }
          return _buildRow(_suggestions[index]);
        }
    );
  }

  Widget _buildRow(WordPair pair){
    final bool alreadySaved = _saved.contains(pair);
    return new ListTile(
      title: new Text(
        pair.asPascalCase,
        style: _biggerFont,
      ),
      trailing: new Icon(
        alreadySaved ? Icons.favorite : Icons.favorite_border,
        color: alreadySaved ? Colors.red : null,
      ),

      onTap: (){
        setState(() {
          if(alreadySaved){
            _saved.remove(pair);
          }else {
            _saved.add(pair);
          }
        });
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    //final WordPair wordPair = new WordPair.random();
    //return new Text(wordPair.asPascalCase);
    return new Scaffold(
      appBar: new AppBar(
        title: new Text("Scaffold title: Startup Name Generator"),
        actions: <Widget>[
          //在 Flutter 中，导航器管理应用程序的路由栈。
          // 将路由推入（push）到导航器的栈中，将会显示更新为该路由页面。
          // 从导航器的栈中弹出（pop）路由，将显示返回到前一个路由。
          new IconButton(
              icon: const Icon(Icons.list),
              onPressed: _pushSaved
          ),
        ],
      ),
      body: _buildSuggestions(),
    );
  }

  void _pushSaved(){
    Navigator.of(context).push(
      // ignore: expected_token
      new MaterialPageRoute<void>(builder: (BuildContext context) {
        final Iterable<ListTile> tiles = _saved.map(
            (WordPair pair){
              return new ListTile(title: new Text(
                pair.asPascalCase,
                style: _biggerFont,
              ),
              );
            }
        );
        final List<Widget> divided = ListTile.divideTiles(
          context: context,
          tiles: tiles
        ).toList();

        return new Scaffold(
          appBar: new AppBar(
            title: const Text("Saved Suggestions"),
          ),
          body: new ListView(children: divided),
        );
      },
      ),
    );
  }
}




