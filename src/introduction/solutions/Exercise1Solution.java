package introduction.solutions;

/**
 * Exercise1Solution.java
 *
 * Solutions for Exercise Set 1: Primitive Types and Variables.
 * Covers: declaring all eight primitive types, integer overflow behavior,
 * and integer division vs. floating-point division.
 */
public class Exercise1Solution {

    public static void main(String[] args) {
        exercise1_1();
        exercise1_2();
        exercise1_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.1 — Declare All Eight Primitives
    // -------------------------------------------------------------------------

    static void exercise1_1() {
        System.out.println("=== Exercise 1.1: Eight Primitive Types ===");

        byte   b       = 120;
        short  s       = 30_000;
        int    i       = 2_000_000;
        long   l       = 9_000_000_000L;   // L suffix required for long literals
        float  f       = 3.14F;            // F suffix required for float literals
        double d       = 2.718281828;
        char   c       = 'J';
        boolean flag   = true;

        System.out.println("byte:    " + b);
        System.out.println("short:   " + s);
        System.out.println("int:     " + i);
        System.out.println("long:    " + l);
        System.out.println("float:   " + f);
        System.out.println("double:  " + d);
        System.out.println("char:    " + c);
        System.out.println("boolean: " + flag);
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.2 — Integer Overflow
    // -------------------------------------------------------------------------

    static void exercise1_2() {
        System.out.println("=== Exercise 1.2: Integer Overflow ===");

        // byte holds values -128 to 127. Adding 1 to 127 wraps to -128.
        byte maxByte      = 127;
        byte overflowByte = (byte)(maxByte + 1);
        System.out.println("byte max:       " + maxByte);
        System.out.println("byte max + 1:   " + overflowByte);
        // Output: -128
        // The bit pattern for 128 (10000000 in binary) is interpreted as -128
        // in the two's complement representation that Java uses for integers.

        // int holds values -2,147,483,648 to 2,147,483,647. Adding 1 wraps to MIN_VALUE.
        int maxInt      = Integer.MAX_VALUE;
        int overflowInt = (int)(maxInt + 1L); // cast prevents compile-time constant folding
        System.out.println("int max:        " + maxInt);
        System.out.println("int max + 1:    " + overflowInt);
        // Output: -2147483648
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.3 — Integer Division vs. Double Division
    // -------------------------------------------------------------------------

    static void exercise1_3() {
        System.out.println("=== Exercise 1.3: Integer Division vs. Double Division ===");

        int a = 7;
        int b = 2;

        // Integer division: the fractional part is discarded (truncated, not rounded).
        // Both operands are int, so the result type is int — 3, not 3.5.
        int   intResult    = a / b;
        System.out.println("7 / 2         = " + intResult);   // Output: 3

        // Casting one operand to double before the division promotes the entire
        // expression to double, preserving the fractional part.
        double doubleResult = (double) a / b;
        System.out.println("(double)7 / 2 = " + doubleResult); // Output: 3.5

        // A double literal on either side also forces double arithmetic.
        System.out.println("7.0 / 2       = " + (7.0 / 2));   // Output: 3.5
        System.out.println("7 / 2.0       = " + (7 / 2.0));   // Output: 3.5
        System.out.println();
    }
}
