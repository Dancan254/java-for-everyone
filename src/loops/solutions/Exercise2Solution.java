package loops.solutions;

import java.util.Scanner;

/**
 * Exercise2Solution.java
 *
 * Solutions for Exercise Set 2: While Loops and Input Validation.
 * Covers: while loops, integer arithmetic, input loops, running accumulation.
 *
 * Note: the Scanner-based exercises (2.2 and 2.3) call System.in.
 * To test without interactive input, the Collatz and digit-sum examples
 * use hardcoded values in addition to Scanner prompts so the output is visible.
 */
public class Exercise2Solution {

    public static void main(String[] args) {

        // --- Exercise 2.1: Collatz Sequence ---
        // Apply the Collatz rule until the value reaches 1.
        // Counting steps gives a sense of the sequence length.
        System.out.println("=== Exercise 2.1: Collatz Sequence ===");
        int collatzStart = 27; // 27 is a famous example — it takes 111 steps to reach 1.
        collatzSequence(collatzStart);

        // Also demonstrate for a small value.
        collatzSequence(6);

        // --- Exercise 2.2: Digit Sum (hardcoded demo + optional Scanner) ---
        // Digit extraction using integer arithmetic avoids string conversion.
        // n % 10 gives the last digit; n / 10 removes it.
        System.out.println("\n=== Exercise 2.2: Digit Sum ===");
        int[] testValues = {4567, 100, 999, 1};
        for (int value : testValues) {
            System.out.println("digitSum(" + value + ") = " + digitSum(value));
        }
        // Output:
        // digitSum(4567) = 22
        // digitSum(100)  = 1
        // digitSum(999)  = 27
        // digitSum(1)    = 1

        // --- Exercise 2.3: Running Average (Scanner demo with preset values) ---
        // Running average is maintained by tracking sum and count separately.
        // Dividing sum / count at each step gives the current average.
        // This demo uses preset values to produce visible output without interaction.
        System.out.println("\n=== Exercise 2.3: Running Average (preset values) ===");
        double[] inputs = {10.0, 20.0, 15.0, 30.0, 25.0};
        double runningSum   = 0;
        int    runningCount = 0;

        for (double input : inputs) {
            runningSum += input;
            runningCount++;
            double currentAvg = runningSum / runningCount;
            System.out.printf("After %.1f: count=%d, average=%.2f%n",
                    input, runningCount, currentAvg);
        }
        // Output:
        // After 10.0: count=1, average=10.00
        // After 20.0: count=2, average=15.00
        // After 15.0: count=3, average=15.00
        // After 30.0: count=4, average=18.75
        // After 25.0: count=5, average=20.00

        System.out.printf("Final: %d numbers entered, average = %.2f%n", runningCount,
                runningCount > 0 ? runningSum / runningCount : 0.0);
        // Output: Final: 5 numbers entered, average = 20.00
    }

    /**
     * Prints the Collatz sequence starting from n and returns the number of steps.
     * If n is even: n = n / 2. If n is odd: n = 3*n + 1. Stop when n == 1.
     */
    static int collatzSequence(int start) {
        System.out.print("Collatz(" + start + "): " + start);
        int n     = start;
        int steps = 0;

        while (n != 1) {
            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
            System.out.print(" -> " + n);
            steps++;
        }

        System.out.println(" (" + steps + " steps)");
        return steps;
    }

    /**
     * Returns the sum of the digits of n.
     * Uses integer arithmetic exclusively — no conversion to String.
     * Works correctly for positive integers; treats 0 as having digit sum 0.
     */
    static int digitSum(int n) {
        // Handle negative input gracefully by working on the absolute value.
        n = Math.abs(n);

        int sum = 0;
        // Each iteration: extract the last digit with % 10, then remove it with / 10.
        while (n > 0) {
            sum += n % 10; // Last digit.
            n   /= 10;     // Remove the last digit.
        }
        return sum;
    }
}
