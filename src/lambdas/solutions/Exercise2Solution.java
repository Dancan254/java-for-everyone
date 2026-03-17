package lambdas.solutions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Exercise2Solution.java
 *
 * Solutions for Exercise Set 2: Built-in Functional Interfaces.
 * Covers: Predicate<T>, Function<T,R>, Consumer<T>, Supplier<T>, and
 * BiFunction<T,U,R> — all drawn from java.util.function.
 */
public class Exercise2Solution {

    public static void main(String[] args) {

        // --- Exercise 2.1: Predicate ---
        exercise2_1();

        // --- Exercise 2.2: Function and Consumer ---
        exercise2_2();

        // --- Exercise 2.3: Supplier and BiFunction ---
        exercise2_3();
    }

    static void exercise2_1() {
        System.out.println("=== Exercise 2.1: Predicate ===");

        // Predicate<T>: T -> boolean. Used to test a condition.
        Predicate<Integer> isPositive = n -> n > 0;

        // isPalindrome: reverse the string using StringBuilder and compare.
        Predicate<String> isPalindrome = s -> s.equals(new StringBuilder(s).reverse().toString());

        // hasUpperCase: iterate over characters and test each with Character.isUpperCase.
        Predicate<String> hasUpperCase = s -> {
            for (char c : s.toCharArray()) {
                if (Character.isUpperCase(c)) return true;
            }
            return false;
        };

        System.out.println("isPositive(-5)    = " + isPositive.test(-5));   // Output: false
        System.out.println("isPositive(0)     = " + isPositive.test(0));    // Output: false
        System.out.println("isPositive(12)    = " + isPositive.test(12));   // Output: true

        System.out.println("isPalindrome(racecar) = " + isPalindrome.test("racecar")); // Output: true
        System.out.println("isPalindrome(level)   = " + isPalindrome.test("level"));   // Output: true
        System.out.println("isPalindrome(hello)   = " + isPalindrome.test("hello"));   // Output: false

        System.out.println("hasUpperCase(password) = " + hasUpperCase.test("password")); // Output: false
        System.out.println("hasUpperCase(Password) = " + hasUpperCase.test("Password")); // Output: true
        System.out.println("hasUpperCase(PASSWORD) = " + hasUpperCase.test("PASSWORD")); // Output: true
    }

    static void exercise2_2() {
        System.out.println("\n=== Exercise 2.2: Function and Consumer ===");

        // Function<T, R>: T -> R. Transforms a value of one type into another.
        // capitalize: upper-case the first char, lower-case the rest.
        Function<String, String> capitalize = s ->
            s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();

        // toBinary: convert integer to its binary string representation.
        Function<Integer, String> toBinary = n -> Integer.toBinaryString(n);

        // Consumer<T>: T -> void. Produces a side effect with no return value.
        // printFormatted: surrounds the string with square brackets.
        Consumer<String> printFormatted = s -> System.out.println("[" + s + "]");

        // Consumer<List<Integer>>: sums all elements and prints the result.
        Consumer<List<Integer>> printSum = list -> {
            int sum = 0;
            for (int n : list) sum += n;
            System.out.println("sum(" + list + ") = " + sum);
        };

        System.out.println("capitalize(hELLO wORLD) = " + capitalize.apply("hELLO wORLD"));
        // Output: capitalize(hELLO wORLD) = Hello world

        System.out.println("toBinary(42) = " + toBinary.apply(42));
        // Output: toBinary(42) = 101010

        printFormatted.accept("hello world");
        // Output: [hello world]

        printSum.accept(List.of(1, 2, 3, 4, 5));
        // Output: sum([1, 2, 3, 4, 5]) = 15
    }

    static void exercise2_3() {
        System.out.println("\n=== Exercise 2.3: Supplier and BiFunction ===");

        // Supplier<T>: () -> T. Produces a value without taking any input.
        // Each call to get() creates a completely independent new list.
        Supplier<List<String>> freshList = () -> new ArrayList<>();

        List<String> list1 = freshList.get();
        List<String> list2 = freshList.get();

        list1.add("alpha");
        list2.add("beta");

        // The two lists are independent objects — modifying one does not affect the other.
        System.out.println("list1: " + list1);  // Output: list1: [alpha]
        System.out.println("list2: " + list2);  // Output: list2: [beta]

        // BiFunction<T, U, R>: (T, U) -> R. Accepts two arguments and returns a result.
        // formatEntry: combines a key string and a value string into "key=value".
        BiFunction<String, String, String> formatEntry = (key, value) -> key + "=" + value;
        System.out.println("entry: " + formatEntry.apply("name", "Alice"));
        // Output: entry: name=Alice

        // power: raises the first argument to the power of the second.
        // Math.pow returns double; cast to int for an integer result.
        BiFunction<Integer, Integer, Integer> power = (base, exp) -> (int) Math.pow(base, exp);
        System.out.println("2^10 = " + power.apply(2, 10));
        // Output: 2^10 = 1024
    }
}
