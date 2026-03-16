package io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * FileWritingDemo.java
 *
 * Demonstrates all approaches to writing text files in Java:
 *   1. Files.writeString()   — write a String in one call (Java 11+)
 *   2. Files.writeString() with APPEND — append to an existing file
 *   3. Files.write()         — write a List<String> (one line per element)
 *   4. BufferedWriter        — write line by line (best for large output)
 *   5. Checking existence and creating directories before writing
 *
 * Every temporary file created here is deleted at the end of the program.
 */
public class FileWritingDemo {

    public static void main(String[] args) throws IOException {

        System.out.println("=== Approach 1: Files.writeString() ===");
        writeWithWriteString();

        System.out.println("\n=== Approach 2: Files.writeString() with APPEND ===");
        writeWithAppend();

        System.out.println("\n=== Approach 3: Files.write() with a List<String> ===");
        writeWithWriteLines();

        System.out.println("\n=== Approach 4: BufferedWriter ===");
        writeWithBufferedWriter();

        System.out.println("\n=== Approach 5: Creating Directories Before Writing ===");
        writeWithDirectoryCreation();

        System.out.println("\nAll temporary files cleaned up.");
    }

    /**
     * Files.writeString() writes a String to a file in a single call.
     * If the file does not exist, it is created.
     * If the file already exists, it is overwritten (not appended).
     * Java 11+ required.
     */
    static void writeWithWriteString() throws IOException {
        Path path    = Path.of("demo-write1-temp.txt");
        String text  = "Hello from Files.writeString()!\nSecond line.\nThird line.";

        Files.writeString(path, text);
        System.out.println("Written to: " + path);

        // Read back and print to verify.
        System.out.println("Contents:\n" + Files.readString(path));
        // Output:
        // Contents:
        // Hello from Files.writeString()!
        // Second line.
        // Third line.

        Files.deleteIfExists(path);
    }

    /**
     * StandardOpenOption.APPEND causes Files.writeString() to add content to the end
     * of the file rather than replacing it. If the file does not exist, it is created.
     *
     * Other useful StandardOpenOption values:
     *   CREATE        — create the file if it does not exist (default behaviour for write methods)
     *   CREATE_NEW    — create the file, fail if it already exists
     *   TRUNCATE_EXISTING — overwrite the file (default behaviour for write methods)
     *   SYNC          — flush every write to physical disk immediately
     */
    static void writeWithAppend() throws IOException {
        Path path = Path.of("demo-write2-temp.txt");

        // Initial write — creates the file.
        Files.writeString(path, "Entry 1: First write\n");

        // Subsequent appends — adds to the end.
        Files.writeString(path, "Entry 2: Second write\n", StandardOpenOption.APPEND);
        Files.writeString(path, "Entry 3: Third write\n",  StandardOpenOption.APPEND);

        System.out.println("File after three appended writes:");
        System.out.println(Files.readString(path));
        // Output:
        // Entry 1: First write
        // Entry 2: Second write
        // Entry 3: Third write

        Files.deleteIfExists(path);
    }

    /**
     * Files.write() accepts an Iterable<String> and writes each element on its own line.
     * A platform-appropriate line separator is added after each element.
     * Best when you already have your data in a List.
     */
    static void writeWithWriteLines() throws IOException {
        Path path = Path.of("demo-write3-temp.txt");

        List<String> cities = List.of("London", "Tokyo", "Cairo", "Sydney", "New York");

        Files.write(path, cities);

        System.out.println("Written lines:");
        Files.readAllLines(path).forEach(line -> System.out.println("  " + line));
        // Output:
        //   London
        //   Tokyo
        //   Cairo
        //   Sydney
        //   New York

        Files.deleteIfExists(path);
    }

    /**
     * BufferedWriter batches writes through an internal buffer, reducing the number
     * of actual disk writes. This is the most efficient approach for writing large
     * amounts of text line by line.
     *
     * writer.newLine() writes the platform-appropriate line separator (\r\n on Windows,
     * \n on Unix/Mac), which is more portable than hardcoding "\n".
     *
     * try-with-resources ensures the writer is flushed and closed even if an
     * exception occurs — critical because unflushed data in the buffer would be lost.
     */
    static void writeWithBufferedWriter() throws IOException {
        Path path = Path.of("demo-write4-temp.txt");

        // Simulate generating a report with many lines.
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("=== Sales Report ===");
            writer.newLine();

            // Write a formatted table.
            writer.write(String.format("%-20s %10s %10s", "Product", "Quantity", "Revenue"));
            writer.newLine();
            writer.write("-".repeat(42));
            writer.newLine();

            // Data rows — generated in a loop as they would be in a real application.
            String[][] data = {
                {"Widget",    "150", "1498.50"},
                {"Gadget",    " 82", "2049.18"},
                {"Doohickey", "310", "1391.90"}
            };

            double totalRevenue = 0;
            for (String[] row : data) {
                writer.write(String.format("%-20s %10s %10s", row[0], row[1], row[2]));
                writer.newLine();
                totalRevenue += Double.parseDouble(row[2].trim());
            }

            writer.write("-".repeat(42));
            writer.newLine();
            writer.write(String.format("%-20s %10s %10.2f", "TOTAL", "", totalRevenue));
            writer.newLine();
        }
        // The writer is closed here — the buffer is flushed to disk.

        System.out.println("Report written. Contents:");
        System.out.println(Files.readString(path));
        // Output:
        // === Sales Report ===
        // Product               Quantity    Revenue
        // ------------------------------------------
        // Widget                     150    1498.50
        // Gadget                      82    2049.18
        // Doohickey                  310    1391.90
        // ------------------------------------------
        // TOTAL                           4939.58

        Files.deleteIfExists(path);
    }

    /**
     * Demonstrates creating missing directories before writing.
     * Files.writeString() throws NoSuchFileException if the parent directory does not exist.
     * Always call Files.createDirectories() for the parent path first.
     */
    static void writeWithDirectoryCreation() throws IOException {
        // Nested directory that may not exist.
        Path dir  = Path.of("demo-temp-dir/subdir");
        Path file = dir.resolve("output.txt");

        // Create the directory and all missing parents in one call.
        // If the directory already exists, this is a no-op (no exception is thrown).
        Files.createDirectories(dir);
        System.out.println("Directory created: " + dir);

        // Now we can safely write the file.
        Files.writeString(file, "Written to a newly created directory.");
        System.out.println("File written: " + file);
        System.out.println("Contents: " + Files.readString(file));
        // Output: Contents: Written to a newly created directory.

        // Clean up the created files and directories.
        Files.deleteIfExists(file);
        Files.deleteIfExists(dir);
        Files.deleteIfExists(Path.of("demo-temp-dir"));
        System.out.println("Temporary directory removed.");
    }
}
