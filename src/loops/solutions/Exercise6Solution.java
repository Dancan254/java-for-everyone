package loops.solutions;

/**
 * Exercise6Solution.java
 *
 * Solutions for Exercise Set 6: Advanced Challenges.
 * Covers: extended FizzBuzz, Pascal's Triangle with 2D arrays, Roman numeral conversion.
 */
public class Exercise6Solution {

    public static void main(String[] args) {

        // --- Exercise 6.1: FizzBuzz Extended ---
        // The key: build the output String by checking each divisibility condition separately
        // and appending the appropriate word. If no condition matched, print the number.
        System.out.println("=== Exercise 6.1: FizzBuzz Extended (1-30 shown) ===");

        for (int i = 1; i <= 30; i++) {
            StringBuilder output = new StringBuilder();

            // Each condition is independent — they can all be true for the same number.
            if (i % 3 == 0) output.append("Fizz");
            if (i % 5 == 0) output.append("Buzz");
            if (i % 7 == 0) output.append("Bang");

            // If no condition matched, the StringBuilder is empty — print the number instead.
            if (output.length() == 0) {
                System.out.print(i + " ");
            } else {
                System.out.print(output + " ");
            }
        }
        System.out.println();
        // Output: 1 2 Fizz 4 Buzz Fizz Bang 8 Fizz Buzz 11 Fizz 13 Bang FizzBuzz 16 17 Fizz 19 Buzz FizzBang 22 23 Fizz Buzz 26 Fizz Bang 29 FizzBuzz

        // --- Exercise 6.2: Pascal's Triangle ---
        // Each row is computed from the previous row.
        // triangle[row][col] = triangle[row-1][col-1] + triangle[row-1][col]
        // First and last elements of every row are always 1.
        System.out.println("\n=== Exercise 6.2: Pascal's Triangle (10 rows) ===");

        int rows          = 10;
        int[][] triangle  = new int[rows][];

        for (int row = 0; row < rows; row++) {
            triangle[row]    = new int[row + 1]; // Row i has i+1 elements.
            triangle[row][0] = 1;                // First element is always 1.
            triangle[row][row] = 1;              // Last element is always 1.

            // Middle elements: sum of the two elements directly above.
            for (int col = 1; col < row; col++) {
                triangle[row][col] = triangle[row - 1][col - 1] + triangle[row - 1][col];
            }
        }

        // Print the triangle centered by padding each row with leading spaces.
        for (int row = 0; row < rows; row++) {
            // Each element takes 4 characters; leading spaces center each row.
            String indent = " ".repeat((rows - row - 1) * 2);
            System.out.print(indent);
            for (int value : triangle[row]) {
                System.out.printf("%4d", value);
            }
            System.out.println();
        }
        // Output (last row): 1   9  36  84 126 126  84  36   9   1

        // --- Exercise 6.3: Roman Numeral Converter ---
        // Parallel arrays map decimal values to Roman symbols.
        // Repeatedly subtract the largest fitting value and append its symbol.
        System.out.println("\n=== Exercise 6.3: Roman Numeral Converter ===");

        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        int[] testNumbers = {1, 4, 9, 14, 40, 49, 90, 399, 400, 944, 1999, 2024, 3999};
        for (int num : testNumbers) {
            System.out.println(num + " = " + toRoman(num, values, symbols));
        }
        // Output:
        // 1    = I
        // 4    = IV
        // 9    = IX
        // 14   = XIV
        // 40   = XL
        // 49   = XLIX
        // 90   = XC
        // 399  = CCCXCIX
        // 400  = CD
        // 944  = CMXLIV
        // 1999 = MCMXCIX
        // 2024 = MMXXIV
        // 3999 = MMMCMXCIX
    }

    /**
     * Converts a positive integer (1–3999) to its Roman numeral representation.
     * Uses a greedy approach: at each step, use the largest symbol that fits,
     * subtract its value from n, and repeat until n reaches 0.
     */
    static String toRoman(int n, int[] values, String[] symbols) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            // Append the symbol as many times as its value fits into n.
            while (n >= values[i]) {
                result.append(symbols[i]);
                n -= values[i]; // Reduce n by the value of the symbol just appended.
            }
        }

        return result.toString();
    }
}
