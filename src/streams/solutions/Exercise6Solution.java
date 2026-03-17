package streams.solutions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Exercise6Solution.java
 *
 * Solutions for Exercise Set 6: Stream Chaining.
 * Covers multi-step pipelines that combine multiple intermediate and terminal
 * operations to solve realistic data processing problems:
 *   - Word frequency analysis on raw paragraph text
 *   - Order processing report with revenue calculations and grouping
 *   - Student grade report with GPA ranking, partitioning, and per-year counts
 */
public class Exercise6Solution {

    record Order(String orderId, String customer, String product, int quantity, double unitPrice) {
        double lineTotal() { return quantity * unitPrice; }
    }

    record Student(String name, String major, int year, double gpa) {}

    public static void main(String[] args) {

        // --- Exercise 6.1: Word Frequency Analysis ---
        System.out.println("=== Exercise 6.1: Word Frequency Analysis ===");
        exercise1();

        // --- Exercise 6.2: Order Processing Report ---
        System.out.println("\n=== Exercise 6.2: Order Processing Report ===");
        exercise2();

        // --- Exercise 6.3: Student Grade Report ---
        System.out.println("\n=== Exercise 6.3: Student Grade Report ===");
        exercise3();
    }

    // ---------------------------------------------------------------------------
    // Exercise 6.1
    // ---------------------------------------------------------------------------

    /**
     * Tokenizes a paragraph, groups words by first letter with counts, then finds
     * the 5 most frequently occurring words in a second pipeline on the same source.
     */
    static void exercise1() {
        String paragraph =
                "Streams make data processing expressive and readable. " +
                "Filter and map are the most common stream operations. " +
                "FlatMap is essential when each element produces multiple results. " +
                "Readable code is easier to maintain and to understand. " +
                "Operations on streams are lazy until a terminal operation runs. " +
                "The terminal operation triggers execution of the entire pipeline.";

        // Pipeline 1: group words by first letter with count, sorted by letter.
        String[] rawTokens = paragraph.split("[\\s.,!?;:\"'()-]+");

        Map<Character, Long> countByFirstLetter = Arrays.stream(rawTokens)
                .map(String::toLowerCase)
                .filter(w -> !w.isEmpty())
                .collect(Collectors.groupingBy(
                        w -> w.charAt(0),
                        Collectors.counting()
                ));

        System.out.println("Word count grouped by first letter:");
        countByFirstLetter.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println("  " + e.getKey() + ": " + e.getValue()));
        // Output (abbreviated):
        //   a: 5
        //   c: 3
        //   d: 2
        //   e: 7
        //   ...

        // Pipeline 2: top 5 most frequent words.
        System.out.println("\nTop 5 most frequent words:");
        Arrays.stream(rawTokens)
                .map(String::toLowerCase)
                .filter(w -> !w.isEmpty())
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> System.out.println("  \"" + e.getKey() + "\": " + e.getValue()));
        // Output:
        //   "and": 3
        //   "are": 2
        //   "the": 2
        //   "to": 2
        //   "streams": 2
    }

    // ---------------------------------------------------------------------------
    // Exercise 6.2
    // ---------------------------------------------------------------------------

    /**
     * Processes a list of orders to produce five report sections: total revenue,
     * per-customer revenue sorted descending, top customer, filtered orders for
     * one customer, and the top 3 products by units sold.
     */
    static void exercise2() {
        List<Order> orders = List.of(
                new Order("ORD-001", "Alice",   "Laptop",      2, 999.99),
                new Order("ORD-002", "Bob",     "Headphones",  3, 149.99),
                new Order("ORD-003", "Alice",   "USB Hub",     5,  39.99),
                new Order("ORD-004", "Carol",   "Smartphone",  1, 699.99),
                new Order("ORD-005", "Dave",    "Laptop",      1, 999.99),
                new Order("ORD-006", "Bob",     "Webcam",      2,  89.99),
                new Order("ORD-007", "Carol",   "Headphones",  4, 149.99),
                new Order("ORD-008", "Alice",   "Smartphone",  1, 699.99),
                new Order("ORD-009", "Dave",    "USB Hub",     3,  39.99),
                new Order("ORD-010", "Eve",     "Webcam",      1,  89.99),
                new Order("ORD-011", "Eve",     "Laptop",      1, 999.99),
                new Order("ORD-012", "Bob",     "Smartphone",  2, 699.99)
        );

        // 1. Total revenue across all orders.
        double totalRevenue = orders.stream()
                .mapToDouble(Order::lineTotal)
                .sum();
        System.out.printf("Total revenue: $%.2f%n", totalRevenue);
        // Output: Total revenue: $9999.79

        // 2. Revenue per customer, sorted descending.
        Map<String, Double> revenueByCustomer = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::customer,
                        Collectors.summingDouble(Order::lineTotal)
                ));

        System.out.println("\nRevenue per customer (desc):");
        revenueByCustomer.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(e -> System.out.printf("  %-8s $%.2f%n", e.getKey(), e.getValue()));
        // Output:
        //   Bob      $2129.93
        //   Alice    $2939.93
        //   Carol    $1299.95
        //   Eve      $1089.98
        //   Dave     $1119.96

        // 3. Customer with the highest total revenue.
        revenueByCustomer.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(e -> System.out.printf("%nTop customer: %s ($%.2f)%n", e.getKey(), e.getValue()));
        // Output:
        // Top customer: Alice ($2939.93)

        // 4. All orders for "Bob", sorted by unit price descending.
        System.out.println("\nBob's orders (sorted by unit price desc):");
        orders.stream()
                .filter(o -> o.customer().equals("Bob"))
                .sorted(Comparator.comparingDouble(Order::unitPrice).reversed())
                .forEach(o -> System.out.printf("  %-12s qty=%d  total=$%.2f%n",
                        o.product(), o.quantity(), o.lineTotal()));
        // Output:
        //   Smartphone   qty=2  total=$1399.98
        //   Headphones   qty=3  total=$449.97
        //   Webcam       qty=2  total=$179.98

        // 5. Top 3 products by total units sold.
        System.out.println("\nTop 3 products by units sold:");
        orders.stream()
                .collect(Collectors.groupingBy(
                        Order::product,
                        Collectors.summingInt(Order::quantity)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .forEach(e -> System.out.println("  " + e.getKey() + ": " + e.getValue() + " units"));
        // Output:
        //   Headphones: 7 units
        //   USB Hub: 8 units
        //   Smartphone: 4 units
    }

    // ---------------------------------------------------------------------------
    // Exercise 6.3
    // ---------------------------------------------------------------------------

    /**
     * Produces a multi-section student grade report using filter, sorted,
     * groupingBy with averagingDouble and maxBy downstream collectors,
     * partitioningBy, and a year-level count map.
     */
    static void exercise3() {
        List<Student> students = List.of(
                new Student("Alice Chen",      "Computer Science", 3, 3.92),
                new Student("Bob Okafor",      "Computer Science", 1, 2.85),
                new Student("Carol Reyes",     "Computer Science", 2, 3.71),
                new Student("Dave Kim",        "Computer Science", 4, 3.45),
                new Student("Eve Patel",       "Mathematics",      2, 3.88),
                new Student("Frank Mueller",   "Mathematics",      3, 3.55),
                new Student("Grace Liu",       "Mathematics",      1, 2.60),
                new Student("Hank Johansson",  "Mathematics",      4, 3.78),
                new Student("Ivy Nakamura",    "Physics",          1, 3.95),
                new Student("James Osei",      "Physics",          2, 3.40),
                new Student("Kara Singh",      "Physics",          3, 3.82),
                new Student("Leo Ferreira",    "Physics",          4, 2.95),
                new Student("Maya Dubois",     "Computer Science", 2, 3.65),
                new Student("Noah Andersen",   "Mathematics",      3, 3.12)
        );

        // 1. Students with GPA > 3.5, sorted by GPA descending.
        System.out.println("Honor-track students (GPA > 3.5, sorted desc):");
        students.stream()
                .filter(s -> s.gpa() > 3.5)
                .sorted(Comparator.comparingDouble(Student::gpa).reversed())
                .forEach(s -> System.out.printf("  %-20s %.2f%n", s.name(), s.gpa()));
        // Output:
        //   Ivy Nakamura         3.95
        //   Alice Chen           3.92
        //   Eve Patel            3.88
        //   Kara Singh           3.82
        //   Hank Johansson       3.78
        //   Carol Reyes          3.71
        //   Maya Dubois          3.65
        //   Frank Mueller        3.55

        // 2. Average GPA per major.
        System.out.println("\nAverage GPA per major:");
        students.stream()
                .collect(Collectors.groupingBy(
                        Student::major,
                        Collectors.averagingDouble(Student::gpa)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.printf("  %-18s %.2f%n", e.getKey(), e.getValue()));
        // Output:
        //   Computer Science   3.52
        //   Mathematics        3.19
        //   Physics            3.53

        // 3. Top student per major using groupingBy with maxBy downstream.
        System.out.println("\nTop student per major:");
        students.stream()
                .collect(Collectors.groupingBy(
                        Student::major,
                        Collectors.maxBy(Comparator.comparingDouble(Student::gpa))
                ))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> e.getValue().ifPresent(s ->
                        System.out.printf("  %-18s %s (%.2f)%n", e.getKey(), s.name(), s.gpa())));
        // Output:
        //   Computer Science   Alice Chen (3.92)
        //   Mathematics        Hank Johansson (3.78)
        //   Physics            Ivy Nakamura (3.95)

        // 4. Partition into honor roll (GPA >= 3.7) and not.
        Map<Boolean, List<Student>> partition = students.stream()
                .collect(Collectors.partitioningBy(s -> s.gpa() >= 3.7));

        String honorNames = partition.get(true).stream()
                .map(Student::name)
                .collect(Collectors.joining(", "));

        System.out.println("\nHonor roll (GPA >= 3.7): " + partition.get(true).size() + " students");
        System.out.println("  " + honorNames);
        System.out.println("Not on honor roll: " + partition.get(false).size() + " students");
        // Output:
        // Honor roll (GPA >= 3.7): 6 students
        //   Alice Chen, Carol Reyes, Eve Patel, Hank Johansson, Ivy Nakamura, Kara Singh
        // Not on honor roll: 8 students

        // 5. Number of students per year level (1-4), sorted by year.
        System.out.println("\nStudents per year:");
        students.stream()
                .collect(Collectors.groupingBy(Student::year, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> System.out.println("  Year " + e.getKey() + ": " + e.getValue() + " student(s)"));
        // Output:
        //   Year 1: 3 student(s)
        //   Year 2: 4 student(s)
        //   Year 3: 4 student(s)
        //   Year 4: 3 student(s)
    }
}
