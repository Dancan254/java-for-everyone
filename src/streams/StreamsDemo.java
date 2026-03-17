import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.OptionalDouble;
import java.util.stream.Stream;

/**
 * StreamsDemo.java
 *
 * A self-contained demonstration of the Java Streams API covering every concept in streams.md:
 * creating streams (from collections, arrays, Stream.of, infinite), all intermediate operations
 * (filter, map, flatMap, distinct, sorted, peek, limit, skip), all terminal operations
 * (forEach, collect, count, reduce, findFirst/findAny, anyMatch/allMatch/noneMatch, min, max, toList),
 * Collectors (toList, toSet, toMap, joining, counting, summingInt, averagingInt, groupingBy,
 * partitioningBy, mapping), lazy evaluation with peek, and primitive streams (IntStream, LongStream,
 * DoubleStream).
 */
public class StreamsDemo {

    // A simple record used in groupingBy and partitioningBy examples.
    record Employee(String name, String department, double salary, int yearsExperience) {}

    public static void main(String[] args) {

        // --- 1. Creating Streams ---

        System.out.println("=== 1. Creating Streams ===");

        // From a collection — every Collection has a .stream() method.
        List<String> cities = List.of("London", "Tokyo", "Cairo");
        Stream<String> cityStream = cities.stream();
        System.out.println("Stream from list, count: " + cityStream.count()); // Output: 3

        // From an array — use Arrays.stream().
        String[] words = {"hello", "world", "streams"};
        Stream<String> wordStream = Arrays.stream(words);
        System.out.println("Stream from array, count: " + wordStream.count()); // Output: 3

        // From explicit values — use Stream.of().
        Stream<String> colourStream = Stream.of("red", "green", "blue");
        System.out.println("Stream.of count: " + colourStream.count()); // Output: 3

        // Empty stream — useful as a safe default return value.
        Stream<String> empty = Stream.empty();
        System.out.println("Empty stream count: " + empty.count()); // Output: 0

        // Infinite stream with iterate — always pair with limit().
        List<Integer> firstFive = Stream.iterate(0, n -> n + 1)
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("iterate(0, n->n+1) limit 5: " + firstFive);
        // Output: iterate(0, n->n+1) limit 5: [0, 1, 2, 3, 4]

        // Infinite stream with generate — produces a Supplier's result endlessly.
        List<Double> fiveRandoms = Stream.generate(Math::random)
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("generate(random) limit 5: " + fiveRandoms.size() + " values generated");
        // Output: generate(random) limit 5: 5 values generated

        // IntStream range and rangeClosed — compact ways to produce integer sequences.
        System.out.print("IntStream.range(1,6): ");
        IntStream.range(1, 6).forEach(n -> System.out.print(n + " "));
        System.out.println();
        // Output: IntStream.range(1,6): 1 2 3 4 5

        System.out.print("IntStream.rangeClosed(1,5): ");
        IntStream.rangeClosed(1, 5).forEach(n -> System.out.print(n + " "));
        System.out.println();
        // Output: IntStream.rangeClosed(1,5): 1 2 3 4 5

        // --- 2. Intermediate Operations ---

        System.out.println("\n=== 2. Intermediate Operations ===");

        // filter: keeps only elements that satisfy the predicate.
        List<Integer> evens = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("filter (evens): " + evens);
        // Output: filter (evens): [2, 4, 6, 8, 10]

        // map: transforms each element; the output stream can be a different type.
        List<String> upper = List.of("alice", "bob", "carol").stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println("map (toUpperCase): " + upper);
        // Output: map (toUpperCase): [ALICE, BOB, CAROL]

        // flatMap: each element maps to a stream; flatMap flattens all inner streams into one.
        List<String> sentences = List.of("the quick brown fox", "jumps over the lazy dog");
        List<String> wordList = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .collect(Collectors.toList());
        System.out.println("flatMap (split sentences): " + wordList);
        // Output: flatMap (split sentences): [the, quick, brown, fox, jumps, over, the, lazy, dog]

        // distinct: removes duplicates using equals().
        List<Integer> unique = List.of(3, 1, 4, 1, 5, 9, 2, 6, 5, 3).stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("distinct: " + unique);
        // Output: distinct: [3, 1, 4, 5, 9, 2, 6]

        // sorted(): natural ordering.
        List<String> alphabetical = List.of("Charlie", "Alice", "Bob").stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("sorted (natural): " + alphabetical);
        // Output: sorted (natural): [Alice, Bob, Charlie]

        // sorted(Comparator): custom ordering.
        List<String> byLength = List.of("Charlie", "Alice", "Bob").stream()
                .sorted(Comparator.comparingInt(String::length).reversed())
                .collect(Collectors.toList());
        System.out.println("sorted (by length desc): " + byLength);
        // Output: sorted (by length desc): [Charlie, Alice, Bob]

        // limit: keeps only the first n elements.
        List<Integer> firstThree = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("limit(3): " + firstThree);
        // Output: limit(3): [1, 2, 3]

        // skip: discards the first n elements.
        List<Integer> afterSkip = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
                .skip(7)
                .collect(Collectors.toList());
        System.out.println("skip(7): " + afterSkip);
        // Output: skip(7): [8, 9, 10]

        // skip + limit together — classic pagination pattern.
        List<Integer> page = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
                .skip(3)
                .limit(4)
                .collect(Collectors.toList());
        System.out.println("skip(3).limit(4) — page 2: " + page);
        // Output: skip(3).limit(4) — page 2: [4, 5, 6, 7]

        // Multi-step pipeline chaining all intermediate operations.
        List<String> multiStep = List.of(
                "stream", "lambda", "map", "filter", "stream",
                "collect", "map", "optional", "reduce"
        ).stream()
                .filter(w -> w.length() > 3)     // keep words longer than 3 chars
                .map(String::toUpperCase)          // uppercase them
                .distinct()                        // remove duplicates
                .sorted()                          // sort alphabetically
                .limit(4)                          // take the first 4
                .collect(Collectors.toList());

        System.out.println("Multi-step pipeline: " + multiStep);
        // Output: Multi-step pipeline: [COLLECT, FILTER, LAMBDA, OPTIONAL]

        // --- 3. Terminal Operations ---

        System.out.println("\n=== 3. Terminal Operations ===");

        // forEach: iterates and performs a side effect; returns void.
        System.out.print("forEach: ");
        List.of("Alice", "Bob", "Carol").stream()
                .forEach(name -> System.out.print(name + " "));
        System.out.println();
        // Output: forEach: Alice Bob Carol

        // collect: gathers stream elements into a container.
        List<Integer> evenList = List.of(1, 2, 3, 4, 5, 6).stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("collect(toList): " + evenList);
        // Output: collect(toList): [2, 4, 6]

        // count: returns the number of elements as a long.
        long countLong = List.of("a", "bb", "ccc", "dddd").stream()
                .filter(s -> s.length() >= 2)
                .count();
        System.out.println("count (length >= 2): " + countLong);
        // Output: count (length >= 2): 3

        // reduce: folds all elements into a single value.
        int sum = List.of(1, 2, 3, 4, 5).stream()
                .reduce(0, Integer::sum);
        System.out.println("reduce (sum): " + sum);
        // Output: reduce (sum): 15

        int product = List.of(1, 2, 3, 4, 5).stream()
                .reduce(1, (a, b) -> a * b);
        System.out.println("reduce (product): " + product);
        // Output: reduce (product): 120

        String joined = List.of("Java", "is", "expressive").stream()
                .reduce("", (a, b) -> a.isEmpty() ? b : a + " " + b);
        System.out.println("reduce (join): " + joined);
        // Output: reduce (join): Java is expressive

        // findFirst: returns an Optional<T> with the first element in encounter order.
        Optional<String> first = List.of("apple", "banana", "cherry").stream()
                .filter(s -> s.startsWith("b"))
                .findFirst();
        first.ifPresent(val -> System.out.println("findFirst: " + val));
        // Output: findFirst: banana

        // anyMatch / allMatch / noneMatch — short-circuit predicates.
        List<Integer> mixed = List.of(2, 4, 6, 7, 8);
        System.out.println("anyMatch (odd): "  + mixed.stream().anyMatch(n -> n % 2 != 0)); // Output: true
        System.out.println("allMatch (even): " + mixed.stream().allMatch(n -> n % 2 == 0)); // Output: false
        System.out.println("noneMatch (neg): " + mixed.stream().noneMatch(n -> n < 0));      // Output: true

        // min and max — return Optional<T>.
        List<String> fruitList = List.of("apple", "fig", "banana", "kiwi");
        fruitList.stream().min(Comparator.comparingInt(String::length))
                .ifPresent(s -> System.out.println("min (shortest): " + s));
        // Output: min (shortest): fig

        fruitList.stream().max(Comparator.comparingInt(String::length))
                .ifPresent(s -> System.out.println("max (longest): " + s));
        // Output: max (longest): banana

        // toList() (Java 16+) — shorthand for collect(Collectors.toList()); returns unmodifiable list.
        List<String> upperList = Stream.of("x", "y", "z")
                .map(String::toUpperCase)
                .toList();
        System.out.println("toList(): " + upperList);
        // Output: toList(): [X, Y, Z]

        // --- 4. Collectors ---

        System.out.println("\n=== 4. Collectors ===");

        List<String> namesWithDupes = List.of("Alice", "Bob", "Alice", "Carol", "Bob");

        // toSet: collects into a Set, removing duplicates.
        Set<String> nameSet = namesWithDupes.stream().collect(Collectors.toSet());
        System.out.println("toSet size (3 unique): " + nameSet.size()); // Output: 3

        // toMap: builds a Map where keys and values are derived from each element.
        Map<String, Integer> wordLengths = List.of("apple", "fig", "banana").stream()
                .collect(Collectors.toMap(
                        word -> word,
                        String::length
                ));
        System.out.println("toMap (word->length): " + wordLengths);
        // Output: toMap (word->length): {apple=5, fig=3, banana=6}

        // joining: concatenates stream elements into a String.
        String csv = List.of("Alice", "Bob", "Carol").stream()
                .collect(Collectors.joining(", "));
        System.out.println("joining (csv): " + csv);
        // Output: joining (csv): Alice, Bob, Carol

        String formatted = List.of("Alice", "Bob", "Carol").stream()
                .collect(Collectors.joining(", ", "[", "]"));
        System.out.println("joining (bracketed): " + formatted);
        // Output: joining (bracketed): [Alice, Bob, Carol]

        // counting / summingInt / averagingInt.
        List<String> shortWords = List.of("a", "bb", "ccc", "dd", "e");
        long wordCount = shortWords.stream().collect(Collectors.counting());
        int totalLength = shortWords.stream().collect(Collectors.summingInt(String::length));
        double avgLength = shortWords.stream().collect(Collectors.averagingInt(String::length));

        System.out.println("counting: "     + wordCount);   // Output: 5
        System.out.println("summingInt: "   + totalLength); // Output: 9
        System.out.println("averagingInt: " + avgLength);   // Output: 1.8

        // groupingBy: groups elements by a classifier into Map<K, List<V>>.
        List<Employee> employees = List.of(
                new Employee("Alice",  "Engineering", 95000, 8),
                new Employee("Bob",    "Engineering", 88000, 5),
                new Employee("Carol",  "Marketing",   72000, 3),
                new Employee("Dave",   "Marketing",   68000, 7),
                new Employee("Eve",    "HR",          64000, 11)
        );

        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::department));

        System.out.println("\ngroupingBy (department -> employees):");
        byDept.forEach((dept, emps) -> {
            String memberNames = emps.stream()
                    .map(Employee::name)
                    .collect(Collectors.joining(", "));
            System.out.println("  " + dept + ": " + memberNames);
        });
        // Output:
        //   Engineering: Alice, Bob
        //   Marketing: Carol, Dave
        //   HR: Eve

        // groupingBy with downstream collector — count per department.
        Map<String, Long> countByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::department, Collectors.counting()));
        System.out.println("groupingBy + counting: " + countByDept);
        // Output: groupingBy + counting: {Engineering=2, Marketing=2, HR=1}

        // groupingBy with downstream — average salary per department.
        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.averagingDouble(Employee::salary)
                ));
        System.out.println("groupingBy + averagingDouble: " + avgSalaryByDept);
        // Output: groupingBy + averagingDouble: {Engineering=91500.0, Marketing=70000.0, HR=64000.0}

        // partitioningBy: splits into exactly two groups — true and false.
        Map<Boolean, List<Employee>> partition = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.salary() >= 80000));

        String highEarners = partition.get(true).stream()
                .map(Employee::name)
                .collect(Collectors.joining(", "));
        String others = partition.get(false).stream()
                .map(Employee::name)
                .collect(Collectors.joining(", "));

        System.out.println("partitioningBy (salary >= 80000):");
        System.out.println("  High earners: " + highEarners); // Output:   High earners: Alice, Bob
        System.out.println("  Others:       " + others);      // Output:   Others:       Carol, Dave, Eve

        // --- 5. Lazy Evaluation with peek ---

        System.out.println("\n=== 5. Lazy Evaluation (peek) ===");

        // peek() lets you observe elements at a specific pipeline stage without modifying them.
        // This makes the vertical-per-element (not horizontal-per-operation) execution order visible.
        System.out.println("Pipeline trace (filter evens, then square):");
        List<Integer> lazyResult = List.of(1, 2, 3, 4, 5).stream()
                .peek(n -> System.out.println("  source:   " + n))
                .filter(n -> n % 2 == 0)
                .peek(n -> System.out.println("  filtered: " + n))
                .map(n -> n * n)
                .peek(n -> System.out.println("  mapped:   " + n))
                .collect(Collectors.toList());
        System.out.println("  result: " + lazyResult);
        // Output:
        //   source:   1
        //   source:   2
        //   filtered: 2
        //   mapped:   4
        //   source:   3
        //   source:   4
        //   filtered: 4
        //   mapped:   16
        //   source:   5
        //   result: [4, 16]

        // limit() demonstrates that elements beyond the cutoff are never processed at all.
        System.out.println("\nlimit(3) — elements 7-10 are never examined:");
        List<Integer> limitDemo = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).stream()
                .peek(n -> System.out.print(n + " "))
                .filter(n -> n % 2 == 0)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println();
        System.out.println("result: " + limitDemo);
        // Output:
        // 1 2 3 4 5 6
        // result: [2, 4, 6]

        // --- 6. Primitive Streams ---

        System.out.println("\n=== 6. Primitive Streams ===");

        // IntStream avoids boxing each int into Integer, reducing heap allocation.
        // It also provides built-in numeric operations (sum, average, min, max, count).
        int intSum = IntStream.of(88, 92, 75, 96, 84).sum();
        System.out.println("IntStream.sum(): " + intSum); // Output: IntStream.sum(): 435

        OptionalDouble avg = IntStream.of(88, 92, 75, 96, 84).average();
        System.out.println("IntStream.average(): " + avg); // Output: IntStream.average(): OptionalDouble[87.0]

        System.out.println("IntStream.min(): " + IntStream.of(88, 92, 75, 96, 84).min());
        // Output: IntStream.min(): OptionalInt[75]

        System.out.println("IntStream.max(): " + IntStream.of(88, 92, 75, 96, 84).max());
        // Output: IntStream.max(): OptionalInt[96]

        // IntSummaryStatistics collects all numeric stats in a single pass.
        IntSummaryStatistics stats = IntStream.of(88, 92, 75, 96, 84).summaryStatistics();
        System.out.println("IntSummaryStatistics: " + stats);
        // Output: IntSummaryStatistics: IntSummaryStatistics{count=5, sum=435, min=75, average=87.000000, max=96}

        // LongStream — same idea but for long values; useful for large numeric ranges.
        long longSum = LongStream.rangeClosed(1, 100).sum();
        System.out.println("LongStream.rangeClosed(1,100).sum(): " + longSum);
        // Output: LongStream.rangeClosed(1,100).sum(): 5050

        // mapToInt: converts Stream<String> to IntStream to use sum() directly.
        int totalChars = List.of("hello", "world", "streams").stream()
                .mapToInt(String::length)
                .sum();
        System.out.println("mapToInt + sum (total chars): " + totalChars);
        // Output: mapToInt + sum (total chars): 18

        // boxed(): converts IntStream back to Stream<Integer> for use in collections.
        List<Integer> boxedList = IntStream.rangeClosed(1, 5)
                .boxed()
                .collect(Collectors.toList());
        System.out.println("IntStream.boxed() -> List: " + boxedList);
        // Output: IntStream.boxed() -> List: [1, 2, 3, 4, 5]

        // mapToDouble: converts Stream<Employee> to DoubleStream for salary calculations.
        double totalSalary = employees.stream()
                .mapToDouble(Employee::salary)
                .sum();
        System.out.printf("mapToDouble + sum (total salary): $%.0f%n", totalSalary);
        // Output: mapToDouble + sum (total salary): $387000

        double avgSalary = employees.stream()
                .mapToDouble(Employee::salary)
                .average()
                .orElse(0.0);
        System.out.printf("mapToDouble + average (avg salary): $%.2f%n", avgSalary);
        // Output: mapToDouble + average (avg salary): $77400.00

        // --- 7. Complete Example: Employee Report ---

        System.out.println("\n=== 7. Complete Example: Employee Report ===");

        List<Employee> fullRoster = List.of(
                new Employee("Alice",  "Engineering", 105000, 8),
                new Employee("Bob",    "Engineering",  92000, 5),
                new Employee("Carol",  "Engineering",  88000, 12),
                new Employee("Dave",   "Marketing",    74000, 3),
                new Employee("Eve",    "Marketing",    69000, 7),
                new Employee("Frank",  "HR",           62000, 11),
                new Employee("Grace",  "HR",           58000, 2),
                new Employee("Hank",   "Engineering",  97000, 9)
        );

        // Engineering employees sorted by salary descending.
        System.out.println("Engineering by salary (desc):");
        fullRoster.stream()
                .filter(e -> e.department().equals("Engineering"))
                .sorted(Comparator.comparingDouble(Employee::salary).reversed())
                .forEach(e -> System.out.printf("  %-8s $%.0f%n", e.name(), e.salary()));
        // Output:
        //   Alice    $105000
        //   Hank     $97000
        //   Bob      $92000
        //   Carol    $88000

        // Total salary bill using mapToDouble.
        double totalBill = fullRoster.stream()
                .mapToDouble(Employee::salary)
                .sum();
        System.out.printf("%nTotal salary bill: $%.0f%n", totalBill);
        // Output:
        // Total salary bill: $645000

        // Map of department name -> comma-separated employee names.
        Map<String, String> namesByDept = fullRoster.stream()
                .collect(Collectors.groupingBy(
                        Employee::department,
                        Collectors.mapping(Employee::name, Collectors.joining(", "))
                ));
        System.out.println("\nNames by department:");
        namesByDept.forEach((dept, members) -> System.out.println("  " + dept + ": " + members));
        // Output:
        //   Engineering: Alice, Bob, Carol, Hank
        //   Marketing: Dave, Eve
        //   HR: Frank, Grace

        // Highest paid employee.
        fullRoster.stream()
                .max(Comparator.comparingDouble(Employee::salary))
                .ifPresent(e -> System.out.println("\nHighest paid: " + e.name() + " ($" + e.salary() + ")"));
        // Output:
        // Highest paid: Alice ($105000.0)

        // All names joined in a single string.
        String allNames = fullRoster.stream()
                .map(Employee::name)
                .collect(Collectors.joining(", "));
        System.out.println("\nAll employees: " + allNames);
        // Output:
        // All employees: Alice, Bob, Carol, Dave, Eve, Frank, Grace, Hank
    }
}
