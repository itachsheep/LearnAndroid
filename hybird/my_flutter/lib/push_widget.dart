import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';

class PushWidget extends StatefulWidget {
  @override
  _PushWidgetState createState() => _PushWidgetState();
}


class _PushWidgetState extends State<PushWidget> {
  VoidCallback _backPressedListenerUnsub;

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
  }

  @override
  void didChangeDependencies() {
    // TODO: implement didChangeDependencies
    super.didChangeDependencies();
  }

  @override
  void dispose() {
    // TODO: implement dispose
    super.dispose();
    _backPressedListenerUnsub?.call();
  }

  @override
  Widget build(BuildContext context) {
    //return FlutterRouteWidget(message:"Pushed Widget");
    return Scaffold(
      appBar: AppBar(
        title: Text('push widget'),
      ),
      body: Center(
        child: RaisedButton(
          child: Text('Open first route'),
          onPressed: () {

           /* FlutterBoost.singleton.openPage("first", {}, animated: true, resultHandler:(String key , Map<dynamic,dynamic> result){
              print("did recieve second route result $key $result");
            });*/

            FlutterBoost.singleton.openPage("flutterbus://first", {
              "query": {"aaa": "bbb"}
            });
          },
        ),
      ),
    );
  }
}