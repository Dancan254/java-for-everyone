# equals() and hashCode()

Every Java class inherits two methods from `Object`: `equals()` and `hashCode()`. By default, `equals()` checks whether two variables point to the same object in memory, and `hashCode()` returns a number derived from the object's memory address. For many classes — particularly value-holding data classes — this default behaviour is wrong. This guide explains how to override them correctly and what happens when you do not.

---

## 1. The Problem: Default equals() Compares Identity, Not Value

When you create two `Point` objects with the same coordinates, the default `equals()` returns `false` because they are two different objects, even though they represent the same logical value.

```java
public class Point {
    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
```

```java
Point p1 = new Point(3, 4);
Point p2 = new Point(3, 4);

System.out.println(p1 == p2);       // Output: false — different objects in memory
System.out.println(p1.equals(p2));  // Output: false — same result as == by default
```

This is the correct behaviour for some classes (like database connections or locks), but wrong for value objects like coordinates, money amounts, or user records, where equality should be based on field values.

---

## 2. Overriding equals() — the Five Properties

A correct `equals()` implementation must satisfy five properties defined in the Java specification. Violating any of them produces subtle, hard-to-diagnose bugs.

| Property | Meaning |
|---|---|
| Reflexive | `a.equals(a)` must be `true` |
| Symmetric | If `a.equals(b)` is `true`, then `b.equals(a)` must also be `true` |
| Transitive | If `a.equals(b)` and `b.equals(c)`, then `a.equals(c)` must be `true` |
| Consistent | Multiple calls to `a.equals(b)` return the same result (assuming no mutation) |
| Null-safe | `a.equals(null)` must return `false`, never throw |

The standard idiom for overriding `equals()`:

```java
import java.util.Objects;

public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        // 1. Reflexive: an object is always equal to itself.
        if (this == obj) return true;

        // 2. Null-safe and type check: null and wrong types are never equal.
        if (!(obj instanceof Point other)) return false;

        // 3. Compare all fields that define value equality.
        return this.x == other.x && this.y == other.y;
    }
}
```

```java
Point p1 = new Point(3, 4);
Point p2 = new Point(3, 4);
Point p3 = new Point(0, 0);

System.out.println(p1.equals(p2));    // Output: true
System.out.println(p1.equals(p3));    // Output: false
System.out.println(p1.equals(null));  // Output: false (null-safe)
System.out.println(p1.equals("xy"));  // Output: false (type-safe)
```

---

## 3. The equals/hashCode Contract

The Java specification states a strict contract:

> **If `a.equals(b)` is `true`, then `a.hashCode()` must equal `b.hashCode()`.**

The converse is not required: two objects can have the same hash code without being equal (this is called a hash collision and is expected). But the forward direction is absolute.

This contract is what makes `HashMap` and `HashSet` work. When you put an object into a `HashMap`, Java uses its hash code to find the right bucket, then uses `equals()` to find the exact key within that bucket. If equal objects have different hash codes, they will end up in different buckets and the map will never find one when you look up the other.

---

## 4. Overriding hashCode() — the Formula Pattern

The simplest correct approach is `Objects.hash(field1, field2, ...)`, which combines the hash codes of the listed fields in a well-distributed way.

```java
import java.util.Objects;

public class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Point other)) return false;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        // Objects.hash() calls hashCode() on each argument and combines the results.
        // Use the same fields as equals() — no more, no less.
        return Objects.hash(x, y);
    }
}
```

```java
Point p1 = new Point(3, 4);
Point p2 = new Point(3, 4);

System.out.println(p1.equals(p2));              // Output: true
System.out.println(p1.hashCode() == p2.hashCode()); // Output: true — contract satisfied
```

The rule is simple: **use the same set of fields in both `equals()` and `hashCode()`.**

---

## 5. Why Violating the Contract Breaks HashMap and HashSet

Here is a concrete demonstration of what goes wrong when `equals()` is overridden but `hashCode()` is not.

```java
// A Point class with equals() overridden but hashCode() NOT overridden.
// hashCode() still returns a memory-address-based value — different for each object.
public class BrokenPoint {
    int x;
    int y;

    public BrokenPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BrokenPoint other)) return false;
        return this.x == other.x && this.y == other.y;
    }
    // hashCode() NOT overridden — still returns identity-based hash code.
}
```

```java
import java.util.HashSet;
import java.util.HashMap;

BrokenPoint a = new BrokenPoint(1, 2);
BrokenPoint b = new BrokenPoint(1, 2); // "equal" to a according to equals()

// HashSet stores objects by their hash code bucket.
HashSet<BrokenPoint> set = new HashSet<>();
set.add(a);

// a.equals(b) is true, so we expect the set to contain b.
System.out.println(set.contains(b)); // Output: false — contract violation!
// a and b have different hash codes, so the set looks in the wrong bucket.

// Same problem with HashMap.
HashMap<BrokenPoint, String> map = new HashMap<>();
map.put(a, "value");
System.out.println(map.get(b)); // Output: null — can never find the key!
```

The `HashSet` reports that it does not contain `b`, even though `b` is "equal" to the stored element `a`. The contract violation made the collection unreliable. This is one of the most common sources of subtle bugs in Java code.

---

## 6. Mutable Fields in equals/hashCode — the Danger

If a field used in `equals()` or `hashCode()` is mutable and you change it after the object has been added to a hash-based collection, the object ends up in the wrong bucket and the collection can never find it again.

```java
import java.util.HashSet;
import java.util.Objects;

public class MutablePoint {
    int x;
    int y;

    // Constructor, equals, hashCode using both fields...
    @Override public boolean equals(Object o) {
        if (!(o instanceof MutablePoint p)) return false;
        return x == p.x && y == p.y;
    }
    @Override public int hashCode() { return Objects.hash(x, y); }
}
```

```java
MutablePoint p = new MutablePoint();
p.x = 1; p.y = 2;

HashSet<MutablePoint> set = new HashSet<>();
set.add(p);

System.out.println(set.contains(p)); // Output: true — found in the correct bucket.

// Mutate a field that is part of the hash code.
p.x = 99;

// The set still holds p, but now p.hashCode() returns a different value.
// The set looks in the wrong bucket and cannot find the object it already holds.
System.out.println(set.contains(p)); // Output: false — the object is lost!
```

The standard advice: prefer immutable fields in classes that override `equals()` and `hashCode()`. If you must use mutable classes as map keys or set members, never mutate them while they are in the collection.

---

## 7. Records Automatically Generate Correct equals/hashCode

Java records (Java 16+) automatically generate `equals()`, `hashCode()`, and `toString()` based on all record components. The generated implementations are correct and satisfy the contract.

```java
record Point(int x, int y) {}
```

```java
Point p1 = new Point(3, 4);
Point p2 = new Point(3, 4);

System.out.println(p1.equals(p2));                  // Output: true
System.out.println(p1.hashCode() == p2.hashCode()); // Output: true
System.out.println(p1);                             // Output: Point[x=3, y=4]
```

For data classes with no custom logic, records are the preferred way to get correct `equals()` and `hashCode()` without writing any code.

---

## 8. IDE Generation vs Manual vs Objects.hash()

| Approach | Recommendation |
|---|---|
| IDE generation (`Generate > equals() and hashCode()`) | Good starting point; generates correct boilerplate |
| Manual implementation using `instanceof` pattern + `Objects.hash()` | Recommended for custom classes that cannot be records |
| `java.util.Objects.hash(fields...)` | Always use this for hashCode — do not implement the bit-shifting yourself |
| Records | Best for simple data classes — zero boilerplate, automatically correct |

---

## 9. Complete Example: Product in a HashSet and HashMap

```java
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Product {
    private final String sku;
    private final String name;
    private double price; // Mutable — we deliberately do NOT include this in equals/hashCode.

    public Product(String sku, String name, double price) {
        this.sku   = sku;
        this.name  = name;
        this.price = price;
    }

    // Identity is based on SKU and name only.
    // Price is excluded because products are "the same product" regardless of price changes.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Product other)) return false;
        return Objects.equals(this.sku, other.sku)
            && Objects.equals(this.name, other.name);
    }

    @Override
    public int hashCode() {
        // Use the same fields as equals(). Objects.equals handles null safely.
        return Objects.hash(sku, name);
    }

    public void setPrice(double price) { this.price = price; }
    public double getPrice()           { return price; }
    public String getSku()             { return sku; }

    @Override
    public String toString() {
        return "Product{sku='" + sku + "', name='" + name + "', price=" + price + "}";
    }
}
```

```java
import java.util.HashMap;
import java.util.HashSet;

public class ProductDemo {
    public static void main(String[] args) {

        Product p1 = new Product("SKU-001", "Widget", 9.99);
        Product p2 = new Product("SKU-001", "Widget", 9.99); // Logically the same product.
        Product p3 = new Product("SKU-002", "Gadget", 24.99);

        // HashSet correctly deduplicates equal products.
        HashSet<Product> catalogue = new HashSet<>();
        catalogue.add(p1);
        catalogue.add(p2); // This is a duplicate — p1.equals(p2) is true.
        catalogue.add(p3);

        System.out.println("Catalogue size: " + catalogue.size()); // Output: 2 (not 3)
        System.out.println("Contains p2: " + catalogue.contains(p2)); // Output: true

        // HashMap correctly stores and retrieves by product key.
        HashMap<Product, Integer> stock = new HashMap<>();
        stock.put(p1, 100);
        stock.put(p3, 50);

        // Retrieve using p2 (a different object with the same SKU/name).
        // This works because p1.equals(p2) and p1.hashCode() == p2.hashCode().
        System.out.println("Stock for p2 (= p1): " + stock.get(p2)); // Output: 100

        // Price changes do not affect map lookup because price is not in equals/hashCode.
        p1.setPrice(12.99);
        System.out.println("Stock after price change: " + stock.get(p1)); // Output: 100
    }
}
```

---

## 10. Common Mistakes

### Using == inside a custom equals()
Using `==` to compare object fields compares references, not values. Use `Objects.equals(a, b)` for nullable fields (it handles null safely) or `.equals()` for non-nullable fields.

```java
// Wrong: compares String references — may fail even for equal strings.
return this.name == other.name;

// Correct: compares String contents, null-safe.
return Objects.equals(this.name, other.name);
```

### Including mutable fields in hashCode
If a field is included in `hashCode()` and its value changes, the object's hash code changes. Objects in hash-based collections with changed hash codes become permanently unreachable.

### Overriding equals() without overriding hashCode()
The most common mistake in this area. `equals()` and `hashCode()` must always be overridden together.

### Using a subset of fields in hashCode but all fields in equals
If `hashCode()` considers fewer fields than `equals()`, two objects that `equals()` considers different could still share a hash code. This is allowed (collisions are expected) but hurts performance. More importantly, the reverse (more fields in `hashCode()` than `equals()`) violates the contract.

---

## 11. Key Takeaways

- The default `equals()` compares identity (same object); override it for classes where value equality is meaningful
- A correct `equals()` must be reflexive, symmetric, transitive, consistent, and null-safe
- **If `a.equals(b)` then `a.hashCode() == b.hashCode()` — this contract is absolute and must not be violated**
- Always override `hashCode()` whenever you override `equals()` — use `Objects.hash(fields...)` for the implementation
- Use the same set of fields in both `equals()` and `hashCode()`
- Mutable fields in `equals()`/`hashCode()` are dangerous when objects are used as map keys or set members
- Records automatically provide correct `equals()`, `hashCode()`, and `toString()` — prefer records for simple data classes

---

Correct `equals()` and `hashCode()` are the foundation of reliable `HashMap` and `HashSet` usage, which are the two most commonly used data structures in enterprise Java applications. Getting them right once is far easier than debugging the subtle failures that occur when they are wrong.
