# Object-Oriented Programming Practice Exercises

This exercise file covers the full OOP block: classes and objects, encapsulation, inheritance, polymorphism, abstraction, interfaces, downcasting, and records. Work through the sets in order — each one builds directly on the concepts from the previous set and the corresponding guides.

---

## Exercise Set 1: Classes, Objects, and Constructors

### Exercise 1.1: Book Class
Create a class `Book` with private fields: `String title`, `String author`, `int year`, `double price`.

Requirements:
- Provide a constructor that sets all four fields.
- Provide a no-argument constructor that sets default values (`"Unknown"`, `"Unknown"`, `0`, `0.0`).
- Provide getters for all fields and setters for `price` only (title, author, and year should be immutable after construction).
- Override `toString()` to return something readable, for example: `Book{title='Clean Code', author='Robert Martin', year=2008, price=$35.99}`.
- Add an `isAffordable(double budget)` method that returns `true` if the price is within budget.

Demonstrate all features in `main`.

### Exercise 1.2: Counter Class with Static Field
Create a class `Counter` with:
- A private `int count` field initialized to 0.
- `increment()`, `decrement()`, and `reset()` instance methods.
- A `getCount()` getter.
- A static field `totalCounters` that tracks how many `Counter` objects have been created in total.
- A static method `getTotalCounters()`.

Demonstrate that `totalCounters` is shared across all instances.

### Exercise 1.3: BankAccount with Validation
Create a class `BankAccount` with private fields: `String accountNumber`, `String owner`, `double balance`.

Requirements:
- The constructor should validate that `balance >= 0`. Throw `IllegalArgumentException` if not.
- `deposit(double amount)`: adds to the balance. Reject negative amounts.
- `withdraw(double amount)`: subtracts from the balance. Reject negative amounts and amounts that would make the balance go below zero. Return `true` on success, `false` on failure.
- `transfer(BankAccount target, double amount)`: withdraws from this account and deposits into the target account. Only execute if the withdrawal succeeds.
- Override `toString()`.

---

## Exercise Set 2: Encapsulation and Access Control

### Exercise 2.1: Temperature Class
Design a `Temperature` class that stores a temperature value internally in Celsius.

Requirements:
- The field `celsius` is private.
- Provide `getCelsius()`, `getFahrenheit()`, and `getKelvin()` getters that compute the converted values on demand. Do not store Fahrenheit or Kelvin as fields.
- Provide `setCelsius(double)` with validation: reject values below absolute zero (-273.15°C).
- Provide `setFahrenheit(double)` that converts to Celsius and stores it (with the same validation).
- Override `toString()` to show all three scales.

### Exercise 2.2: Stack with Capacity
Create a generic-like (or `Object`-based, since generics are a later topic) `IntStack` class:
- Internally backed by a private `int[]` with a fixed capacity set in the constructor.
- `push(int value)` — adds to the top. Throw `IllegalStateException` if the stack is full.
- `pop()` — removes and returns the top element. Throw `java.util.EmptyStackException` if empty.
- `peek()` — returns the top element without removing it. Throw `java.util.EmptyStackException` if empty.
- `isEmpty()` and `isFull()` boolean methods.
- `size()` returns the current number of elements.

The internal array and size counter must be private. All interaction happens through the public API.

### Exercise 2.3: Immutable Point
Create a class `Point` that represents an (x, y) coordinate and is fully immutable:
- Fields `x` and `y` are `private` and `final`.
- No setters.
- A constructor that sets both fields.
- `distanceTo(Point other)` method that computes the Euclidean distance.
- `translate(double dx, double dy)` method that returns a NEW `Point` at (x+dx, y+dy) — it does not modify `this`.
- Override `equals(Object)`, `hashCode()`, and `toString()`.

---

## Exercise Set 3: Inheritance

### Exercise 3.1: Vehicle Hierarchy
Create a base class `Vehicle` with protected fields `String make`, `String model`, `int year`, and a method `String describe()` that returns `"[year] [make] [model]"`.

Create two subclasses:
- `Car` — adds `int numberOfDoors`. Override `describe()` to append `"([numberOfDoors]-door)"` to the parent result.
- `Motorcycle` — adds `boolean hasSidecar`. Override `describe()` to append `"(sidecar: yes/no)"`.

Both subclasses must call `super()` in their constructors and `super.describe()` in their `describe()` methods.

Create a `Garage` class with a `Vehicle[]` array. Add a method `printAll()` that calls `describe()` on every vehicle. Demonstrate that the correct `describe()` version is called for each type.

### Exercise 3.2: Employee Hierarchy
Create the following hierarchy:

```
Employee (base)
├── Manager       (extends Employee)
└── Contractor    (extends Employee)
```

`Employee` has: `String name`, `String id`, `double baseSalary`, and an abstract-style method `double calculateAnnualCompensation()`.

`Manager` adds: `int teamSize` and a `double bonus`. Annual compensation = `baseSalary * 12 + bonus`.

`Contractor` adds: `double hourlyRate` and `int hoursPerYear`. Annual compensation = `hourlyRate * hoursPerYear`.

Add `toString()` to all three classes. In `main`, create a mixed array of employees, call `calculateAnnualCompensation()` on each, and print totals. Demonstrate constructor chaining with `super(...)`.

### Exercise 3.3: Shape with Concrete and Abstract Logic
Create an abstract class `Shape` with:
- A `String color` field with getter/setter.
- An abstract method `double area()`.
- An abstract method `double perimeter()`.
- A concrete method `String describe()` that uses `area()` and `perimeter()` to produce a readable summary.

Create concrete subclasses `Circle`, `Rectangle`, and `RightTriangle`. Each must implement `area()` and `perimeter()`, and may optionally override `describe()`.

---

## Exercise Set 4: Polymorphism and Interfaces

### Exercise 4.1: Comparable Shapes
Extend the `Shape` hierarchy from Exercise 3.3 to implement `Comparable<Shape>`. Shapes should compare by area — a larger area is "greater".

Create an array of 6 mixed shapes (circles, rectangles, triangles) and sort it using `Arrays.sort()`. Print the shapes before and after sorting.

### Exercise 4.2: Printable and Saveable Interfaces
Define two interfaces:
- `Printable` — one method: `void print()`.
- `Saveable` — one method: `String serialize()`.

Create a class `Product` that implements both interfaces. `print()` outputs a formatted product description. `serialize()` returns a CSV representation `"id,name,price"`.

Create a method that accepts `Printable` objects and calls `print()` on each. Demonstrate that you can pass `Product` objects without them knowing they are `Product` at the call site.

### Exercise 4.3: Strategy Pattern with Interfaces
You are building a payment system. Define a `PaymentStrategy` interface with one method: `boolean pay(double amount)`.

Implement three strategies:
- `CreditCardPayment` — always succeeds; prints the last 4 digits of the card.
- `PayPalPayment` — succeeds only if the user has sufficient balance (set in the constructor).
- `BitcoinPayment` — applies a 1% transaction fee; succeeds only if `amount * 1.01 <= walletBalance`.

Create a `ShoppingCart` class that holds a `PaymentStrategy` field. It has a `checkout()` method that tries to pay the total amount and reports success or failure.

Demonstrate switching the payment strategy at runtime.

---

## Exercise Set 5: Abstraction and Advanced OOP

### Exercise 5.1: Abstract Logger
Define an abstract class `Logger` with:
- An abstract method `void writeLog(String message)`.
- A concrete method `void log(String level, String message)` that formats the log entry as `"[LEVEL] message"` and calls `writeLog`.
- The `log` method should also prepend a timestamp using `java.time.LocalDateTime.now()`.

Create two concrete loggers:
- `ConsoleLogger` — writes to `System.out`.
- `InMemoryLogger` — stores log entries in a `List<String>` and provides a `getEntries()` method.

Demonstrate both loggers using the same `log()` calls from the abstract class.

### Exercise 5.2: Interface Default Methods
Define a `Drawable` interface with:
- An abstract method `void draw()`.
- A default method `void drawWithBorder()` that calls `draw()` wrapped with lines of dashes.
- A static method `Drawable createDot()` that returns a simple implementation that prints `"."`.

Create three classes — `Circle`, `Square`, `Triangle` — that implement `Drawable`. Show that all three can use `drawWithBorder()` without overriding it, and that `Circle` can override `drawWithBorder()` if it wants a different border style.

### Exercise 5.3: Builder Pattern
Implement a builder for a `Person` class that has many optional fields: `String firstName`, `String lastName` (required), `int age`, `String email`, `String phone`, `String address`.

The builder pattern requires:
- A static inner class `Person.Builder`.
- Required fields are set in the `Builder` constructor.
- Each optional field has a fluent setter that returns `this` (the builder).
- A `build()` method that validates and constructs the `Person`.

The `Person` constructor is private and takes only a `Builder`. This prevents construction without the builder.

Demonstrate creating several `Person` objects with different combinations of optional fields.

---

## Exercise Set 6: Records and Modern OOP

### Exercise 6.1: Converting a Class to a Record
Take the `Point` class from Exercise 2.3 and rewrite it as a record. Demonstrate that:
- The generated `equals()` compares by value, not by reference.
- `toString()` is automatically meaningful.
- `hashCode()` is automatically consistent with `equals()`.
- Records are immutable — there are no setters.

Add the `distanceTo` and `translate` methods as instance methods on the record.

### Exercise 6.2: Record with Compact Constructor
Create a record `Range` that represents a numeric range with `double min` and `double max`. Use a compact constructor to validate that `min <= max`. Throw `IllegalArgumentException` with a clear message if the invariant is violated.

Add methods:
- `boolean contains(double value)` — returns true if `min <= value <= max`.
- `double width()` — returns `max - min`.
- `Range intersect(Range other)` — returns a new Range representing the overlap of the two ranges, or throws `IllegalArgumentException` if there is no overlap.

### Exercise 6.3: Downcasting and Pattern Matching
Create a sealed-style class hierarchy (do not use `sealed` — just regular inheritance):
- Base class `Shape` with abstract method `double area()`.
- Subclasses: `Circle`, `Rectangle`, `Triangle`.

Write a method `describeShape(Shape shape)` that:
1. Uses the old-style `instanceof` check with explicit cast to call type-specific methods.
2. Then rewrites the same logic using Java 16+ pattern matching `instanceof`:
   ```java
   if (shape instanceof Circle c) {
       // use c directly
   }
   ```

Demonstrate both versions produce identical output.

---

## Mini-Project 1: Library Management System

Design a library management system using the OOP concepts from this entire block.

**Required classes:**
- `Book` — title, author, ISBN, available (boolean).
- `Member` — name, memberId, list of borrowed books (`Book[]` or hardcode max 5).
- `Library` — holds a collection of `Book` objects and `Member` objects.

**Required operations in `Library`:**
- `addBook(Book book)`
- `registerMember(Member member)`
- `borrowBook(String memberId, String isbn)` — marks the book unavailable, adds to member's list. Fail gracefully if book is unavailable or member has hit the limit.
- `returnBook(String memberId, String isbn)` — marks the book available, removes from member's list.
- `searchByTitle(String keyword)` — returns all books whose title contains the keyword (case-insensitive).
- `printAvailableBooks()`
- `printMemberReport(String memberId)` — shows all books currently borrowed by the member.

Demonstrate all operations in `main`.

---

## Mini-Project 2: Animal Kingdom Simulator

Build a small animal simulation using inheritance and polymorphism.

**Hierarchy:**
```
Animal (abstract base)
├── Mammal (abstract)
│   ├── Dog
│   └── Cat
└── Bird (abstract)
    ├── Eagle
    └── Parrot
```

**Abstract `Animal`:** fields `name`, `age`; abstract method `String makeSound()`.

**Abstract `Mammal`:** adds `String furColor`; abstract method `void move()` that prints how the animal moves.

**Abstract `Bird`:** adds `double wingspan`; abstract method `void fly()`.

**Concrete classes:** each provides `makeSound()`, `move()` or `fly()`, and a meaningful `toString()`.

**Simulation:** Create an `Animal[]` array with all six concrete types. Write a loop that calls `makeSound()` on each. Write separate loops that use `instanceof` to cast to `Mammal` and call `move()`, and cast to `Bird` and call `fly()`.

Then add an `Identifiable` interface with method `String getId()` (return the name). Have all concrete classes implement it. Write a method that accepts `Identifiable[]` and prints each ID.

---

## Common Mistakes

### Calling instance methods or accessing instance fields on a null reference
If a variable has not been assigned (or was assigned `null`), calling any method on it throws `NullPointerException`.

```java
BankAccount account = null;
account.deposit(100); // NullPointerException — account is null

// Always initialize before use:
BankAccount account = new BankAccount("001", "Alice", 0.0);
account.deposit(100); // Works correctly.
```

### Forgetting super() in a subclass constructor
If the superclass does not have a no-argument constructor, the subclass must explicitly call `super(...)` with appropriate arguments. The `super(...)` call must be the first statement in the constructor body.

```java
class Vehicle {
    String make;
    Vehicle(String make) { this.make = make; }
}

class Car extends Vehicle {
    int doors;

    // Wrong: no call to super — will not compile if Vehicle has no no-arg constructor.
    Car(int doors) {
        this.doors = doors; // Compile error: constructor Vehicle() is undefined
    }

    // Correct:
    Car(String make, int doors) {
        super(make);        // Must be first.
        this.doors = doors;
    }
}
```

### Using == to compare String objects
`==` on objects tests reference equality (whether they are the same object in memory), not value equality. Use `equals()` for content comparison.

```java
String a = new String("hello");
String b = new String("hello");

System.out.println(a == b);       // false — different objects
System.out.println(a.equals(b)); // true  — same characters
```

### Not overriding equals() and hashCode() together
If you override `equals()`, you must override `hashCode()` too. Objects that are equal must have the same hash code. Failing to do this breaks `HashMap`, `HashSet`, and all hash-based structures.

```java
// Wrong: overrides equals but not hashCode.
// Two "equal" objects could have different hash codes and be treated as distinct in a HashSet.
class BadPoint {
    int x, y;
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BadPoint p)) return false;
        return x == p.x && y == p.y;
    }
    // Missing: @Override public int hashCode() { return Objects.hash(x, y); }
}
```

### Shadowing a field with a method parameter of the same name
Without `this.`, the assignment `name = name` assigns the parameter to itself and leaves the field unchanged. Always use `this.fieldName = paramName` in constructors and setters.

```java
class Person {
    private String name;

    Person(String name) {
        name = name;       // Wrong: assigns the parameter to itself; the field stays null.
        this.name = name;  // Correct: assigns the parameter to the instance field.
    }
}
```

### Treating an abstract class as directly instantiable
Abstract classes cannot be instantiated with `new`. Only concrete subclasses that implement all abstract methods can be instantiated.

```java
abstract class Shape {
    abstract double area();
}

// Shape s = new Shape(); // Compile error: Shape is abstract; cannot be instantiated.
Shape s = new Circle(5.0); // Correct: Circle is a concrete subclass of Shape.
```

---

Solutions for these exercises are in the `solutions/` subfolder. The OOP solutions are organized as one file per exercise set, with all three exercises in each file.
