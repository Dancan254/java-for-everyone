package oop;

public class Main {
    public static void main(String[] args) {
        Animal animal = new Dog("Spot", 3, "Labrador");// implicit, upcasting
        //dog.bark();
        animal.eat();
        animal.sleep();

        Dog dog =(Dog) animal; //downcasting
        dog.bark();

        Animal animal2 = new Animal("animal", 34);
        Dog dog2 = (Dog) animal2;
        dog2.bark();

        //instance of-  beore downcasting
        var animal3 = new Dog("Spot", 3, "Labrador");
        if(animal3 instanceof Dog){
            var dog3 = (Dog) animal3;
            dog3.bark();
        }
        treat(new Dog("dfjdhf", 56, "ina"));
    }

    public static void treat(Animal animal) {
        if (animal instanceof Dog dog && dog.getAge() >= 5) {
            System.out.println("Treating dog: " + dog.getBreed());
            dog.bark();
        } else {
            System.out.println("Treating generic animal: " + animal.getName());
        }
    }

    public static String treat2(Animal animal) {
        return switch(animal){
            case Dog dog when dog.getAge()>=5 -> "Treating dog: " + dog.getBreed();
            case Animal animal1 -> "Treating generic animal: " + animal1.getName();
        };
    }
}
