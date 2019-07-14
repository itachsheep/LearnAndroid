
void main() {
  var boy = new Boy('tao', 'wei');
  boy.say();
  /*var person = new Person(
    firstName: 'tao',
    lastName: 'wei',
  );
  person.say();*/
}

class Point {
}

class Person {
 final String firstName, lastName;
  /*Person(this.firstName,this.lastName){
    print('in person');
  }

  Person.fromJson(Map data) {
    print('in Person fromJson');
  }*/
  Person({this.firstName,this.lastName});

 void say(){
   print("person : $firstName $lastName");
 }
}

class Boy extends Person {
  /*Boy(String f,String l) : super(f,l){
    print('in boy');
  }*/
  /*Employee.fromJson(Map data) : super.fromJson(data){
    print('in Employee');
  }*/

  Boy(String f,String l):super(
    firstName:f,
    lastName:l,
  );

  void say(){
    print("boy : $firstName $lastName");
  }
}

