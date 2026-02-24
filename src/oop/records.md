# Records

A **record** is a special kind of class in Java designed for one specific purpose: **carrying immutable data**. Before records existed, creating a simple data-holding class required writing a constructor, private fields, getters, `equals()`, `hashCode()`, and `toString()` — often 40–60 lines of boilerplate for what was conceptually just a few fields. Records eliminate all of that.

```
Think of it like this:
  A regular class is a full house — you build every room yourself.
  A record is a furnished apartment — the essentials are already there.
  You just tell it what data it holds.
```

Records were introduced as a standard feature in **Java 16**.

---

## 1. The Problem Records Solve

Consider what it takes to represent a simple `Point` in a regular class:

```java
// Regular class — lots of boilerplate for just two fields
public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point[x=" + x + ", y=" + y + "]";
    }
}
```

With a record, this becomes:

```java
// Record — identical behaviour, one line
public record Point(int x, int y) { }
```

Same result. Zero boilerplate.

---

## 2. Basic Syntax

```java
public record RecordName(Type field1, Type field2, ...) { }
```

The fields listed in the parentheses are called the **record components**. They define the structure of the record.

```java
public record Person(String name, int age) { }
```

```java
// Creating a record instance — same as a regular class
Person person = new Person("Alice", 30);

// Accessing components — accessor methods (NOT getters with "get" prefix)
System.out.println(person.name()); // Output: Alice
System.out.println(person.age());  // Output: 30

// toString() is automatically generated
System.out.println(person);        // Output: Person[name=Alice, age=30]
```

---

## 3. What the Compiler Generates Automatically

When you declare a record, the Java compiler generates all of the following for you. You do not write any of this — it happens behind the scenes.

### The canonical constructor
A constructor that takes all components as parameters and assigns them to the fields.
```java
public record Product(String name, double price) { }

// The compiler generates this:
// public Product(String name, double price) {
//     this.name = name;
//     this.price = price;
// }

Product p = new Product("Laptop", 999.99);
```

### Accessor methods
One method per component, named exactly after the component (no `get` prefix).
```java
Product p = new Product("Laptop", 999.99);
System.out.println(p.name());   // Laptop
System.out.println(p.price());  // 999.99
```

### `equals()` — value-based comparison
Two records are equal if they are the same type and all their components are equal. This is different from regular classes, where `equals()` checks object identity by default.
```java
Product p1 = new Product("Laptop", 999.99);
Product p2 = new Product("Laptop", 999.99);
Product p3 = new Product("Mouse", 29.99);

System.out.println(p1.equals(p2)); // true  — same values
System.out.println(p1.equals(p3)); // false — different values
System.out.println(p1 == p2);      // false — different objects in memory
```

### `hashCode()`
Consistent with `equals()` — two records with equal components produce the same hash code. This makes records safe to use in `HashMap` and `HashSet`.

### `toString()`
Returns a readable string in the format `RecordName[field1=value1, field2=value2]`.
```java
System.out.println(new Product("Laptop", 999.99));
// Output: Product[name=Laptop, price=999.99]
```

---

## 4. Records Are Immutable

All record fields are **implicitly `private` and `final`**. Once a record is created, its data cannot be changed. There are no setters.

```java
public record Coordinate(double latitude, double longitude) { }

Coordinate coord = new Coordinate(1.286389, 36.817223);
// coord.latitude = 2.0;  // Compilation error — field is final
```

This immutability is intentional. Records are designed for data that should not change after creation — API responses, configuration values, method return bundles, database query results.

---

## 5. The Compact Constructor

The **compact constructor** lets you add validation or transformation logic to the canonical constructor without repeating the parameter list or the assignments. The assignments still happen automatically after your code runs.

```java
public record Person(String name, int age) {
    // Compact constructor — no parameter list, no this.field = field assignments
    public Person {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 0 and 150.");
        }
        // Assignments happen automatically after this block
    }
}
```

```java
Person valid   = new Person("Alice", 30);   // OK
Person invalid = new Person("", 30);        // throws IllegalArgumentException
Person bad     = new Person("Bob", -5);     // throws IllegalArgumentException
```

You can also **normalize** data in the compact constructor:
```java
public record Email(String address) {
    public Email {
        if (address == null || !address.contains("@")) {
            throw new IllegalArgumentException("Invalid email: " + address);
        }
        address = address.toLowerCase().strip(); // normalize before assignment
    }
}
```

```java
Email e = new Email("  Alice@Example.COM  ");
System.out.println(e.address()); // Output: alice@example.com
```

---

## 6. Custom Constructors

You can also define a standard constructor with an explicit parameter list, as long as it eventually calls the canonical constructor using `this(...)`.

```java
public record Range(int min, int max) {
    // Canonical constructor called via this()
    public Range {
        if (min > max) {
            throw new IllegalArgumentException("min cannot exceed max.");
        }
    }

    // Custom convenience constructor — single value range
    public Range(int value) {
        this(value, value); // delegates to the canonical constructor
    }
}
```

```java
Range normal = new Range(1, 10);
Range single = new Range(5);       // delegates to Range(5, 5)
Range bad    = new Range(10, 1);   // throws IllegalArgumentException
```

---

## 7. Adding Methods to Records

Records can have instance methods, static methods, and static fields. They cannot have non-static (instance) fields beyond the components.

### Instance methods
```java
public record Circle(double radius) {
    public double area() {
        return Math.PI * radius * radius;
    }

    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    public boolean isLargerThan(Circle other) {
        return this.radius > other.radius;
    }
}
```

```java
Circle c = new Circle(5.0);
System.out.println(c.area());       // Output: 78.53981633974483
System.out.println(c.perimeter());  // Output: 31.41592653589793

Circle small = new Circle(3.0);
System.out.println(c.isLargerThan(small)); // Output: true
```

### Static methods and fields
```java
public record Temperature(double celsius) {
    // Static constant — the one exception to "no non-component fields"
    public static final double ABSOLUTE_ZERO = -273.15;

    // Static factory methods — a common pattern with records
    public static Temperature fromFahrenheit(double f) {
        return new Temperature((f - 32) * 5.0 / 9.0);
    }

    public static Temperature fromKelvin(double k) {
        return new Temperature(k - 273.15);
    }

    // Instance method
    public double toFahrenheit() {
        return celsius * 9.0 / 5.0 + 32;
    }

    public boolean isFreezing() {
        return celsius <= 0;
    }
}
```

```java
Temperature boiling  = new Temperature(100.0);
Temperature body     = Temperature.fromFahrenheit(98.6);
Temperature absolute = Temperature.fromKelvin(0);

System.out.println(boiling.toFahrenheit());  // Output: 212.0
System.out.println(body.celsius());          // Output: 36.99...
System.out.println(absolute.celsius());      // Output: -273.15
System.out.println(boiling.isFreezing());    // Output: false
```

---

## 8. Implementing Interfaces

Records can implement interfaces. This is one of the most powerful features — it lets records participate in polymorphism.

```java
public interface Describable {
    String describe();
}

public interface Priceable {
    double getPrice();
    default boolean isExpensive() {
        return getPrice() > 500.0;
    }
}
```

```java
public record Product(String name, double price) implements Describable, Priceable {
    @Override
    public String describe() {
        return name + " costs $" + String.format("%.2f", price);
    }

    @Override
    public double getPrice() {
        return price;
    }
}
```

```java
Product laptop = new Product("Laptop", 999.99);
Product mouse  = new Product("Mouse", 29.99);

System.out.println(laptop.describe());      // Output: Laptop costs $999.99
System.out.println(laptop.isExpensive());   // Output: true
System.out.println(mouse.isExpensive());    // Output: false

// Polymorphism — store as interface type
Describable d = new Product("Keyboard", 49.99);
System.out.println(d.describe());           // Output: Keyboard costs $49.99
```

---

## 9. Overriding Auto-Generated Methods

You can override any of the three auto-generated methods — `toString()`, `equals()`, and `hashCode()` — if the default behaviour does not suit your needs.

### Overriding `toString()`
```java
public record Student(String name, int age, double gpa) {
    @Override
    public String toString() {
        return String.format("Student{name='%s', age=%d, gpa=%.2f}", name, age, gpa);
    }
}
```

```java
System.out.println(new Student("Alice", 20, 3.85));
// Default would print: Student[name=Alice, age=20, gpa=3.85]
// Override prints:     Student{name='Alice', age=20, gpa=3.85}
```

### Overriding `equals()` — case-insensitive name comparison
```java
public record Tag(String label) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag other)) return false;
        return label.equalsIgnoreCase(other.label);
    }

    @Override
    public int hashCode() {
        return label.toLowerCase().hashCode();
    }
}
```

```java
Tag t1 = new Tag("Java");
Tag t2 = new Tag("java");
System.out.println(t1.equals(t2)); // Output: true (case-insensitive)
```

---

## 10. Nested and Generic Records

### Nested records
Records are implicitly `static` when nested, so they do not hold a reference to the outer class.

```java
public class Order {
    public record Item(String productName, int quantity, double unitPrice) {
        public double totalPrice() {
            return quantity * unitPrice;
        }
    }

    private String orderId;
    private Item[] items;

    public Order(String orderId, Item[] items) {
        this.orderId = orderId;
        this.items = items;
    }
}
```

```java
Order.Item keyboard = new Order.Item("Keyboard", 2, 49.99);
System.out.println(keyboard.totalPrice()); // Output: 99.98
```

### Generic records
Records support generics the same way classes do.

```java
public record Pair<A, B>(A first, B second) {
    public Pair<B, A> swap() {
        return new Pair<>(second, first);
    }
}
```

```java
Pair<String, Integer> p    = new Pair<>("Alice", 30);
Pair<Integer, String> swap = p.swap();

System.out.println(p.first());    // Output: Alice
System.out.println(p.second());   // Output: 30
System.out.println(swap.first()); // Output: 30
```

```java
// Useful as a method return type when you need to return two values
public record Result<T>(T value, String message) {
    public boolean isSuccess() {
        return message == null || message.isBlank();
    }
}
```

```java
Result<Integer> ok  = new Result<>(42, "");
Result<Integer> err = new Result<>(0, "Division by zero");

System.out.println(ok.isSuccess());  // true
System.out.println(err.isSuccess()); // false
```

---

## 11. Records and Pattern Matching (Java 21+)

Java 21 introduced **record patterns**, which let you destructure a record's components directly inside an `instanceof` check or a `switch` expression.

### With `instanceof`
```java
public record Point(int x, int y) { }

Object obj = new Point(3, 7);

// Old way
if (obj instanceof Point p) {
    System.out.println("x=" + p.x() + ", y=" + p.y());
}

// Record pattern — destructure directly
if (obj instanceof Point(int x, int y)) {
    System.out.println("x=" + x + ", y=" + y);
}
```

### With `switch`
```java
public record Circle(double radius) { }
public record Rectangle(double width, double height) { }

public static double area(Object shape) {
    return switch (shape) {
        case Circle(double r)            -> Math.PI * r * r;
        case Rectangle(double w, double h) -> w * h;
        default -> throw new IllegalArgumentException("Unknown shape");
    };
}
```

```java
System.out.println(area(new Circle(5)));         // 78.53...
System.out.println(area(new Rectangle(4, 6)));   // 24.0
```

---

## 12. Records vs Regular Classes

| Feature                  | Regular Class                    | Record                              |
|--------------------------|----------------------------------|-------------------------------------|
| Fields                   | Mutable by default               | Immutable (`private final`)         |
| Constructor              | You write it                     | Auto-generated (canonical)          |
| Accessors                | Getters with `get` prefix        | Methods named after the component   |
| `equals()` / `hashCode()`| Checks identity unless overridden| Value-based, auto-generated         |
| `toString()`             | Memory address unless overridden | Readable format, auto-generated     |
| Inheritance              | Can extend other classes         | Cannot extend any class             |
| Interfaces               | Can implement                    | Can implement                       |
| Boilerplate              | High                             | Zero                                |
| Best for                 | Complex domain objects           | Immutable data carriers             |

---

## 13. Restrictions

Records have a few deliberate limitations:

- **Cannot extend another class.** Records implicitly extend `java.lang.Record`. They can only implement interfaces.
- **Cannot declare non-static instance fields** beyond the record components. Extra state must come from the components themselves.
- **Cannot be abstract.** Records are always concrete and final — they cannot be extended either.
- **Components are always final.** You cannot add a setter or modify a component after construction.

```java
// These are all compilation errors:
public record Bad extends SomeClass(int x) { }  // cannot extend a class
public abstract record Bad(int x) { }            // cannot be abstract
```

---

## 14. Common Use Cases

### Data Transfer Objects (DTOs)
Carrying data between application layers without business logic attached.
```java
public record UserDto(long id, String username, String email) { }
```

### API response wrappers
```java
public record ApiResponse<T>(int status, String message, T data) {
    public boolean isOk() {
        return status >= 200 && status < 300;
    }
}
```

### Method return bundles — returning multiple values
```java
public record MinMax(int min, int max) { }

public static MinMax findMinMax(int[] arr) {
    int min = arr[0], max = arr[0];
    for (int n : arr) {
        if (n < min) min = n;
        if (n > max) max = n;
    }
    return new MinMax(min, max);
}
```

```java
int[] data = {3, 1, 7, 2, 9, 4};
MinMax result = findMinMax(data);
System.out.println("Min: " + result.min() + ", Max: " + result.max());
// Output: Min: 1, Max: 9
```

### Configuration or settings values
```java
public record DatabaseConfig(String host, int port, String dbName) {
    public String connectionString() {
        return "jdbc:mysql://" + host + ":" + port + "/" + dbName;
    }
}
```

### Map keys and collection elements
Because records have value-based `equals()` and `hashCode()`, they work correctly as `HashMap` keys and in `HashSet`.
```java
// Regular class as key would need manual equals/hashCode
// Record works out of the box
java.util.Map<Point, String> labels = new java.util.HashMap<>();
labels.put(new Point(0, 0), "Origin");
labels.put(new Point(1, 0), "Unit X");

System.out.println(labels.get(new Point(0, 0))); // Output: Origin
```

---

## 15. Complete Example

```java
// Value object for a monetary amount
public record Money(double amount, String currency) {
    public Money {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative: " + amount);
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency cannot be blank.");
        }
        currency = currency.toUpperCase().strip();
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                "Cannot add " + this.currency + " and " + other.currency
            );
        }
        return new Money(this.amount + other.amount, this.currency);
    }

    public Money multiply(double factor) {
        return new Money(this.amount * factor, this.currency);
    }

    public boolean isGreaterThan(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot compare different currencies.");
        }
        return this.amount > other.amount;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", amount, currency);
    }
}
```

```java
// Product using Money as a component
public record Product(String name, Money price, int stock) {
    public Product {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative.");
        }
    }

    public boolean isAvailable() {
        return stock > 0;
    }

    public Money totalValue() {
        return price.multiply(stock);
    }
}
```

```java
// Order line item
public record OrderLine(Product product, int quantity) {
    public OrderLine {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be at least 1.");
        }
        if (quantity > product.stock()) {
            throw new IllegalArgumentException(
                "Not enough stock for " + product.name() +
                ". Requested: " + quantity + ", Available: " + product.stock()
            );
        }
    }

    public Money lineTotal() {
        return product.price().multiply(quantity);
    }
}
```

```java
// Main — wires everything together
public class Main {
    public static void main(String[] args) {
        Money laptopPrice  = new Money(999.99, "usd");
        Money mousePrice   = new Money(29.99, "usd");

        Product laptop = new Product("Laptop", laptopPrice, 10);
        Product mouse  = new Product("Mouse", mousePrice, 50);

        System.out.println(laptop);
        // Output: Product[name=Laptop, price=999.99 USD, stock=10]

        System.out.println("Laptop available: " + laptop.isAvailable());
        // Output: Laptop available: true

        System.out.println("Total laptop stock value: " + laptop.totalValue());
        // Output: Total laptop stock value: 9999.90 USD

        // Order lines
        OrderLine line1 = new OrderLine(laptop, 2);
        OrderLine line2 = new OrderLine(mouse, 3);

        Money orderTotal = line1.lineTotal().add(line2.lineTotal());

        System.out.println("Line 1 total: " + line1.lineTotal()); // 1999.98 USD
        System.out.println("Line 2 total: " + line2.lineTotal()); // 89.97 USD
        System.out.println("Order total:  " + orderTotal);        // 2089.95 USD

        // Value-based equality
        Money m1 = new Money(100.0, "usd");
        Money m2 = new Money(100.0, "USD"); // compact constructor normalises to uppercase
        System.out.println(m1.equals(m2));  // true
    }
}
```

---

## 16. Key Takeaways

- A record is a concise, immutable data carrier — the compiler generates the constructor, accessors, `equals()`, `hashCode()`, and `toString()` automatically
- Components are accessed via methods named after them (`name()`, not `getName()`)
- All components are `private final` — records are immutable by design
- Use the **compact constructor** to validate or normalize data without repeating assignments
- Records can implement interfaces and participate in polymorphism, but cannot extend a class
- Records have value-based equality — two records with the same component values are equal
- Static methods, static fields, and instance methods can all be added to a record
- Records work naturally as `HashMap` keys, DTO objects, method return bundles, and configuration values
- **Record patterns** (Java 21+) allow destructuring records directly in `instanceof` and `switch`

---

Records are not a replacement for regular classes — they are a tool for a specific job. When your class exists purely to hold data and pass it around, a record removes the noise and makes the intent clear immediately.
