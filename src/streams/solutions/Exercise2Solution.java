package streams.solutions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Exercise2Solution.java
 *
 * Solutions for Exercise Set 2: Intermediate Operations.
 * Covers: filter, map, flatMap, distinct, sorted, limit, skip,
 * and composing these operations in multi-step pipelines.
 */
public class Exercise2Solution {

    public static void main(String[] args) {

        // --- Exercise 2.1: filter and map ---
        System.out.println("=== Exercise 2.1: filter and map ===");
        exercise1();

        // --- Exercise 2.2: flatMap ---
        System.out.println("\n=== Exercise 2.2: flatMap ===");
        exercise2();

        // --- Exercise 2.3: distinct, sorted, limit, and skip ---
        System.out.println("\n=== Exercise 2.3: distinct, sorted, limit, skip ===");
        exercise3();
    }

    // ---------------------------------------------------------------------------
    // Exercise 2.1
    // ---------------------------------------------------------------------------

    /**
     * Filters names to those containing a space (full names), maps to uppercase,
     * and sorts. Then separately filters positive odd integers and collects their squares.
     */
    static void exercise1() {
        // Part A: full names uppercased and sorted.
        List<String> names = List.of(
                "Alice Johnson", "Bob", "Carol Smith", "Dave",
                "Eve Martinez", "Frank", "Grace Lee", "Alice Johnson"
        );

        List<String> fullNamesUpper = names.stream()
                .filter(name -> name.contains(" "))   // keep only names with a space
                .map(String::toUpperCase)              // uppercase
                .sorted()                              // alphabetical
                .collect(Collectors.toList());

        System.out.println("Full names (upper, sorted): " + fullNamesUpper);
        // Output: Full names (upper, sorted): [ALICE JOHNSON, ALICE JOHNSON, CAROL SMITH, EVE MARTINEZ, GRACE LEE]

        // Part B: positive odd integers — collect their squares.
        List<Integer> numbers = List.of(-7, -3, 0, 1, 4, 5, 6, 7, -1, 9, 12, 15);

        List<Integer> oddSquares = numbers.stream()
                .filter(n -> n > 0 && n % 2 != 0)    // positive and odd
                .map(n -> n * n)                       // square each
                .collect(Collectors.toList());

        System.out.println("Squares of positive odds: " + oddSquares);
        // Output: Squares of positive odds: [1, 25, 49, 81, 225]
    }

    // ---------------------------------------------------------------------------
    // Exercise 2.2
    // ---------------------------------------------------------------------------

    /**
     * Uses flatMap to split sentences into words, then applies a second pipeline
     * to filter, deduplicate, and sort the word list.
     *
     * Why map would not work: map(s -> s.split(" ")) returns Stream<String[]>,
     * producing one String[] per sentence rather than a flat stream of individual
     * words. flatMap collapses the inner streams so every word becomes a top-level
     * element in a single Stream<String>.
     */
    static void exercise2() {
        List<String> sentences = List.of(
                "Streams make data processing expressive and readable.",
                "Filter and map are the most common stream operations.",
                "FlatMap is essential when each element produces multiple results.",
                "Readable code is easier to maintain and understand.",
                "Operations on streams are lazy until a terminal operation runs."
        );

        // Step 1: flatMap splits each sentence into words; collect all into one list.
        List<String> allWords = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split("\\s+")))
                .collect(Collectors.toList());

        System.out.println("All words (flat list, first 10): " +
                allWords.subList(0, Math.min(10, allWords.size())));
        // Output: All words (flat list, first 10): [Streams, make, data, processing, expressive, and, readable., Filter, and, map]

        // Step 2: filter short words, lowercase, deduplicate, sort.
        List<String> cleanedWords = allWords.stream()
                .filter(w -> w.length() >= 4)                          // keep words >= 4 chars
                .map(w -> w.replaceAll("[^a-zA-Z]", "").toLowerCase()) // strip punctuation, lowercase
                .filter(w -> !w.isEmpty())                             // remove anything that became empty
                .distinct()                                            // remove duplicates
                .sorted()                                              // alphabetical
                .collect(Collectors.toList());

        System.out.println("Cleaned distinct words (>=4 chars): " + cleanedWords);
        // Output: Cleaned distinct words (>=4 chars): [and, code, common, data, each, easier, element, essential, expressive, filter, flatmap, lazy, make, maintain, most, multiple, operations, produces, processing, readable, results, runs, streams, terminal, understand, until, when]
    }

    // ---------------------------------------------------------------------------
    // Exercise 2.3
    // ---------------------------------------------------------------------------

    /**
     * Demonstrates distinct, sorted (ascending and descending), skip, and limit
     * on the same source list with four separate pipelines.
     */
    static void exercise3() {
        List<Integer> numbers = List.of(5, 3, 8, 3, 1, 9, 2, 5, 7, 1, 4, 6, 8, 2);

        // 1. Remove duplicates, sort ascending.
        List<Integer> uniqueSorted = numbers.stream()
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Distinct, sorted ascending: " + uniqueSorted);
        // Output: Distinct, sorted ascending: [1, 2, 3, 4, 5, 6, 7, 8, 9]

        // 2. Skip first 4, take next 5.
        List<Integer> page = numbers.stream()
                .skip(4)
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("skip(4).limit(5): " + page);
        // Output: skip(4).limit(5): [1, 9, 2, 5, 7]

        // 3. Distinct, sorted descending, top 3 values.
        List<Integer> top3 = numbers.stream()
                .distinct()
                .sorted((a, b) -> b - a)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("Top 3 distinct values (desc): " + top3);
        // Output: Top 3 distinct values (desc): [9, 8, 7]

        // 4. Distinct in encounter order, limited to 6.
        List<Integer> firstSixDistinct = numbers.stream()
                .distinct()
                .limit(6)
                .collect(Collectors.toList());
        System.out.println("First 6 distinct (encounter order): " + firstSixDistinct);
        // Output: First 6 distinct (encounter order): [5, 3, 8, 1, 9, 2]
    }
}
