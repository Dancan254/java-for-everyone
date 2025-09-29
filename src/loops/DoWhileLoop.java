package loops;

import java.time.LocalTime;
import java.util.Scanner;

public class DoWhileLoop {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do{
            System.out.println("\n--- Fun Menu ---");
            System.out.println("1. Tell a joke");
            System.out.println("2. Random number");
            System.out.println("3. Current time");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice){
                case 1 -> {
                    System.out.println("Why don't scientists trust atoms? Because they make up everything! 😄");
                    System.out.println("Just trying");
                }
                case 2 -> System.out.println("Your random number is: " + (int) (Math.random() * 100));
                case 3 -> System.out.println("Current time is: " + LocalTime.now());
                case 0 -> System.out.println("See you next time!");
                default -> System.out.println("Invalid option");
            }
        } while(choice != 0);

        String password = "";
        do{
            System.out.println("Create a password whose length > 6");
            password = scanner.nextLine();
            if (password.length() < 6){
                System.out.println("Password must be at least 6 characters long, Weak!!!!");
            }
        }while (password.length() < 6);

        System.out.println("Your app is in safe hands, nice password");
        scanner.close();
    }
}
