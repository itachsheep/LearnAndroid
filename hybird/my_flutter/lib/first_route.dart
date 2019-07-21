import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';


class FirstRouteWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('first_route'),
      ),
      body: Center(
        child: RaisedButton(
          child: Text('Open second route'),
          onPressed: () {

            FlutterBoost.singleton.openPage("second", {}, animated: true, resultHandler:(String key , Map<dynamic,dynamic> result){
              print("did recieve second route result $key $result");
            });

          },
        ),
      ),
    );
  }
}

