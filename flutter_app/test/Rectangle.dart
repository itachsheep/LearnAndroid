
String scream(int length) => "A${'a' * length}h!";

void main(){
  final values = [1, 2, 3, 5, 10, 50];
  /*for (var length in values) {
    print(scream(length));
  }*/
  //函数式编程，链式编程
  //values.map(scream).forEach(print);
  values.skip(1).take(3).map(scream).forEach(print);
}