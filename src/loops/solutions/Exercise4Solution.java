package loops.solutions;

import java.util.Random;

/**
 * Exercise4Solution.java
 *
 * Solutions for Exercise Set 4: Break, Continue, and Labeled Loops.
 * Covers: break with early exit, continue for skipping, labeled break for nested loops.
 */
public class Exercise4Solution {

    public static void main(String[] args) {

        // --- Exercise 4.1: First Missing Number ---
        // Sort the array, then scan for the position where arr[i] != i+1.
        // Alternatively, sum the array and subtract from the expected sum.
        System.out.println("=== Exercise 4.1: First Missing Number ===");

        int[] shuffled = {3, 7, 1, 2, 8, 4, 6};
        // Expected range: 1 to 8 (8 elements would mean 1..8 but we have 7 — one is missing).
        int expectedMax = shuffled.length + 1; // If n elements, range is 1..(n+1), one missing.

        // Calculate the expected sum of 1..expectedMax and subtract the actual sum.
        int expectedSum = expectedMax * (expectedMax + 1) / 2;
        int actualSum   = 0;
        for (int value : shuffled) {
            actualSum += value;
        }

        int missing = expectedSum - actualSum;
        System.out.println("Array: " + java.util.Arrays.toString(shuffled));
        System.out.println("Expected range: 1 to " + expectedMax);
        System.out.println("Missing number: " + missing);
        // Output: Missing number: 5

        // Alternative approach using break: sort a copy, then scan for the gap.
        int[] sorted = shuffled.clone();
        java.util.Arrays.sort(sorted);
        int missingByBreak = -1;
        for (int i = 0; i < sorted.length; i++) {
            if (sorted[i] != i + 1) {
                missingByBreak = i + 1;
                break; // Found the gap — no need to continue.
            }
        }
        System.out.println("Missing by break approach: " + missingByBreak);
        // Output: Missing by break approach: 5

        // --- Exercise 4.2: Skipping Multiples ---
        // continue skips the rest of the current iteration and moves to i+1.
        // Numbers that are multiples of 3 or 7 are skipped entirely.
        System.out.println("\n=== Exercise 4.2: Skipping Multiples of 3 or 7 ===");

        int printedCount = 0;
        System.out.print("Printed: ");

        for (int i = 1; i <= 50; i++) {
            if (i % 3 == 0 || i % 7 == 0) {
                continue; // Skip this number — jump directly to i++.
            }
            System.out.print(i + " ");
            printedCount++;
        }

        System.out.println();
        System.out.println("Count of numbers printed: " + printedCount);
        // Output: Count of numbers printed: 29

        // --- Exercise 4.3: Labeled Break in a Matrix Search ---
        // A labeled break exits the specified outer loop immediately,
        // even though the break statement itself is inside the inner loop.
        System.out.println("\n=== Exercise 4.3: Labeled Break in Matrix Search ===");

        Random random = new Random(42); // Fixed seed for reproducible output.
        int[][] matrix = new int[5][5];

        // Fill the matrix with random integers 1–100.
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                matrix[row][col] = random.nextInt(100) + 1;
            }
        }

        // Print the matrix before searching.
        System.out.println("Matrix:");
        for (int[] row : matrix) {
            System.out.println("  " + java.util.Arrays.toString(row));
        }

        // Search for the first value > 90.
        int foundValue = -1;
        int foundRow   = -1;
        int foundCol   = -1;

        outerSearch:
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] > 90) {
                    // Record the find and immediately exit both loops.
                    foundValue = matrix[row][col];
                    foundRow   = row;
                    foundCol   = col;
                    break outerSearch; // Exits the outer loop labeled "outerSearch".
                }
            }
        }

        if (foundValue != -1) {
            System.out.printf("First value > 90: %d at row %d, column %d%n",
                    foundValue, foundRow, foundCol);
        } else {
            System.out.println("No value greater than 90 found.");
        }
    }
}
