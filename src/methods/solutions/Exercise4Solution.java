package methods.solutions;

/**
 * Exercise4Solution.java
 *
 * Solutions for Exercise Set 4: Recursive Methods - Basic.
 * Covers: factorial, exponentiation, triangular-number sum, Fibonacci,
 * and string recursion (reverse, character count, palindrome check).
 *
 * Every recursive method here follows the same two-part structure:
 *   1. Base case  — the simplest input whose answer is known directly.
 *   2. Recursive case — express the answer in terms of a smaller sub-problem.
 */
public class Exercise4Solution {

    public static void main(String[] args) {

        // === Exercise 4.1: Basic Recursion ===
        System.out.println("=== Exercise 4.1: Basic Recursion ===");

        // factorial
        // Call trace for factorial(4):
        //   factorial(4) = 4 * factorial(3)
        //                        = 3 * factorial(2)
        //                              = 2 * factorial(1)
        //                                    = 1           <- base case
        //   Returns: 1 -> 2 -> 6 -> 24
        for (int n = 0; n <= 6; n++) {
            System.out.println("factorial(" + n + ") = " + factorial(n));
        }
        // Output:
        // factorial(0) = 1
        // factorial(1) = 1
        // factorial(2) = 2
        // factorial(3) = 6
        // factorial(4) = 24
        // factorial(5) = 120
        // factorial(6) = 720

        // power
        // Call trace for power(2, 4):
        //   power(2,4) = 2 * power(2,3)
        //                      = 2 * power(2,2)
        //                            = 2 * power(2,1)
        //                                  = 2 * power(2,0)
        //                                        = 1        <- base case
        //   Returns: 1 -> 2 -> 4 -> 8 -> 16
        System.out.println("\npower(2, 0) = " + power(2, 0));
        // Output: power(2, 0) = 1
        System.out.println("power(2, 4) = " + power(2, 4));
        // Output: power(2, 4) = 16
        System.out.println("power(3, 3) = " + power(3, 3));
        // Output: power(3, 3) = 27

        // sumN
        // Call trace for sumN(4):
        //   sumN(4) = 4 + sumN(3)
        //                   = 3 + sumN(2)
        //                         = 2 + sumN(1)
        //                               = 1        <- base case
        //   Returns: 1 -> 3 -> 6 -> 10
        System.out.println("\nsumN(1) = " + sumN(1));
        // Output: sumN(1) = 1
        System.out.println("sumN(5) = " + sumN(5));
        // Output: sumN(5) = 15
        System.out.println("sumN(10)= " + sumN(10));
        // Output: sumN(10)= 55

        // === Exercise 4.2: Fibonacci Sequence ===
        System.out.println("\n=== Exercise 4.2: Fibonacci Sequence ===");

        // Call trace for fibonacci(5):
        //   fib(5) = fib(4)              + fib(3)
        //          = (fib(3)+fib(2))     + (fib(2)+fib(1))
        //          = ((fib(2)+fib(1))+fib(2)) + ...
        //   Base cases: fib(0)=0, fib(1)=1
        //   fib(5) = 5
        System.out.print("Fibonacci sequence (n=0..9): ");
        for (int n = 0; n <= 9; n++) {
            System.out.print(fibonacci(n) + (n < 9 ? ", " : ""));
        }
        System.out.println();
        // Output: Fibonacci sequence (n=0..9): 0, 1, 1, 2, 3, 5, 8, 13, 21, 34

        // === Exercise 4.3: String Recursion ===
        System.out.println("\n=== Exercise 4.3: String Recursion ===");

        // reverseString
        // Call trace for reverseString("abc"):
        //   reverseString("abc") = reverseString("bc") + "a"
        //   reverseString("bc")  = reverseString("c")  + "b"
        //   reverseString("c")   = "c"                  <- base case
        //   Returns: "c" -> "cb" -> "cba"
        String word = "recursion";
        System.out.println("reverseString(\"" + word + "\") = \""
                + reverseString(word) + "\"");
        // Output: reverseString("recursion") = "noisrucer"

        // countCharacter
        // Each call checks the first character, then recurses on the tail.
        System.out.println("countCharacter(\"mississippi\", 's') = "
                + countCharacter("mississippi", 's'));
        // Output: countCharacter("mississippi", 's') = 4

        // isPalindrome
        // Each call compares the outermost characters, then recurses on the inner substring.
        System.out.println("isPalindrome(\"racecar\") = " + isPalindrome("racecar"));
        // Output: isPalindrome("racecar") = true
        System.out.println("isPalindrome(\"hello\")   = " + isPalindrome("hello"));
        // Output: isPalindrome("hello")   = false
    }

    // -------------------------------------------------------------------------
    // Recursive methods
    // -------------------------------------------------------------------------

    /**
     * Returns n! (n factorial).
     * Base case: 0! = 1 and 1! = 1 (by mathematical definition).
     * Recursive case: n! = n * (n-1)!
     */
    static long factorial(int n) {
        if (n <= 1) {
            return 1; // base case: 0! = 1, 1! = 1
        }
        return n * factorial(n - 1);
    }

    /**
     * Returns base raised to the power of exponent.
     * Base case: any number to the power of 0 is 1.
     * Recursive case: base^exp = base * base^(exp-1)
     */
    static long power(int base, int exponent) {
        if (exponent == 0) {
            return 1; // base case: x^0 = 1 for all x
        }
        return base * power(base, exponent - 1);
    }

    /**
     * Returns 1 + 2 + 3 + ... + n (the nth triangular number).
     * Base case: sum of numbers from 1 to 1 is 1.
     * Recursive case: sumN(n) = n + sumN(n-1)
     */
    static int sumN(int n) {
        if (n <= 1) {
            return 1; // base case
        }
        return n + sumN(n - 1);
    }

    /**
     * Returns the nth Fibonacci number.
     * Base cases: fib(0) = 0, fib(1) = 1.
     * Recursive case: fib(n) = fib(n-1) + fib(n-2)
     *
     * Note: this naive implementation is O(2^n). For large n, memoization
     * or an iterative approach is required to avoid redundant recomputation.
     */
    static int fibonacci(int n) {
        if (n == 0) {
            return 0; // base case
        }
        if (n == 1) {
            return 1; // base case
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    /**
     * Reverses a string recursively.
     * Base case: a string of length 0 or 1 is already reversed.
     * Recursive case: reverse the tail, then append the first character.
     *
     * Call trace for "abc":
     *   reverseString("abc") = reverseString("bc") + 'a'
     *   reverseString("bc")  = reverseString("c")  + 'b'
     *   reverseString("c")   = "c"
     *   Returns: "c" -> "cb" -> "cba"
     */
    static String reverseString(String str) {
        if (str.length() <= 1) {
            return str; // base case
        }
        // Take the first character and move it to the end of the reversed tail.
        return reverseString(str.substring(1)) + str.charAt(0);
    }

    /**
     * Counts occurrences of ch in str recursively.
     * Base case: empty string contains zero occurrences.
     * Recursive case: check the first character, then recurse on the rest.
     */
    static int countCharacter(String str, char ch) {
        if (str.isEmpty()) {
            return 0; // base case
        }
        // 1 if the first character matches, 0 otherwise — then recurse on the tail.
        int match = (str.charAt(0) == ch) ? 1 : 0;
        return match + countCharacter(str.substring(1), ch);
    }

    /**
     * Checks whether str is a palindrome recursively.
     * Base case: empty string or single character is always a palindrome.
     * Recursive case: outermost characters must match AND the inner substring
     * must also be a palindrome.
     *
     * Call trace for "racecar":
     *   isPalindrome("racecar") -> 'r'=='r', isPalindrome("aceca")
     *   isPalindrome("aceca")   -> 'a'=='a', isPalindrome("cec")
     *   isPalindrome("cec")     -> 'c'=='c', isPalindrome("e")
     *   isPalindrome("e")       -> true (base case)
     */
    static boolean isPalindrome(String str) {
        if (str.length() <= 1) {
            return true; // base case
        }
        // If the first and last characters differ, it cannot be a palindrome.
        if (str.charAt(0) != str.charAt(str.length() - 1)) {
            return false;
        }
        // Strip the matched outer characters and check the inner substring.
        return isPalindrome(str.substring(1, str.length() - 1));
    }
}
