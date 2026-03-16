package collections.solutions;

import java.util.*;

/**
 * Exercise 4.1 — Order Processing System
 *
 * Demonstrates a real-world-style simulation using two enums,
 * a domain class, and multiple collection types (List, Map, EnumMap).
 */
public class OrderProcessingSystem {

    // ---- Enums ----

    enum OrderStatus {
        PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }

    enum PaymentMethod {
        CREDIT_CARD, DEBIT_CARD, PAYPAL, BANK_TRANSFER
    }

    // ---- Order class ----

    static class Order {
        private final String orderId;
        private final String customerName;
        private OrderStatus status;
        private final PaymentMethod payment;
        private final List<String> items;
        private final double total;

        Order(String orderId, String customerName, PaymentMethod payment,
                List<String> items, double total) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.status = OrderStatus.PENDING;
            this.payment = payment;
            this.items = items;
            this.total = total;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public OrderStatus getStatus() {
            return status;
        }

        public PaymentMethod getPayment() {
            return payment;
        }

        public double getTotal() {
            return total;
        }

        public void setStatus(OrderStatus status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return String.format("Order[%s, %s, %s, $%.2f, %s]",
                    orderId, customerName, status, total, items);
        }
    }

    // ---- Status advancement ----

    /**
     * Advances an order to the next logical status:
     * PENDING -> CONFIRMED -> SHIPPED -> DELIVERED.
     * Returns false if the order is already DELIVERED or CANCELLED.
     */
    public static boolean advanceStatus(Order order) {
        switch (order.getStatus()) {
            case PENDING:
                order.setStatus(OrderStatus.CONFIRMED);
                return true;
            case CONFIRMED:
                order.setStatus(OrderStatus.SHIPPED);
                return true;
            case SHIPPED:
                order.setStatus(OrderStatus.DELIVERED);
                return true;
            case DELIVERED:
            case CANCELLED:
                return false;
            default:
                return false;
        }
    }

    /**
     * Cancels an order only if it is PENDING or CONFIRMED.
     */
    public static void cancelOrder(Order order) {
        if (order.getStatus() == OrderStatus.PENDING
                || order.getStatus() == OrderStatus.CONFIRMED) {
            order.setStatus(OrderStatus.CANCELLED);
            System.out.println("  Cancelled: " + order.getOrderId());
        } else {
            System.out.println("  Cannot cancel " + order.getOrderId()
                    + " (status: " + order.getStatus() + ")");
        }
    }

    // ---- Grouping helpers ----

    public static Map<OrderStatus, List<Order>> groupByStatus(List<Order> orders) {
        Map<OrderStatus, List<Order>> map = new EnumMap<>(OrderStatus.class);
        for (OrderStatus s : OrderStatus.values()) {
            map.put(s, new ArrayList<>());
        }
        for (Order o : orders) {
            map.get(o.getStatus()).add(o);
        }
        return map;
    }

    public static Map<PaymentMethod, Double> revenueByPayment(List<Order> orders) {
        Map<PaymentMethod, Double> map = new EnumMap<>(PaymentMethod.class);
        for (PaymentMethod pm : PaymentMethod.values()) {
            map.put(pm, 0.0);
        }
        for (Order o : orders) {
            map.put(o.getPayment(), map.get(o.getPayment()) + o.getTotal());
        }
        return map;
    }

    public static EnumMap<OrderStatus, Integer> countByStatus(List<Order> orders) {
        EnumMap<OrderStatus, Integer> counts = new EnumMap<>(OrderStatus.class);
        for (OrderStatus s : OrderStatus.values()) {
            counts.put(s, 0);
        }
        for (Order o : orders) {
            counts.put(o.getStatus(), counts.get(o.getStatus()) + 1);
        }
        return counts;
    }

    // ---- Main ----

    public static void main(String[] args) {

        // Create six orders
        List<Order> orders = new ArrayList<>(Arrays.asList(
                new Order("ORD-001", "Alice", PaymentMethod.CREDIT_CARD,
                        Arrays.asList("Laptop", "Mouse"), 1029.98),
                new Order("ORD-002", "Bob", PaymentMethod.PAYPAL,
                        Arrays.asList("Headphones"), 79.99),
                new Order("ORD-003", "Carol", PaymentMethod.DEBIT_CARD,
                        Arrays.asList("Keyboard", "Monitor"), 549.98),
                new Order("ORD-004", "Dave", PaymentMethod.BANK_TRANSFER,
                        Arrays.asList("Desk Chair"), 299.99),
                new Order("ORD-005", "Eve", PaymentMethod.CREDIT_CARD,
                        Arrays.asList("USB Hub", "Webcam"), 94.98),
                new Order("ORD-006", "Frank", PaymentMethod.PAYPAL,
                        Arrays.asList("Book"), 24.99)));

        // Advance some orders through the pipeline
        System.out.println("--- Advancing orders ---");
        advanceStatus(orders.get(0)); // PENDING -> CONFIRMED
        advanceStatus(orders.get(0)); // CONFIRMED -> SHIPPED
        advanceStatus(orders.get(0)); // SHIPPED -> DELIVERED

        advanceStatus(orders.get(1)); // PENDING -> CONFIRMED
        advanceStatus(orders.get(1)); // CONFIRMED -> SHIPPED

        advanceStatus(orders.get(2)); // PENDING -> CONFIRMED

        // Cancel order 4 (Dave) — should succeed (PENDING)
        cancelOrder(orders.get(3));

        // Try to cancel order 1 (Alice) — should fail (DELIVERED)
        cancelOrder(orders.get(0));

        // ---- Reports ----

        // Orders grouped by status
        System.out.println("\n--- Orders by Status ---");
        Map<OrderStatus, List<Order>> byStatus = groupByStatus(orders);
        for (Map.Entry<OrderStatus, List<Order>> entry : byStatus.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                System.out.println(entry.getKey() + ":");
                for (Order o : entry.getValue()) {
                    System.out.println("  " + o);
                }
            }
        }

        // Revenue per payment method
        System.out.println("\n--- Revenue by Payment Method ---");
        Map<PaymentMethod, Double> revenue = revenueByPayment(orders);
        for (Map.Entry<PaymentMethod, Double> entry : revenue.entrySet()) {
            System.out.printf("  %-15s : $%,.2f%n", entry.getKey(), entry.getValue());
        }

        // Count per status
        System.out.println("\n--- Order Count by Status ---");
        EnumMap<OrderStatus, Integer> counts = countByStatus(orders);
        for (Map.Entry<OrderStatus, Integer> entry : counts.entrySet()) {
            System.out.printf("  %-10s : %d%n", entry.getKey(), entry.getValue());
        }
    }
}
