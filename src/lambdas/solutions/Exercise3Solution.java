package lambdas.solutions;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Exercise3Solution.java
 *
 * Solutions for Exercise Set 3: Method References.
 * Covers all four method reference forms:
 *   1. Static method reference:          ClassName::staticMethod
 *   2. Bound instance method reference:  object::instanceMethod
 *   3. Unbound instance method reference: ClassName::instanceMethod
 *   4. Constructor reference:            ClassName::new
 */
public class Exercise3Solution {

    public static void main(String[] args) {

        // --- Exercise 3.1: Static Method References ---
        exercise3_1();

        // --- Exercise 3.2: Bound and Unbound Instance Method References ---
        exercise3_2();

        // --- Exercise 3.3: Constructor References ---
        exercise3_3();
    }

    static void exercise3_1() {
        System.out.println("=== Exercise 3.1: Static Method References ===");

        // Static method reference: ClassName::staticMethod
        // The lambda s -> Integer.parseInt(s) passes its argument directly to a static method.
        // The method reference Integer::parseInt does the same thing, more concisely.
        Function<String, Integer> parseInt = Integer::parseInt;
        System.out.println("parseInt(\"255\")    = " + parseInt.apply("255"));
        // Output: parseInt("255")    = 255

        // Math::abs is a static method — Math.abs(d) — that accepts one double.
        Function<Double, Double> absoluteVal = Math::abs;
        System.out.println("abs(-3.14)         = " + absoluteVal.apply(-3.14));
        // Output: abs(-3.14)         = 3.14

        // Integer::toBinaryString is a static method that takes one int argument.
        Function<Integer, String> toBinaryStr = Integer::toBinaryString;
        System.out.println("toBinary(255)      = " + toBinaryStr.apply(255));
        // Output: toBinary(255)      = 11111111

        // System.out.println is an instance method on the bound object System.out.
        // This is a bound reference because the instance (System.out) is fixed.
        Consumer<String> printLine = System.out::println;
        printLine.accept("method reference");
        // Output: method reference
    }

    static void exercise3_2() {
        System.out.println("\n=== Exercise 3.2: Bound and Unbound Instance Method References ===");

        // --- Part A: Bound instance method references ---
        // The instance is determined before the lambda is written.
        // The reference captures that specific instance.

        String separator = "---";

        // separator.toUpperCase() — the receiver is the captured separator object.
        Supplier<String> upperSeparator = separator::toUpperCase;
        System.out.println("separator upper: " + upperSeparator.get());
        // Output: separator upper: ---
        // (--- is already all non-letter characters so toUpperCase has no visual effect)

        // "Java"::equals would only test equality with "Java".
        // Here we want to test whether any string starts with "Java",
        // so we capture the prefix string and call its startsWith indirectly.
        String javaPrefix = "Java";
        Function<String, Boolean> startsWithJava = javaPrefix::equals;
        // The above tests for exact equality — use startsWith for the prefix check instead:
        Function<String, Boolean> startsWithJavaPrefix = s -> s.startsWith(javaPrefix);
        // javaPrefix is effectively final — captured correctly.
        System.out.println("startsWithJava(\"Java is great\")   = " + startsWithJavaPrefix.apply("Java is great"));
        // Output: startsWithJava("Java is great")   = true
        System.out.println("startsWithJava(\"Python is great\") = " + startsWithJavaPrefix.apply("Python is great"));
        // Output: startsWithJava("Python is great") = false

        // --- Part B: Unbound instance method references ---
        // The instance is NOT captured ahead of time.
        // The lambda receives an instance of the class as its first argument.

        // String::toLowerCase — the String instance is supplied when apply() is called.
        Function<String, String> lower = String::toLowerCase;
        System.out.println("lower(\"HELLO\") = " + lower.apply("HELLO"));
        // Output: lower("HELLO") = hello

        // String::length — no extra arguments; the String itself is the receiver.
        Function<String, Integer> length = String::length;
        System.out.println("length(\"lambda\") = " + length.apply("lambda"));
        // Output: length("lambda") = 6

        // String::trim — strips leading/trailing whitespace from the supplied String.
        Function<String, String> trim = String::trim;
        System.out.println("trim(\"  hello  \") = " + trim.apply("  hello  "));
        // Output: trim("  hello  ") = hello
    }

    static void exercise3_3() {
        System.out.println("\n=== Exercise 3.3: Constructor References ===");

        // Constructor reference: ClassName::new
        // Replaces a lambda whose entire body is "new ClassName(args)".

        // ArrayList::new calls the no-argument ArrayList constructor.
        Supplier<ArrayList<String>> makeList = ArrayList::new;
        ArrayList<String> list = makeList.get();
        list.add("hello");
        list.add("world");
        System.out.println("list: " + list);
        // Output: list: [hello, world]

        // StringBuilder::new with a String argument calls StringBuilder(String).
        Function<String, StringBuilder> makeBuilder = StringBuilder::new;
        StringBuilder sb = makeBuilder.apply("built");
        System.out.println("builder: " + sb);
        // Output: builder: built

        // int[]::new — constructor reference for a primitive array.
        // The Function receives the desired length and returns a new int[] of that size.
        Function<Integer, int[]> makeArray = int[]::new;
        int[] arr = makeArray.apply(5);
        System.out.println("array length: " + arr.length);
        // Output: array length: 5
    }
}
