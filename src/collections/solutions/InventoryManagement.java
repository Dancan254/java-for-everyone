package collections.solutions;

import java.util.*;

/**
 * Exercise 3.3 — Inventory Management System
 *
 * Demonstrates an enum with a tax-rate field, multiple collection indexes
 * (List, Map by ID, Map by Category), and business-logic methods
 * operating on those structures.
 */
public class InventoryManagement {

    // ---- Enum with tax rate ----
    enum Category {
        ELECTRONICS(0.15),
        CLOTHING(0.08),
        FOOD(0.00),
        BOOKS(0.05),
        TOYS(0.10);

        private final double taxRate;

        Category(double taxRate) {
            this.taxRate = taxRate;
        }

        public double getTaxRate() {
            return taxRate;
        }
    }

    // ---- Product class ----
    static class Product {
        private final String id;
        private final String name;
        private final Category category;
        private final double price;
        private final int quantity;

        Product(String id, String name, Category category, double price, int quantity) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.price = price;
            this.quantity = quantity;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Category getCategory() {
            return category;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        @Override
        public String toString() {
            return String.format("%-6s %-20s %-12s $%8.2f  qty: %d",
                    id, name, category, price, quantity);
        }
    }

    // ---- Business methods ----

    /** Sum of price × quantity for all products. */
    public static double calculateTotalValue(List<Product> products) {
        double total = 0;
        for (Product p : products) {
            total += p.getPrice() * p.getQuantity();
        }
        return total;
    }

    /** Tax on a single product (price × quantity × category tax rate). */
    public static double calculateTaxForProduct(Product p) {
        return p.getPrice() * p.getQuantity() * p.getCategory().getTaxRate();
    }

    /** Total tax owed per category. */
    public static Map<Category, Double> taxByCategory(List<Product> products) {
        Map<Category, Double> taxes = new EnumMap<>(Category.class);
        for (Category c : Category.values()) {
            taxes.put(c, 0.0);
        }
        for (Product p : products) {
            taxes.put(p.getCategory(),
                    taxes.get(p.getCategory()) + calculateTaxForProduct(p));
        }
        return taxes;
    }

    /** Products with quantity below the given threshold. */
    public static List<Product> lowStock(List<Product> products, int threshold) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getQuantity() < threshold) {
                result.add(p);
            }
        }
        return result;
    }

    // ---- Main ----

    public static void main(String[] args) {

        // 1. Product list
        List<Product> products = new ArrayList<>(Arrays.asList(
                new Product("E001", "Laptop", Category.ELECTRONICS, 999.99, 12),
                new Product("E002", "Wireless Mouse", Category.ELECTRONICS, 29.99, 45),
                new Product("C001", "Winter Jacket", Category.CLOTHING, 89.99, 8),
                new Product("C002", "Running Shoes", Category.CLOTHING, 64.99, 22),
                new Product("F001", "Organic Apples", Category.FOOD, 4.99, 100),
                new Product("F002", "Almond Milk", Category.FOOD, 3.49, 60),
                new Product("B001", "Java in Action", Category.BOOKS, 44.99, 3),
                new Product("B002", "Clean Code", Category.BOOKS, 39.99, 15),
                new Product("T001", "LEGO Set", Category.TOYS, 59.99, 5),
                new Product("T002", "Board Game", Category.TOYS, 24.99, 18)));

        // 2. Index by ID
        Map<String, Product> idIndex = new HashMap<>();
        for (Product p : products) {
            idIndex.put(p.getId(), p);
        }

        // 3. Group by category
        Map<Category, List<Product>> categoryIndex = new EnumMap<>(Category.class);
        for (Category c : Category.values()) {
            categoryIndex.put(c, new ArrayList<>());
        }
        for (Product p : products) {
            categoryIndex.get(p.getCategory()).add(p);
        }

        // ---- Inventory Report ----
        System.out.println("===== INVENTORY REPORT =====\n");

        // All products by category
        for (Map.Entry<Category, List<Product>> entry : categoryIndex.entrySet()) {
            System.out.println("--- " + entry.getKey() + " (tax: "
                    + (int) (entry.getKey().getTaxRate() * 100) + "%) ---");
            for (Product p : entry.getValue()) {
                System.out.println("  " + p);
            }
        }

        // Total value
        double totalValue = calculateTotalValue(products);
        System.out.printf("%nTotal inventory value: $%,.2f%n", totalValue);

        // Tax breakdown
        Map<Category, Double> taxes = taxByCategory(products);
        System.out.println("\n--- Tax Breakdown by Category ---");
        double totalTax = 0;
        for (Map.Entry<Category, Double> entry : taxes.entrySet()) {
            System.out.printf("  %-12s : $%,.2f%n", entry.getKey(), entry.getValue());
            totalTax += entry.getValue();
        }
        System.out.printf("  %-12s : $%,.2f%n", "TOTAL TAX", totalTax);

        // Low stock warnings
        int threshold = 10;
        List<Product> lowStockItems = lowStock(products, threshold);
        System.out.println("\n--- Low Stock (< " + threshold + " units) ---");
        if (lowStockItems.isEmpty()) {
            System.out.println("  All products are well-stocked.");
        } else {
            for (Product p : lowStockItems) {
                System.out.println("  ⚠ " + p.getName() + " (" + p.getId()
                        + ") — only " + p.getQuantity() + " left");
            }
        }

        // Quick lookup demo
        System.out.println("\n--- Quick Lookup ---");
        Product found = idIndex.get("B001");
        System.out.println("Lookup B001: " + (found != null ? found : "not found"));
    }
}
