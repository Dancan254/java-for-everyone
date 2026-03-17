package exceptions.solutions;

/**
 * Exercise5Solution.java
 *
 * Solutions for Exercise Set 5: try-with-resources and AutoCloseable.
 * Covers: implementing AutoCloseable, guaranteed resource cleanup in both
 * the normal and exceptional paths, multiple resources declared in one
 * try-with-resources (closed in reverse order), and suppressed exceptions.
 */
public class Exercise5Solution {

    // -------------------------------------------------------------------------
    // Exercise 5.1 — ManagedResource
    // -------------------------------------------------------------------------

    /**
     * A simple resource class that implements AutoCloseable.
     * Any class implementing AutoCloseable can be used in a try-with-resources
     * statement; the JVM calls close() automatically when the block exits,
     * regardless of whether it exits normally or via an exception.
     */
    static class ManagedResource implements AutoCloseable {

        public ManagedResource() {
            System.out.println("ManagedResource opened");
        }

        public void process() {
            System.out.println("Processing...");
        }

        /**
         * Called automatically by try-with-resources. In a real class this
         * would release a file handle, network socket, or database connection.
         */
        @Override
        public void close() {
            System.out.println("ManagedResource closed");
        }
    }

    /**
     * A version of ManagedResource whose process() throws to demonstrate
     * that close() is still called on the exceptional path.
     */
    static class FaultyManagedResource implements AutoCloseable {

        public FaultyManagedResource() {
            System.out.println("FaultyManagedResource opened");
        }

        public void process() {
            throw new RuntimeException("Processing failed");
        }

        @Override
        public void close() {
            System.out.println("FaultyManagedResource closed");
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 5.2 — Multiple Resources (close order)
    // -------------------------------------------------------------------------

    /**
     * Two resources declared in one try-with-resources.
     * Resources are closed in reverse declaration order:
     * OutputResource is declared second, so it is closed first.
     */
    static class InputResource implements AutoCloseable {
        public InputResource()  { System.out.println("InputResource opened");  }
        public void read()      { System.out.println("InputResource reading"); }
        @Override
        public void close()     { System.out.println("InputResource closed");  }
    }

    static class OutputResource implements AutoCloseable {
        public OutputResource() { System.out.println("OutputResource opened");  }
        public void write()     { System.out.println("OutputResource writing"); }
        @Override
        public void close()     { System.out.println("OutputResource closed");  }
    }

    /**
     * A version of OutputResource whose write() throws to demonstrate the
     * close order on the exceptional path.
     */
    static class FaultyOutputResource implements AutoCloseable {
        public FaultyOutputResource() { System.out.println("FaultyOutputResource opened"); }
        public void write()           { throw new RuntimeException("Write failed"); }
        @Override
        public void close()           { System.out.println("FaultyOutputResource closed"); }
    }

    // -------------------------------------------------------------------------
    // Exercise 5.3 — Suppressed Exceptions
    // -------------------------------------------------------------------------

    /**
     * A resource where both the body operation and close() throw.
     * In a try-with-resources, if the body throws an exception AND close()
     * also throws, Java keeps the body exception as the primary and attaches
     * the close exception to it as a suppressed exception. Neither is lost.
     *
     * This is a key advantage over classic finally: in a finally block, an
     * exception thrown from finally silently replaces the original exception.
     */
    static class BrokenResource implements AutoCloseable {

        public BrokenResource() {
            System.out.println("BrokenResource opened");
        }

        public void use() {
            throw new RuntimeException("Primary failure");
        }

        @Override
        public void close() {
            // This would normally be catastrophic in a finally block —
            // it would replace the primary exception. try-with-resources
            // suppresses this instead of losing the primary.
            throw new RuntimeException("Close failure");
        }
    }

    // -------------------------------------------------------------------------
    // main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 5.1: ManagedResource ---

        System.out.println("=== Exercise 5.1: AutoCloseable — Normal Path ===");

        try (ManagedResource res = new ManagedResource()) {
            res.process();
        }
        // Output:
        // ManagedResource opened
        // Processing...
        // ManagedResource closed   <- called automatically

        System.out.println();

        System.out.println("=== Exercise 5.1: AutoCloseable — Exception Path ===");

        try (FaultyManagedResource res = new FaultyManagedResource()) {
            res.process(); // throws
        } catch (RuntimeException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        // Output:
        // FaultyManagedResource opened
        // FaultyManagedResource closed   <- called before the exception propagates
        // Caught: Processing failed

        // --- Exercise 5.2: Multiple Resources ---

        System.out.println("\n=== Exercise 5.2: Multiple Resources — Normal Path ===");

        try (InputResource in = new InputResource();
             OutputResource out = new OutputResource()) {
            in.read();
            out.write();
        }
        // Output:
        // InputResource opened
        // OutputResource opened
        // InputResource reading
        // OutputResource writing
        // OutputResource closed    <- second declared, first closed
        // InputResource closed     <- first declared, last closed

        System.out.println();

        System.out.println("=== Exercise 5.2: Multiple Resources — Exception Path ===");

        try (InputResource in = new InputResource();
             FaultyOutputResource out = new FaultyOutputResource()) {
            in.read();
            out.write(); // throws
        } catch (RuntimeException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        // Output:
        // InputResource opened
        // FaultyOutputResource opened
        // InputResource reading
        // FaultyOutputResource closed  <- closed before InputResource even on exception
        // InputResource closed
        // Caught: Write failed

        // --- Exercise 5.3: Suppressed Exceptions ---

        System.out.println("\n=== Exercise 5.3: Suppressed Exceptions ===");

        try (BrokenResource res = new BrokenResource()) {
            res.use(); // throws "Primary failure"
        } catch (RuntimeException e) {
            // The body exception is the primary — it is what we caught.
            System.out.println("Primary exception: " + e.getMessage());

            // close() also threw, but that exception was suppressed rather than
            // replacing the primary. getSuppressed() lets us retrieve it.
            Throwable[] suppressed = e.getSuppressed();
            System.out.println("Suppressed exceptions: " + suppressed.length);
            for (Throwable s : suppressed) {
                System.out.println("  Suppressed: " + s.getMessage());
            }
        }
        // Output:
        // BrokenResource opened
        // BrokenResource closed   <- called automatically; its exception is suppressed
        // Primary exception: Primary failure
        // Suppressed exceptions: 1
        //   Suppressed: Close failure
    }
}
