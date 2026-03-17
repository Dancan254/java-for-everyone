# Exceptions Practice Exercises

Exception handling is not about hiding errors. It is about giving your program the information it needs to fail gracefully, recover when possible, and communicate clearly when it cannot. The exercises below build from basic try/catch through custom exceptions, exception chaining, and resource management. Work through each set in order.

---

## Exercise Set 1: try/catch/finally

### Exercise 1.1: Safe Division

Write a method `safeDivide(int a, int b)` that:
- Attempts `a / b` inside a `try` block
- Catches `ArithmeticException` and returns `0`, printing a message explaining why
- Prints `"Division attempted"` in a `finally` block every time, regardless of outcome

Call it with `(10, 2)`, `(10, 0)`, and `(-15, 3)`. Show that `finally` runs in all three cases.

### Exercise 1.2: Multiple Catch Blocks

Write a method `parseAndIndex(String[] arr, String indexStr)` that:
1. Parses `indexStr` to an integer using `Integer.parseInt`
2. Uses that integer to index into `arr`
3. Calls `.toUpperCase()` on the result

Use three separate `catch` blocks for `NumberFormatException`, `ArrayIndexOutOfBoundsException`, and `NullPointerException`, each printing a specific message. Call the method four times:
- Valid input
- A non-numeric string for the index
- An index that is out of range
- An index that points to a `null` element (put a `null` in the array)

### Exercise 1.3: Finally for Cleanup

Create a class `FakeConnection` (not a real I/O class — just a regular class with `open()`, `query()`, and `close()` methods that print messages). `query()` should throw a `RuntimeException` when called with a bad argument.

Write a method that opens the connection, calls `query()`, and always closes it in `finally`. Demonstrate that `close()` is called even when `query()` throws.

---

## Exercise Set 2: Checked vs Unchecked Exceptions

### Exercise 2.1: The Checked Exception Contract

Write a method `readConfig(String filename) throws java.io.IOException` that simulates reading a configuration file. If `filename` is `null` or empty, throw a new `java.io.IOException("No filename provided")`. Otherwise print `"Reading: " + filename`.

Write two callers:
- One that handles the `IOException` with a `try-catch`
- One that propagates it using `throws IOException` in its own signature

Show that a third method that calls `readConfig` without either handling or declaring the exception does not compile (write the error as a comment).

### Exercise 2.2: When to Use Unchecked

Write a class `BankAccount` with a `double balance` field and a method `withdraw(double amount)` that throws `IllegalArgumentException` if `amount <= 0` and `IllegalStateException` if `amount > balance`. Neither of these requires a `try-catch` at the call site.

Demonstrate: call `withdraw` with `-50`, then with an amount that exceeds the balance. Catch both exceptions at the call site and print specific messages for each. Explain in a comment why these are `RuntimeException` subclasses rather than checked exceptions.

### Exercise 2.3: Checked Exception Propagation Chain

Write three methods that call each other in sequence:
- `readLine()` throws `java.io.IOException`
- `processFile()` calls `readLine()` and declares `throws java.io.IOException`
- `loadData()` calls `processFile()` and handles the `IOException` in a `try-catch`

Inside `readLine()`, throw `new java.io.IOException("Disk read failure")`. Show the entire chain compiles and the exception is caught in `loadData()`.

---

## Exercise Set 3: Custom Exceptions

### Exercise 3.1: Custom Unchecked Exception

Create `InsufficientFundsException` extending `RuntimeException` with:
- A `private double amount` field storing the shortfall
- A constructor `InsufficientFundsException(double amount)` that calls `super` with a descriptive message
- A `getAmount()` accessor

Use it inside a `BankAccount.withdraw(double amount)` method. Catch it in `main`, print the message, and print the shortfall separately via `getAmount()`.

### Exercise 3.2: Custom Checked Exception

Create `InvalidAgeException` extending `Exception` with:
- A constructor accepting a `String message`
- A second constructor accepting `int age` that builds the message automatically: `"Age " + age + " is not valid (must be 0–150)."`

Use it inside a `Person.setAge(int age) throws InvalidAgeException` method. Demonstrate with a valid age and two invalid ages (`-5` and `200`).

### Exercise 3.3: Domain-Specific Exception Hierarchy

Create a small exception hierarchy:
- `AppException extends RuntimeException` — base class with a `String code` field and a constructor `AppException(String code, String message)`; add `getCode()`
- `UserNotFoundException extends AppException` — constructor takes a `long userId`, sets code `"USER_NOT_FOUND"` and builds a message
- `DuplicateEmailException extends AppException` — constructor takes a `String email`, sets code `"DUPLICATE_EMAIL"` and builds a message

Write a `UserService` class with `findUser(long id)` and `registerUser(String email)` that throw these exceptions. Catch them polymorphically with a single `catch (AppException e)` block and print both `e.getCode()` and `e.getMessage()`.

---

## Exercise Set 4: Exception Chaining and Re-throwing

### Exercise 4.1: Wrapping a Low-Level Exception

Write a method `loadUserData(int userId)` that accesses an array with `userId` as an index. If the index is out of bounds, catch the `ArrayIndexOutOfBoundsException` and wrap it in a custom `DataAccessException extends Exception`, passing the original as the cause.

In `main`, catch `DataAccessException`, print its message, then print the cause's message using `e.getCause().getMessage()`.

### Exercise 4.2: Re-throwing with Context

Write a method `parsePrice(String input)` that calls `Double.parseDouble(input)`. If a `NumberFormatException` is thrown, catch it and re-throw a new `IllegalArgumentException("Invalid price format: '" + input + "'", e)`, preserving the original exception as the cause.

In `main`, catch the `IllegalArgumentException`, print its message, and also print `e.getCause().getClass().getSimpleName()` to confirm the cause is still accessible.

### Exercise 4.3: Multi-level Exception Chain

Simulate a three-layer architecture where each layer wraps the exception from the layer below:

- `databaseLayer()` throws `java.sql.SQLException("Connection timeout")`
- `repositoryLayer()` catches it and throws a checked `RepositoryException extends Exception` with the `SQLException` as the cause
- `serviceLayer()` catches it and throws an unchecked `ServiceException extends RuntimeException` with the `RepositoryException` as the cause
- `main` catches `ServiceException`, prints its message, then follows the cause chain with `getCause()` twice to reach and print the original `SQLException` message

---

## Exercise Set 5: try-with-resources and AutoCloseable

### Exercise 5.1: Implementing AutoCloseable

Create a class `ManagedResource` that implements `AutoCloseable`:
- Constructor prints `"ManagedResource opened"`
- A method `process()` prints `"Processing..."`
- `close()` prints `"ManagedResource closed"`

Use it in a try-with-resources block. Show that `close()` is called automatically after the `try` block exits normally. Then repeat with `process()` throwing a `RuntimeException` — show that `close()` is still called before the exception propagates.

### Exercise 5.2: Multiple Resources in One Block

Create two classes `InputResource` and `OutputResource`, both implementing `AutoCloseable`. Each constructor prints `"Opened"` with its name. Each `close()` prints `"Closed"` with its name.

Declare both in a single try-with-resources statement. Show that they are closed in reverse declaration order (last declared, first closed). Do this for both the normal path and the exception path.

### Exercise 5.3: Suppressed Exceptions

Create a `BrokenResource` that implements `AutoCloseable`. Its `use()` method throws a `RuntimeException("Primary failure")`. Its `close()` method throws a `RuntimeException("Close failure")`.

Use it in a try-with-resources block. When both the body and `close()` throw, Java keeps the body exception as the primary and attaches the close exception as a suppressed exception. In the catch block, call `e.getSuppressed()` to retrieve and print the suppressed exceptions, demonstrating that neither exception is silently lost.

---

## Common Mistakes

### Catching Exception or Throwable too broadly

Catching the top of the hierarchy silences information you need. You lose the ability to handle different failure modes differently, and you may accidentally catch exceptions you had no intention of handling (including `OutOfMemoryError` if you catch `Throwable`).

```java
// Avoid — masks all possible failures with one generic handler.
try {
    processData();
} catch (Exception e) {
    System.out.println("Something went wrong.");
}

// Prefer — handle each failure mode explicitly.
try {
    processData();
} catch (IOException e) {
    System.out.println("I/O failure: " + e.getMessage());
} catch (IllegalArgumentException e) {
    System.out.println("Bad input: " + e.getMessage());
}
```

### Swallowing exceptions (empty catch blocks)

An empty catch block discards the exception entirely. The error silently disappears, the program continues in a potentially broken state, and there is no record of what went wrong. This is one of the most harmful patterns in Java code.

```java
// Never do this — the exception vanishes with no trace.
try {
    loadConfig("settings.json");
} catch (IOException e) {
    // nothing here
}

// At minimum, log the message. In production code, use a proper logger.
try {
    loadConfig("settings.json");
} catch (IOException e) {
    System.out.println("Config load failed: " + e.getMessage());
}
```

### Throwing exceptions from finally blocks

If both the `try` body and the `finally` block throw exceptions, the `finally` exception replaces the original. The first exception — the one that explains what actually went wrong — is permanently lost. There is no suppression mechanism for classic `finally` (unlike try-with-resources).

```java
// Dangerous: the IOException from the try block is silently discarded.
try {
    readFile(); // throws IOException
} finally {
    closeResource(); // also throws RuntimeException — this one wins.
}

// Safe: guard the finally block so it does not throw.
try {
    readFile();
} finally {
    try {
        closeResource();
    } catch (Exception ignored) {
        System.out.println("Close failed (original exception takes priority).");
    }
}
```

### Checking exception type with == instead of instanceof

Exception types form a class hierarchy. A `FileNotFoundException` IS an `IOException`. Using `==` checks for exact class identity and misses all subclasses, so subtype exceptions fall through uncaught.

```java
// Wrong: == only matches the exact class, not subclasses.
try {
    readFile();
} catch (IOException e) {
    if (e.getClass() == IOException.class) {
        // A FileNotFoundException would not match this — it would go unhandled.
    }
}

// Correct: instanceof respects the class hierarchy.
try {
    readFile();
} catch (IOException e) {
    if (e instanceof java.io.FileNotFoundException) {
        System.out.println("File not found: " + e.getMessage());
    } else {
        System.out.println("I/O error: " + e.getMessage());
    }
}
```

---

Solutions for these exercises are in the `solutions/` subfolder.
