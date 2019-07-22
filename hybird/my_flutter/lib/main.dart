import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'simple_page_widgets.dart';
import 'flutter_route_page.dart';
import 'my_route.dart';
import 'first_route.dart';

/*void main() {
  runApp(MyApp());
}*/

void main() => runApp(_widgetForRoute(window.defaultRouteName));

final String channelName = "com.flutterbus/demo";

Future<Null> jumpToNativePage() async {
  MethodChannel methodChannel = MethodChannel(channelName);
  await methodChannel.invokeMethod("gotoNativePage");
}

Widget _widgetForRoute(String route) {
  switch (route) {
    case 'first':
      return new Scaffold(
        appBar: AppBar(
          title: const Text("this is hybird1 flutter page"),
        ),
        body: Center(
          child: RaisedButton(
            child:Text("混合开发一 flutter 界面"),
            onPressed: () {
              jumpToNativePage();
            },
          ),
        ),
      );
    case 'second':
      return MyApp();
    default:
      return Center(
        child: Text('Unknown route: $route', textDirection: TextDirection.ltr),
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