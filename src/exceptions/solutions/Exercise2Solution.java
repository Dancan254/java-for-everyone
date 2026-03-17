package exceptions.solutions;

import java.io.IOException;

/**
 * Exercise2Solution.java
 *
 * Solutions for Exercise Set 2: Checked vs Unchecked Exceptions.
 * Covers: the checked exception contract (catch or declare), when unchecked
 * exceptions are appropriate, and multi-level propagation of checked exceptions.
 */
public class Exercise2Solution {

    // -------------------------------------------------------------------------
    // Exercise 2.1 — The Checked Exception Contract
    // -------------------------------------------------------------------------

    /**
     * Simulates reading a configuration file.
     * Throws a checked IOException because the method declares "throws IOException"
     * in its signature — the compiler forces every caller to either catch or
     * re-declare the exception. This is the checked exception contract.
     */
    static void readConfig(String filename) throws IOException {
        if (filename == null || filename.isEmpty()) {
            throw new IOException("No filename provided");
        }
        System.out.println("Reading: " + filename);
    }

    /**
     * Caller option 1: handle the IOException here with a try-catch.
     * The exception does not escape this method.
     */
    static void startWithHandling() {
        try {
            readConfig("app.properties");
            readConfig(""); // triggers the exception
        } catch (IOException e) {
            System.out.println("Config error caught in startWithHandling: " + e.getMessage());
        }
    }

    /**
     * Caller option 2: propagate by declaring throws IOException.
     * The responsibility is passed to whoever calls this method.
     */
    static void startWithPropagation() throws IOException {
        readConfig("app.properties");
    }

    // The following method does not compile because it calls readConfig without
    // handling or declaring the IOException:
    //
    // static void startWithNoHandling() {
    //     readConfig("app.properties"); // Compiler error: unhandled exception type IOException
    // }

    // -------------------------------------------------------------------------
    // Exercise 2.2 — BankAccount with unchecked exceptions
    // -------------------------------------------------------------------------

    /**
     * A minimal bank account that validates withdrawals.
     *
     * IllegalArgumentException and IllegalStateException are both unchecked
     * (they extend RuntimeException). They are used here because these failures
     * represent programming mistakes or invariant violations — the caller controls
     * the amount and should pass valid values. Checked exceptions are reserved
     * for external conditions the caller cannot always prevent (file missing, network
     * down). Requiring try-catch on every withdrawal would clutter calling code
     * for failures that should not occur in correct usage.
     */
    static class BankAccount {
        private double balance;

        public BankAccount(double initialBalance) {
            this.balance = initialBalance;
        }

        public double getBalance() { return balance; }

        public void withdraw(double amount) {
            if (amount <= 0) {
                // Unchecked: the caller sent an invalid argument — a programming error.
                throw new IllegalArgumentException(
                        "Withdrawal amount must be positive, but received: " + amount);
            }
            if (amount > balance) {
                // Unchecked: the object is not in a state that allows this operation.
                throw new IllegalStateException(
                        "Insufficient funds. Balance=" + balance + ", requested=" + amount);
            }
            balance -= amount;
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 2.3 — Checked Exception Propagation Chain
    // -------------------------------------------------------------------------

    /**
     * Lowest layer: throws a checked IOException directly.
     * Every caller must handle or declare it.
     */
    static void readLine() throws IOException {
        throw new IOException("Disk read failure");
    }

    /**
     * Middle layer: does not handle the IOException — propagates it upward.
     * The "throws IOException" declaration tells the compiler this is intentional.
     */
    static void processFile() throws IOException {
        readLine();
    }

    /**
     * Top layer: catches the IOException and handles it here.
     * No "throws IOException" needed because the exception does not escape.
     */
    static void loadData() {
        try {
            processFile();
        } catch (IOException e) {
            System.out.println("loadData caught: " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 2.1: Checked Exception Contract ---

        System.out.println("=== Exercise 2.1: Checked Exception Contract ===");

        startWithHandling();
        // Output:
        // Reading: app.properties
        // Config error caught in startWithHandling: No filename provided

        // Demonstrate startWithPropagation — catch it here since it declares throws.
        try {
            startWithPropagation();
            System.out.println("Propagation caller: config loaded.");
        } catch (IOException e) {
            System.out.println("Propagation caller caught: " + e.getMessage());
        }
        // Output: Propagation caller: config loaded.

        // --- Exercise 2.2: Unchecked Exceptions on BankAccount ---

        System.out.println("\n=== Exercise 2.2: Unchecked Exceptions ===");

        BankAccount account = new BankAccount(100.0);

        try {
            account.withdraw(-50.0);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
        }
        // Output: IllegalArgumentException: Withdrawal amount must be positive, but received: -50.0

        try {
            account.withdraw(250.0);
        } catch (IllegalStateException e) {
            System.out.println("IllegalStateException: " + e.getMessage());
        }
        // Output: IllegalStateException: Insufficient funds. Balance=100.0, requested=250.0

        // A valid withdrawal succeeds without any exception.
        account.withdraw(30.0);
        System.out.println("Balance after valid withdrawal: " + account.getBalance());
        // Output: Balance after valid withdrawal: 70.0

        // --- Exercise 2.3: Propagation Chain ---

        System.out.println("\n=== Exercise 2.3: Checked Propagation Chain ===");

        loadData();
        // Output: loadData caught: Disk read failure
        // readLine threw -> processFile declared -> loadData caught
    }
}
