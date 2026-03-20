package io.solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Exercise3Solution.java
 *
 * Solutions for Exercise Set 3: Writing Files.
 * Covers: Files.writeString(), Files.write() with a List<String>,
 * appending with StandardOpenOption.APPEND, and writing CSV data.
 */
public class Exercise3Solution {

    public static void main(String[] args) throws IOException {
        Path temp = Files.createTempFile("exercise3", ".txt");

        exercise3_1(temp);
        exercise3_2(temp);
        exercise3_3();

        Files.delete(temp);
    }

    // -------------------------------------------------------------------------
    // Exercise 3.1 — Files.writeString()
    // -------------------------------------------------------------------------

    static void exercise3_1(Path path) throws IOException {
        System.out.println("=== Exercise 3.1: Files.writeString() ===");

        Files.writeString(path, "Hello, File I/O!\n");
        String content = Files.readString(path);
        System.out.println("Written and read back:");
        System.out.print(content);
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 3.2 — Files.write() with List and APPEND
    // -------------------------------------------------------------------------

    static void exercise3_2(Path path) throws IOException {
        System.out.println("=== Exercise 3.2: Files.write() and APPEND ===");

        // Write a list of lines — each element becomes one line.
        List<String> lines = List.of("Line 1", "Line 2", "Line 3");
        Files.write(path, lines);

        System.out.println("After initial write:");
        Files.readAllLines(path).forEach(l -> System.out.println("  " + l));

        // Append more lines without overwriting.
        List<String> more = List.of("Line 4", "Line 5");
        Files.write(path, more, StandardOpenOption.APPEND);

        System.out.println("After append:");
        Files.readAllLines(path).forEach(l -> System.out.println("  " + l));
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 3.3 — Writing CSV Data
    // -------------------------------------------------------------------------

    static void exercise3_3() throws IOException {
        System.out.println("=== Exercise 3.3: Writing CSV Data ===");

        Path csv = Files.createTempFile("data", ".csv");

        StringBuilder sb = new StringBuilder();
        sb.append("name,age,city\n");
        sb.append("Alice,30,London\n");
        sb.append("Bob,25,Paris\n");
        sb.append("Charlie,35,New York\n");

        Files.writeString(csv, sb.toString());

        System.out.println("CSV content:");
        Files.readAllLines(csv).forEach(l -> System.out.println("  " + l));

        Files.delete(csv);
        System.out.println();
    }
}
