import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'simple_page_widgets.dart';

void main() => runApp(MyApp());
//void main() => runApp(_widgetForRoute(window.defaultRouteName));

class MyApp extends StatefulWidget  {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new _MyAppState();
  }
}
class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    ///register page widget builders,the key is pageName
    FlutterBoost.singleton.registerPageBuilders({
      'sample://firstPage': (pageName, params, _) => FirstRouteWidget(),
      'sample://secondPage': (pageName, params, _) => SecondRouteWidget(),
    });

    ///query current top page and load it
    FlutterBoost.handleOnStartPage();
  }
  @override
  Widget build(BuildContext context) => MaterialApp (
    title: 'Flutter Boost example',
    builder: FlutterBoost.init(), ///init container manager
    home: Container());
}
