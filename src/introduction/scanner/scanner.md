# Scanner and User Input

## Learning Objectives

By the end of this topic, you should be able to:

- Import and instantiate a `Scanner` connected to `System.in`
- Choose the correct reading method for each data type
- Handle the newline-buffer issue when mixing numeric reads with `nextLine()`
- Validate input before reading it using `hasNextInt()` and similar methods

---

## 1. What Is Scanner?

The `Scanner` class reads input from a source — typically the keyboard (`System.in`) — and parses it into typed values. It is in the `java.util` package and must be imported before use.

---

## 2. Setting Up Scanner

```java
import java.util.Scanner;

public class InputExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // read input here

        scanner.close(); // always close when done
    }
}
```

---

## 3. Reading Methods

| Method | Reads | Example |
|--------|-------|---------|
| `nextInt()` | An `int` token | `int age = scanner.nextInt();` |
| `nextLong()` | A `long` token | `long big = scanner.nextLong();` |
| `nextDouble()` | A `double` token | `double price = scanner.nextDouble();` |
| `nextFloat()` | A `float` token | `float weight = scanner.nextFloat();` |
| `nextBoolean()` | A `boolean` token | `boolean flag = scanner.nextBoolean();` |
| `next()` | One word — stops at whitespace | `String word = scanner.next();` |
| `nextLine()` | An entire line including spaces | `String line = scanner.nextLine();` |

---

## 4. Complete Example

```java
import java.util.Scanner;

public class UserProfile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume the trailing newline left by nextInt()

        System.out.print("Enter your GPA: ");
        double gpa = scanner.nextDouble();

        System.out.println("\n--- Profile ---");
        System.out.println("Name: " + name);
        System.out.println("Age:  " + age);
        System.out.printf("GPA:  %.2f%n", gpa);

        scanner.close();
    }
}
```

---

## 5. Validating Input

Use `hasNextInt()`, `hasNextDouble()`, etc. to verify the next token before reading it. This avoids an `InputMismatchException` when the user types something unexpected.

```java
System.out.print("Enter an integer: ");
while (!scanner.hasNextInt()) {
    System.out.println("Not a valid integer. Try again: ");
    scanner.next(); // discard the invalid token
}
int value = scanner.nextInt();
```

---

## Common Mistakes

### nextLine() reads an empty string after nextInt() or nextDouble()

`nextInt()` and `nextDouble()` read the numeric token but leave the newline character `'\n'` in the input buffer. The next `nextLine()` call immediately reads that leftover newline and returns an empty string instead of waiting for input.

```java
int age = scanner.nextInt();
String name = scanner.nextLine(); // reads "" instead of the user's name
```

**Fix:** call `scanner.nextLine()` immediately after any `nextInt()` or `nextDouble()` to consume the trailing newline before reading text:

```java
int age = scanner.nextInt();
scanner.nextLine();               // discard the trailing newline
String name = scanner.nextLine(); // now reads correctly
```

### Using next() instead of nextLine() for multi-word input

`next()` stops at the first whitespace. Use `nextLine()` when input may contain spaces.

```java
System.out.print("Enter full name: ");
String name = scanner.next();     // reads "Jane" only — stops before the space
String name = scanner.nextLine(); // reads "Jane Doe" — correct
```

---

## What's Next?

**Next:** [Strings](../strings/strings.md)
