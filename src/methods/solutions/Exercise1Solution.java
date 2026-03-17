package methods.solutions;

/**
 * Exercise1Solution.java
 *
 * Solutions for Exercise Set 1: Basic Method Creation.
 * Covers: return types, parameters, boolean logic, string manipulation,
 * division-by-zero handling, and the isPrime algorithm.
 */
public class Exercise1Solution {

    public static void main(String[] args) {

        // === Exercise 1.1: SimpleCalculator ===
        System.out.println("=== Exercise 1.1: SimpleCalculator ===");

        SimpleCalculator calc = new SimpleCalculator();

        System.out.println("add(10, 4)      = " + calc.add(10, 4));
        // Output: add(10, 4)      = 14

        System.out.println("subtract(10, 4) = " + calc.subtract(10, 4));
        // Output: subtract(10, 4) = 6

        System.out.println("multiply(10, 4) = " + calc.multiply(10, 4));
        // Output: multiply(10, 4) = 40

        System.out.println("divide(10, 4)   = " + calc.divide(10, 4));
        // Output: divide(10, 4)   = 2.5

        System.out.println("divide(10, 0)   = " + calc.divide(10, 0));
        // Output: divide(10, 0)   = Error: division by zero

        // === Exercise 1.2: NumberChecker ===
        System.out.println("\n=== Exercise 1.2: NumberChecker ===");

        NumberChecker nc = new NumberChecker();
        int[] samples = {0, 1, 2, 7, 12, -5};

        for (int n : samples) {
            System.out.printf(
                "%4d -> even=%-5b odd=%-5b prime=%-5b positive=%b%n",
                n,
                nc.isEven(n),
                nc.isOdd(n),
                nc.isPrime(n),
                nc.isPositive(n)
            );
        }
        // Output:
        //    0 -> even=true  odd=false prime=false positive=false
        //    1 -> even=false odd=true  prime=false positive=true
        //    2 -> even=true  odd=false prime=true  positive=true
        //    7 -> even=false odd=true  prime=true  positive=true
        //   12 -> even=true  odd=false prime=false positive=true
        //   -5 -> even=false odd=true  prime=false positive=false

        // === Exercise 1.3: StringHelper ===
        System.out.println("\n=== Exercise 1.3: StringHelper ===");

        StringHelper sh = new StringHelper();
        String sample = "hello world";

        System.out.println("reverseString(\"" + sample + "\") = \""
                + sh.reverseString(sample) + "\"");
        // Output: reverseString("hello world") = "dlrow olleh"

        System.out.println("countWords(\"" + sample + "\")    = "
                + sh.countWords(sample));
        // Output: countWords("hello world")    = 2

        System.out.println("toUpperCase(\"" + sample + "\")  = \""
                + sh.toUpperCase(sample) + "\"");
        // Output: toUpperCase("hello world")  = "HELLO WORLD"

        String pal  = "racecar";
        String npal = "java";
        System.out.println("isPalindrome(\"" + pal  + "\") = " + sh.isPalindrome(pal));
        // Output: isPalindrome("racecar") = true
        System.out.println("isPalindrome(\"" + npal + "\") = " + sh.isPalindrome(npal));
        // Output: isPalindrome("java") = false
    }

    // -------------------------------------------------------------------------
    // Inner classes
    // -------------------------------------------------------------------------

    /**
     * A minimal four-operation calculator.
     * divide() returns a String so it can carry an error message when the
     * divisor is zero without throwing an exception or using Double.NaN.
     */
    static class SimpleCalculator {

        int add(int a, int b) {
            return a + b;
        }

        int subtract(int a, int b) {
            return a - b;
        }

        int multiply(int a, int b) {
            return a * b;
        }

        // Returns a String to accommodate both a numeric result and an error message.
        String divide(double a, double b) {
            if (b == 0) {
                return "Error: division by zero";
            }
            return String.valueOf(a / b);
        }
    }

    /**
     * Checks numeric properties of integers.
     * isPrime uses trial division up to sqrt(n) — the standard O(sqrt(n)) approach.
     * Any composite number n must have a factor <= sqrt(n), so we only need to
     * check divisors up to that point.
     */
    static class NumberChecker {

        boolean isEven(int num) {
            return num % 2 == 0;
        }

        boolean isOdd(int num) {
            return num % 2 != 0;
        }

        boolean isPrime(int num) {
            if (num < 2) {
                return false; // 0 and 1 are not prime by definition
            }
            // Check every integer from 2 up to sqrt(num).
            // If none divide evenly into num, it is prime.
            for (int i = 2; i * i <= num; i++) {
                if (num % i == 0) {
                    return false;
                }
            }
            return true;
        }

        boolean isPositive(int num) {
            return num > 0; // 0 is not positive
        }
    }

    /**
     * Common string utility methods.
     * reverseString uses StringBuilder for O(n) reversal.
     * countWords trims first to avoid counting leading/trailing whitespace as words.
     * isPalindrome compares the string to its reverse after lowercasing.
     */
    static class StringHelper {

        String reverseString(String str) {
            return new StringBuilder(str).reverse().toString();
        }

        int countWords(String str) {
            String trimmed = str.trim();
            if (trimmed.isEmpty()) {
                return 0;
            }
            // split("\\s+") handles multiple consecutive spaces correctly.
            return trimmed.split("\\s+").length;
        }

        String toUpperCase(String str) {
            return str.toUpperCase();
        }

        boolean isPalindrome(String str) {
            String lower    = str.toLowerCase();
            String reversed = new StringBuilder(lower).reverse().toString();
            return lower.equals(reversed);
        }
    }
}
