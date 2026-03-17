package lambdas.solutions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Exercise5Solution.java
 *
 * Solutions for Exercise Set 5: Functional Interfaces with Collections.
 * Covers: List.sort() with Comparator lambdas, List.removeIf() with Predicate
 * lambdas, List.forEach() with Consumer lambdas, Map.forEach() with BiConsumer
 * lambdas, Map.computeIfAbsent(), and Map.replaceAll() with BiFunction.
 */
public class Exercise5Solution {

    public static void main(String[] args) {

        // --- Exercise 5.1: Sorting with Comparator Lambdas ---
        exercise5_1();

        // --- Exercise 5.2: Filtering and Transforming Lists ---
        exercise5_2();

        // --- Exercise 5.3: Map Operations with Lambdas ---
        exercise5_3();
    }

    static void exercise5_1() {
        System.out.println("=== Exercise 5.1: Sorting with Comparator Lambdas ===");

        List<String> base = List.of("cherry", "banana", "fig", "date", "elderberry", "apple");

        // Sort 1: alphabetical (natural string ordering).
        List<String> alpha = new ArrayList<>(base);
        alpha.sort((a, b) -> a.compareTo(b));
        System.out.println("alphabetical:       " + alpha);
        // Output: alphabetical:       [apple, banana, cherry, date, elderberry, fig]

        // Sort 2: by length ascending; alphabetical for strings of equal length.
        List<String> byLenAsc = new ArrayList<>(base);
        byLenAsc.sort((a, b) -> {
            int lenCmp = Integer.compare(a.length(), b.length());
            return lenCmp != 0 ? lenCmp : a.compareTo(b);
        });
        System.out.println("by length asc:      " + byLenAsc);
        // Output: by length asc:      [fig, date, apple, banana, cherry, elderberry]

        // Sort 3: by length descending.
        // Reverse the length comparison by swapping a and b in Integer.compare.
        List<String> byLenDesc = new ArrayList<>(base);
        byLenDesc.sort((a, b) -> {
            int lenCmp = Integer.compare(b.length(), a.length()); // reversed
            return lenCmp != 0 ? lenCmp : a.compareTo(b);
        });
        System.out.println("by length desc:     " + byLenDesc);
        // Output: by length desc:     [elderberry, banana, cherry, apple, date, fig]
    }

    static void exercise5_2() {
        System.out.println("\n=== Exercise 5.2: Filtering and Transforming Lists ===");

        List<String> products = List.of(
            "Widget Pro", "Gadget Mini", "Widget Basic", "Doohickey",
            "Gadget Max", "Thingamajig", "Widget Ultra"
        );

        // 1. removeIf: keep only products starting with "Widget".
        // removeIf removes elements for which the Predicate returns true,
        // so we negate: remove if it does NOT start with "Widget".
        List<String> widgets = new ArrayList<>(products);
        widgets.removeIf(name -> !name.startsWith("Widget"));
        System.out.println("widgets only: " + widgets);
        // Output: widgets only: [Widget Pro, Widget Basic, Widget Ultra]

        // 2. forEach: print each product name in upper case.
        // Consumer<String> — no return value, side effect only.
        products.forEach(name -> System.out.println(name.toUpperCase()));
        // Output:
        // WIDGET PRO
        // GADGET MINI
        // WIDGET BASIC
        // DOOHICKEY
        // GADGET MAX
        // THINGAMAJIG
        // WIDGET ULTRA

        // 3. Transform: build a new list with " - ON SALE" appended to each name.
        // A Function<String, String> is applied in a manual loop to build the result list.
        Function<String, String> appendSale = name -> name + " - ON SALE";
        List<String> onSale = new ArrayList<>();
        for (String product : products) {
            onSale.add(appendSale.apply(product));
        }
        System.out.println("on sale: " + onSale);
        // Output: on sale: [Widget Pro - ON SALE, Gadget Mini - ON SALE, Widget Basic - ON SALE,
        //                   Doohickey - ON SALE, Gadget Max - ON SALE, Thingamajig - ON SALE,
        //                   Widget Ultra - ON SALE]
    }

    static void exercise5_3() {
        System.out.println("\n=== Exercise 5.3: Map Operations with Lambdas ===");

        // LinkedHashMap preserves insertion order so the output is deterministic.
        Map<String, Integer> scores = new LinkedHashMap<>();
        scores.put("Alice",  91);
        scores.put("Bob",    74);
        scores.put("Carol",  85);
        scores.put("David",  60);
        scores.put("Eve",    95);

        // 1. Map.forEach with a BiConsumer lambda: (key, value) -> action.
        // Derive the letter grade from the score and print both.
        scores.forEach((name, score) -> {
            String grade = gradeLetter(score);
            System.out.printf("%-7s  %2d -> %s%n", name + ":", score, grade);
        });
        // Output:
        // Alice:    91 -> A
        // Bob:      74 -> C
        // Carol:    85 -> B
        // David:    60 -> D
        // Eve:      95 -> A

        // 2. computeIfAbsent: add Frank with a default score of 0 only if absent.
        // The Function receives the key and returns the default value to insert.
        scores.computeIfAbsent("Frank", key -> 0);
        System.out.println("Frank default score: " + scores.get("Frank"));
        // Output: Frank default score: 0

        // 3. replaceAll: apply a BiFunction<String, Integer, Integer> to every entry.
        // The function receives the key and current value; its return value replaces the value.
        // Add 5 bonus points to every score, capping at 100.
        BiFunction<String, Integer, Integer> addBonus = (name, score) -> Math.min(score + 5, 100);
        scores.replaceAll(addBonus);
        System.out.println("after bonus: " + scores);
        // Output: after bonus: {Alice=96, Bob=79, Carol=90, David=65, Eve=100, Frank=5}
    }

    /** Returns the letter grade corresponding to a numeric score. */
    static String gradeLetter(int score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        return "D";
    }
}
