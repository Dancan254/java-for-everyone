package loops.solutions;

/**
 * Exercise1Solution.java
 *
 * Solutions for Exercise Set 1: Basic for Loops.
 * Covers: for loop structure, printf formatting, accumulation, nested loops.
 */
public class Exercise1Solution {

    public static void main(String[] args) {

        // --- Exercise 1.1: Number Table ---
        // Print each number from 1 to 20 alongside its square and cube.
        // printf with fixed-width format strings keeps the columns aligned.
        System.out.println("=== Exercise 1.1: Number Table ===");
        System.out.printf("%-8s %-10s %-10s%n", "Number", "Square", "Cube");

        for (int i = 1; i <= 20; i++) {
            int square = i * i;
            long cube  = (long) i * i * i; // Use long to avoid overflow for larger values.
            System.out.printf("%-8d %-10d %-10d%n", i, square, cube);
        }
        // Output (first three rows):
        // Number   Square     Cube
        // 1        1          1
        // 2        4          8
        // 3        9          27

        // --- Exercise 1.2: Sum of Odds ---
        // Accumulate the sum of odd numbers from 1 to 99 and print each step.
        // Incrementing by 2 starting from 1 naturally visits only odd numbers.
        System.out.println("\n=== Exercise 1.2: Sum of Odds ===");
        int sumOfOdds = 0;

        for (int i = 1; i <= 99; i += 2) {
            sumOfOdds += i;
            System.out.println("Added " + i + ", running sum = " + sumOfOdds);
        }

        System.out.println("Total sum of odd numbers 1-99: " + sumOfOdds);
        // Output (last line): Total sum of odd numbers 1-99: 2500
        // Mathematical check: sum of first n odd numbers = n^2; n=50 odds -> 50^2 = 2500. Correct.

        // --- Exercise 1.3: Multiplication Table Grid ---
        // The outer loop drives the rows (first factor) and the inner loop drives the columns.
        // %4d ensures each cell is right-aligned in a field of width 4.
        System.out.println("\n=== Exercise 1.3: Multiplication Table Grid ===");

        for (int row = 1; row <= 10; row++) {
            for (int col = 1; col <= 10; col++) {
                System.out.printf("%4d", row * col);
            }
            // Move to the next line after all columns in this row are printed.
            System.out.println();
        }
        // Output (first two rows):
        //    1   2   3   4   5   6   7   8   9  10
        //    2   4   6   8  10  12  14  16  18  20
    }
}
