package methods;

public class Person {

    private String name;
    public Person(String namep){
        this.name = namep;
    }
    public void setName(String name) { // Instance method
        this.name = name;
    }

    public String getName() { // Instance method
        return this.name;
    }

    public static String sayHi(){
        return "Hello World!";
    }
}
