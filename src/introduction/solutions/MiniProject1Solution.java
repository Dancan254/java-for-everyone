package introduction.solutions;

import java.util.Scanner;

/**
 * MiniProject1Solution.java
 *
 * Solution for Mini-Project 1: Unit Converter.
 * Converts user-supplied measurements across distance, temperature, and mass units.
 */
public class MiniProject1Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ----- Distance -----
        System.out.print("Enter a distance in metres: ");
        double metres = scanner.nextDouble();

        System.out.println("\n=== Distance ===");
        System.out.println("Metres:      " + String.format("%.3f", metres));
        System.out.println("Kilometres:  " + String.format("%.3f", metres / 1000));
        System.out.println("Centimetres: " + String.format("%.3f", metres * 100));
        System.out.println("Millimetres: " + String.format("%.3f", metres * 1000));
        System.out.println("Miles:       " + String.format("%.3f", metres / 1609.344));
        System.out.println("Feet:        " + String.format("%.3f", metres * 3.28084));
        System.out.println("Inches:      " + String.format("%.3f", metres * 39.3701));

        // ----- Temperature -----
        System.out.print("\nEnter a temperature in Celsius: ");
        double celsius = scanner.nextDouble();

        double fahrenheit = (celsius * 9.0 / 5.0) + 32;
        double kelvin     = celsius + 273.15;

        System.out.println("\n=== Temperature ===");
        System.out.println("Celsius:     " + String.format("%.3f", celsius));
        System.out.println("Fahrenheit:  " + String.format("%.3f", fahrenheit));
        System.out.println("Kelvin:      " + String.format("%.3f", kelvin));

        // ----- Mass -----
        System.out.print("\nEnter a mass in kilograms: ");
        double kg = scanner.nextDouble();

        System.out.println("\n=== Mass ===");
        System.out.println("Kilograms:   " + String.format("%.3f", kg));
        System.out.println("Grams:       " + String.format("%.3f", kg * 1000));
        System.out.println("Pounds:      " + String.format("%.3f", kg * 2.20462));
        System.out.println("Ounces:      " + String.format("%.3f", kg * 35.274));

        scanner.close();
    }
}
