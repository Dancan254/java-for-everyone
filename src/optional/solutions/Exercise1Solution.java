package optional.solutions;

import java.util.Optional;

/**
 * Exercise1Solution.java
 *
 * Solutions for Exercise Set 1: Creating and Checking Optional.
 * Covers: Optional.of(), Optional.ofNullable(), Optional.empty(),
 * isPresent(), isEmpty(), ifPresent(), and ifPresentOrElse().
 */
public class Exercise1Solution {

    public static void main(String[] args) {
        exercise1_1();
        exercise1_2();
        exercise1_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.1 — Creating Optional Instances
    // -------------------------------------------------------------------------

    static void exercise1_1() {
        System.out.println("=== Exercise 1.1: Creating Optional Instances ===");

        // Optional.of() — the value must be non-null or NullPointerException is thrown.
        Optional<String> present = Optional.of("hello");
        System.out.println("Optional.of(\"hello\"): " + present);       // Output: Optional[hello]
        System.out.println("isPresent: "             + present.isPresent()); // Output: true

        // Optional.ofNullable() — accepts null; returns empty Optional if null.
        Optional<String> nullable = Optional.ofNullable(null);
        System.out.println("Optional.ofNullable(null): " + nullable);   // Output: Optional.empty
        System.out.println("isEmpty: "                   + nullable.isEmpty()); // Output: true

        // Optional.empty() — an explicitly empty Optional.
        Optional<String> empty = Optional.empty();
        System.out.println("Optional.empty(): " + empty);               // Output: Optional.empty
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.2 — isPresent() and isEmpty()
    // -------------------------------------------------------------------------

    static void exercise1_2() {
        System.out.println("=== Exercise 1.2: isPresent() and isEmpty() ===");

        Optional<String> withValue = Optional.of("Java");
        Optional<String> withoutValue = Optional.empty();

        System.out.println("withValue.isPresent():    " + withValue.isPresent());    // true
        System.out.println("withValue.isEmpty():      " + withValue.isEmpty());      // false
        System.out.println("withoutValue.isPresent(): " + withoutValue.isPresent()); // false
        System.out.println("withoutValue.isEmpty():   " + withoutValue.isEmpty());   // true
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.3 — ifPresent() and ifPresentOrElse()
    // -------------------------------------------------------------------------

    static void exercise1_3() {
        System.out.println("=== Exercise 1.3: ifPresent() and ifPresentOrElse() ===");

        Optional<String> name = Optional.of("Alice");
        Optional<String> noName = Optional.empty();

        // ifPresent() — only runs the consumer if a value is present.
        name.ifPresent(n -> System.out.println("Hello, " + n + "!")); // Output: Hello, Alice!
        noName.ifPresent(n -> System.out.println("This won't print."));

        // ifPresentOrElse() — runs the consumer if present, the runnable if absent.
        name.ifPresentOrElse(
                n -> System.out.println("Name: " + n),
                ()  -> System.out.println("No name provided")
        );
        // Output: Name: Alice

        noName.ifPresentOrElse(
                n -> System.out.println("Name: " + n),
                ()  -> System.out.println("No name provided")
        );
        // Output: No name provided
        System.out.println();
    }
}
