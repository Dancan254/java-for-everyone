package io.solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Exercise2Solution.java
 *
 * Solutions for Exercise Set 2: Reading Files.
 * Covers: Files.readString(), Files.readAllLines(), Files.lines() with streams —
 * counting lines, finding the longest line, and filtering lines.
 */
public class Exercise2Solution {

    public static void main(String[] args) throws IOException {
        // Create a temp file with sample content for all exercises.
        Path temp = Files.createTempFile("exercise2", ".txt");
        Files.writeString(temp,
                "Java is a statically typed language.\n" +
                "It runs on the JVM.\n" +
                "Streams make data processing concise and expressive.\n" +
                "File I/O in Java uses the java.nio.file package.\n" +
                "Optional eliminates null-pointer exceptions.\n"
        );

        exercise2_1(temp);
        exercise2_2(temp);
        exercise2_3(temp);

        Files.delete(temp);
    }

    // -------------------------------------------------------------------------
    // Exercise 2.1 — Files.readString()
    // -------------------------------------------------------------------------

    static void exercise2_1(Path path) throws IOException {
        System.out.println("=== Exercise 2.1: Files.readString() ===");

        String content = Files.readString(path);
        System.out.println("Full content:");
        System.out.print(content);
        System.out.println("Character count: " + content.length());
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 2.2 — Files.readAllLines()
    // -------------------------------------------------------------------------

    static void exercise2_2(Path path) throws IOException {
        System.out.println("=== Exercise 2.2: Files.readAllLines() ===");

        List<String> lines = Files.readAllLines(path);
        System.out.println("Line count: " + lines.size());

        for (int i = 0; i < lines.size(); i++) {
            System.out.printf("%d: %s%n", i + 1, lines.get(i));
        }
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 2.3 — Files.lines() with Stream operations
    // -------------------------------------------------------------------------

    static void exercise2_3(Path path) throws IOException {
        System.out.println("=== Exercise 2.3: Files.lines() with Streams ===");

        // Count lines longer than 40 characters.
        long longLineCount = Files.lines(path)
                .filter(line -> line.length() > 40)
                .count();
        System.out.println("Lines longer than 40 chars: " + longLineCount);

        // Find the longest line.
        Optional<String> longest = Files.lines(path)
                .max((a, b) -> Integer.compare(a.length(), b.length()));
        longest.ifPresent(l -> System.out.println("Longest line: " + l));

        // Filter lines containing "Java".
        System.out.println("Lines mentioning 'Java':");
        Files.lines(path)
                .filter(line -> line.contains("Java"))
                .forEach(line -> System.out.println("  " + line));
        System.out.println();
    }
}
