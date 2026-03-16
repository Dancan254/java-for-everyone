# toString()

Every Java class inherits a `toString()` method from `Object`. By default it returns something like `Product@4e9ba398` — the class name, an `@` sign, and a hexadecimal memory address. This is almost never useful. Overriding `toString()` to return a meaningful string is one of the most valuable small improvements you can make to any class.

---

## 1. Why toString() Matters Beyond Debugging

`toString()` is automatically called in more places than just explicit `System.out.println` calls.

**String concatenation:** whenever you concatenate an object with a string using `+`, Java calls `toString()` on the object.

```java
Product product = new Product("Widget", 9.99);
System.out.println("Selected: " + product);
// Without override: Selected: Product@4e9ba398
// With override:    Selected: Widget ($9.99)
```

**Logging:** every logging framework calls `toString()` on logged objects.

```java
logger.info("Processing order for product: {}", product);
// Without override: Processing order for product: Product@4e9ba398
// With override:    Processing order for product: Widget ($9.99)
```

**Error messages:** exceptions that mention objects use `toString()`.

```java
throw new IllegalArgumentException("Invalid product: " + product);
```

**Collection printing:** printing a `List` or `Map` calls `toString()` on each element.

```java
List<Product> cart = List.of(p1, p2, p3);
System.out.println(cart);
// Without override: [Product@1a2b3c, Product@4d5e6f, Product@7a8b9c]
// With override:    [Widget ($9.99), Gadget ($24.99), Doohickey ($4.49)]
```

---

## 2. The Default Implementation

`Object.toString()` returns `getClass().getName() + "@" + Integer.toHexString(hashCode())`. This is always unique per object instance, which is why it was the original default, but it carries no domain meaning whatsoever.

```java
public class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // No toString() override.
}
```

```java
Point p = new Point(3, 4);
System.out.println(p); // Output: Point@1b6d3586 (or similar — changes each run)
```

---

## 3. Writing a Useful toString()

A good `toString()` includes enough information to uniquely identify the object in a human-readable way. For most classes this means including all or most of the significant fields.

The `@Override` annotation is required. It tells the compiler you intend to override a method from the parent class. If you misspell the method name or use wrong parameter types, the compiler will catch the error instead of silently creating a new unrelated method.

```java
public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }
}
```

```java
Point p = new Point(3, 4);
System.out.println(p);                  // Output: Point(3, 4)
System.out.println("Position: " + p);  // Output: Position: Point(3, 4)
```

---

## 4. String.format() vs Concatenation vs StringBuilder

All three approaches produce the same result. Choose based on readability.

**Concatenation** — clearest for simple cases.

```java
@Override
public String toString() {
    return "Order{id=" + id + ", customer=" + customer + ", total=" + total + "}";
}
// Output: Order{id=42, customer=Alice, total=149.97}
```

**String.format()** — best when you need precise numeric formatting or column alignment.

```java
@Override
public String toString() {
    return String.format("Order{id=%d, customer=%s, total=$%.2f}", id, customer, total);
}
// Output: Order{id=42, customer=Alice, total=$149.97}
```

**StringBuilder** — most efficient when `toString()` assembles many pieces, particularly in a loop. For simple 2-4 field classes, concatenation or `String.format()` is preferable.

```java
@Override
public String toString() {
    StringBuilder sb = new StringBuilder("Order{");
    sb.append("id=").append(id);
    sb.append(", customer=").append(customer);
    sb.append(", items=[");
    for (int i = 0; i < items.size(); i++) {
        if (i > 0) sb.append(", ");
        sb.append(items.get(i));
    }
    sb.append("], total=$").append(String.format("%.2f", total));
    sb.append("}");
    return sb.toString();
}
```

---

## 5. @Override — Why It Is Required

Always write `@Override` on `toString()`. Without it, a typo like `tostring()` would silently compile as a new method and the default `Object.toString()` would still be called — you would never know your override was ignored.

```java
// Wrong: method is named tostring (lowercase s) — will NOT override Object.toString().
// The default memory-address output is used silently.
public String tostring() {
    return "Point(" + x + ", " + y + ")";
}

// Correct: @Override catches the typo at compile time.
@Override
public String toString() {
    return "Point(" + x + ", " + y + ")";
}
```

---

## 6. Records Automatically Generate toString()

Records (Java 16+) automatically generate a `toString()` that includes all component names and their values. The output format is `ClassName[field1=value1, field2=value2]`.

```java
record Point(int x, int y) {}
record Product(String name, double price) {}

System.out.println(new Point(3, 4));        // Output: Point[x=3, y=4]
System.out.println(new Product("Widget", 9.99)); // Output: Product[name=Widget, price=9.99]
```

You can override `toString()` on a record if you need a different format:

```java
record Product(String name, double price) {
    @Override
    public String toString() {
        return String.format("%s ($%.2f)", name, price);
    }
}

System.out.println(new Product("Widget", 9.99)); // Output: Widget ($9.99)
```

---

## 7. Circular Reference Danger

If class A has a reference to class B, and class B has a reference to class A, and both `toString()` methods call the other's `toString()`, the result is infinite recursion ending in a `StackOverflowError`.

```java
class Person {
    String name;
    Department department;

    @Override
    public String toString() {
        // Calls department.toString(), which calls person.toString() — infinite loop!
        return "Person{name=" + name + ", dept=" + department + "}";
    }
}

class Department {
    String name;
    Person head;

    @Override
    public String toString() {
        // Calls head.toString(), which calls department.toString() — infinite loop!
        return "Department{name=" + name + ", head=" + head + "}";
    }
}
```

**Fix:** break the cycle by printing only the primitive/String fields of the referenced object, not the full `toString()`.

```java
@Override
public String toString() {
    // Print the department's name only, not the full department object.
    return "Person{name=" + name + ", dept=" + (department != null ? department.name : "none") + "}";
}
```

---

## 8. Complete Example: Order + OrderLine

```java
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class OrderLine {
    private final String productName;
    private final int    quantity;
    private final double unitPrice;

    public OrderLine(String productName, int quantity, double unitPrice) {
        this.productName = productName;
        this.quantity    = quantity;
        this.unitPrice   = unitPrice;
    }

    public double lineTotal() {
        return quantity * unitPrice;
    }

    @Override
    public String toString() {
        // Concise format: product name, quantity, unit price, line total.
        return String.format("%s x%d @ $%.2f = $%.2f",
                productName, quantity, unitPrice, lineTotal());
    }
}
```

```java
import java.util.List;
import java.util.StringJoiner;

public class Order {
    private final int         id;
    private final String      customer;
    private final List<OrderLine> lines;

    public Order(int id, String customer) {
        this.id       = id;
        this.customer = customer;
        this.lines    = new ArrayList<>();
    }

    public void addLine(OrderLine line) {
        lines.add(line);
    }

    public double total() {
        return lines.stream().mapToDouble(OrderLine::lineTotal).sum();
    }

    @Override
    public String toString() {
        // StringJoiner assembles the order lines with a separator.
        StringJoiner lineJoiner = new StringJoiner(", ");
        for (OrderLine line : lines) {
            lineJoiner.add(line.toString()); // toString() called explicitly for clarity.
        }

        return String.format("Order{id=%d, customer='%s', lines=[%s], total=$%.2f}",
                id, customer, lineJoiner.toString(), total());
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        Order order = new Order(1001, "Alice");
        order.addLine(new OrderLine("Widget",    3, 9.99));
        order.addLine(new OrderLine("Gadget",    1, 24.99));
        order.addLine(new OrderLine("Doohickey", 5, 4.49));

        // toString() is called implicitly when the object is printed.
        System.out.println(order);
        // Output: Order{id=1001, customer='Alice', lines=[Widget x3 @ $9.99 = $29.97, Gadget x1 @ $24.99 = $24.99, Doohickey x5 @ $4.49 = $22.45], total=$77.41}

        // toString() is called implicitly in string concatenation.
        System.out.println("Processing: " + order);
        // Output: Processing: Order{id=1001, ...}

        // Individual OrderLine objects print their own concise representation.
        for (OrderLine line : order.getLines()) {
            System.out.println("  " + line);
        }
        // Output:
        //   Widget x3 @ $9.99 = $29.97
        //   Gadget x1 @ $24.99 = $24.99
        //   Doohickey x5 @ $4.49 = $22.45
    }
}
```

---

## 9. Key Takeaways

- The default `toString()` returns a memory address — override it in every class that represents domain data
- `@Override` is required; it makes the compiler catch typos that would otherwise silently ignore your override
- Include all fields that are significant for identification; exclude fields that add noise without value
- Use `String.format()` for numeric precision, concatenation for simple cases, and `StringBuilder` when assembling many parts in a loop
- Records automatically generate a correct `toString()` in the format `ClassName[field1=value1, ...]`
- Beware of circular references: if A.toString() calls B.toString() and B.toString() calls A.toString(), the program crashes with `StackOverflowError`

---

`toString()` is the first method any teammate, log analyzer, or debugger uses to understand what an object is. The five minutes it takes to write a good override repays itself every time the object appears in a log file, error message, or test failure output.
