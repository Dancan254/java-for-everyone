# Streams

A Stream is a pipeline of operations on a sequence of elements that allows you to express complex data processing queries in a declarative, readable style. Unlike a collection, a stream does not store data — it processes data from a source on demand. You already know lambdas and collections; streams are where those two concepts combine into their most powerful form.

```
Think of it like this:
  A collection is a bucket of water — it holds the water.
  A stream is a pipe — water flows through it and you apply
  filters, transformers, and collectors along the way.
  The pipe itself holds nothing. It only defines what happens
  to each drop of water as it passes through.
```

## 1. What Is a Stream?

A `Stream<T>` represents a sequence of elements that supports sequential and parallel aggregate operations. Three properties define how streams behave, and understanding them is essential before writing a single line of stream code.

**Laziness** — intermediate operations (filter, map, sorted, etc.) do not execute when you call them. They build up a description of the pipeline. Execution only begins when a terminal operation is called. This means if you chain five operations but the terminal operation only needs the first two elements, Java may never evaluate the remaining three operations on most elements.

**Single-use** — once a terminal operation has been called on a stream, the stream is consumed and cannot be reused. Attempting to reuse it throws `IllegalStateException`.

**Non-mutating** — a stream never modifies its source collection. You always get a new result; the original list remains unchanged.

```java
List<String> names = List.of("Alice", "Bob", "Carol");

// This creates a stream — it does NOT modify names
Stream<String> stream = names.stream();

// The stream is consumed here. names is still ["Alice", "Bob", "Carol"].
long count = stream.filter(n -> n.startsWith("A")).count();

System.out.println(count); // Output: 1
System.out.println(names); // Output: [Alice, Bob, Carol]
```

Collection vs. Stream at a glance:

| Question | Collection | Stream |
|---|---|---|
| Does it store elements? | Yes | No |
| Can you iterate it more than once? | Yes | No |
| Does it evaluate lazily? | No | Yes |
| Does it modify its source? | Possible | Never |

## 2. Creating Streams

There are several ways to obtain a stream depending on where your data lives.

**From a collection** — every `Collection` has a `.stream()` method.

```java
List<String> cities = List.of("London", "Tokyo", "Cairo");
Stream<String> cityStream = cities.stream();
```

**From an array** — use `Arrays.stream()`.

```java
int[] numbers = {1, 2, 3, 4, 5};
IntStream numberStream = Arrays.stream(numbers);

String[] words = {"hello", "world"};
Stream<String> wordStream = Arrays.stream(words);
```

**From explicit values** — use `Stream.of()`.

```java
Stream<String> stream = Stream.of("red", "green", "blue");
```

**Empty stream** — useful as a safe default return value.

```java
Stream<String> empty = Stream.empty();
```

**Infinite streams** — streams that generate elements on demand. Always pair them with `limit()` or they will run forever.

```java
// Produces 0, 1, 2, 3, 4 ...
Stream<Integer> naturals = Stream.iterate(0, n -> n + 1);

// Produces random doubles endlessly
Stream<Double> randoms = Stream.generate(Math::random);

// Safe — take only the first 10
List<Integer> firstTen = Stream.iterate(0, n -> n + 1)
        .limit(10)
        .collect(Collectors.toList());
// Output: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
```

**Primitive streams** — `IntStream`, `LongStream`, and `DoubleStream` avoid the boxing overhead of `Stream<Integer>`. Covered in depth in section 7.

```java
IntStream oneToTen = IntStream.range(1, 11);       // 1 to 10 (exclusive end)
IntStream oneToTenInclusive = IntStream.rangeClosed(1, 10); // 1 to 10 (inclusive end)
```

## 3. Intermediate Operations

Intermediate operations return a new `Stream` and are lazy — they do not execute until a terminal operation is called. Each operation describes one step in the pipeline.

### filter(Predicate)

Keeps only the elements that match the condition.

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> evens = numbers.stream()
        .filter(n -> n % 2 == 0)
        .collect(Collectors.toList());

System.out.println(evens);
// Output: [2, 4, 6, 8, 10]
```

### map(Function)

Transforms each element into something else. The output stream can be a different type.

```java
List<String> names = List.of("alice", "bob", "carol");

List<String> upper = names.stream()
        .map(String::toUpperCase)
        .collect(Collectors.toList());

System.out.println(upper);
// Output: [ALICE, BOB, CAROL]
```

### flatMap(Function)

Used when each element maps to a collection or stream of elements. `flatMap` flattens all those inner streams into a single stream.

```java
List<String> sentences = List.of("the quick brown fox", "jumps over the lazy dog");

List<String> words = sentences.stream()
        .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
        .collect(Collectors.toList());

System.out.println(words);
// Output: [the, quick, brown, fox, jumps, over, the, lazy, dog]
```

Without `flatMap`, you would get a `Stream<String[]>` — a stream of arrays. `flatMap` collapses the nesting.

### distinct()

Removes duplicate elements using `equals()`.

```java
List<Integer> withDupes = List.of(3, 1, 4, 1, 5, 9, 2, 6, 5, 3);

List<Integer> unique = withDupes.stream()
        .distinct()
        .collect(Collectors.toList());

System.out.println(unique);
// Output: [3, 1, 4, 5, 9, 2, 6]
```

### sorted() and sorted(Comparator)

`sorted()` uses the element's natural ordering. `sorted(Comparator)` accepts a custom ordering.

```java
List<String> names = List.of("Charlie", "Alice", "Bob");

// Natural order (alphabetical)
List<String> alphabetical = names.stream()
        .sorted()
        .collect(Collectors.toList());
System.out.println(alphabetical);
// Output: [Alice, Bob, Charlie]

// Custom order — longest name first
List<String> byLength = names.stream()
        .sorted(Comparator.comparingInt(String::length).reversed())
        .collect(Collectors.toList());
System.out.println(byLength);
// Output: [Charlie, Alice, Bob]
```

### peek(Consumer)

Performs an action on each element without modifying the stream. Its primary purpose is debugging — it lets you observe elements at a specific point in the pipeline.

```java
List<Integer> result = List.of(1, 2, 3, 4, 5).stream()
        .peek(n -> System.out.println("Before filter: " + n))
        .filter(n -> n % 2 == 0)
        .peek(n -> System.out.println("After filter: " + n))
        .collect(Collectors.toList());

// Output:
// Before filter: 1
// Before filter: 2
// After filter: 2
// Before filter: 3
// Before filter: 4
// After filter: 4
// Before filter: 5
```

### limit(n) and skip(n)

`limit(n)` keeps only the first `n` elements. `skip(n)` discards the first `n` elements and keeps the rest.

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> page = numbers.stream()
        .skip(3)    // skip the first 3
        .limit(4)   // keep only the next 4
        .collect(Collectors.toList());

System.out.println(page);
// Output: [4, 5, 6, 7]
```

### Multi-Step Pipeline Example

Intermediate operations can be chained freely. Each step receives the output of the previous step.

```java
List<String> words = List.of(
        "stream", "lambda", "map", "filter", "stream",
        "collect", "map", "optional", "reduce"
);

List<String> result = words.stream()
        .filter(w -> w.length() > 3)          // keep words longer than 3 chars
        .map(String::toUpperCase)              // uppercase them
        .distinct()                            // remove duplicates
        .sorted()                              // sort alphabetically
        .limit(4)                              // take the first 4
        .collect(Collectors.toList());

System.out.println(result);
// Output: [COLLECT, FILTER, LAMBDA, OPTIONAL]
```

## 4. Terminal Operations

Terminal operations trigger the entire pipeline and produce a result. After a terminal operation completes, the stream is consumed.

### forEach(Consumer)

Iterates over each element and performs an action. Returns `void`.

```java
List<String> names = List.of("Alice", "Bob", "Carol");
names.stream().forEach(System.out::println);
// Output:
// Alice
// Bob
// Carol
```

### collect(Collector)

Gathers the stream elements into a container. This is the most commonly used terminal operation. Collectors are covered in depth in section 5.

```java
List<Integer> evens = List.of(1, 2, 3, 4, 5, 6).stream()
        .filter(n -> n % 2 == 0)
        .collect(Collectors.toList());

System.out.println(evens);
// Output: [2, 4, 6]
```

### count()

Returns the number of elements in the stream as a `long`.

```java
long count = List.of("a", "bb", "ccc", "dddd").stream()
        .filter(s -> s.length() >= 2)
        .count();

System.out.println(count);
// Output: 3
```

### reduce(identity, BinaryOperator)

Folds all elements into a single value by repeatedly applying a combining function.

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5);

// Sum: starts at 0, adds each element
int sum = numbers.stream()
        .reduce(0, Integer::sum);
System.out.println(sum);
// Output: 15

// Product: starts at 1, multiplies each element
int product = numbers.stream()
        .reduce(1, (a, b) -> a * b);
System.out.println(product);
// Output: 120

// Concatenation
String joined = List.of("Java", "is", "expressive").stream()
        .reduce("", (a, b) -> a.isEmpty() ? b : a + " " + b);
System.out.println(joined);
// Output: Java is expressive
```

### findFirst() and findAny()

Both return an `Optional<T>`. `findFirst()` returns the first element in encounter order. `findAny()` may return any element and is more efficient in parallel streams.

```java
Optional<String> first = List.of("apple", "banana", "cherry").stream()
        .filter(s -> s.startsWith("b"))
        .findFirst();

first.ifPresent(System.out::println);
// Output: banana
```

### anyMatch, allMatch, noneMatch

Short-circuit predicates that return `boolean`. They stop processing as soon as the answer is known.

```java
List<Integer> numbers = List.of(2, 4, 6, 7, 8);

boolean anyOdd   = numbers.stream().anyMatch(n -> n % 2 != 0);
boolean allEven  = numbers.stream().allMatch(n -> n % 2 == 0);
boolean noneNeg  = numbers.stream().noneMatch(n -> n < 0);

System.out.println(anyOdd);  // Output: true
System.out.println(allEven); // Output: false
System.out.println(noneNeg); // Output: true
```

### min(Comparator) and max(Comparator)

Return an `Optional<T>` containing the smallest or largest element according to the given comparator.

```java
List<String> words = List.of("apple", "fig", "banana", "kiwi");

Optional<String> shortest = words.stream()
        .min(Comparator.comparingInt(String::length));

Optional<String> longest = words.stream()
        .max(Comparator.comparingInt(String::length));

shortest.ifPresent(s -> System.out.println("Shortest: " + s));
longest.ifPresent(s -> System.out.println("Longest: " + s));
// Output:
// Shortest: fig
// Longest: banana
```

### toList() (Java 16+)

A shorthand for `collect(Collectors.toList())`. Returns an unmodifiable list.

```java
List<String> result = Stream.of("x", "y", "z")
        .map(String::toUpperCase)
        .toList();

System.out.println(result);
// Output: [X, Y, Z]
```

## 5. Collectors

The `Collectors` class provides factory methods for the most common collection strategies. You pass a `Collector` to the `collect()` terminal operation.

### Basic Collectors

```java
List<String> names = List.of("Alice", "Bob", "Alice", "Carol", "Bob");

// Collect to a mutable list
List<String> asList = names.stream().collect(Collectors.toList());

// Collect to a set (removes duplicates)
Set<String> asSet = names.stream().collect(Collectors.toSet());
System.out.println(asSet);
// Output: [Alice, Bob, Carol]  (order not guaranteed)

// Collect to an unmodifiable list
List<String> immutable = names.stream().collect(Collectors.toUnmodifiableList());
```

### Collectors.toMap

```java
List<String> words = List.of("apple", "fig", "banana");

Map<String, Integer> wordLengths = words.stream()
        .collect(Collectors.toMap(
                word -> word,          // key: the word itself
                String::length         // value: its length
        ));

System.out.println(wordLengths);
// Output: {apple=5, fig=3, banana=6}
```

### Collectors.joining

Concatenates stream elements into a single `String`.

```java
List<String> names = List.of("Alice", "Bob", "Carol");

String csv = names.stream()
        .collect(Collectors.joining(", "));
System.out.println(csv);
// Output: Alice, Bob, Carol

String formatted = names.stream()
        .collect(Collectors.joining(", ", "[", "]"));
System.out.println(formatted);
// Output: [Alice, Bob, Carol]
```

### Collectors.counting, summingInt, averagingInt

```java
List<String> words = List.of("a", "bb", "ccc", "dd", "e");

long count = words.stream().collect(Collectors.counting());
int totalLength = words.stream().collect(Collectors.summingInt(String::length));
double avgLength = words.stream().collect(Collectors.averagingInt(String::length));

System.out.println(count);       // Output: 5
System.out.println(totalLength); // Output: 9
System.out.println(avgLength);   // Output: 1.8
```

### Collectors.groupingBy

Groups elements by a classifier function and returns a `Map<K, List<V>>`. This is one of the most powerful collectors.

```java
record Employee(String name, String department, double salary) {}

List<Employee> employees = List.of(
        new Employee("Alice",   "Engineering", 95000),
        new Employee("Bob",     "Engineering", 88000),
        new Employee("Carol",   "Marketing",   72000),
        new Employee("Dave",    "Marketing",   68000),
        new Employee("Eve",     "HR",          64000)
);

// Group employees by department
Map<String, List<Employee>> byDept = employees.stream()
        .collect(Collectors.groupingBy(Employee::department));

byDept.forEach((dept, emps) -> {
    System.out.println(dept + ": " + emps.stream()
            .map(Employee::name)
            .collect(Collectors.joining(", ")));
});
// Output:
// Engineering: Alice, Bob
// Marketing: Carol, Dave
// HR: Eve
```

**groupingBy with a downstream collector** — you can apply a second collector to each group.

```java
// Count employees per department
Map<String, Long> countByDept = employees.stream()
        .collect(Collectors.groupingBy(
                Employee::department,
                Collectors.counting()
        ));

System.out.println(countByDept);
// Output: {Engineering=2, Marketing=2, HR=1}

// Average salary per department
Map<String, Double> avgSalaryByDept = employees.stream()
        .collect(Collectors.groupingBy(
                Employee::department,
                Collectors.averagingDouble(Employee::salary)
        ));

System.out.println(avgSalaryByDept);
// Output: {Engineering=91500.0, Marketing=70000.0, HR=64000.0}
```

### Collectors.partitioningBy

Splits the stream into exactly two groups — those that match the predicate (`true`) and those that do not (`false`). Returns a `Map<Boolean, List<T>>`.

```java
Map<Boolean, List<Employee>> partition = employees.stream()
        .collect(Collectors.partitioningBy(e -> e.salary() >= 80000));

System.out.println("High earners: " + partition.get(true).stream()
        .map(Employee::name).collect(Collectors.joining(", ")));
System.out.println("Others: " + partition.get(false).stream()
        .map(Employee::name).collect(Collectors.joining(", ")));
// Output:
// High earners: Alice, Bob
// Others: Carol, Dave, Eve
```

## 6. Stream Pipeline: Lazy Evaluation

Intermediate operations do not execute immediately. Java builds a pipeline description and only pulls elements through when a terminal operation demands them. This is called lazy evaluation and has real performance implications.

The following example uses `peek()` to make the execution order visible.

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

System.out.println("Building pipeline...");

List<Integer> result = numbers.stream()
        .peek(n  -> System.out.println("  source:  " + n))
        .filter(n -> n % 2 == 0)
        .peek(n  -> System.out.println("  filtered:" + n))
        .map(n   -> n * n)
        .peek(n  -> System.out.println("  mapped:  " + n))
        .collect(Collectors.toList());

System.out.println("Result: " + result);
// Output:
// Building pipeline...
//   source:  1
//   source:  2
//   filtered:2
//   mapped:  4
//   source:  3
//   source:  4
//   filtered:4
//   mapped:  16
//   source:  5
//   source:  6
//   filtered:6
//   mapped:  36
//   source:  7
//   source:  8
//   filtered:8
//   mapped:  64
//   source:  9
//   source:  10
//   filtered:10
//   mapped:  100
// Result: [4, 16, 36, 64, 100]
```

Notice that each element travels through the entire pipeline before the next element enters. The stream processes vertically (per element), not horizontally (per operation).

**limit() cuts off processing entirely** — elements beyond the limit are never touched.

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

List<Integer> firstThreeEvens = numbers.stream()
        .peek(n -> System.out.println("Checking: " + n))
        .filter(n -> n % 2 == 0)
        .limit(3)
        .collect(Collectors.toList());

System.out.println("Result: " + firstThreeEvens);
// Output:
// Checking: 1
// Checking: 2
// Checking: 3
// Checking: 4
// Checking: 5
// Checking: 6
// Result: [2, 4, 6]
// Elements 7, 8, 9, 10 were never examined at all.
```

## 7. Primitive Streams

`Stream<Integer>` boxes each `int` into an `Integer` object. For large numeric workloads this creates unnecessary heap allocations. `IntStream`, `LongStream`, and `DoubleStream` operate directly on primitive values with no boxing.

They also provide numeric convenience methods (`sum()`, `average()`, `min()`, `max()`) that are not available on `Stream<T>`.

### Creating Primitive Streams

```java
IntStream range    = IntStream.range(1, 6);         // 1, 2, 3, 4, 5  (exclusive end)
IntStream closed   = IntStream.rangeClosed(1, 5);   // 1, 2, 3, 4, 5  (inclusive end)
LongStream longs   = LongStream.range(0L, 1_000_000L);
DoubleStream dubs  = DoubleStream.of(1.1, 2.2, 3.3);
```

### Built-In Numeric Operations

```java
IntStream scores = IntStream.of(88, 92, 75, 96, 84);

System.out.println(scores.sum());               // Output: 435
// (Must recreate — stream is consumed)

System.out.println(IntStream.of(88, 92, 75, 96, 84).average()); // Output: OptionalDouble[87.0]
System.out.println(IntStream.of(88, 92, 75, 96, 84).min());     // Output: OptionalInt[75]
System.out.println(IntStream.of(88, 92, 75, 96, 84).max());     // Output: OptionalInt[96]
System.out.println(IntStream.of(88, 92, 75, 96, 84).count());   // Output: 5
```

### Converting Between Stream Types

`mapToInt()` and `mapToDouble()` convert a `Stream<T>` to a primitive stream. `boxed()` converts back to an object stream.

```java
List<String> words = List.of("hello", "world", "streams");

// Convert Stream<String> -> IntStream to use sum()
int totalChars = words.stream()
        .mapToInt(String::length)
        .sum();
System.out.println(totalChars);
// Output: 18

// Convert IntStream -> Stream<Integer> when you need object stream methods
List<Integer> boxed = IntStream.rangeClosed(1, 5)
        .boxed()
        .collect(Collectors.toList());
System.out.println(boxed);
// Output: [1, 2, 3, 4, 5]
```

### Stream vs IntStream for Summing — Side by Side

```java
List<Integer> numbers = List.of(10, 20, 30, 40, 50);

// Using Stream<Integer> with reduce — verbose, involves boxing
int sumWithReduce = numbers.stream()
        .reduce(0, Integer::sum);
System.out.println(sumWithReduce);
// Output: 150

// Using IntStream — cleaner, no boxing
int sumWithIntStream = numbers.stream()
        .mapToInt(Integer::intValue)
        .sum();
System.out.println(sumWithIntStream);
// Output: 150
```

For small lists the difference is negligible. For millions of elements, `IntStream` is meaningfully faster.

## 8. Stream vs Collection

| Feature | Collection | Stream |
|---|---|---|
| Stores data | Yes | No |
| Reusable | Yes | No (single-use) |
| Lazy | No | Yes |
| Modifies source | Possible | Never |
| External iteration | Yes (for-each) | No (internal) |
| Size known upfront | Yes | Not always (infinite streams) |
| Numeric shortcuts | No | Yes (IntStream, etc.) |

A collection is the right choice when you need to store, access, and modify data repeatedly. A stream is the right choice when you need to describe a transformation or query over that data.

## 9. Complete Example

The following example builds an employee reporting system that demonstrates how multiple stream operations work together in a realistic codebase.

```java
import java.util.*;
import java.util.stream.*;

public record Employee(
        String name,
        String department,
        double salary,
        int yearsExperience
) {}
```

```java
public class EmployeeReport {
    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee("Alice",   "Engineering", 105000, 8),
                new Employee("Bob",     "Engineering",  92000, 5),
                new Employee("Carol",   "Engineering",  88000, 12),
                new Employee("Dave",    "Marketing",    74000, 3),
                new Employee("Eve",     "Marketing",    69000, 7),
                new Employee("Frank",   "HR",           62000, 11),
                new Employee("Grace",   "HR",           58000, 2),
                new Employee("Hank",    "Engineering",  97000, 9)
        );

        // All Engineering employees sorted by salary descending
        List<Employee> engineers = employees.stream()
                .filter(e -> e.department().equals("Engineering"))
                .sorted(Comparator.comparingDouble(Employee::salary).reversed())
                .collect(Collectors.toList());

        System.out.println("Engineering by salary (desc):");
        engineers.forEach(e ->
                System.out.printf("  %-8s $%.0f%n", e.name(), e.salary()));
        // Output:
        //   Alice    $105000
        //   Hank     $97000
        //   Bob      $92000
        //   Carol    $88000

        // Average salary across the whole company
        double avgSalary = employees.stream()
                .mapToDouble(Employee::salary)
                .average()
                .orElse(0.0);

        System.out.printf("%nCompany average salary: $%.2f%n", avgSalary);
        // Output:
        // Company average salary: $80625.00

        // Map of department -> list of employee names
        Map<String, List<String>> namesByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(Employee::name, Collectors.toList())
                ));

        System.out.println("\nNames by department:");
        namesByDept.forEach((dept, names) ->
                System.out.println("  " + dept + ": " + names));
        // Output:
        //   Engineering: [Alice, Bob, Carol, Hank]
        //   Marketing: [Dave, Eve]
        //   HR: [Frank, Grace]

        // Highest paid employee
        Optional<Employee> highestPaid = employees.stream()
                .max(Comparator.comparingDouble(Employee::salary));

        highestPaid.ifPresent(e ->
                System.out.println("\nHighest paid: " + e.name() + " ($" + e.salary() + ")"));
        // Output:
        // Highest paid: Alice ($105000.0)

        // Whether any employee has more than 10 years experience
        boolean hasVeteran = employees.stream()
                .anyMatch(e -> e.yearsExperience() > 10);

        System.out.println("\nAny employee with 10+ years: " + hasVeteran);
        // Output:
        // Any employee with 10+ years: true

        // Total salary bill
        double totalSalary = employees.stream()
                .mapToDouble(Employee::salary)
                .sum();

        System.out.printf("%nTotal salary bill: $%.0f%n", totalSalary);
        // Output:
        // Total salary bill: $645000

        // Formatted report string — all names joined
        String allNames = employees.stream()
                .map(Employee::name)
                .collect(Collectors.joining(", "));

        System.out.println("\nAll employees: " + allNames);
        // Output:
        // All employees: Alice, Bob, Carol, Dave, Eve, Frank, Grace, Hank
    }
}
```

## 10. Key Takeaways

- A Stream is a pipeline that processes data from a source; it does not store data and does not modify its source
- Intermediate operations (`filter`, `map`, `flatMap`, `sorted`, `distinct`, `peek`, `limit`, `skip`) are lazy and return a new `Stream`
- Terminal operations (`collect`, `forEach`, `count`, `reduce`, `findFirst`, `anyMatch`, `min`, `max`, `toList`) trigger the pipeline and consume the stream
- A stream can only be used once; calling a terminal operation twice throws `IllegalStateException`
- `Collectors.groupingBy` and `Collectors.partitioningBy` are among the most powerful tools in the API — they transform flat lists into structured maps with a single expression
- `IntStream`, `LongStream`, and `DoubleStream` eliminate boxing overhead and provide built-in numeric operations like `sum()` and `average()`
- Lazy evaluation means elements that are never needed (due to `limit()` or short-circuit operations) are never processed — this is a genuine performance benefit, not just a theoretical one
- Infinite streams (`Stream.iterate`, `Stream.generate`) are valid and safe as long as they are paired with a limiting terminal condition

---

Streams are the most expressive way to process collections in modern Java, and fluency with them is essential for reading and writing Spring Boot service layer code, where stream-based data transformations appear in nearly every repository or data access method.
