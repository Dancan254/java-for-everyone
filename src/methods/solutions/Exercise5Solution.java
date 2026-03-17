package methods.solutions;

/**
 * Exercise5Solution.java
 *
 * Solutions for Exercise Set 5: Recursive Methods - Intermediate.
 * Covers: digit-level arithmetic recursion, index-based array recursion,
 * and recursive pattern printing.
 *
 * Digit operations work by isolating the last digit with (n % 10) and
 * discarding it with (n / 10), recursing until no digits remain.
 * Array recursion passes an index to simulate iteration without a loop.
 */
public class Exercise5Solution {

    public static void main(String[] args) {

        // === Exercise 5.1: Digit Operations ===
        System.out.println("=== Exercise 5.1: Digit Operations ===");

        // sumDigits
        // Call trace for sumDigits(123):
        //   sumDigits(123) = 3 + sumDigits(12)
        //   sumDigits(12)  = 2 + sumDigits(1)
        //   sumDigits(1)   = 1              <- base case
        //   Returns: 1 -> 3 -> 6
        System.out.println("sumDigits(123)   = " + sumDigits(123));
        // Output: sumDigits(123)   = 6
        System.out.println("sumDigits(4567)  = " + sumDigits(4567));
        // Output: sumDigits(4567)  = 22
        System.out.println("sumDigits(0)     = " + sumDigits(0));
        // Output: sumDigits(0)     = 0

        // countDigits
        // Call trace for countDigits(456):
        //   countDigits(456) = 1 + countDigits(45)
        //   countDigits(45)  = 1 + countDigits(4)
        //   countDigits(4)   = 1              <- base case
        //   Returns: 1 -> 2 -> 3
        System.out.println("\ncountDigits(1)       = " + countDigits(1));
        // Output: countDigits(1)       = 1
        System.out.println("countDigits(456)     = " + countDigits(456));
        // Output: countDigits(456)     = 3
        System.out.println("countDigits(100000)  = " + countDigits(100000));
        // Output: countDigits(100000)  = 6

        // reverseNumber
        // Call trace for reverseNumber(123):
        //   reverseNumber(123, 0) = reverseNumber(12, 0*10 + 3) = reverseNumber(12, 3)
        //   reverseNumber(12, 3)  = reverseNumber(1,  3*10 + 2) = reverseNumber(1, 32)
        //   reverseNumber(1, 32)  = reverseNumber(0, 32*10 + 1) = reverseNumber(0, 321)
        //   reverseNumber(0, 321) = 321                          <- base case
        System.out.println("\nreverseNumber(123)   = " + reverseNumber(123));
        // Output: reverseNumber(123)   = 321
        System.out.println("reverseNumber(1200)  = " + reverseNumber(1200));
        // Output: reverseNumber(1200)  = 21
        System.out.println("reverseNumber(9)     = " + reverseNumber(9));
        // Output: reverseNumber(9)     = 9

        // === Exercise 5.2: Array Recursion ===
        System.out.println("\n=== Exercise 5.2: Array Recursion ===");

        int[] arr = {4, 2, 9, 1, 7, 3};
        System.out.println("Array: [4, 2, 9, 1, 7, 3]");

        System.out.println("arraySum(arr)      = " + arraySum(arr, 0));
        // Output: arraySum(arr)      = 26

        System.out.println("arrayMax(arr)      = " + arrayMax(arr, 0));
        // Output: arrayMax(arr)      = 9

        System.out.println("contains(arr, 7)   = " + contains(arr, 7, 0));
        // Output: contains(arr, 7)   = true
        System.out.println("contains(arr, 5)   = " + contains(arr, 5, 0));
        // Output: contains(arr, 5)   = false

        // === Exercise 5.3: Pattern Printing ===
        System.out.println("\n=== Exercise 5.3: Pattern Printing ===");

        System.out.print("printStars(5): ");
        printStars(5);
        System.out.println();
        // Output: printStars(5): *****

        System.out.println("printTriangle(4):");
        printTriangle(4);
        // Output:
        // *
        // **
        // ***
        // ****

        System.out.println("printNumbers(5):");
        printNumbers(5);
        // Output:
        // 1
        // 2
        // 3
        // 4
        // 5
    }

    // -------------------------------------------------------------------------
    // Digit operation methods
    // -------------------------------------------------------------------------

    /**
     * Returns the sum of all digits in n.
     * Base case: n == 0 contributes 0.
     * Recursive case: add the last digit (n % 10) to sumDigits(n / 10).
     * Math.abs ensures negative inputs are handled correctly.
     */
    static int sumDigits(int n) {
        n = Math.abs(n);
        if (n == 0) {
            return 0; // base case: no digits left to sum
        }
        return (n % 10) + sumDigits(n / 10); // last digit + sum of remaining digits
    }

    /**
     * Returns the number of digits in n.
     * Base case: a single-digit number (0–9) has exactly 1 digit.
     * Recursive case: count 1 for the current last digit, recurse on n / 10.
     */
    static int countDigits(int n) {
        n = Math.abs(n);
        if (n < 10) {
            return 1; // base case: single digit
        }
        return 1 + countDigits(n / 10);
    }

    /**
     * Returns the digits of n in reverse order as an integer.
     * Uses an accumulator to build the reversed number as the call stack unwinds.
     *
     * Public entry point delegates to the helper with an initial accumulator of 0.
     */
    static int reverseNumber(int n) {
        return reverseNumberHelper(Math.abs(n), 0);
    }

    /**
     * Tail-recursive helper for reverseNumber.
     * Base case: when n == 0 all digits have been processed; return the accumulator.
     * Recursive case: shift the accumulator left by one decimal place and add the
     * last digit of n, then recurse on n / 10.
     *
     * Call trace for n=123, acc=0:
     *   helper(123, 0)  -> helper(12,  0*10 + 3=3)
     *   helper(12,  3)  -> helper(1,   3*10 + 2=32)
     *   helper(1,   32) -> helper(0,  32*10 + 1=321)
     *   helper(0,  321) -> 321
     */
    private static int reverseNumberHelper(int n, int acc) {
        if (n == 0) {
            return acc; // base case: all digits consumed
        }
        return reverseNumberHelper(n / 10, acc * 10 + n % 10);
    }

    // -------------------------------------------------------------------------
    // Array recursion methods
    // -------------------------------------------------------------------------

    /**
     * Returns the sum of arr[index..arr.length-1].
     * Base case: index past the end of the array — return 0.
     * Recursive case: current element plus the sum of the remaining elements.
     */
    static int arraySum(int[] arr, int index) {
        if (index >= arr.length) {
            return 0; // base case: no elements left
        }
        return arr[index] + arraySum(arr, index + 1);
    }

    /**
     * Returns the maximum value in arr[index..arr.length-1].
     * Base case: last element — nothing left to compare.
     * Recursive case: compare current element against the max of the rest.
     */
    static int arrayMax(int[] arr, int index) {
        if (index == arr.length - 1) {
            return arr[index]; // base case: only one element remains
        }
        int maxOfRest = arrayMax(arr, index + 1);
        return Math.max(arr[index], maxOfRest);
    }

    /**
     * Returns true if target exists in arr[index..arr.length-1].
     * Base case: index past the end — target was not found.
     * Recursive case: return true immediately on a match, otherwise search the rest.
     */
    static boolean contains(int[] arr, int target, int index) {
        if (index >= arr.length) {
            return false; // base case: exhausted the array without finding target
        }
        if (arr[index] == target) {
            return true; // found — no need to recurse further
        }
        return contains(arr, target, index + 1);
    }

    // -------------------------------------------------------------------------
    // Pattern printing methods
    // -------------------------------------------------------------------------

    /**
     * Prints n asterisks without a newline.
     * Base case: n == 0 — nothing to print.
     * Recursive case: print one star, then recurse for n-1.
     */
    static void printStars(int n) {
        if (n == 0) {
            return; // base case
        }
        System.out.print("*");
        printStars(n - 1);
    }

    /**
     * Prints a left-aligned triangle of rows lines.
     * Row 1 has 1 star, row 2 has 2, ..., row n has n stars.
     * Recursion prints smaller triangles first, then adds the last (longest) row.
     *
     * Call trace for printTriangle(3):
     *   printTriangle(3) -> printTriangle(2), then prints "***\n"
     *   printTriangle(2) -> printTriangle(1), then prints "**\n"
     *   printTriangle(1) -> printTriangle(0), then prints "*\n"
     *   printTriangle(0) -> returns (base case)
     */
    static void printTriangle(int rows) {
        if (rows == 0) {
            return; // base case
        }
        printTriangle(rows - 1); // print the smaller triangle above first
        printStars(rows);        // then print the current row
        System.out.println();
    }

    /**
     * Prints integers from 1 to n, one per line.
     * Recursion first descends to the base case (n==1), then prints on the
     * way back up — which naturally produces ascending order.
     *
     * Call trace for printNumbers(3):
     *   printNumbers(3) -> printNumbers(2) -> printNumbers(1) -> prints "1"
     *   Back in printNumbers(2): prints "2"
     *   Back in printNumbers(3): prints "3"
     */
    static void printNumbers(int n) {
        if (n <= 0) {
            return; // base case
        }
        printNumbers(n - 1); // recurse first so smaller numbers are printed first
        System.out.println(n);
    }
}
