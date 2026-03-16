import java.util.Arrays;

/**
 * Exercise1Solution.java
 *
 * Solutions for Arrays Exercise Set 1: Array Fundamentals.
 * Covers: min/max/sum/average/range computation, array rotation with modulo, frequency counting.
 */
public class Exercise1Solution {

    // --- Statistics helper ---

    /** Returns the minimum value in arr. Throws if arr is null or empty. */
    static int min(int[] arr) {
        validateArray(arr);
        int min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) min = arr[i];
        }
        return min;
    }

    /** Returns the maximum value in arr. */
    static int max(int[] arr) {
        validateArray(arr);
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
        }
        return max;
    }

    /** Returns the sum of all elements in arr. */
    static long sum(int[] arr) {
        validateArray(arr);
        long total = 0;
        for (int value : arr) {
            total += value; // Use long to avoid integer overflow on large arrays.
        }
        return total;
    }

    /** Returns the average (mean) of all elements as a double. */
    static double average(int[] arr) {
        return (double) sum(arr) / arr.length;
    }

    /** Returns max - min (the statistical range of the data). */
    static int range(int[] arr) {
        return max(arr) - min(arr);
    }

    private static void validateArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }
    }

    // --- Rotation ---

    /**
     * Returns a new array with all elements shifted left by `positions` places.
     * Elements that fall off the left wrap around to the right end.
     *
     * The key insight: element at index i moves to index (i - positions + length) % length.
     * Equivalently, the new array's index j gets the element from (j + positions) % length.
     */
    static int[] rotateLeft(int[] arr, int positions) {
        int n = arr.length;
        // Normalise positions to avoid out-of-range shifts (e.g., rotating by n is a no-op).
        positions = positions % n;
        if (positions < 0) positions += n; // Handle negative positions gracefully.

        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            // The element that belongs at position i after a left-rotation by `positions`
            // is the element that was at position (i + positions) in the original array.
            result[i] = arr[(i + positions) % n];
        }
        return result;
    }

    // --- Frequency Count ---

    /**
     * Returns a frequency array where result[i] is the count of value i in arr.
     * Assumes all values are in the range 0–99.
     */
    static int[] frequencyCount(int[] arr) {
        int[] freq = new int[100]; // Indices 0–99 correspond to values 0–99.
        for (int value : arr) {
            freq[value]++; // Increment the bucket for this value.
        }
        return freq;
    }

    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 1.1: Descriptive Statistics ---
        System.out.println("=== Exercise 1.1: Descriptive Statistics ===");

        int[] data = {14, 3, 88, 42, 7, 55, 21, 99, 6, 73};
        System.out.println("Data: " + Arrays.toString(data));
        System.out.println("min:     " + min(data));      // Output: min:     3
        System.out.println("max:     " + max(data));      // Output: max:     99
        System.out.println("sum:     " + sum(data));      // Output: sum:     408
        System.out.printf ("average: %.2f%n", average(data)); // Output: average: 40.80
        System.out.println("range:   " + range(data));   // Output: range:   96

        // --- Exercise 1.2: Array Rotation ---
        System.out.println("\n=== Exercise 1.2: Array Rotation ===");

        int[] original = {1, 2, 3, 4, 5};
        System.out.println("Original:          " + Arrays.toString(original));

        int[] rotated2 = rotateLeft(original, 2);
        System.out.println("Rotated left by 2: " + Arrays.toString(rotated2));
        // Output: Rotated left by 2: [3, 4, 5, 1, 2]

        int[] rotated5 = rotateLeft(original, 5);
        System.out.println("Rotated left by 5: " + Arrays.toString(rotated5));
        // Output: Rotated left by 5: [1, 2, 3, 4, 5]  (full rotation = no change)

        int[] rotated7 = rotateLeft(original, 7);
        System.out.println("Rotated left by 7: " + Arrays.toString(rotated7));
        // Output: Rotated left by 7: [3, 4, 5, 1, 2]  (7 % 5 = 2, same as rotating by 2)

        // --- Exercise 1.3: Frequency Count ---
        System.out.println("\n=== Exercise 1.3: Frequency Count ===");

        int[] values = {3, 1, 3, 0, 2, 3};
        int[] freq = frequencyCount(values);
        System.out.println("Data: " + Arrays.toString(values));
        System.out.println("Frequency of 0: " + freq[0]); // Output: 1
        System.out.println("Frequency of 1: " + freq[1]); // Output: 1
        System.out.println("Frequency of 2: " + freq[2]); // Output: 1
        System.out.println("Frequency of 3: " + freq[3]); // Output: 3

        // Demonstration with a larger dataset.
        int[] dice = {1, 3, 5, 2, 4, 6, 3, 3, 1, 5, 2, 4, 3, 6, 1};
        int[] diceFreq = frequencyCount(dice);
        System.out.println("\nDice rolls: " + Arrays.toString(dice));
        for (int face = 1; face <= 6; face++) {
            System.out.println("  Face " + face + " appeared " + diceFreq[face] + " time(s)");
        }
        // Output:
        //   Face 1 appeared 3 time(s)
        //   Face 2 appeared 2 time(s)
        //   Face 3 appeared 4 time(s)
        //   Face 4 appeared 2 time(s)
        //   Face 5 appeared 2 time(s)
        //   Face 6 appeared 2 time(s)
    }
}
