# Generics Practice Exercises

Generics are what separate reusable, type-safe code from raw-Object soup that collapses at runtime. The exercises below build from writing generic classes through bounded type parameters, wildcards, and generic interfaces. Complete each set in order — the concepts compound.

---

## Exercise Set 1: Generic Classes

### Exercise 1.1: Generic Box

Write a generic class `Box<T>` with the following contract:
- A private field `value` of type `T`
- A constructor that accepts a `T`
- `getValue()` returning `T`
- `setValue(T value)` replacing the stored value
- A `toString()` returning `"Box[value]"`

Demonstrate it with `Box<String>`, `Box<Integer>`, and `Box<Double>`. Show that attempting to call `setValue` with the wrong type is a compile error (write it as a comment).

### Exercise 1.2: Generic Pair

Write a generic class `Pair<A, B>` that stores two values of potentially different types:
- Fields `first` of type `A` and `second` of type `B`
- A constructor `Pair(A first, B second)`
- `getFirst()` and `getSecond()`
- A method `swap()` that returns a new `Pair<B, A>` with the positions reversed
- `toString()` returning `"(first, second)"`

Demonstrate with:
- `Pair<String, Integer>` for a name and age
- `Pair<Boolean, String>` for a feature flag and its label
- Call `swap()` and show the returned type is `Pair<Integer, String>`

### Exercise 1.3: Generic Stack

Write a generic class `Stack<T>` backed by an array. Recall that Java does not allow `new T[n]` directly — use the cast `(T[]) new Object[n]` and suppress the unchecked warning.

Required methods:
- `push(T item)` — add an item; grow the backing array (double its capacity) when full
- `pop()` — remove and return the top item; throw `EmptyStackException` if empty; null out the vacated slot
- `peek()` — return the top item without removing it; throw `EmptyStackException` if empty
- `isEmpty()` and `size()`
- `toString()` showing the stack contents

Demonstrate with a `Stack<String>` and a `Stack<Integer>`. Show that the stack grows correctly beyond the initial capacity.

---

## Exercise Set 2: Generic Methods

### Exercise 2.1: Generic Swap

Write a static generic method `swap(T[] arr, int i, int j)` that swaps two elements in any array without any knowledge of the element type.

Demonstrate it on a `String[]` and an `Integer[]`. Confirm the compiler infers `T` without you specifying it at the call site.

### Exercise 2.2: Generic Find

Write two static generic methods:
- `findFirst(T[] arr, T target)` — returns the first element equal to `target`, or `null` if not found
- `contains(T[] arr, T target)` — returns `true` if any element equals `target`

Demonstrate on a `String[]` and a `Double[]`. Search for values that are present and values that are absent.

### Exercise 2.3: Generic Reverse

Write a static generic method `reverse(T[] arr)` that reverses the elements of any array in place. Do not create a second array — swap elements from the ends toward the middle.

Demonstrate on a `String[]` containing five words and an `Integer[]` containing six numbers. Print the array before and after the reversal using `Arrays.toString`.

---

## Exercise Set 3: Bounded Type Parameters

### Exercise 3.1: Generic Max and Min

Write two static generic methods:
- `max(T a, T b)` — returns the larger of two values
- `min(T a, T b)` — returns the smaller of two values

Both must carry the bound `<T extends Comparable<T>>` so that `compareTo` is available inside the method.

Demonstrate each with `Integer`, `Double`, and `String` arguments. Show that the compiler rejects a type that does not implement `Comparable` (write the rejection as a comment).

### Exercise 3.2: Sum of a Number List

Write a static generic method `sum(List<T> list)` with the bound `<T extends Number>` that returns the sum of all elements as a `double`. Use `.doubleValue()` to convert each element.

Demonstrate with a `List<Integer>`, a `List<Double>`, and a `List<Long>`.

### Exercise 3.3: Find Maximum in an Array

Write a static generic method `findMax(T[] arr)` with multiple bounds `<T extends Comparable<T> & java.io.Serializable>`. The method should:
- Throw `IllegalArgumentException` if the array is null or empty
- Return the maximum element by iterating and calling `compareTo`

Demonstrate with a `String[]` and an `Integer[]`. Explain in a comment why `String` and `Integer` satisfy both bounds while a plain custom class would not.

---

## Exercise Set 4: Wildcards

### Exercise 4.1: Print Any List

Write a static method `printAll(List<?> list)` that prints every element of any list regardless of its element type, one element per line with its index: `"[0] Alice"`.

Demonstrate by passing a `List<String>`, a `List<Integer>`, and a `List<Double>` to the same method. Explain in a comment why `List<Object>` would not work as the parameter type here.

### Exercise 4.2: Average of Numeric Lists

Write a static method `average(List<? extends Number> list)` that returns the arithmetic mean as a `double`.

Demonstrate with a `List<Integer>` (exam scores) and a `List<Double>` (ratings). Show that a `List<String>` cannot be passed (write the compiler error as a comment).

Explain in a comment why you cannot call `list.add(...)` inside this method.

### Exercise 4.3: Fill a List

Write a static method `fillWith(List<? super Integer> list, int count)` that adds `count` consecutive integers starting from 1 into the given list.

Demonstrate by calling it on a `List<Integer>`, a `List<Number>`, and a `List<Object>`. Print each list after filling.

Write a second method that applies PECS to copy from a `List<? extends Number>` into a `List<? super Number>` and demonstrate the transfer.

---

## Exercise Set 5: Generic Interfaces and Collections

### Exercise 5.1: Implement Comparable in a Generic Context

Create a class `SortedPair<T extends Comparable<T>>` that always stores its two values in ascending order (smaller first), regardless of the order they are passed to the constructor.

- Fields `lower` and `upper` of type `T`
- Constructor that accepts two `T` values and uses `compareTo` to assign them correctly
- `getLower()` and `getUpper()`
- `toString()` returning `"[lower, upper]"`

Demonstrate with `SortedPair<Integer>` and `SortedPair<String>`. Pass values in wrong order and confirm they are reordered.

### Exercise 5.2: Generic Repository Interface

Define a generic interface `Repository<T, ID>` with:
- `T findById(ID id)` — return the entity or `null`
- `void save(T entity)` — store or overwrite
- `void delete(ID id)` — remove
- `List<T> findAll()` — return all stored entities

Create a `Product` class (fields: `Long id`, `String name`, `double price`) and a `ProductRepository` class that implements `Repository<Product, Long>` using a `HashMap<Long, Product>` as the backing store.

Demonstrate: save three products, find by ID, delete one, call `findAll` and print the remaining two.

### Exercise 5.3: Generic Iterable Stack

Extend your `Stack<T>` from Exercise 1.3 so that it implements `Iterable<T>`. The iterator should return elements from top to bottom (most-recently-pushed first).

Demonstrate by using a `Stack<String>` in an enhanced for loop.

---

## Common Mistakes

### Using raw types instead of parameterised types

A raw type drops all generic information. The compiler cannot catch wrong assignments, and you are back to the pre-generics world of ClassCastException at runtime.

```java
// Wrong: raw type — the compiler cannot enforce the element type.
List list = new ArrayList();
list.add("hello");
list.add(42);        // No error at compile time.
String s = (String) list.get(1); // ClassCastException at runtime.

// Correct: parameterised type — wrong elements are caught at compile time.
List<String> names = new ArrayList<>();
names.add("hello");
// names.add(42); // Compiler error: incompatible types.
String s = names.get(0); // No cast required.
```

### Trying to create an array of a generic type

Java forbids `new T[n]` because the actual type of `T` is erased at runtime and the JVM would not know what array to create. The compiler rejects it outright.

```java
// Wrong: not allowed — T is erased at runtime.
public class Box<T> {
    // T[] items = new T[10]; // Compiler error: generic array creation.
}

// Correct: cast an Object array and suppress the unchecked warning.
public class Box<T> {
    @SuppressWarnings("unchecked")
    T[] items = (T[]) new Object[10]; // Safe: we know every slot holds a T.
}
```

### Confusing upper-bounded wildcards (read) with lower-bounded wildcards (write)

Remember PECS: **Producer Extends, Consumer Super.**

- `List<? extends Number>` is a **producer** — you read `Number` values from it, but you cannot add anything to it (the compiler does not know the exact subtype).
- `List<? super Integer>` is a **consumer** — you can add `Integer` values to it, but you can only read elements as `Object`.

```java
// Wrong: trying to add to an upper-bounded list.
List<? extends Number> readOnly = new ArrayList<Integer>();
// readOnly.add(42); // Compiler error: cannot add to a wildcard list.

// Correct: use lower bound when you need to write.
List<? super Integer> writeable = new ArrayList<Number>();
writeable.add(42); // Fine — Integer is a subtype of any supertype of Integer.
```

---

Solutions for these exercises are in the `solutions/` subfolder.
