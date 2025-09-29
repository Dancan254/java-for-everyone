package loops;

public class Loops {
    public static void main(String[] args) {
        /**
         for (initialization; condition; increment/decrement) {
                code
         }
         */
        //post increment and pre increment
        //we want to print 1-10
        for(int index = 1; index <=10; index++){
            System.out.println("number: " + index);
        }
        //print the first 10 natural number and their squares
        for(int i = 1; i<=10; i++){
            int square = i*i;
            System.out.println("Number: " + i + " Square: " + square);
        }


        //enhanced for loop
        /**
         for each number in the array do this(multiply by2, print the number, modulus)
         an array is called numbers
         for(Type eachNumber : numbers){
            multiply eachNumber by 2
         }
         */
        int[] numbers = {1,2,3,4,5,6,7,8,9,10};
        for(int number : numbers){
            int result = number*2;
            System.out.println("Number: " + result + " Modulus: " + result);
        }
        /*
          star pattern
                        *
                        **
                        ***
                        *****
                        *****
         */
        //
         //row 1 -> columns 1
        //row2 -> 2 cols
        //row 3 -> 3 cols
        //number of columns == number of that row
        for(int i = 1; i<=5; i++){
            for(int j = 1; j<=i; j++){
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
