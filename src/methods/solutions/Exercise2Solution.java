package methods.solutions;

/**
 * Exercise2Solution.java
 *
 * Solutions for Exercise Set 2: Method Overloading.
 * Covers: overloaded method signatures, compile-time dispatch, area formulas,
 * polymorphic display methods, and bidirectional temperature conversion.
 *
 * Java resolves overloaded calls at compile time based on the static types of
 * the arguments — the compiler picks the most specific matching signature.
 */
public class Exercise2Solution {

    public static void main(String[] args) {

        // === Exercise 2.1: AreaCalculator ===
        System.out.println("=== Exercise 2.1: AreaCalculator ===");

        AreaCalculator ac = new AreaCalculator();

        // Circle with radius 5 — area = pi * r^2
        System.out.printf("Circle  r=5:               area = %.4f%n",
                ac.calculateArea(5.0));
        // Output: Circle  r=5:               area = 78.5398

        // Rectangle 4 x 6 — area = length * width
        System.out.printf("Rectangle 4x6:             area = %.4f%n",
                ac.calculateArea(4.0, 6.0));
        // Output: Rectangle 4x6:             area = 24.0000

        // Triangle base=6, height=4 — area = 0.5 * base * height
        System.out.printf("Triangle base=6, h=4:      area = %.4f%n",
                ac.calculateArea(6.0, 4.0, true));
        // Output: Triangle base=6, h=4:      area = 12.0000

        // === Exercise 2.2: Printer ===
        System.out.println("\n=== Exercise 2.2: Printer ===");

        Printer p = new Printer();
        p.display(42);
        // Output: Integer: 42
        p.display(3.14);
        // Output: Double: 3.14
        p.display("hello");
        // Output: String: hello
        p.display(7, 13);
        // Output: Two integers: 7, 13

        // === Exercise 2.3: Temperature Converter ===
        System.out.println("\n=== Exercise 2.3: Temperature Converter ===");

        TemperatureConverter tc = new TemperatureConverter();

        // Single-arg overload always treats input as Celsius.
        System.out.printf("convert(100.0)        = %.2f F%n", tc.convert(100.0));
        // Output: convert(100.0)        = 212.00 F

        // Two-arg overload reads the unit string to decide direction.
        System.out.printf("convert(0.0,  \"C\")    = %.2f F%n", tc.convert(0.0, "C"));
        // Output: convert(0.0,  "C")    = 32.00 F

        System.out.printf("convert(212.0,\"F\")    = %.2f C%n", tc.convert(212.0, "F"));
        // Output: convert(212.0,"F")    = 100.00 C
    }

    // -------------------------------------------------------------------------
    // Inner classes
    // -------------------------------------------------------------------------

    /**
     * Demonstrates method overloading for area calculations.
     * Each overload has a distinct parameter list; the compiler dispatches to
     * the correct one based solely on argument count and types.
     */
    static class AreaCalculator {

        // Circle: A = pi * r^2
        double calculateArea(double radius) {
            return Math.PI * radius * radius;
        }

        // Rectangle: A = l * w
        // The two-double signature is distinct from the circle signature
        // because it has two parameters instead of one.
        double calculateArea(double length, double width) {
            return length * width;
        }

        // Triangle: A = 0.5 * base * height
        // The boolean third parameter makes this signature unique.
        // isTriangle is required by the exercise spec to differentiate from
        // other potential two-double shapes; its value is not used in the formula.
        double calculateArea(double base, double height, boolean isTriangle) {
            return 0.5 * base * height;
        }
    }

    /**
     * Overloaded display methods that print a type label before the value.
     * Java would promote an int to double if no int overload existed, so the
     * int overload must be declared explicitly to prevent that promotion.
     */
    static class Printer {

        void display(int num) {
            System.out.println("Integer: " + num);
        }

        void display(double num) {
            System.out.println("Double: " + num);
        }

        void display(String text) {
            System.out.println("String: " + text);
        }

        // Two-int overload is chosen when exactly two int arguments are passed.
        void display(int num1, int num2) {
            System.out.println("Two integers: " + num1 + ", " + num2);
        }
    }

    /**
     * Temperature conversion with two overloads.
     * The single-argument form always converts Celsius to Fahrenheit.
     * The two-argument form reads the unit tag to decide direction.
     */
    static class TemperatureConverter {

        // Celsius to Fahrenheit: F = C * 9/5 + 32
        double convert(double celsius) {
            return celsius * 9.0 / 5.0 + 32;
        }

        // Direction determined by the unit string: "C" -> F, "F" -> C.
        double convert(double temp, String unit) {
            if (unit.equalsIgnoreCase("C")) {
                return temp * 9.0 / 5.0 + 32; // Celsius to Fahrenheit
            } else {
                return (temp - 32) * 5.0 / 9.0; // Fahrenheit to Celsius
            }
        }
    }
}
