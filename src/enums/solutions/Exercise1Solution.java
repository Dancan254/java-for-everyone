package enums.solutions;

/**
 * Exercise1Solution.java
 *
 * Solutions for Exercise Set 1: Basic Enums.
 * Covers: declaring a simple enum, values()/ordinal()/name(), valueOf(),
 * and iterating with both for-each and indexed for loops.
 */
public class Exercise1Solution {

    // Declare the enum inside the class so the file is self-contained.
    enum Color {
        RED, GREEN, BLUE, YELLOW
    }

    public static void main(String[] args) {
        exercise1_1();
        exercise1_2();
        exercise1_3();
        exercise1_4();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.1 — Declare a Simple Enum
    // -------------------------------------------------------------------------

    static void exercise1_1() {
        System.out.println("=== Exercise 1.1: Simple Enum ===");

        Color chosen = Color.GREEN;
        System.out.println("Chosen color: " + chosen);                     // Output: GREEN
        System.out.println("Is GREEN == BLUE? " + (chosen == Color.BLUE)); // Output: false
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.2 — values(), ordinal(), and name()
    // -------------------------------------------------------------------------

    static void exercise1_2() {
        System.out.println("=== Exercise 1.2: values(), ordinal(), name() ===");

        for (Color c : Color.values()) {
            System.out.println(c.ordinal() + ": " + c.name());
        }
        // Output:
        // 0: RED
        // 1: GREEN
        // 2: BLUE
        // 3: YELLOW
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.3 — valueOf()
    // -------------------------------------------------------------------------

    static void exercise1_3() {
        System.out.println("=== Exercise 1.3: valueOf() ===");

        Color blue = Color.valueOf("BLUE");
        System.out.println("valueOf(\"BLUE\"): " + blue); // Output: BLUE

        try {
            Color wrong = Color.valueOf("blue"); // Case-sensitive — throws exception.
            System.out.println(wrong);
        } catch (IllegalArgumentException e) {
            System.out.println("valueOf(\"blue\") threw: " + e.getMessage());
            // Output: No enum constant enums.solutions.Exercise1Solution.Color.blue
        }
        // valueOf() is strictly case-sensitive. "BLUE" works; "blue" does not.
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.4 — Iterating with a Traditional for Loop
    // -------------------------------------------------------------------------

    static void exercise1_4() {
        System.out.println("=== Exercise 1.4: Indexed for Loop ===");

        Color[] colors = Color.values();
        for (int i = 0; i < colors.length; i++) {
            System.out.println(i + ": " + colors[i]);
        }
        // Output is identical to Exercise 1.2.
        System.out.println();
    }
}
