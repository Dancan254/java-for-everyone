package generics.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Exercise4Solution.java
 *
 * Solutions for Exercise Set 4: Wildcards.
 * Covers: unbounded wildcard <?>, upper-bounded wildcard <? extends T>,
 * lower-bounded wildcard <? super T>, and the PECS principle
 * (Producer Extends, Consumer Super).
 */
public class Exercise4Solution {

    // -------------------------------------------------------------------------
    // Exercise 4.1 — Print Any List (unbounded wildcard)
    // -------------------------------------------------------------------------

    /**
     * Prints every element of any list with its zero-based index.
     *
     * List<?> (unbounded wildcard) accepts a list of any element type.
     * Inside the method, elements can only be read as Object because the
     * concrete type is unknown. You cannot add anything to a List<?>.
     *
     * Why not List<Object>? Because generic types are invariant.
     * List<String> is NOT a List<Object>, so passing a List<String> to a
     * method that expects List<Object> would be a compile error.
     * List<?> accepts all parameterised variants of List.
     */
    static void printAll(List<?> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("[" + i + "] " + list.get(i));
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 4.2 — Average of Numeric Lists (upper-bounded wildcard)
    // -------------------------------------------------------------------------

    /**
     * Returns the arithmetic mean of a list whose element type is Number
     * or any subclass of Number (Integer, Double, Long, etc.).
     *
     * <? extends Number> is an upper bound. It is a "producer" wildcard —
     * the list produces Number values for us to read via doubleValue().
     * We CANNOT call list.add(...) here because the compiler does not know
     * the precise subtype: adding an Integer to a List<? extends Number>
     * would break type safety if the list happened to be a List<Double>.
     */
    static double average(List<? extends Number> list) {
        if (list.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        return sum / list.size();
    }

    // -------------------------------------------------------------------------
    // Exercise 4.3 — Fill a List (lower-bounded wildcard) + PECS copy
    // -------------------------------------------------------------------------

    /**
     * Adds count consecutive integers (1, 2, ..., count) to list.
     *
     * <? super Integer> is a lower bound. It accepts List<Integer>,
     * List<Number>, and List<Object> — any list whose element type is
     * a supertype of Integer. This is a "consumer" wildcard: the list
     * consumes the Integer values we write into it. We can only read
     * back elements as Object because the precise supertype is unknown.
     */
    static void fillWith(List<? super Integer> list, int count) {
        for (int i = 1; i <= count; i++) {
            list.add(i);
        }
    }

    /**
     * Copies all elements from src into dest.
     *
     * PECS in a single method signature:
     *   src  produces values we read   -> <? extends Number> (Producer Extends)
     *   dest consumes values we write  -> <? super Number>   (Consumer Super)
     *
     * This allows, for example, copying from a List<Integer> into a List<Number>
     * because Integer extends Number and Number is a supertype of Number.
     */
    static void copyNumbers(List<? extends Number> src, List<? super Number> dest) {
        for (Number item : src) {
            dest.add(item);
        }
    }

    // -------------------------------------------------------------------------
    // main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 4.1: printAll ---

        System.out.println("=== Exercise 4.1: Print Any List ===");

        List<String>  names   = Arrays.asList("Alice", "Bob", "Carol");
        List<Integer> numbers = Arrays.asList(10, 20, 30);
        List<Double>  prices  = Arrays.asList(4.99, 14.99, 24.99);

        System.out.println("-- Strings --");
        printAll(names);
        // Output:
        // [0] Alice
        // [1] Bob
        // [2] Carol

        System.out.println("-- Integers --");
        printAll(numbers);
        // Output:
        // [0] 10
        // [1] 20
        // [2] 30

        System.out.println("-- Doubles --");
        printAll(prices);
        // Output:
        // [0] 4.99
        // [1] 14.99
        // [2] 24.99

        // The following would be a compile error — List<Object> is not a wildcard;
        // a List<String> cannot be passed where List<Object> is expected:
        // static void printAllWrong(List<Object> list) { ... }
        // printAllWrong(names); // Compiler error: incompatible types

        // --- Exercise 4.2: average ---

        System.out.println("\n=== Exercise 4.2: Average of Numeric Lists ===");

        List<Integer> scores  = Arrays.asList(80, 90, 75, 95);
        List<Double>  ratings = Arrays.asList(4.5, 3.8, 5.0, 4.2);

        System.out.printf("Average score:  %.2f%n", average(scores));
        // Output: Average score:  85.00

        System.out.printf("Average rating: %.2f%n", average(ratings));
        // Output: Average rating: 4.38

        // The following would be a compile error — String does not extend Number:
        // List<String> words = Arrays.asList("a", "b");
        // average(words); // Compiler error: String is not within bounds

        // Cannot add inside average() because the exact subtype is unknown:
        // list.add(42); // Compiler error: no suitable method found for add(int)

        // --- Exercise 4.3: fillWith and copyNumbers ---

        System.out.println("\n=== Exercise 4.3: Fill a List and PECS Copy ===");

        List<Integer> intList    = new ArrayList<>();
        List<Number>  numList    = new ArrayList<>();
        List<Object>  objectList = new ArrayList<>();

        fillWith(intList,    5);
        fillWith(numList,    5);
        fillWith(objectList, 5);

        System.out.println(intList);    // Output: [1, 2, 3, 4, 5]
        System.out.println(numList);    // Output: [1, 2, 3, 4, 5]
        System.out.println(objectList); // Output: [1, 2, 3, 4, 5]

        // PECS copy: Integer source -> Number destination.
        List<Integer> source      = Arrays.asList(100, 200, 300);
        List<Number>  destination = new ArrayList<>();

        copyNumbers(source, destination);
        System.out.println(destination); // Output: [100, 200, 300]

        // Double source -> Number destination also works.
        List<Double>  doubleSource = Arrays.asList(1.1, 2.2, 3.3);
        List<Number>  numDest      = new ArrayList<>();
        copyNumbers(doubleSource, numDest);
        System.out.println(numDest); // Output: [1.1, 2.2, 3.3]
    }
}
