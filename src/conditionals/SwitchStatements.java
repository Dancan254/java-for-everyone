package conditionals;

import java.util.Scanner;

public class SwitchStatements {
    /**
     * An alternative for multiple if-else if statements
     Syntax
     int day
     switch(day){
        case 1:
            // output monday
            break;
        case 2:
            //output Tuesday
            break;
     ..........
        default:
            // invalid day
            break;
     }
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a day: ");
        int day = scanner.nextInt();

        switch (day){
            case 1: {
                System.out.println("ooooooh it's Monday");
                System.out.println("Bad day");
                }
                break;

            case 2:
                System.out.println("It's Tuesday");
                break;
            case 3:
                System.out.println("It's Wednesday");
                break;
            case 4:
                System.out.println("It's Thursday");
                break;
            default:
                System.out.println("Invalid day");
        }

        //better version
        switch (day){
            case 1 -> {
                System.out.println("It's Monday");
                System.out.println("Bad day");
            }
            case 2 -> System.out.println("It's Tuesday");
            case 3 -> System.out.println("It's Wednesday");
            case 4 -> System.out.println("It's Thursday");
            default -> System.out.println("Invalid day");
        }

        int month = 1;
        switch (month){
            case 12:
            case 1:
            case 2:
                System.out.println("It is winter");
                break;
            case 3:
            case 4:
            case 5:
                System.out.println("It is spring");
                break;
            default:
                System.out.println("It is summer");
                break;
        }

        switch (month){
            case 12, 1, 2 -> System.out.println("It is winter");
            case 3, 4, 5 -> System.out.println("It is spring");
            default -> System.out.println("It is summer");
        }
        scanner.close();
    }
}
