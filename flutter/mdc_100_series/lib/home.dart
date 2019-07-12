// Copyright 2018-present the Flutter authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import 'package:flutter/material.dart';

import 'package:intl/intl.dart';
import 'model/product.dart';
import 'model/products_repository.dart';

class HomePage extends StatelessWidget {
  // TODO: Make a collection of cards (102)
  // TODO: Add a variable for Category (104)
  @override
  Widget build(BuildContext context) {
    // TODO: Return an AsymmetricView (104)
    // TODO: Pass Category variable to AsymmetricView (104)
    return Scaffold(
      // TODO: Add app bar (102)
      appBar: AppBar(
        //为 AppBar 的 leading 字段设置一个 IconButton 。
        // （需要将其放在 title 字段前面，因为它们遵循从头至尾 leading-to-trailing 的先后顺序）
        leading: IconButton(
            icon: Icon(
              Icons.menu,
              semanticLabel: 'menu',
            ),
            onPressed: (){
              print('Menu Button');
            }
        ),
        title: const Text("SHRINE"),
        // TODO: Add trailing buttons (102)
        actions: <Widget>[
          IconButton(
            icon: Icon(
              Icons.search,
              semanticLabel: 'search',
            ),
            onPressed: (){
              print('Search Button');
            },
          ),

          IconButton(
            icon: Icon(
              Icons.tune,
              semanticLabel: 'filter',
            ),
            onPressed: (){
              print('Filter Button');
            },
          )
        ],
      ),
      // TODO: Add a grid view (102)
      body:GridView.count(
        crossAxisCount: 2,
        padding: EdgeInsets.all(16.0),
        childAspectRatio: 8.0 / 9.0,
        children: _buildGridCards(context)
      ),
      // TODO: Set resizeToAvoidBottomInset (101)
    );
  }

  List<Card> _buildGridCards(BuildContext context){
    List<Product> products = ProductsRepository.loadProducts(Category.all);
    if(products == null && products.isEmpty){
      return const <Card>[];
    }

    final ThemeData theme = Theme.of(context);
    final NumberFormat formatter = NumberFormat.simpleCurrency(
      locale: Localizations.localeOf(context).toString());
    return products.map((products) {
      return Card(
          child: Column(
            //"将文本向头部对齐"
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              AspectRatio(//图片形状由 AspectRatio 决定，而不是提供的图片本身的形状
                aspectRatio: 18.0 / 11.0,
                child: Image.asset(
                  products.assetName,
                  package: products.assetPackage,
                    fit: BoxFit.fitWidth),
              ),
              Padding(//Padding 则使得文本从边缘向中间移动一点
                padding: EdgeInsets.fromLTRB(16.0, 12.0, 16.0, 8.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: <Widget>[
                    //两个 Text widgets 上下放置，用 SizedBox 来表示它们之间有 8 points 的距离。
                    // 我们在 Padding 之中创建了一个 Column 来放置它们。
                    Text(products.name),
                    SizedBox(height: 8.0),
                    Text(
                      formatter.format(products.price),
                      style: theme.textTheme.body2,
                    ),
                  ],
                ),
              ),
            ],
          )
      );
    }).toList();
    }
}
