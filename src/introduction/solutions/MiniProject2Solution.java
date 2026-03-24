package introduction.solutions;

import java.util.Scanner;

/**
 * MiniProject2Solution.java
 *
 * Solution for Mini-Project 2: Shopping Receipt Generator.
 * Reads three items from the user and prints a formatted receipt with subtotal,
 * tax at 8.5%, and total.
 */
public class MiniProject2Solution {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] names  = new String[3];
        double[] prices = new double[3];

        for (int i = 0; i < 3; i++) {
            System.out.print("Enter item " + (i + 1) + " name: ");
            names[i] = scanner.nextLine();

            System.out.print("Enter item " + (i + 1) + " price: ");
            prices[i] = scanner.nextDouble();
            scanner.nextLine(); // consume trailing newline before next name read
        }

        double subtotal = prices[0] + prices[1] + prices[2];
        double tax      = subtotal * 0.085;
        double total    = subtotal + tax;

        String border  = "=".repeat(40);
        String divider = "-".repeat(40);

        System.out.println("\n" + border);
        System.out.println("           SHOPPING RECEIPT");
        System.out.println(border);
        for (int i = 0; i < 3; i++) {
            System.out.println(String.format("%-20s $%6.2f", names[i], prices[i]));
        }
        System.out.println(divider);
        System.out.println(String.format("%-20s $%6.2f", "Subtotal:", subtotal));
        System.out.println(String.format("%-20s $%6.2f", "Tax (8.5%):", tax));
        System.out.println(String.format("%-20s $%6.2f", "Total:", total));
        System.out.println(border);

        scanner.close();
    }
}
