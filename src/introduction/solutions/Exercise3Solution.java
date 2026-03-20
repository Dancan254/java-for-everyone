package introduction.solutions;

/**
 * Exercise3Solution.java
 *
 * Solutions for Exercise Set 3: Type Casting and Conversion.
 * Covers: widening conversions (no cast needed), narrowing conversions
 * (explicit cast, truncation), and the char-int relationship.
 */
public class Exercise3Solution {

    public static void main(String[] args) {
        exercise3_1();
        exercise3_2();
        exercise3_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 3.1 — Widening Conversion
    // -------------------------------------------------------------------------

    static void exercise3_1() {
        System.out.println("=== Exercise 3.1: Widening Conversion ===");

        int    intVal    = 42;
        long   longVal   = intVal;    // int -> long: no cast needed
        double doubleVal = longVal;   // long -> double: no cast needed

        System.out.println("int:    " + intVal);    // Output: 42
        System.out.println("long:   " + longVal);   // Output: 42
        System.out.println("double: " + doubleVal); // Output: 42.0

        // Widening means converting to a type with a larger range.
        // Java performs widening conversions automatically because no information
        // is lost: every int value fits in a long, and every long value can be
        // represented (approximately) as a double.
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 3.2 — Narrowing Conversion
    // -------------------------------------------------------------------------

    static void exercise3_2() {
        System.out.println("=== Exercise 3.2: Narrowing Conversion ===");

        double d1  = 9.99;
        int    i1  = (int) d1;   // Truncation: fractional part is dropped, not rounded.
        System.out.println("(int) 9.99 = " + i1); // Output: 9
        // Result is 9, not 10. Java casting discards the decimal portion entirely.

        double d2  = 300.75;
        byte   b   = (byte) d2;
        System.out.println("(byte) 300.75 = " + b); // Output: 44
        // Step 1: truncate to int -> 300
        // Step 2: overflow-wrap into byte range [-128, 127]:
        //         300 - 256 = 44 (subtracts 256, one full byte cycle)
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 3.3 — char and int Relationship
    // -------------------------------------------------------------------------

    static void exercise3_3() {
        System.out.println("=== Exercise 3.3: char and int Relationship ===");

        char letter = 'A';
        System.out.println("char:       " + letter);          // Output: A
        System.out.println("as int:     " + (int) letter);    // Output: 65

        char next = (char)(letter + 1);
        System.out.println("'A' + 1:    " + next);            // Output: B

        System.out.println("\nA-Z with Unicode values:");
        for (int i = 0; i < 26; i++) {
            char ch = (char)('A' + i);
            System.out.printf("  %c = %d%n", ch, (int) ch);
        }
        // Output: A = 65, B = 66, ... Z = 90
        System.out.println();
    }
}
