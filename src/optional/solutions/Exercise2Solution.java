package optional.solutions;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Exercise2Solution.java
 *
 * Solutions for Exercise Set 2: Retrieving Values from Optional.
 * Covers: orElse(), orElseGet(), orElseThrow(), and the danger of get().
 */
public class Exercise2Solution {

    public static void main(String[] args) {
        exercise2_1();
        exercise2_2();
        exercise2_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 2.1 — orElse() vs orElseGet()
    // -------------------------------------------------------------------------

    static void exercise2_1() {
        System.out.println("=== Exercise 2.1: orElse() vs orElseGet() ===");

        Optional<String> empty = Optional.empty();

        // orElse() — always evaluates the fallback expression, even when a value is present.
        String result1 = empty.orElse("default");
        System.out.println("orElse:    " + result1); // Output: default

        // orElseGet() — evaluates the Supplier only when no value is present (lazy).
        // Prefer this when the fallback is expensive to compute.
        String result2 = empty.orElseGet(() -> "computed default");
        System.out.println("orElseGet: " + result2); // Output: computed default

        // With a present Optional, orElse still evaluates but discards the result.
        Optional<String> present = Optional.of("actual");
        String result3 = present.orElse("ignored default");
        System.out.println("present.orElse: " + result3); // Output: actual
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 2.2 — orElseThrow()
    // -------------------------------------------------------------------------

    static void exercise2_2() {
        System.out.println("=== Exercise 2.2: orElseThrow() ===");

        Optional<String> present = Optional.of("found");
        String value = present.orElseThrow(() -> new RuntimeException("No value!"));
        System.out.println("orElseThrow (present): " + value); // Output: found

        Optional<String> empty = Optional.empty();
        try {
            empty.orElseThrow(() -> new IllegalStateException("Value required but absent"));
        } catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage()); // Output: Value required but absent
        }
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 2.3 — Danger of get()
    // -------------------------------------------------------------------------

    static void exercise2_3() {
        System.out.println("=== Exercise 2.3: Danger of get() ===");

        Optional<String> present = Optional.of("safe");
        System.out.println("get() on present: " + present.get()); // Output: safe

        // Calling get() on an empty Optional throws NoSuchElementException.
        Optional<String> empty = Optional.empty();
        try {
            String bad = empty.get(); // Never do this without checking isPresent() first.
            System.out.println(bad);
        } catch (NoSuchElementException e) {
            System.out.println("get() on empty threw: NoSuchElementException");
        }

        // Prefer orElse / orElseGet / orElseThrow over get() in production code.
        System.out.println();
    }
}
