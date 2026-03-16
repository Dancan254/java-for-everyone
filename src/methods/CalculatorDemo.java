package methods;

import java.util.Scanner;

public class CalculatorDemo {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Simple Calculator!");

        while (true) {
            displayMenu();
            int choice = getChoice();

            if (choice == 0) {
                System.out.println("Thank you for using Simple Calculator!");
                scanner.close();
                break;
            }

            performOperation(choice);
        }
    }

    private static void displayMenu() {
        System.out.println("\n---------------------------");
        System.out.println("# Calculator Menu");
        System.out.println("---------------------------");
        System.out.println("1. Add two numbers");
        System.out.println("2. Subtract two numbers");
        System.out.println("3. Multiply two numbers");
        System.out.println("4. Divide two numbers");
        System.out.println("5. Create multiplication table");
        System.out.println("0. Exit");
        System.out.print("> Enter your option: ");
    }

    private static int getChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void performOperation(int choice) {
        switch (choice) {
            case 1:
                add();
                break;
            case 2:
                subtract();
                break;
            case 3:
                multiply();
                break;
            case 4:
                divide();
                break;
            case 5:
                multiplicationTable();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }

    }

    private static double getUserInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    private static void add() {
        double a = getUserInput("Enter first number: ");
        double b = getUserInput("Enter second number: ");
        double result = a + b;
        System.out.printf("Result: %.2f + %.2f = %.2f%n", a, b, result);
    }

    private static void subtract() {
        double a = getUserInput("Enter first number: ");
        double b = getUserInput("Enter second number: ");
        double result = a - b;
        System.out.printf("Result: %.2f - %.2f = %.2f%n", a, b, result);
    }

    private static void multiply() {
        double a = getUserInput("Enter first number: ");
        double b = getUserInput("Enter second number: ");
        double result = a * b;
        System.out.printf("Result: %.2f * %.2f = %.2f%n", a, b, result);
    }

    private static void divide() {
        double a = getUserInput("Enter first number: ");
        double b = getUserInput("Enter second number: ");

        if (b == 0) {
            System.out.println("Error: Cannot divide by zero!");
            return;
        }

        double result = a / b;
        System.out.printf("Result: %.2f / %.2f = %.2f%n", a, b, result);
    }

    private static void multiplicationTable() {
        double number = getUserInput("Enter a number to multiply: ");
        int rows = (int) getUserInput("Enter number of rows: ");

        System.out.printf("%nMultiplication Table for %.0f:%n", number);
        for (int i = 1; i <= rows; i++) {
            System.out.printf("%.0f x %d = %.0f%n", number, i, number * i);
        }
    }
}
