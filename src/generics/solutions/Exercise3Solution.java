package generics.solutions;

import java.util.Arrays;
import java.util.List;

/**
 * Exercise3Solution.java
 *
 * Solutions for Exercise Set 3: Bounded Type Parameters.
 * Covers: upper bounds with extends, accessing bound-type methods inside generic
 * code, multiple bounds with &, and IllegalArgumentException for invalid input.
 */
public class Exercise3Solution {

    // -------------------------------------------------------------------------
    // Exercise 3.1 — Generic Max and Min
    // -------------------------------------------------------------------------

    /**
     * Returns the larger of two values.
     * The bound <T extends Comparable<T>> makes compareTo available inside
     * this method. Without the bound, T would erase to Object, which has no
     * compareTo method, and the call would fail to compile.
     */
    static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    /**
     * Returns the smaller of two values using the same bound.
     */
    static <T extends Comparable<T>> T min(T a, T b) {
        return a.compareTo(b) <= 0 ? a : b;
    }

    // -------------------------------------------------------------------------
    // Exercise 3.2 — Sum of a Number List
    // -------------------------------------------------------------------------

    /**
     * Returns the sum of all elements in list as a double.
     * The bound <T extends Number> makes doubleValue() available on each element.
     * Without it, T would erase to Object, which has no doubleValue() method.
     */
    static <T extends Number> double sum(List<T> list) {
        double total = 0;
        for (T element : list) {
            total += element.doubleValue();
        }
        return total;
    }

    // -------------------------------------------------------------------------
    // Exercise 3.3 — Find Maximum in an Array
    // -------------------------------------------------------------------------

    /**
     * Returns the maximum element from a non-null, non-empty array.
     *
     * The multiple bounds T extends Comparable<T> & java.io.Serializable require
     * that T supports both natural ordering and serialisation. String and Integer
     * satisfy both. A plain custom class without implementing either interface
     * would be rejected at compile time.
     *
     * @throws IllegalArgumentException if arr is null or empty
     */
    static <T extends Comparable<T> & java.io.Serializable> T findMax(T[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be null or empty.");
        }
        T max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(max) > 0) {
                max = arr[i];
            }
        }
        return max;
    }

    // -------------------------------------------------------------------------
    // main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 3.1: max and min ---

        System.out.println("=== Exercise 3.1: Generic Max and Min ===");

        System.out.println(max(42, 99));             // Output: 99
        System.out.println(min(42, 99));             // Output: 42

        System.out.println(max(3.14, 2.71));         // Output: 3.14
        System.out.println(min(3.14, 2.71));         // Output: 2.71

        System.out.println(max("apple", "mango"));   // Output: mango
        System.out.println(min("apple", "mango"));   // Output: apple

        // The line below would be a compile error — Object does not implement Comparable.
        // max(new Object(), new Object()); // Compiler error: Object is not within bounds

        // --- Exercise 3.2: sum ---

        System.out.println("\n=== Exercise 3.2: Sum of a Number List ===");

        List<Integer> ints    = Arrays.asList(1, 2, 3, 4, 5);
        List<Double>  doubles = Arrays.asList(1.5, 2.5, 3.0);
        List<Long>    longs   = Arrays.asList(100L, 200L, 300L);

        System.out.println(sum(ints));    // Output: 15.0
        System.out.println(sum(doubles)); // Output: 7.0
        System.out.println(sum(longs));   // Output: 600.0

        // --- Exercise 3.3: findMax ---

        System.out.println("\n=== Exercise 3.3: Find Maximum in an Array ===");

        String[]  words  = {"banana", "apple", "cherry", "date"};
        Integer[] values = {3, 1, 4, 1, 5, 9, 2, 6};

        System.out.println(findMax(words));   // Output: date
        System.out.println(findMax(values));  // Output: 9

        // String and Integer both implement Comparable<T> (natural ordering)
        // and Serializable (part of their standard library implementation).
        // A custom class like: class Point { int x, y; }
        // would be rejected by the compiler because it implements neither.

        // Confirm the guard clause works.
        try {
            findMax(new Integer[]{});
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
            // Output: Caught: Array must not be null or empty.
        }
    }
}
