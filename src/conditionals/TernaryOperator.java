package conditionals;

public class TernaryOperator {
    /*
    Ternary Operator -> used instead of if-else statements -> for simple conditions
    Syntax:
    variable = (condition) ? value_if_true : value_if_false;
     */
    public static void main(String[] args) {

        int age = 16;
        if(age > 18){
            String message = "You can vote";
            System.out.println(message);
        }else{
            System.out.println("you can't vote");
        }
        //how do we feel feel senior be representing this in a ternary op
        String message2 = (age > 18) ? "you can vote" : "you can't vote";
        System.out.println(message2);

        int score = 85;
        String results = (score >= 80) ? "You got a A" : "You failed";
        System.out.println(results);

        int a = 6, b = 7;
        int max = (a > b) ? a : b;
        System.out.println("The max is: " + max);

        int score1 = 90;
        if (score1 >= 90){
            System.out.println("You got a A");
        } else if (score1 >= 80) {
            System.out.println("You got a B");
        }else {
            System.out.println("You got a C");
        }

        String grade = (score1 >= 90) ? "A" : (score1 >=80) ? "B" : "C";
        System.out.println("Your grade is: " + grade);

        int number = 90;
        String result3 = (number % 2 == 0) ? "Even" : "Odd";
        System.out.println(result3);

        String str = null;
        String str2 = "I am not null";
        if (str != null && str.equals(str2) ) {
            System.out.println("Both are equal");
        }

    }
}
