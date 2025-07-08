package introduction.datatypes;

public class Casting {
    public static void main(String[] args) {
        byte byteValue = 42;
        short shortValue = byteValue;  // byte to short
        int intValue = shortValue;     // short to int
        long longValue = intValue;     // int to long
        float floatValue = longValue;  // long to float
        double doubleValue = floatValue;

        System.out.println("Byte value: " + byteValue);
        System.out.println("Short value: " + shortValue);
        System.out.println("Int value: " + intValue);
        System.out.println("Long value: " + longValue);
        System.out.println("Float value: " + floatValue);
        System.out.println("Double value: " + doubleValue);

        System.out.println("=".repeat(50));
        System.out.println("Explicit casting");
        //explicit
        double doubleValu = 9.78;
        int intValu = (int) doubleValu; // 9 (decimal part lost)

        long longValu = 12345678934545435L;
        int intValue2 = (int) longValu;   // Possible data loss

        float floatValu = 3.14f;
        int intValue3 = (int) floatValu;  // 3

        System.out.println("Double value: " + doubleValu);
        System.out.println("Int value: " + intValu);
        System.out.println("Long value: " + longValu);
        System.out.println("Int value: " + intValue2);
        System.out.println("Float value: " + floatValu);
        System.out.println("Int value: " + intValue3);
    }
}
