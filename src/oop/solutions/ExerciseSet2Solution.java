package oop.solutions;

import java.util.EmptyStackException;
import java.util.Objects;

/**
 * ExerciseSet2Solution.java
 *
 * Solutions for Exercise Set 2: Encapsulation and Access Control.
 * Covers: computed properties, validation in setters, internal data
 *         structure hiding (IntStack), and full immutability (Point).
 */
public class ExerciseSet2Solution {

    public static void main(String[] args) {

        // === Exercise 2.1: Temperature ===
        System.out.println("=== Exercise 2.1: Temperature ===");

        // Construct with a Celsius value; all conversions are computed on demand.
        Temperature t = new Temperature(100.0);
        System.out.println(t);
        // Output: Temperature{100.00°C, 212.00°F, 373.15K}

        // setCelsius validates against absolute zero.
        t.setCelsius(-273.15);
        System.out.println("At absolute zero: " + t);
        // Output: At absolute zero: Temperature{-273.15°C, -459.67°F, 0.00K}

        // setFahrenheit converts to Celsius internally before storing.
        t.setFahrenheit(32.0); // 32°F = 0°C
        System.out.println("Freezing point (set via Fahrenheit): " + t);
        // Output: Freezing point (set via Fahrenheit): Temperature{0.00°C, 32.00°F, 273.15K}

        // Validation: below absolute zero should be rejected.
        try {
            t.setCelsius(-300.0);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
            // Output: Caught: Temperature cannot be below absolute zero (-273.15°C).
        }

        // === Exercise 2.2: IntStack ===
        System.out.println("\n=== Exercise 2.2: IntStack ===");

        IntStack stack = new IntStack(4);

        System.out.println("Empty: " + stack.isEmpty()); // Output: Empty: true
        System.out.println("Size:  " + stack.size());    // Output: Size:  0

        // push() adds elements to the top; internal array is completely hidden.
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);

        System.out.println("Full:  " + stack.isFull()); // Output: Full:  true
        System.out.println("Peek:  " + stack.peek());   // Output: Peek:  40
        System.out.println("Size:  " + stack.size());   // Output: Size:  4

        // Overfilling a full stack throws IllegalStateException.
        try {
            stack.push(99);
        } catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage());
            // Output: Caught: Stack is full.
        }

        // pop() removes and returns the top element (LIFO order).
        System.out.println("Pop: " + stack.pop()); // Output: Pop: 40
        System.out.println("Pop: " + stack.pop()); // Output: Pop: 30
        System.out.println("Size after two pops: " + stack.size()); // Output: Size after two pops: 2

        // Drain the remaining elements.
        stack.pop();
        stack.pop();

        // Popping an empty stack throws EmptyStackException.
        try {
            stack.pop();
        } catch (EmptyStackException e) {
            System.out.println("Caught EmptyStackException on pop from empty stack.");
            // Output: Caught EmptyStackException on pop from empty stack.
        }

        // === Exercise 2.3: Immutable Point ===
        System.out.println("\n=== Exercise 2.3: Immutable Point ===");

        Point p1 = new Point(0.0, 0.0);
        Point p2 = new Point(3.0, 4.0);

        System.out.println("p1: " + p1); // Output: p1: Point(0.00, 0.00)
        System.out.println("p2: " + p2); // Output: p2: Point(3.00, 4.00)

        // Distance uses the Pythagorean theorem: sqrt(dx^2 + dy^2).
        System.out.printf("Distance p1 to p2: %.2f%n", p1.distanceTo(p2));
        // Output: Distance p1 to p2: 5.00

        // translate() returns a new Point — p2 is unchanged (immutability demonstrated).
        Point p3 = p2.translate(1.0, 1.0);
        System.out.println("p2 after translate call: " + p2); // Output: p2 after translate call: Point(3.00, 4.00)
        System.out.println("p3 (new from translate): " + p3); // Output: p3 (new from translate): Point(4.00, 5.00)

        // equals() compares by value; two distinct objects with the same coordinates are equal.
        Point p4 = new Point(3.0, 4.0);
        System.out.println("p2.equals(p4): " + p2.equals(p4));     // Output: p2.equals(p4): true
        System.out.println("p2 == p4:      " + (p2 == p4));        // Output: p2 == p4:      false

        // hashCode() is consistent with equals(): equal objects have the same hash.
        System.out.println("Same hash: " + (p2.hashCode() == p4.hashCode()));
        // Output: Same hash: true
    }

    // -------------------------------------------------------------------------
    // Exercise 2.1 — Temperature
    // -------------------------------------------------------------------------

    /**
     * Stores a temperature internally in Celsius and exposes computed
     * conversions to Fahrenheit and Kelvin as getter methods.
     * No additional fields are stored — computation happens on demand.
     */
    static class Temperature {

        private double celsius;

        Temperature(double celsius) {
            setCelsius(celsius); // Validate on construction.
        }

        double getCelsius() {
            return celsius;
        }

        /** Computes Fahrenheit from the stored Celsius value. */
        double getFahrenheit() {
            return celsius * 9.0 / 5.0 + 32.0;
        }

        /** Computes Kelvin from the stored Celsius value. */
        double getKelvin() {
            return celsius + 273.15;
        }

        /** Validates against absolute zero before storing. */
        void setCelsius(double celsius) {
            if (celsius < -273.15) {
                throw new IllegalArgumentException(
                        "Temperature cannot be below absolute zero (-273.15°C).");
            }
            this.celsius = celsius;
        }

        /** Converts the Fahrenheit input to Celsius, then stores it with validation. */
        void setFahrenheit(double fahrenheit) {
            // Conversion: C = (F - 32) * 5/9
            setCelsius((fahrenheit - 32.0) * 5.0 / 9.0);
        }

        @Override
        public String toString() {
            return String.format("Temperature{%.2f°C, %.2f°F, %.2fK}",
                    getCelsius(), getFahrenheit(), getKelvin());
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 2.2 — IntStack
    // -------------------------------------------------------------------------

    /**
     * A fixed-capacity stack backed by a private int array.
     * All interaction is through the public API; the internal array and
     * the size counter are fully hidden.
     */
    static class IntStack {

        private final int[] data;
        private int size;

        /** Constructs an empty stack with the given maximum capacity. */
        IntStack(int capacity) {
            data = new int[capacity];
            size = 0;
        }

        /**
         * Pushes a value onto the top of the stack.
         * @throws IllegalStateException if the stack is already full.
         */
        void push(int value) {
            if (isFull()) {
                throw new IllegalStateException("Stack is full.");
            }
            data[size++] = value; // Store at the current top, then advance the pointer.
        }

        /**
         * Removes and returns the top element.
         * @throws EmptyStackException if the stack is empty.
         */
        int pop() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return data[--size]; // Retreat the pointer first, then read the value.
        }

        /**
         * Returns the top element without removing it.
         * @throws EmptyStackException if the stack is empty.
         */
        int peek() {
            if (isEmpty()) {
                throw new EmptyStackException();
            }
            return data[size - 1];
        }

        boolean isEmpty() { return size == 0; }
        boolean isFull()  { return size == data.length; }
        int     size()    { return size; }
    }

    // -------------------------------------------------------------------------
    // Exercise 2.3 — Immutable Point
    // -------------------------------------------------------------------------

    /**
     * An immutable (x, y) coordinate.
     * Fields are private and final — no setters exist.
     * Operations that "modify" the point return a new Point instance instead.
     */
    static class Point {

        private final double x;
        private final double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        double getX() { return x; }
        double getY() { return y; }

        /** Returns the Euclidean distance to another point. */
        double distanceTo(Point other) {
            double dx = this.x - other.x;
            double dy = this.y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        /**
         * Returns a NEW Point displaced by (dx, dy).
         * This object is not modified — immutability is preserved.
         */
        Point translate(double dx, double dy) {
            return new Point(x + dx, y + dy);
        }

        /**
         * Value equality: two Points are equal if their coordinates match.
         * Must override both equals() and hashCode() together.
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point other)) return false;
            return Double.compare(x, other.x) == 0
                && Double.compare(y, other.y) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return String.format("Point(%.2f, %.2f)", x, y);
        }
    }
}
