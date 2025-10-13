package methods;

public class LearningMethods {

    public static void main(String[] args) {
        //you call it here
        // datatype x = methodName(4, 5);
        //sout(x)
        int a = 90;
        int b = 80;
        int result = add(a, b);
        System.out.println("the sum is " + result);

        //alternatively
        System.out.println(add(a, b));
        System.out.println("With void");
        add2(a, b);

        //instance methods
        //ClassName variableName = new ClassName();
        //variableName.methodName();
        LearningMethods lm = new LearningMethods();
        int product = lm.multiply(a, b);
        System.out.println(product);
        //static
        //ClassName.methodName();
        int resultSum = addNums(1, 3, 5, 7, 9, 9, 9, 10);
        System.out.println("Add using var args");
        System.out.println("The sum is: " +resultSum);

        System.out.println("Pass by value");
        int x = 100;
        passByValue(100);
        System.out.println(x);
    }
    static void passByValue(int x){
        x = 200;
    }

    protected static int addNums(int... numbers){
        int sum = 0;
        for(int num : numbers){
            sum += num;
        }
        return sum;
    }
    /*

     */
    public int multiply(int a, int b){
        return a * b;
    }
    /*
    accessModifier returnType methodName(param a, param b){
    //code
    return sth;
    }
     */
    public static int add(int a, int b){
        return a + b;
    }
    public static int  add(int a, int b, int c){
        return a + b+ c;
    }

    public static void add2(int a, int b){
        System.out.println("The sum of " + a + " and " + b +" = "+ (a+b));
    }

    public static int[] returnArray(){
        return new int[]{1,2,3,4,5,6,7,8,9,10};
    }

}
