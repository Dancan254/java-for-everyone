# Strings

## Learning Objectives

By the end of this topic, you should be able to:

- Concatenate strings using `+` and `+=`
- Apply the most commonly used `String` methods
- Compare String values correctly using `.equals()`
- Format output using `String.format` and `printf`

---

## 1. What Is a String?

`String` is a class in Java, not a primitive type. A String value is a sequence of characters enclosed in double quotes. Strings are **immutable** — once a String object is created, its content cannot be changed. Any operation that appears to modify a String actually creates a new one.

```java
String name    = "Alice";
String empty   = "";     // empty string — not null
String nullRef = null;   // holds no reference to any String object
```

---

## 2. String Concatenation

Use `+` to join Strings and values:

```java
String first = "John";
String last  = "Doe";
String full  = first + " " + last;  // "John Doe"

// Java calls toString() automatically when concatenating non-String values
int age = 25;
String message = "I am " + age + " years old"; // "I am 25 years old"
```

Use `+=` to append to an existing variable:

```java
String greeting = "Hello, ";
greeting += "World"; // "Hello, World"
```

When building a String inside a loop with many concatenations, prefer `StringBuilder` for performance. See [strings-deep-dive.md](strings-deep-dive.md).

---

## 3. Useful String Methods

```java
String text    = "  Java Programming  ";
String trimmed = text.trim();           // "Java Programming"

trimmed.length()                        // 16
trimmed.toUpperCase()                   // "JAVA PROGRAMMING"
trimmed.toLowerCase()                   // "java programming"
trimmed.charAt(5)                       // 'P'
trimmed.indexOf("gram")                 // 8
trimmed.contains("Java")               // true
trimmed.startsWith("Java")             // true
trimmed.endsWith("ing")                // true
trimmed.replace("Java", "C++")         // "C++ Programming"
trimmed.substring(5)                   // "Programming"
trimmed.substring(0, 4)                // "Java"
trimmed.isEmpty()                      // false
"".isEmpty()                           // true
```

### Splitting and joining

```java
String csv     = "Alice,30,Engineer";
String[] parts = csv.split(",");              // ["Alice", "30", "Engineer"]

String joined  = String.join(" | ", parts);  // "Alice | 30 | Engineer"
```

### Formatting

`String.format` returns a formatted String. `System.out.printf` prints one directly.

```java
String formatted = String.format("Name: %-10s Age: %3d", "Alice", 30);
// "Name: Alice      Age:  30"

System.out.printf("Price: $%.2f%n", 19.99);
// "Price: $19.99"
```

Common format specifiers:

| Specifier | Type | Example |
|-----------|------|---------|
| `%d` | Integer | `42` |
| `%f` | Floating-point | `3.140000` |
| `%.2f` | Float, 2 decimal places | `3.14` |
| `%s` | String | `"hello"` |
| `%-10s` | Left-aligned String, width 10 | `"hello     "` |
| `%n` | Platform line separator | (newline) |

---

## 4. Comparing Strings

Never use `==` to compare String contents. `==` tests whether two variables point to the same object in memory, not whether the characters are the same.

```java
String a = new String("hello");
String b = new String("hello");

System.out.println(a == b);                // false — different objects
System.out.println(a.equals(b));           // true  — same content
System.out.println(a.equalsIgnoreCase(b)); // true  — same content, ignoring case
```

Prefer putting the known literal on the left to avoid a `NullPointerException` if the variable might be `null`:

```java
if ("yes".equals(input)) { }   // safe if input is null
if (input.equals("yes")) { }   // throws NullPointerException if input is null
```

---

## 5. Going Deeper

[strings-deep-dive.md](strings-deep-dive.md) covers:

- The String pool and `intern()`
- Why immutability matters and how it affects performance
- `StringBuilder` for efficient concatenation in loops
- Regular expressions with `matches()`, `replaceAll()`, `split()`
- Text blocks (Java 15+)

---

## Common Mistakes

### Using == instead of .equals() for String comparison

```java
String input = scanner.nextLine();

if (input == "yes") { }          // unreliable — may be false even when content matches
if (input.equals("yes")) { }     // correct — compares content
```

### Calling methods on a null String

```java
String name = null;
int len = name.length(); // NullPointerException at runtime
```

Check for `null` before calling methods on any String that might not have been assigned, or initialize it to `""` if an empty String is a sensible default.

### Concatenation in a loop

```java
String result = "";
for (int i = 0; i < 10000; i++) {
    result += i; // creates a new String object on every iteration — slow
}
```

Use `StringBuilder.append()` inside loops instead. See [strings-deep-dive.md](strings-deep-dive.md).

---

## What's Next?

**Next:** [Exercises](../exercise.md)
