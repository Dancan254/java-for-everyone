package generics.solutions;

import java.util.Arrays;

/**
 * Exercise1Solution.java
 *
 * Solutions for Exercise Set 1: Generic Classes.
 * Covers: generic class declaration, type parameter placeholders,
 * multiple type parameters (Pair<A,B>), generic array workaround (Stack<T>),
 * automatic capacity growth, and EmptyStackException.
 */
public class Exercise1Solution {

    // -------------------------------------------------------------------------
    // Exercise 1.1 — Box<T>
    // -------------------------------------------------------------------------

    /**
     * A generic container that holds exactly one value of any reference type.
     * T is a placeholder replaced by the caller's chosen type at instantiation.
     */
    static class Box<T> {
        private T value;

        public Box(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Box[" + value + "]";
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 1.2 — Pair<A, B>
    // -------------------------------------------------------------------------

    /**
     * A generic container for two values of potentially different types.
     * A and B are independent type parameters chosen independently by the caller.
     */
    static class Pair<A, B> {
        private final A first;
        private final B second;

        public Pair(A first, B second) {
            this.first  = first;
            this.second = second;
        }

        public A getFirst()  { return first;  }
        public B getSecond() { return second; }

        /**
         * Returns a new Pair with the two positions swapped.
         * The return type Pair<B, A> is the mirror of Pair<A, B>.
         */
        public Pair<B, A> swap() {
            return new Pair<>(second, first);
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 1.3 — Stack<T>
    // -------------------------------------------------------------------------

    /**
     * A generic last-in-first-out stack backed by an array that doubles in
     * capacity whenever it is full. Because Java forbids new T[n], the backing
     * array is created as Object[] and cast to T[]; the @SuppressWarnings
     * annotation documents that this cast is deliberate and safe.
     */
    static class Stack<T> {
        private T[] elements;
        private int size;
        private static final int INITIAL_CAPACITY = 4;

        @SuppressWarnings("unchecked")
        public Stack() {
            // Generic array creation is forbidden. Cast an Object[] instead.
            // Every element we store will be a T, so the cast is safe.
            elements = (T[]) new Object[INITIAL_CAPACITY];
            size = 0;
        }

        /** Adds item to the top of the stack; grows the backing array if full. */
        public void push(T item) {
            if (size == elements.length) {
                // Double capacity when full.
                elements = Arrays.copyOf(elements, elements.length * 2);
            }
            elements[size++] = item;
        }

        /**
         * Removes and returns the top item.
         *
         * @throws java.util.EmptyStackException if the stack is empty
         */
        public T pop() {
            if (isEmpty()) {
                throw new java.util.EmptyStackException();
            }
            T item = elements[--size];
            // Null out the vacated slot so the garbage collector can reclaim the object.
            elements[size] = null;
            return item;
        }

        /**
         * Returns the top item without removing it.
         *
         * @throws java.util.EmptyStackException if the stack is empty
         */
        public T peek() {
            if (isEmpty()) {
                throw new java.util.EmptyStackException();
            }
            return elements[size - 1];
        }

        public boolean isEmpty() { return size == 0; }
        public int size()        { return size; }

        @Override
        public String toString() {
            // Only show the populated portion of the backing array.
            return "Stack" + Arrays.toString(Arrays.copyOf(elements, size));
        }
    }

    // -------------------------------------------------------------------------
    // main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 1.1: Box<T> ---

        System.out.println("=== Exercise 1.1: Box<T> ===");

        Box<String>  nameBox  = new Box<>("Alice");
        Box<Integer> ageBox   = new Box<>(30);
        Box<Double>  priceBox = new Box<>(19.99);

        System.out.println(nameBox);   // Output: Box[Alice]
        System.out.println(ageBox);    // Output: Box[30]
        System.out.println(priceBox);  // Output: Box[19.99]

        // No cast is required — the compiler already knows the type.
        String  name  = nameBox.getValue();
        Integer age   = ageBox.getValue();
        Double  price = priceBox.getValue();

        System.out.println("name=" + name + ", age=" + age + ", price=" + price);
        // Output: name=Alice, age=30, price=19.99

        ageBox.setValue(31);
        System.out.println("After setValue: " + ageBox);
        // Output: After setValue: Box[31]

        // The line below is a compile error — the types do not match:
        // ageBox.setValue("oops"); // Compiler error: incompatible types: String cannot be converted to Integer

        // --- Exercise 1.2: Pair<A, B> ---

        System.out.println("\n=== Exercise 1.2: Pair<A, B> ===");

        Pair<String, Integer> nameAndAge = new Pair<>("Alice", 30);
        System.out.println(nameAndAge);
        // Output: (Alice, 30)

        Pair<Integer, String> swapped = nameAndAge.swap();
        System.out.println(swapped);
        // Output: (30, Alice)

        Pair<Boolean, String> featureFlag = new Pair<>(true, "darkMode");
        System.out.println(featureFlag.getFirst() + " -> " + featureFlag.getSecond());
        // Output: true -> darkMode

        // swap() returns Pair<String, Boolean> — types are tracked independently.
        Pair<String, Boolean> flagSwapped = featureFlag.swap();
        System.out.println(flagSwapped);
        // Output: (darkMode, true)

        // --- Exercise 1.3: Stack<T> ---

        System.out.println("\n=== Exercise 1.3: Stack<T> ===");

        // Stack<String>
        Stack<String> wordStack = new Stack<>();
        wordStack.push("first");
        wordStack.push("second");
        wordStack.push("third");

        System.out.println(wordStack);
        // Output: Stack[first, second, third]

        System.out.println("peek: " + wordStack.peek());
        // Output: peek: third

        System.out.println("pop:  " + wordStack.pop());
        // Output: pop:  third

        System.out.println(wordStack);
        // Output: Stack[first, second]

        // Stack<Integer> — push beyond initial capacity of 4 to force a resize.
        Stack<Integer> intStack = new Stack<>();
        for (int i = 1; i <= 6; i++) {
            intStack.push(i * 10);
        }
        // The backing array doubled from 4 to 8 automatically.
        System.out.println(intStack);
        // Output: Stack[10, 20, 30, 40, 50, 60]

        System.out.print("Popping: ");
        while (!intStack.isEmpty()) {
            System.out.print(intStack.pop() + " ");
        }
        System.out.println();
        // Output: Popping: 60 50 40 30 20 10

        // Confirm EmptyStackException is thrown when popping from an empty stack.
        try {
            intStack.pop();
        } catch (java.util.EmptyStackException e) {
            System.out.println("Caught EmptyStackException as expected.");
            // Output: Caught EmptyStackException as expected.
        }
    }
}
