package conditionals;

import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your score (0-100): ");
        int score = scanner.nextInt();

        if (score >= 90 && score <= 100) {
            // 90 -100
            System.out.println("Grade: A");
        } else if (score >= 80 && score <= 89) {
            // 80 -89
            System.out.println("Grade: B");
        } else if (score >= 70 && score <= 79) {
            //70-79
            System.out.println("Grade: C");
        } else if (score >= 60 && score <= 69) {
            //60-69
            System.out.println("Grade: D");
        } else {
            System.out.println("Grade: F");
        }

        scanner.close();
    }
}
