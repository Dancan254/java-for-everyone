package methods;

public class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }
    public void makeSound(){
        System.out.println("make sound");
    }

    //how to access the name
    //you need a getter
    public String getName(){
        return this.name;
    }
}
