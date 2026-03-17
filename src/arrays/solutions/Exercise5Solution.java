package arrays.solutions;

import java.util.Arrays;

/**
 * Exercise5Solution.java
 *
 * Solutions for Arrays Exercise Set 5: Classic Array Algorithms.
 * Covers: Kadane's algorithm (maximum subarray), two-sum (brute force and two-pointer),
 * maximum profit from stock prices (single-pass O(n)).
 */
public class Exercise5Solution {

    // --- Kadane's Algorithm ---

    /**
     * Finds the contiguous subarray with the largest sum using Kadane's algorithm.
     * Time complexity: O(n). Space: O(1).
     *
     * Key idea:
     *   At each position, decide: extend the current subarray (currentSum + arr[i])
     *   or start fresh from arr[i] alone — whichever is larger.
     *   Track the global maximum seen so far.
     *
     * Returns int[3]: { maxSum, startIndex, endIndex }.
     */
    static int[] maxSubarray(int[] arr) {
        int maxSum      = arr[0];
        int currentSum  = arr[0];
        int start       = 0;
        int end         = 0;
        int tempStart   = 0; // Tracks where the current subarray started.

        for (int i = 1; i < arr.length; i++) {
            // Starting fresh is better than extending if arr[i] alone exceeds currentSum + arr[i].
            if (arr[i] > currentSum + arr[i]) {
                currentSum = arr[i];
                tempStart  = i; // The new subarray starts at i.
            } else {
                currentSum += arr[i];
            }

            // Update the global maximum if the current subarray exceeds it.
            if (currentSum > maxSum) {
                maxSum = currentSum;
                start  = tempStart;
                end    = i;
            }
        }

        return new int[]{maxSum, start, end};
    }

    // --- Two-Sum: Brute Force ---

    /**
     * Finds all unique pairs (arr[i], arr[j]) where i < j and arr[i] + arr[j] == target.
     * Time: O(n^2). Prints each pair.
     */
    static void twoSumBrute(int[] arr, int target) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    System.out.println("  (" + arr[i] + ", " + arr[j] + ")");
                }
            }
        }
    }

    // --- Two-Sum: Two-Pointer ---

    /**
     * Finds all unique pairs that sum to target using the two-pointer technique.
     * Requires the input array to be sorted. Time: O(n log n) for sort, O(n) for search.
     *
     * Two pointers start at each end and move inward:
     * - If the sum equals target: record the pair, move both pointers.
     * - If the sum is less than target: move the left pointer right (increase sum).
     * - If the sum is greater than target: move the right pointer left (decrease sum).
     */
    static void twoSumTwoPointer(int[] arr, int target) {
        int[] sorted = arr.clone(); // Sort a copy; do not destroy the original.
        Arrays.sort(sorted);

        int left  = 0;
        int right = sorted.length - 1;

        while (left < right) {
            int sum = sorted[left] + sorted[right];
            if (sum == target) {
                System.out.println("  (" + sorted[left] + ", " + sorted[right] + ")");
                left++;
                right--;
            } else if (sum < target) {
                left++;  // Increase the sum by moving the left pointer right.
            } else {
                right--; // Decrease the sum by moving the right pointer left.
            }
        }
    }

    // --- Maximum Stock Profit ---

    /**
     * Returns the maximum profit from a single buy-sell transaction.
     * Time: O(n) — one pass tracks the minimum price seen so far and the max achievable profit.
     *
     * At each day: if selling today would beat the current max profit, update maxProfit.
     * Then update minPrice if today is cheaper than any previously seen day.
     */
    static int maxProfit(int[] prices) {
        if (prices.length < 2) return 0;

        int minPrice  = prices[0];
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            // If selling today at prices[i] beats our best profit so far, update maxProfit.
            int profitIfSoldToday = prices[i] - minPrice;
            if (profitIfSoldToday > maxProfit) {
                maxProfit = profitIfSoldToday;
            }

            // Update minPrice if today is the cheapest day seen so far.
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            }
        }

        return maxProfit;
    }

    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 5.1: Kadane's Algorithm ---
        System.out.println("=== Exercise 5.1: Kadane's Maximum Subarray ===");

        int[][] testCases = {
                {-2, 1, -3, 4, -1, 2, 1, -5, 4},
                {1, 2, 3, 4, 5},
                {-1, -2, -3, -4},
                {-2, -3, 4, -1, -2, 1, 5, -3}
        };

        for (int[] arr : testCases) {
            int[] result = maxSubarray(arr);
            System.out.printf("Array: %-35s -> maxSum=%d, subarray=%s%n",
                    Arrays.toString(arr),
                    result[0],
                    Arrays.toString(Arrays.copyOfRange(arr, result[1], result[2] + 1)));
        }
        // Output:
        // Array: [-2, 1, -3, 4, -1, 2, 1, -5, 4] -> maxSum=6, subarray=[4, -1, 2, 1]
        // Array: [1, 2, 3, 4, 5]                  -> maxSum=15, subarray=[1, 2, 3, 4, 5]
        // Array: [-1, -2, -3, -4]                 -> maxSum=-1, subarray=[-1]
        // Array: [-2, -3, 4, -1, -2, 1, 5, -3]   -> maxSum=7, subarray=[4, -1, -2, 1, 5]

        // --- Exercise 5.2: Two-Sum ---
        System.out.println("\n=== Exercise 5.2: Two-Sum ===");

        int[] nums    = {1, 5, 3, 4, 6, 2};
        int   target  = 7;

        System.out.println("Array: " + Arrays.toString(nums) + ", target: " + target);

        System.out.println("Brute force pairs:");
        twoSumBrute(nums, target);
        // Output:
        //   (1, 6)
        //   (5, 2)
        //   (3, 4)

        System.out.println("Two-pointer pairs (sorted input):");
        twoSumTwoPointer(nums, target);
        // Output (pairs from sorted {1,2,3,4,5,6}):
        //   (1, 6)
        //   (2, 5)
        //   (3, 4)

        // --- Exercise 5.3: Maximum Stock Profit ---
        System.out.println("\n=== Exercise 5.3: Maximum Stock Profit ===");

        int[][] priceSets = {
                {7, 1, 5, 3, 6, 4},   // Best: buy at 1, sell at 6 -> profit 5
                {7, 6, 4, 3, 1},       // Prices only fall -> profit 0
                {1, 2, 3, 4, 5},       // Prices only rise -> buy at 1, sell at 5 -> profit 4
                {3, 8, 1, 10, 2}       // Buy at 1, sell at 10 -> profit 9
        };

        for (int[] prices : priceSets) {
            System.out.printf("Prices: %-25s -> maxProfit = %d%n",
                    Arrays.toString(prices), maxProfit(prices));
        }
        // Output:
        // Prices: [7, 1, 5, 3, 6, 4]   -> maxProfit = 5
        // Prices: [7, 6, 4, 3, 1]       -> maxProfit = 0
        // Prices: [1, 2, 3, 4, 5]       -> maxProfit = 4
        // Prices: [3, 8, 1, 10, 2]      -> maxProfit = 9
    }
}
