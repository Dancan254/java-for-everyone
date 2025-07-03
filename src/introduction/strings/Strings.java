package introduction.strings;

public class Strings {
    public static void main(String[] args) {
        //stack memory - primitive data types are stored, references to objects
        //heap memory -  objects are stored
        //how to create a string
        String name = "John"; //create an object
        String name2= new String("Ian");

        String fname = "Onyango";
        String lname = "Ojo";

        String fullname = fname + " " +  lname; // Onyango Ojo
        System.out.println("Ian " + "Mongs");

        System.out.println(name + name2);

        int age = 23;
        String myname = "Joshua";
        // my name is joshua, I am 23 years old
        System.out.println("My name is " + myname + ", I am " + age + " years old.");

        //mess with String methods
        String str = "Hello World";

        String strUpperCase = str.toUpperCase();
        System.out.println(strUpperCase);
        System.out.println("To lowercase" + str.toLowerCase());
        System.out.println("Length: " + str.length());
        System.out.println("Does it contain 'World'? " + str.contains("World"));
        System.out.println("Replace Hello with Hi: " + str.replace("Hello", "Hi"));
        System.out.println(str);
        //substring
        System.out.println(str.substring(4));
        System.out.println(str.substring(4, 7));

        String string1 = "hello";
        String string2 = "hello";
        String string3 = new String("Hello");
        String string4 = new String("Hello");

        System.out.println(string4 == string3);// false
        System.out.println(string4.equals(string3));// true
        System.out.println("Str3 " + System.identityHashCode(string3));
        System.out.println("Str4 " + System.identityHashCode(string4));

        System.out.println(string1 == string2); // memory - true
        System.out.println("string1 " + System.identityHashCode(string1));
        System.out.println(string1.equals(string2)); // true
        System.out.println("string2 " + System.identityHashCode(string2));

        char tabs = '\t';
        System.out.println( "tabs " +  tabs);

        char quote = '\'';
        System.out.println("Quote " + quote);
    }
}
