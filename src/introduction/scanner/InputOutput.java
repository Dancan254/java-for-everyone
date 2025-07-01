package introduction.scanner;

import java.util.Scanner;

public class InputOutput {

    public static void main(String[] args) {
        //create an instance of the scanner class -> object
        Scanner input  = new Scanner(System.in);

        //System.out.println("What is your name?");
        // What is your name
        //-input comes in the next line - ln. creates a new line -> Ian
        System.out.print("HI, what is your name? ");
        //HI, what is your name? Ian - the input is done in the same line
        String name = input.next();
        System.out.println("Nice to meet you, " + name + ".");


        System.out.println("I can help you add two numbers: ");
        System.out.println("Enter the first number: ");
        int num1 = input.nextInt();

        System.out.println("Enter the second number: ");
        int num2 = input.nextInt();

        int sum = num1 + num2;
        System.out.println("You entered " + num1 + " and " + num2 + ", so the sum is " + sum);

        System.out.println("Trying some fishy stuff");
        System.out.println("Enter a number: ");
        int fishy = input.nextInt();
        input.nextLine();
        System.out.println("Enter a string");
        String fishyString = input.nextLine();

        System.out.println("Fishy number: " + fishy);
        System.out.println("Fishy string: " + fishyString);

        System.out.println("What is your name: ");
        String name2 = input.nextLine();
        System.out.println("Nice to meet you, " + name2 + ".");
        input.close();
    }
}
