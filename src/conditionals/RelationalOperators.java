package conditionals;

public class RelationalOperators {

    public static void main(String[] args) {
        int a = 7;
        int b = 10;
        int c = 15;

        // 7 == 8
        boolean isEqualTo = a==b;
        System.out.println("Is " + a + " equal to " + b + "? " + isEqualTo );
        boolean isNotEqualTo = a!=b;
        System.out.println("Is " + a + " not equal to " + b + "? " + isNotEqualTo );
        boolean isLessThan = b < c;
        System.out.println("Is " + b + " less than " + c + "?" + isLessThan );
    }
}
