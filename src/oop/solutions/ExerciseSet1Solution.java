package oop.solutions;

import java.util.Objects;

/**
 * ExerciseSet1Solution.java
 *
 * Solutions for Exercise Set 1: Classes, Objects, and Constructors.
 * Covers: constructors (overloaded and no-arg), getters, setters, toString(),
 *         instance methods, static fields and methods, input validation,
 *         and inter-object method calls (transfer).
 */
public class ExerciseSet1Solution {

    public static void main(String[] args) {

        // === Exercise 1.1: Book ===
        System.out.println("=== Exercise 1.1: Book ===");

        // Full constructor — all four fields are set at construction time.
        Book book1 = new Book("Clean Code", "Robert Martin", 2008, 35.99);
        System.out.println(book1);
        // Output: Book{title='Clean Code', author='Robert Martin', year=2008, price=$35.99}

        // No-arg constructor — defaults are applied.
        Book book2 = new Book();
        System.out.println(book2);
        // Output: Book{title='Unknown', author='Unknown', year=0, price=$0.00}

        // Only price has a setter; title, author, year are immutable after construction.
        book1.setPrice(29.99);
        System.out.println("Updated price: " + book1.getPrice());
        // Output: Updated price: 29.99

        // isAffordable checks whether the price fits within a given budget.
        System.out.println("Affordable at $40: " + book1.isAffordable(40.00)); // Output: true
        System.out.println("Affordable at $20: " + book1.isAffordable(20.00)); // Output: false

        // === Exercise 1.2: Counter ===
        System.out.println("\n=== Exercise 1.2: Counter ===");

        // Each construction increments the shared static totalCounters field.
        Counter c1 = new Counter();
        Counter c2 = new Counter();
        Counter c3 = new Counter();

        System.out.println("Total counters created: " + Counter.getTotalCounters());
        // Output: Total counters created: 3

        // Instance state is independent across objects.
        c1.increment();
        c1.increment();
        c1.increment();
        c2.increment();
        c2.decrement();

        System.out.println("c1 count: " + c1.getCount()); // Output: c1 count: 3
        System.out.println("c2 count: " + c2.getCount()); // Output: c2 count: 0
        System.out.println("c3 count: " + c3.getCount()); // Output: c3 count: 0

        c1.reset();
        System.out.println("c1 after reset: " + c1.getCount()); // Output: c1 after reset: 0

        // totalCounters is unchanged by increment/decrement — it only tracks creation.
        System.out.println("Total counters (unchanged): " + Counter.getTotalCounters());
        // Output: Total counters (unchanged): 3

        // === Exercise 1.3: BankAccount ===
        System.out.println("\n=== Exercise 1.3: BankAccount ===");

        BankAccount alice = new BankAccount("ACC-001", "Alice", 500.00);
        BankAccount bob   = new BankAccount("ACC-002", "Bob",   200.00);

        System.out.println(alice);
        // Output: BankAccount{number='ACC-001', owner='Alice', balance=$500.00}
        System.out.println(bob);
        // Output: BankAccount{number='ACC-002', owner='Bob', balance=$200.00}

        // Deposit valid amount.
        alice.deposit(150.00);
        System.out.println("Alice after deposit of $150: " + alice.getBalance());
        // Output: Alice after deposit of $150: 650.0

        // Withdraw valid amount.
        boolean success = alice.withdraw(100.00);
        System.out.println("Withdraw $100 from Alice: " + success + " | Balance: " + alice.getBalance());
        // Output: Withdraw $100 from Alice: true | Balance: 550.0

        // Withdraw more than the balance — rejected, returns false.
        boolean failed = alice.withdraw(10000.00);
        System.out.println("Withdraw $10000 from Alice: " + failed + " | Balance: " + alice.getBalance());
        // Output: Withdraw $10000 from Alice: false | Balance: 550.0

        // Transfer: withdraws from alice and deposits into bob atomically.
        alice.transfer(bob, 200.00);
        System.out.println("Alice after transfer: " + alice.getBalance()); // Output: Alice after transfer: 350.0
        System.out.println("Bob after transfer: "   + bob.getBalance());   // Output: Bob after transfer: 400.0

        // Constructor validation: negative initial balance is rejected.
        try {
            BankAccount bad = new BankAccount("ACC-003", "Eve", -100.00);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
            // Output: Caught: Initial balance cannot be negative.
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 1.1 — Book
    // -------------------------------------------------------------------------

    /**
     * Represents a book with title, author, year, and price.
     * Only price is mutable after construction; the other three fields are
     * effectively immutable (no setters provided).
     */
    static class Book {

        private String title;
        private String author;
        private int    year;
        private double price;

        /** Full constructor — sets all four fields. */
        Book(String title, String author, int year, double price) {
            this.title  = title;
            this.author = author;
            this.year   = year;
            this.price  = price;
        }

        /** No-arg constructor — applies sensible defaults. */
        Book() {
            this("Unknown", "Unknown", 0, 0.0);
        }

        // Getters — all four fields are readable.
        String getTitle()  { return title;  }
        String getAuthor() { return author; }
        int    getYear()   { return year;   }
        double getPrice()  { return price;  }

        // Only price has a setter; title, author, and year are immutable.
        void setPrice(double price) {
            this.price = price;
        }

        /**
         * Returns true if this book's price is within the given budget.
         * Demonstrates an instance method that uses the object's own state.
         */
        boolean isAffordable(double budget) {
            return price <= budget;
        }

        @Override
        public String toString() {
            return String.format("Book{title='%s', author='%s', year=%d, price=$%.2f}",
                    title, author, year, price);
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 1.2 — Counter
    // -------------------------------------------------------------------------

    /**
     * A simple counter that tracks how many times it has been incremented.
     * The static field totalCounters is shared across all Counter instances
     * and increments each time a new Counter is constructed.
     */
    static class Counter {

        private int count = 0;

        // Static field — belongs to the class, not to any single instance.
        private static int totalCounters = 0;

        Counter() {
            totalCounters++; // Track every creation at the class level.
        }

        void increment() { count++;    }
        void decrement() { count--;    }
        void reset()     { count = 0;  }
        int  getCount()  { return count; }

        // Static method — accessed via Counter.getTotalCounters(), not via an instance.
        static int getTotalCounters() { return totalCounters; }
    }

    // -------------------------------------------------------------------------
    // Exercise 1.3 — BankAccount
    // -------------------------------------------------------------------------

    /**
     * A simple bank account with deposit, withdrawal, and transfer operations.
     * All mutating methods validate their inputs and fail gracefully rather
     * than silently corrupting state.
     */
    static class BankAccount {

        private final String accountNumber;
        private final String owner;
        private double balance;

        /**
         * Constructs a BankAccount.
         * @throws IllegalArgumentException if the initial balance is negative.
         */
        BankAccount(String accountNumber, String owner, double balance) {
            if (balance < 0) {
                throw new IllegalArgumentException("Initial balance cannot be negative.");
            }
            this.accountNumber = accountNumber;
            this.owner         = owner;
            this.balance       = balance;
        }

        double getBalance() { return balance; }

        /** Adds the given amount to the balance. Rejects non-positive amounts. */
        void deposit(double amount) {
            if (amount <= 0) {
                System.out.println("Deposit rejected: amount must be positive.");
                return;
            }
            balance += amount;
        }

        /**
         * Subtracts the given amount from the balance.
         * @return true if the withdrawal succeeded; false if rejected.
         */
        boolean withdraw(double amount) {
            if (amount <= 0) {
                System.out.println("Withdrawal rejected: amount must be positive.");
                return false;
            }
            if (amount > balance) {
                System.out.println("Withdrawal rejected: insufficient funds.");
                return false;
            }
            balance -= amount;
            return true;
        }

        /**
         * Transfers the given amount from this account to the target account.
         * The transfer only proceeds if the withdrawal from this account succeeds.
         */
        void transfer(BankAccount target, double amount) {
            if (withdraw(amount)) {
                target.deposit(amount);
            }
        }

        @Override
        public String toString() {
            return String.format("BankAccount{number='%s', owner='%s', balance=$%.2f}",
                    accountNumber, owner, balance);
        }
    }
}
