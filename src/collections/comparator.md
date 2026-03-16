# Comparator and Comparable

Sorting is one of the most common operations in any application. Java provides two interfaces for defining order: `Comparable` for an object's natural order and `Comparator` for any alternative ordering you need on the fly. Understanding both — and knowing when to use each — is essential for writing clean, flexible code.

---

## 1. The Two Interfaces at a Glance

```
Think of it like this:
  Comparable is like a person's name — it defines the one natural way to order the object.
  Comparator is like a sorting rule you hand to a filing clerk — it defines an ordering
  for a specific situation without changing the object itself.
  You can have one natural order (Comparable) and as many Comparators as you need.
```

| | `Comparable<T>` | `Comparator<T>` |
|---|---|---|
| Lives in | The class being sorted | A separate object or lambda |
| Method | `compareTo(T other)` | `compare(T a, T b)` |
| Number of orderings | One (the natural order) | Unlimited |
| Used by | `Collections.sort(list)`, `TreeSet`, `TreeMap` (no arg) | `Collections.sort(list, comparator)`, `list.sort(comparator)` |

---

## 2. Comparable — Natural Ordering

Implement `Comparable<T>` when the class has one obvious, canonical ordering that applies everywhere.

`compareTo` must return:
- A **negative** integer if `this` is less than `other`.
- **Zero** if `this` equals `other`.
- A **positive** integer if `this` is greater than `other`.

```java
public class Student implements Comparable<Student> {
    private final String name;
    private final double gpa;
    private final int    graduationYear;

    public Student(String name, double gpa, int graduationYear) {
        this.name           = name;
        this.gpa            = gpa;
        this.graduationYear = graduationYear;
    }

    // Natural order: alphabetical by name.
    @Override
    public int compareTo(Student other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name + " (GPA: " + gpa + ", Year: " + graduationYear + ")";
    }

    public String getName()        { return name; }
    public double getGpa()         { return gpa; }
    public int    getGraduationYear() { return graduationYear; }
}
```

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

List<Student> students = new ArrayList<>(List.of(
    new Student("Charlie", 3.5, 2025),
    new Student("Alice",   3.8, 2024),
    new Student("Bob",     3.2, 2026)
));

// Collections.sort uses compareTo — the natural order defined by Comparable.
Collections.sort(students);

for (Student s : students) {
    System.out.println(s);
}
// Output:
// Alice (GPA: 3.8, Year: 2024)
// Bob (GPA: 3.2, Year: 2026)
// Charlie (GPA: 3.5, Year: 2025)
```

### Integer.compare() and Double.compare()

Never compute the difference of two numbers to implement `compareTo`. For integers, the subtraction can overflow if the values are extreme. Use the static compare methods instead.

```java
// Wrong: integer subtraction overflows for large negative values.
// return this.graduationYear - other.graduationYear;

// Correct: Integer.compare handles all values safely.
// return Integer.compare(this.graduationYear, other.graduationYear);

// Correct: Double.compare handles NaN and -0.0 correctly.
// return Double.compare(this.gpa, other.gpa);
```

---

## 3. Comparator — Custom Ordering

`Comparator<T>` defines an ordering without modifying the class. Use it when:
- The class does not implement `Comparable`.
- You need an ordering different from the natural order.
- You need multiple different orderings.

### Classic anonymous-class style (pre-Java 8)

```java
import java.util.Comparator;

Comparator<Student> byGpa = new Comparator<Student>() {
    @Override
    public int compare(Student a, Student b) {
        return Double.compare(b.getGpa(), a.getGpa()); // Descending: higher GPA first.
    }
};

students.sort(byGpa);
```

### Lambda style (Java 8+)

A `Comparator` is a functional interface — it has one abstract method (`compare`). Use a lambda.

```java
// Ascending by GPA.
students.sort((a, b) -> Double.compare(a.getGpa(), b.getGpa()));

// Descending by GPA (swap a and b).
students.sort((a, b) -> Double.compare(b.getGpa(), a.getGpa()));

// Ascending by graduation year.
students.sort((a, b) -> Integer.compare(a.getGraduationYear(), b.getGraduationYear()));
```

---

## 4. Comparator Factory Methods (Java 8+)

`Comparator` provides static and default methods that build comparators cleanly without explicit lambda logic.

### Comparator.comparing()

`Comparator.comparing(keyExtractor)` takes a method reference that extracts the sort key. It handles null-safety and consistent comparison automatically.

```java
import java.util.Comparator;

// Sort by name using a method reference — cleaner than a lambda.
Comparator<Student> byName = Comparator.comparing(Student::getName);
students.sort(byName);

// Sort by GPA in ascending order.
Comparator<Student> byGpa = Comparator.comparingDouble(Student::getGpa);
students.sort(byGpa);

// Sort by graduation year.
Comparator<Student> byYear = Comparator.comparingInt(Student::getGraduationYear);
students.sort(byYear);
```

### reversed()

`.reversed()` flips an existing comparator.

```java
// Descending by GPA (highest first).
Comparator<Student> byGpaDesc = Comparator.comparingDouble(Student::getGpa).reversed();
students.sort(byGpaDesc);

for (Student s : students) {
    System.out.println(s);
}
// Output (example, starting with highest GPA):
// Alice (GPA: 3.8, Year: 2024)
// Charlie (GPA: 3.5, Year: 2025)
// Bob (GPA: 3.2, Year: 2026)
```

### thenComparing() — multi-level sort

Chain `.thenComparing()` to sort by a secondary key when the primary keys are equal.

```java
// Primary: ascending graduation year.
// Secondary (tie-break): descending GPA.
Comparator<Student> byYearThenGpa = Comparator
        .comparingInt(Student::getGraduationYear)
        .thenComparing(Comparator.comparingDouble(Student::getGpa).reversed());

students.sort(byYearThenGpa);

for (Student s : students) {
    System.out.println(s);
}
// Output (sorted by year, then highest GPA first within each year):
// Alice (GPA: 3.8, Year: 2024)
// Charlie (GPA: 3.5, Year: 2025)
// Bob (GPA: 3.2, Year: 2026)
```

### Comparator.naturalOrder() and Comparator.reverseOrder()

```java
// naturalOrder() uses the class's compareTo method.
List<String> names = new ArrayList<>(List.of("Charlie", "Alice", "Bob"));
names.sort(Comparator.naturalOrder());
System.out.println(names); // Output: [Alice, Bob, Charlie]

// reverseOrder() is the reverse of natural order.
names.sort(Comparator.reverseOrder());
System.out.println(names); // Output: [Charlie, Bob, Alice]
```

### nullsFirst() and nullsLast()

```java
List<String> withNulls = new ArrayList<>(List.of("Charlie", null, "Alice", null, "Bob"));

withNulls.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
System.out.println(withNulls); // Output: [null, null, Alice, Bob, Charlie]

withNulls.sort(Comparator.nullsLast(Comparator.naturalOrder()));
System.out.println(withNulls); // Output: [Alice, Bob, Charlie, null, null]
```

---

## 5. Sorted Collections: TreeSet and TreeMap

`TreeSet` and `TreeMap` maintain elements in sorted order. They use `Comparable` by default, or a `Comparator` supplied at construction.

```java
import java.util.TreeSet;
import java.util.TreeMap;

// TreeSet — uses Student's natural order (alphabetical by name).
TreeSet<Student> sortedStudents = new TreeSet<>(students);
for (Student s : sortedStudents) {
    System.out.println(s.getName()); // Output: Alice, Bob, Charlie (alphabetical)
}

// TreeSet with a custom Comparator — sorted by GPA descending.
TreeSet<Student> byGpa = new TreeSet<>(
        Comparator.comparingDouble(Student::getGpa).reversed()
);
byGpa.addAll(students);
for (Student s : byGpa) {
    System.out.println(s.getName() + ": " + s.getGpa());
}
// Output:
// Alice: 3.8
// Charlie: 3.5
// Bob: 3.2

// TreeMap — keys sorted by natural order.
TreeMap<String, Student> byName = new TreeMap<>();
for (Student s : students) {
    byName.put(s.getName(), s);
}
System.out.println(byName.firstKey()); // Output: Alice
System.out.println(byName.lastKey());  // Output: Charlie
```

---

## 6. Using Comparator with Streams

`sorted()` in the Streams API accepts a `Comparator`.

```java
import java.util.List;
import java.util.stream.Collectors;

// All students with GPA >= 3.4, sorted by GPA descending.
List<Student> honour = students.stream()
        .filter(s -> s.getGpa() >= 3.4)
        .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
        .collect(Collectors.toList());

honour.forEach(System.out::println);
// Output:
// Alice (GPA: 3.8, Year: 2024)
// Charlie (GPA: 3.5, Year: 2025)

// Min and max using a Comparator.
students.stream()
        .max(Comparator.comparingDouble(Student::getGpa))
        .ifPresent(s -> System.out.println("Top student: " + s.getName())); // Output: Top student: Alice

students.stream()
        .min(Comparator.comparingInt(Student::getGraduationYear))
        .ifPresent(s -> System.out.println("Earliest graduate: " + s.getName())); // Output: Earliest graduate: Alice
```

---

## 7. Complete Example: Product Catalogue Sorting

```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductSortDemo {

    record Product(String name, String category, double price, int stock) {}

    public static void main(String[] args) {

        List<Product> catalogue = new ArrayList<>(List.of(
            new Product("Widget",    "Hardware", 9.99,  150),
            new Product("Gadget",    "Electronics", 24.99, 80),
            new Product("Doohickey", "Hardware", 4.49,  200),
            new Product("Gizmo",     "Electronics", 14.99, 60),
            new Product("Thingamajig", "Hardware", 19.99, 45)
        ));

        // Sort by price ascending.
        catalogue.sort(Comparator.comparingDouble(Product::price));
        System.out.println("-- By price (ascending) --");
        catalogue.forEach(p -> System.out.printf("%-15s $%.2f%n", p.name(), p.price()));
        // Output:
        // Doohickey       $4.49
        // Widget          $9.99
        // Gizmo           $14.99
        // Thingamajig     $19.99
        // Gadget          $24.99

        // Sort by category, then by price descending within each category.
        Comparator<Product> byCategoryThenPriceDesc = Comparator
                .comparing(Product::category)
                .thenComparing(Comparator.comparingDouble(Product::price).reversed());

        catalogue.sort(byCategoryThenPriceDesc);
        System.out.println("\n-- By category, then price desc --");
        catalogue.forEach(p ->
                System.out.printf("%-12s %-15s $%.2f%n", p.category(), p.name(), p.price()));
        // Output:
        // Electronics  Gadget          $24.99
        // Electronics  Gizmo           $14.99
        // Hardware     Thingamajig     $19.99
        // Hardware     Widget          $9.99
        // Hardware     Doohickey       $4.49
    }
}
```

---

## 8. Common Mistakes

### Using subtraction for compareTo

Subtracting two integers to implement `compareTo` is a common but dangerous shortcut. It overflows for large values and produces incorrect ordering.

```java
// Wrong: overflows when a is Integer.MIN_VALUE and b is positive.
public int compareTo(IntWrapper other) {
    return this.value - other.value;
}

// Correct.
public int compareTo(IntWrapper other) {
    return Integer.compare(this.value, other.value);
}
```

### Inconsistent compareTo and equals

If `a.compareTo(b) == 0`, it should be true that `a.equals(b)`. `TreeSet` and `TreeMap` use `compareTo` for membership, not `equals`. An inconsistency means an element present by `equals` may not be found in a `TreeSet`.

```java
// If compareTo is by GPA but equals is by name, a TreeSet keyed on GPA
// will treat two students with the same GPA as duplicates,
// even if their names differ. This is almost always a bug.
```

### Forgetting to chain thenComparing for stable multi-key sorts

If two elements are equal on the primary key, the sort is unstable without a `thenComparing` chain. `Collections.sort` and `List.sort` are guaranteed stable (equal elements preserve their original relative order), but adding explicit tie-breaking makes intent clear.

```java
// Without thenComparing, ties in category are ordered arbitrarily.
catalogue.sort(Comparator.comparing(Product::category));

// With thenComparing, ties in category are broken by name.
catalogue.sort(Comparator.comparing(Product::category)
                         .thenComparing(Product::name));
```

---

## 9. Key Takeaways

- Implement `Comparable<T>` for the natural, canonical ordering of a class; use `compareTo` to define it
- Always use `Integer.compare()`, `Double.compare()`, or delegate to `String.compareTo()` — never compute differences
- Use `Comparator` when you need an ordering that is separate from the class, or when you need multiple orderings
- `Comparator.comparing(KeyExtractor)` with method references is the cleanest way to build comparators
- Chain `.thenComparing()` for multi-level sorts and call `.reversed()` to flip any comparator
- `TreeSet` and `TreeMap` require either `Comparable` elements or a `Comparator` at construction
- `Comparator.nullsFirst()` and `nullsLast()` handle null elements without manual null checks
- The Streams `sorted(Comparator)`, `min(Comparator)`, and `max(Comparator)` methods all accept the same comparators

---

Sorting is one of those operations that looks trivial and turns complex the moment you need multi-key or null-safe ordering. The `Comparator` builder methods — `comparing`, `thenComparing`, `reversed`, `nullsFirst` — cover virtually every sorting requirement you will encounter in practice.
