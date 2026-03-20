package optional.solutions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Exercise4Solution.java
 *
 * Solutions for Exercise Set 4: Real-World Optional Patterns.
 * Covers: returning Optional from methods instead of null, chaining,
 * and Optional::stream integration for filtering in stream pipelines.
 */
public class Exercise4Solution {

    // -------------------------------------------------------------------------
    // Exercise 4.1 — Returning Optional instead of null
    // -------------------------------------------------------------------------

    static Optional<String> findUserById(int id) {
        // Simulated "database" of users indexed by ID.
        List<String> users = List.of("Alice", "Bob", "Charlie");
        if (id >= 0 && id < users.size()) {
            return Optional.of(users.get(id));
        }
        return Optional.empty(); // Never return null — return Optional.empty() instead.
    }

    static void exercise4_1() {
        System.out.println("=== Exercise 4.1: Returning Optional from Methods ===");

        Optional<String> found = findUserById(1);
        found.ifPresentOrElse(
                u -> System.out.println("Found user: " + u),
                ()  -> System.out.println("User not found")
        );
        // Output: Found user: Bob

        Optional<String> notFound = findUserById(99);
        notFound.ifPresentOrElse(
                u -> System.out.println("Found user: " + u),
                ()  -> System.out.println("User not found")
        );
        // Output: User not found
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 4.2 — Optional::stream for filtering in stream pipelines
    // -------------------------------------------------------------------------

    static void exercise4_2() {
        System.out.println("=== Exercise 4.2: Optional::stream in a Stream Pipeline ===");

        List<Optional<String>> maybeNames = List.of(
                Optional.of("Alice"),
                Optional.empty(),
                Optional.of("Bob"),
                Optional.empty(),
                Optional.of("Charlie")
        );

        // Optional::stream converts a present Optional to a single-element stream
        // and an empty Optional to an empty stream. flatMap merges them all.
        List<String> names = maybeNames.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        System.out.println("Non-empty names: " + names);
        // Output: [Alice, Bob, Charlie]
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 4.3 — Chaining Optional operations
    // -------------------------------------------------------------------------

    static Optional<String> lookupConfig(String key) {
        if ("db.host".equals(key)) return Optional.of("  localhost  ");
        return Optional.empty();
    }

    static void exercise4_3() {
        System.out.println("=== Exercise 4.3: Chaining Optional Operations ===");

        // Chain map() calls to transform the value step by step.
        String host = lookupConfig("db.host")
                .map(String::trim)
                .map(String::toUpperCase)
                .orElse("UNKNOWN");

        System.out.println("DB Host: " + host); // Output: LOCALHOST

        String missing = lookupConfig("db.port")
                .map(String::trim)
                .orElse("5432");

        System.out.println("DB Port: " + missing); // Output: 5432 (default)
        System.out.println();
    }

    public static void main(String[] args) {
        exercise4_1();
        exercise4_2();
        exercise4_3();
    }
}
