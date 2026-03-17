package methods.solutions;

/**
 * Exercise6Solution.java
 *
 * Solutions for Exercise Set 6: Advanced Challenges.
 * Covers: Euclidean GCD algorithm, LCM derivation, binary conversion via
 * recursive string building, and the Tower of Hanoi.
 *
 * These problems showcase recursion at its most elegant: the mathematical
 * structure of each problem maps directly to a recursive definition, making
 * the recursive solution shorter and clearer than any iterative equivalent.
 */
public class Exercise6Solution {

    public static void main(String[] args) {

        // === Exercise 6.1: GCD and LCM ===
        System.out.println("=== Exercise 6.1: GCD and LCM ===");

        // GCD via the Euclidean algorithm.
        // Call trace for gcd(48, 18):
        //   gcd(48, 18) = gcd(18, 48 % 18) = gcd(18, 12)
        //   gcd(18, 12) = gcd(12, 18 % 12) = gcd(12, 6)
        //   gcd(12,  6) = gcd(6,  12 %  6) = gcd(6,  0)
        //   gcd(6,   0) = 6                 <- base case (b == 0)
        System.out.println("gcd(48, 18)  = " + gcd(48, 18));
        // Output: gcd(48, 18)  = 6

        System.out.println("gcd(100, 75) = " + gcd(100, 75));
        // Output: gcd(100, 75) = 25

        System.out.println("gcd(17, 13)  = " + gcd(17, 13));
        // Output: gcd(17, 13)  = 1  (co-prime — no common factor other than 1)

        // LCM uses the identity: lcm(a, b) = |a * b| / gcd(a, b)
        System.out.println("\nlcm(4, 6)    = " + lcm(4, 6));
        // Output: lcm(4, 6)    = 12

        System.out.println("lcm(12, 15)  = " + lcm(12, 15));
        // Output: lcm(12, 15)  = 60

        System.out.println("lcm(7, 5)    = " + lcm(7, 5));
        // Output: lcm(7, 5)    = 35  (co-prime: lcm = product)

        // === Exercise 6.2: Binary Conversion ===
        System.out.println("\n=== Exercise 6.2: Binary Conversion ===");

        // Call trace for decimalToBinary(13):
        //   decimalToBinary(13) = decimalToBinary(6) + (13 % 2 = "1")
        //   decimalToBinary(6)  = decimalToBinary(3) + (6  % 2 = "0")
        //   decimalToBinary(3)  = decimalToBinary(1) + (3  % 2 = "1")
        //   decimalToBinary(1)  = "1"                 <- base case
        //   Returns: "1" -> "11" -> "110" -> "1101"
        int[] testValues = {0, 1, 2, 5, 10, 13, 255};
        for (int v : testValues) {
            System.out.printf("decimalToBinary(%3d) = %-9s  (verify: %s)%n",
                    v,
                    decimalToBinary(v),
                    Integer.toBinaryString(v)); // Java built-in for cross-check
        }
        // Output:
        // decimalToBinary(  0) = 0          (verify: 0)
        // decimalToBinary(  1) = 1          (verify: 1)
        // decimalToBinary(  2) = 10         (verify: 10)
        // decimalToBinary(  5) = 101        (verify: 101)
        // decimalToBinary( 10) = 1010       (verify: 1010)
        // decimalToBinary( 13) = 1101       (verify: 1101)
        // decimalToBinary(255) = 11111111   (verify: 11111111)

        // === Exercise 6.3: Tower of Hanoi ===
        System.out.println("\n=== Exercise 6.3: Tower of Hanoi ===");

        // Tower of Hanoi requires 2^n - 1 moves for n disks.
        // Call trace for towerOfHanoi(2, 'A', 'C', 'B'):
        //   Move disk 1 from A to B (using C as auxiliary)
        //   Move disk 2 from A to C
        //   Move disk 1 from B to C
        System.out.println("Tower of Hanoi — 2 disks:");
        towerOfHanoi(2, 'A', 'C', 'B');
        // Output:
        // Move disk 1 from A to B
        // Move disk 2 from A to C
        // Move disk 1 from B to C

        System.out.println("\nTower of Hanoi — 3 disks:");
        towerOfHanoi(3, 'A', 'C', 'B');
        // Output:
        // Move disk 1 from A to C
        // Move disk 2 from A to B
        // Move disk 1 from C to B
        // Move disk 3 from A to C
        // Move disk 1 from B to A
        // Move disk 2 from B to C
        // Move disk 1 from A to C

        System.out.println("\nMove counts:");
        for (int n = 1; n <= 5; n++) {
            System.out.println("  " + n + " disk(s) -> " + (int)(Math.pow(2, n) - 1) + " moves");
        }
        // Output:
        //   1 disk(s) -> 1 moves
        //   2 disk(s) -> 3 moves
        //   3 disk(s) -> 7 moves
        //   4 disk(s) -> 15 moves
        //   5 disk(s) -> 31 moves
    }

    // -------------------------------------------------------------------------
    // GCD and LCM
    // -------------------------------------------------------------------------

    /**
     * Finds the Greatest Common Divisor using the Euclidean algorithm.
     *
     * Mathematical basis: gcd(a, b) == gcd(b, a mod b).
     * This works because any divisor of both a and b also divides (a mod b),
     * so the GCD is preserved at every step.
     *
     * Base case: gcd(a, 0) = a — when b reaches 0, a is the GCD.
     * Each call reduces b (it becomes the remainder, which is strictly smaller),
     * guaranteeing termination.
     */
    static int gcd(int a, int b) {
        if (b == 0) {
            return a; // base case: a divides a and 0, so a is the GCD
        }
        return gcd(b, a % b); // key step: replace (a,b) with (b, a mod b)
    }

    /**
     * Finds the Least Common Multiple.
     *
     * Uses the identity: lcm(a, b) = |a * b| / gcd(a, b).
     * Dividing before multiplying (a / gcd * b) avoids integer overflow
     * for moderately large inputs.
     */
    static long lcm(int a, int b) {
        // Divide first to reduce the intermediate value and avoid overflow.
        return (long) a / gcd(a, b) * b;
    }

    // -------------------------------------------------------------------------
    // Binary conversion
    // -------------------------------------------------------------------------

    /**
     * Converts a non-negative decimal integer to its binary string representation.
     *
     * Key insight: the last binary digit of n is (n % 2); the remaining digits
     * are the binary representation of (n / 2). Recursion naturally builds the
     * string from most-significant bit to least-significant bit.
     *
     * Base case: n < 2 — the binary representation is just "0" or "1".
     * Recursive case: binary(n) = binary(n/2) + (n%2)
     *
     * Call trace for n=13 (binary 1101):
     *   decimalToBinary(13) = decimalToBinary(6)  + "1"
     *   decimalToBinary(6)  = decimalToBinary(3)  + "0"
     *   decimalToBinary(3)  = decimalToBinary(1)  + "1"
     *   decimalToBinary(1)  = "1"                  <- base case
     *   Returns: "1" -> "11" -> "110" -> "1101"
     */
    static String decimalToBinary(int n) {
        if (n < 2) {
            return String.valueOf(n); // base case: "0" or "1"
        }
        // Prepend the result of the recursive call (higher-order bits come first).
        return decimalToBinary(n / 2) + (n % 2);
    }

    // -------------------------------------------------------------------------
    // Tower of Hanoi
    // -------------------------------------------------------------------------

    /**
     * Prints the optimal sequence of moves to transfer n disks from the 'from'
     * peg to the 'to' peg using 'aux' as a temporary holding peg.
     *
     * Algorithm (three steps):
     *   1. Move the top (n-1) disks from 'from' to 'aux' (using 'to' as temp).
     *   2. Move the largest disk (disk n) directly from 'from' to 'to'.
     *   3. Move the (n-1) disks from 'aux' to 'to' (using 'from' as temp).
     *
     * Base case: n == 0 — no disks to move, do nothing.
     *
     * Call trace for towerOfHanoi(2, A, C, B):
     *   Step 1: towerOfHanoi(1, A, B, C) -> "Move disk 1 from A to B"
     *   Step 2: "Move disk 2 from A to C"
     *   Step 3: towerOfHanoi(1, B, C, A) -> "Move disk 1 from B to C"
     *
     * The number of moves grows as 2^n - 1 because each level of recursion
     * doubles the previous count and adds one move for the largest disk.
     *
     * @param n    number of disks to move
     * @param from source peg label
     * @param to   destination peg label
     * @param aux  auxiliary peg label (used for temporary storage)
     */
    static void towerOfHanoi(int n, char from, char to, char aux) {
        if (n == 0) {
            return; // base case: nothing to move
        }

        // Step 1: move the top n-1 disks out of the way onto the auxiliary peg.
        towerOfHanoi(n - 1, from, aux, to);

        // Step 2: move the largest remaining disk to its final destination.
        System.out.println("Move disk " + n + " from " + from + " to " + to);

        // Step 3: move the n-1 disks from the auxiliary peg onto the destination.
        towerOfHanoi(n - 1, aux, to, from);
    }
}
