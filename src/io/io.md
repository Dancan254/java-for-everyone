# I/O and File Handling

Every non-trivial program eventually needs to read data from somewhere beyond the keyboard and write output somewhere beyond the screen. Java's file I/O story has two APIs: the legacy `java.io` package (streams and `File` objects) and the modern `java.nio.file` package (NIO.2, introduced in Java 7). The modern API is cleaner, more expressive, and handles edge cases more reliably. This guide focuses on `java.nio.file` as the primary approach and covers the legacy `Scanner`-based reading for completeness.

---

## 1. Why File I/O Matters

Without file I/O, programs lose all their data when they shut down. File I/O enables:
- Persisting state across program runs (configuration, user data, logs)
- Reading large datasets that would not be practical to hard-code
- Generating output that can be shared with other programs (CSV, JSON, reports)
- Processing existing data files (importing records, parsing logs)

---

## 2. The java.nio.file Package — Path and Files

The two central classes in `java.nio.file` are `Path` and `Files`.

**`Path`** represents a file or directory location. It is purely a description of a location — creating a `Path` does not create the file. `Path` is an interface; you create instances using the factory method `Path.of(...)` (Java 11+) or `Paths.get(...)` (Java 7+).

**`Files`** is a utility class of static methods that actually interact with the file system — reading, writing, copying, moving, deleting, and checking existence.

```java
import java.nio.file.Path;
import java.nio.file.Files;

// Create a Path — no file is created or opened yet.
Path filePath = Path.of("data/report.txt");
Path absolute = Path.of("/home/user/documents/notes.txt");

// Resolve a relative path against a base path.
Path base = Path.of("/home/user");
Path file = base.resolve("documents/notes.txt");
System.out.println(file);
// Output: /home/user/documents/notes.txt

// Inspect a Path.
System.out.println(filePath.getFileName()); // Output: report.txt
System.out.println(filePath.getParent());   // Output: data
System.out.println(filePath.isAbsolute());  // Output: false
```

---

## 3. Reading a File

### Files.readString() — simplest for small files (Java 11+)

Reads the entire file into a single `String` in one call. Best for small text files where the whole content fits comfortably in memory.

```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

Path path = Path.of("hello.txt");

try {
    String content = Files.readString(path);
    System.out.println(content);
    // Output: (entire file contents as a single String)
} catch (IOException e) {
    System.err.println("Could not read file: " + e.getMessage());
}
```

### Files.readAllLines() — when you need the lines as a List

Returns a `List<String>` where each element is one line of the file.

```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.io.IOException;

Path path = Path.of("cities.txt");

try {
    List<String> lines = Files.readAllLines(path);
    for (int i = 0; i < lines.size(); i++) {
        System.out.println((i + 1) + ": " + lines.get(i));
    }
    // Output:
    // 1: London
    // 2: Tokyo
    // 3: Cairo
} catch (IOException e) {
    System.err.println("Could not read file: " + e.getMessage());
}
```

### BufferedReader — for large files or streaming line-by-line

`BufferedReader` reads characters through an internal buffer, reducing the number of actual disk reads. Use this when the file may be too large to read into memory at once.

```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.IOException;

Path path = Path.of("large-data.csv");

try (BufferedReader reader = Files.newBufferedReader(path)) {
    // try-with-resources (TWR) closes the reader automatically — see section 6.
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    System.err.println("Error reading file: " + e.getMessage());
}
```

`reader.readLine()` returns `null` when the end of the file is reached — the `!= null` check is the standard termination condition for this pattern.

---

## 4. Writing a File

### Files.writeString() — simplest (Java 11+)

Writes a `String` to a file, creating the file if it does not exist and overwriting it if it does.

```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

Path path    = Path.of("output.txt");
String text  = "Hello from Java file I/O!";

try {
    Files.writeString(path, text);
    System.out.println("Written successfully.");
} catch (IOException e) {
    System.err.println("Could not write file: " + e.getMessage());
}
```

To **append** to an existing file instead of overwriting it:

```java
import java.nio.file.StandardOpenOption;

Files.writeString(path, "\nAppended line.", StandardOpenOption.APPEND);
```

### Files.write() — for a list of lines or a byte array

Writes an `Iterable<String>` (such as a `List<String>`) with a line separator after each element.

```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.io.IOException;

Path path = Path.of("names.txt");
List<String> lines = List.of("Alice", "Bob", "Carol");

try {
    Files.write(path, lines);
    // File content:
    // Alice
    // Bob
    // Carol
} catch (IOException e) {
    System.err.println("Could not write file: " + e.getMessage());
}
```

### BufferedWriter — for large files or custom formatting

`BufferedWriter` batches writes through an internal buffer, reducing disk flushes.

```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedWriter;
import java.io.IOException;

Path path = Path.of("report.txt");

try (BufferedWriter writer = Files.newBufferedWriter(path)) {
    // try-with-resources ensures the writer is flushed and closed.
    for (int i = 1; i <= 5; i++) {
        writer.write("Line " + i);
        writer.newLine(); // Platform-independent line separator.
    }
} catch (IOException e) {
    System.err.println("Error writing file: " + e.getMessage());
}
```

---

## 5. Checking Existence and Creating Directories

```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

Path filePath = Path.of("data/config.txt");
Path dirPath  = Path.of("data/reports");

// Check whether a path exists.
System.out.println(Files.exists(filePath));       // true or false
System.out.println(Files.isDirectory(dirPath));   // true if it exists and is a directory
System.out.println(Files.isRegularFile(filePath)); // true if it exists and is a regular file

// Create a single directory (parent must already exist).
try {
    Files.createDirectory(dirPath);
} catch (IOException e) {
    System.err.println("Could not create directory: " + e.getMessage());
}

// Create a directory and all missing parent directories.
try {
    Files.createDirectories(Path.of("output/2024/reports"));
} catch (IOException e) {
    System.err.println("Could not create directories: " + e.getMessage());
}
```

---

## 6. Try-With-Resources (TWR)

Any resource that implements `java.lang.AutoCloseable` can be used in a try-with-resources statement. The compiler generates a `finally` block that calls `close()` on the resource automatically, even if an exception occurs.

```java
// Without TWR — verbose and error-prone.
BufferedReader reader = null;
try {
    reader = Files.newBufferedReader(Path.of("file.txt"));
    // ... use reader ...
} catch (IOException e) {
    System.err.println(e.getMessage());
} finally {
    if (reader != null) {
        try {
            reader.close(); // Must be in its own try/catch — close() can also throw.
        } catch (IOException e) {
            // Ignored.
        }
    }
}

// With TWR — clean and safe. The reader is closed automatically.
try (BufferedReader reader = Files.newBufferedReader(Path.of("file.txt"))) {
    // ... use reader ...
} catch (IOException e) {
    System.err.println(e.getMessage());
}
```

TWR supports multiple resources in a single statement, separated by semicolons:

```java
try (BufferedReader reader = Files.newBufferedReader(source);
     BufferedWriter writer = Files.newBufferedWriter(destination)) {
    // Resources are closed in reverse declaration order when the block exits.
    String line;
    while ((line = reader.readLine()) != null) {
        writer.write(line);
        writer.newLine();
    }
} catch (IOException e) {
    System.err.println("Error: " + e.getMessage());
}
```

Always use TWR when working with streams, readers, writers, and connections. It is the correct and idiomatic Java pattern.

---

## 7. Handling IOException

`IOException` is a checked exception — the compiler requires you to either catch it or declare it in a `throws` clause. Every method in the `Files` class throws `IOException`.

```java
// Option 1: catch and handle at the point of use.
try {
    String content = Files.readString(Path.of("config.txt"));
    System.out.println(content);
} catch (IOException e) {
    // Log the error; provide a meaningful message rather than swallowing silently.
    System.err.println("Failed to read config: " + e.getMessage());
    // In a real application, you might use a logger or rethrow as a RuntimeException.
}

// Option 2: declare throws and let the caller handle it.
public static String loadConfig(String fileName) throws IOException {
    return Files.readString(Path.of(fileName));
}
```

Common `IOException` subclasses to know:
- `FileNotFoundException` — the file does not exist (thrown by legacy `FileInputStream`/`FileReader`).
- `NoSuchFileException` — the file does not exist (thrown by `Files` methods, NIO.2 style).
- `AccessDeniedException` — the process lacks permission to access the file.
- `FileAlreadyExistsException` — a `createFile` call found the file already exists.

---

## 8. Reading with Scanner

`Scanner` was designed primarily for parsing tokens and primitives from an input source. It is slower than `BufferedReader` for line-by-line reading but convenient for mixed data (e.g., reading a file where each line contains integers separated by spaces).

```java
import java.util.Scanner;
import java.nio.file.Path;
import java.io.IOException;

Path path = Path.of("numbers.txt");

// Pass a Path directly to the Scanner constructor (Java 10+).
try (Scanner scanner = new Scanner(path)) {
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        System.out.println(line);
    }
} catch (IOException e) {
    System.err.println("Error: " + e.getMessage());
}
```

Reading structured data (e.g., each line is a comma-separated pair of a name and a score):

```java
try (Scanner scanner = new Scanner(Path.of("scores.txt"))) {
    while (scanner.hasNextLine()) {
        String   line   = scanner.nextLine();
        String[] parts  = line.split(",");
        String   name   = parts[0].trim();
        int      score  = Integer.parseInt(parts[1].trim());
        System.out.println(name + ": " + score);
    }
} catch (IOException e) {
    System.err.println("Error: " + e.getMessage());
}
```

---

## 9. Common Mistakes

### Forgetting to close streams without TWR
If you open a `BufferedReader` or `BufferedWriter` without TWR and an exception occurs before `close()` is called, the stream is never closed. This leaks a file handle and can corrupt data that was buffered but never flushed.

```java
// Wrong: if an exception is thrown before close(), the file handle leaks.
BufferedWriter writer = Files.newBufferedWriter(Path.of("out.txt"));
writer.write("data");
writer.close(); // Never reached if the line above throws.

// Correct: TWR guarantees close() is called regardless.
try (BufferedWriter writer = Files.newBufferedWriter(Path.of("out.txt"))) {
    writer.write("data");
}
```

### Hard-coding absolute paths
Absolute paths that work on your machine will fail on any other machine.

```java
// Wrong: this path does not exist on anyone else's system.
Path path = Path.of("C:/Users/alice/projects/java-for-everyone/data.txt");

// Better: use a relative path (relative to the working directory).
Path path = Path.of("data/data.txt");

// Best for resources bundled with the application: use the classpath.
// Path classpath = Path.of(getClass().getResource("/data.txt").toURI());
```

### Not handling IOException
The compiler forces you to handle or declare `IOException`. Swallowing it silently (an empty catch block) hides problems that are impossible to debug later.

```java
// Wrong: exception is silently ignored.
try {
    String content = Files.readString(Path.of("config.txt"));
} catch (IOException e) {
    // Swallowed — you will never know the file could not be read.
}

// Correct: at minimum, log the error message.
try {
    String content = Files.readString(Path.of("config.txt"));
} catch (IOException e) {
    System.err.println("Failed to read config.txt: " + e.getMessage());
    // In production code, use a logger: logger.error("Failed to read config.txt", e);
}
```

---

## 10. When to Use What

| Scenario | Best Choice |
|---|---|
| Read entire small file | `Files.readString()` |
| Read lines into a List | `Files.readAllLines()` |
| Read a large file line by line | `BufferedReader` via `Files.newBufferedReader()` |
| Write a String to a file | `Files.writeString()` |
| Write a List of lines | `Files.write()` |
| Write large amounts of output | `BufferedWriter` via `Files.newBufferedWriter()` |
| Read mixed types (ints, strings) | `Scanner` with a `Path` argument |
| Check existence / create directories | `Files.exists()`, `Files.createDirectories()` |

---

## 11. Complete Example: Product Catalogue Reader and Report Writer

```java
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductCatalogue {

    record Product(String name, String category, double price) {}

    public static void main(String[] args) throws IOException {

        // Write a sample CSV file so this example is self-contained.
        Path csvPath = Path.of("products.csv");
        Files.writeString(csvPath,
                "name,category,price\n"
                + "Laptop,Electronics,999.99\n"
                + "Keyboard,Electronics,49.99\n"
                + "Desk,Furniture,349.00\n"
                + "Chair,Furniture,199.00\n"
                + "Notebook,Stationery,4.99\n"
                + "Pen,Stationery,1.49\n"
        );

        // Read the CSV file and parse each line into a Product.
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(csvPath)) {
            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip the header row.
                    continue;
                }
                String[] parts = line.split(",");
                String   name     = parts[0];
                String   category = parts[1];
                double   price    = Double.parseDouble(parts[2]);
                products.add(new Product(name, category, price));
            }
        }

        System.out.println("Loaded " + products.size() + " products.");
        // Output: Loaded 6 products.

        // Generate a summary report grouped by category.
        Path reportPath = Path.of("summary-report.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(reportPath)) {
            writer.write("=== Product Catalogue Summary Report ===");
            writer.newLine();
            writer.newLine();

            // Group by category manually using a for loop.
            List<String> categories = List.of("Electronics", "Furniture", "Stationery");

            for (String category : categories) {
                writer.write("Category: " + category);
                writer.newLine();

                double total = 0;
                int    count = 0;

                for (Product p : products) {
                    if (p.category().equals(category)) {
                        writer.write(String.format("  %-15s $%.2f%n", p.name(), p.price()));
                        total += p.price();
                        count++;
                    }
                }

                writer.write(String.format("  Subtotal: %d items, $%.2f%n", count, total));
                writer.newLine();
            }

            // Overall total.
            double grandTotal = products.stream().mapToDouble(Product::price).sum();
            writer.write(String.format("Grand Total: $%.2f%n", grandTotal));
        }

        // Print the report to the console by reading it back.
        System.out.println(Files.readString(reportPath));
        // Output:
        // === Product Catalogue Summary Report ===
        //
        // Category: Electronics
        //   Laptop          $999.99
        //   Keyboard        $49.99
        //   Subtotal: 2 items, $1049.98
        //
        // Category: Furniture
        //   Desk            $349.00
        //   Chair           $199.00
        //   Subtotal: 2 items, $548.00
        //
        // Category: Stationery
        //   Notebook        $4.99
        //   Pen             $1.49
        //   Subtotal: 2 items, $6.48
        //
        // Grand Total: $1604.46

        // Clean up the temporary files.
        Files.delete(csvPath);
        Files.delete(reportPath);
    }
}
```

---

## 12. Key Takeaways

- `Path` describes a location; `Files` is the utility class that acts on it — keep this distinction clear
- `Files.readString()` and `Files.writeString()` (Java 11+) are the most concise API for small text files
- `BufferedReader` and `BufferedWriter` obtained from `Files.newBufferedReader()` / `Files.newBufferedWriter()` are the right choice for large files
- Always wrap file-handling resources in try-with-resources — it guarantees the stream is closed even if an exception occurs
- `IOException` is checked — catch it explicitly and log a useful message; never swallow it silently
- Use `Files.createDirectories()` to create missing parent directories before writing, rather than assuming the path exists
- `Scanner` is convenient for parsing structured text (mixed types per line) but is slower than `BufferedReader` for pure line-by-line reading

---

File I/O is a prerequisite for the mini projects in `projects.md`, all of which involve reading from or writing to files. The `Files` class methods shown here are also the foundation of how Spring Boot reads property files and configuration at startup.
