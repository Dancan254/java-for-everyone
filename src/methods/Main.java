package methods;

public class Main {
    public static void main(String[] args) {
        //Create an obj of peson class
        Person person = new Person("name");
        String name = person.getName();
        System.out.println("Name is: " + name);
        //setName
        person.setName("ian");

        System.out.println("Name is: " + person.getName());

        System.out.println(Person.sayHi());
        double squareroot = Math.sqrt(81);

        Animal animal = new Animal("Animal");
        animal.makeSound();
        String animalName = animal.getName();
        System.out.println("Animal name is: " + animalName);

        Animal animal2 = new Animal("Animal2");
        System.out.println("Animal name is: " + animal2.getName());

        Animal dog = new Dog("Dog");
        dog.makeSound();
    }
}
