package io.solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Exercise1Solution.java
 *
 * Solutions for Exercise Set 1: Path Operations.
 * Covers: creating Path instances, inspecting components (getFileName,
 * getParent, isAbsolute), resolving relative paths, and Files.exists().
 */
public class Exercise1Solution {

    public static void main(String[] args) throws IOException {
        exercise1_1();
        exercise1_2();
        exercise1_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.1 — Creating and Inspecting Paths
    // -------------------------------------------------------------------------

    static void exercise1_1() {
        System.out.println("=== Exercise 1.1: Creating and Inspecting Paths ===");

        Path relative = Path.of("data/report.txt");
        System.out.println("Path:         " + relative);                    // data/report.txt
        System.out.println("getFileName(): " + relative.getFileName());     // report.txt
        System.out.println("getParent():   " + relative.getParent());       // data
        System.out.println("isAbsolute():  " + relative.isAbsolute());      // false

        Path absolute = Path.of("/home/user/documents/notes.txt");
        System.out.println("Absolute path: " + absolute);                   // /home/user/documents/notes.txt
        System.out.println("isAbsolute():  " + absolute.isAbsolute());      // true
        System.out.println("getName(0):    " + absolute.getName(0));        // home
        System.out.println("getNameCount(): " + absolute.getNameCount());   // 4
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.2 — Resolving and Relativizing Paths
    // -------------------------------------------------------------------------

    static void exercise1_2() {
        System.out.println("=== Exercise 1.2: resolve() and relativize() ===");

        Path base   = Path.of("/home/user");
        Path file   = base.resolve("documents/notes.txt");
        System.out.println("resolve: " + file);
        // Output: /home/user/documents/notes.txt

        Path other  = Path.of("/home/user/downloads/file.zip");
        Path rel    = base.relativize(other);
        System.out.println("relativize: " + rel);
        // Output: downloads/file.zip

        // Path.of handles multiple segments.
        Path multi = Path.of("src", "main", "java", "App.java");
        System.out.println("multi-segment: " + multi);
        // Output: src/main/java/App.java
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.3 — Files.exists() and path checks
    // -------------------------------------------------------------------------

    static void exercise1_3() throws IOException {
        System.out.println("=== Exercise 1.3: Files.exists() ===");

        // Create a real temp file to demonstrate exists().
        Path temp = Files.createTempFile("exercise1", ".txt");
        System.out.println("Temp file:   " + temp);
        System.out.println("exists():    " + Files.exists(temp));       // Output: true
        System.out.println("isRegularFile(): " + Files.isRegularFile(temp)); // Output: true
        System.out.println("isDirectory():   " + Files.isDirectory(temp));   // Output: false

        Files.delete(temp);
        System.out.println("After delete, exists(): " + Files.exists(temp)); // Output: false
        System.out.println();
    }
}
