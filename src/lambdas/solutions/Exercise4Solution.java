package lambdas.solutions;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Exercise4Solution.java
 *
 * Solutions for Exercise Set 4: Composing Functions.
 * Covers: Predicate.and(), Predicate.or(), Predicate.negate(),
 * Function.andThen(), and Function.compose().
 *
 * Key distinction:
 *   andThen(g) — applies g AFTER the current function:  f.andThen(g) => g(f(x))
 *   compose(g) — applies g BEFORE the current function: f.compose(g) => f(g(x))
 */
public class Exercise4Solution {

    public static void main(String[] args) {

        // --- Exercise 4.1: Predicate Composition ---
        exercise4_1();

        // --- Exercise 4.2: Function Composition with andThen() and compose() ---
        exercise4_2();

        // --- Exercise 4.3: Mixed Pipeline ---
        exercise4_3();
    }

    static void exercise4_1() {
        System.out.println("=== Exercise 4.1: Predicate Composition ===");

        // Base predicates — each tests one simple condition.
        Predicate<Integer> isEven           = n -> n % 2 == 0;
        Predicate<Integer> isPositive       = n -> n > 0;
        Predicate<Integer> isMultipleOfFive = n -> n % 5 == 0;

        // and(): both predicates must return true.
        Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);

        // or(): at least one predicate must return true.
        Predicate<Integer> isEvenOrMultipleOfFive = isEven.or(isMultipleOfFive);

        // negate(): flips the result of isEven, so the composite returns true for odd numbers.
        Predicate<Integer> isOdd = isEven.negate();

        int[] testValues = {-10, 0, 5, 6, 10, 15};

        System.out.println("isEvenAndPositive:");
        for (int v : testValues) {
            System.out.printf("  %3d -> %b%n", v, isEvenAndPositive.test(v));
        }
        // Output:
        // isEvenAndPositive:
        //   -10 -> false
        //     0 -> false
        //     5 -> false
        //     6 -> true
        //    10 -> true
        //    15 -> false

        System.out.println("isEvenOrMultipleOfFive:");
        for (int v : testValues) {
            System.out.printf("  %3d -> %b%n", v, isEvenOrMultipleOfFive.test(v));
        }
        // Output:
        // isEvenOrMultipleOfFive:
        //   -10 -> true   (even)
        //     0 -> true   (even and multiple of five)
        //     5 -> true   (multiple of five)
        //     6 -> true   (even)
        //    10 -> true   (even and multiple of five)
        //    15 -> true   (multiple of five)

        System.out.println("isOdd:");
        for (int v : testValues) {
            System.out.printf("  %3d -> %b%n", v, isOdd.test(v));
        }
        // Output:
        // isOdd:
        //   -10 -> false
        //     0 -> false
        //     5 -> true
        //     6 -> false
        //    10 -> false
        //    15 -> true
    }

    static void exercise4_2() {
        System.out.println("\n=== Exercise 4.2: Function Composition ===");

        // Individual transformation steps as Function instances.
        Function<String, String> trim        = String::trim;
        Function<String, String> toLowerCase = String::toLowerCase;
        Function<String, String> removeSpaces = s -> s.replace(" ", "_");
        Function<String, String> addPrefix    = s -> "tag_" + s;

        // andThen() chains left-to-right: each step receives the output of the previous one.
        // Order: trim -> toLowerCase -> removeSpaces -> addPrefix
        Function<String, String> normalizeAndThen =
            trim.andThen(toLowerCase).andThen(removeSpaces).andThen(addPrefix);

        // compose() chains right-to-left: the argument is applied first.
        // To get the same order (trim first, addPrefix last), compose in reverse:
        // addPrefix.compose(removeSpaces).compose(toLowerCase).compose(trim)
        Function<String, String> normalizeCompose =
            addPrefix.compose(removeSpaces).compose(toLowerCase).compose(trim);

        String input = "  Hello World  ";
        System.out.println("andThen result:  " + normalizeAndThen.apply(input));
        // Output: andThen result:  tag_hello_world
        System.out.println("compose result:  " + normalizeCompose.apply(input));
        // Output: compose result:  tag_hello_world
    }

    static void exercise4_3() {
        System.out.println("\n=== Exercise 4.3: Mixed Pipeline ===");

        // Build each step separately to keep the chain readable.
        Function<String, Integer>  parse      = Integer::parseInt;  // "7" -> 7
        Function<Integer, Integer> timesThree = n -> n * 3;         // 7 -> 21
        Function<Integer, Integer> minusOne   = n -> n - 1;         // 21 -> 20
        Function<Integer, String>  label      = n -> "value: " + n; // 20 -> "value: 20"

        // andThen() applies each step in the order written.
        Function<String, String> pipeline =
            parse.andThen(timesThree).andThen(minusOne).andThen(label);

        System.out.println("pipeline(\"7\")  = " + pipeline.apply("7"));
        // Output: pipeline("7")  = value: 20   (7*3-1 = 20)

        System.out.println("pipeline(\"0\")  = " + pipeline.apply("0"));
        // Output: pipeline("0")  = value: -1   (0*3-1 = -1)

        System.out.println("pipeline(\"-4\") = " + pipeline.apply("-4"));
        // Output: pipeline("-4") = value: -13  (-4*3-1 = -13)
    }
}
