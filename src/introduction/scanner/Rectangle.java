package introduction.scanner;

import java.util.Scanner;

public class Rectangle {

    public static void main(String[] args) {
        //create a program that calculates the area of a rectangle from user input
        //create an object of scanner class
        Scanner input = new Scanner(System.in);
        //prompt user to enter the first value-  length
        System.out.println("Welcome to our first toy cli project");
        System.out.println("=".repeat(26));
        System.out.println("Enter the length: ");
        //use the nextInt() method to read in
        int length = input.nextInt();
        //ask for the  width
        System.out.println("Enter the width: ");
        //use the nextInt to read the width
        int width = input.nextInt();
        //calc area = l * w
        int area = length * width;
        //output
        System.out.println("You entered " + length + " and " + width);
        System.out.println("The area of the rectangle is " + area);

        input.close();
    }
}
