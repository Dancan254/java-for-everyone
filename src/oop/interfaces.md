# Interfaces as a Design Tool

An interface is more than just a list of methods a class must implement. It is a formal agreement between the code that defines a capability and the code that uses it, enforced at compile time. Understanding how to design with interfaces — not just how to write the syntax — is one of the most important skills in professional Java development.

```
Think of it like this:
  A power outlet is an interface.
  It defines exactly what shape the plug must be and what voltage it delivers.
  Any device that follows that contract can plug in.
  The outlet does not care if it is a lamp, a phone charger, or a laptop.
  Interfaces work the same way: the caller does not care what is on the other end,
  only that it honors the contract.
```

## 1. Interface as a Contract

When you define an interface, you are writing a contract. Every class that implements that interface promises to provide a working implementation of every method declared in it. The compiler holds classes to that promise — if a method is missing, the code will not compile.

```java
public interface Exportable {
    String toJson();
    byte[] toCsv();
}

// Compiler error: Report does not implement toCsv()
public class Report implements Exportable {
    @Override
    public String toJson() {
        return "{ \"title\": \"Q1 Report\" }";
    }
    // Missing toCsv() — compile-time error
}
```

This matters enormously when working in teams or building APIs. If you publish an interface as part of a library, every consumer who implements it is guaranteed to have those methods available. You can write code that calls `toJson()` on any `Exportable` and know with certainty, before the program ever runs, that the method exists. The contract is checked at build time, not discovered at runtime.

## 2. Programming to an Interface

Programming to an interface means declaring variables, parameters, and return types using the interface type rather than the concrete implementation type. This is one of the most practical design principles in Java.

```java
// Tightly coupled to a specific implementation
ArrayList<String> names = new ArrayList<>();

// Programmed to the interface
List<String> names = new ArrayList<>();
```

The second approach lets you swap the implementation without touching any code that uses `names`. If you later decide a `LinkedList` performs better for your access pattern, one line changes:

```java
// Only this one line changes
List<String> names = new LinkedList<>();

// All code that uses names stays identical
names.add("Alice");
names.add("Bob");
System.out.println(names.get(0)); // Output: Alice
```

The same principle applies to method signatures:

```java
// Bad: forces callers to pass an ArrayList specifically
public void printAll(ArrayList<String> items) { ... }

// Good: accepts any List implementation
public void printAll(List<String> items) { ... }
```

When a method accepts `List<String>`, callers can pass an `ArrayList`, a `LinkedList`, an unmodifiable list from `Collections.unmodifiableList()`, or any other `List` implementation. The method works with all of them because it only calls methods defined on the `List` interface.

## 3. Functional Interfaces (Java 8+)

A functional interface is an interface that declares exactly one abstract method. This single requirement is what allows the interface to be used with a lambda expression, because a lambda is simply a compact way to provide that one method's implementation.

The `@FunctionalInterface` annotation is optional but strongly recommended. It tells the compiler to enforce the one-abstract-method rule, so if you accidentally add a second abstract method, you get a clear error instead of silent behavior change.

```java
@FunctionalInterface
public interface Transformer {
    String transform(String input);
}
```

You can implement this interface using an anonymous class or a lambda:

```java
// Anonymous class — verbose but explicit
Transformer upper = new Transformer() {
    @Override
    public String transform(String input) {
        return input.toUpperCase();
    }
};

// Lambda — same result, far less noise
Transformer upper = input -> input.toUpperCase();

System.out.println(upper.transform("hello")); // Output: HELLO
```

Functional interfaces are the foundation of Java's `java.util.function` package (`Function<T,R>`, `Predicate<T>`, `Consumer<T>`, `Supplier<T>`) and the entire Streams API. Every time you write a lambda in Java, you are implementing a functional interface.

## 4. Default Methods (Java 8+)

Default methods allow an interface to provide a method implementation directly, using the `default` keyword. Their primary purpose is to evolve an existing interface without breaking all the classes that already implement it.

Suppose you have a `Logger` interface used across dozens of classes in a codebase:

```java
public interface Logger {
    void log(String message);
}
```

A year later you want to add a convenience method `logError`. Without default methods, adding an abstract method to `Logger` would force every implementing class to add that method or fail to compile. With a default method, existing classes keep working unchanged:

```java
public interface Logger {
    void log(String message);

    // New method added without breaking existing implementations
    default void logError(String message) {
        log("[ERROR] " + message);
    }
}
```

Any existing class that implements `Logger` automatically inherits `logError` for free. A class that wants custom behavior can override it:

```java
public class FileLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("FILE: " + message);
    }

    // Uses the default logError — no need to override
}

public class AlertLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("ALERT: " + message);
    }

    @Override
    public void logError(String message) {
        System.out.println("CRITICAL ALERT: " + message.toUpperCase());
    }
}
```

```java
Logger file = new FileLogger();
Logger alert = new AlertLogger();

file.logError("Disk full");     // Output: FILE: [ERROR] Disk full
alert.logError("Disk full");    // Output: CRITICAL ALERT: DISK FULL
```

## 5. Static Methods in Interfaces

Interfaces can declare static methods. These are utility or factory methods that logically belong to the interface's concept but do not operate on an instance.

```java
public interface Color {
    int getRed();
    int getGreen();
    int getBlue();

    static Color of(int red, int green, int blue) {
        return new Color() {
            public int getRed()   { return red; }
            public int getGreen() { return green; }
            public int getBlue()  { return blue; }
        };
    }

    static Color fromHex(String hex) {
        int r = Integer.parseInt(hex.substring(1, 3), 16);
        int g = Integer.parseInt(hex.substring(3, 5), 16);
        int b = Integer.parseInt(hex.substring(5, 7), 16);
        return of(r, g, b);
    }
}
```

```java
Color red = Color.of(255, 0, 0);
Color blue = Color.fromHex("#0000FF");

System.out.println(red.getRed());    // Output: 255
System.out.println(blue.getBlue());  // Output: 255
```

Static methods in interfaces are not inherited by implementing classes. You call them directly on the interface name (`Color.of(...)`), not on an instance or subtype. This differs from static methods in abstract classes, which are inherited and can be called on subclass names. Interface static methods are strictly namespaced to the interface itself.

## 6. Private Methods (Java 9+)

When two or more default methods in an interface share logic, you can extract that shared logic into a private method. This avoids duplicating code while keeping the helper invisible to implementing classes.

```java
public interface AuditLogger {
    void logCreate(String entityName, long id);
    void logDelete(String entityName, long id);

    default void logCreate(String entityName, long id, String user) {
        writeAuditEntry("CREATE", entityName, id, user);
    }

    default void logDelete(String entityName, long id, String user) {
        writeAuditEntry("DELETE", entityName, id, user);
    }

    private void writeAuditEntry(String action, String entity, long id, String user) {
        String entry = String.format("[AUDIT] %s on %s#%d by %s", action, entity, id, user);
        System.out.println(entry);
    }
}
```

```java
public class DatabaseAuditLogger implements AuditLogger {
    @Override
    public void logCreate(String entityName, long id) {
        logCreate(entityName, id, "system");
    }

    @Override
    public void logDelete(String entityName, long id) {
        logDelete(entityName, id, "system");
    }
}
```

```java
AuditLogger logger = new DatabaseAuditLogger();
logger.logCreate("User", 42, "admin");
// Output: [AUDIT] CREATE on User#42 by admin
logger.logDelete("Order", 7, "admin");
// Output: [AUDIT] DELETE on Order#7 by admin
```

The private method `writeAuditEntry` is only visible inside the interface. Implementing classes cannot call or override it.

## 7. Interface Segregation

The Interface Segregation Principle states that a class should not be forced to implement methods it does not use. A large, catch-all interface creates unnecessary coupling and forces implementors to write empty or stub methods.

Consider a fat interface that tries to represent all office machine capabilities:

```java
// Bad: one interface for everything
public interface OfficeMachine {
    void print(String document);
    void scan(String fileName);
    void fax(String number, String document);
    void staple(int copies);
}

// A simple inkjet printer cannot fax or staple,
// but it is forced to implement those methods anyway
public class BasicPrinter implements OfficeMachine {
    @Override
    public void print(String document) {
        System.out.println("Printing: " + document);
    }

    @Override
    public void scan(String fileName) {
        throw new UnsupportedOperationException("Scanner not available");
    }

    @Override
    public void fax(String number, String document) {
        throw new UnsupportedOperationException("Fax not available");
    }

    @Override
    public void staple(int copies) {
        throw new UnsupportedOperationException("Stapler not available");
    }
}
```

The better design splits the fat interface into focused, single-purpose interfaces:

```java
// Good: each interface represents one capability
public interface Printer {
    void print(String document);
}

public interface Scanner {
    void scan(String fileName);
}

public interface FaxMachine {
    void fax(String number, String document);
}

public interface Stapler {
    void staple(int copies);
}

// A basic printer only implements what it actually supports
public class BasicPrinter implements Printer {
    @Override
    public void print(String document) {
        System.out.println("Printing: " + document);
    }
}

// An all-in-one machine implements everything it can do
public class AllInOnePrinter implements Printer, Scanner, FaxMachine, Stapler {
    @Override
    public void print(String document) {
        System.out.println("Printing: " + document);
    }

    @Override
    public void scan(String fileName) {
        System.out.println("Scanning to: " + fileName);
    }

    @Override
    public void fax(String number, String document) {
        System.out.println("Faxing to " + number + ": " + document);
    }

    @Override
    public void staple(int copies) {
        System.out.println("Stapling " + copies + " copies");
    }
}
```

Code that only needs to print accepts a `Printer`. Code that needs to scan accepts a `Scanner`. Neither caller is exposed to methods it does not use.

## 8. Marker Interfaces

A marker interface is an interface with no methods at all. It communicates a property or capability through the type system itself, not through any method signature.

```java
// From the Java standard library
public interface Serializable {
    // No methods
}

public interface Cloneable {
    // No methods
}
```

When a class implements `Serializable`, it is telling the JVM's object serialization mechanism: "You are allowed to convert instances of this class to a byte stream." The interface adds no behavior — it adds a type that can be checked at runtime with `instanceof` or by frameworks using reflection.

```java
public class UserProfile implements java.io.Serializable {
    private String username;
    private int age;

    public UserProfile(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
```

```java
Object obj = new UserProfile("alice", 30);

if (obj instanceof java.io.Serializable) {
    System.out.println("This object can be serialized"); // Output: This object can be serialized
}
```

Marker interfaces are less common today because annotations (like `@Entity` in JPA) can serve the same purpose with more flexibility. However, they remain valuable when the type check needs to be performed at compile time, which an annotation alone cannot do.

## 9. Commonly Used Built-in Interfaces

Java's standard library defines many interfaces that unlock language features or framework integration.

### Comparable\<T\>

Defines a class's natural ordering. Implementing it allows objects to be sorted by `Collections.sort()` and `Arrays.sort()` without any additional configuration.

```java
public class Student implements Comparable<Student> {
    private String name;
    private double gpa;

    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    @Override
    public int compareTo(Student other) {
        // Negative: this comes before other
        // Zero: equal
        // Positive: this comes after other
        return Double.compare(other.gpa, this.gpa); // Descending by GPA
    }

    @Override
    public String toString() {
        return name + " (" + gpa + ")";
    }
}
```

```java
List<Student> students = new ArrayList<>();
students.add(new Student("Carol", 3.5));
students.add(new Student("Alice", 3.9));
students.add(new Student("Bob", 3.7));

Collections.sort(students);
System.out.println(students);
// Output: [Alice (3.9), Bob (3.7), Carol (3.5)]
```

### Comparator\<T\>

Defines an external ordering strategy that is separate from the class itself. Use `Comparator` when you need multiple different orderings, or when you cannot modify the class you are sorting.

```java
public class StudentNameComparator implements Comparator<Student> {
    @Override
    public int compare(Student a, Student b) {
        return a.getName().compareTo(b.getName());
    }
}
```

```java
List<Student> students = new ArrayList<>();
students.add(new Student("Carol", 3.5));
students.add(new Student("Alice", 3.9));
students.add(new Student("Bob", 3.7));

students.sort(new StudentNameComparator());
System.out.println(students);
// Output: [Alice (3.9), Bob (3.7), Carol (3.5)]  <- sorted by name A-Z
```

### Iterable\<T\>

Implementing `Iterable<T>` allows your custom class to work in a for-each loop. You must implement the `iterator()` method, which returns an `Iterator<T>`.

```java
public class NumberRange implements Iterable<Integer> {
    private final int start;
    private final int end;

    public NumberRange(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public java.util.Iterator<Integer> iterator() {
        return new java.util.Iterator<Integer>() {
            int current = start;

            public boolean hasNext() { return current <= end; }
            public Integer next()    { return current++; }
        };
    }
}
```

```java
for (int n : new NumberRange(1, 5)) {
    System.out.print(n + " ");
}
// Output: 1 2 3 4 5
```

### AutoCloseable

Implementing `AutoCloseable` allows objects to be used in a try-with-resources block. The `close()` method is called automatically when the block exits, even if an exception is thrown.

```java
public class DatabaseConnection implements AutoCloseable {
    private final String url;

    public DatabaseConnection(String url) {
        this.url = url;
        System.out.println("Connection opened: " + url);
    }

    public void query(String sql) {
        System.out.println("Executing: " + sql);
    }

    @Override
    public void close() {
        System.out.println("Connection closed: " + url);
    }
}
```

```java
try (DatabaseConnection conn = new DatabaseConnection("jdbc:mysql://localhost/mydb")) {
    conn.query("SELECT * FROM users");
}
// Output:
// Connection opened: jdbc:mysql://localhost/mydb
// Executing: SELECT * FROM users
// Connection closed: jdbc:mysql://localhost/mydb
```

The connection is closed automatically at the end of the try block, with no explicit call needed.

## 10. Interface vs Abstract Class

| Feature              | Interface                                       | Abstract Class                             |
|----------------------|-------------------------------------------------|--------------------------------------------|
| Instantiation        | Cannot be instantiated                          | Cannot be instantiated                     |
| Fields               | Only `public static final` constants            | Any access modifier, instance state allowed |
| Constructors         | Not allowed                                     | Allowed, called by subclasses via `super()` |
| Abstract methods     | All methods abstract by default (pre-Java 8)    | Explicitly declared with `abstract`        |
| Concrete methods     | Via `default` keyword (Java 8+)                 | Any regular method                         |
| Inheritance          | A class can implement many interfaces           | A class can extend only one abstract class |
| Access modifiers     | Methods implicitly `public`                     | Methods can be any access level            |
| Private methods      | Allowed as helpers for default methods (Java 9+)| Always allowed                             |
| Use case             | Define a capability shared across unrelated types | Share state and behavior among related types |

When to choose an interface:

- You are defining a capability or role (`Printable`, `Serializable`, `Payable`) rather than a type hierarchy
- Unrelated classes need to fulfill the same contract
- You need a class to fulfill multiple contracts simultaneously
- You are designing a public API that others will implement

When to choose an abstract class:

- Subclasses share common state (instance fields) that would be duplicated otherwise
- You want to enforce a template structure with some steps already implemented
- The relationship is clearly "is-a" and a single hierarchy makes sense
- You need a constructor to initialize shared state

## 11. Complete Example

This example builds a small payment processing system using multiple interfaces and polymorphic processing.

```java
public interface Payable {
    void processPayment(double amount);
    String getPaymentMethod();
}
```

```java
public interface Refundable {
    void processRefund(double amount);
}
```

```java
public class CreditCardPayment implements Payable, Refundable {
    private final String cardNumber;
    private double balance;

    public CreditCardPayment(String cardNumber, double initialBalance) {
        this.cardNumber = cardNumber;
        this.balance = initialBalance;
    }

    @Override
    public void processPayment(double amount) {
        balance -= amount;
        System.out.println("Credit card " + cardNumber + ": charged $" + amount
                + " | Remaining balance: $" + balance);
    }

    @Override
    public String getPaymentMethod() {
        return "Credit Card (" + cardNumber + ")";
    }

    @Override
    public void processRefund(double amount) {
        balance += amount;
        System.out.println("Credit card " + cardNumber + ": refunded $" + amount
                + " | New balance: $" + balance);
    }
}
```

```java
public class BankTransfer implements Payable {
    private final String accountNumber;

    public BankTransfer(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Bank transfer from account " + accountNumber
                + ": transferred $" + amount);
    }

    @Override
    public String getPaymentMethod() {
        return "Bank Transfer (" + accountNumber + ")";
    }
}
```

```java
public class PaymentProcessor {
    public void processAll(Payable[] payments, double amount) {
        for (Payable payment : payments) {
            System.out.println("Processing via: " + payment.getPaymentMethod());
            payment.processPayment(amount);

            // Only attempt refunds on payment methods that support it
            if (payment instanceof Refundable) {
                Refundable refundable = (Refundable) payment;
                System.out.println("This payment method supports refunds. Issuing partial refund.");
                refundable.processRefund(amount * 0.1);
            }

            System.out.println("---");
        }
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Payable[] payments = {
            new CreditCardPayment("4111-1111-1111-1111", 1000.00),
            new BankTransfer("ACC-789456"),
            new CreditCardPayment("5500-0000-0000-0004", 500.00)
        };

        PaymentProcessor processor = new PaymentProcessor();
        processor.processAll(payments, 150.00);
    }
}
```

```java
// Output:
// Processing via: Credit Card (4111-1111-1111-1111)
// Credit card 4111-1111-1111-1111: charged $150.0 | Remaining balance: $850.0
// This payment method supports refunds. Issuing partial refund.
// Credit card 4111-1111-1111-1111: refunded $15.0 | New balance: $865.0
// ---
// Processing via: Bank Transfer (ACC-789456)
// Bank transfer from account ACC-789456: transferred $150.0
// ---
// Processing via: Credit Card (5500-0000-0000-0004)
// Credit card 5500-0000-0000-0004: charged $150.0 | Remaining balance: $350.0
// This payment method supports refunds. Issuing partial refund.
// Credit card 5500-0000-0000-0004: refunded $15.0 | New balance: $365.0
// ---
```

`PaymentProcessor` knows nothing about credit cards or bank transfers. It only works with `Payable`. The `instanceof` check for `Refundable` is a deliberate design decision: it lets the processor take advantage of an optional capability without requiring every payment method to support it.

## 12. Key Takeaways

- An interface is a compile-time enforced contract: every declared method must be implemented or the code does not compile
- Program to interfaces, not implementations: declare variables and parameters as the interface type so implementations can be swapped freely
- A functional interface has exactly one abstract method and can be implemented with a lambda expression; annotate it with `@FunctionalInterface` to make the constraint explicit
- Default methods allow interfaces to evolve — new methods can be added without breaking existing implementors
- Static methods in interfaces are utility methods scoped to the interface name and are not inherited
- Private methods in interfaces (Java 9+) let you eliminate code duplication inside default methods
- Follow the Interface Segregation Principle: many small, focused interfaces are better than one large interface
- Marker interfaces communicate a type-level property that can be checked with `instanceof` or reflection, even though they carry no method signatures
- `Comparable<T>` defines natural ordering; `Comparator<T>` defines an external ordering strategy; `Iterable<T>` enables for-each; `AutoCloseable` enables try-with-resources
- Prefer an interface when defining a capability; prefer an abstract class when sharing state and behavior among closely related types

---

Interfaces are the backbone of Spring Boot's dependency injection: every service, repository, and component you write as an interface lets Spring swap, mock, or decorate the implementation without touching any of the code that depends on it.
