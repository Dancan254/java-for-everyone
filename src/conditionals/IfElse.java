package conditionals;

import java.util.Scanner;

public class IfElse {
    public static void main(String[] args) {
        /*
        if(condition){
            //your code goes here
          } else{
                alternative
             }
         */
        Scanner scanner = new Scanner(System.in);
        System.out.println("How old are you?" );
        int age = scanner.nextInt();

        if(age >= 18){
            System.out.println("You are allowed to vote");
        } else{
            System.out.println("You are not allowed to vote");
            System.out.println("You are too young");
        }
        System.out.println("Enter a day: ");
        int day = scanner.nextInt();

        if (day == 1) {
            System.out.println("It is a Monday");
        } else if (day ==2) {
            System.out.println("It is a Tuesday");
        } else if (day == 3) {
            System.out.println("It is a Wednesday");
        } else if (day == 4) {
            System.out.println("It is a Thursday");
        } else if (day == 5) {
            System.out.println("It is a Friday");
        }else if (day == 6) {
            System.out.println("It is a Saturday");
        }else if (day == 7) {
            System.out.println("It is a Sunday");
        } else{
            System.out.println("Invalid day");
        }

        switch (day){
            case 1 -> System.out.println("It is a Monday");
            case 2 -> System.out.println("It is a Tuesday");
            case 3 -> System.out.println("It is a Wednesday");
            case 4 -> System.out.println("It is a Thursday");
            case 5 -> System.out.println("It is a Friday");
            case 6 -> System.out.println("It is a Saturday");
            case 7 -> System.out.println("It is a Sunday");
            default -> System.out.println("Invalid day");
        }
        scanner.close();
    }
}
