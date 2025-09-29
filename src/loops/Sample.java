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

        for (int i = 1; i <=100 ; i++) {
            if (i % 7 == 0){
                System.out.println("Yeeeiy got the number: " + i);
                break;
            }
        }

        // 1-10 -> odd - even-skip it -> continue
        for (int i = 1; i <=10 ; i++) {
            if (i % 2 == 0){
                continue;
            }
            System.out.println("Odd: " + i);
        }
    }
}
