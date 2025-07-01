package introduction;

public class Main {
    public static void main(String[] args) {

        //obj1
        Simple obj1 = new Simple("Ian");
        Simple obj2 = new Simple("Ian");

        System.out.println("Obj1 " + System.identityHashCode(obj1));
        System.out.println("Obj2 " + System.identityHashCode(obj2));
    }
}
