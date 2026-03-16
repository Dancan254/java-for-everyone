/**
 * SealedDemo — a self-contained demonstration of sealed classes and pattern matching.
 *
 * Topics covered:
 *   1. Sealed interfaces and record subtypes
 *   2. Pattern matching for instanceof (Java 16)
 *   3. Switch pattern matching with type patterns (Java 21)
 *   4. Guarded patterns with 'when'
 *   5. Null handling in pattern switch
 *   6. Record patterns (Java 21)
 *   7. Complete sealed expression-tree example
 *
 * Requires Java 21 or later.
 * Compile: javac --enable-preview --release 21 SealedDemo.java
 * Run:     java  --enable-preview SealedDemo
 */
public class SealedDemo {

    // -------------------------------------------------------------------------
    // Sealed Payment hierarchy
    // -------------------------------------------------------------------------

    // A sealed interface restricts which classes may implement it.
    // The compiler knows the complete set of subtypes.
    sealed interface Payment permits CashPayment, CardPayment, CryptoPayment {}

    // Each permitted type is a record, which is implicitly final.
    record CashPayment(double amount) implements Payment {}
    record CardPayment(String cardNumber, double amount) implements Payment {}
    record CryptoPayment(String walletAddress, double amount, String currency) implements Payment {}

    // -------------------------------------------------------------------------
    // Sealed Shape hierarchy
    // -------------------------------------------------------------------------

    sealed interface Shape permits Circle, Rectangle, Triangle {}

    record Circle(double radius)              implements Shape {}
    record Rectangle(double width, double height) implements Shape {}
    record Triangle(double base, double height)   implements Shape {}

    // -------------------------------------------------------------------------
    // Sealed expression tree
    // -------------------------------------------------------------------------

    sealed interface Expr permits Num, Add, Mul, Neg {}

    record Num(double value)            implements Expr {}
    record Add(Expr left, Expr right)   implements Expr {}
    record Mul(Expr left, Expr right)   implements Expr {}
    record Neg(Expr expr)               implements Expr {}

    // -------------------------------------------------------------------------
    // main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        instanceofPatternDemo();
        sealedSwitchDemo();
        guardedPatternDemo();
        nullHandlingDemo();
        recordPatternDemo();
        expressionTreeDemo();
    }

    // -------------------------------------------------------------------------
    // 1. Pattern matching for instanceof
    // -------------------------------------------------------------------------

    static void instanceofPatternDemo() {
        System.out.println("=== instanceof pattern matching ===");

        Object[] values = { "hello", 42, 3.14, true, null };

        for (Object obj : values) {
            // The old way required a separate cast after the test:
            //   if (obj instanceof String) { String s = (String) obj; ... }
            //
            // The new way combines test and cast into one binding variable.
            if (obj instanceof String s) {
                System.out.println("String of length " + s.length() + ": " + s.toUpperCase());
                // Output (first iteration): String of length 5: HELLO
            } else if (obj instanceof Integer i) {
                System.out.println("Integer: " + (i * 2)); // Output: Integer: 84
            } else if (obj instanceof Double d) {
                System.out.println("Double: " + String.format("%.4f", d)); // Output: Double: 3.1400
            } else if (obj instanceof Boolean b) {
                System.out.println("Boolean: " + !b); // Output: Boolean: false (negated)
            } else {
                System.out.println("null or unknown"); // Output: null or unknown
            }
        }

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 2. Sealed switch with exhaustiveness checking
    // -------------------------------------------------------------------------

    static void sealedSwitchDemo() {
        System.out.println("=== Sealed switch ===");

        Payment[] payments = {
            new CashPayment(500.00),
            new CardPayment("4111111111111234", 250.00),
            new CryptoPayment("1A2b3C4d5E6f", 1500.00, "BTC")
        };

        for (Payment p : payments) {
            // No 'default' branch needed — the compiler knows these three cases
            // cover all permitted subtypes of Payment.
            double fee = switch (p) {
                case CashPayment   cash   -> 0.0;
                case CardPayment   card   -> card.amount() * 0.02;
                case CryptoPayment crypto -> crypto.amount() * 0.01;
            };
            System.out.printf("Payment fee: $%.2f%n", fee);
        }
        // Output:
        // Payment fee: $0.00
        // Payment fee: $5.00
        // Payment fee: $15.00

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 3. Guarded patterns with 'when'
    // -------------------------------------------------------------------------

    static void guardedPatternDemo() {
        System.out.println("=== Guarded patterns ===");

        Payment[] payments = {
            new CashPayment(500.00),
            new CashPayment(15_000.00), // Large cash — needs special handling.
            new CardPayment("4111111111111234", 75.00),
            new CryptoPayment("1A2b3C", 200.00, "ETH")
        };

        for (Payment p : payments) {
            // 'when' clauses refine a type pattern with an additional boolean condition.
            // Cases are tried top-to-bottom; the first matching case wins.
            String description = switch (p) {
                case CashPayment cash when cash.amount() > 10_000 ->
                        "Large cash $" + cash.amount() + " — reporting required.";
                case CashPayment cash ->
                        "Cash payment of $" + cash.amount();
                case CardPayment card ->
                        "Card ending " + card.cardNumber().substring(card.cardNumber().length() - 4)
                        + " for $" + card.amount();
                case CryptoPayment crypto ->
                        crypto.amount() + " " + crypto.currency();
            };
            System.out.println(description);
        }
        // Output:
        // Cash payment of $500.0
        // Large cash $15000.0 — reporting required.
        // Card ending 1234 for $75.0
        // 200.0 ETH

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 4. Null handling in pattern switch
    // -------------------------------------------------------------------------

    static void nullHandlingDemo() {
        System.out.println("=== Null handling in switch ===");

        Payment[] payments = { new CashPayment(100), null };

        for (Payment p : payments) {
            // A 'case null' branch handles the null case explicitly instead of
            // throwing NullPointerException at the switch statement.
            String result = switch (p) {
                case null          -> "No payment provided.";
                case CashPayment c -> "Cash: $" + c.amount();
                case CardPayment c -> "Card: " + c.cardNumber();
                case CryptoPayment c -> "Crypto: " + c.currency();
            };
            System.out.println(result);
        }
        // Output:
        // Cash: $100.0
        // No payment provided.

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 5. Record patterns — destructuring in switch
    // -------------------------------------------------------------------------

    static void recordPatternDemo() {
        System.out.println("=== Record patterns ===");

        record Point(int x, int y) {}

        // A record pattern matches a record type and binds its components directly.
        // This avoids calling point.x() and point.y() manually.
        Object[] objects = {
            new Point(0, 0),
            new Point(0, 5),
            new Point(3, 0),
            new Point(3, 4),
            "not a point"
        };

        for (Object obj : objects) {
            String description = switch (obj) {
                case Point(int x, int y) when x == 0 && y == 0 -> "Origin";
                case Point(int x, int y) when x == 0           -> "On Y-axis at " + y;
                case Point(int x, int y) when y == 0           -> "On X-axis at " + x;
                case Point(int x, int y)                       -> "Point(" + x + ", " + y + ")";
                default                                        -> "Not a point: " + obj;
            };
            System.out.println(description);
        }
        // Output:
        // Origin
        // On Y-axis at 5
        // On X-axis at 3
        // Point(3, 4)
        // Not a point: not a point

        System.out.println();

        // Shape area calculation using record patterns.
        Shape[] shapes = {
            new Circle(5.0),
            new Rectangle(4.0, 6.0),
            new Triangle(3.0, 8.0)
        };

        for (Shape shape : shapes) {
            // Record patterns extract the components; no separate accessor calls needed.
            double area = switch (shape) {
                case Circle(double r)                  -> Math.PI * r * r;
                case Rectangle(double w, double h)     -> w * h;
                case Triangle(double base, double height) -> 0.5 * base * height;
            };
            System.out.printf("%s area: %.2f%n", shape.getClass().getSimpleName(), area);
        }
        // Output:
        // Circle area: 78.54
        // Rectangle area: 24.00
        // Triangle area: 12.00

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 6. Complete expression-tree example
    // -------------------------------------------------------------------------

    static void expressionTreeDemo() {
        System.out.println("=== Expression tree ===");

        // Build the expression: (3 + 4) * (-2)
        Expr expression = new Mul(
                new Add(new Num(3), new Num(4)),
                new Neg(new Num(2))
        );

        // Print the expression as a string.
        System.out.println(printExpr(expression));  // Output: ((3.0 + 4.0) * (-2.0))

        // Evaluate the expression.
        System.out.println(evalExpr(expression));   // Output: -14.0

        // Build: 5 + (2 * 3)
        Expr expression2 = new Add(new Num(5), new Mul(new Num(2), new Num(3)));
        System.out.println(printExpr(expression2)); // Output: (5.0 + (2.0 * 3.0))
        System.out.println(evalExpr(expression2));  // Output: 11.0

        System.out.println();
    }

    // Evaluate an expression tree recursively.
    // The switch is exhaustive over Expr — no default branch required.
    static double evalExpr(Expr expr) {
        return switch (expr) {
            case Num(double v)       -> v;
            case Add(Expr l, Expr r) -> evalExpr(l) + evalExpr(r);
            case Mul(Expr l, Expr r) -> evalExpr(l) * evalExpr(r);
            case Neg(Expr e)         -> -evalExpr(e);
        };
    }

    // Convert an expression tree to a human-readable string.
    static String printExpr(Expr expr) {
        return switch (expr) {
            case Num(double v)       -> String.valueOf(v);
            case Add(Expr l, Expr r) -> "(" + printExpr(l) + " + " + printExpr(r) + ")";
            case Mul(Expr l, Expr r) -> "(" + printExpr(l) + " * " + printExpr(r) + ")";
            case Neg(Expr e)         -> "(-" + printExpr(e) + ")";
        };
    }
}
