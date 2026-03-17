package arrays.solutions;

import java.util.Arrays;

/**
 * Exercise2Solution.java
 *
 * Solutions for Arrays Exercise Set 2: Searching and Sorting.
 * Covers: linear search, binary search with comparison counting, selection sort, merge of sorted arrays.
 */
public class Exercise2Solution {

    // Shared comparison counter — reset before each search to count cleanly.
    static int comparisonCount;

    // --- Linear Search ---

    /**
     * Searches from left to right. Returns the index of the first occurrence, or -1.
     * In the worst case (target not present) this visits every element: O(n).
     */
    static int linearSearch(int[] arr, int target) {
        comparisonCount = 0;
        for (int i = 0; i < arr.length; i++) {
            comparisonCount++;
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // --- Binary Search ---

    /**
     * Requires a sorted array. Repeatedly halves the search space: O(log n).
     * Uses three pointers: low (left boundary), high (right boundary), mid (midpoint).
     */
    static int binarySearch(int[] arr, int target) {
        comparisonCount = 0;
        int low  = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisonCount++;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // --- Selection Sort ---

    /**
     * In each pass, find the minimum element in the unsorted portion (indices pass..end)
     * and swap it into position `pass`. After k passes, the first k elements are sorted.
     *
     * Returns the total number of swaps performed.
     */
    static int selectionSort(int[] arr) {
        int swaps = 0;

        for (int pass = 0; pass < arr.length - 1; pass++) {
            // Find the index of the minimum value in arr[pass..arr.length-1].
            int minIndex = pass;
            for (int i = pass + 1; i < arr.length; i++) {
                if (arr[i] < arr[minIndex]) {
                    minIndex = i;
                }
            }

            // Only swap if the minimum is not already in the correct position.
            if (minIndex != pass) {
                int temp      = arr[pass];
                arr[pass]     = arr[minIndex];
                arr[minIndex] = temp;
                swaps++;
            }

            System.out.println("After pass " + (pass + 1) + ": " + Arrays.toString(arr));
        }

        return swaps;
    }

    // --- Merge Two Sorted Arrays ---

    /**
     * Merges two sorted arrays into a single sorted array in O(n + m) time.
     * Two index pointers advance through a and b; at each step the smaller
     * element is placed into the result. Once one array is exhausted, the
     * remaining elements from the other are copied directly.
     */
    static int[] merge(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int   ia = 0; // Current index in array a.
        int   ib = 0; // Current index in array b.
        int   ir = 0; // Current write index in result.

        // Compare the front elements of both arrays; take the smaller one.
        while (ia < a.length && ib < b.length) {
            if (a[ia] <= b[ib]) {
                result[ir++] = a[ia++];
            } else {
                result[ir++] = b[ib++];
            }
        }

        // Copy any remaining elements from a (b may already be exhausted).
        while (ia < a.length) {
            result[ir++] = a[ia++];
        }

        // Copy any remaining elements from b (a may already be exhausted).
        while (ib < b.length) {
            result[ir++] = b[ib++];
        }

        return result;
    }

    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 2.1: Linear vs Binary Search ---
        System.out.println("=== Exercise 2.1: Linear vs Binary Search ===");

        int[] sorted = {5, 12, 17, 23, 31, 44, 56, 68, 75, 89};
        System.out.println("Array: " + Arrays.toString(sorted));

        int target = 44;
        int linearIdx = linearSearch(sorted, target);
        int linearComparisons = comparisonCount;

        int binaryIdx = binarySearch(sorted, target);
        int binaryComparisons = comparisonCount;

        System.out.printf("Target %d: linear index=%d (%d comparisons), "
                        + "binary index=%d (%d comparisons)%n",
                target, linearIdx, linearComparisons, binaryIdx, binaryComparisons);
        // Output: Target 44: linear index=5 (6 comparisons), binary index=5 (2 comparisons)

        int missing = 50;
        int lm = linearSearch(sorted, missing);
        int lc = comparisonCount;
        int bm = binarySearch(sorted, missing);
        int bc = comparisonCount;

        System.out.printf("Target %d (missing): linear=%d (%d comp), binary=%d (%d comp)%n",
                missing, lm, lc, bm, bc);
        // Output: Target 50 (missing): linear=-1 (10 comp), binary=-1 (4 comp)

        // --- Exercise 2.2: Selection Sort ---
        System.out.println("\n=== Exercise 2.2: Selection Sort ===");

        int[] unsorted = {64, 25, 12, 22, 11};
        System.out.println("Before: " + Arrays.toString(unsorted));
        int totalSwaps = selectionSort(unsorted);
        System.out.println("After:  " + Arrays.toString(unsorted));
        System.out.println("Total swaps: " + totalSwaps);
        // Output: Total swaps: 4

        // --- Exercise 2.3: Merge Two Sorted Arrays ---
        System.out.println("\n=== Exercise 2.3: Merge Two Sorted Arrays ===");

        int[] a = {1, 3, 5};
        int[] b = {2, 4, 6, 8};
        int[] merged = merge(a, b);
        System.out.println("a: " + Arrays.toString(a));
        System.out.println("b: " + Arrays.toString(b));
        System.out.println("merged: " + Arrays.toString(merged));
        // Output: merged: [1, 2, 3, 4, 5, 6, 8]

        int[] c = {1, 2, 3};
        int[] d = {4, 5, 6};
        System.out.println("merge({1,2,3},{4,5,6}): " + Arrays.toString(merge(c, d)));
        // Output: merge({1,2,3},{4,5,6}): [1, 2, 3, 4, 5, 6]

        int[] e = {10};
        int[] f = {1, 5, 20};
        System.out.println("merge({10},{1,5,20}): " + Arrays.toString(merge(e, f)));
        // Output: merge({10},{1,5,20}): [1, 5, 10, 20]
    }
}
