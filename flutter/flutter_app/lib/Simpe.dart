import 'package:flutter/material.dart';

class SimpleText extends Text {
  final TextDirection textDirection;
  SimpleText(String data,{this.textDirection}) : super(data) ;

  @override
  Widget build(BuildContext context) {
    var build = super.build(context);

    return build;
  }
}