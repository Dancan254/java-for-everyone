package conditionals.SolutionsForPracticeExercises;

import java.util.ArrayList;
import java.util.Scanner;

public  class QuadraticEquationSolver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=====Quadratic Equation Solver=====");

        System.out.println("Enter the coefficients");
        System.out.println("a: ");
        int a = scanner.nextInt();
        System.out.println("b: ");
        int b = scanner.nextInt();
        System.out.println("c: ");
        int c = scanner.nextInt();

        System.out.println("=====Solution=====");

        double descriminant = (b*b) - (4*a*c);
        System.out.println("Descriminant = " + descriminant);

        if (descriminant > 0) {
            System.out.println("The equation has two real roots.");
        }else if (descriminant == 0) {
            System.out.println("The equation has one real solution.");
        }else if (descriminant < 0) {
            System.out.println("The equation has no real roots.");
        }else{
            System.out.println("Invalid equation!");
        }

        scanner.close();

    }
}