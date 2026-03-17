package methods.solutions;

import java.util.Arrays;

/**
 * Exercise3Solution.java
 *
 * Solutions for Exercise Set 3: Array Methods.
 * Covers: array traversal, min/max/sum/average, array transformation,
 * filtering with manual size counting, and varargs (...) syntax.
 *
 * Key note on filterEven: Java arrays have a fixed size at creation time,
 * so we make two passes — one to count qualifying elements, one to copy them.
 */
public class Exercise3Solution {

    public static void main(String[] args) {

        int[] data = {3, 7, 2, 9, 4, 6, 1, 8, 5};

        // === Exercise 3.1: ArrayOperations ===
        System.out.println("=== Exercise 3.1: ArrayOperations ===");
        System.out.println("Array: " + Arrays.toString(data));
        // Output: Array: [3, 7, 2, 9, 4, 6, 1, 8, 5]

        System.out.println("findMax     = " + ArrayOperations.findMax(data));
        // Output: findMax     = 9

        System.out.println("findMin     = " + ArrayOperations.findMin(data));
        // Output: findMin     = 1

        System.out.printf("average     = %.2f%n", ArrayOperations.calculateAverage(data));
        // Output: average     = 5.00

        System.out.println("sumArray    = " + ArrayOperations.sumArray(data));
        // Output: sumArray    = 45

        // === Exercise 3.2: ArrayTransformation ===
        System.out.println("\n=== Exercise 3.2: ArrayTransformation ===");
        System.out.println("Original:      " + Arrays.toString(data));
        // Output: Original:      [3, 7, 2, 9, 4, 6, 1, 8, 5]

        System.out.println("reversed:      " + Arrays.toString(ArrayTransformation.reverseArray(data)));
        // Output: reversed:      [5, 8, 1, 6, 4, 9, 2, 7, 3]

        System.out.println("doubled:       " + Arrays.toString(ArrayTransformation.doubleElements(data)));
        // Output: doubled:       [6, 14, 4, 18, 8, 12, 2, 16, 10]

        System.out.println("evens only:    " + Arrays.toString(ArrayTransformation.filterEven(data)));
        // Output: evens only:    [2, 4, 6, 8]

        // Original array must not have been modified — these methods return new arrays.
        System.out.println("Original unchanged: " + Arrays.toString(data));
        // Output: Original unchanged: [3, 7, 2, 9, 4, 6, 1, 8, 5]

        // === Exercise 3.3: Varargs ===
        System.out.println("\n=== Exercise 3.3: Varargs ===");

        System.out.println("findMaximum(3,1,9,4,7)         = "
                + VarargMethods.findMaximum(3, 1, 9, 4, 7));
        // Output: findMaximum(3,1,9,4,7)         = 9

        System.out.println("concatenate(\"Java\",\"is\",\"fun\") = \""
                + VarargMethods.concatenate("Java", "is", "fun") + "\"");
        // Output: concatenate("Java","is","fun") = "Java is fun"

        System.out.printf("sumAll(1.5, 2.5, 3.0)          = %.1f%n",
                VarargMethods.sumAll(1.5, 2.5, 3.0));
        // Output: sumAll(1.5, 2.5, 3.0)          = 7.0
    }

    // -------------------------------------------------------------------------
    // Inner classes
    // -------------------------------------------------------------------------

    /**
     * Single-pass traversal methods.
     * findMax and findMin seed their accumulator with the first element so they
     * work correctly even when all values are negative or very large.
     */
    static class ArrayOperations {

        static int findMax(int[] arr) {
            int max = arr[0]; // seed with first element, not Integer.MIN_VALUE,
                              // so the result is always an actual array value
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] > max) {
                    max = arr[i];
                }
            }
            return max;
        }

        static int findMin(int[] arr) {
            int min = arr[0];
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] < min) {
                    min = arr[i];
                }
            }
            return min;
        }

        static double calculateAverage(int[] arr) {
            // Cast to double before dividing to avoid integer truncation.
            return (double) sumArray(arr) / arr.length;
        }

        static int sumArray(int[] arr) {
            int sum = 0;
            for (int n : arr) {
                sum += n;
            }
            return sum;
        }
    }

    /**
     * Non-destructive transformation methods — the original array is never modified.
     * Each method allocates and returns a new array.
     *
     * filterEven uses two passes:
     *   Pass 1 — count even numbers so the result array can be sized exactly.
     *   Pass 2 — copy qualifying elements into the result.
     */
    static class ArrayTransformation {

        static int[] reverseArray(int[] arr) {
            int[] result = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[arr.length - 1 - i];
            }
            return result;
        }

        static int[] doubleElements(int[] arr) {
            int[] result = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[i] * 2;
            }
            return result;
        }

        static int[] filterEven(int[] arr) {
            // Pass 1: count even elements so we can size the result array.
            int count = 0;
            for (int n : arr) {
                if (n % 2 == 0) {
                    count++;
                }
            }

            // Pass 2: populate the result array.
            int[] result = new int[count];
            int   idx    = 0;
            for (int n : arr) {
                if (n % 2 == 0) {
                    result[idx++] = n;
                }
            }
            return result;
        }
    }

    /**
     * Varargs methods — the "..." syntax lets callers pass any number of arguments.
     * Internally the JVM passes them as a regular array, so standard array logic applies.
     *
     * findMaximum seeds max with values[0] to handle all-negative input correctly.
     * concatenate uses a StringBuilder and appends a space between words.
     */
    static class VarargMethods {

        static int findMaximum(int... numbers) {
            int max = numbers[0];
            for (int i = 1; i < numbers.length; i++) {
                if (numbers[i] > max) {
                    max = numbers[i];
                }
            }
            return max;
        }

        static String concatenate(String... words) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < words.length; i++) {
                if (i > 0) {
                    sb.append(" "); // add separator before every word except the first
                }
                sb.append(words[i]);
            }
            return sb.toString();
        }

        static double sumAll(double... values) {
            double total = 0;
            for (double v : values) {
                total += v;
            }
            return total;
        }
    }
}
