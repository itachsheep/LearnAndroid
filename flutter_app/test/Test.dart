class Bicycle {
  int cadence;
  int _speed = 1000;
  int gear;

  Bicycle(this.cadence, this.gear);

  int get speed => _speed;
  void applyBrake(int decrement) {
    _speed -= decrement;
  }

  void speedUp(int increment) {
    _speed += increment;
  }
  @override
  String toString() => 'Bicycle: $_speed mph';
}
void for_test(){
  for(int j = 0; j < 6; j++){
    print('my test ${j}');
  }
}
void main() {
  //for_test()
  var bike = new Bicycle(2, 1);
  print(bike);
//  print('this --> '+$bike.speed);
}