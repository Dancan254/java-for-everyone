package conditionals;

import java.util.Scanner;

public class NestedConditions {
    public static void main(String[] args) {
        /*
        if(first condition){
                if(nested condition) {
                    //do sth here
                 } else {
                    //do an altenative
                 }
          } else if(2nd condition){
            if(condition){
                // do sth
             } else{
                //do sth
             }
         } else {
                //do sth if the above fails
         }
         */
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to wings to fly, we help people get scholarships");
        System.out.println("=".repeat(40));
        System.out.println("What grade did you get in your KCSE: ");
        String grade = scanner.nextLine();
        System.out.println("What's your family income in KES: ");
        double income = scanner.nextDouble();
        // if the student got an A and family income < 15000 -> full scholarship
        // """ family income > 500000 -> give them a partial scholarship
        // == > dont use this to compare Strings -> instead use .equals() -> compare the actual values in the strings
        if (grade.equals("A")){
            if ((income <= 15000)){
                System.out.println("You got a full scholarship");
            } else if (income < 100000){
                System.out.println("You got a partial scholarship");
            } else {
                System.out.println("You are eligible for a merit-based scholarship only");
            }
        } else if (grade.equals("B")){
            if (income <= 60000){
                System.out.println("Eligible for need-based scholarship");
            } else {
                System.out.println("Not eligible for scholarship");
            }
        } else{
            System.out.println("You are not eligible for a scholarship");
        }

        scanner.close();
    }
}
