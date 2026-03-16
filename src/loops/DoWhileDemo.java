package loops;

import java.util.Scanner;

public class DoWhileDemo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
//        System.out.println("Enter numbers apart zero");
//        while (true){
//            System.out.println("enter a number:");
//            int number = input.nextInt();
//
//            if(number == 0){
//                break;
//            }
//            System.out.println("hello");
//        }
//        int num = 0;
//        do {
//            System.out.println("Hello");
//            num++;
//        }while(num <= 5 );

        String password;

        do {
            System.out.print("Create a password (at least 6 characters): ");
            password = input.nextLine();

            if (password.length() < 6) {
                System.out.println("Too short! Make it longer.");
            }
        } while (password.length() < 6);
        System.out.println("Great password!");

        int choice;
        do{
            System.out.println("Choose an operation");
            System.out.println("1. buy data");
            System.out.println("2. buy minutes");
            System.out.println("0 . exit");
            choice = input.nextInt();
            switch (choice){
                case 1 -> System.out.println("Buying data");
                case 2 -> System.out.println("Buying minutes");
                case 0 -> System.out.println("Exiting");
                default -> System.out.println("Invalid choice");
            }
        }while (choice != 0);

        input.close();
        /*int number = 6;

        for (int i = 1; i <= 5; i++) {
               if ( i == 3){
                   break;
               }
            System.out.println(i);
        }
        int count = 3;
        System.out.println("Starting count down");
        while(count > 0){
            System.out.println(count);
            --count;
        }

        divide(number, 0);

         */
    }

    public static void divide(int a, int b){
        if(b == 0) return;
        System.out.println(a/b);
    }
}
