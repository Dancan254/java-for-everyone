package exceptions.solutions;

import java.sql.SQLException;

/**
 * Exercise4Solution.java
 *
 * Solutions for Exercise Set 4: Exception Chaining and Re-throwing.
 * Covers: wrapping low-level exceptions in higher-level ones while preserving
 * the original cause, re-throwing with added context, and following a multi-level
 * cause chain at the catch site.
 */
public class Exercise4Solution {

    // -------------------------------------------------------------------------
    // Exercise 4.1 — Wrapping a Low-Level Exception
    // -------------------------------------------------------------------------

    /** High-level exception used by the data-access layer. */
    static class DataAccessException extends Exception {
        public DataAccessException(String message, Throwable cause) {
            // Passing the cause to the superclass constructor chains the exceptions.
            // e.getCause() at the catch site will return the original Throwable.
            super(message, cause);
        }
    }

    /**
     * Simulates loading a user record from an array.
     * Out-of-bounds array access is a low-level detail; the caller knows nothing
     * about arrays — it only understands "user data could not be loaded."
     * Wrapping translates between layers without losing the original cause.
     */
    static void loadUserData(int userId) throws DataAccessException {
        int[] data = new int[10];
        try {
            data[userId] = 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            // Wrap: give the caller a meaningful, domain-level description.
            // The original ArrayIndexOutOfBoundsException is preserved as the cause.
            throw new DataAccessException("Failed to load data for user id=" + userId, e);
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 4.2 — Re-throwing with Context
    // -------------------------------------------------------------------------

    /**
     * Parses a price string.
     * NumberFormatException is a low-level parsing detail. Re-throwing as
     * IllegalArgumentException adds domain context ("what was the bad input?")
     * while preserving the original exception as the cause so stack traces
     * remain complete.
     */
    static double parsePrice(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            // Re-throw: attach the original exception as the cause.
            throw new IllegalArgumentException("Invalid price format: '" + input + "'", e);
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 4.3 — Multi-level Exception Chain
    // -------------------------------------------------------------------------

    /** Checked exception from the repository layer. */
    static class RepositoryException extends Exception {
        public RepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /** Unchecked exception from the service layer. */
    static class ServiceException extends RuntimeException {
        public ServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Database layer: lowest level. Throws a checked SQLException to represent
     * a real infrastructure failure.
     */
    static void databaseLayer() throws SQLException {
        throw new SQLException("Connection timeout");
    }

    /**
     * Repository layer: wraps the SQL exception in a domain-level checked
     * exception so higher layers do not need to know about JDBC.
     */
    static void repositoryLayer() throws RepositoryException {
        try {
            databaseLayer();
        } catch (SQLException e) {
            throw new RepositoryException("User lookup failed at repository layer", e);
        }
    }

    /**
     * Service layer: wraps the repository exception in an unchecked
     * ServiceException so controllers and callers are not forced to declare it.
     */
    static void serviceLayer() {
        try {
            repositoryLayer();
        } catch (RepositoryException e) {
            throw new ServiceException("Service could not complete user lookup", e);
        }
    }

    // -------------------------------------------------------------------------
    // main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 4.1: Wrapping ---

        System.out.println("=== Exercise 4.1: Wrapping a Low-Level Exception ===");

        try {
            loadUserData(99);
        } catch (DataAccessException e) {
            System.out.println("High-level message: " + e.getMessage());
            System.out.println("Root cause:         " + e.getCause().getMessage());
        }
        // Output:
        // High-level message: Failed to load data for user id=99
        // Root cause:         Index 99 out of bounds for length 10

        // Valid userId — no exception.
        try {
            loadUserData(5);
            System.out.println("loadUserData(5) succeeded.");
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
        // Output: loadUserData(5) succeeded.

        // --- Exercise 4.2: Re-throwing with Context ---

        System.out.println("\n=== Exercise 4.2: Re-throwing with Context ===");

        // Valid price string.
        System.out.println(parsePrice("19.99"));
        // Output: 19.99

        // Invalid price string — re-thrown with context.
        try {
            parsePrice("nineteen");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
            // The cause is the original NumberFormatException from Double.parseDouble.
            System.out.println("Cause type: " + e.getCause().getClass().getSimpleName());
        }
        // Output:
        // Caught: Invalid price format: 'nineteen'
        // Cause type: NumberFormatException

        // --- Exercise 4.3: Multi-level Chain ---

        System.out.println("\n=== Exercise 4.3: Multi-level Exception Chain ===");

        try {
            serviceLayer();
        } catch (ServiceException e) {
            // Level 1: ServiceException from the service layer.
            System.out.println("ServiceException:    " + e.getMessage());

            // Level 2: RepositoryException is the cause of ServiceException.
            Throwable repoCause = e.getCause();
            System.out.println("RepositoryException: " + repoCause.getMessage());

            // Level 3: SQLException is the cause of RepositoryException.
            Throwable sqlCause = repoCause.getCause();
            System.out.println("SQLException:        " + sqlCause.getMessage());
        }
        // Output:
        // ServiceException:    Service could not complete user lookup
        // RepositoryException: User lookup failed at repository layer
        // SQLException:        Connection timeout
    }
}
