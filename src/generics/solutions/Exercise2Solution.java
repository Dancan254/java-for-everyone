package generics.solutions;

import java.util.Arrays;

/**
 * Exercise2Solution.java
 *
 * Solutions for Exercise Set 2: Generic Methods.
 * Covers: declaring type parameters on static methods, type inference at the
 * call site, working with arrays of any reference type, and in-place algorithms
 * written once to serve all element types.
 */
public class Exercise2Solution {

    // -------------------------------------------------------------------------
    // Exercise 2.1 — Generic Swap
    // -------------------------------------------------------------------------

    /**
     * Swaps the elements at positions i and j in an array of any reference type.
     * T is inferred from the array argument — no explicit type specification needed
     * at the call site. T[] arr is the array to modify in place.
     */
    static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // -------------------------------------------------------------------------
    // Exercise 2.2 — Generic Find
    // -------------------------------------------------------------------------

    /**
     * Returns the first element in arr that equals target, or null if not found.
     * Uses equals() — correct for all reference types including String and Double.
     */
    static <T> T findFirst(T[] arr, T target) {
        for (T element : arr) {
            if (element.equals(target)) {
                return element;
            }
        }
        return null;
    }

    /**
     * Returns true if any element in arr equals target.
     * Delegates to findFirst — avoids duplicating the search logic.
     */
    static <T> boolean contains(T[] arr, T target) {
        return findFirst(arr, target) != null;
    }

    // -------------------------------------------------------------------------
    // Exercise 2.3 — Generic Reverse
    // -------------------------------------------------------------------------

    /**
     * Reverses the elements of arr in place by swapping from the ends inward.
     * No second array is created; the swap helper above does the element exchange.
     */
    static <T> void reverse(T[] arr) {
        int left  = 0;
        int right = arr.length - 1;
        while (left < right) {
            swap(arr, left, right);
            left++;
            right--;
        }
    }

    // -------------------------------------------------------------------------
    // main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 2.1: Generic Swap ---

        System.out.println("=== Exercise 2.1: Generic Swap ===");

        String[] names = {"Alice", "Bob", "Carol", "Diana"};
        System.out.println("Before: " + Arrays.toString(names));
        // Output: Before: [Alice, Bob, Carol, Diana]

        // T is inferred as String from the argument type — no <String> needed.
        swap(names, 0, 3);
        System.out.println("After swap(0,3): " + Arrays.toString(names));
        // Output: After swap(0,3): [Diana, Bob, Carol, Alice]

        Integer[] numbers = {10, 20, 30, 40, 50};
        System.out.println("Before: " + Arrays.toString(numbers));
        // Output: Before: [10, 20, 30, 40, 50]

        // T is inferred as Integer from the argument type.
        swap(numbers, 1, 3);
        System.out.println("After swap(1,3): " + Arrays.toString(numbers));
        // Output: After swap(1,3): [10, 40, 30, 20, 50]

        // --- Exercise 2.2: Generic Find ---

        System.out.println("\n=== Exercise 2.2: Generic Find ===");

        String[] words = {"banana", "apple", "cherry", "date"};
        System.out.println(findFirst(words, "cherry"));  // Output: cherry
        System.out.println(findFirst(words, "mango"));   // Output: null

        System.out.println(contains(words, "apple"));    // Output: true
        System.out.println(contains(words, "mango"));    // Output: false

        Double[] prices = {9.99, 14.99, 4.99, 24.99};
        System.out.println(findFirst(prices, 14.99));    // Output: 14.99
        System.out.println(findFirst(prices, 99.99));    // Output: null

        System.out.println(contains(prices, 4.99));      // Output: true
        System.out.println(contains(prices, 0.01));      // Output: false

        // --- Exercise 2.3: Generic Reverse ---

        System.out.println("\n=== Exercise 2.3: Generic Reverse ===");

        String[] sentence = {"the", "quick", "brown", "fox", "jumps"};
        System.out.println("Before: " + Arrays.toString(sentence));
        // Output: Before: [the, quick, brown, fox, jumps]

        reverse(sentence);
        System.out.println("After:  " + Arrays.toString(sentence));
        // Output: After:  [jumps, fox, brown, quick, the]

        Integer[] evens = {2, 4, 6, 8, 10, 12};
        System.out.println("Before: " + Arrays.toString(evens));
        // Output: Before: [2, 4, 6, 8, 10, 12]

        reverse(evens);
        System.out.println("After:  " + Arrays.toString(evens));
        // Output: After:  [12, 10, 8, 6, 4, 2]

        // Edge cases: single-element and empty arrays are already in order.
        String[] single = {"only"};
        reverse(single);
        System.out.println("Single element: " + Arrays.toString(single));
        // Output: Single element: [only]
    }
}
