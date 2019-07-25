import 'package:flutter/material.dart';

import 'package:flutter/services.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'simple_page_widgets.dart';
import 'flutter_route_page.dart';
import 'my_route.dart';
import 'first_route.dart';
import 'WigetDemo.dart';

/*void main() {
  runApp(MyApp());
}*/

//void main() => runApp(_widgetForRoute(window.defaultRouteName));
void main() => runApp(_widgetForRoute());//"first"

final String channelName = "com.flutterbus/demo";

Future<Null> jumpToNativePage() async {
  MethodChannel methodChannel = MethodChannel(channelName);
  await methodChannel.invokeMethod("gotoNativePage");
}

Widget _widgetForRoute() {
  return new MaterialApp(
      title: "taowei",
      home: new MyDemo(),
  );
}
class MyDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // Material 是UI呈现的“一张纸”
    return new Material(
      // Column is 垂直方向的线性布局.
      child: new Column(
        children: <Widget>[
          new AppBarDemo(
            title: new Text(
              'Example title',
              style: Theme.of(context).primaryTextTheme.title,
            ),
          ),
          new Expanded(
            child: new Center(
              child: RaisedButton(
                child:Text("不用flutter boost,混合开发"),
                onPressed: () {
                  jumpToNativePage();
                },
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();

    FlutterBoost.singleton.registerPageBuilders({
      'first': (pageName, params, _) => FirstRouteWidget(),
      'myFlutterPage': (pageName, params, _){
        print("first params:$params");
        return MyRouteWidget();
      },
      'second': (pageName, params, _) => SecondRouteWidget(),
      'tab': (pageName, params, _) => TabRouteWidget(),
      'flutterFragment': (pageName, params, _) => FragmentRouteWidget(params),

      ///可以在native层通过 getContainerParams 来传递参数
      'flutterPage': (pageName, params, _) {
        print("flutterPage params:$params");

        return FlutterRouteWidget();
      },
    });

    FlutterBoost.handleOnStartPage();

  }

//  Map<String, WidgetBuilder> routes = {
////    "second": (BuildContext context) =>
////        SecondRouteWidget(),
////  };

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Boost example',
//        builder: FlutterBoost.init(postPush: _onRoutePushed),
        builder: FlutterBoost.init(),
//        routes: routes,
        home: Container());
  }

  void _onRoutePushed(
      String pageName, String uniqueId, Map params, Route route, Future _) {

  }
}