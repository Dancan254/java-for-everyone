package introduction.solutions;

/**
 * Exercise2Solution.java
 *
 * Solutions for Exercise Set 2: Arithmetic and Assignment Operators.
 * Covers: temperature conversion, simple interest calculation,
 * and area/perimeter of a circle using Math.PI.
 */
public class Exercise2Solution {

    public static void main(String[] args) {
        exercise2_1();
        exercise2_2();
        exercise2_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 2.1 — Temperature Converter
    // -------------------------------------------------------------------------

    static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32;
    }

    static void exercise2_1() {
        System.out.println("=== Exercise 2.1: Temperature Converter ===");

        double[] celsiusValues = {0, 100, -40, 37};

        for (double c : celsiusValues) {
            double f = celsiusToFahrenheit(c);
            System.out.printf("%.1f C = %.1f F%n", c, f);
        }
        // Output:
        // 0.0 C = 32.0 F
        // 100.0 C = 212.0 F
        // -40.0 C = -40.0 F
        // 37.0 C = 98.6 F
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 2.2 — Simple Interest
    // -------------------------------------------------------------------------

    static double simpleInterest(double principal, double rate, int years) {
        // I = P * R * T  (rate expressed as a decimal, e.g. 5% -> 0.05)
        return principal * rate * years;
    }

    static void exercise2_2() {
        System.out.println("=== Exercise 2.2: Simple Interest ===");

        printInterest(1000.00, 0.05, 3);
        printInterest(5000.00, 0.08, 10);
        printInterest(250.00,  0.03, 2);
        System.out.println();
    }

    static void printInterest(double principal, double rate, int years) {
        double interest = simpleInterest(principal, rate, years);
        System.out.printf("Principal: $%.2f | Rate: %.0f%% | Years: %d | Interest: $%.2f%n",
                principal, rate * 100, years, interest);
    }

    // -------------------------------------------------------------------------
    // Exercise 2.3 — Area and Perimeter of a Circle
    // -------------------------------------------------------------------------

    static double circleArea(double radius) {
        return Math.PI * radius * radius;
    }

    static double circlePerimeter(double radius) {
        return 2 * Math.PI * radius;
    }

    static void exercise2_3() {
        System.out.println("=== Exercise 2.3: Circle Area and Perimeter ===");

        double[] radii = {5.0, 10.0, 2.5};

        for (double r : radii) {
            System.out.printf("Radius: %.1f | Area: %s | Perimeter: %s%n",
                    r,
                    String.format("%.2f", circleArea(r)),
                    String.format("%.2f", circlePerimeter(r)));
        }
        // Output:
        // Radius: 5.0  | Area: 78.54  | Perimeter: 31.42
        // Radius: 10.0 | Area: 314.16 | Perimeter: 62.83
        // Radius: 2.5  | Area: 19.63  | Perimeter: 15.71
        System.out.println();
    }
}
