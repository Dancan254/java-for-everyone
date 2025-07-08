package introduction.swap;

public class VariableSwap {

    public static void main(String[] args) {

        int a = 90;
        int b = 80;
        System.out.println("a = " + a + " b = " + b);

        int temp = a;
        a = b;
        b =temp;

        System.out.println("After Swapping, the !magic happens");
        System.out.println("a = " + a + " b = " + b);
    }
}
