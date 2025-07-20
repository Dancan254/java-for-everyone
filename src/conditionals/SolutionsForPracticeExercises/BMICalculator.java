package conditionals.SolutionsForPracticeExercises;

import java.util.Scanner;

public class BMICalculator {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Hey there, let's calculate your BMI:)");
        
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();

        double height = validInput(scanner,"Enter your height in metres: ",0.1,3.0);
        
        double mass = validInput(scanner,"Enter your mass in kilograms: ",1,300);

        double BMI = mass / (height * height);
        String result;

        if (BMI < 18.5) {
            result = "Underweight";
        }else if (BMI >= 18.5 && BMI <= 24.9 ) {
            result = "Normal weight";
        }else if (BMI >= 25 && BMI < 30) {
            result = "Overweight";
        }else if (BMI >= 30) {
            result = "Obese";
        }else{
            result = "Invalid BMI!";
        }

        System.out.println("=====BMI Calculator=====");
        System.out.println("Name: " + name);
        System.out.println("Mass: " + mass);
        System.out.println("Height: " + height);
        System.out.printf("BMI: %.2f\n",BMI);
        System.out.println("Result: " + result);

        scanner.close();
    }

    public static double validInput(Scanner scanner,String prompt, double min, double max){
        double input;
        while (true) {
            System.out.println(prompt);
            if (scanner.hasNextDouble()) {
                input = scanner.nextDouble();
                if (input >= min && input <= max) {
                    return input;
                }else{
                    System.out.println("Please enter a value between " + min + " and " + "max");
                }
            
            }else{
                System.out.println("Invalid input! Please enter a numeric value.");
                scanner.next();
            }
        }
    }

    
}