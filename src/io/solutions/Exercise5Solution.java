package io.solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Exercise5Solution.java
 *
 * Solutions for Exercise Set 5: Real-World I/O.
 * Covers: a word frequency counter that reads a file and counts word
 * occurrences, and a simple CSV reader that parses lines into records.
 */
public class Exercise5Solution {

    // -------------------------------------------------------------------------
    // Exercise 5.1 — Word Frequency Counter
    // -------------------------------------------------------------------------

    static void exercise5_1() throws IOException {
        System.out.println("=== Exercise 5.1: Word Frequency Counter ===");

        // Write a sample text file.
        Path path = Files.createTempFile("words", ".txt");
        Files.writeString(path,
                "the quick brown fox jumps over the lazy dog\n" +
                "the fox ran quickly over the hill\n" +
                "a quick brown dog outpaced a lazy fox\n"
        );

        // Count word frequencies using a stream pipeline.
        Map<String, Long> freq = Files.lines(path)
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .filter(word -> !word.isBlank())
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

        // Print top 5 words by frequency.
        System.out.println("Top 5 words:");
        freq.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(5)
                .forEach(e -> System.out.printf("  %-10s %d%n", e.getKey(), e.getValue()));
        // Output (example):
        //   the        5
        //   fox        3
        //   a          2
        //   brown      2
        //   lazy       2

        Files.delete(path);
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 5.2 — Simple CSV Reader
    // -------------------------------------------------------------------------

    record Person(String name, int age, String city) {}

    static void exercise5_2() throws IOException {
        System.out.println("=== Exercise 5.2: CSV Reader ===");

        Path csv = Files.createTempFile("people", ".csv");
        Files.writeString(csv,
                "name,age,city\n" +
                "Alice,30,London\n" +
                "Bob,25,Paris\n" +
                "Charlie,35,New York\n" +
                "Diana,28,Tokyo\n"
        );

        // Parse CSV: skip the header line, split each line, create records.
        List<Person> people = Files.lines(csv)
                .skip(1)                          // skip header
                .filter(line -> !line.isBlank())
                .map(line -> {
                    String[] parts = line.split(",");
                    return new Person(parts[0], Integer.parseInt(parts[1].trim()), parts[2]);
                })
                .collect(Collectors.toList());

        System.out.println("Parsed records:");
        people.forEach(p -> System.out.printf("  %-10s age=%-3d city=%s%n",
                p.name(), p.age(), p.city()));

        // Find the oldest person.
        people.stream()
                .max(Comparator.comparingInt(Person::age))
                .ifPresent(p -> System.out.println("Oldest: " + p.name() + " (" + p.age() + ")"));

        Files.delete(csv);
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        exercise5_1();
        exercise5_2();
    }
}
