package lambdas.solutions;

import java.util.ArrayList;
import java.util.List;

/**
 * Exercise6Solution.java
 *
 * Solutions for Exercise Set 6: Custom Functional Interfaces.
 * Covers: defining @FunctionalInterface annotations, generic functional interfaces,
 * using custom interfaces as method parameters, and chaining custom interfaces
 * with a manually written compose helper.
 */
public class Exercise6Solution {

    // --- Interfaces for Exercise 6.1 ---

    @FunctionalInterface
    interface Validator<T> {
        boolean validate(T value);
    }

    @FunctionalInterface
    interface Formatter<T> {
        String format(T value);
    }

    @FunctionalInterface
    interface Reducer<T> {
        T reduce(T accumulator, T next);
    }

    // --- Interface for Exercises 6.2 and 6.3 ---

    // A generic two-type-parameter functional interface: A -> B.
    // This is analogous to java.util.function.Function<A, B> but defined here
    // to demonstrate how to build your own.
    @FunctionalInterface
    interface Transformer<A, B> {
        B transform(A input);
    }

    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 6.1: Define and Use @FunctionalInterface ---
        exercise6_1();

        // --- Exercise 6.2: Generic Functional Interfaces ---
        exercise6_2();

        // --- Exercise 6.3: Chaining Custom Functional Interfaces ---
        exercise6_3();
    }

    static void exercise6_1() {
        System.out.println("=== Exercise 6.1: Custom @FunctionalInterface ===");

        // Validator<String>: checks whether the string is a plausible email address.
        // Rule: contains exactly one '@', and at least one '.' appears after the '@'.
        Validator<String> isValidEmail = email -> {
            int atIndex = email.indexOf('@');
            if (atIndex < 0 || email.indexOf('@', atIndex + 1) >= 0) return false; // not exactly one @
            String afterAt = email.substring(atIndex + 1);
            return afterAt.contains(".");
        };

        System.out.println("validate(user@example.com) = " + isValidEmail.validate("user@example.com")); // Output: true
        System.out.println("validate(not-an-email)     = " + isValidEmail.validate("not-an-email"));     // Output: false
        System.out.println("validate(bad@)             = " + isValidEmail.validate("bad@"));              // Output: false

        // Formatter<Double>: formats a double as a US currency string using String.format.
        // "$%,.2f" produces a dollar sign, comma-grouped thousands, and two decimal places.
        Formatter<Double> currencyFormat = value -> String.format("$%,.2f", value);

        System.out.println("format(1234.56) = " + currencyFormat.format(1234.56)); // Output: format(1234.56) = $1,234.56
        System.out.println("format(0.5)     = " + currencyFormat.format(0.5));     // Output: format(0.5)     = $0.50

        // Reducer<Integer>: combines an accumulator and the next element into a new accumulator.
        Reducer<Integer> sum = (accumulator, next) -> accumulator + next;

        // Use the reducer in a loop to compute the running total.
        int[] values = {3, 7, 2, 9, 1};
        int total = 0;
        for (int v : values) {
            total = sum.reduce(total, v);
        }
        System.out.println("reduce sum of [3, 7, 2, 9, 1] = " + total);
        // Output: reduce sum of [3, 7, 2, 9, 1] = 22
    }

    static void exercise6_2() {
        System.out.println("\n=== Exercise 6.2: Generic Functional Interfaces ===");

        // mapList: applies a Transformer to every element and collects results.
        // The method is generic in A (input type) and B (output type).

        // Transformer<String, Integer>: maps each string to its length.
        Transformer<String, Integer> lengthOf = s -> s.length();
        List<Integer> lengths = mapList(List.of("hello", "lambda", "java", "stream"), lengthOf);
        System.out.println("lengths: " + lengths);
        // Output: lengths: [5, 6, 4, 6]

        // Transformer<Integer, String>: converts small integers to English words.
        Transformer<Integer, String> toWords = n -> {
            if (n == 1) return "one";
            if (n == 2) return "two";
            if (n == 3) return "three";
            return "many";
        };
        List<String> words = mapList(List.of(1, 2, 5, 1, 9), toWords);
        System.out.println("words:   " + words);
        // Output: words:   [one, two, many, one, many]

        // Transformer<String, String>: reverses each string.
        Transformer<String, String> reverse = s -> new StringBuilder(s).reverse().toString();
        List<String> reversed = mapList(List.of("hello", "world", "java"), reverse);
        System.out.println("reversed: " + reversed);
        // Output: reversed: [olleh, dlrow, avaj]
    }

    static void exercise6_3() {
        System.out.println("\n=== Exercise 6.3: Chaining Custom Functional Interfaces ===");

        // chain(): composes two Transformers into a single Transformer.
        // first produces a B from an A; second produces a C from that B.
        // The returned Transformer goes directly from A to C.

        // Chain 1: String -> Integer (parseInt) then Integer -> Boolean (isEven).
        Transformer<String, Integer>  parseToInt = Integer::parseInt;
        Transformer<Integer, Boolean> isEven     = n -> n % 2 == 0;
        Transformer<String, Boolean>  isEvenStr  = chain(parseToInt, isEven);

        System.out.println("isEven(\"42\") = " + isEvenStr.transform("42")); // Output: true
        System.out.println("isEven(\"7\")  = " + isEvenStr.transform("7"));  // Output: false

        // Chain 2: String -> String (trim) then String -> String (toUpperCase).
        Transformer<String, String> trim     = String::trim;
        Transformer<String, String> toUpper  = String::toUpperCase;
        Transformer<String, String> trimAndUpper = chain(trim, toUpper);

        System.out.println("trimAndUpper(\"  hello  \") = " + trimAndUpper.transform("  hello  "));
        // Output: trimAndUpper("  hello  ") = HELLO

        // Chain 3: trim -> toUpperCase -> length -> isEven (four steps chained two at a time).
        Transformer<String, Integer>  trimUpperLength     = chain(trimAndUpper, String::length);
        Transformer<String, Boolean>  trimUpperLengthEven = chain(trimUpperLength, isEven);

        System.out.println("trimUpperLengthEven(\"  hello  \") = " + trimUpperLengthEven.transform("  hello  "));
        // "  hello  ".trim() = "hello" (5 chars) -> toUpperCase = "HELLO" -> length = 5 -> 5 % 2 == 0? false
        // Output: trimUpperLengthEven("  hello  ") = false

        System.out.println("trimUpperLengthEven(\"  hi  \") = " + trimUpperLengthEven.transform("  hi  "));
        // "  hi  ".trim() = "hi" (2 chars) -> toUpperCase = "HI" -> length = 2 -> 2 % 2 == 0? true
        // Output: trimUpperLengthEven("  hi  ") = true
    }

    // -------------------------------------------------------------------------
    // Static helper methods

    /**
     * Applies transformer to every element of list and returns a new list of results.
     * Demonstrates passing a custom functional interface as a method parameter.
     */
    static <A, B> List<B> mapList(List<A> list, Transformer<A, B> transformer) {
        List<B> result = new ArrayList<>();
        for (A element : list) {
            result.add(transformer.transform(element));
        }
        return result;
    }

    /**
     * Composes two Transformers into one: first applies "first", then passes the
     * result to "second". The returned Transformer converts A directly to C.
     *
     * This mirrors what Function.andThen() does, implemented manually to show
     * how chaining works under the hood.
     */
    static <A, B, C> Transformer<A, C> chain(Transformer<A, B> first, Transformer<B, C> second) {
        // The returned lambda captures both "first" and "second" from the enclosing scope.
        // Both references are effectively final — they are never reassigned.
        return input -> second.transform(first.transform(input));
    }
}
