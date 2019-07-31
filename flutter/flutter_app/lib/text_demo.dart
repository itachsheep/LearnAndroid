import 'package:flutter/material.dart';

class TextDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Material(
      child: new Column(
        children: <Widget>[
          Text(
            'Flutter allows you to build beautiful native apps on iOS and Android from a single codebase.',
            textAlign: TextAlign.center,
            textDirection: TextDirection.ltr,// 文本对齐方式
          ),

          RichText(
            textDirection: TextDirection.ltr,
            text: TextSpan(
              text: 'RichText',
              style: TextStyle(color: Colors.black),
              children: <TextSpan>[
                TextSpan(
                  text: ' allows you',
                  style: TextStyle(
                    color: Colors.green,
                    decoration: TextDecoration.underline,
                    decorationStyle: TextDecorationStyle.solid,
                  ),
                ),
                TextSpan(
                    text: ' to build beautiful native apps',
                    style: TextStyle(
                      fontSize: 18,
                    )
                ),
                TextSpan(
                    text: ' on iOS and Android',
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                    )
                ),
                TextSpan(
                    text: ' from a single codebase.',
                    style: TextStyle(
                      shadows: [Shadow(color: Colors.black38, offset: Offset(3, 3))],
                    )
                ),
              ],
            ),
          ),

          Icon(
            Icons.adb,
            textDirection:TextDirection.ltr,
          ),
          Icon(
            Icons.adb,
            textDirection:TextDirection.ltr,
            size: 50, //icon大小
          ),
          Icon(
            Icons.adb,
            textDirection:TextDirection.ltr,
            color: Colors.red, //icon颜色
          ),
          RaisedButton(
            onPressed: null, // onPressed为null视为不可点击
            disabledTextColor: Colors.grey, // 不可点击的文本颜色
            disabledColor: Colors.blue, // 不可点击的按钮颜色
            disabledElevation: 5, // 不可点击时图层高度
            child: Text('Disabled Button'),

          ),
          RaisedButton(
            onPressed: () { // onPressed不为null视为可点击
              print('You click the button');
            },
            textColor: Colors.white, // 文本颜色
            color: Colors.blueAccent, // 按钮颜色
            highlightColor: Colors.lightBlue, //点击按钮后高亮的颜色
            elevation: 5, // 按钮图层高度
            highlightElevation: 8, // 点击按钮高亮后的图层高度
            animationDuration: Duration(milliseconds: 300), // 点击按钮后过渡动画时间
            child: Text('ClickButton'),
          ),


        ],
      ),

    );
  }
}

class raiseBtDemo extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Container(
          margin:  EdgeInsets.only(top: 50.0),
          child:new Column(
            children: <Widget>[
              RaisedButton(
                onPressed: null, // onPressed为null视为不可点击
                disabledTextColor: Colors.grey, // 不可点击的文本颜色
                disabledColor: Colors.blue, // 不可点击的按钮颜色
                disabledElevation: 5, // 不可点击时图层高度
                child: Text('Disabled Button'),
              ),
              RaisedButton(
                onPressed: () { // onPressed不为null视为可点击
                  print('You click the button');
                },
                textColor: Colors.white, // 文本颜色
                color: Colors.blueAccent, // 按钮颜色
                highlightColor: Colors.lightBlue, //点击按钮后高亮的颜色
                elevation: 5, // 按钮图层高度
                highlightElevation: 8, // 点击按钮高亮后的图层高度
                animationDuration: Duration(milliseconds: 300), // 点击按钮后过渡动画时间
                child: Text('ClickButton'),
              ),
            ],
          ),
        ),
      ),
    );
  }

}