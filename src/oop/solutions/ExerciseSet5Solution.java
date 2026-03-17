package oop.solutions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * ExerciseSet5Solution.java
 *
 * Solutions for Exercise Set 5: Abstraction and Advanced OOP.
 * Covers: abstract classes as template-method hosts, interface default and
 *         static methods, method chaining in the Builder pattern, and
 *         private constructors enforcing construction through a builder.
 */
public class ExerciseSet5Solution {

    public static void main(String[] args) {

        // === Exercise 5.1: Abstract Logger ===
        System.out.println("=== Exercise 5.1: Abstract Logger ===");

        // The same log() calls are issued to both loggers.
        // writeLog() is the only part that differs — that is the abstract hook.
        ConsoleLogger console = new ConsoleLogger();
        console.log("INFO",  "Application started.");
        console.log("WARN",  "Low memory detected.");
        console.log("ERROR", "Failed to connect to database.");
        // Output (timestamps vary):
        // [2026-03-17T10:00:00] [INFO] Application started.
        // [2026-03-17T10:00:00] [WARN] Low memory detected.
        // [2026-03-17T10:00:00] [ERROR] Failed to connect to database.

        System.out.println();

        InMemoryLogger memory = new InMemoryLogger();
        memory.log("INFO",  "Application started.");
        memory.log("DEBUG", "User object loaded.");
        memory.log("INFO",  "Request processed.");

        // InMemoryLogger stores entries and exposes them via getEntries().
        System.out.println("Stored log entries:");
        for (String entry : memory.getEntries()) {
            System.out.println("  " + entry);
        }
        // Output:
        //   [2026-03-17T10:00:00] [INFO] Application started.
        //   [2026-03-17T10:00:00] [DEBUG] User object loaded.
        //   [2026-03-17T10:00:00] [INFO] Request processed.

        // === Exercise 5.2: Drawable Interface ===
        System.out.println("\n=== Exercise 5.2: Drawable Interface ===");

        // All three shapes use the default drawWithBorder() inherited from Drawable.
        Drawable square   = new Square();
        Drawable triangle = new Triangle();

        System.out.println("Square with default border:");
        square.drawWithBorder();
        // Output:
        // ----------
        // [Square]
        // ----------

        System.out.println("Triangle with default border:");
        triangle.drawWithBorder();
        // Output:
        // ----------
        // [Triangle]
        // ----------

        // DrawableCircle overrides drawWithBorder() to use a different border style.
        DrawableCircle circle = new DrawableCircle();
        System.out.println("Circle with custom border:");
        circle.drawWithBorder();
        // Output:
        // **********
        // (Circle)
        // **********

        // Static factory method on the interface itself.
        Drawable dot = Drawable.createDot();
        System.out.println("Dot (from static factory):");
        dot.draw();
        // Output: .

        // === Exercise 5.3: Builder Pattern ===
        System.out.println("\n=== Exercise 5.3: Builder Pattern ===");

        // Required fields (firstName, lastName) are set in the Builder constructor.
        // Optional fields use fluent setters. The Person constructor is private.
        Person p1 = new Person.Builder("Alice", "Smith")
                .age(30)
                .email("alice@example.com")
                .phone("555-0100")
                .build();
        System.out.println(p1);
        // Output: Person{firstName='Alice', lastName='Smith', age=30, email='alice@example.com', phone='555-0100', address='N/A'}

        // Minimum viable person — only required fields.
        Person p2 = new Person.Builder("Bob", "Jones").build();
        System.out.println(p2);
        // Output: Person{firstName='Bob', lastName='Jones', age=0, email='N/A', phone='N/A', address='N/A'}

        // All optional fields provided.
        Person p3 = new Person.Builder("Carol", "White")
                .age(25)
                .email("carol@example.com")
                .phone("555-0200")
                .address("123 Main St, Springfield")
                .build();
        System.out.println(p3);
        // Output: Person{firstName='Carol', lastName='White', age=25, email='carol@example.com', phone='555-0200', address='123 Main St, Springfield'}
    }

    // -------------------------------------------------------------------------
    // Exercise 5.1 — Abstract Logger
    // -------------------------------------------------------------------------

    private static final DateTimeFormatter LOG_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Abstract base class that provides the log() template method.
     * Subclasses only need to implement writeLog() — all formatting
     * is handled once here in the concrete log() method.
     */
    static abstract class Logger {

        /**
         * Formats the log entry and delegates writing to the subclass.
         * This is the template method: the common logic is fixed here;
         * writeLog() is the abstract hook that varies per subclass.
         */
        void log(String level, String message) {
            String timestamp = LocalDateTime.now().format(LOG_FORMAT);
            String entry = String.format("[%s] [%s] %s", timestamp, level, message);
            writeLog(entry);
        }

        /** Subclasses define where (and how) the formatted entry is written. */
        abstract void writeLog(String message);
    }

    /** Writes log entries to standard output. */
    static class ConsoleLogger extends Logger {
        @Override
        void writeLog(String message) {
            System.out.println(message);
        }
    }

    /** Accumulates log entries in memory; useful for testing or batch retrieval. */
    static class InMemoryLogger extends Logger {
        private final List<String> entries = new ArrayList<>();

        @Override
        void writeLog(String message) {
            entries.add(message); // Store rather than print.
        }

        /** Returns an unmodifiable view of all stored entries. */
        List<String> getEntries() {
            return List.copyOf(entries);
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 5.2 — Drawable Interface with Default and Static Methods
    // -------------------------------------------------------------------------

    /**
     * Interface demonstrating abstract methods, a default method,
     * and a static factory method — all in one type.
     */
    interface Drawable {

        /** Each implementing class defines how it draws itself. */
        void draw();

        /**
         * Default method: wraps draw() with a line of dashes.
         * Implementing classes inherit this for free; they may override it.
         */
        default void drawWithBorder() {
            System.out.println("----------");
            draw();
            System.out.println("----------");
        }

        /**
         * Static factory method on the interface.
         * Returns an anonymous implementation that prints a single dot.
         */
        static Drawable createDot() {
            return () -> System.out.println(".");
        }
    }

    /** Uses the inherited default drawWithBorder(). */
    static class Square implements Drawable {
        @Override
        public void draw() {
            System.out.println("[Square]");
        }
    }

    /** Uses the inherited default drawWithBorder(). */
    static class Triangle implements Drawable {
        @Override
        public void draw() {
            System.out.println("[Triangle]");
        }
    }

    /**
     * Overrides drawWithBorder() with a different border style.
     * draw() is still required; only the surrounding decoration changes.
     */
    static class DrawableCircle implements Drawable {
        @Override
        public void draw() {
            System.out.println("(Circle)");
        }

        /** Custom border using asterisks instead of dashes. */
        @Override
        public void drawWithBorder() {
            System.out.println("**********");
            draw();
            System.out.println("**********");
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 5.3 — Builder Pattern
    // -------------------------------------------------------------------------

    /**
     * A Person with required and optional fields.
     * The private constructor prevents direct instantiation;
     * callers must use Person.Builder to construct an instance.
     */
    static class Person {
        // Required fields.
        private final String firstName;
        private final String lastName;

        // Optional fields.
        private final int    age;
        private final String email;
        private final String phone;
        private final String address;

        /** Private: only Builder.build() can reach this constructor. */
        private Person(Builder builder) {
            this.firstName = builder.firstName;
            this.lastName  = builder.lastName;
            this.age       = builder.age;
            this.email     = builder.email;
            this.phone     = builder.phone;
            this.address   = builder.address;
        }

        @Override
        public String toString() {
            return String.format(
                "Person{firstName='%s', lastName='%s', age=%d, email='%s', phone='%s', address='%s'}",
                firstName, lastName, age, email, phone, address);
        }

        /**
         * Static inner Builder class.
         * Required fields are validated in the constructor.
         * Optional fields default to sensible placeholders.
         */
        static class Builder {
            // Required.
            private final String firstName;
            private final String lastName;

            // Optional — defaults applied here.
            private int    age     = 0;
            private String email   = "N/A";
            private String phone   = "N/A";
            private String address = "N/A";

            /** Required fields must be provided; they cannot be null or blank. */
            Builder(String firstName, String lastName) {
                if (firstName == null || firstName.isBlank()) {
                    throw new IllegalArgumentException("firstName is required.");
                }
                if (lastName == null || lastName.isBlank()) {
                    throw new IllegalArgumentException("lastName is required.");
                }
                this.firstName = firstName;
                this.lastName  = lastName;
            }

            // Fluent setters: each returns `this` so calls can be chained.
            Builder age(int age)          { this.age     = age;     return this; }
            Builder email(String email)   { this.email   = email;   return this; }
            Builder phone(String phone)   { this.phone   = phone;   return this; }
            Builder address(String addr)  { this.address = addr;    return this; }

            /** Constructs and returns the Person. Validates here if necessary. */
            Person build() {
                return new Person(this);
            }
        }
    }
}
