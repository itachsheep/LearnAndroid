
void main(){
  var boy = new Boy('tao', 'wei');
}

class Point {
}

class Person {
  String firstName, lastName;
  Person(this.firstName,this.lastName){
    print('in person');
  }

  Person.fromJson(Map data) {
    print('in Person fromJson');
  }
}

class Boy extends Person {
  Boy(String f,String l) : super(f,l){
    print('in boy');
  }
  /*Employee.fromJson(Map data) : super.fromJson(data){
    print('in Employee');
  }*/
}

