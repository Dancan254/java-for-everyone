package introduction;

import java.util.Scanner;

public class HelloWorld {

    public static void main(String[] args) {
//        Scanner input = new Scanner(System.in);
//        System.out.println("What is your name: ");
//        String name2 = input.nextLine();
//        System.out.println("enter a number: ");
//        double number = input.nextDouble();
//        System.out.println("number: " + number);
//        System.out.println("Nice to meet you, " + name2 + ".");

        for(int i = 0; i < 10; i++){
            if (i == 5){
                continue;
            }
            System.out.println(i);
        }
    }
}
