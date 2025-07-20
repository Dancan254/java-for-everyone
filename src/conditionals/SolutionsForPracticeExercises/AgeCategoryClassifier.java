package conditionals.SolutionsForPracticeExercises;

import java.util.Scanner;

public class AgeCategoryClassifier {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hey there, let's find out your age category:)");
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();

        System.out.println("=====Age Classifier=====");
        System.out.println("Hey " + name + ".");

        int age;

        while (true) {
            System.out.println("Enter your current age: ");
            if (scanner.hasNextInt()) {
                age = scanner.nextInt();

                if (age < 2) {
                    System.out.println("You're a baby:)");
                }else if (age <= 12 ) {
                    System.out.println("You're a child:)");
                }else if (age <= 19) {
                    System.out.println("You're a teenager:)");
                }else if (age <= 59) {
                    System.out.println("You're an adult:)");
                }else if (age >= 60) {
                    System.out.println("You're a senior:)");
                }else{
                    System.out.println("Invalid age!");
                }


                scanner.close();
                return;
            }else{
                scanner.next();
            }
            System.out.println("Invalid age!");
        }



     
        
    }

    
}