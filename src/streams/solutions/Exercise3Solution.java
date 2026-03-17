package streams.solutions;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Exercise3Solution.java
 *
 * Solutions for Exercise Set 3: Terminal Operations.
 * Covers: forEach, count, reduce, findFirst, findAny, min, max,
 * anyMatch, allMatch, noneMatch, toList(), and short-circuit behavior.
 */
public class Exercise3Solution {

    public static void main(String[] args) {

        // --- Exercise 3.1: count, reduce, and forEach ---
        System.out.println("=== Exercise 3.1: count, reduce, and forEach ===");
        exercise1();

        // --- Exercise 3.2: findFirst, findAny, min, max, and matching ---
        System.out.println("\n=== Exercise 3.2: findFirst, min, max, and matching ===");
        exercise2();

        // --- Exercise 3.3: toList() and terminal short-circuiting ---
        System.out.println("\n=== Exercise 3.3: toList() and Short-Circuiting ===");
        exercise3();
    }

    // ---------------------------------------------------------------------------
    // Exercise 3.1
    // ---------------------------------------------------------------------------

    /**
     * Demonstrates count(), reduce() for string concatenation and integer product,
     * and forEach() over indexed positions using IntStream.range.
     */
    static void exercise1() {
        List<String> productNames = List.of(
                "Notebook", "Pen", "Stapler", "Binder", "Tape",
                "Scissors", "Highlighter", "Eraser", "Ruler", "Folder"
        );

        // 1. Count names longer than 5 characters.
        long longNames = productNames.stream()
                .filter(n -> n.length() > 5)
                .count();
        System.out.println("Names longer than 5 chars: " + longNames); // Output: 6

        // 2. Concatenate all names into a comma-separated string using reduce.
        String joined = productNames.stream()
                .reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b);
        System.out.println("All names joined: " + joined);
        // Output: All names joined: Notebook, Pen, Stapler, Binder, Tape, Scissors, Highlighter, Eraser, Ruler, Folder

        // 3. Product of a list of integers using reduce.
        List<Integer> values = List.of(2, 3, 4, 5, 6, 7, 2, 3);
        int product = values.stream()
                .reduce(1, (a, b) -> a * b);
        System.out.println("Integer product: " + product); // Output: Integer product: 15120

        // 4. Numbered list using IntStream.range — no external mutable state.
        System.out.println("Numbered product list:");
        IntStream.range(0, productNames.size())
                .forEach(i -> System.out.println("  " + (i + 1) + ". " + productNames.get(i)));
        // Output:
        //   1. Notebook
        //   2. Pen
        //   3. Stapler
        //   4. Binder
        //   5. Tape
        //   6. Scissors
        //   7. Highlighter
        //   8. Eraser
        //   9. Ruler
        //   10. Folder
    }

    // ---------------------------------------------------------------------------
    // Exercise 3.2
    // ---------------------------------------------------------------------------

    /**
     * Uses parallel lists of employee names and salaries to demonstrate
     * findFirst, min, max, anyMatch, allMatch, and noneMatch.
     */
    static void exercise2() {
        List<String> names = List.of(
                "Alice", "Marcus", "Diana", "Mohammed", "Sarah",
                "Liam", "Mia", "Carlos", "Nina", "Oscar"
        );
        List<Integer> salaries = List.of(
                87000, 54000, 91000, 76000, 43000,
                68000, 99000, 62000, 48000, 115000
        );

        // 1. First name starting with "M".
        Optional<String> firstM = names.stream()
                .filter(n -> n.startsWith("M"))
                .findFirst();
        System.out.println("First name starting with M: " + firstM.orElse("None found"));
        // Output: First name starting with M: Marcus

        // 2. Highest salary — find its index to look up the name.
        int maxSalary = salaries.stream()
                .max(Comparator.naturalOrder())
                .orElseThrow();
        int maxIndex = salaries.indexOf(maxSalary);
        System.out.println("Highest salary: " + names.get(maxIndex) + " ($" + maxSalary + ")");
        // Output: Highest salary: Oscar ($115000)

        // 3. Lowest salary.
        int minSalary = salaries.stream()
                .min(Comparator.naturalOrder())
                .orElseThrow();
        int minIndex = salaries.indexOf(minSalary);
        System.out.println("Lowest salary: " + names.get(minIndex) + " ($" + minSalary + ")");
        // Output: Lowest salary: Sarah ($43000)

        // 4. Any salary above 90000.
        boolean anyAbove90k = salaries.stream().anyMatch(s -> s > 90000);
        System.out.println("Any salary > 90000: " + anyAbove90k); // Output: true

        // 5. All salaries above 40000.
        boolean allAbove40k = salaries.stream().allMatch(s -> s > 40000);
        System.out.println("All salaries > 40000: " + allAbove40k); // Output: true

        // 6. No salary below 30000.
        boolean noneBelow30k = salaries.stream().noneMatch(s -> s < 30000);
        System.out.println("None below 30000: " + noneBelow30k); // Output: true
    }

    // ---------------------------------------------------------------------------
    // Exercise 3.3
    // ---------------------------------------------------------------------------

    /**
     * Demonstrates toList() (Java 16+), short-circuit evaluation with anyMatch and peek,
     * and findFirst on a filtered stream.
     */
    static void exercise3() {
        List<Integer> numbers = List.of(
                -12, 3, -5, 18, 7, -3, 22, 18, 55, -9,
                4, 63, -1, 8, 30, 18, -7, 44, 2, 11
        );

        // 1. Filter positives, sort descending, collect with toList().
        List<Integer> positiveDesc = numbers.stream()
                .filter(n -> n > 0)
                .sorted(Comparator.reverseOrder())
                .toList(); // Java 16+ shorthand; returns unmodifiable list
        System.out.println("Positive values (desc): " + positiveDesc);
        // Output: Positive values (desc): [63, 55, 44, 30, 22, 18, 18, 18, 11, 8, 7, 4, 3, 2]

        // 2. Short-circuit demonstration with peek.
        // Stream.iterate produces an infinite sequence. The 7th element is 7.
        // anyMatch stops as soon as n == 7 is found, so peek fires only 7 times.
        System.out.println("Elements examined before anyMatch finds 7:");
        boolean found = Stream.iterate(1, n -> n + 1)
                .peek(n -> System.out.print(n + " "))
                .anyMatch(n -> n == 7);
        System.out.println();
        System.out.println("anyMatch(n == 7): " + found);
        // Output:
        // Elements examined before anyMatch finds 7:
        // 1 2 3 4 5 6 7
        // anyMatch(n == 7): true

        // 3. findFirst even number greater than 50 from 30 integers.
        List<Integer> thirtyNums = List.of(
                11, 23, 37, 41, 53, 19, 67, 48, 82, 13,
                29, 71, 55, 60, 17, 43, 88, 31, 14, 77,
                92, 25, 38, 63, 50, 16, 45, 74, 9, 61
        );

        Optional<Integer> firstEvenAbove50 = thirtyNums.stream()
                .filter(n -> n % 2 == 0 && n > 50)
                .findFirst();
        System.out.println("First even number > 50: " + firstEvenAbove50.map(Object::toString).orElse("Not found"));
        // Output: First even number > 50: 82
    }
}
