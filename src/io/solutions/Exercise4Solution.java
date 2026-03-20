package io.solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Exercise4Solution.java
 *
 * Solutions for Exercise Set 4: File Management.
 * Covers: Files.copy(), Files.move(), Files.delete(), Files.createDirectory(),
 * and Files.createTempFile() — with Files.exists() checks at each step.
 */
public class Exercise4Solution {

    public static void main(String[] args) throws IOException {
        exercise4_1();
        exercise4_2();
    }

    // -------------------------------------------------------------------------
    // Exercise 4.1 — Copy and Delete
    // -------------------------------------------------------------------------

    static void exercise4_1() throws IOException {
        System.out.println("=== Exercise 4.1: Copy and Delete ===");

        // Create a source file.
        Path source = Files.createTempFile("source", ".txt");
        Files.writeString(source, "This is the source file content.");
        System.out.println("Source created:  " + Files.exists(source)); // true

        // Copy to a new location.
        Path dest = source.getParent().resolve("copied_file.txt");
        Files.copy(source, dest);
        System.out.println("Copy created:    " + Files.exists(dest));   // true
        System.out.println("Copy content:    " + Files.readString(dest));

        // Delete both.
        Files.delete(source);
        Files.delete(dest);
        System.out.println("Source deleted:  " + !Files.exists(source)); // true
        System.out.println("Copy deleted:    " + !Files.exists(dest));   // true
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 4.2 — Create Directory, Move, and Clean Up
    // -------------------------------------------------------------------------

    static void exercise4_2() throws IOException {
        System.out.println("=== Exercise 4.2: Create Directory and Move ===");

        // Create a temp directory.
        Path tempDir = Files.createTempDirectory("exercise4_dir");
        System.out.println("Directory created: " + Files.isDirectory(tempDir)); // true

        // Create a file to move.
        Path original = Files.createTempFile("to_move", ".txt");
        Files.writeString(original, "Moving this file.");
        System.out.println("Original exists:  " + Files.exists(original)); // true

        // Move the file into the temp directory.
        Path moved = tempDir.resolve(original.getFileName());
        Files.move(original, moved);
        System.out.println("Original after move: " + Files.exists(original)); // false
        System.out.println("Moved file exists:   " + Files.exists(moved));    // true
        System.out.println("Moved content:       " + Files.readString(moved));

        // Clean up: delete file then directory.
        Files.delete(moved);
        Files.delete(tempDir);
        System.out.println("Cleaned up: " + !Files.exists(tempDir)); // true
        System.out.println();
    }
}
