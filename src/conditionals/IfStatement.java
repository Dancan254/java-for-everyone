package conditionals;

public class IfStatement {
    public static void main(String[] args) {

        int marks = 9;

        if(marks >= 90){
            System.out.println("You got a A");
            System.out.println("You are bright");
        }

        if(marks < 60)
            System.out.println("You failed");

        int number = 10;

        if (number > 0) {
            System.out.println(number + " is positive");
        }

        if (number % 2 == 0) {
            System.out.println(number + " is even");
        }

        System.out.println("Program continues...");
    }
}
