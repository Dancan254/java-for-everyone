package oop.solutions;

import java.util.Arrays;

/**
 * ExerciseSet4Solution.java
 *
 * Solutions for Exercise Set 4: Polymorphism and Interfaces.
 * Covers: Comparable for natural ordering, multiple interface implementation,
 *         interface-typed references, and the Strategy design pattern.
 */
public class ExerciseSet4Solution {

    public static void main(String[] args) {

        // === Exercise 4.1: Comparable Shapes ===
        System.out.println("=== Exercise 4.1: Comparable Shapes ===");

        // Mixed array of Shape implementations.
        Shape[] shapes = {
            new Circle("red",    3.0),
            new Rectangle("blue", 2.0, 5.0),
            new RightTriangle("green", 6.0, 8.0),
            new Circle("yellow", 1.5),
            new Rectangle("purple", 7.0, 3.0),
            new RightTriangle("orange", 5.0, 5.0)
        };

        System.out.println("Before sorting:");
        for (Shape s : shapes) {
            System.out.printf("  %s (area=%.2f)%n", s, s.area());
        }
        // Output (before sorting):
        //   red Circle        (area=28.27)
        //   blue Rectangle    (area=10.00)
        //   green RightTriangle (area=24.00)
        //   yellow Circle     (area=7.07)
        //   purple Rectangle  (area=21.00)
        //   orange RightTriangle (area=12.50)

        // Arrays.sort() uses the compareTo() defined in Shape, which sorts by area ascending.
        Arrays.sort(shapes);

        System.out.println("After sorting (ascending area):");
        for (Shape s : shapes) {
            System.out.printf("  %s (area=%.2f)%n", s, s.area());
        }
        // Output (after sorting):
        //   yellow Circle     (area=7.07)
        //   blue Rectangle    (area=10.00)
        //   orange RightTriangle (area=12.50)
        //   purple Rectangle  (area=21.00)
        //   green RightTriangle (area=24.00)
        //   red Circle        (area=28.27)

        // === Exercise 4.2: Printable and Saveable Interfaces ===
        System.out.println("\n=== Exercise 4.2: Printable and Saveable ===");

        Product p1 = new Product("P001", "Mechanical Keyboard", 89.99);
        Product p2 = new Product("P002", "USB Hub",             24.99);
        Product p3 = new Product("P003", "Monitor Stand",       45.00);

        // printAll() accepts Printable — it does not know or care that these are Products.
        printAll(new Printable[]{p1, p2, p3});

        // serialize() is accessed via the Saveable interface type.
        Saveable[] saveables = {p1, p2, p3};
        System.out.println("\nSerialized:");
        for (Saveable s : saveables) {
            System.out.println("  " + s.serialize());
        }
        // Output:
        //   P001,Mechanical Keyboard,89.99
        //   P002,USB Hub,24.99
        //   P003,Monitor Stand,45.00

        // === Exercise 4.3: Strategy Pattern ===
        System.out.println("\n=== Exercise 4.3: Payment Strategy ===");

        ShoppingCart cart = new ShoppingCart(120.00);

        // Strategy 1: Credit card — always succeeds.
        cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456"));
        cart.checkout();
        // Output: Charging $120.00 to card ending in 3456. Payment successful.

        // Strategy 2: PayPal — succeeds only if balance is sufficient.
        cart.setPaymentStrategy(new PayPalPayment("user@example.com", 200.00));
        cart.checkout();
        // Output: PayPal payment of $120.00 via user@example.com. Payment successful.

        cart.setPaymentStrategy(new PayPalPayment("poor@example.com", 50.00));
        cart.checkout();
        // Output: PayPal payment failed: insufficient balance ($50.00 available).

        // Strategy 3: Bitcoin — applies a 1% fee; payment fails if total exceeds wallet.
        cart.setPaymentStrategy(new BitcoinPayment(200.00));
        cart.checkout();
        // Output: Bitcoin payment: $120.00 + 1% fee = $121.20. Payment successful.

        cart.setPaymentStrategy(new BitcoinPayment(100.00));
        cart.checkout();
        // Output: Bitcoin payment failed: insufficient wallet balance ($100.00 available, need $121.20).
    }

    /** Accepts any Printable and calls print() on each — caller has no knowledge of Product. */
    static void printAll(Printable[] items) {
        System.out.println("Product Catalogue:");
        for (Printable item : items) {
            item.print();
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 4.1 — Comparable Shape hierarchy
    // -------------------------------------------------------------------------

    /**
     * Abstract base class extended to implement Comparable<Shape>.
     * compareTo() compares shapes by area so that Arrays.sort() works naturally.
     */
    static abstract class Shape implements Comparable<Shape> {
        private String color;

        Shape(String color) { this.color = color; }

        String getColor()         { return color; }
        void   setColor(String c) { color = c;    }

        abstract double area();
        abstract double perimeter();

        /** Larger area = greater. Double.compare handles floating-point safely. */
        @Override
        public int compareTo(Shape other) {
            return Double.compare(this.area(), other.area());
        }

        @Override
        public String toString() {
            return color + " " + getClass().getSimpleName();
        }
    }

    static class Circle extends Shape {
        private double radius;

        Circle(String color, double radius) {
            super(color);
            this.radius = radius;
        }

        @Override public double area()      { return Math.PI * radius * radius; }
        @Override public double perimeter() { return 2 * Math.PI * radius; }
    }

    static class Rectangle extends Shape {
        private double width;
        private double height;

        Rectangle(String color, double width, double height) {
            super(color);
            this.width  = width;
            this.height = height;
        }

        @Override public double area()      { return width * height; }
        @Override public double perimeter() { return 2 * (width + height); }
    }

    static class RightTriangle extends Shape {
        private double a;
        private double b;

        RightTriangle(String color, double a, double b) {
            super(color);
            this.a = a;
            this.b = b;
        }

        @Override public double area()      { return 0.5 * a * b; }
        @Override public double perimeter() { return a + b + Math.sqrt(a * a + b * b); }
    }

    // -------------------------------------------------------------------------
    // Exercise 4.2 — Printable and Saveable
    // -------------------------------------------------------------------------

    /** Contract for anything that can be printed to the console. */
    interface Printable {
        void print();
    }

    /** Contract for anything that can be serialized to a String. */
    interface Saveable {
        String serialize();
    }

    /**
     * Implements both interfaces.
     * The call site (printAll, saveAll) only sees the interface type,
     * not the concrete Product class.
     */
    static class Product implements Printable, Saveable {
        private String id;
        private String name;
        private double price;

        Product(String id, String name, double price) {
            this.id    = id;
            this.name  = name;
            this.price = price;
        }

        @Override
        public void print() {
            System.out.printf("  [%s] %s — $%.2f%n", id, name, price);
        }

        /** Returns a CSV row: id,name,price */
        @Override
        public String serialize() {
            return id + "," + name + "," + price;
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 4.3 — Strategy Pattern
    // -------------------------------------------------------------------------

    /** The strategy contract — each implementation decides how to process a payment. */
    interface PaymentStrategy {
        /** Attempts to pay the given amount. Returns true on success. */
        boolean pay(double amount);
    }

    /** Credit card strategy — always authorises the charge. */
    static class CreditCardPayment implements PaymentStrategy {
        private String cardNumber;

        CreditCardPayment(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        @Override
        public boolean pay(double amount) {
            // Show only the last 4 digits for security.
            String last4 = cardNumber.substring(cardNumber.length() - 4);
            System.out.printf("Charging $%.2f to card ending in %s.%n", amount, last4);
            return true;
        }
    }

    /** PayPal strategy — succeeds only when the account balance is sufficient. */
    static class PayPalPayment implements PaymentStrategy {
        private String email;
        private double balance;

        PayPalPayment(String email, double balance) {
            this.email   = email;
            this.balance = balance;
        }

        @Override
        public boolean pay(double amount) {
            if (amount > balance) {
                System.out.printf("PayPal payment failed: insufficient balance ($%.2f available).%n", balance);
                return false;
            }
            balance -= amount;
            System.out.printf("PayPal payment of $%.2f via %s.%n", amount, email);
            return true;
        }
    }

    /**
     * Bitcoin strategy — applies a 1% transaction fee.
     * The total cost (amount * 1.01) must not exceed the wallet balance.
     */
    static class BitcoinPayment implements PaymentStrategy {
        private double walletBalance;

        BitcoinPayment(double walletBalance) {
            this.walletBalance = walletBalance;
        }

        @Override
        public boolean pay(double amount) {
            double total = amount * 1.01; // 1% fee applied here.
            if (total > walletBalance) {
                System.out.printf(
                    "Bitcoin payment failed: insufficient wallet balance ($%.2f available, need $%.2f).%n",
                    walletBalance, total);
                return false;
            }
            walletBalance -= total;
            System.out.printf("Bitcoin payment: $%.2f + 1%% fee = $%.2f.%n", amount, total);
            return true;
        }
    }

    /**
     * A shopping cart whose payment strategy can be swapped at runtime.
     * This demonstrates the Strategy pattern: the cart is decoupled from
     * any specific payment implementation.
     */
    static class ShoppingCart {
        private double          total;
        private PaymentStrategy paymentStrategy;

        ShoppingCart(double total) {
            this.total = total;
        }

        /** Swap the strategy without modifying ShoppingCart itself. */
        void setPaymentStrategy(PaymentStrategy strategy) {
            this.paymentStrategy = strategy;
        }

        void checkout() {
            if (paymentStrategy == null) {
                System.out.println("No payment strategy set.");
                return;
            }
            boolean ok = paymentStrategy.pay(total);
            System.out.println("Payment " + (ok ? "successful." : "failed."));
        }
    }
}
