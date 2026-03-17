package streams.solutions;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Exercise4Solution.java
 *
 * Solutions for Exercise Set 4: Collectors.
 * Covers: toList, toSet, toMap (including merge function), joining,
 * counting, summingInt, averagingDouble, groupingBy (with downstream
 * collectors), and partitioningBy.
 */
public class Exercise4Solution {

    record Product(String name, String category, double price) {}

    public static void main(String[] args) {

        // --- Exercise 4.1: toList, toSet, and toMap ---
        System.out.println("=== Exercise 4.1: toList, toSet, and toMap ===");
        exercise1();

        // --- Exercise 4.2: joining, counting, summingInt, averagingDouble ---
        System.out.println("\n=== Exercise 4.2: joining, counting, summingInt, averagingDouble ===");
        exercise2();

        // --- Exercise 4.3: groupingBy and partitioningBy ---
        System.out.println("\n=== Exercise 4.3: groupingBy and partitioningBy ===");
        exercise3();
    }

    // ---------------------------------------------------------------------------
    // Exercise 4.1
    // ---------------------------------------------------------------------------

    /**
     * Collects a list with duplicates into a List (preserving all), a Set (deduplicating),
     * a Map<String, Integer> of name to length, and a Map with a merge function that
     * handles duplicate keys by keeping the longer length value.
     */
    static void exercise1() {
        List<String> products = List.of(
                "Monitor", "Keyboard", "Mouse", "Monitor",
                "Headset", "Webcam", "Mouse", "Keyboard", "Cable", "Dock"
        );

        // 1. toList — preserves duplicates and insertion order.
        List<String> asList = products.stream().collect(Collectors.toList());
        System.out.println("toList size (with duplicates): " + asList.size()); // Output: 10

        // 2. toSet — removes duplicates; size reflects unique elements.
        Set<String> asSet = products.stream().collect(Collectors.toSet());
        System.out.println("toSet size (unique only): " + asSet.size()); // Output: 7

        // 3. toMap — name -> character length; fails on duplicate keys without a merge function.
        //    Use distinct() first to ensure unique keys here.
        Map<String, Integer> nameLengths = products.stream()
                .distinct()
                .collect(Collectors.toMap(
                        name -> name,
                        String::length
                ));
        System.out.println("toMap (name -> length): " + nameLengths);
        // Output: toMap (name -> length): {Dock=4, Webcam=6, Cable=5, Mouse=5, Keyboard=8, Monitor=7, Headset=7}

        // 4. toMap with a merge function — when a duplicate key is encountered,
        //    keep the larger of the two length values (identical here, but demonstrates the pattern).
        Map<String, Integer> nameLengthsMerged = products.stream()
                .collect(Collectors.toMap(
                        name -> name,
                        String::length,
                        Math::max  // merge function: keep the greater value on key collision
                ));
        System.out.println("toMap with merge (max length on collision): " + nameLengthsMerged);
        // Output: toMap with merge (max length on collision): {Dock=4, Webcam=6, Cable=5, Mouse=5, Keyboard=8, Monitor=7, Headset=7}
    }

    // ---------------------------------------------------------------------------
    // Exercise 4.2
    // ---------------------------------------------------------------------------

    /**
     * Demonstrates Collectors.joining with all three variants, counting with a filter,
     * summingInt, and averagingDouble on a list of city names.
     */
    static void exercise2() {
        List<String> cities = List.of(
                "Amsterdam", "Barcelona", "Cairo", "Dubai",
                "Edinburgh", "Florence", "Geneva", "Helsinki"
        );

        // 1. Simple delimiter join.
        String csv = cities.stream().collect(Collectors.joining(", "));
        System.out.println("Joined (csv): " + csv);
        // Output: Joined (csv): Amsterdam, Barcelona, Cairo, Dubai, Edinburgh, Florence, Geneva, Helsinki

        // 2. Join with delimiter, prefix, and suffix.
        String formatted = cities.stream()
                .collect(Collectors.joining(" | ", "Cities: [", "]"));
        System.out.println("Joined (formatted): " + formatted);
        // Output: Joined (formatted): Cities: [Amsterdam | Barcelona | Cairo | Dubai | Edinburgh | Florence | Geneva | Helsinki]

        // 3. counting() — count names longer than 6 characters.
        long longCityCount = cities.stream()
                .filter(c -> c.length() > 6)
                .collect(Collectors.counting());
        System.out.println("Cities with > 6 chars: " + longCityCount); // Output: 5

        // 4. summingInt — total characters across all names.
        int totalChars = cities.stream()
                .collect(Collectors.summingInt(String::length));
        System.out.println("Total character count: " + totalChars); // Output: 56

        // 5. averagingDouble — average name length.
        double avgLength = cities.stream()
                .collect(Collectors.averagingDouble(String::length));
        System.out.printf("Average name length: %.2f%n", avgLength); // Output: Average name length: 7.00
    }

    // ---------------------------------------------------------------------------
    // Exercise 4.3
    // ---------------------------------------------------------------------------

    /**
     * Uses groupingBy (with basic, counting, and averagingDouble downstream collectors)
     * and partitioningBy on a Product list to demonstrate structured collection strategies.
     */
    static void exercise3() {
        List<Product> products = List.of(
                new Product("Laptop",        "Electronics", 999.99),
                new Product("Smartphone",    "Electronics", 699.99),
                new Product("Headphones",    "Electronics", 149.99),
                new Product("USB Hub",       "Electronics",  39.99),
                new Product("Blazer",        "Clothing",    189.99),
                new Product("Jeans",         "Clothing",     59.99),
                new Product("T-Shirt",       "Clothing",     24.99),
                new Product("Running Shoes", "Clothing",     89.99),
                new Product("Olive Oil",     "Food",          8.99),
                new Product("Coffee Beans",  "Food",         14.49)
        );

        // 1. groupingBy(category) — Map<String, List<Product>>.
        Map<String, List<Product>> byCategory = products.stream()
                .collect(Collectors.groupingBy(Product::category));

        System.out.println("Products by category:");
        byCategory.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> {
                    String names = e.getValue().stream()
                            .map(Product::name)
                            .collect(Collectors.joining(", "));
                    System.out.println("  " + e.getKey() + ": " + names);
                });
        // Output:
        //   Clothing: Blazer, Jeans, T-Shirt, Running Shoes
        //   Electronics: Laptop, Smartphone, Headphones, USB Hub
        //   Food: Olive Oil, Coffee Beans

        // 2. groupingBy with counting() downstream.
        Map<String, Long> countPerCategory = products.stream()
                .collect(Collectors.groupingBy(Product::category, Collectors.counting()));
        System.out.println("Count per category: " + countPerCategory);
        // Output: Count per category: {Electronics=4, Clothing=4, Food=2}

        // 3. groupingBy with averagingDouble(price) downstream.
        Map<String, Double> avgPricePerCategory = products.stream()
                .collect(Collectors.groupingBy(
                        Product::category,
                        Collectors.averagingDouble(Product::price)
                ));
        System.out.println("Average price per category:");
        avgPricePerCategory.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.printf("  %-12s $%.2f%n", e.getKey(), e.getValue()));
        // Output:
        //   Clothing     $91.24
        //   Electronics  $472.49
        //   Food         $11.74

        // 4. partitioningBy — split into >= $50 and < $50.
        Map<Boolean, List<Product>> partitioned = products.stream()
                .collect(Collectors.partitioningBy(p -> p.price() >= 50.0));

        String expensive = partitioned.get(true).stream()
                .map(Product::name)
                .collect(Collectors.joining(", "));
        String budget = partitioned.get(false).stream()
                .map(Product::name)
                .collect(Collectors.joining(", "));

        System.out.println("partitioningBy (price >= $50):");
        System.out.println("  >= $50: " + expensive);
        System.out.println("  < $50:  " + budget);
        // Output:
        //   >= $50: Laptop, Smartphone, Headphones, Blazer, Jeans, Running Shoes
        //   < $50:  USB Hub, T-Shirt, Olive Oil, Coffee Beans
    }
}
