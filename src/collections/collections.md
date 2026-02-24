# The Collections Framework

The Collections Framework is a unified architecture for storing, retrieving, and manipulating groups of objects in Java. It replaces the need for hand-rolled data structures by providing a set of well-tested interfaces and implementations that cover almost every data management scenario you will encounter. Understanding it is essential because real applications are rarely about single variables — they are about managing collections of data efficiently.

```
Think of it like this:
  You have a list of students, a set of unique cities, and a lookup table of IDs.
  Arrays could technically hold all of this, but they fight you every step of the way.
  The Collections Framework gives you the right tool for each job,
  all sharing the same familiar interface so you only need to learn the pattern once.
```

## 1. The Collections Framework Overview

The framework is built around a hierarchy of interfaces. Each interface defines a contract, and multiple classes implement each contract with different performance trade-offs.

```
Iterable
└── Collection
    ├── List  (ordered, allows duplicates)
    ├── Set   (no duplicates)
    └── Queue (FIFO ordering)
Map (key-value pairs, not a Collection)
```

Arrays fall short in several important ways:

- **Fixed size.** Once created, an array cannot grow or shrink. Adding a new element requires creating a new array and copying everything over.
- **No built-in search or sort methods.** You must write them yourself or call a separate utility class.
- **No remove operation.** Removing an element from the middle of an array means manually shifting every element after it.
- **No type-safe key-value storage.** There is no array equivalent of a dictionary or map.

The Collections Framework solves all of these problems with clean, generic interfaces.

## 2. List — ArrayList

`ArrayList` is the most commonly used collection in Java. It is a resizable array under the hood: it allocates a backing array and doubles its capacity automatically when that array fills up.

**Declaration — program to the interface, not the implementation:**

```java
List<String> cities = new ArrayList<>();
```

By declaring the variable as `List<String>` instead of `ArrayList<String>`, you can swap the implementation later without changing any other code.

**Core operations:**

```java
List<String> cities = new ArrayList<>();

// Adding elements
cities.add("Austin");           // appends to the end
cities.add("Boston");
cities.add("Chicago");
cities.add(1, "Denver");        // inserts at index 1, shifts others right

System.out.println(cities);
// Output: [Austin, Denver, Boston, Chicago]

// Reading elements
System.out.println(cities.get(0));      // Output: Austin
System.out.println(cities.size());      // Output: 4
System.out.println(cities.isEmpty());   // Output: false
System.out.println(cities.contains("Boston")); // Output: true

// Updating elements
cities.set(2, "Baltimore");
System.out.println(cities.get(2));      // Output: Baltimore

// Removing elements
cities.remove(0);               // removes by index
cities.remove("Denver");        // removes by object value
System.out.println(cities);
// Output: [Baltimore, Chicago]
```

**Iterating an ArrayList:**

```java
List<String> names = new ArrayList<>();
names.add("Alice");
names.add("Bob");
names.add("Carol");

// For-each loop (preferred when you do not need the index)
for (String name : names) {
    System.out.println(name);
}
// Output:
// Alice
// Bob
// Carol

// Traditional for loop (use when you need the index)
for (int i = 0; i < names.size(); i++) {
    System.out.println(i + ": " + names.get(i));
}
// Output:
// 0: Alice
// 1: Bob
// 2: Carol
```

**When to use ArrayList:** When you need fast random access by index, when reads far outnumber writes, or when you simply need an ordered list with duplicates allowed.

## 3. List — LinkedList

`LinkedList` implements the same `List` interface, but internally it stores each element in a separate node that holds a reference to the next and previous node. There is no backing array.

```java
LinkedList<String> tasks = new LinkedList<>();

tasks.add("Write tests");
tasks.add("Review PR");
tasks.add("Deploy");

// LinkedList-specific methods for working at the ends
tasks.addFirst("Plan sprint");      // inserts at the front
tasks.addLast("Send report");       // inserts at the back

System.out.println(tasks.peekFirst()); // Output: Plan sprint  (does not remove)
System.out.println(tasks.peekLast());  // Output: Send report  (does not remove)

tasks.removeFirst();  // removes and returns "Plan sprint"
tasks.removeLast();   // removes and returns "Send report"

System.out.println(tasks);
// Output: [Write tests, Review PR, Deploy]
```

**ArrayList vs LinkedList — a quick comparison:**

| Operation              | ArrayList                  | LinkedList                  |
|------------------------|----------------------------|-----------------------------|
| Access by index        | O(1) — fast                | O(n) — must walk the chain  |
| Insert / remove at end | O(1) amortized             | O(1)                        |
| Insert / remove middle | O(n) — must shift elements | O(n) — must walk to position|
| Memory per element     | Lower (packed array)       | Higher (node + two pointers)|

**When to use LinkedList:** When you frequently add or remove elements at the front or back of the list, such as implementing a queue or a double-ended task buffer. For almost everything else, `ArrayList` is the better default.

## 4. Set — HashSet

A `Set` is a collection that never contains duplicate elements. `HashSet` is the most common implementation. It stores elements in a hash table, which gives it very fast add, remove, and lookup operations, but it provides no guarantee about the order of iteration.

```java
Set<String> tags = new HashSet<>();

tags.add("java");
tags.add("programming");
tags.add("java");       // duplicate — silently ignored
tags.add("backend");

System.out.println(tags.size());            // Output: 3  (not 4)
System.out.println(tags.contains("java"));  // Output: true

tags.remove("backend");
System.out.println(tags);
// Output: [programming, java]  (order is not guaranteed)
```

**Eliminating duplicates from a List:**

```java
List<String> withDuplicates = new ArrayList<>();
withDuplicates.add("red");
withDuplicates.add("blue");
withDuplicates.add("red");
withDuplicates.add("green");
withDuplicates.add("blue");

Set<String> unique = new HashSet<>(withDuplicates);
System.out.println(unique);
// Output: [red, green, blue]  (order may vary)
```

**When to use HashSet:** When all you need to know is whether something is in a group (membership testing), and order does not matter. It is the fastest `Set` implementation.

## 5. Set — LinkedHashSet and TreeSet

When you need a `Set` with a predictable order, Java provides two alternatives.

**LinkedHashSet** maintains elements in the order they were inserted, without allowing duplicates.

**TreeSet** keeps elements sorted in their natural order (or by a `Comparator` you provide). It implements `NavigableSet`, which adds methods for range queries.

```java
List<String> input = Arrays.asList("banana", "apple", "cherry", "apple", "date");

// HashSet: no duplicates, unpredictable order
Set<String> hashSet = new HashSet<>(input);

// LinkedHashSet: no duplicates, insertion order preserved
Set<String> linkedHashSet = new LinkedHashSet<>(input);

// TreeSet: no duplicates, sorted alphabetically
Set<String> treeSet = new TreeSet<>(input);

System.out.println("HashSet:       " + hashSet);
// Output: HashSet:       [banana, cherry, apple, date]  (order varies)

System.out.println("LinkedHashSet: " + linkedHashSet);
// Output: LinkedHashSet: [banana, apple, cherry, date]

System.out.println("TreeSet:       " + treeSet);
// Output: TreeSet:       [apple, banana, cherry, date]
```

**TreeSet range operations:**

```java
TreeSet<String> fruits = new TreeSet<>();
fruits.add("apple");
fruits.add("banana");
fruits.add("cherry");
fruits.add("date");
fruits.add("elderberry");

System.out.println(fruits.first());              // Output: apple
System.out.println(fruits.last());               // Output: elderberry
System.out.println(fruits.headSet("cherry"));    // Output: [apple, banana]  (exclusive)
System.out.println(fruits.tailSet("cherry"));    // Output: [cherry, date, elderberry]  (inclusive)
```

## 6. Map — HashMap

A `Map` stores key-value pairs. Every key is unique, and each key maps to exactly one value. `HashMap` is the most common implementation, using a hash table for O(1) average-case lookups.

**Declaration:**

```java
Map<String, Integer> scores = new HashMap<>();
```

**Core operations:**

```java
Map<String, Integer> scores = new HashMap<>();

// Adding and updating entries
scores.put("Alice", 92);
scores.put("Bob", 85);
scores.put("Carol", 90);
scores.put("Alice", 95);    // overwrites the previous value for "Alice"

// Reading values
System.out.println(scores.get("Bob"));          // Output: 85
System.out.println(scores.get("Dave"));         // Output: null  (key does not exist)
System.out.println(scores.getOrDefault("Dave", 0)); // Output: 0

// Checking membership
System.out.println(scores.containsKey("Carol"));    // Output: true
System.out.println(scores.containsValue(85));        // Output: true

// Conditional insertion
scores.putIfAbsent("Bob", 99);   // ignored because "Bob" already exists
System.out.println(scores.get("Bob")); // Output: 85

// Removing an entry
scores.remove("Carol");
System.out.println(scores.size()); // Output: 2
```

**Iterating a Map:**

```java
Map<String, Integer> scores = new HashMap<>();
scores.put("Alice", 95);
scores.put("Bob", 85);
scores.put("Carol", 90);

// Iterate over keys only
for (String name : scores.keySet()) {
    System.out.println(name);
}

// Iterate over values only
for (int score : scores.values()) {
    System.out.println(score);
}

// Iterate over key-value pairs (most common)
for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    System.out.println(entry.getKey() + " -> " + entry.getValue());
}
// Output (order may vary):
// Alice -> 95
// Bob -> 85
// Carol -> 90
```

**When to use HashMap:** Any time you need to look up a value by a known key in constant time — configuration tables, caches, frequency counters, index structures.

## 7. Map — LinkedHashMap and TreeMap

Just like with sets, Java provides ordered map implementations when insertion order or sorted order matters.

**LinkedHashMap** preserves the order in which entries were inserted.

**TreeMap** keeps entries sorted by key in natural order (or by a supplied `Comparator`).

**Building a word-frequency counter, then printing alphabetically:**

```java
String[] words = {"banana", "apple", "cherry", "apple", "banana", "apple"};

// Count frequencies with HashMap (fast, unordered)
Map<String, Integer> frequency = new HashMap<>();
for (String word : words) {
    frequency.put(word, frequency.getOrDefault(word, 0) + 1);
}

System.out.println("HashMap (unordered): " + frequency);
// Output: HashMap (unordered): {banana=2, cherry=1, apple=3}  (order varies)

// Print alphabetically by passing the HashMap into a TreeMap
Map<String, Integer> sorted = new TreeMap<>(frequency);
System.out.println("TreeMap (sorted):    " + sorted);
// Output: TreeMap (sorted):    {apple=3, banana=2, cherry=1}
```

**LinkedHashMap preserving insertion order:**

```java
Map<String, String> config = new LinkedHashMap<>();
config.put("host", "localhost");
config.put("port", "8080");
config.put("timeout", "30");

for (Map.Entry<String, String> entry : config.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}
// Output:
// host = localhost
// port = 8080
// timeout = 30
```

## 8. Queue and Deque — ArrayDeque

A `Queue` processes elements in FIFO (first-in, first-out) order. A `Deque` (double-ended queue) supports insertion and removal at both ends, which means it can serve as both a queue and a stack.

`ArrayDeque` is the recommended implementation for both. It is backed by a resizable array and is generally faster than `LinkedList` for queue and stack operations.

**Queue interface method pairs:**

| Action  | Throws exception | Returns null/false |
|---------|------------------|--------------------|
| Insert  | `add(e)`         | `offer(e)`         |
| Remove  | `remove()`       | `poll()`           |
| Examine | `element()`      | `peek()`           |

Prefer the safe variants (`offer`, `poll`, `peek`) to avoid unchecked exceptions on empty collections.

**ArrayDeque as a queue (FIFO):**

```java
Deque<String> queue = new ArrayDeque<>();

queue.offer("first");
queue.offer("second");
queue.offer("third");

System.out.println(queue.peek());   // Output: first  (does not remove)
System.out.println(queue.poll());   // Output: first  (removes it)
System.out.println(queue.poll());   // Output: second
System.out.println(queue);
// Output: [third]
```

**ArrayDeque as a stack (LIFO):**

```java
Deque<String> stack = new ArrayDeque<>();

stack.push("page1");    // same as addFirst
stack.push("page2");
stack.push("page3");

System.out.println(stack.peek());   // Output: page3  (top of stack, not removed)
System.out.println(stack.pop());    // Output: page3  (removes from top)
System.out.println(stack.pop());    // Output: page2
System.out.println(stack);
// Output: [page1]
```

**When to use ArrayDeque:** Any time you need to process items in arrival order (task queues, BFS traversal), or when you need an undo/redo stack. Prefer it over the legacy `Stack` class, which is synchronized and slower.

## 9. Choosing the Right Collection

| Need                                               | Use            |
|----------------------------------------------------|----------------|
| Ordered list, duplicates allowed, fast random access | ArrayList      |
| Ordered list, fast insert/remove at ends           | LinkedList     |
| No duplicates, fast lookup, no order needed        | HashSet        |
| No duplicates, insertion order preserved           | LinkedHashSet  |
| No duplicates, sorted order                        | TreeSet        |
| Key-value lookup, no order needed                  | HashMap        |
| Key-value, insertion order preserved               | LinkedHashMap  |
| Key-value, sorted by key                           | TreeMap        |
| FIFO queue or LIFO stack                           | ArrayDeque     |

## 10. The Collections Utility Class

`java.util.Collections` (note the plural) is a utility class containing only static methods that operate on collections. It is distinct from the `Collection` interface.

```java
List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9, 3));

// Sort ascending (natural order)
Collections.sort(numbers);
System.out.println(numbers);
// Output: [1, 2, 3, 5, 8, 9]

// Reverse the order
Collections.reverse(numbers);
System.out.println(numbers);
// Output: [9, 8, 5, 3, 2, 1]

// Shuffle randomly
Collections.shuffle(numbers);
System.out.println(numbers);
// Output: [3, 9, 1, 8, 5, 2]  (order varies)

// Find min and max
Collections.sort(numbers); // sort first for clarity
System.out.println(Collections.min(numbers)); // Output: 1
System.out.println(Collections.max(numbers)); // Output: 9

// Count occurrences of an element
List<String> colors = Arrays.asList("red", "blue", "red", "green", "red");
System.out.println(Collections.frequency(colors, "red")); // Output: 3

// Create a read-only view of a list
List<String> mutable = new ArrayList<>(Arrays.asList("a", "b", "c"));
List<String> readOnly = Collections.unmodifiableList(mutable);
// readOnly.add("d"); // throws UnsupportedOperationException at runtime
System.out.println(readOnly); // Output: [a, b, c]
```

## 11. Complete Example

This example builds a simple student registry using three collections together, each chosen for a different purpose.

**The Student class:**

```java
public class Student {
    private String id;
    private String name;
    private String city;

    public Student(String id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    public String getId()   { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }

    @Override
    public String toString() {
        return name + " (" + id + ") from " + city;
    }
}
```

**The StudentRegistry class:**

```java
import java.util.*;

public class StudentRegistry {

    // Ordered list — preserves enrollment order, allows iterating all students
    private List<Student> enrollmentList = new ArrayList<>();

    // Set of unique cities — automatically deduplicates
    private Set<String> cities = new HashSet<>();

    // Map for fast ID lookup — O(1) access by student ID
    private Map<String, Student> idIndex = new HashMap<>();

    public void enroll(Student student) {
        enrollmentList.add(student);
        cities.add(student.getCity());
        idIndex.put(student.getId(), student);
    }

    public void remove(String id) {
        Student student = idIndex.remove(id);
        if (student != null) {
            enrollmentList.remove(student);
            // Rebuild city set after removal to ensure accuracy
            cities.clear();
            for (Student s : enrollmentList) {
                cities.add(s.getCity());
            }
            System.out.println("Removed: " + student.getName());
        } else {
            System.out.println("Student not found: " + id);
        }
    }

    public Student findById(String id) {
        return idIndex.getOrDefault(id, null);
    }

    public void printAll() {
        System.out.println("--- All Students (enrollment order) ---");
        for (Student s : enrollmentList) {
            System.out.println("  " + s);
        }
    }

    public void printStats() {
        System.out.println("--- Registry Stats ---");
        System.out.println("Total students : " + enrollmentList.size());
        System.out.println("Unique cities  : " + cities.size());
        System.out.println("Cities         : " + new TreeSet<>(cities)); // sorted for display
    }

    public static void main(String[] args) {
        StudentRegistry registry = new StudentRegistry();

        // Populate from a common data source
        registry.enroll(new Student("S001", "Alice",   "Austin"));
        registry.enroll(new Student("S002", "Bob",     "Boston"));
        registry.enroll(new Student("S003", "Carol",   "Austin"));   // same city as Alice
        registry.enroll(new Student("S004", "Dave",    "Chicago"));
        registry.enroll(new Student("S005", "Eve",     "Boston"));   // same city as Bob

        registry.printAll();
        // Output:
        // --- All Students (enrollment order) ---
        //   Alice (S001) from Austin
        //   Bob (S002) from Boston
        //   Carol (S003) from Austin
        //   Dave (S004) from Chicago
        //   Eve (S005) from Boston

        registry.printStats();
        // Output:
        // --- Registry Stats ---
        // Total students : 5
        // Unique cities  : 3
        // Cities         : [Austin, Boston, Chicago]

        // Fast lookup by ID
        Student found = registry.findById("S003");
        System.out.println("Found: " + found);
        // Output: Found: Carol (S003) from Austin

        // Lookup of a non-existent ID
        System.out.println("Found: " + registry.findById("S999"));
        // Output: Found: null

        // Remove a student and verify stats update
        registry.remove("S002");
        // Output: Removed: Bob

        registry.printStats();
        // Output:
        // --- Registry Stats ---
        // Total students : 4
        // Unique cities  : 3   (Boston still has Eve)
        // Cities         : [Austin, Boston, Chicago]
    }
}
```

## 12. Key Takeaways

- The Collections Framework provides a unified set of interfaces (`List`, `Set`, `Queue`, `Map`) and multiple implementations for each, letting you choose the right trade-off for your use case.
- Always declare collection variables using the interface type (`List`, `Set`, `Map`) rather than the concrete class to keep code flexible.
- `ArrayList` is the default general-purpose list; reach for `LinkedList` only when you have frequent insertions or removals at the ends.
- `HashSet` offers the fastest membership testing; use `LinkedHashSet` to preserve insertion order and `TreeSet` for sorted order.
- `HashMap` is the default map; `LinkedHashMap` preserves insertion order and `TreeMap` sorts by key.
- `ArrayDeque` is the recommended implementation for both queues and stacks; prefer `offer`/`poll`/`peek` over the exception-throwing variants.
- The `Collections` utility class provides ready-made algorithms — sort, reverse, shuffle, min, max, frequency, and unmodifiable wrappers — so you rarely need to write them yourself.
- Mixing collection types in a single class (as in the student registry) is common and expected: use each type where its strengths apply.

---

The Collections Framework is the backbone of nearly every real Java application — once you internalize which collection fits which problem, you will spend less time fighting your data structures and more time solving the actual problem at hand.
