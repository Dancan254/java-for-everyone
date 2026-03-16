import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GenericsDemo.java
 *
 * A self-contained demonstration of Java Generics covering every concept in generics.md:
 * type parameters, generic classes, generic methods, multiple type parameters,
 * bounded types, wildcards (unbounded, upper-bounded, lower-bounded), PECS,
 * generic interfaces, type erasure consequences, and the complete Stack<T> example.
 */
public class GenericsDemo {

    // -------------------------------------------------------------------------
    // Supporting classes and interfaces used throughout the demo
    // -------------------------------------------------------------------------

    // A generic Box that holds a single value of any type.
    // T is a placeholder replaced by the caller's chosen type at instantiation.
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

    // A generic Pair holding two values of potentially different types.
    // A and B are independent type parameters chosen by the caller.
    static class Pair<A, B> {
        private final A first;
        private final B second;

        public Pair(A first, B second) {
            this.first  = first;
            this.second = second;
        }

        public A getFirst()  { return first;  }
        public B getSecond() { return second; }

        // Returns a new Pair with the positions swapped; note the return type Pair<B,A>.
        public Pair<B, A> swap() {
            return new Pair<>(second, first);
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
        }
    }

    // A generic Stack that grows automatically when full.
    // Demonstrates the workaround for generic array creation.
    static class Stack<T> {
        private T[] elements;
        private int size;
        private static final int DEFAULT_CAPACITY = 4; // Small for demo visibility

        @SuppressWarnings("unchecked")
        public Stack() {
            // Generic array creation is forbidden — cast an Object array instead.
            // @SuppressWarnings("unchecked") acknowledges this deliberate safe cast.
            elements = (T[]) new Object[DEFAULT_CAPACITY];
            size = 0;
        }

        public void push(T item) {
            // Double the backing array when it is full.
            if (size == elements.length) {
                elements = Arrays.copyOf(elements, elements.length * 2);
            }
            elements[size++] = item;
        }

        public T pop() {
            if (isEmpty()) {
                throw new java.util.EmptyStackException();
            }
            T item = elements[--size];
            elements[size] = null; // Null out the slot to allow garbage collection.
            return item;
        }

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
            return "Stack" + Arrays.toString(Arrays.copyOf(elements, size));
        }
    }

    // A generic Repository interface with two type parameters: the entity type and its ID type.
    // This pattern mirrors Spring Data's JpaRepository<T, ID>.
    interface Repository<T, ID> {
        T findById(ID id);
        void save(T entity);
        void delete(ID id);
    }

    // A simple User entity used with the Repository interface.
    static class User {
        private final Long id;
        private final String name;

        public User(Long id, String name) {
            this.id   = id;
            this.name = name;
        }

        public Long getId()    { return id;   }
        public String getName() { return name; }

        @Override
        public String toString() {
            return "User{id=" + id + ", name='" + name + "'}";
        }
    }

    // A concrete implementation of Repository<User, Long>.
    // The compiler enforces that findById takes a Long and returns a User.
    static class UserRepository implements Repository<User, Long> {
        private final Map<Long, User> store = new HashMap<>();

        @Override
        public User findById(Long id) { return store.get(id); }

        @Override
        public void save(User user)   { store.put(user.getId(), user); }

        @Override
        public void delete(Long id)   { store.remove(id); }
    }

    // -------------------------------------------------------------------------
    // Static utility methods with their own type parameters
    // -------------------------------------------------------------------------

    // Swaps two elements in an array of any reference type.
    // The type parameter <T> is declared before the return type void.
    static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Returns the first element equal to target, or null if not found.
    static <T> T findFirst(T[] arr, T target) {
        for (T element : arr) {
            if (element.equals(target)) {
                return element;
            }
        }
        return null;
    }

    // Upper bound: T must extend Number, so doubleValue() is available inside the method.
    static <T extends Number> double sumList(List<T> list) {
        double total = 0;
        for (T element : list) {
            total += element.doubleValue();
        }
        return total;
    }

    // Multiple bounds: T must implement both Comparable<T> and java.io.Serializable.
    // The class bound (if any) must appear first; interface bounds follow with &.
    static <T extends Comparable<T> & java.io.Serializable> T findMax(T[] arr) {
        T max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(max) > 0) {
                max = arr[i];
            }
        }
        return max;
    }

    // Unbounded wildcard: accepts a List of any type.
    // Elements can only be read as Object because the actual type is unknown.
    static void printList(List<?> list) {
        for (Object element : list) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    // Upper-bounded wildcard: accepts any List whose element type is Number or a subtype.
    // Use when the list is a producer of values you will read (PECS: Producer Extends).
    static double average(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        return sum / list.size();
    }

    // Lower-bounded wildcard: accepts List<Integer>, List<Number>, or List<Object>.
    // Use when the list is a consumer of values you will write (PECS: Consumer Super).
    static void addNumbers(List<? super Integer> list) {
        for (int i = 1; i <= 5; i++) {
            // Safe: Integer is always assignable to a supertype of Integer.
            list.add(i);
        }
    }

    // PECS in one method: src produces values (extends), dest consumes them (super).
    static <T> void copy(List<? extends T> src, List<? super T> dest) {
        for (T item : src) {
            dest.add(item);
        }
    }

    // Works with any type that can compare itself to another instance of the same type.
    static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    // -------------------------------------------------------------------------
    // main method
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- 1. The Problem Generics Solve ---

        System.out.println("=== 1. The Problem Generics Solve ===");

        // Without generics, a Box holds Object and every retrieval requires a cast.
        // A wrong cast compiles fine but crashes at runtime — ClassCastException.
        Box<String>  stringBox  = new Box<>("Hello");
        Box<Integer> intBox     = new Box<>(42);
        Box<Double>  priceBox   = new Box<>(19.99);

        System.out.println(stringBox);   // Output: Box[Hello]
        System.out.println(intBox);      // Output: Box[42]
        System.out.println(priceBox);    // Output: Box[19.99]

        // No cast required when retrieving — the compiler already knows the type.
        String  s = stringBox.getValue();
        Integer n = intBox.getValue();
        System.out.println("Retrieved string: " + s); // Output: Retrieved string: Hello
        System.out.println("Retrieved int:    " + n); // Output: Retrieved int:    42

        // The line below would be a compile-time error, not a runtime crash:
        // String bad = (String) intBox.getValue(); // Compiler error: incompatible types

        // --- 2. Generic Methods ---

        System.out.println("\n=== 2. Generic Methods ===");

        String[] names = {"Alice", "Bob", "Carol", "Diana"};
        System.out.println(Arrays.toString(names));
        // Output: [Alice, Bob, Carol, Diana]

        swap(names, 0, 3);
        System.out.println(Arrays.toString(names));
        // Output: [Diana, Bob, Carol, Alice]

        String found = findFirst(names, "Carol");
        System.out.println("Found: " + found); // Output: Found: Carol

        Integer[] numbers = {10, 20, 30, 40, 50};
        swap(numbers, 1, 3);
        System.out.println(Arrays.toString(numbers));
        // Output: [10, 40, 30, 20, 50]

        // --- 3. Multiple Type Parameters (Pair<A,B>) ---

        System.out.println("\n=== 3. Multiple Type Parameters (Pair<A,B>) ===");

        Pair<String, Integer> nameAndAge = new Pair<>("Alice", 30);
        System.out.println(nameAndAge);
        // Output: (Alice, 30)

        Pair<Integer, String> swapped = nameAndAge.swap();
        System.out.println(swapped);
        // Output: (30, Alice)

        Pair<String, Boolean> config = new Pair<>("darkMode", true);
        System.out.println(config.getFirst() + " = " + config.getSecond());
        // Output: darkMode = true

        // --- 4. Bounded Type Parameters ---

        System.out.println("\n=== 4. Bounded Type Parameters ===");

        List<Integer> ints    = Arrays.asList(1, 2, 3, 4, 5);
        List<Double>  doubles = Arrays.asList(1.5, 2.5, 3.0);

        System.out.println(sumList(ints));    // Output: 15.0
        System.out.println(sumList(doubles)); // Output: 7.0

        // Multiple bounds — String and Integer both implement Comparable and Serializable.
        String[] words   = {"banana", "apple", "cherry", "date"};
        Integer[] values = {3, 1, 4, 1, 5, 9, 2, 6};

        System.out.println(findMax(words));   // Output: date
        System.out.println(findMax(values));  // Output: 9

        // --- 5. Wildcards ---

        System.out.println("\n=== 5. Wildcards ===");

        // Unbounded wildcard: prints any list regardless of element type.
        List<String>  nameList  = Arrays.asList("Alice", "Bob", "Carol");
        List<Integer> numList   = Arrays.asList(1, 2, 3);
        List<Double>  priceList = Arrays.asList(9.99, 14.99, 4.99);

        System.out.print("Names:  "); printList(nameList);   // Output: Alice Bob Carol
        System.out.print("Ints:   "); printList(numList);    // Output: 1 2 3
        System.out.print("Prices: "); printList(priceList);  // Output: 9.99 14.99 4.99

        // Upper-bounded wildcard: average works with any List<? extends Number>.
        List<Integer> scores  = Arrays.asList(80, 90, 75, 95);
        List<Double>  ratings = Arrays.asList(4.5, 3.8, 5.0, 4.2);

        System.out.printf("Average score:  %.2f%n", average(scores));   // Output: Average score:  85.00
        System.out.printf("Average rating: %.2f%n", average(ratings));  // Output: Average rating: 4.38

        // Lower-bounded wildcard: addNumbers accepts List<Integer>, List<Number>, List<Object>.
        List<Integer> intTarget    = new ArrayList<>();
        List<Number>  numTarget    = new ArrayList<>();
        List<Object>  objectTarget = new ArrayList<>();

        addNumbers(intTarget);
        addNumbers(numTarget);
        addNumbers(objectTarget);

        System.out.println(intTarget);    // Output: [1, 2, 3, 4, 5]
        System.out.println(numTarget);    // Output: [1, 2, 3, 4, 5]
        System.out.println(objectTarget); // Output: [1, 2, 3, 4, 5]

        // PECS: copy reads from a List<? extends T> and writes into a List<? super T>.
        List<Integer> source      = Arrays.asList(1, 2, 3);
        List<Number>  destination = new ArrayList<>();
        copy(source, destination);
        System.out.println(destination); // Output: [1, 2, 3]

        // --- 6. Generic Interfaces ---

        System.out.println("\n=== 6. Generic Interfaces (Repository<T,ID>) ===");

        UserRepository repo = new UserRepository();
        repo.save(new User(1L, "Alice"));
        repo.save(new User(2L, "Bob"));

        System.out.println(repo.findById(1L));  // Output: User{id=1, name='Alice'}
        System.out.println(repo.findById(2L));  // Output: User{id=2, name='Bob'}

        repo.delete(1L);
        System.out.println(repo.findById(1L));  // Output: null

        // --- 7. Complete Example: Stack<T> ---

        System.out.println("\n=== 7. Complete Example: Stack<T> ===");

        // Stack<String>
        Stack<String> wordStack = new Stack<>();
        wordStack.push("first");
        wordStack.push("second");
        wordStack.push("third");

        System.out.println(wordStack);
        // Output: Stack[first, second, third]

        System.out.println("Peek: " + wordStack.peek());
        // Output: Peek: third

        System.out.println("Pop: " + wordStack.pop());
        // Output: Pop: third

        System.out.println(wordStack);
        // Output: Stack[first, second]

        // Stack<Integer>
        Stack<Integer> intStack = new Stack<>();
        intStack.push(10);
        intStack.push(20);
        intStack.push(30);

        System.out.println(intStack);
        // Output: Stack[10, 20, 30]

        System.out.print("Popping: ");
        while (!intStack.isEmpty()) {
            System.out.print(intStack.pop() + " ");
        }
        System.out.println();
        // Output: Popping: 30 20 10

        // --- 8. Type Erasure Consequences ---

        System.out.println("\n=== 8. Type Erasure Consequences ===");

        // At runtime, Box<String> and Box<Integer> are both just Box.
        Box<String>  a = new Box<>("text");
        Box<Integer> b = new Box<>(99);

        // Both are instances of Box (raw type) — the type parameter is erased.
        System.out.println(a instanceof Box);  // Output: true
        System.out.println(b instanceof Box);  // Output: true

        // The following is a compile error — you cannot use instanceof with a parameterized type:
        // if (a instanceof Box<String>) { }
        // At runtime, the type argument <String> no longer exists.
        System.out.println("(instanceof with parameterized types is a compile error — see comment)");

        // --- 9. Generic Method: max with Comparable bound ---

        System.out.println("\n=== 9. Comparable Bound: max<T extends Comparable<T>> ===");

        System.out.println(max(42, 99));          // Output: 99
        System.out.println(max("apple", "mango")); // Output: mango
        System.out.println(max(3.14, 2.71));       // Output: 3.14
    }
}
