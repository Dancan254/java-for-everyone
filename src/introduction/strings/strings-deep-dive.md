# Strings: Deep Dive

`String` is the most frequently used class in any Java application. You already know how to create and concatenate strings from the Introduction guide. This guide goes deeper: the String pool, immutability, the full suite of useful methods, text blocks, `StringBuilder`, regular expressions, and the surprisingly important `==` vs `.equals()` distinction.

---

## 1. Why Strings Get Their Own Deep-Dive

`String` is special in Java in several ways that other classes are not:
- It has a dedicated pool in memory shared across the entire application.
- It is immutable — every "modification" creates a new object.
- String literals have special syntax — they are the only type that does not require `new`.
- The `+` operator is overloaded to work with strings.
- It implements `CharSequence`, `Comparable<String>`, and `Serializable`.

Understanding how `String` works internally prevents an entire class of bugs and performance problems that beginners frequently encounter.

---

## 2. The String Pool and intern() — == vs .equals()

The JVM maintains a string pool: a shared memory area that holds one copy of each unique string literal. When you write `"hello"` twice in your code, both refer to the same object in the pool. This saves memory for commonly used strings.

```java
String a = "hello"; // Stored in the string pool.
String b = "hello"; // Same literal — reuses the same pool object.
String c = new String("hello"); // Explicitly creates a NEW object on the heap — bypasses the pool.

System.out.println(a == b);        // Output: true  — same object in the pool
System.out.println(a == c);        // Output: false — c is a new heap object
System.out.println(a.equals(c));   // Output: true  — same character sequence
```

This is one of Java's most important gotchas: `==` tests whether two variables reference the same object. `.equals()` tests whether the character sequences are the same. **For strings, always use `.equals()`.**

`intern()` forces a string onto the pool, returning the pool instance:

```java
String d = c.intern(); // Returns the pool version of "hello".
System.out.println(a == d); // Output: true — now pointing to the same pool object.
```

In practice, call `intern()` rarely. Its main use case is reducing memory when storing very large numbers of equal strings. Always compare with `.equals()` regardless.

---

## 3. String Immutability — Every "Modification" Creates a New Object

Once a `String` object is created, its character sequence can never change. Methods like `toUpperCase()`, `replace()`, and `trim()` do not modify the original string — they return a new `String` object.

```java
String original = "hello";
String upper    = original.toUpperCase();

System.out.println(original); // Output: hello — unchanged
System.out.println(upper);    // Output: HELLO — a new String object

// The reference itself can be reassigned, but the object it points to never changes.
original = original + " world"; // Creates a new String; reassigns the reference.
System.out.println(original); // Output: hello world
```

Immutability is what makes `String` safe to share across threads, safe to use as a `HashMap` key, and safe to cache in the string pool.

---

## 4. Key String Methods

### Length and Characters

```java
String s = "Hello, World!";

System.out.println(s.length());       // Output: 13
System.out.println(s.charAt(0));      // Output: H
System.out.println(s.charAt(12));     // Output: !
System.out.println(s.isEmpty());      // Output: false (length == 0)
System.out.println("".isEmpty());     // Output: true
System.out.println("  ".isBlank());   // Output: true (blank = empty or only whitespace, Java 11+)
```

### Substrings and Searching

```java
String s = "Hello, World!";

System.out.println(s.substring(7));      // Output: World!       (from index 7 to end)
System.out.println(s.substring(7, 12));  // Output: World        (indices 7 to 11 inclusive)
System.out.println(s.indexOf("World")); // Output: 7             (first occurrence)
System.out.println(s.indexOf("xyz"));   // Output: -1            (not found)
System.out.println(s.lastIndexOf('l')); // Output: 10            (last 'l')
System.out.println(s.contains("World")); // Output: true
System.out.println(s.startsWith("Hello")); // Output: true
System.out.println(s.endsWith("!"));    // Output: true
```

### Case, Trim, and Whitespace

```java
String s = "  Hello, World!  ";

System.out.println(s.trim());        // Output: "Hello, World!"  (removes leading/trailing ASCII whitespace)
System.out.println(s.strip());       // Output: "Hello, World!"  (removes leading/trailing Unicode whitespace, Java 11+)
System.out.println(s.stripLeading()); // Output: "Hello, World!  " (only leading)
System.out.println(s.stripTrailing()); // Output: "  Hello, World!" (only trailing)

System.out.println("hello".toUpperCase()); // Output: HELLO
System.out.println("WORLD".toLowerCase()); // Output: world
```

### Replace and Split

```java
String s = "The quick brown fox";

System.out.println(s.replace("quick", "slow")); // Output: The slow brown fox
System.out.println(s.replace('o', '0'));         // Output: The quick br0wn f0x

// split() splits on a regex pattern; for a literal dot use "\\."
String csv = "Alice,Bob,Carol";
String[] parts = csv.split(",");
System.out.println(parts[0]); // Output: Alice
System.out.println(parts[1]); // Output: Bob

// Limit the split to at most n parts.
String limited = "a:b:c:d:e".split(":", 3)[2];
System.out.println(limited); // Output: c:d:e (everything from the third split onward)
```

### Repeat and Formatted (Java 11+/12+)

```java
System.out.println("ab".repeat(4));   // Output: abababab
System.out.println("-".repeat(20));   // Output: --------------------

// String.formatted() (Java 15+) is equivalent to String.format() but called as an instance method.
String greeting = "Hello, %s! You are %d years old.".formatted("Alice", 30);
System.out.println(greeting); // Output: Hello, Alice! You are 30 years old.
```

---

## 5. String.format() and printf() — Format Specifiers

`String.format()` and `System.out.printf()` use the same format specifier syntax.

| Specifier | Meaning | Example |
|---|---|---|
| `%s` | String | `"hello"` |
| `%d` | Decimal integer | `42` |
| `%f` | Floating point | `3.140000` |
| `%n` | Platform line separator | (newline) |
| `%05d` | Integer padded with zeros to width 5 | `00042` |
| `%.2f` | Float with 2 decimal places | `3.14` |
| `%-10s` | Left-aligned string in 10-char field | `"hello     "` |
| `%10s` | Right-aligned string in 10-char field | `"     hello"` |

```java
String name   = "Alice";
int    age    = 30;
double salary = 95000.50;

System.out.printf("Name: %-10s Age: %3d Salary: $%,.2f%n", name, age, salary);
// Output: Name: Alice      Age:  30 Salary: $95,000.50

String formatted = String.format("ID: %05d", 42);
System.out.println(formatted); // Output: ID: 00042

// Useful for building tables.
System.out.printf("%-15s %10s %8s%n", "Product", "Quantity", "Price");
System.out.printf("%-15s %10d %8.2f%n", "Widget", 100, 9.99);
System.out.printf("%-15s %10d %8.2f%n", "Gadget", 50, 24.99);
// Output:
// Product          Quantity    Price
// Widget                100     9.99
// Gadget                 50    24.99
```

---

## 6. StringBuilder — When and Why to Use It

Because `String` is immutable, every `+` concatenation creates a new `String` object that copies all previous characters. In a loop, this means the total work is proportional to the square of the number of iterations (O(n²)).

```java
// Bad: quadratic time. Each iteration creates a new String of increasing size.
String result = "";
for (int i = 0; i < 10000; i++) {
    result += i; // Allocates a new String on every iteration.
}
```

`StringBuilder` uses a mutable internal buffer and appends directly to it. Building the final string requires one allocation at the end.

```java
// Good: linear time. StringBuilder appends to a buffer in-place.
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 10000; i++) {
    sb.append(i);
}
String result = sb.toString(); // Single allocation at the end.
```

**Other StringBuilder methods:**

```java
StringBuilder sb = new StringBuilder("Hello");

sb.append(", World");      // Hello, World
sb.insert(5, "!");         // Hello!, World
sb.delete(5, 6);           // Hello, World (removes "!" at index 5)
sb.reverse();              // dlroW ,olleH
sb.replace(0, 5, "Java");  // Java ,olleH  — (hypothetically after previous ops)

System.out.println(sb.length()); // Current length of the buffer.
System.out.println(sb.charAt(0)); // First character.
```

**Rule of thumb:** use `+` for simple one-liner concatenations (the compiler optimises these anyway). Use `StringBuilder` explicitly only when you are building a string in a loop or assembling many pieces programmatically.

---

## 7. String Comparison

| Method | Tests |
|---|---|
| `a == b` | Same object reference — almost never what you want |
| `a.equals(b)` | Same character sequence, case-sensitive |
| `a.equalsIgnoreCase(b)` | Same character sequence, case-insensitive |
| `a.compareTo(b)` | Lexicographic order (negative, zero, or positive) |
| `a.compareToIgnoreCase(b)` | Lexicographic order, case-insensitive |

```java
String a = "Hello";
String b = "hello";
String c = "World";

System.out.println(a.equals(b));             // Output: false
System.out.println(a.equalsIgnoreCase(b));   // Output: true
System.out.println(a.compareTo(c));          // Output: negative (H < W in ASCII)
System.out.println(a.compareToIgnoreCase(b)); // Output: 0 (equal when ignoring case)
```

---

## 8. Text Blocks (Java 15+)

Text blocks allow multiline strings without manual `\n` and string concatenation. The content between `"""` delimiters is a single string with preserved formatting and no need to escape most special characters.

```java
// Old way — verbose and hard to read.
String json = "{\n"
        + "  \"name\": \"Alice\",\n"
        + "  \"age\": 30\n"
        + "}";

// Text block — reads like the actual content.
String jsonBlock = """
        {
          "name": "Alice",
          "age": 30
        }
        """;

System.out.println(jsonBlock);
// Output:
// {
//   "name": "Alice",
//   "age": 30
// }
```

The indentation of the closing `"""` determines how much leading whitespace is stripped from each line. Text blocks are ideal for SQL queries, JSON/HTML templates, and any multiline string.

---

## 9. Regular Expressions Basics

Regular expressions are patterns for matching and manipulating text. `String` provides three convenience methods.

**`matches(regex)`** — returns `true` if the entire string matches the pattern.

```java
System.out.println("hello123".matches("[a-z]+\\d+"));   // Output: true
System.out.println("123abc".matches("[a-z]+\\d+"));     // Output: false
System.out.println("2024-03-15".matches("\\d{4}-\\d{2}-\\d{2}")); // Output: true
```

**`replaceAll(regex, replacement)`** — replaces all substrings matching the regex.

```java
String text = "The   quick   brown  fox";
// Replace one-or-more whitespace characters with a single space.
System.out.println(text.replaceAll("\\s+", " ")); // Output: The quick brown fox

// Remove all digits.
System.out.println("abc123def456".replaceAll("\\d", "")); // Output: abcdef
```

**`Pattern` and `Matcher` for more control:**

```java
import java.util.regex.Pattern;
import java.util.regex.Matcher;

String text    = "Contact us at support@example.com or sales@company.org";
Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
Matcher matcher = emailPattern.matcher(text);

while (matcher.find()) {
    System.out.println("Found email: " + matcher.group());
}
// Output:
// Found email: support@example.com
// Found email: sales@company.org
```

---

## 10. Common Mistakes

### Using == for string comparison
This is the most common Java beginner mistake. `==` compares references, not content. It returns `true` only by coincidence (when both strings are pooled literals).

```java
String a = new String("hello");
String b = new String("hello");

if (a == b) {           // Wrong — almost always false for non-literal strings.
    System.out.println("equal");
}

if (a.equals(b)) {      // Correct — always checks character sequence.
    System.out.println("equal"); // Output: equal
}
```

### String concatenation in a loop (O(n²) performance)
Each `+=` on a String creates a new String object that copies all previous characters. For 1000 iterations, the total characters copied is proportional to 1+2+3+...+1000 = 500,500 — quadratic.

```java
// Bad for large n.
String result = "";
for (int i = 0; i < 1000; i++) {
    result += i;  // Quadratic time and memory.
}

// Good: StringBuilder is linear time.
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);
}
String result = sb.toString();
```

### Calling methods on potentially null strings
If a `String` variable might be `null`, calling any method on it throws `NullPointerException`.

```java
String name = getName(); // might return null

// Wrong: throws NullPointerException if name is null.
if (name.equals("Alice")) { ... }

// Correct option 1: check for null first.
if (name != null && name.equals("Alice")) { ... }

// Correct option 2: use the string literal on the left.
if ("Alice".equals(name)) { ... }  // Works correctly even when name is null.

// Correct option 3: use Objects.equals() which handles null on either side.
if (java.util.Objects.equals(name, "Alice")) { ... }
```

### Forgetting that split() takes a regex
`split(".")` does not split on literal dots — `.` in regex matches any character. Use `split("\\.")` to split on a literal dot.

```java
String version = "1.2.3";
System.out.println(version.split(".").length);    // Output: 0 — "." matches every char, strange result
System.out.println(version.split("\\.").length);  // Output: 3 — correct: splits on literal dot
```

---

## 11. Key Takeaways

- The string pool shares identical string literals — `==` is true for pooled strings but false for `new String(...)` objects; always use `.equals()` for content comparison
- `String` is immutable — every "modifying" method returns a new `String` object; the original is unchanged
- Use `StringBuilder` when building a string in a loop; use `+` freely for single-statement concatenation
- `String.format()` (or `printf`) with format specifiers (`%s`, `%d`, `%.2f`) is the cleanest way to produce formatted output
- Text blocks (`"""..."""`, Java 15+) eliminate escape-character noise from multiline strings
- Calling a method on a potentially-null `String` throws `NullPointerException` — use `"literal".equals(variable)` or `Objects.equals()` to be null-safe
- `matches()`, `replaceAll()`, and `split()` all accept regex patterns — remember to escape special characters like `.`, `+`, and `*` with `\\`

---

Mastery of `String` and `StringBuilder` shows up in every part of Java development — parsing input, building responses, formatting logs, and writing tests. The methods in this guide cover the operations you will reach for repeatedly.
