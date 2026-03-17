package streams.solutions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Exercise1Solution.java
 *
 * Solutions for Exercise Set 1: Creating Streams.
 * Covers: stream() from collections, Arrays.stream() from arrays, Stream.of(),
 * Stream.empty(), Stream.iterate(), Stream.generate(), IntStream.range(),
 * IntStream.rangeClosed(), and streaming a record collection.
 */
public class Exercise1Solution {

    record Product(String name, String category, double price) {}

    public static void main(String[] args) {

        // --- Exercise 1.1: Multiple Creation Methods ---
        System.out.println("=== Exercise 1.1: Multiple Creation Methods ===");
        exercise1();

        // --- Exercise 1.2: Infinite Streams with a Limit ---
        System.out.println("\n=== Exercise 1.2: Infinite Streams with a Limit ===");
        exercise2();

        // --- Exercise 1.3: Streaming a Record Collection ---
        System.out.println("\n=== Exercise 1.3: Streaming a Record Collection ===");
        exercise3();
    }

    // ---------------------------------------------------------------------------
    // Exercise 1.1
    // ---------------------------------------------------------------------------

    /**
     * Demonstrates creating streams from a List, an array, Stream.of(), and Stream.empty().
     * Each stream is counted and the count is printed with a label.
     */
    static void exercise1() {
        // 1. From a List<String> using .stream().
        List<String> countries = List.of("Brazil", "Germany", "Japan", "Nigeria", "Canada");
        long countFromList = countries.stream().count();
        System.out.println("Stream from List (count): " + countFromList); // Output: 5

        // 2. From a String[] array using Arrays.stream().
        String[] cities = {"Lagos", "Berlin", "Tokyo", "Toronto", "Sao Paulo"};
        long countFromArray = Arrays.stream(cities).count();
        System.out.println("Stream from array (count): " + countFromArray); // Output: 5

        // 3. From explicit values using Stream.of().
        long countFromOf = Stream.of(10, 20, 30, 40, 50).count();
        System.out.println("Stream.of (count): " + countFromOf); // Output: 5

        // 4. Empty stream — useful as a safe default return value.
        long countFromEmpty = Stream.empty().count();
        System.out.println("Stream.empty (count): " + countFromEmpty); // Output: 0
    }

    // ---------------------------------------------------------------------------
    // Exercise 1.2
    // ---------------------------------------------------------------------------

    /**
     * Demonstrates infinite streams (iterate, generate) and IntStream range methods,
     * each bounded with limit() or collected from a closed range.
     */
    static void exercise2() {
        // 1. Powers of 2 using Stream.iterate, limited to 10 elements.
        List<Integer> powersOfTwo = Stream.iterate(1, n -> n * 2)
                .limit(10)
                .collect(Collectors.toList());
        System.out.println("Powers of 2 (limit 10): " + powersOfTwo);
        // Output: Powers of 2 (limit 10): [1, 2, 4, 8, 16, 32, 64, 128, 256, 512]

        // 2. Stream.generate producing "ping" — collect first 5 results.
        List<String> pings = Stream.generate(() -> "ping")
                .limit(5)
                .collect(Collectors.toList());
        System.out.println("Stream.generate (ping x5): " + pings);
        // Output: Stream.generate (ping x5): [ping, ping, ping, ping, ping]

        // 3. IntStream.range(10, 20) — exclusive end, produces 10..19.
        int rangeSum = IntStream.range(10, 20).sum();
        System.out.println("IntStream.range(10, 20) sum: " + rangeSum); // Output: 145

        // 4. IntStream.rangeClosed(1, 100) — inclusive end, produces 1..100.
        int closedSum = IntStream.rangeClosed(1, 100).sum();
        System.out.println("IntStream.rangeClosed(1, 100) sum: " + closedSum); // Output: 5050
    }

    // ---------------------------------------------------------------------------
    // Exercise 1.3
    // ---------------------------------------------------------------------------

    /**
     * Streams a record collection to count elements, sort and print names,
     * and group by category.
     */
    static void exercise3() {
        List<Product> products = List.of(
                new Product("Laptop",        "Electronics", 999.99),
                new Product("Headphones",    "Electronics", 149.99),
                new Product("USB Hub",       "Electronics",  39.99),
                new Product("T-Shirt",       "Clothing",     24.99),
                new Product("Jeans",         "Clothing",     59.99),
                new Product("Winter Jacket", "Clothing",    129.99),
                new Product("Rice",          "Food",          3.49),
                new Product("Olive Oil",     "Food",          8.99)
        );

        // 1. Total number of products.
        long total = products.stream().count();
        System.out.println("Total products: " + total); // Output: Total products: 8

        // 2. All product names sorted alphabetically.
        List<String> sortedNames = products.stream()
                .map(Product::name)
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Names (sorted): " + sortedNames);
        // Output: Names (sorted): [Headphones, Jeans, Laptop, Olive Oil, Rice, T-Shirt, USB Hub, Winter Jacket]

        // 3. Count of products per category using groupingBy.
        Map<String, Long> countByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::category, Collectors.counting()));
        System.out.println("Products per category:");
        countByCategory.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println("  " + e.getKey() + ": " + e.getValue()));
        // Output:
        //   Clothing: 3
        //   Electronics: 3
        //   Food: 2
    }
}
