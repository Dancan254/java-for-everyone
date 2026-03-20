# Java I/O — Exercises

These exercises cover the `java.nio.file` API: `Path`, `Files`, `StandardOpenOption`, and stream-based file reading. Work through each set in order. All solutions are in the `solutions/` subfolder.

---

## Exercise Set 1 — Path Operations

### 1.1 Create and inspect a Path

Create a `Path` object representing `"data/report.txt"`. Print:
- The file name (the last component of the path)
- The parent directory
- Whether the path is absolute

Expected output:
```
File name: report.txt
Parent:    data
Absolute:  false
```

### 1.2 Create an absolute Path

Create a `Path` for `"/home/user/documents/notes.txt"`. Print the same three fields and explain why `isAbsolute()` now returns `true`.

### 1.3 Resolve two paths

Given a base path of `"/home/user"`, use `resolve("documents/notes.txt")` to build the full path. Print the result and verify it equals `Path.of("/home/user/documents/notes.txt")`.

### 1.4 Relativize paths

Given:
```java
Path base   = Path.of("/home/user");
Path target = Path.of("/home/user/projects/java/Main.java");
```
Use `base.relativize(target)` to find the path from `base` to `target`. Print the result. What does `relativize` return when both paths share no common prefix?

### 1.5 Check existence

Create a `Path` pointing to a file you know does not exist (e.g., `"nonexistent.txt"`). Use `Files.exists()` to check whether it exists and print the result. Then create the file with `Files.writeString()`, check again, and delete it.

---

## Exercise Set 2 — Reading Files

For each exercise below, first write a small temp file using `Files.createTempFile()` or `Files.writeString()`, then read it back using the specified method.

### 2.1 Read with Files.readString()

Write three lines to a temp file. Read the entire file back with `Files.readString()` and print the content. Print also the total character count using `String.length()`.

### 2.2 Read with Files.readAllLines()

Write five city names to a temp file (one per line). Read them back with `Files.readAllLines()`. Print each line prefixed with its 1-based index:
```
1: London
2: Tokyo
3: Cairo
4: Sydney
5: New York
```

### 2.3 Count lines with Files.lines()

Write a file containing at least ten lines. Use `Files.lines(path).count()` to count the lines and print the result. Remember that `Files.lines()` returns a `Stream<String>` — use try-with-resources to ensure the stream is closed.

### 2.4 Find the longest line

Using the same file from 2.3, use `Files.lines()` with a stream pipeline to find the longest line. Use `max(Comparator.comparingInt(String::length))` and print the longest line along with its length.

### 2.5 Filter lines with a stream

Write a file containing a mix of lines, some starting with `"ERROR"` and some with `"INFO"`. Use `Files.lines()` with `filter()` to print only the `"ERROR"` lines.

---

## Exercise Set 3 — Writing Files

### 3.1 Write a String with Files.writeString()

Use `Files.writeString()` to write the string `"Hello, file I/O!"` to a file called `hello.txt`. Read it back and print the contents to verify.

### 3.2 Write a List with Files.write()

Create a `List<String>` of five programming languages. Use `Files.write()` to write them to `languages.txt`. Read the file back with `Files.readAllLines()` and print each line.

### 3.3 Append with StandardOpenOption.APPEND

Write an initial line to a file. Then append two more lines using `Files.writeString()` with `StandardOpenOption.APPEND`. Read the final file and verify all three lines are present.

### 3.4 Write CSV data

Build a list of strings where each string is a CSV row:
```
name,category,price
Laptop,Electronics,999.99
Keyboard,Electronics,49.99
Desk,Furniture,349.00
```
Write it to `products.csv` using `Files.write()`. Read it back with `Files.readAllLines()` and print each row.

### 3.5 Overwrite versus append

Write `"First version"` to a file, then write `"Second version"` to the same path without `APPEND`. Verify that the file now contains only `"Second version"`. Explain in a comment why the first write is gone.

---

## Exercise Set 4 — File Management

### 4.1 Copy a file

Create a temp file with some content. Use `Files.copy()` to copy it to a new path. Verify both files exist with `Files.exists()` and that the copy contains the same content.

### 4.2 Move a file

Use `Files.move()` to rename or relocate the copy from 4.1. After the move, verify that the original copy path no longer exists and that the new path does.

### 4.3 Delete a file

Use `Files.delete()` to remove a file. Wrap it in a try-catch for `IOException` and print a message if the file did not exist. Then try `Files.deleteIfExists()` — note that it does not throw if the file is absent.

### 4.4 Create a directory

Use `Files.createDirectory()` to create a single directory called `"temp-output"`. Verify it exists with `Files.isDirectory()`. Then create a file inside it and delete everything at the end.

### 4.5 Create a temp file

Use `Files.createTempFile("exercise", ".txt")` to create a temp file in the system temp directory. Print the full path, write some content to it, read it back, and delete it.

---

## Exercise Set 5 — Real-World I/O

### 5.1 Word frequency counter

Write a program that:
1. Creates a temp file containing at least 20 words across several lines (include repeated words).
2. Reads the file with `Files.lines()`.
3. Splits each line into words using `line.split("\\s+")`.
4. Counts how many times each word appears using a `Map<String, Integer>`.
5. Prints the top 5 most frequent words and their counts, sorted in descending order.

Example output:
```
Word frequency (top 5):
  the       : 5
  java      : 4
  file      : 3
  is        : 2
  reading   : 2
```

### 5.2 Simple CSV reader

Write a program that:
1. Creates a temp CSV file with a header row and at least four data rows. Each row has three fields: `name`, `score` (integer), and `grade` (a letter).
2. Reads the file skipping the header.
3. Parses each row by splitting on `","`.
4. Prints each record in a formatted table.
5. Computes and prints the average score.

Example output:
```
Name            Score  Grade
---------------------------------
Alice             92    A
Bob               78    B
Carol             85    B
David             61    D
---------------------------------
Average score: 79.00
```

---

## Common Mistakes

**Forgetting to handle IOException**
`IOException` is a checked exception. Every method in the `Files` class declares it. Always either catch it explicitly and log a meaningful message, or declare `throws IOException` in the method signature. An empty catch block hides failures silently.

**Not closing Scanner or streams**
`Scanner`, `BufferedReader`, and `Files.lines()` all hold file handles that must be closed. Always open them inside a try-with-resources block. If `Files.lines()` is not closed, the underlying file handle leaks until the JVM shuts down.

**Using string concatenation to build paths**
Do not build paths with `+`:
```java
// Wrong — fragile, platform-dependent separator handling.
String path = baseDir + "/" + fileName;

// Correct — Path.resolve() handles separators correctly on all platforms.
Path path = Path.of(baseDir).resolve(fileName);
```

**Assuming the parent directory exists**
`Files.writeString()` throws `NoSuchFileException` if the parent directory is missing. Call `Files.createDirectories(path.getParent())` before writing to any nested path.

**Hard-coding absolute paths**
Absolute paths that work on your machine fail on every other machine. Use relative paths or `Files.createTempFile()` for files that only need to exist during a single run.

---

Solutions for these exercises are in the `solutions/` subfolder.
