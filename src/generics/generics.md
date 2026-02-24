# Generics

Generics allow you to write classes, interfaces, and methods where the type is a parameter, defined by the caller rather than hard-coded. They were introduced in Java 5 to solve a fundamental problem: code that needed to be reusable across many types had no way to enforce type safety at compile time. Generics give you that reusability and that safety at the same time.

```
Think of it like this:
  A Box class without generics can hold anything — but when you take something out,
  you have to guess what it is and cast it, and if you guess wrong, the program crashes.
  A Box<T> class tells you exactly what is inside before you even open it.
  The type is checked when you put the item in, not when you take it out.
```

## 1. The Problem Generics Solve

Before generics, a reusable container had to hold `Object`. This meant every retrieval required a cast, and a wrong cast would compile just fine but crash at runtime.

```java
// Pre-generics: a Box that holds Object
public class Box {
    private Object value;

    public Box(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
```

```java
Box stringBox = new Box("Hello");
Box intBox    = new Box(42);

// You must cast to get a usable type
String s = (String) stringBox.getValue(); // Works fine
Integer i = (Integer) intBox.getValue();  // Works fine

// But nothing stops this mistake at compile time:
String bad = (String) intBox.getValue();
// Output: Exception in thread "main" java.lang.ClassCastException:
//         class java.lang.Integer cannot be cast to class java.lang.String
```

The ClassCastException only appears at runtime, potentially deep inside production code. With generics, the same mistake becomes a compiler error — it never reaches production.

```java
// Generic version: the type is a parameter
public class Box<T> {
    private T value;

    public Box(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }
}
```

```java
Box<String>  stringBox = new Box<>("Hello");
Box<Integer> intBox    = new Box<>(42);

String s  = stringBox.getValue(); // No cast required
Integer i = intBox.getValue();    // No cast required

// This now fails at compile time, not runtime:
// String bad = (String) intBox.getValue(); // Compiler error: incompatible types
```

The error moved from runtime to compile time. That is the core value proposition of generics.

## 2. Generic Classes

A generic class declares one or more type parameters in angle brackets after the class name. Those parameters act as placeholders that are replaced with a real type when the class is instantiated.

**Syntax:** `public class ClassName<T> { ... }`

```java
public class Box<T> {
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
```

```java
Box<String>  nameBox  = new Box<>("Alice");
Box<Integer> ageBox   = new Box<>(30);
Box<Double>  priceBox = new Box<>(19.99);

System.out.println(nameBox);   // Output: Box[Alice]
System.out.println(ageBox);    // Output: Box[30]
System.out.println(priceBox);  // Output: Box[19.99]

// The compiler enforces the type — this does not compile:
// ageBox.setValue("oops"); // Compiler error: incompatible types: String cannot be converted to Integer
```

`T` is simply a name for "whatever type the caller chooses." When you write `Box<String>`, every `T` in that class becomes `String` for that instance. When you write `Box<Integer>`, every `T` becomes `Integer`.

## 3. Type Parameter Naming Conventions

Type parameter names are single uppercase letters by convention. These letters are not enforced by the compiler, but every Java developer follows them, and the standard library uses them throughout.

| Letter | Convention                  |
|--------|-----------------------------|
| T      | Type (general purpose)      |
| E      | Element (collections)       |
| K      | Key (maps)                  |
| V      | Value (maps)                |
| N      | Number                      |
| R      | Return type                 |

These are conventions, not rules, but following them makes generic code immediately readable to any Java developer. When you see `Map<K, V>` or `List<E>`, the intent is clear without needing to read the full class.

## 4. Generic Methods

A method can declare its own type parameter independent of the class it belongs to. This is useful for utility methods that need to work across types without requiring the entire class to be generic.

**Syntax:** `public static <T> ReturnType methodName(T arg) { ... }`

```java
public class ArrayUtils {

    // Swaps two elements in an array of any type
    public static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Returns the first element that equals the target, or null if not found
    public static <T> T findFirst(T[] arr, T target) {
        for (T element : arr) {
            if (element.equals(target)) {
                return element;
            }
        }
        return null;
    }
}
```

```java
String[] names = {"Alice", "Bob", "Carol", "Diana"};
System.out.println(java.util.Arrays.toString(names));
// Output: [Alice, Bob, Carol, Diana]

ArrayUtils.swap(names, 0, 3);
System.out.println(java.util.Arrays.toString(names));
// Output: [Diana, Bob, Carol, Alice]

String found = ArrayUtils.findFirst(names, "Carol");
System.out.println(found);
// Output: Carol

Integer[] numbers = {10, 20, 30, 40, 50};
ArrayUtils.swap(numbers, 1, 3);
System.out.println(java.util.Arrays.toString(numbers));
// Output: [10, 40, 30, 20, 50]
```

Notice that the type is never specified explicitly at the call site. The compiler infers `T` from the arguments passed in — `String[]` makes `T` become `String`, and `Integer[]` makes `T` become `Integer`.

## 5. Multiple Type Parameters

A class or method can declare more than one type parameter, separated by commas. This is common for containers that hold two related but potentially different values.

```java
public class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A first, B second) {
        this.first  = first;
        this.second = second;
    }

    public A getFirst()  { return first;  }
    public B getSecond() { return second; }

    // Returns a new Pair with the positions swapped
    public Pair<B, A> swap() {
        return new Pair<>(second, first);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
```

```java
Pair<String, Integer> nameAndAge = new Pair<>("Alice", 30);
System.out.println(nameAndAge);
// Output: (Alice, 30)

Pair<Integer, String> swapped = nameAndAge.swap();
System.out.println(swapped);
// Output: (30, Alice)

Pair<String, Boolean> config = new Pair<>("darkMode", true);
System.out.println(config.getFirst() + " = " + config.getSecond());
// Output: darkMode = true
```

`Map<K, V>` in the Java standard library follows exactly this pattern. `K` is the key type and `V` is the value type, and both are independently chosen by the caller.

## 6. Bounded Type Parameters

Sometimes you need to restrict what types can be used as a type argument. Bounds let you require that `T` be a specific type or a subclass of it.

### Upper bound: extends

`<T extends Number>` means T must be `Number` or any subclass of it (`Integer`, `Double`, `Long`, etc.). This grants access to methods defined on `Number` inside the generic code.

```java
import java.util.List;

public class MathUtils {

    // T must be a Number — so .doubleValue() is available
    public static <T extends Number> double sum(List<T> list) {
        double total = 0;
        for (T element : list) {
            total += element.doubleValue();
        }
        return total;
    }
}
```

```java
List<Integer> ints    = java.util.Arrays.asList(1, 2, 3, 4, 5);
List<Double>  doubles = java.util.Arrays.asList(1.5, 2.5, 3.0);

System.out.println(MathUtils.sum(ints));
// Output: 15.0

System.out.println(MathUtils.sum(doubles));
// Output: 7.0

// This does not compile — String does not extend Number:
// List<String> strings = java.util.Arrays.asList("a", "b");
// MathUtils.sum(strings); // Compiler error: String is not within bounds
```

### Multiple bounds

A type parameter can be required to satisfy more than one constraint using `&`. The class bound (if any) must come first, followed by interface bounds.

```java
import java.io.Serializable;
import java.util.Arrays;

public class Sorter {

    // T must implement both Comparable<T> and Serializable
    public static <T extends Comparable<T> & Serializable> T findMax(T[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        T max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].compareTo(max) > 0) {
                max = arr[i];
            }
        }
        return max;
    }
}
```

```java
String[] words   = {"banana", "apple", "cherry", "date"};
Integer[] values = {3, 1, 4, 1, 5, 9, 2, 6};

System.out.println(Sorter.findMax(words));
// Output: date

System.out.println(Sorter.findMax(values));
// Output: 9
```

`String` and `Integer` both implement `Comparable` and `Serializable`, so both calls compile. A custom class that did not implement both would be rejected at compile time.

## 7. Wildcards

Wildcards use `?` as a type argument when you do not need to name the type but still want flexibility. They appear in method parameters, not in class declarations.

### Unbounded wildcard: `<?>`

`List<?>` accepts a list of any type. Use this when you only need to read elements and treat them as `Object`, and the specific type does not matter.

```java
import java.util.List;

public class Printer {

    public static void printList(List<?> list) {
        for (Object element : list) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}
```

```java
List<String>  names   = java.util.Arrays.asList("Alice", "Bob", "Carol");
List<Integer> numbers = java.util.Arrays.asList(1, 2, 3);
List<Double>  prices  = java.util.Arrays.asList(9.99, 14.99, 4.99);

Printer.printList(names);
// Output: Alice Bob Carol

Printer.printList(numbers);
// Output: 1 2 3

Printer.printList(prices);
// Output: 9.99 14.99 4.99
```

### Upper bounded wildcard: `<? extends T>`

`List<? extends Number>` accepts `List<Integer>`, `List<Double>`, `List<Long>`, or any other list whose element type is a subtype of `Number`. Use this when you are **reading** from the list (the list is a producer of values).

```java
import java.util.List;

public class Statistics {

    public static double average(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        return sum / list.size();
    }
}
```

```java
List<Integer> scores  = java.util.Arrays.asList(80, 90, 75, 95);
List<Double>  ratings = java.util.Arrays.asList(4.5, 3.8, 5.0, 4.2);

System.out.printf("Average score:  %.2f%n", Statistics.average(scores));
// Output: Average score:  85.00

System.out.printf("Average rating: %.2f%n", Statistics.average(ratings));
// Output: Average rating: 4.38
```

### Lower bounded wildcard: `<? super T>`

`List<? super Integer>` accepts `List<Integer>`, `List<Number>`, or `List<Object>` — any list whose element type is a supertype of `Integer`. Use this when you are **writing** to the list (the list is a consumer of values).

```java
import java.util.List;

public class Filler {

    public static void addNumbers(List<? super Integer> list) {
        for (int i = 1; i <= 5; i++) {
            list.add(i); // Safe — Integer is always compatible with a supertype of Integer
        }
    }
}
```

```java
List<Integer> intList    = new java.util.ArrayList<>();
List<Number>  numList    = new java.util.ArrayList<>();
List<Object>  objectList = new java.util.ArrayList<>();

Filler.addNumbers(intList);
Filler.addNumbers(numList);
Filler.addNumbers(objectList);

System.out.println(intList);
// Output: [1, 2, 3, 4, 5]

System.out.println(numList);
// Output: [1, 2, 3, 4, 5]

System.out.println(objectList);
// Output: [1, 2, 3, 4, 5]
```

### The PECS mnemonic

**Producer Extends, Consumer Super.**

- If a structure **produces** values you will read, use `<? extends T>`.
- If a structure **consumes** values you will write, use `<? super T>`.

```java
// src produces T values — use extends (you are reading from it)
// dest consumes T values — use super (you are writing into it)
public static <T> void copy(List<? extends T> src, List<? super T> dest) {
    for (T item : src) {
        dest.add(item);
    }
}
```

```java
List<Integer> source      = java.util.Arrays.asList(1, 2, 3);
List<Number>  destination = new java.util.ArrayList<>();

copy(source, destination);
System.out.println(destination);
// Output: [1, 2, 3]
```

## 8. Generic Interfaces

Interfaces can also be parameterized with type parameters. A class that implements a generic interface substitutes a concrete type for each parameter, creating a type-safe contract.

```java
public interface Repository<T, ID> {
    T findById(ID id);
    void save(T entity);
    void delete(ID id);
}
```

```java
public class User {
    private Long id;
    private String name;
    private String email;

    public User(Long id, String name, String email) {
        this.id    = id;
        this.name  = name;
        this.email = email;
    }

    public Long getId()    { return id;    }
    public String getName()  { return name;  }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
```

```java
import java.util.HashMap;
import java.util.Map;

public class UserRepository implements Repository<User, Long> {
    private Map<Long, User> store = new HashMap<>();

    @Override
    public User findById(Long id) {
        return store.get(id);
    }

    @Override
    public void save(User user) {
        store.put(user.getId(), user);
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }
}
```

```java
UserRepository repo = new UserRepository();

repo.save(new User(1L, "Alice", "alice@example.com"));
repo.save(new User(2L, "Bob",   "bob@example.com"));

System.out.println(repo.findById(1L));
// Output: User{id=1, name='Alice', email='alice@example.com'}

System.out.println(repo.findById(2L));
// Output: User{id=2, name='Bob', email='bob@example.com'}

repo.delete(1L);
System.out.println(repo.findById(1L));
// Output: null
```

This pattern is exactly how Spring Data JPA works. `JpaRepository<User, Long>` is a generic interface — Spring generates the implementation automatically, and your repository inherits type-safe methods like `findById(Long id)` and `save(User entity)` without writing a single line of persistence code.

## 9. Type Erasure

Generics exist only at compile time. The compiler uses the type information to verify correctness and insert casts where needed, but then **removes** all type parameters from the compiled bytecode. At runtime, `Box<String>` and `Box<Integer>` are both just `Box`. This process is called type erasure.

The erasure rule: `T` is replaced with `Object` (or the upper bound if one was declared — `<T extends Number>` becomes `Number` at runtime).

This design preserved backward compatibility with pre-generics Java code, but it has three important consequences.

```java
import java.util.ArrayList;
import java.util.List;

public class TypeErasureDemo {

    public static void main(String[] args) {

        // Consequence 1: Cannot use instanceof with a parameterized type.
        // At runtime the type parameter is gone, so the check is meaningless.
        List<String> list = new ArrayList<>();
        // if (list instanceof List<String>) { }
        // Compiler error: illegal generic type for instanceof
        // You can only check: if (list instanceof List) { } — without the type argument

        // Consequence 2: Cannot create an instance of a type parameter.
        // new T() is not allowed because the compiler cannot know the constructor to call.
        // public static <T> T create() {
        //     return new T(); // Compiler error: cannot instantiate type T
        // }
        // Workaround: pass a Class<T> token or a Supplier<T> to the method.

        // Consequence 3: Cannot create a generic array directly.
        // T[] arr = new T[10]; // Compiler error: generic array creation
        // Workaround: cast an Object array (with an unchecked warning).
        // T[] arr = (T[]) new Object[10];
    }
}
```

Understanding type erasure explains why certain patterns that seem logical in Java are forbidden by the compiler. The information simply does not exist at runtime.

## 10. Complete Example

```java
import java.util.Arrays;

public class Stack<T> {
    private T[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public Stack() {
        // Generic array creation is not directly allowed — cast an Object array instead
        elements = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public void push(T item) {
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
        elements[size] = null; // Allow garbage collection
        return item;
    }

    public T peek() {
        if (isEmpty()) {
            throw new java.util.EmptyStackException();
        }
        return elements[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return "Stack" + Arrays.toString(Arrays.copyOf(elements, size));
    }
}
```

```java
public class MathUtils {

    // Works with any type that can be compared to itself
    public static <T extends Comparable<T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {

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

        while (!intStack.isEmpty()) {
            System.out.print(intStack.pop() + " ");
        }
        System.out.println();
        // Output: 30 20 10

        // Stack<Double>
        Stack<Double> doubleStack = new Stack<>();
        doubleStack.push(1.1);
        doubleStack.push(2.2);
        doubleStack.push(3.3);

        System.out.println("Size: " + doubleStack.size());
        // Output: Size: 3

        // MathUtils.max with different types
        System.out.println(MathUtils.max(42, 99));
        // Output: 99

        System.out.println(MathUtils.max("apple", "mango"));
        // Output: mango

        System.out.println(MathUtils.max(3.14, 2.71));
        // Output: 3.14
    }
}
```

## 11. Key Takeaways

- Generics move type errors from runtime to compile time — the single most important reason to use them
- A type parameter (`T`, `E`, `K`, `V`) is a placeholder replaced by the caller's chosen type at instantiation
- Generic classes, generic methods, and generic interfaces each declare type parameters independently
- Upper bounds (`<T extends Number>`) restrict what types are allowed and unlock the bound type's methods inside the generic code
- Wildcards (`<?>`, `<? extends T>`, `<? super T>`) provide flexibility at call sites; use PECS to remember which to choose
- Type erasure removes all generic type information at runtime, which is why `instanceof` with parameterized types and `new T()` are both forbidden
- `@SuppressWarnings("unchecked")` is the correct way to acknowledge a deliberate, safe unchecked cast (such as `(T[]) new Object[n]`)
- Generic interfaces like `Repository<T, ID>` are the foundation of patterns like Spring Data JPA
- Naming conventions (`T`, `E`, `K`, `V`, `N`, `R`) are not enforced but are universally followed — use them

---

Generics are what make the Collections framework and Spring's APIs readable and type-safe — every `List<E>`, `Map<K, V>`, `Optional<T>`, and `JpaRepository<T, ID>` you use daily is built on exactly the concepts covered here.
