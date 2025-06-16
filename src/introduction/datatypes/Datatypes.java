package introduction.datatypes;

public class Datatypes {
    public static void main(String[] args) {
        // datatype nameOfVariable  = value;

        //byte - 8bits
        byte num = 27;
        System.out.println(num);

        //short 16 bits
        short  shortNum = 3455;
        System.out.println(shortNum);

        //int 32 bits
        int number2 = 2;
        System.out.println(number2);

        //long 64 bits
        long biggerNumber = 500000000L;
        System.out.println(biggerNumber);

        //Decimals
        float decimal = 2.5f;
        System.out.println(decimal);

        double decimal2 = 2.5;
        System.out.println(decimal2);

        char grade = 'A';
        char symbol = '$';
        char unicodeChar = '\u0096';
        char newline = '\n';

        System.out.println(unicodeChar);

        //Non primitive
        String name = "John Doe";
        String message = "Welcome to Java!";
        String empty = "";
        System.out.println(name);

        int num4 = 7;

//        int a, b, c, d;
//        a = 1;
//        b = 2;
//        c = 3;
//        d = 4;
//
//        int sum = a + b + c + d;
//        System.out.println(sum);

        int e = 7, g = 8, f = 9;
        int h = e + g + f;
        System.out.println(h);

        int a = 10, b = 3;

        int sum = a + b;        // 13
        int difference = a - b; // 7
        int product = a * b;    // 30
        int quotient = a / b;   // 3 (integer division)
        int remainder = a % b;  // 1

        System.out.println("sum" + sum);
        System.out.println("diff" + difference);
        System.out.println("product" + product);
        System.out.println(quotient);
        System.out.println(remainder);

        int score = 85;
        score += 10;    // score is now 95
        score -= 5;     // score is now 90
        score *= 2;     // score is now 180
        score /= 3;     // score is now 60
        score %= 7;

        System.out.println("Increment and decrement operators");
        int x = 5;
        int y = ++x;
        System.out.println(y);
        int z = 5;
        int w = z++;
        System.out.println(w);

        System.out.println(z);

        int p = 10+2*5;
        System.out.println(p);

        System.out.println("Number " + 20 + 5);

        int number8 = 8;
        double deci8 = number8;
        System.out.println(deci8);

        double deci9 = 9.3;
        int number9 = (int) deci9;
        System.out.println(number9);

        int total = 10;
        String ten = String.valueOf(total);
        System.out.println(ten);

        String numString = "2344";
        int numberStr = Integer.parseInt(numString);
        System.out.println(numberStr);
    }
}
