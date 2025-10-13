import methods.LearningMethods;

import java.util.Scanner;

public class Audi {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Do you have money?");
        String response1 = input.nextLine();

        if(response1.equalsIgnoreCase("yes")){
            System.out.println("Buy Audi");
        }
        else if(response1.equalsIgnoreCase("no")){
            System.out.println("Do you have a job?");
            String response2 = input.nextLine();

            if(response2.equalsIgnoreCase("yes")){
                System.out.println("Do they pay you?");
                String response3 = input.nextLine();

                if(response3.equalsIgnoreCase("yes")){
                    System.out.println("Buy Audi");
                }
            }
            else{
                System.out.println("Do you have possessions?");
                String response4 = input.nextLine();
                if(response4.equalsIgnoreCase("yes")){
                    System.out.println("Buy audi");
                }
                else{
                    System.out.println("Do you have a kidney?");
                    String response5 = input.nextLine();
                    if(response5.equalsIgnoreCase("yes")){
                        System.out.println("Sell it buy Audi");
                    }
                    else{
                        System.out.println("You are a liar sell it buy Audi");
                    }
                }
            }
        }
        else{
            System.out.println("Invalid");
        }
        input.close();
    }
}
