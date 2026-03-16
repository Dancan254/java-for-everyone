# Iterators and the Iterable Contract

The enhanced for loop (`for (String s : list)`) looks simple, but underneath it uses a protocol called the **Iterator pattern**. Understanding this protocol explains why some types work with the enhanced for loop and others do not, how to remove elements while iterating safely, and how to make your own classes work with the enhanced for loop.

---

## 1. What the Enhanced For Loop Actually Does

```
Think of it like this:
  An Iterator is a bookmark that moves through a book one page at a time.
  hasNext() asks "are there more pages?"
  next() turns to the next page and returns its content.
  The enhanced for loop is syntactic sugar that creates the bookmark and calls these
  two methods in a loop — so you never have to write the loop machinery yourself.
```

The enhanced for loop:

```java
List<String> names = List.of("Alice", "Bob", "Carol");

for (String name : names) {
    System.out.println(name);
}
```

is compiled by the Java compiler into exactly:

```java
Iterator<String> it = names.iterator();
while (it.hasNext()) {
    String name = it.next();
    System.out.println(name);
}
// Output:
// Alice
// Bob
// Carol
```

The enhanced for loop works with any class that implements `Iterable<T>`, which includes all `Collection` types (`List`, `Set`, `Queue`, `Deque`) and arrays.

---

## 2. The Iterator Interface

`java.util.Iterator<T>` has three methods:

| Method | Description |
|---|---|
| `boolean hasNext()` | Returns `true` if the iteration has more elements |
| `T next()` | Returns the next element and advances the cursor |
| `void remove()` | Removes the last element returned by `next()` from the underlying collection (optional operation) |

`remove()` is the only safe way to remove elements from a collection while iterating. All other approaches throw `ConcurrentModificationException`.

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

List<String> names = new ArrayList<>(List.of("Alice", "Bob", "Carol", "Dave"));

// Remove all names with length <= 3 while iterating.
Iterator<String> it = names.iterator();
while (it.hasNext()) {
    String name = it.next();
    if (name.length() <= 3) {
        it.remove(); // Safe: removes through the iterator, not directly from the list.
    }
}

System.out.println(names); // Output: [Alice, Carol, Dave]
```

---

## 3. ConcurrentModificationException

Modifying a collection directly while iterating over it with a for-each loop or an explicit iterator throws `ConcurrentModificationException`. Collections maintain a modification count; the iterator checks this count on every `next()` call.

```java
List<String> names = new ArrayList<>(List.of("Alice", "Bob", "Carol"));

// Wrong: removing from the list directly while the enhanced for loop runs.
for (String name : names) {
    if (name.equals("Bob")) {
        names.remove(name); // Throws ConcurrentModificationException.
    }
}
```

Safe alternatives:

```java
// Option 1: use Iterator.remove().
Iterator<String> it = names.iterator();
while (it.hasNext()) {
    if (it.next().equals("Bob")) {
        it.remove(); // Safe.
    }
}
System.out.println(names); // Output: [Alice, Carol]

// Option 2: use List.removeIf() (Java 8+) — cleaner for simple conditions.
names.removeIf(name -> name.equals("Bob"));
System.out.println(names); // Output: [Alice, Carol]

// Option 3: collect into a new list.
List<String> filtered = names.stream()
        .filter(name -> !name.equals("Bob"))
        .collect(java.util.stream.Collectors.toList());
System.out.println(filtered); // Output: [Alice, Carol]
```

---

## 4. ListIterator — Bidirectional Iteration

`ListIterator<T>` extends `Iterator<T>` with the ability to iterate backwards, access the current index, and replace elements in place. It is available only for `List` types.

```java
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

List<String> names = new ArrayList<>(List.of("alice", "bob", "carol"));

// Capitalise each name in place using ListIterator.set().
ListIterator<String> lit = names.listIterator();
while (lit.hasNext()) {
    String name = lit.next();
    String capitalised = name.substring(0, 1).toUpperCase() + name.substring(1);
    lit.set(capitalised); // Replace the element that was just returned by next().
}

System.out.println(names); // Output: [Alice, Bob, Carol]

// Iterate in reverse using hasPrevious() and previous().
while (lit.hasPrevious()) {
    System.out.println(lit.previous());
}
// Output:
// Carol
// Bob
// Alice
```

---

## 5. The Iterable Interface

`java.lang.Iterable<T>` has one abstract method:

```java
public interface Iterable<T> {
    Iterator<T> iterator();
}
```

Any class that implements `Iterable<T>` can be used in an enhanced for loop. Implementing it requires implementing a corresponding `Iterator<T>`.

---

## 6. Implementing Iterable on Your Own Class

To make a custom class work with the enhanced for loop, implement `Iterable<T>` and provide a nested `Iterator<T>` implementation.

```java
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A fixed-size circular buffer that supports iteration in insertion order.
 * Elements are stored in an array; when the buffer is full, new elements
 * overwrite the oldest ones.
 */
public class CircularBuffer<T> implements Iterable<T> {

    private final Object[] data;
    private int head  = 0;    // Index of the oldest element.
    private int size  = 0;    // Number of elements currently stored.
    private final int capacity;

    public CircularBuffer(int capacity) {
        this.capacity = capacity;
        this.data     = new Object[capacity];
    }

    /** Add an element, overwriting the oldest if full. */
    public void add(T element) {
        int tail = (head + size) % capacity; // Where to write.
        data[tail] = element;
        if (size < capacity) {
            size++;
        } else {
            head = (head + 1) % capacity; // Oldest slot is now the next slot.
        }
    }

    public int size() { return size; }

    /** Return an iterator that visits elements in insertion order, oldest first. */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private int remaining = size;       // How many elements are left to return.
            private int cursor    = head;       // Index of the next element to return.

            @Override
            public boolean hasNext() {
                return remaining > 0;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T element = (T) data[cursor];
                cursor    = (cursor + 1) % capacity; // Advance, wrapping at capacity.
                remaining--;
                return element;
            }
        };
    }
}
```

```java
CircularBuffer<Integer> buffer = new CircularBuffer<>(3);
buffer.add(1);
buffer.add(2);
buffer.add(3);
buffer.add(4); // Overwrites 1 — the oldest element.

// The enhanced for loop works because CircularBuffer implements Iterable.
for (int value : buffer) {
    System.out.println(value);
}
// Output:
// 2
// 3
// 4
```

---

## 7. Iterator vs forEach vs Streams

Three ways to process every element of a collection:

```java
List<String> names = List.of("Alice", "Bob", "Carol");

// 1. Iterator — most explicit; necessary when you need to remove elements.
Iterator<String> it = names.iterator();
while (it.hasNext()) {
    System.out.println(it.next());
}

// 2. forEach (Iterable.forEach, Java 8+) — concise for simple processing.
names.forEach(System.out::println);

// 3. Stream — for transforming, filtering, or collecting results.
names.stream()
     .filter(name -> name.length() > 3)
     .map(String::toUpperCase)
     .forEach(System.out::println);
// Output: ALICE CAROL
```

**When to use each:**

| Approach | Best for |
|---|---|
| Iterator | Removing elements while iterating; fine-grained cursor control; bidirectional traversal (ListIterator) |
| Enhanced for | Simple read-only traversal; clearest and least verbose for the common case |
| `forEach` | Simple processing with a method reference; slightly more concise than enhanced for |
| Stream | Filtering, transforming, reducing, or collecting results; multi-step pipelines |

---

## 8. Common Mistakes

### Calling next() without checking hasNext()

`next()` throws `NoSuchElementException` if the iteration is exhausted. Always check `hasNext()` first.

```java
Iterator<String> it = List.of("Alice").iterator();
it.next();           // Returns "Alice".
it.next();           // Throws NoSuchElementException — no more elements.
```

### Calling remove() before next()

`remove()` removes the element most recently returned by `next()`. Calling it before any `next()` call, or calling it twice in a row, throws `IllegalStateException`.

```java
Iterator<String> it = new ArrayList<>(List.of("Alice", "Bob")).iterator();
it.remove(); // IllegalStateException — next() has not been called yet.
```

### Modifying the collection while using an enhanced for loop

As shown in Section 3, this always causes `ConcurrentModificationException`. Use `removeIf` or an explicit `Iterator.remove()` instead.

### Using Iterator on concurrent collections without synchronisation

Standard iterators are fail-fast: they detect structural modification and throw immediately. `ConcurrentHashMap` and `CopyOnWriteArrayList` provide weakly-consistent iterators that do not throw but may not reflect all concurrent modifications. Know which collection you are using before assuming iterator behaviour.

---

## 9. Key Takeaways

- The enhanced for loop compiles to an explicit `Iterator` loop; it works with any `Iterable<T>`
- `Iterator` provides `hasNext()`, `next()`, and `remove()` — the only three methods you need
- `Iterator.remove()` is the only safe way to remove elements from a collection during iteration
- Direct modification of a collection during iteration causes `ConcurrentModificationException`
- `ListIterator` extends `Iterator` with backward traversal and in-place replacement via `set()`
- Implementing `Iterable<T>` on a custom class requires providing a nested `Iterator<T>` that implements `hasNext()` and `next()`
- For simple traversal, prefer the enhanced for loop; for element removal, use `removeIf()` or an explicit iterator; for transformation pipelines, use streams

---

The Iterator pattern is the foundation that all Java collection traversal is built on. Once you understand what the enhanced for loop compiles to, the whole design becomes transparent — and the rules about when you can and cannot modify a collection while iterating follow naturally.
