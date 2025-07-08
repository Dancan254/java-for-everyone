package introduction;

import java.util.Scanner;

public class Simple {
    public static void main(String[] args) {

        // ❌ Problem: nextLine() after nextInt()
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your age:");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter your name");
        String name = scanner.nextLine();  // This will be empty!
        System.out.println("Your name is " + name + " and your age is " + age);
//        int age = scanner.nextInt();
//        scanner.nextLine();  // Consume the newline
//        String name = scanner.nextLine();
    }
}
