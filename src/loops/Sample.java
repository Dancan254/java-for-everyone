package loops;

public class Sample {
    public static void main(String[] args) {
        //pre increment
        int number = 90;
        int number2 = ++number; //-> 91
        System.out.println(number); //91
        System.out.println(number2);

        //post
        int number3 = 80;
        int number4 = number3++; //number -> 80
        //number -> 81
        System.out.println(number3);
        System.out.println(number4);
    }
}
