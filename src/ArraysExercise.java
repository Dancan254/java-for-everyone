import java.util.Arrays;

public class ArraysExercise {

    public static void main(String[] args) {
        // datatype[] variableName = new datatype[size];
        // datatype[] variableName = {value1, value2, value3}; -> 
        // access elements variableName[index]
        //assign values => variableName[index] = value;
        
        String[] fruits = {"Mango", "Orange", "Apple"};
        /// first element
        String firstFruit = fruits[0];
        System.out.println(firstFruit);
        
        //length 
        int length = fruits.length;
        System.out.println("The array has " + length + " elements");
        
        int[] numbers = new int[5];
        numbers[0] = 6;
        numbers[1] = 10;
        numbers[2] = 15;
        numbers[3] = 20;
        numbers[4] = 25;
        System.out.println(Arrays.toString(numbers));

        for (int number : numbers){
            int result = (int)Math.pow(number, 2);
            System.out.println(number + " power 2 "+result);
        }
        //numbers -> loop through it
        // 0, 4 -> last index -> length -1
        System.out.println("For i loop");
        for (int i = 0; i < numbers.length; i++) {
            System.out.println(numbers[i]);
        }
        System.out.println("Enhanced for loop");
        for (int number : numbers){
            System.out.println(number);
        }
        Person person1 = new Person();
        person1.name = "John";
        person1.age = 25;
        Person person2 = new Person();
        person2.name = "Mary";
        person2.age = 23;

        Person[] people = new Person[3];
        people[0] = person1;
        people[1] = person2;

        System.out.println("People");

        System.out.println(Arrays.toString(people));

        int[] numbers2 = new int[5];
        System.out.println(Arrays.toString(numbers2));
    }
}

class Person{
    String name;
    int age;

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", age=" + age + '}';
    }
}
