package conditionals;
import java.util.List;
import java.util.Scanner;

public class Conditionals {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a number (1-7): ");
        int day = scanner.nextInt();

        switch (day) {
            case 1 -> System.out.println("Monday");
            case 2 -> System.out.println("Tuesday");
            case 3 -> System.out.println("Wednesday");
            case 4 -> System.out.println("Thursday");
            case 5 -> System.out.println("Friday");
            case 6 -> System.out.println("Saturday");
            case 7 -> System.out.println("Sunday");
            default -> System.out.println("Invalid day number");
        }


        //String result = divide(10, 0);

        int month = 3;

        switch (month) {
            case 12, 1, 2 -> System.out.println("Winter");
            case 3, 4, 5 -> System.out.println("Spring");
            case 6, 7, 8 -> System.out.println("Summer");
            case 9, 10, 11 -> System.out.println("Fall");
            default -> System.out.println("Invalid month");
        }

        int age = 20;
        if (age >= 18) {
            System.out.println("You are old enough to vote");
        } else {
            System.out.println("You cannot vote");
        }
        String result = (age >=18) ? "you can vote" : "you cant vote";

        int value1 = 10, value2 = 20;
        if(value1 > value2){
            System.out.println("max is: " + value1);
        }else{
            System.out.println("max is: " + value2);
        }
        int max = (value1 > value2) ? value1 : value2;
        System.out.println("max is: " + max);

        String result2 = (value1 > value2) ? "max is " + value1 : "max is " + value2;

        int score = 90;
        char grade = (score >= 90) ? 'A' : (score >= 80) ? 'B' : (score >=70) ? 'C' : 'D';
    }

    public static int findMax(int a, int b){
        if(a > b) return a;
        else return b;
    }

    public static String divide(int a, int b) {
        // a/b
        if (b == 0) {
            return "Cannot divide by 0";
        } else {
            int result = a / b;
            return String.valueOf(result);
        }
    }
}
