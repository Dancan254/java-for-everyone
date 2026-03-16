import java.util.Arrays;

/**
 * Exercise3Solution.java
 *
 * Solutions for Arrays Exercise Set 3: Array Transformations.
 * Covers: removing duplicates in-place style, set intersection and union, sliding window maximum.
 */
public class Exercise3Solution {

    // --- Remove Duplicates ---

    /**
     * Returns a new array containing only the first occurrence of each value.
     * Preserves the original order of first appearances.
     *
     * Strategy: for each element, do a linear scan of the elements already kept
     * to see if this value has been seen before. If not, add it.
     * This is O(n^2) but requires no extra data structures beyond arrays.
     */
    static int[] removeDuplicates(int[] arr) {
        // Allocate a work array large enough to hold all elements in the worst case.
        int[] temp = new int[arr.length];
        int   count = 0; // Number of unique elements found so far.

        for (int value : arr) {
            // Check whether this value is already in temp[0..count-1].
            boolean alreadySeen = false;
            for (int i = 0; i < count; i++) {
                if (temp[i] == value) {
                    alreadySeen = true;
                    break;
                }
            }

            if (!alreadySeen) {
                temp[count++] = value; // Record this new unique value.
            }
        }

        // Copy only the unique portion into a correctly-sized result array.
        return Arrays.copyOf(temp, count);
    }

    // --- Intersection ---

    /**
     * Returns an array containing all values present in both a and b, with no duplicates.
     * For each element of a, check whether it appears in b and has not already been added.
     */
    static int[] intersection(int[] a, int[] b) {
        int[] temp  = new int[Math.min(a.length, b.length)];
        int   count = 0;

        for (int valueA : a) {
            // Only proceed if valueA is in b.
            if (contains(b, valueA) && !contains(Arrays.copyOf(temp, count), valueA)) {
                temp[count++] = valueA;
            }
        }

        return Arrays.copyOf(temp, count);
    }

    // --- Union ---

    /**
     * Returns an array containing all values present in either a or b, with no duplicates.
     * Strategy: start with all unique values from a, then add any value from b not yet included.
     */
    static int[] union(int[] a, int[] b) {
        int[] temp  = new int[a.length + b.length];
        int   count = 0;

        // Add all elements from a (de-duplicating within a as well).
        for (int value : a) {
            if (!contains(Arrays.copyOf(temp, count), value)) {
                temp[count++] = value;
            }
        }

        // Add elements from b that are not already in temp.
        for (int value : b) {
            if (!contains(Arrays.copyOf(temp, count), value)) {
                temp[count++] = value;
            }
        }

        return Arrays.copyOf(temp, count);
    }

    // --- Sliding Window Maximum ---

    /**
     * Returns an array where result[i] is the maximum value in arr[i..i+k-1].
     * There are (arr.length - k + 1) windows.
     *
     * This naive approach is O(n*k). For large inputs a deque-based O(n) algorithm exists,
     * but the nested loop version is the most instructive for learning array operations.
     */
    static int[] slidingMax(int[] arr, int k) {
        int windowCount = arr.length - k + 1;
        int[] result    = new int[windowCount];

        for (int start = 0; start < windowCount; start++) {
            // Find the maximum within the window arr[start..start+k-1].
            int windowMax = arr[start];
            for (int j = start + 1; j < start + k; j++) {
                if (arr[j] > windowMax) {
                    windowMax = arr[j];
                }
            }
            result[start] = windowMax;
        }

        return result;
    }

    // --- Utility ---

    /** Returns true if arr contains the given value. */
    static boolean contains(int[] arr, int value) {
        for (int element : arr) {
            if (element == value) return true;
        }
        return false;
    }

    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 3.1: Remove Duplicates ---
        System.out.println("=== Exercise 3.1: Remove Duplicates ===");

        int[] withDupes = {4, 1, 2, 4, 3, 1, 5};
        int[] unique    = removeDuplicates(withDupes);
        System.out.println("Input:  " + Arrays.toString(withDupes));
        System.out.println("Output: " + Arrays.toString(unique));
        // Output: Output: [4, 1, 2, 3, 5]

        int[] allSame = {7, 7, 7, 7};
        System.out.println("All same: " + Arrays.toString(removeDuplicates(allSame)));
        // Output: All same: [7]

        int[] alreadyUnique = {1, 2, 3};
        System.out.println("No dupes: " + Arrays.toString(removeDuplicates(alreadyUnique)));
        // Output: No dupes: [1, 2, 3]

        // --- Exercise 3.2: Intersection and Union ---
        System.out.println("\n=== Exercise 3.2: Intersection and Union ===");

        int[] a = {1, 2, 3, 4, 5};
        int[] b = {3, 4, 5, 6, 7};

        System.out.println("a:            " + Arrays.toString(a));
        System.out.println("b:            " + Arrays.toString(b));
        System.out.println("intersection: " + Arrays.toString(intersection(a, b)));
        // Output: intersection: [3, 4, 5]

        System.out.println("union:        " + Arrays.toString(union(a, b)));
        // Output: union:        [1, 2, 3, 4, 5, 6, 7]

        // Edge case: no overlap.
        int[] c = {1, 2};
        int[] d = {3, 4};
        System.out.println("intersection (no overlap): " + Arrays.toString(intersection(c, d)));
        // Output: intersection (no overlap): []

        // --- Exercise 3.3: Sliding Window Maximum ---
        System.out.println("\n=== Exercise 3.3: Sliding Window Maximum ===");

        int[] arr = {1, 3, -1, -3, 5, 3, 6, 7};
        int   k   = 3;
        int[] maxes = slidingMax(arr, k);
        System.out.println("Array:               " + Arrays.toString(arr));
        System.out.println("Window size k=" + k + ":   " + Arrays.toString(maxes));
        // Output: Window size k=3:   [3, 3, 5, 5, 6, 7]

        int[] arr2 = {2, 1, 5, 3, 6, 4, 8, 7};
        System.out.println("Array:               " + Arrays.toString(arr2));
        System.out.println("Window size k=4:   " + Arrays.toString(slidingMax(arr2, 4)));
        // Output: Window size k=4:   [5, 6, 6, 8, 8]
    }
}
