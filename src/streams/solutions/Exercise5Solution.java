package streams.solutions;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 * Exercise5Solution.java
 *
 * Solutions for Exercise Set 5: Numeric Streams.
 * Covers: IntStream (range, rangeClosed, of, sum, min, max, average, count,
 * summaryStatistics), mapToInt, mapToDouble, boxed(), LongStream, DoubleStream,
 * and converting between object streams and primitive streams.
 */
public class Exercise5Solution {

    record Product(String name, String category, double price) {}

    public static void main(String[] args) {

        // --- Exercise 5.1: IntStream Operations ---
        System.out.println("=== Exercise 5.1: IntStream Operations ===");
        exercise1();

        // --- Exercise 5.2: mapToInt, mapToDouble, and boxed ---
        System.out.println("\n=== Exercise 5.2: mapToInt, mapToDouble, boxed ===");
        exercise2();

        // --- Exercise 5.3: LongStream and DoubleStream ---
        System.out.println("\n=== Exercise 5.3: LongStream and DoubleStream ===");
        exercise3();
    }

    // ---------------------------------------------------------------------------
    // Exercise 5.1
    // ---------------------------------------------------------------------------

    /**
     * Demonstrates IntStream range operations, built-in numeric terminals,
     * and summaryStatistics() for a single-pass multi-stat collection.
     */
    static void exercise1() {
        // 1. Sum of integers 1 to 50.
        int sum1to50 = IntStream.rangeClosed(1, 50).sum();
        System.out.println("Sum 1..50: " + sum1to50); // Output: Sum 1..50: 1275

        // 2. Sum of squares 1^2 + 2^2 + ... + 50^2.
        int sumOfSquares = IntStream.rangeClosed(1, 50)
                .map(n -> n * n)
                .sum();
        System.out.println("Sum of squares 1..50: " + sumOfSquares); // Output: Sum of squares 1..50: 42925

        // 3. Numeric stats on exam scores — each terminal call requires a fresh stream.
        int[] scores = {88, 73, 95, 61, 84, 97, 79, 66};
        System.out.println("Score stats:");
        System.out.println("  sum:     " + IntStream.of(scores).sum());     // Output:   sum:     643
        System.out.println("  min:     " + IntStream.of(scores).min().getAsInt()); // Output:   min:     61
        System.out.println("  max:     " + IntStream.of(scores).max().getAsInt()); // Output:   max:     97
        System.out.printf ("  average: %.2f%n", IntStream.of(scores).average().getAsDouble()); // Output:   average: 80.38
        System.out.println("  count:   " + IntStream.of(scores).count());   // Output:   count:   8

        // 4. summaryStatistics() collects all stats in a single pass.
        IntSummaryStatistics stats = IntStream.of(scores).summaryStatistics();
        System.out.println("IntSummaryStatistics: " + stats);
        // Output: IntSummaryStatistics: IntSummaryStatistics{count=8, sum=643, min=61, average=80.375000, max=97}
    }

    // ---------------------------------------------------------------------------
    // Exercise 5.2
    // ---------------------------------------------------------------------------

    /**
     * Converts Stream<String> to IntStream with mapToInt to compute length-based
     * statistics, then boxes back to a List<Integer>. Also uses mapToDouble
     * on a Product stream for price calculations.
     */
    static void exercise2() {
        List<String> words = List.of(
                "algorithm", "stream", "lambda", "pipeline",
                "collector", "reduce", "map", "filter"
        );

        // 1. Total character count using mapToInt + sum().
        int totalChars = words.stream()
                .mapToInt(String::length)
                .sum();
        System.out.println("Total characters: " + totalChars); // Output: Total characters: 58

        // 2. Longest word length using mapToInt + max().
        int longestLength = words.stream()
                .mapToInt(String::length)
                .max()
                .getAsInt();
        System.out.println("Longest word length: " + longestLength); // Output: Longest word length: 9

        // 3. Average word length using mapToInt + average().
        double avgLength = words.stream()
                .mapToInt(String::length)
                .average()
                .getAsDouble();
        System.out.printf("Average word length: %.2f%n", avgLength); // Output: Average word length: 7.25

        // 4. Word lengths collected as List<Integer> using boxed().
        List<Integer> lengths = words.stream()
                .mapToInt(String::length)
                .boxed()
                .collect(Collectors.toList());
        System.out.println("Word lengths as List: " + lengths);
        // Output: Word lengths as List: [9, 6, 6, 8, 9, 6, 3, 6]

        // Products for parts 5 and 6.
        List<Product> products = List.of(
                new Product("Laptop",     "Electronics", 999.99),
                new Product("Smartphone", "Electronics", 699.99),
                new Product("Headphones", "Electronics", 149.99),
                new Product("Blazer",     "Clothing",    189.99),
                new Product("Jeans",      "Clothing",     59.99),
                new Product("Olive Oil",  "Food",          8.99)
        );

        // 5. Total revenue using mapToDouble + sum().
        double totalRevenue = products.stream()
                .mapToDouble(Product::price)
                .sum();
        System.out.printf("Total revenue: $%.2f%n", totalRevenue); // Output: Total revenue: $2108.94

        // 6. Average price using mapToDouble + average().
        double avgPrice = products.stream()
                .mapToDouble(Product::price)
                .average()
                .getAsDouble();
        System.out.printf("Average price: $%.2f%n", avgPrice); // Output: Average price: $351.49
    }

    // ---------------------------------------------------------------------------
    // Exercise 5.3
    // ---------------------------------------------------------------------------

    /**
     * Demonstrates LongStream for large numeric ranges and exact long arithmetic,
     * and DoubleStream for decimal values with a Celsius-to-Fahrenheit conversion.
     */
    static void exercise3() {
        // 1. Sum of 1 to 1,000,000 — requires long to avoid int overflow.
        long sumToMillion = LongStream.rangeClosed(1, 1_000_000).sum();
        System.out.println("Sum 1..1,000,000: " + sumToMillion); // Output: Sum 1..1,000,000: 500000500000

        // 2. First 20 powers of 2 as a List<Long> using LongStream.iterate.
        List<Long> powersOf2 = LongStream.iterate(1L, n -> n * 2)
                .limit(20)
                .boxed()
                .collect(Collectors.toList());
        System.out.println("First 20 powers of 2: " + powersOf2);
        // Output: First 20 powers of 2: [1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288]

        // 3. DoubleStream of Celsius temperatures — average, then convert to Fahrenheit.
        double[] celsius = {0.0, 20.0, 37.0, 100.0, -10.0, 22.5};

        double avgCelsius = DoubleStream.of(celsius).average().getAsDouble();
        System.out.printf("Average temperature (C): %.2f%n", avgCelsius);
        // Output: Average temperature (C): 28.25

        // Convert each Celsius value to Fahrenheit: F = C * 9/5 + 32.
        List<Double> fahrenheit = DoubleStream.of(celsius)
                .map(c -> c * 9.0 / 5.0 + 32.0)
                .boxed()
                .collect(Collectors.toList());
        System.out.println("Temperatures in Fahrenheit: " + fahrenheit);
        // Output: Temperatures in Fahrenheit: [32.0, 68.0, 98.6, 212.0, 14.0, 72.5]
    }
}
