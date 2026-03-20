package collections;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * CollectionsDemo.java
 *
 * A self-contained demonstration of the Java Collections Framework covering
 * every concept in collections.md:
 *
 *   - ArrayList: add, add-at-index, get, size, isEmpty, contains, set, remove-by-index,
 *     remove-by-value, for-each iteration, index-based iteration
 *   - LinkedList: addFirst/addLast, peekFirst/peekLast, removeFirst/removeLast
 *   - ArrayDeque as a Queue (FIFO): offer, peek, poll
 *   - ArrayDeque as a Stack (LIFO): push, peek, pop
 *   - HashSet: add, size, contains, remove, duplicate suppression,
 *     constructing from a List to eliminate duplicates
 *   - TreeSet: sorted natural order, first/last, headSet/tailSet
 *   - HashMap: put, get, getOrDefault, containsKey, containsValue,
 *     putIfAbsent, remove, size, keySet/values/entrySet iteration
 *   - TreeMap: sorted keys, word-frequency counter pattern
 *   - LinkedHashMap: insertion-order preserved iteration
 *   - Collections utility class: sort, reverse, shuffle, min, max,
 *     frequency, unmodifiableList
 *   - Iterator: explicit iteration with hasNext/next/remove
 */
public class CollectionsDemo {

    // -------------------------------------------------------------------------
    // main method
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- 1. ArrayList Operations ---

        System.out.println("=== 1. ArrayList Operations ===");

        List<String> cities = new ArrayList<>();

        // add() appends to the end; add(index, value) inserts and shifts
        cities.add("Austin");
        cities.add("Boston");
        cities.add("Chicago");
        cities.add(1, "Denver");    // inserts at index 1, shifts Boston and Chicago right

        System.out.println(cities);
        // Output: [Austin, Denver, Boston, Chicago]

        // Reading elements
        System.out.println(cities.get(0));          // Output: Austin
        System.out.println(cities.size());          // Output: 4
        System.out.println(cities.isEmpty());       // Output: false
        System.out.println(cities.contains("Boston")); // Output: true

        // Updating elements
        cities.set(2, "Baltimore");
        System.out.println(cities.get(2));          // Output: Baltimore

        // Removing elements
        cities.remove(0);               // removes by index — removes "Austin"
        cities.remove("Denver");        // removes by object value
        System.out.println(cities);
        // Output: [Baltimore, Chicago]

        // For-each loop — preferred when the index is not needed
        List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Carol"));
        for (String name : names) {
            System.out.println(name);
        }
        // Output:
        // Alice
        // Bob
        // Carol

        // Traditional for loop — use when the index matters
        for (int i = 0; i < names.size(); i++) {
            System.out.println(i + ": " + names.get(i));
        }
        // Output:
        // 0: Alice
        // 1: Bob
        // 2: Carol

        // --- 2. LinkedList as a Deque ---

        System.out.println("\n=== 2. LinkedList as a Deque ===");

        // LinkedList implements both List and Deque, making it useful for
        // working at the front and back of a sequence.
        LinkedList<String> tasks = new LinkedList<>();
        tasks.add("Write tests");
        tasks.add("Review PR");
        tasks.add("Deploy");

        tasks.addFirst("Plan sprint");  // inserts at the front
        tasks.addLast("Send report");   // inserts at the back

        System.out.println(tasks.peekFirst()); // Output: Plan sprint  (does not remove)
        System.out.println(tasks.peekLast());  // Output: Send report  (does not remove)

        tasks.removeFirst();    // removes "Plan sprint"
        tasks.removeLast();     // removes "Send report"

        System.out.println(tasks);
        // Output: [Write tests, Review PR, Deploy]

        // --- 3. ArrayDeque as a Queue (FIFO) ---

        System.out.println("\n=== 3. ArrayDeque as a Queue (FIFO) ===");

        // offer/poll/peek are the safe variants — they return null instead of
        // throwing an exception when the deque is empty.
        Deque<String> queue = new ArrayDeque<>();
        queue.offer("first");
        queue.offer("second");
        queue.offer("third");

        System.out.println(queue.peek());   // Output: first  (does not remove)
        System.out.println(queue.poll());   // Output: first  (removes it)
        System.out.println(queue.poll());   // Output: second
        System.out.println(queue);
        // Output: [third]

        // --- 4. ArrayDeque as a Stack (LIFO) ---

        System.out.println("\n=== 4. ArrayDeque as a Stack (LIFO) ===");

        // push adds to the front (same as addFirst); pop removes from the front.
        Deque<String> stack = new ArrayDeque<>();
        stack.push("page1");
        stack.push("page2");
        stack.push("page3");

        System.out.println(stack.peek());   // Output: page3  (top of stack, not removed)
        System.out.println(stack.pop());    // Output: page3  (removes from top)
        System.out.println(stack.pop());    // Output: page2
        System.out.println(stack);
        // Output: [page1]

        // --- 5. HashSet ---

        System.out.println("\n=== 5. HashSet ===");

        // A Set never contains duplicates. HashSet provides O(1) average add/remove/lookup
        // but makes no guarantees about iteration order.
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

        // Eliminating duplicates from a List by passing it into a HashSet constructor
        List<String> withDuplicates = new ArrayList<>(
                Arrays.asList("red", "blue", "red", "green", "blue"));
        Set<String> unique = new HashSet<>(withDuplicates);
        System.out.println(unique);
        // Output: [red, green, blue]  (order may vary)

        // --- 6. TreeSet — Sorted Order and Range Queries ---

        System.out.println("\n=== 6. TreeSet (sorted order and range queries) ===");

        // TreeSet keeps elements sorted in natural order and implements NavigableSet,
        // which adds range-query methods such as headSet and tailSet.
        List<String> input = Arrays.asList("banana", "apple", "cherry", "apple", "date");

        Set<String> hashSet      = new HashSet<>(input);
        Set<String> linkedHashSet = new java.util.LinkedHashSet<>(input);
        Set<String> treeSet      = new TreeSet<>(input);

        System.out.println("HashSet:       " + hashSet);
        // Output: HashSet:       [banana, cherry, apple, date]  (order varies)

        System.out.println("LinkedHashSet: " + linkedHashSet);
        // Output: LinkedHashSet: [banana, apple, cherry, date]

        System.out.println("TreeSet:       " + treeSet);
        // Output: TreeSet:       [apple, banana, cherry, date]

        // Range operations
        TreeSet<String> fruits = new TreeSet<>();
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("cherry");
        fruits.add("date");
        fruits.add("elderberry");

        System.out.println(fruits.first());              // Output: apple
        System.out.println(fruits.last());               // Output: elderberry
        System.out.println(fruits.headSet("cherry"));    // Output: [apple, banana]  (exclusive upper bound)
        System.out.println(fruits.tailSet("cherry"));    // Output: [cherry, date, elderberry]  (inclusive lower bound)

        // --- 7. HashMap ---

        System.out.println("\n=== 7. HashMap ===");

        // HashMap is the go-to Map for O(1) average-case key lookups.
        // Iteration order is not guaranteed.
        Map<String, Integer> scores = new HashMap<>();

        // put() adds a new entry or overwrites an existing one with the same key
        scores.put("Alice", 92);
        scores.put("Bob", 85);
        scores.put("Carol", 90);
        scores.put("Alice", 95);    // overwrites 92 with 95

        // Reading values
        System.out.println(scores.get("Bob"));               // Output: 85
        System.out.println(scores.get("Dave"));              // Output: null  (key does not exist)
        System.out.println(scores.getOrDefault("Dave", 0));  // Output: 0

        // Checking membership
        System.out.println(scores.containsKey("Carol"));     // Output: true
        System.out.println(scores.containsValue(85));        // Output: true

        // Conditional insertion — ignored because "Bob" already has a value
        scores.putIfAbsent("Bob", 99);
        System.out.println(scores.get("Bob"));               // Output: 85

        // Removing an entry
        scores.remove("Carol");
        System.out.println(scores.size());                   // Output: 2

        // Iterating a Map
        Map<String, Integer> results = new HashMap<>();
        results.put("Alice", 95);
        results.put("Bob", 85);
        results.put("Carol", 90);

        // Iterate over keys only
        System.out.println("Keys:");
        for (String name : results.keySet()) {
            System.out.println("  " + name);
        }
        // Output (order may vary):
        //   Alice
        //   Bob
        //   Carol

        // Iterate over values only
        System.out.println("Values:");
        for (int score : results.values()) {
            System.out.println("  " + score);
        }
        // Output (order may vary):
        //   95
        //   85
        //   90

        // Iterate over key-value pairs — most common pattern
        System.out.println("Entries:");
        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            System.out.println("  " + entry.getKey() + " -> " + entry.getValue());
        }
        // Output (order may vary):
        //   Alice -> 95
        //   Bob -> 85
        //   Carol -> 90

        // --- 8. TreeMap — Sorted Keys ---

        System.out.println("\n=== 8. TreeMap (sorted keys) ===");

        // TreeMap stores entries sorted by key in natural order.
        // A common pattern: count with HashMap (fast), then view sorted via TreeMap.
        String[] words = {"banana", "apple", "cherry", "apple", "banana", "apple"};

        Map<String, Integer> frequency = new HashMap<>();
        for (String word : words) {
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        System.out.println("HashMap (unordered): " + frequency);
        // Output: HashMap (unordered): {banana=2, cherry=1, apple=3}  (order varies)

        // Pass the HashMap into a TreeMap constructor to sort by key alphabetically
        Map<String, Integer> sorted = new TreeMap<>(frequency);
        System.out.println("TreeMap (sorted):    " + sorted);
        // Output: TreeMap (sorted):    {apple=3, banana=2, cherry=1}

        // --- 9. LinkedHashMap — Insertion Order ---

        System.out.println("\n=== 9. LinkedHashMap (insertion order) ===");

        // LinkedHashMap is a HashMap that maintains the order entries were inserted.
        // Useful when the sequence of keys carries meaning, such as a configuration file.
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

        // --- 10. Collections Utility Methods ---

        System.out.println("\n=== 10. Collections Utility Methods ===");

        List<Integer> numbers = new ArrayList<>(Arrays.asList(5, 2, 8, 1, 9, 3));

        // Sort ascending (natural order)
        Collections.sort(numbers);
        System.out.println(numbers);
        // Output: [1, 2, 3, 5, 8, 9]

        // Reverse the order in-place
        Collections.reverse(numbers);
        System.out.println(numbers);
        // Output: [9, 8, 5, 3, 2, 1]

        // Shuffle randomly — exact output varies each run
        Collections.shuffle(numbers);
        System.out.println(numbers);
        // Output: [3, 9, 1, 8, 5, 2]  (order varies)

        // Find min and max (sort first so the output below is deterministic)
        Collections.sort(numbers);
        System.out.println(Collections.min(numbers)); // Output: 1
        System.out.println(Collections.max(numbers)); // Output: 9

        // Count occurrences of an element
        List<String> colors = Arrays.asList("red", "blue", "red", "green", "red");
        System.out.println(Collections.frequency(colors, "red")); // Output: 3

        // Create a read-only view — mutation attempts throw UnsupportedOperationException
        List<String> mutable  = new ArrayList<>(Arrays.asList("a", "b", "c"));
        List<String> readOnly = Collections.unmodifiableList(mutable);
        // readOnly.add("d"); // throws UnsupportedOperationException at runtime
        System.out.println(readOnly); // Output: [a, b, c]

        // --- 11. Iterator Usage ---

        System.out.println("\n=== 11. Iterator Usage ===");

        // An Iterator gives explicit control over traversal and supports safe removal
        // of elements during iteration — something a for-each loop cannot do.
        List<String> languages = new ArrayList<>(
                Arrays.asList("Java", "Python", "C++", "JavaScript", "Go"));

        Iterator<String> it = languages.iterator();
        while (it.hasNext()) {
            String lang = it.next();
            if (lang.contains("+")) {
                it.remove();    // safe removal — does not cause ConcurrentModificationException
            }
        }
        System.out.println(languages);
        // Output: [Java, Python, JavaScript, Go]

        // Iterating and printing each element explicitly
        Iterator<String> reader = languages.iterator();
        while (reader.hasNext()) {
            System.out.println(reader.next());
        }
        // Output:
        // Java
        // Python
        // JavaScript
        // Go
    }
}
