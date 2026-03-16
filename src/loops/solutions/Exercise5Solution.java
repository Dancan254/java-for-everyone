import java.util.Scanner;

/**
 * Exercise5Solution.java
 *
 * Solutions for Exercise Set 5: Algorithm Implementation with Loops.
 * Covers: binary search, bubble sort with early exit, Fibonacci with array memoization.
 */
public class Exercise5Solution {

    public static void main(String[] args) {

        // --- Exercise 5.1: Binary Search ---
        // Binary search works by repeatedly halving the search space.
        // It requires the array to be sorted. Each iteration eliminates half the candidates.
        System.out.println("=== Exercise 5.1: Binary Search ===");

        int[] sorted = {2, 5, 8, 12, 16, 23, 38, 42, 56, 72, 88, 91, 95, 99, 103};
        System.out.println("Array: " + java.util.Arrays.toString(sorted));

        // Search 1: target exists in the middle of the array.
        int target1 = 38;
        int idx1    = binarySearch(sorted, target1);
        System.out.println("Search for " + target1 + ": index " + idx1);
        // Output: Search for 38: index 6

        // Search 2: target is the last element (right boundary).
        int target2 = 103;
        int idx2    = binarySearch(sorted, target2);
        System.out.println("Search for " + target2 + ": index " + idx2);
        // Output: Search for 103: index 14

        // Search 3: target does not exist — should return -1.
        int target3 = 50;
        int idx3    = binarySearch(sorted, target3);
        System.out.println("Search for " + target3 + ": index " + idx3);
        // Output: Search for 50: index -1

        // --- Exercise 5.2: Bubble Sort ---
        // In each pass, adjacent elements that are out of order are swapped.
        // If a full pass produces no swaps, the array is already sorted — stop early.
        System.out.println("\n=== Exercise 5.2: Bubble Sort ===");

        int[] unsorted = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Before sort: " + java.util.Arrays.toString(unsorted));
        int passCount = bubbleSort(unsorted);
        System.out.println("After sort:  " + java.util.Arrays.toString(unsorted));
        System.out.println("Passes needed: " + passCount);
        // Output: Passes needed: 4  (early termination saves passes)

        // --- Exercise 5.3: Fibonacci with Array Memoization ---
        // Store previously computed values in an array so each value is computed exactly once.
        // Retrieving fib(n) afterward is O(1) — just read fibs[n].
        System.out.println("\n=== Exercise 5.3: Fibonacci with Memoization ===");

        long[] fibs = computeFibonacci(30);
        System.out.print("First 30 Fibonacci numbers:");
        for (int i = 0; i < fibs.length; i++) {
            if (i % 10 == 0) System.out.println();
            System.out.printf("%12d", fibs[i]);
        }
        System.out.println();
        // Output (last line includes fib(29) = 514229):
        //         832040    1346269    2178309    3524578    5702887
        //        9227465   14930352   24157817   39088169   63245986

        // Look up a specific Fibonacci number using the stored array — O(1).
        System.out.println("fib(0)  = " + fibs[0]);   // Output: fib(0)  = 0
        System.out.println("fib(1)  = " + fibs[1]);   // Output: fib(1)  = 1
        System.out.println("fib(10) = " + fibs[10]);  // Output: fib(10) = 55
        System.out.println("fib(29) = " + fibs[29]);  // Output: fib(29) = 514229
    }

    /**
     * Binary search: returns the index of target in arr, or -1 if not found.
     * Precondition: arr must be sorted in ascending order.
     *
     * Each iteration: compute mid, compare arr[mid] to target.
     * - Equal: found — return mid.
     * - Less than target: target must be in the right half — move low up.
     * - Greater than target: target must be in the left half — move high down.
     */
    static int binarySearch(int[] arr, int target) {
        int low  = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid] == target) {
                return mid; // Found the target.
            } else if (arr[mid] < target) {
                low = mid + 1;  // Target is in the right half.
            } else {
                high = mid - 1; // Target is in the left half.
            }
        }

        return -1; // Target was not found.
    }

    /**
     * Bubble sort in-place. Each pass bubbles the largest unsorted element to its correct position.
     * Returns the number of passes actually performed (early exit optimization).
     */
    static int bubbleSort(int[] arr) {
        int passes = 0;

        for (int pass = 0; pass < arr.length - 1; pass++) {
            boolean swapped = false;

            // Each pass: compare adjacent pairs and swap if out of order.
            // After pass k, the last k elements are already in their final positions.
            for (int i = 0; i < arr.length - 1 - pass; i++) {
                if (arr[i] > arr[i + 1]) {
                    // Swap arr[i] and arr[i+1].
                    int temp   = arr[i];
                    arr[i]     = arr[i + 1];
                    arr[i + 1] = temp;
                    swapped    = true;
                }
            }

            passes++;
            System.out.println("After pass " + passes + ": " + java.util.Arrays.toString(arr));

            // If no swap occurred, the array is fully sorted — no need to continue.
            if (!swapped) {
                break;
            }
        }

        return passes;
    }

    /**
     * Computes the first n Fibonacci numbers and returns them in an array.
     * fib[0] = 0, fib[1] = 1, fib[i] = fib[i-1] + fib[i-2].
     *
     * The loop reads from the array for its two previous values rather than
     * recomputing them — this is the essence of memoization.
     */
    static long[] computeFibonacci(int n) {
        long[] fibs = new long[n];
        fibs[0] = 0; // Base case 1: fib(0) = 0.
        if (n > 1) {
            fibs[1] = 1; // Base case 2: fib(1) = 1.
        }

        // Each subsequent value is the sum of the two preceding values.
        // Both values are already in the array — no recursion needed.
        for (int i = 2; i < n; i++) {
            fibs[i] = fibs[i - 1] + fibs[i - 2];
        }

        return fibs;
    }
}
