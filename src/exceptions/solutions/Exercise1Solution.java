package exceptions.solutions;

/**
 * Exercise1Solution.java
 *
 * Solutions for Exercise Set 1: try/catch/finally.
 * Covers: basic exception handling, multiple catch blocks ordered from
 * specific to general, and the finally block as a guaranteed cleanup point.
 */
public class Exercise1Solution {

    // -------------------------------------------------------------------------
    // Exercise 1.1 — Safe Division
    // -------------------------------------------------------------------------

    /**
     * Divides a by b. Returns 0 if b is zero and prints an explanation.
     * The finally block always executes regardless of whether an exception
     * was thrown — it documents the attempt even when it fails.
     */
    static int safeDivide(int a, int b) {
        try {
            return a / b;
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero — returning 0.");
            return 0;
        } finally {
            // Runs whether the division succeeded, failed, or returned early.
            System.out.println("Division attempted (" + a + " / " + b + ")");
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 1.2 — Multiple Catch Blocks
    // -------------------------------------------------------------------------

    /**
     * Parses indexStr as an integer, uses it to index arr, and returns the
     * element in upper case. Three distinct catch blocks handle the three
     * ways this sequence can fail. Each provides a message specific to the
     * failure mode rather than a generic "something went wrong."
     *
     * Catch order matters: more specific exceptions must come before more
     * general ones. All three here are unrelated siblings, so order is
     * flexible, but the principle is worth noting.
     */
    static String parseAndIndex(String[] arr, String indexStr) {
        try {
            int index = Integer.parseInt(indexStr); // May throw NumberFormatException
            String value = arr[index];              // May throw ArrayIndexOutOfBoundsException
            return value.toUpperCase();             // May throw NullPointerException
        } catch (NumberFormatException e) {
            System.out.println("Bad index string — not a number: '" + indexStr + "'");
            return null;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Index " + indexStr + " is out of range for array of length " + arr.length);
            return null;
        } catch (NullPointerException e) {
            System.out.println("Element at index " + indexStr + " is null — cannot call toUpperCase()");
            return null;
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 1.3 — FakeConnection with finally cleanup
    // -------------------------------------------------------------------------

    /**
     * A stand-in for a real connection. Demonstrates the finally-based
     * cleanup pattern without requiring actual I/O.
     */
    static class FakeConnection {
        private boolean open = false;

        void open() {
            open = true;
            System.out.println("Connection opened.");
        }

        /**
         * Simulates a query. Throws RuntimeException for "DROP" to represent
         * a bad or rejected command.
         */
        void query(String sql) {
            if (sql.startsWith("DROP")) {
                throw new RuntimeException("Dangerous query rejected: " + sql);
            }
            System.out.println("Query executed: " + sql);
        }

        void close() {
            open = false;
            System.out.println("Connection closed.");
        }
    }

    /**
     * Opens, queries, and always closes. The finally block guarantees the
     * connection is released even if query() throws.
     */
    static void runQuery(String sql) {
        FakeConnection conn = new FakeConnection();
        conn.open();
        try {
            conn.query(sql);
        } catch (RuntimeException e) {
            System.out.println("Query failed: " + e.getMessage());
        } finally {
            // close() is called here regardless of what happened above.
            conn.close();
        }
    }

    // -------------------------------------------------------------------------
    // main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 1.1: safeDivide ---

        System.out.println("=== Exercise 1.1: Safe Division ===");

        System.out.println("Result: " + safeDivide(10, 2));
        // Output:
        // Division attempted (10 / 2)
        // Result: 5

        System.out.println("Result: " + safeDivide(10, 0));
        // Output:
        // Cannot divide by zero — returning 0.
        // Division attempted (10 / 0)
        // Result: 0

        System.out.println("Result: " + safeDivide(-15, 3));
        // Output:
        // Division attempted (-15 / 3)
        // Result: -5

        // --- Exercise 1.2: parseAndIndex ---

        System.out.println("\n=== Exercise 1.2: Multiple Catch Blocks ===");

        String[] arr = {"apple", "banana", null, "date"};

        System.out.println(parseAndIndex(arr, "1"));
        // Output:
        // BANANA

        System.out.println(parseAndIndex(arr, "abc"));
        // Output:
        // Bad index string — not a number: 'abc'
        // null

        System.out.println(parseAndIndex(arr, "99"));
        // Output:
        // Index 99 is out of range for array of length 4
        // null

        System.out.println(parseAndIndex(arr, "2"));
        // Output:
        // Element at index 2 is null — cannot call toUpperCase()
        // null

        // --- Exercise 1.3: FakeConnection finally cleanup ---

        System.out.println("\n=== Exercise 1.3: Finally for Cleanup ===");

        System.out.println("-- Normal query --");
        runQuery("SELECT * FROM users");
        // Output:
        // Connection opened.
        // Query executed: SELECT * FROM users
        // Connection closed.

        System.out.println("-- Bad query --");
        runQuery("DROP TABLE users");
        // Output:
        // Connection opened.
        // Query failed: Dangerous query rejected: DROP TABLE users
        // Connection closed.
        // (close() is called even after the exception)
    }
}
