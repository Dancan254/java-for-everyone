package introduction.solutions;

/**
 * Exercise5Solution.java
 *
 * Solutions for Exercise Set 5: Scanner Input.
 * Covers: reading strings, ints, and doubles from the user; BMI calculation;
 * and reading multiple values on one line.
 *
 * Note: This solution uses hardcoded values to keep it runnable without
 * interactive input. The Scanner prompts are shown as comments so you can
 * adapt this to a live Scanner session.
 */
public class Exercise5Solution {

    public static void main(String[] args) {
        exercise5_1();
        exercise5_2();
        exercise5_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 5.1 — Reading Name, Age, and Height
    // -------------------------------------------------------------------------

    static void exercise5_1() {
        System.out.println("=== Exercise 5.1: Name, Age, and Height ===");

        // Interactive version (uncomment to use with real Scanner input):
        // Scanner scanner = new Scanner(System.in);
        // System.out.print("Enter your full name: ");
        // String name = scanner.nextLine();
        // System.out.print("Enter your age: ");
        // int age = scanner.nextInt();
        // scanner.nextLine(); // consume trailing newline after nextInt()
        // System.out.print("Enter your height in meters: ");
        // double height = scanner.nextDouble();
        // scanner.close();

        // Hardcoded values for demonstration:
        String name   = "Jane Doe";
        int    age    = 28;
        double height = 1.72;

        System.out.println("Name:   " + name);
        System.out.printf("Age:    %d%n", age);
        System.out.printf("Height: %.2f m%n", height);
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 5.2 — BMI Calculator
    // -------------------------------------------------------------------------

    static void exercise5_2() {
        System.out.println("=== Exercise 5.2: BMI Calculator ===");

        // Hardcoded values — replace with Scanner reads in an interactive program:
        double weight = 68.0;   // kg
        double height = 1.72;   // m

        double bmi = weight / (height * height);

        String category;
        if (bmi < 18.5) {
            category = "Underweight";
        } else if (bmi < 25.0) {
            category = "Normal weight";
        } else if (bmi < 30.0) {
            category = "Overweight";
        } else {
            category = "Obese";
        }

        System.out.printf("Weight: %.1f kg%n", weight);
        System.out.printf("Height: %.2f m%n",  height);
        System.out.printf("BMI:    %.1f%n",    bmi);
        System.out.println("Category: " + category);
        // Output:
        // Weight: 68.0 kg
        // Height: 1.72 m
        // BMI:    23.0
        // Category: Normal weight
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 5.3 — Reading Multiple Values on One Line
    // -------------------------------------------------------------------------

    static void exercise5_3() {
        System.out.println("=== Exercise 5.3: Multiple Values on One Line ===");

        // Hardcoded to simulate: "Enter three integers separated by spaces: 4 7 13"
        int x = 4, y = 7, z = 13;

        int    sum     = x + y + z;
        long   product = (long) x * y * z;
        double average = sum / 3.0;

        System.out.println("Integers:  " + x + " " + y + " " + z);
        System.out.println("Sum:       " + sum);
        System.out.println("Product:   " + product);
        System.out.printf("Average:   %.2f%n", average);
        // Output:
        // Sum: 24, Product: 364, Average: 8.00

        System.out.println();

        // Hardcoded to simulate: "Enter two words: apple banana"
        String word1 = "apple";
        String word2 = "banana";

        System.out.println("Word 1:  " + word1);
        System.out.println("Word 2:  " + word2);
        System.out.println("Combined length: " + (word1.length() + word2.length()));

        int cmp = word1.compareTo(word2);
        if (cmp < 0) {
            System.out.println("Alphabetically first: " + word1);
        } else if (cmp > 0) {
            System.out.println("Alphabetically first: " + word2);
        } else {
            System.out.println("The words are equal.");
        }
        // Output: Alphabetically first: apple
        System.out.println();
    }
}
