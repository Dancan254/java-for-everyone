package lambdas.solutions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Exercise1Solution.java
 *
 * Solutions for Exercise Set 1: Lambda Syntax.
 * Covers: converting anonymous classes to lambdas, multi-line lambda bodies
 * with braces and explicit return, explicit parameter type annotations, and
 * defining a custom @FunctionalInterface.
 */
public class Exercise1Solution {

    // A custom functional interface: one abstract method, generic type parameter.
    // @FunctionalInterface tells the compiler to enforce the single-abstract-method rule.
    @FunctionalInterface
    interface Combiner<T> {
        T combine(T first, T second);
    }

    public static void main(String[] args) {

        // --- Exercise 1.1: Runnable and Comparator Lambdas ---
        exercise1_1();

        // --- Exercise 1.2: Multi-line Lambda Body ---
        exercise1_2();

        // --- Exercise 1.3: Explicit Parameter Types and Custom Interface ---
        exercise1_3();
    }

    static void exercise1_1() {
        System.out.println("=== Exercise 1.1: Runnable and Comparator Lambdas ===");

        // Lambda replacing the anonymous Runnable class.
        // () -> expression is the no-parameter form.
        Runnable printDate = () -> System.out.println("Today's date");
        printDate.run();
        // Output: Today's date

        // Lambda replacing the anonymous Comparator class.
        // (a, b) -> expression is the two-parameter form; types are inferred.
        Comparator<String> byLength = (a, b) -> Integer.compare(a.length(), b.length());

        List<String> words = new ArrayList<>(List.of("banana", "fig", "apple", "kiwi"));
        words.sort(byLength);
        System.out.println(words);
        // Output: [fig, kiwi, apple, banana]
    }

    static void exercise1_2() {
        System.out.println("\n=== Exercise 1.2: Multi-line Lambda Body ===");

        // Multi-line body: braces required, return statement required.
        // The first comparison by length is one statement; the ternary is the second.
        Comparator<String> byLengthThenAlpha = (a, b) -> {
            int byLength = Integer.compare(a.length(), b.length());
            return byLength != 0 ? byLength : a.compareTo(b);
        };

        List<String> fruits = new ArrayList<>(List.of("pear", "kiwi", "fig", "date", "plum"));
        fruits.sort(byLengthThenAlpha);
        System.out.println(fruits);
        // Output: [fig, date, kiwi, pear, plum]
        // Explanation: fig(3), then date/kiwi/pear/plum all have 4 chars -> alphabetical among them.
    }

    static void exercise1_3() {
        System.out.println("\n=== Exercise 1.3: Explicit Parameter Types and Custom Interface ===");

        // Combiner<Integer>: returns the larger of the two integers.
        // Parameter types are inferred — explicit types are optional but always allowed.
        Combiner<Integer> max = (a, b) -> a >= b ? a : b;
        System.out.println("max(8, 3) = " + max.combine(8, 3));
        // Output: max(8, 3) = 8

        // Combiner<String>: joins two strings with a hyphen.
        Combiner<String> join = (a, b) -> a + "-" + b;
        System.out.println("join(hello, world) = " + join.combine("hello", "world"));
        // Output: join(hello, world) = hello-world

        // Combiner<String> with explicit parameter type annotations.
        // Explicit types are required if you annotate one parameter — you must annotate all.
        Combiner<String> alphabeticalFirst = (String a, String b) -> a.compareTo(b) <= 0 ? a : b;
        System.out.println("first(banana, apple) = " + alphabeticalFirst.combine("banana", "apple"));
        // Output: first(banana, apple) = apple
    }
}
