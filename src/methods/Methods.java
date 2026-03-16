package methods;
public class Methods {
    public static void main(String[] args) {
        boolean isEven = 4 %2 == 0;
        printName("Java");

        String message = new Methods().getMessage();
        System.out.println(message);

        System.out.println(new Methods().getMessage());

        Methods methods = new Methods();
        methods.getMessage();

        System.out.println();

        Message message1 = new Message();
        message1.printHelloNon();
        new Message().printHelloNon();

        Message.printHello();

        printName("Java");

       String ourMessage = new Methods().getMessage();

       int x = 90;
       passByValue(x);
        System.out.println(x);
    }
    public static void passByValue(int x){
        x = 100;
    }
    public static void printName(String name){
        System.out.println("Name is " + name);
    }

    public String getMessage() {
        String message = "Hello, World!";
        return message;
    }

    public boolean isEven(int num){
        return num % 2 == 0;
    }

    public static int add(int a, int b){
        return a + b;
    }

    public static int add(double a, int b){
        return (int)a + b;
    }

    public static int add(int a, int b, int c){
        return a + b + c;
    }
}
