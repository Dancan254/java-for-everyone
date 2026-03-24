package oop;

// Child class (subclass)
public class Dog extends Animal {
    private String breed;

    public Dog(String name, int age, String breed) {
        super(name, age); // Calls the parent constructor
        this.breed = breed;
    }

    // Dog-specific method
    public void bark() {
        System.out.println(getName() + " says: Woof!");
    }

    @Override
    public void sleep() {
        System.out.println("Dog is sleeping");
    }

    @Override
    public void eat() {
        //super.eat(); // Call the parent's eat() method first
        System.out.println(getName() + " wags tail while eating.");
    }

    public String getBreed() {
        return breed;
    }
}
