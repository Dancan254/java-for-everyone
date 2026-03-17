# Streams Practice Exercises

Streams are the primary tool for expressive data processing in modern Java. The exercises below progress from creating streams and applying individual operations to multi-step pipelines that solve realistic problems. Work through each set in order before moving to the next.

---

## Exercise Set 1: Creating Streams

### Exercise 1.1: Multiple Creation Methods
Create a stream in four different ways and print the element count of each:
1. From a `List<String>` of five country names using `.stream()`.
2. From a `String[]` array of five city names using `Arrays.stream()`.
3. From five explicit integer values using `Stream.of()`.
4. From an empty stream using `Stream.empty()`.

Print a labeled count for each stream.

### Exercise 1.2: Infinite Streams with a Limit
Use `Stream.iterate` and `Stream.generate` to produce bounded sequences:
1. Use `Stream.iterate(1, n -> n * 2)` limited to 10 elements to produce the powers of 2: `[1, 2, 4, 8, 16, ...]`.
2. Use `Stream.generate` with a `Supplier` that returns the string `"ping"` and collect the first 5 results.
3. Use `IntStream.range` to produce integers 10 through 19 inclusive and print their sum.
4. Use `IntStream.rangeClosed` to produce integers 1 through 100 and print their sum.

### Exercise 1.3: Streaming a Record Collection
Define a `record Product(String name, String category, double price)` inside the class. Create a `List<Product>` of at least 8 products across 3 categories (e.g. Electronics, Clothing, Food). Stream the list and:
1. Print the total number of products.
2. Print the names of all products sorted alphabetically.
3. Print the count of products in each category using `groupingBy`.

---

## Exercise Set 2: Intermediate Operations

### Exercise 2.1: filter and map
Given a `List<String>` of full names (at least 8, including some duplicates and some short single-word names), apply a stream pipeline to:
1. Filter to names that contain a space (i.e. have a first and last name).
2. Map each surviving name to uppercase.
3. Sort alphabetically.
4. Collect to a `List` and print it.

Then, separately: given a `List<Integer>` of 12 integers ranging from -10 to 20, filter to only positive odd numbers and collect their squares into a list. Print the result.

### Exercise 2.2: flatMap
Given a `List<String>` of at least 5 sentences (choose sentences that share some common words), use `flatMap` to split each sentence into individual words and collect all words into a single flat list. Print the full word list.

Then, from that same flat list, apply a second pipeline that:
1. Filters out words shorter than 4 characters.
2. Maps each word to lowercase.
3. Removes duplicates.
4. Sorts alphabetically.
5. Collects to a list and prints it.

Explain in a comment why `map` would not work in place of `flatMap` for the first step.

### Exercise 2.3: distinct, sorted, limit, and skip
Given a `List<Integer>` containing the values `[5, 3, 8, 3, 1, 9, 2, 5, 7, 1, 4, 6, 8, 2]`:
1. Remove duplicates, sort ascending, and collect to a list. Print it.
2. From the original list, skip the first 4 elements, take the next 5, and collect to a list. Print it.
3. From the original list, remove duplicates, sort descending, and take only the top 3 values. Print them.
4. From the original list, find the distinct elements in their original encounter order and limit to 6. Print them.

---

## Exercise Set 3: Terminal Operations

### Exercise 3.1: count, reduce, and forEach
Given a `List<String>` of at least 10 product names (mix of lengths):
1. Use `count()` to count how many names are longer than 5 characters. Print the count.
2. Use `reduce` with an identity of `""` to concatenate all names into a single comma-separated string (no trailing comma). Print it.
3. Use `reduce` on a `List<Integer>` of at least 8 integers to compute their product (all multiplied together). Print the result.
4. Use `forEach` to print each product name prefixed with a sequential number (e.g. `1. Notebook`). Achieve this without mutating state — use `IntStream.range` over the list indices.

### Exercise 3.2: findFirst, findAny, min, and max
Given a `List<String>` of employee names and a `List<Integer>` of their corresponding salaries (parallel lists, same indices):
1. Find and print the first name that starts with the letter `"M"` using `findFirst`. If none exists, print `"None found"`.
2. Find the employee with the highest salary using `max` on the salary list. Print their name and salary (you will need to look up the name by index).
3. Find the employee with the lowest salary using `min`. Print their name and salary.
4. Determine whether any employee has a salary above 90000 using `anyMatch`. Print the boolean result.
5. Determine whether all employees have a salary above 40000. Print the result.
6. Determine whether no employee has a salary below 30000. Print the result.

### Exercise 3.3: toList() and Terminal Short-Circuiting
Given a `List<Integer>` of 20 integers (include both negative and positive values, and some repeats):
1. Filter to positive values, sort descending, and collect using `toList()` (Java 16+ shorthand). Print the result.
2. Demonstrate short-circuiting: use `anyMatch` on a list of 1,000,000 elements (use `Stream.iterate`) and add a `peek` to count how many elements are examined before the match is found. The match should be found early (e.g. match the 7th element).
3. Find the first even number greater than 50 in a list of 30 integers using `filter` and `findFirst`. Print it, or `"Not found"` if absent.

---

## Exercise Set 4: Collectors

### Exercise 4.1: toList, toSet, and toMap
Given a `List<String>` of 10 product names where several names are duplicated:
1. Collect to a `List` (preserving duplicates and order). Print size.
2. Collect to a `Set` (removing duplicates). Print size.
3. Build a `Map<String, Integer>` mapping each unique product name to its character length using `toMap`. Print the map.
4. Attempt to collect a list with duplicate keys using `toMap` and handle the `IllegalStateException` with a merge function that keeps the longer of the two duplicate values' lengths. Print the result.

### Exercise 4.2: joining, counting, summingInt, and averagingDouble
Given a `List<String>` of at least 8 city names:
1. Join all names with `", "` as delimiter. Print the result.
2. Join all names with `" | "` as delimiter, `"Cities: ["` as prefix, and `"]"` as suffix. Print the result.
3. Use `counting()` to count names that have more than 6 characters. Print the count.
4. Use `summingInt(String::length)` to compute the total character count across all names. Print it.
5. Use `averagingDouble(String::length)` to compute the average name length. Print it formatted to 2 decimal places.

### Exercise 4.3: groupingBy and partitioningBy
Use the `Product` record from Exercise 1.3 (name, category, price) with a list of at least 10 products across 3 categories.
1. Use `groupingBy(Product::category)` to group products by category. For each category, print the category name and the names of its products.
2. Use `groupingBy` with a downstream `counting()` collector to print how many products are in each category.
3. Use `groupingBy` with a downstream `averagingDouble(Product::price)` collector to print the average price per category.
4. Use `partitioningBy(p -> p.price() >= 50.0)` to split products into two groups. Print the names in each partition.

---

## Exercise Set 5: Numeric Streams

### Exercise 5.1: IntStream Operations
1. Use `IntStream.rangeClosed(1, 50)` to compute the sum of integers 1 to 50. Print it.
2. Use `IntStream.rangeClosed(1, 50)` to compute the sum of squares (1^2 + 2^2 + ... + 50^2). Print it.
3. Use `IntStream.of` with at least 8 scores (between 60 and 100) to compute and print: sum, min, max, average, and count — each on its own labeled line.
4. Use `IntStream.of` with the same scores and call `summaryStatistics()`. Print the entire `IntSummaryStatistics` object.

### Exercise 5.2: mapToInt, mapToDouble, and boxed
Given a `List<String>` of at least 8 words:
1. Use `mapToInt(String::length)` to compute the total character count. Print it.
2. Use `mapToInt(String::length)` to find the longest word length. Use `max()` and print the result.
3. Use `mapToInt(String::length).average()` to find the average word length. Print formatted to 2 decimal places.
4. Use `mapToInt(String::length).boxed().collect(Collectors.toList())` to collect all word lengths as a `List<Integer>`. Print the list.

Given a `List<Product>` (from Exercise 1.3):
5. Use `mapToDouble(Product::price)` to compute total revenue (sum). Print it.
6. Use `mapToDouble(Product::price).average()` to find the average price. Print it.

### Exercise 5.3: LongStream and DoubleStream
1. Use `LongStream.rangeClosed(1, 1_000_000)` to compute the sum of integers 1 to 1,000,000. Print it.
2. Use `LongStream.iterate(1L, n -> n * 2).limit(20)` to generate the first 20 powers of 2. Collect to a list and print it.
3. Create a `DoubleStream` from 6 explicit decimal values representing temperatures in Celsius. Compute and print the average. Then convert each value to Fahrenheit (`f = c * 9 / 5 + 32`) using `map`, collect using `boxed()`, and print the Fahrenheit list.

---

## Exercise Set 6: Stream Chaining

### Exercise 6.1: Word Frequency Analysis
Given a paragraph of text as a single `String` (at least 4 sentences, 50+ words), write a stream pipeline that:
1. Splits the paragraph into individual words (split on spaces and punctuation using `split("[\\s.,!?;:\"'()-]+")`).
2. Converts all words to lowercase.
3. Filters out empty strings.
4. Groups words by their first letter using `groupingBy` with `counting()` as the downstream collector.
5. Prints each letter and its count, sorted by letter alphabetically.

Then, in a separate pipeline on the same source text:
6. Find the 5 most frequently occurring words. Use `groupingBy(w -> w, counting())` then sort the resulting map entries by count descending and take the top 5. Print each word and its count.

### Exercise 6.2: Order Processing Report
Define a `record Order(String orderId, String customer, String product, int quantity, double unitPrice)` in the class. Create a list of at least 12 orders covering at least 4 customers and 5 products (include some customers with multiple orders).

Write stream pipelines to produce the following report sections:
1. Total revenue across all orders: `quantity * unitPrice` summed. Print it formatted to 2 decimal places.
2. Revenue per customer (Map of customer name to total revenue), sorted by revenue descending. Print each entry.
3. The customer with the highest total revenue. Print their name and total.
4. All orders for a specific customer, sorted by unit price descending. Print each order's product, quantity, and line total.
5. The top 3 best-selling products by total units sold. Print each product name and units.

### Exercise 6.3: Student Grade Report
Define a `record Student(String name, String major, int year, double gpa)` in the class. Create a list of at least 14 students across at least 3 majors and 4 year levels (1–4).

Write stream pipelines to:
1. Print the name and GPA of every student with a GPA above 3.5, sorted by GPA descending.
2. Compute the average GPA per major. Print each major and its average formatted to 2 decimal places.
3. Find the student with the highest GPA in each major using `groupingBy` with a downstream `maxBy` collector. Print each major and the top student's name and GPA.
4. Partition students into honor roll (GPA >= 3.7) and not. Print the count in each partition and the names of honor roll students joined by `", "`.
5. For each year level (1–4), print the number of students. Use `groupingBy(Student::year, counting())` and iterate the map sorted by key.

---

## Common Mistakes

### Reusing a consumed stream
A stream is consumed after any terminal operation. Calling a second terminal operation on the same stream instance throws `IllegalStateException`.

```java
Stream<String> stream = List.of("a", "b", "c").stream();

long count = stream.count();         // Terminal operation — stream is now consumed.
List<String> list = stream.toList(); // Throws IllegalStateException: stream has already been operated upon or closed.
```

Always obtain a fresh stream from the source for each pipeline.

```java
List<String> names = List.of("a", "b", "c");

long count = names.stream().count();        // Fresh stream.
List<String> list = names.stream().toList(); // Another fresh stream.
```

### Calling multiple terminal operations on one stream
The same rule applies when you save a stream reference and attempt to chain two terminal calls. Each pipeline needs its own stream obtained from the source.

```java
// Wrong: two terminal operations on one stream.
Stream<Integer> s = List.of(1, 2, 3).stream();
System.out.println(s.count());       // Consumes the stream.
System.out.println(s.findFirst());   // Throws IllegalStateException.

// Correct: separate streams.
List<Integer> nums = List.of(1, 2, 3);
System.out.println(nums.stream().count());
System.out.println(nums.stream().findFirst());
```

### Confusing map with flatMap
`map` applies a function to each element and returns a stream of the results — one output per input, at the same nesting level. `flatMap` applies a function that returns a stream and then merges all those inner streams into a single flat stream.

```java
List<String> sentences = List.of("hello world", "streams are useful");

// Wrong: map returns Stream<String[]>, not Stream<String>.
Stream<String[]> wrongResult = sentences.stream()
        .map(s -> s.split(" "));

// Correct: flatMap flattens the String[] results into a single Stream<String>.
List<String> words = sentences.stream()
        .flatMap(s -> Arrays.stream(s.split(" ")))
        .collect(Collectors.toList());
// Output: [hello, world, streams, are, useful]
```

Use `map` when each input produces exactly one output. Use `flatMap` when each input produces zero or more outputs that should be merged into a single stream.

### Using forEach with side effects instead of collect
`forEach` is a terminal operation intended for printing or logging. Using it to build a collection by mutating an external list is error-prone, defeats the purpose of streams, and is unsafe in parallel streams.

```java
// Wrong: mutating an external list inside forEach.
List<String> result = new ArrayList<>();
List.of("a", "bb", "ccc").stream()
        .filter(s -> s.length() > 1)
        .forEach(s -> result.add(s)); // Side effect on a mutable external collection.

// Correct: use collect to build the result functionally.
List<String> result = List.of("a", "bb", "ccc").stream()
        .filter(s -> s.length() > 1)
        .collect(Collectors.toList());
```

`collect` is designed precisely for accumulating stream results into a container. Prefer it over any pattern that manually builds a collection from `forEach`.

---

Solutions for these exercises are in the `solutions/` subfolder.
