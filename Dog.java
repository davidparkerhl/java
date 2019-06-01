public class Dog
{
  int age;
  String name;
  void bark()
  {
    System.out.println("wang");
  }
  void hungry()
  {
    System.out.println("feed me");
  }

  public static void main(String[] args)
  {
    Dog d=new Dog();
    d.bark();
  }
}
