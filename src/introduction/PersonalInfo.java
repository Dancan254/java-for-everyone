package introduction;

import java.util.Scanner;

public class PersonalInfo {

    public static void main(String[] args) {
        //object for scanner
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to my personal info program");
        System.out.println("=".repeat(35));

        System.out.println("What is your first name? ");
        String firstname = input.nextLine();

        System.out.println("What is your last name? ");
        String lastname = input.nextLine();

        System.out.print("Enter your current age: ");
        int currentAge = input.nextInt();

        System.out.print("Enter your birth year: ");
        int birthYear = input.nextInt();

        System.out.print("Enter your favorite number: ");
        double favoriteNumber = input.nextDouble();


        //calculations
        String fullName = firstname + " " + lastname;
        int ageNextYear = currentAge + 1;
        int currentYear = 2025;
        int calculatedAge = currentYear - birthYear;
        double doubledFavoriteNumber = favoriteNumber * 2;
        double squaredFavoriteNumber = favoriteNumber * favoriteNumber;

        // Display results
        System.out.println("\n" + "=".repeat(40));
        System.out.println("         PERSONAL INFORMATION REPORT");
        System.out.println("Hi, " + fullName + " welcome to my personal info program");
        System.out.println("You are " + calculatedAge + " years old");
        System.out.println("You will be " + ageNextYear + " years old next year");
        System.out.println("Your favorite number squared is " + squaredFavoriteNumber);
        System.out.println("Your favorite number doubled is " + doubledFavoriteNumber);

        //fun facts
        int daysLived = currentAge * 365;
        int hoursLived = daysLived * 24;
        System.out.println("\n--- Fun Calculations ---");
        System.out.println("Approximate days lived: " + daysLived);
        System.out.println("Approximate hours lived: " + hoursLived);

        // != -> not equal to
         if (calculatedAge != currentAge) {
            System.out.println("\nNote: There's a difference between your stated age and calculated age.");
            System.out.println("This might be due to birthday timing within the year.");
        }
        input.close();
        System.out.println("Thank you for using my personal info program. Goodbye!");
        System.out.println("=".repeat(40));
        System.out.println("If you wanna use it again, press the run button");
    }
}
