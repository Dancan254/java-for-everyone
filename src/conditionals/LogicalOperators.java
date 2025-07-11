package conditionals;

public class LogicalOperators {
    public static void main(String[] args) {
        // && -> both conditions have to be met
        // || -> at least one condition has to be met
        // ! => it negates the current condition

        // Practical examples
        int score = 85;
        boolean hasGoodGrade = score >= 80 && score <= 100;
        //boolean needsHelp = score < 60 || score > 100;
        boolean isNotPassing = !(score >= 60);

        System.out.println("Has good grade: " + hasGoodGrade);
        System.out.println("Not passing: " + isNotPassing);


        boolean result = 5 > 3 && 2 < 4 || !false;
        // 5 > 3 && 2 < 4 || true
        // true && true || true
        // true || true
        //true
    }
}
