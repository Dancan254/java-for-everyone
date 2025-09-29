package loops;

import java.util.Scanner;

public class WhileLoop {
    public static void main(String[] args) {
//        int counter = 10;
//        System.out.println("Countdown has started........");
//        while(counter > 0){
//            System.out.println(counter);
//            counter--;
//        }
//
//        System.out.println("Boooooooom, failed to detonate it");

        Scanner scanner = new Scanner(System.in);
        int sum =0;
        int number;

        System.out.println("Enter numbers to add, 0 to stop");
        while (true){
            System.out.println("Enter a number: ");
            number = scanner.nextInt();
            if (number == 0){
                break;
            }
             sum += number;
            System.out.println("Current sum is: " + sum);
        }

        System.out.println("Final sum is: " + sum);
        scanner.close();
    }
}
