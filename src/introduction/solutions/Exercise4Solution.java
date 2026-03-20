package introduction.solutions;

/**
 * Exercise4Solution.java
 *
 * Solutions for Exercise Set 4: String Methods.
 * Covers: trim, length, case conversion, charAt, indexOf, contains,
 * substring, replace, split, join, and String.format.
 */
public class Exercise4Solution {

    public static void main(String[] args) {
        exercise4_1();
        exercise4_2();
        exercise4_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 4.1 — Basic String Inspection
    // -------------------------------------------------------------------------

    static void exercise4_1() {
        System.out.println("=== Exercise 4.1: Basic String Inspection ===");

        String sentence = "  The quick brown fox jumps over the lazy dog.  ";
        String trimmed  = sentence.trim();

        System.out.println("Original (trimmed):  \"" + trimmed + "\"");
        System.out.println("length():            " + trimmed.length());        // Output: 44
        System.out.println("toUpperCase():       " + trimmed.toUpperCase());
        System.out.println("toLowerCase():       " + trimmed.toLowerCase());
        System.out.println("charAt(4):           " + trimmed.charAt(4));       // Output: q
        System.out.println("indexOf(\"fox\"):      " + trimmed.indexOf("fox")); // Output: 16
        System.out.println("contains(\"cat\"):     " + trimmed.contains("cat")); // Output: false
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 4.2 — Substring and Replace
    // -------------------------------------------------------------------------

    static void exercise4_2() {
        System.out.println("=== Exercise 4.2: Substring and Replace ===");

        String sentence = "  The quick brown fox jumps over the lazy dog.  ";
        String trimmed  = sentence.trim();

        // Extract "quick" — starts at index 4, ends before index 9
        String quick = trimmed.substring(4, 9);
        System.out.println("substring(4, 9):              \"" + quick + "\""); // Output: quick

        // Extract from "brown" to the end
        int brownStart = trimmed.indexOf("brown");
        String fromBrown = trimmed.substring(brownStart);
        System.out.println("substring(indexOf(\"brown\")):  \"" + fromBrown + "\"");

        // Replace "fox" with "wolf"
        String withWolf = trimmed.replace("fox", "wolf");
        System.out.println("replace(\"fox\", \"wolf\"):       \"" + withWolf + "\"");

        // Replace every space with underscore
        String underscored = trimmed.replace(" ", "_");
        System.out.println("replace(\" \", \"_\"):            \"" + underscored + "\"");
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 4.3 — Split, Join, and Format
    // -------------------------------------------------------------------------

    static void exercise4_3() {
        System.out.println("=== Exercise 4.3: Split, Join, and Format ===");

        String csv   = "Alice,30,Engineer,London";
        String[] parts = csv.split(",");

        System.out.println("split length: " + parts.length); // Output: 4
        for (String part : parts) {
            System.out.println("  " + part);
        }

        String joined = String.join(" | ", parts);
        System.out.println("join:   " + joined);
        // Output: Alice | 30 | Engineer | London

        String formatted = String.format("Name: %s | Age: %s | Job: %s | City: %s",
                parts[0], parts[1], parts[2], parts[3]);
        System.out.println("format: " + formatted);
        // Output: Name: Alice | Age: 30 | Job: Engineer | City: London
        System.out.println();
    }
}
