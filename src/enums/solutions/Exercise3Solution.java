package enums.solutions;

/**
 * Exercise3Solution.java
 *
 * Solutions for Exercise Set 3: Enum Methods.
 * Covers: abstract methods per constant (strategy pattern),
 * and concrete shared methods on the enum body.
 */
public class Exercise3Solution {

    // Each constant provides its own implementation of apply().
    // This keeps all behavior for each operation co-located with the constant.
    enum MathOperation {
        PLUS {
            @Override
            public double apply(double x, double y) {
                return x + y;
            }
        },
        MINUS {
            @Override
            public double apply(double x, double y) {
                return x - y;
            }
        },
        MULTIPLY {
            @Override
            public double apply(double x, double y) {
                return x * y;
            }
        },
        DIVIDE {
            @Override
            public double apply(double x, double y) {
                if (y == 0) throw new ArithmeticException("Division by zero");
                return x / y;
            }
        };

        // Every constant must implement this method.
        public abstract double apply(double x, double y);

        // A shared concrete method available on every constant.
        public String symbol() {
            return switch (this) {
                case PLUS     -> "+";
                case MINUS    -> "-";
                case MULTIPLY -> "*";
                case DIVIDE   -> "/";
            };
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Exercise 3.1: Abstract Methods per Constant ===");

        double x = 10.0;
        double y = 4.0;

        for (MathOperation op : MathOperation.values()) {
            System.out.printf("%.1f %s %.1f = %.1f%n", x, op.symbol(), y, op.apply(x, y));
        }
        // Output:
        // 10.0 + 4.0 = 14.0
        // 10.0 - 4.0 = 6.0
        // 10.0 * 4.0 = 40.0
        // 10.0 / 4.0 = 2.5

        System.out.println();

        // Demonstrating division by zero guard:
        try {
            System.out.println(MathOperation.DIVIDE.apply(5.0, 0.0));
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage()); // Output: Division by zero
        }
    }
}
