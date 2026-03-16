/**
 * Exercise3Solution.java
 *
 * Solutions for Exercise Set 3: Nested Loops and Patterns.
 * Covers: nested for loops, pattern printing, prime detection, space padding.
 */
public class Exercise3Solution {

    public static void main(String[] args) {

        // --- Exercise 3.1: Hollow Rectangle ---
        // A cell is on the border if it is the first or last row, or the first or last column.
        // Interior cells get a space instead of an asterisk.
        System.out.println("=== Exercise 3.1: Hollow Rectangle (width=6, height=4) ===");
        printHollowRectangle(6, 4);

        System.out.println("\nHollow Rectangle (width=8, height=5):");
        printHollowRectangle(8, 5);

        // --- Exercise 3.2: Number Pyramid ---
        // Each row i (1-based) prints numbers 1 through i.
        // The leading spaces right-align the output: row i needs (n-i) spaces before it.
        System.out.println("\n=== Exercise 3.2: Number Pyramid (n=5) ===");
        printNumberPyramid(5);

        System.out.println("\nNumber Pyramid (n=8):");
        printNumberPyramid(8);

        // --- Exercise 3.3: Prime Sieve (nested loops) ---
        // For each candidate n, check all divisors from 2 to n/2.
        // If no divisor divides n evenly, n is prime.
        // This is not the most efficient approach but clearly demonstrates nested loops.
        System.out.println("\n=== Exercise 3.3: Primes up to 200 ===");
        int primeCount = 0;
        System.out.print("Primes: ");

        for (int n = 2; n <= 200; n++) {
            if (isPrime(n)) {
                System.out.print(n + " ");
                primeCount++;
            }
        }

        System.out.println();
        System.out.println("Total primes found: " + primeCount);
        // Output: Total primes found: 46
    }

    /**
     * Prints a hollow rectangle of the given width and height using asterisks.
     * A cell (row, col) is on the border if:
     *   row == 0, row == height-1, col == 0, or col == width-1.
     */
    static void printHollowRectangle(int width, int height) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                boolean isBorder = (row == 0 || row == height - 1
                        || col == 0 || col == width - 1);
                System.out.print(isBorder ? "* " : "  ");
            }
            System.out.println();
        }
    }

    /**
     * Prints a right-aligned number pyramid with n rows.
     * Row i (1-based) contains numbers 1 through i.
     * Leading spaces = (n - i) groups of 2 characters each.
     */
    static void printNumberPyramid(int n) {
        for (int row = 1; row <= n; row++) {
            // Print leading spaces to right-align the pyramid.
            for (int space = 0; space < n - row; space++) {
                System.out.print("  ");
            }
            // Print the numbers for this row.
            for (int num = 1; num <= row; num++) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }

    /**
     * Returns true if n is prime.
     * Checks all factors from 2 to n/2 (as required by the exercise — no Math.sqrt).
     * By definition, 2 is prime and integers less than 2 are not.
     */
    static boolean isPrime(int n) {
        if (n < 2) return false;
        if (n == 2) return true;

        // Even numbers greater than 2 are not prime.
        if (n % 2 == 0) return false;

        // Check odd divisors from 3 to n/2.
        for (int divisor = 3; divisor <= n / 2; divisor += 2) {
            if (n % divisor == 0) {
                return false; // divisor divides n evenly — n is not prime.
            }
        }
        return true;
    }
}
