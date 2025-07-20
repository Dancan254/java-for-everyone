package conditionals.SolutionsForPracticeExercises;

import java.util.Scanner;

public class TrafficLightSystem {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hey there, let's help you manuever traffic and avoid accidents:)");

        System.out.println("What's the current traffic light color?");
        String currentColor = scanner.nextLine();

        String nextColor;
        String action;

        if (currentColor.equalsIgnoreCase("red")) {
            nextColor = "Green";
            action = "Go!";
        }else if (currentColor.equalsIgnoreCase("yellow")) {
            nextColor = "Red";
            action = "Stop!";
        }else if (currentColor.equalsIgnoreCase("green")) {
            nextColor = "Yellow";
            action = "Prepare to stop:)";
        }else{
            nextColor = "Invalid";
            action = "Invalid! Please enter the right traffic color.";
        }

        scanner.close();

        System.out.println("=====Traffic Results=====");
        System.out.println("Current color: " + currentColor);
        System.out.println("Next color: " + nextColor);
        System.out.println("Action: " + action);
    }
    
}