import java.util.Arrays;

/**
 * Exercise6Solution.java
 *
 * Solutions for Arrays Exercise Set 6: String and Char Arrays.
 * Covers: anagram checking with frequency arrays, run-length encoding/decoding,
 * longest common prefix.
 */
public class Exercise6Solution {

    // --- Anagram Checker ---

    /**
     * Returns true if a and b are anagrams — same characters at the same frequencies.
     * Uses an int[26] frequency array (one bucket per lowercase letter a-z).
     * Increments for characters in a, decrements for characters in b.
     * If all buckets are 0 at the end, the strings are anagrams.
     *
     * Time: O(n), Space: O(1) (26 slots is constant regardless of input size).
     */
    static boolean areAnagrams(String a, String b) {
        if (a.length() != b.length()) return false; // Different lengths — cannot be anagrams.

        int[] freq = new int[26]; // Index 0 = 'a', index 25 = 'z'.

        for (char c : a.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                freq[c - 'a']++; // Increment the bucket for this letter.
            }
        }

        for (char c : b.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                freq[c - 'a']--; // Decrement the bucket for this letter.
            }
        }

        // If any bucket is non-zero, one string has a different character frequency.
        for (int count : freq) {
            if (count != 0) return false;
        }
        return true;
    }

    // --- Run-Length Encoding ---

    /**
     * Compresses a string using run-length encoding.
     * Consecutive repeated characters are replaced by count + character.
     * Example: "aaabbb" -> "3a3b".
     */
    static String encode(String input) {
        if (input.isEmpty()) return "";

        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < input.length()) {
            char current = input.charAt(i);
            int  count   = 1;

            // Count how many times this character repeats consecutively.
            while (i + count < input.length() && input.charAt(i + count) == current) {
                count++;
            }

            result.append(count).append(current);
            i += count; // Jump past all repetitions of this character.
        }

        return result.toString();
    }

    /**
     * Decompresses a run-length encoded string back to the original.
     * Reads digit groups as the repeat count, then the following character as the symbol.
     */
    static String decode(String encoded) {
        StringBuilder result = new StringBuilder();
        int i = 0;

        while (i < encoded.length()) {
            // Read all consecutive digit characters to form the count.
            StringBuilder countStr = new StringBuilder();
            while (i < encoded.length() && Character.isDigit(encoded.charAt(i))) {
                countStr.append(encoded.charAt(i));
                i++;
            }

            if (i >= encoded.length()) break; // Malformed input guard.

            int    count = Integer.parseInt(countStr.toString());
            char   c     = encoded.charAt(i);
            i++; // Move past the character.

            result.append(String.valueOf(c).repeat(count));
        }

        return result.toString();
    }

    // --- Longest Common Prefix ---

    /**
     * Returns the longest common prefix shared by all strings in the array.
     * Approach: compare character by character up to the length of the shortest string.
     * Stop at the first position where not all strings agree.
     */
    static String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        // Find the length of the shortest string — we cannot go beyond that.
        int minLength = strs[0].length();
        for (String s : strs) {
            if (s.length() < minLength) minLength = s.length();
        }

        StringBuilder prefix = new StringBuilder();

        for (int pos = 0; pos < minLength; pos++) {
            char candidate = strs[0].charAt(pos); // Character at this position in the first string.

            // Check that every other string has the same character at this position.
            for (int k = 1; k < strs.length; k++) {
                if (strs[k].charAt(pos) != candidate) {
                    return prefix.toString(); // Mismatch found — prefix ends here.
                }
            }

            prefix.append(candidate);
        }

        return prefix.toString();
    }

    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 6.1: Anagram Checker ---
        System.out.println("=== Exercise 6.1: Anagram Checker ===");

        String[][] pairs = {
                {"listen", "silent"},
                {"hello",  "world"},
                {"Astronomer", "Moon starer"},
                {"abc",    "cba"},
                {"ab",     "abc"}
        };

        for (String[] pair : pairs) {
            System.out.printf("%-15s and %-15s -> %s%n",
                    "\"" + pair[0] + "\"",
                    "\"" + pair[1] + "\"",
                    areAnagrams(pair[0], pair[1]) ? "anagrams" : "NOT anagrams");
        }
        // Output:
        // "listen"        and "silent"        -> anagrams
        // "hello"         and "world"          -> NOT anagrams
        // "Astronomer"    and "Moon starer"    -> anagrams
        // "abc"           and "cba"            -> anagrams
        // "ab"            and "abc"            -> NOT anagrams

        // --- Exercise 6.2: Run-Length Encoding ---
        System.out.println("\n=== Exercise 6.2: Run-Length Encoding ===");

        String[] inputs = {"aaabbbccddddee", "abcde", "aaaaa", "a"};

        for (String input : inputs) {
            String encoded = encode(input);
            String decoded = decode(encoded);
            boolean roundTrip = input.equals(decoded);

            System.out.printf("Original: %-20s Encoded: %-20s Decoded: %-20s RoundTrip: %s%n",
                    "\"" + input + "\"",
                    "\"" + encoded + "\"",
                    "\"" + decoded + "\"",
                    roundTrip ? "OK" : "FAIL");
        }
        // Output:
        // Original: "aaabbbccddddee"  Encoded: "3a3b2c4d2e"    Decoded: "aaabbbccddddee"  RoundTrip: OK
        // Original: "abcde"           Encoded: "1a1b1c1d1e"    Decoded: "abcde"           RoundTrip: OK
        // Original: "aaaaa"           Encoded: "5a"             Decoded: "aaaaa"           RoundTrip: OK
        // Original: "a"               Encoded: "1a"             Decoded: "a"               RoundTrip: OK

        // --- Exercise 6.3: Longest Common Prefix ---
        System.out.println("\n=== Exercise 6.3: Longest Common Prefix ===");

        String[][] wordSets = {
                {"flower",  "flow",    "flight"},
                {"dog",     "racecar", "car"},
                {"interview", "interact", "interface"},
                {"a"},
                {"prefix",  "prefix",  "prefix"}
        };

        for (String[] words : wordSets) {
            System.out.printf("%-45s -> \"%s\"%n",
                    Arrays.toString(words),
                    longestCommonPrefix(words));
        }
        // Output:
        // [flower, flow, flight]                 -> "fl"
        // [dog, racecar, car]                    -> ""
        // [interview, interact, interface]       -> "inter"
        // [a]                                    -> "a"
        // [prefix, prefix, prefix]               -> "prefix"
    }
}
