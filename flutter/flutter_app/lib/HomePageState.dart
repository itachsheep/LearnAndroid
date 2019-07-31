import 'custom_combined_widget.dart';
import 'package:flutter/material.dart';

class HomePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new HomePageState();
  }
}

class HomePageState extends State<HomePage> {
  String tips = '这里是提示';

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        title: Text('自定义组合Widget'),
      ),
      body: Container(
        child: buildCombinedWidget(),
//        child: const Text("2222"),
      ),
    );
  }

  Widget buildCombinedWidget() {
    return Center(
      child: Column(
        children: <Widget>[
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: <Widget>[
              CustomCombinedWidget( //使用自定义Widget
                iconData: Icons.home,
                title: '首页',
                onTap: () {
                  setState(() {
                    this.tips = '点击了首页';
                  });
                },
              ),
              CustomCombinedWidget(
                iconData: Icons.list,
                title: '产品',
                onTap: () {
                  setState(() {
                    this.tips = '点击了产品';
                  });
                },
              ),
              CustomCombinedWidget(
                iconData: Icons.more_horiz,
                title: '更多',
                onTap: () {
                  setState(() {
                    this.tips = '点击了更多';
                  });
                },
              ),
            ],
          ),
          Padding(
            padding: EdgeInsets.only(top: 50),
            child: Text(
              this.tips,
              style: TextStyle(fontSize: 20, color: Colors.blue),
            ),
          ),
        ],
      ),
    );
  }
}