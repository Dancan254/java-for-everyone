# Enums

An enum (short for enumeration) is a special Java type used to define a fixed set of named constants. Enums eliminate the ambiguity and fragility of using raw integers or strings to represent a limited range of values. In Java, enums are full-blown classes, which means they can have fields, constructors, and methods.

```
Think of it like this:
  A traffic light can only be RED, YELLOW, or GREEN.
  If you store that as an int (0, 1, 2), nothing stops you from passing 99.
  If you store it as an enum, the compiler enforces that only valid values are used.
  Same idea for days of the week, order statuses, directions, seasons — any fixed set of choices.
```

## 1. What Is an Enum and Why Use It

Before enums existed, developers used integer constants to represent a group of related values.

```java
// The old way: int constants
public class Direction {
    public static final int NORTH = 0;
    public static final int SOUTH = 1;
    public static final int EAST  = 2;
    public static final int WEST  = 3;
}
```

This approach has serious problems:

```java
// Nothing stops this from compiling
int heading = 42;          // Completely invalid, but accepted
int heading2 = Direction.NORTH + Direction.SOUTH; // Arithmetic on "directions" — meaningless

public void move(int direction) {
    // direction could be anything — no type safety at all
}
```

An enum solves all of this at compile time:

```java
// The enum way
public enum Direction {
    NORTH, SOUTH, EAST, WEST
}
```

```java
public void move(Direction direction) {
    // Only NORTH, SOUTH, EAST, or WEST can be passed here
    // Passing 42 is a compile error
}

Direction heading = Direction.NORTH; // Clear and safe
```

Enums are full classes in Java. They implicitly extend `java.lang.Enum` and can have fields, constructors, methods, and implement interfaces. Each constant is a singleton instance of the enum type.

## 2. Basic Enum Syntax

Declaring an enum is straightforward. Constants are listed by convention in ALL_CAPS, separated by commas.

```java
public enum Season {
    SPRING, SUMMER, FALL, WINTER
}
```

```java
public enum Direction {
    NORTH, SOUTH, EAST, WEST
}
```

Use enum constants with the `EnumType.CONSTANT` syntax and compare them with `==`, not `.equals()`. Both work, but `==` is preferred because it is null-safe and more readable.

```java
Season current = Season.SUMMER;

if (current == Season.SUMMER) {
    System.out.println("It's warm outside.");
    // Output: It's warm outside.
}

// .equals() also works but is unnecessary
System.out.println(current.equals(Season.SUMMER)); // Output: true
```

Enums work naturally in switch statements and switch expressions:

```java
Direction heading = Direction.NORTH;

// Traditional switch statement
switch (heading) {
    case NORTH:
        System.out.println("Going north");
        break;
    case SOUTH:
        System.out.println("Going south");
        break;
    case EAST:
        System.out.println("Going east");
        break;
    case WEST:
        System.out.println("Going west");
        break;
}
// Output: Going north
```

## 3. Built-in Enum Methods

Every enum inherits several useful methods from `java.lang.Enum`.

```java
public enum Season {
    SPRING, SUMMER, FALL, WINTER
}
```

`values()` returns all constants as an array, in declaration order:

```java
Season[] seasons = Season.values();

for (Season s : seasons) {
    System.out.println(s);
}
// Output:
// SPRING
// SUMMER
// FALL
// WINTER
```

`ordinal()` returns the zero-based position of a constant in the enum declaration:

```java
System.out.println(Season.SPRING.ordinal()); // Output: 0
System.out.println(Season.SUMMER.ordinal()); // Output: 1
System.out.println(Season.FALL.ordinal());   // Output: 2
System.out.println(Season.WINTER.ordinal()); // Output: 3
```

`name()` returns the exact name of the constant as a String:

```java
Season s = Season.FALL;
System.out.println(s.name()); // Output: FALL
```

`valueOf(String)` looks up a constant by its name. It throws `IllegalArgumentException` if the name does not match any constant:

```java
Season s = Season.valueOf("WINTER");
System.out.println(s); // Output: WINTER

// Season.valueOf("winter"); // IllegalArgumentException — case-sensitive
```

Iterating over all values with a for-each loop is a common pattern:

```java
System.out.println("All seasons and their ordinals:");
for (Season s : Season.values()) {
    System.out.println(s.ordinal() + ": " + s.name());
}
// Output:
// 0: SPRING
// 1: SUMMER
// 2: FALL
// 3: WINTER
```

## 4. Enum Fields and Constructors

Enums can carry data by declaring fields and a constructor. The constructor is always private — you cannot instantiate an enum from outside the class because the constants are the only instances that will ever exist.

```java
public enum DayOfWeek {
    MONDAY(false),
    TUESDAY(false),
    WEDNESDAY(false),
    THURSDAY(false),
    FRIDAY(false),
    SATURDAY(true),
    SUNDAY(true);

    private final boolean weekend;

    // Constructor is implicitly private
    DayOfWeek(boolean weekend) {
        this.weekend = weekend;
    }

    public boolean isWeekend() {
        return weekend;
    }
}
```

Each constant calls the constructor with its own arguments. The constructor runs once per constant when the class is loaded:

```java
for (DayOfWeek day : DayOfWeek.values()) {
    System.out.println(day + " -> weekend: " + day.isWeekend());
}
// Output:
// MONDAY    -> weekend: false
// TUESDAY   -> weekend: false
// WEDNESDAY -> weekend: false
// THURSDAY  -> weekend: false
// FRIDAY    -> weekend: false
// SATURDAY  -> weekend: true
// SUNDAY    -> weekend: true
```

Here is another example with multiple fields, modeled after the classic `Planet` enum:

```java
public enum Planet {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS  (4.869e+24, 6.0518e6),
    EARTH  (5.976e+24, 6.37814e6),
    MARS   (6.421e+23, 3.3972e6);

    private final double mass;   // in kilograms
    private final double radius; // in meters

    Planet(double mass, double radius) {
        this.mass   = mass;
        this.radius = radius;
    }

    static final double G = 6.67300E-11;

    public double surfaceGravity() {
        return G * mass / (radius * radius);
    }

    public double surfaceWeight(double otherMass) {
        return otherMass * surfaceGravity();
    }
}
```

```java
double earthWeight = 75.0;
double mass = earthWeight / Planet.EARTH.surfaceGravity();

for (Planet p : Planet.values()) {
    System.out.printf("Weight on %s: %.2f N%n", p, p.surfaceWeight(mass));
}
// Output:
// Weight on MERCURY: 28.33 N
// Weight on VENUS:   67.89 N
// Weight on EARTH:   75.00 N
// Weight on MARS:    28.46 N
```

## 5. Enum Methods

Enums can define abstract methods so that each constant provides its own implementation. This is a powerful pattern for behavior-per-constant.

```java
public enum Operation {
    PLUS {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },
    MULTIPLY {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE {
        @Override
        public double apply(double x, double y) {
            if (y == 0) throw new ArithmeticException("Division by zero");
            return x / y;
        }
    };

    // Each constant must implement this method
    public abstract double apply(double x, double y);
}
```

```java
double x = 10.0;
double y = 4.0;

for (Operation op : Operation.values()) {
    System.out.printf("%.1f %s %.1f = %.1f%n", x, op, y, op.apply(x, y));
}
// Output:
// 10.0 PLUS     4.0 = 14.0
// 10.0 MINUS    4.0 = 6.0
// 10.0 MULTIPLY 4.0 = 40.0
// 10.0 DIVIDE   4.0 = 2.5
```

Each constant is effectively an anonymous subclass of `Operation` that provides its own body for `apply`. This keeps all behavior for each operation co-located with the constant itself.

## 6. Enum Implementing an Interface

Because enums are full classes, they can implement interfaces. This is useful when you want to enforce a contract across all constants.

```java
public interface Describable {
    String getDescription();
}
```

```java
public enum StatusCode implements Describable {
    OK(200),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    UNAUTHORIZED(401);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return code + " " + name().replace('_', ' ');
    }
}
```

```java
for (StatusCode sc : StatusCode.values()) {
    System.out.println(sc.getDescription());
}
// Output:
// 200 OK
// 404 NOT FOUND
// 500 INTERNAL SERVER ERROR
// 401 UNAUTHORIZED
```

The enum can also be used wherever a `Describable` is expected:

```java
public static void print(Describable d) {
    System.out.println(d.getDescription());
}

print(StatusCode.NOT_FOUND); // Output: 404 NOT FOUND
```

## 7. Enum in switch Expressions (Java 14+)

Java 14 introduced switch expressions as a standard feature. Enums are the ideal companion because the compiler can verify exhaustiveness — it knows every possible constant.

The old switch statement style:

```java
Season s = Season.WINTER;

switch (s) {
    case SPRING:
        System.out.println("Plant seeds");
        break;
    case SUMMER:
        System.out.println("Go swimming");
        break;
    case FALL:
        System.out.println("Rake leaves");
        break;
    case WINTER:
        System.out.println("Build a snowman");
        break;
}
// Output: Build a snowman
```

The new switch expression style — no `break`, no fall-through, returns a value:

```java
Season s = Season.FALL;

String activity = switch (s) {
    case SPRING   -> "Plant seeds";
    case SUMMER   -> "Go swimming";
    case FALL     -> "Rake leaves";
    case WINTER   -> "Build a snowman";
};

System.out.println(activity); // Output: Rake leaves
```

Because `Season` has exactly four constants and all four are handled, the compiler accepts the switch expression without a `default` branch. If you add a new constant to the enum, the compiler will flag every switch expression that no longer covers all cases, preventing silent bugs.

## 8. EnumSet and EnumMap

The `java.util.EnumSet` and `java.util.EnumMap` classes are purpose-built, high-performance alternatives to `HashSet` and `HashMap` when working with enum keys. Internally they use bit vectors (for `EnumSet`) and a simple array indexed by ordinal (for `EnumMap`), making them significantly faster and more memory-efficient than their general-purpose counterparts.

### EnumSet

```java
import java.util.EnumSet;

// A set of specific constants
EnumSet<DayOfWeek> workdays = EnumSet.of(
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
    DayOfWeek.FRIDAY
);
System.out.println(workdays);
// Output: [MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY]

// All constants of the enum
EnumSet<DayOfWeek> allDays = EnumSet.allOf(DayOfWeek.class);
System.out.println(allDays.size()); // Output: 7

// A contiguous range (inclusive on both ends)
EnumSet<DayOfWeek> midweek = EnumSet.range(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY);
System.out.println(midweek);
// Output: [TUESDAY, WEDNESDAY, THURSDAY]

// Membership test
System.out.println(workdays.contains(DayOfWeek.SATURDAY)); // Output: false
```

### EnumMap

```java
import java.util.EnumMap;
import java.util.Map;

EnumMap<Season, String> activities = new EnumMap<>(Season.class);
activities.put(Season.SPRING, "Gardening");
activities.put(Season.SUMMER, "Swimming");
activities.put(Season.FALL,   "Hiking");
activities.put(Season.WINTER, "Skiing");

System.out.println(activities.get(Season.FALL)); // Output: Hiking

for (Map.Entry<Season, String> entry : activities.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}
// Output:
// SPRING: Gardening
// SUMMER: Swimming
// FALL: Hiking
// WINTER: Skiing
```

Prefer `EnumSet` over `HashSet<MyEnum>` and `EnumMap` over `HashMap<MyEnum, V>` whenever you know the key type is an enum.

## 9. Singleton Pattern with Enum

The simplest, safest way to implement a singleton in Java is a single-constant enum.

```java
public enum AppConfig {
    INSTANCE;

    private String databaseUrl = "jdbc:postgresql://localhost:5432/mydb";
    private int maxConnections = 10;

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public int getMaxConnections() {
        return maxConnections;
    }
}
```

```java
// Access the singleton
AppConfig config = AppConfig.INSTANCE;
System.out.println(config.getDatabaseUrl());    // Output: jdbc:postgresql://localhost:5432/mydb
System.out.println(config.getMaxConnections()); // Output: 10
```

This pattern is thread-safe because enum constants are initialized by the JVM class loader, which handles synchronization automatically. It is also serialization-safe because the Java serialization mechanism guarantees that deserializing an enum constant always returns the same instance — something that requires explicit protection in any other singleton pattern.

## 10. Complete Example

This example builds a simplified e-commerce order tracking system using enums to enforce valid order state transitions.

```java
public enum OrderStatus {
    PENDING("Order placed, awaiting processing") {
        @Override
        public boolean canTransitionTo(OrderStatus next) {
            return next == PROCESSING || next == CANCELLED;
        }
    },
    PROCESSING("Order is being prepared") {
        @Override
        public boolean canTransitionTo(OrderStatus next) {
            return next == SHIPPED || next == CANCELLED;
        }
    },
    SHIPPED("Order has been shipped") {
        @Override
        public boolean canTransitionTo(OrderStatus next) {
            return next == DELIVERED;
        }
    },
    DELIVERED("Order delivered to customer") {
        @Override
        public boolean canTransitionTo(OrderStatus next) {
            return false; // Terminal state
        }
    },
    CANCELLED("Order has been cancelled") {
        @Override
        public boolean canTransitionTo(OrderStatus next) {
            return false; // Terminal state
        }
    };

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFinal() {
        return this == DELIVERED || this == CANCELLED;
    }

    public abstract boolean canTransitionTo(OrderStatus next);
}
```

```java
public class Order {
    private final String orderId;
    private OrderStatus status;

    public Order(String orderId) {
        this.orderId = orderId;
        this.status  = OrderStatus.PENDING;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public boolean advanceTo(OrderStatus next) {
        if (status.canTransitionTo(next)) {
            status = next;
            System.out.println("Order " + orderId + ": " + status + " -> " + next);
            return true;
        }
        System.out.println("Order " + orderId
            + ": Invalid transition from " + status + " to " + next);
        return false;
    }

    @Override
    public String toString() {
        return "Order[" + orderId + ", " + status + "]";
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Create a few orders
        Order order1 = new Order("ORD-001");
        Order order2 = new Order("ORD-002");
        Order order3 = new Order("ORD-003");

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);

        // Advance orders through valid transitions
        order1.advanceTo(OrderStatus.PROCESSING);
        // Output: Order ORD-001: PENDING -> PROCESSING
        order1.advanceTo(OrderStatus.SHIPPED);
        // Output: Order ORD-001: PROCESSING -> SHIPPED
        order1.advanceTo(OrderStatus.DELIVERED);
        // Output: Order ORD-001: SHIPPED -> DELIVERED

        order2.advanceTo(OrderStatus.PROCESSING);
        // Output: Order ORD-002: PENDING -> PROCESSING
        order2.advanceTo(OrderStatus.CANCELLED);
        // Output: Order ORD-002: PROCESSING -> CANCELLED

        // Attempt an invalid transition
        order3.advanceTo(OrderStatus.DELIVERED);
        // Output: Order ORD-003: Invalid transition from PENDING to DELIVERED

        // Print status descriptions using switch expression
        for (Order o : orders) {
            String label = switch (o.getStatus()) {
                case PENDING    -> "Waiting to be processed";
                case PROCESSING -> "In progress";
                case SHIPPED    -> "On the way";
                case DELIVERED  -> "Complete";
                case CANCELLED  -> "Closed";
            };
            System.out.println(o.getOrderId() + " -> " + label);
        }
        // Output:
        // ORD-001 -> Complete
        // ORD-002 -> Closed
        // ORD-003 -> Waiting to be processed

        // Filter orders by status
        System.out.println("\nOrders that have reached a final status:");
        for (Order o : orders) {
            if (o.getStatus().isFinal()) {
                System.out.println("  " + o);
            }
        }
        // Output:
        //   Order[ORD-001, DELIVERED]
        //   Order[ORD-002, CANCELLED]
    }
}
```

## 11. Key Takeaways

- Enums replace error-prone int or String constants with a type-safe, self-documenting alternative
- Comparing enum values with `==` is preferred over `.equals()` — it is null-safe and equally correct
- Every enum inherits `values()`, `ordinal()`, `name()`, and `valueOf()` from `java.lang.Enum`
- Enum constructors are always private; each constant is a singleton instance created at class load time
- Enums can have fields, instance methods, and abstract methods overridden per constant
- Enums can implement interfaces, reinforcing that they are full classes in Java
- Switch expressions with enums are exhaustive — the compiler catches missing cases when new constants are added
- Use `EnumSet` and `EnumMap` instead of `HashSet` and `HashMap` when your keys are enums for better performance
- A single-constant enum is the simplest thread-safe and serialization-safe singleton pattern in Java

---

Reach for an enum whenever a variable in your program should only ever hold one of a fixed, named set of values — doing so moves an entire class of bugs from runtime to compile time.
