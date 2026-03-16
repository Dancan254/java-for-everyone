import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * FileReadingDemo.java
 *
 * Demonstrates all three major approaches to reading a text file in Java:
 *   1. Files.readString()    — reads entire file into a single String (Java 11+)
 *   2. Files.readAllLines()  — reads all lines into a List<String>
 *   3. BufferedReader        — reads line by line (best for large files)
 *   4. Scanner               — convenient for parsing mixed-type content
 *
 * This demo creates its own temporary file so it runs without any external dependency.
 * The temp file is deleted at the end of the program.
 */
public class FileReadingDemo {

    public static void main(String[] args) throws IOException {

        // Create a temporary file with some sample content.
        // Path.of creates a Path object — it does NOT create the file yet.
        Path tempFile = Path.of("demo-reading-temp.txt");

        Files.writeString(tempFile,
                "Java I/O Demo\n"
                + "Line 2: Hello from BufferedReader\n"
                + "Line 3: Reading files is easy\n"
                + "Line 4: Try-with-resources keeps things safe\n"
                + "Line 5: Last line\n"
        );

        System.out.println("=== Approach 1: Files.readString() ===");
        readWithReadString(tempFile);

        System.out.println("\n=== Approach 2: Files.readAllLines() ===");
        readWithReadAllLines(tempFile);

        System.out.println("\n=== Approach 3: BufferedReader ===");
        readWithBufferedReader(tempFile);

        System.out.println("\n=== Approach 4: Scanner ===");
        readWithScanner(tempFile);

        // Demonstrate reading a CSV-structured file.
        System.out.println("\n=== Structured CSV Reading ===");
        readStructuredCsv();

        // Clean up — delete the temporary file.
        Files.deleteIfExists(tempFile);
        System.out.println("\nTemporary file cleaned up.");
    }

    /**
     * Files.readString() reads the entire file content into a single String in one call.
     * Best for: small configuration files, templates, or any file that fits in memory.
     * Java 11+ required.
     */
    static void readWithReadString(Path path) throws IOException {
        // The entire file is returned as one String, including embedded newlines.
        String content = Files.readString(path);
        System.out.println("File content (total " + content.length() + " characters):");
        System.out.println(content);
        // Output: (the full file content printed as a single block)
    }

    /**
     * Files.readAllLines() returns a List<String> — one element per line.
     * Newline characters are stripped from each element.
     * Best for: files where you need to process individual lines and the file is not huge.
     */
    static void readWithReadAllLines(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        System.out.println("Total lines: " + lines.size()); // Output: Total lines: 5

        // Process each line individually — here we prepend a line number.
        for (int i = 0; i < lines.size(); i++) {
            System.out.printf("[%d] %s%n", i + 1, lines.get(i));
        }
        // Output:
        // [1] Java I/O Demo
        // [2] Line 2: Hello from BufferedReader
        // [3] Line 3: Reading files is easy
        // [4] Line 4: Try-with-resources keeps things safe
        // [5] Line 5: Last line
    }

    /**
     * BufferedReader reads one line at a time through an internal buffer.
     * This reduces the number of actual disk reads, making it efficient for large files.
     *
     * try-with-resources (TWR) guarantees the reader is closed when the block exits,
     * even if an exception is thrown mid-read.
     *
     * readLine() returns null when the end of the file is reached.
     * Always check for null before using the returned line.
     *
     * Best for: large files, streaming processing, custom line parsing.
     */
    static void readWithBufferedReader(Path path) throws IOException {
        int lineNumber = 0;

        // The BufferedReader is declared in the try() header — it is closed automatically.
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                // Process each line without loading the whole file into memory.
                System.out.printf("Line %-3d (length %2d): %s%n",
                        lineNumber, line.length(), line);
            }
        }
        // Output:
        // Line 1   (length 13): Java I/O Demo
        // Line 2   (length 32): Line 2: Hello from BufferedReader
        // Line 3   (length 29): Line 3: Reading files is easy
        // Line 4   (length 44): Line 4: Try-with-resources keeps things safe
        // Line 5   (length 15): Line 5: Last line
    }

    /**
     * Scanner provides convenient parsing methods for structured text.
     * Useful when each line contains multiple values of different types.
     * Slower than BufferedReader for pure line-by-line reading.
     */
    static void readWithScanner(Path path) throws IOException {
        // Scanner accepts a Path directly (Java 10+).
        try (Scanner scanner = new Scanner(path)) {
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                count++;
                System.out.println("Scanner read: " + line);
            }
            System.out.println("Total lines read by Scanner: " + count);
        }
        // Output:
        // Scanner read: Java I/O Demo
        // Scanner read: Line 2: Hello from BufferedReader
        // ... (all 5 lines)
        // Total lines read by Scanner: 5
    }

    /**
     * Demonstrates reading a CSV file and parsing each field.
     * Creates a temporary CSV, reads it, parses it into structured data, then deletes it.
     */
    static void readStructuredCsv() throws IOException {
        Path csvFile = Path.of("demo-products-temp.csv");

        // Write a sample CSV file with a header row and data rows.
        Files.writeString(csvFile,
                "id,name,price,quantity\n"
                + "1,Widget,9.99,100\n"
                + "2,Gadget,24.99,50\n"
                + "3,Doohickey,4.49,200\n"
        );

        System.out.println("Parsing CSV products:");

        try (BufferedReader reader = Files.newBufferedReader(csvFile)) {
            String line = reader.readLine(); // Read and discard the header row.
            System.out.println("Header: " + line);

            double total = 0;

            // Read and parse each data row.
            while ((line = reader.readLine()) != null) {
                String[] parts    = line.split(",");
                int      id       = Integer.parseInt(parts[0]);
                String   name     = parts[1];
                double   price    = Double.parseDouble(parts[2]);
                int      quantity = Integer.parseInt(parts[3]);

                double lineTotal = price * quantity;
                total += lineTotal;

                System.out.printf("  ID=%d  %-12s $%6.2f x %3d = $%8.2f%n",
                        id, name, price, quantity, lineTotal);
            }

            System.out.printf("  Total inventory value: $%.2f%n", total);
        }

        Files.deleteIfExists(csvFile);
        // Output:
        // Header: id,name,price,quantity
        //   ID=1  Widget       $  9.99 x 100 = $   999.00
        //   ID=2  Gadget       $ 24.99 x  50 = $  1249.50
        //   ID=3  Doohickey    $  4.49 x 200 = $   898.00
        //   Total inventory value: $3146.50
    }
}
