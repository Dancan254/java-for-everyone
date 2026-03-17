package generics.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Exercise5Solution.java
 *
 * Solutions for Exercise Set 5: Generic Interfaces and Collections.
 * Covers: Comparable<T> used inside generic class logic, implementing a
 * generic interface with a concrete type pair, and implementing Iterable<T>
 * to enable enhanced for-loop iteration over a custom collection.
 */
public class Exercise5Solution {

    // -------------------------------------------------------------------------
    // Exercise 5.1 — SortedPair<T extends Comparable<T>>
    // -------------------------------------------------------------------------

    /**
     * A pair that always stores its two values in ascending order.
     * The bound T extends Comparable<T> means compareTo is available
     * in the constructor so the ordering can be established immediately.
     */
    static class SortedPair<T extends Comparable<T>> {
        private final T lower;
        private final T upper;

        /**
         * Accepts two values in any order and assigns them so that
         * lower <= upper according to T's natural ordering.
         */
        public SortedPair(T a, T b) {
            if (a.compareTo(b) <= 0) {
                this.lower = a;
                this.upper = b;
            } else {
                this.lower = b;
                this.upper = a;
            }
        }

        public T getLower() { return lower; }
        public T getUpper() { return upper; }

        @Override
        public String toString() {
            return "[" + lower + ", " + upper + "]";
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 5.2 — Generic Repository interface + Product + ProductRepository
    // -------------------------------------------------------------------------

    /**
     * A type-safe CRUD interface. T is the entity type; ID is the key type.
     * This pattern mirrors Spring Data's JpaRepository<T, ID>.
     */
    interface Repository<T, ID> {
        T findById(ID id);
        void save(T entity);
        void delete(ID id);
        List<T> findAll();
    }

    /** A simple product entity with an id, name, and price. */
    static class Product {
        private final Long   id;
        private final String name;
        private final double price;

        public Product(Long id, String name, double price) {
            this.id    = id;
            this.name  = name;
            this.price = price;
        }

        public Long   getId()    { return id;    }
        public String getName()  { return name;  }
        public double getPrice() { return price; }

        @Override
        public String toString() {
            return "Product{id=" + id + ", name='" + name + "', price=" + price + "}";
        }
    }

    /**
     * Concrete implementation of Repository<Product, Long>.
     * The compiler enforces that save/findById/delete use the correct types —
     * no raw casts required anywhere in this class.
     */
    static class ProductRepository implements Repository<Product, Long> {
        private final Map<Long, Product> store = new HashMap<>();

        @Override
        public Product findById(Long id) {
            return store.get(id); // returns null if not present
        }

        @Override
        public void save(Product product) {
            store.put(product.getId(), product);
        }

        @Override
        public void delete(Long id) {
            store.remove(id);
        }

        @Override
        public List<Product> findAll() {
            return new ArrayList<>(store.values());
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 5.3 — Iterable Stack<T>
    // -------------------------------------------------------------------------

    /**
     * A generic stack that implements Iterable<T> so it can be used in an
     * enhanced for loop. The iterator returns elements from top to bottom
     * (most-recently-pushed first) without modifying the stack.
     */
    static class Stack<T> implements Iterable<T> {
        private T[] elements;
        private int size;

        @SuppressWarnings("unchecked")
        public Stack() {
            elements = (T[]) new Object[4];
            size = 0;
        }

        public void push(T item) {
            if (size == elements.length) {
                elements = Arrays.copyOf(elements, elements.length * 2);
            }
            elements[size++] = item;
        }

        public T pop() {
            if (size == 0) throw new java.util.EmptyStackException();
            T item = elements[--size];
            elements[size] = null;
            return item;
        }

        public boolean isEmpty() { return size == 0; }
        public int size()        { return size; }

        /**
         * Returns an Iterator that walks the backing array from top (size-1)
         * down to bottom (0), yielding the most-recently-pushed element first.
         */
        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                // Start from the top of the stack and walk downward.
                private int cursor = size - 1;

                @Override
                public boolean hasNext() {
                    return cursor >= 0;
                }

                @Override
                public T next() {
                    if (!hasNext()) {
                        throw new java.util.NoSuchElementException();
                    }
                    return elements[cursor--];
                }
            };
        }

        @Override
        public String toString() {
            return "Stack" + Arrays.toString(Arrays.copyOf(elements, size));
        }
    }

    // -------------------------------------------------------------------------
    // main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 5.1: SortedPair ---

        System.out.println("=== Exercise 5.1: SortedPair<T extends Comparable<T>> ===");

        // Pass values in wrong order — constructor corrects them.
        SortedPair<Integer> intPair = new SortedPair<>(99, 3);
        System.out.println(intPair);
        // Output: [3, 99]

        System.out.println("lower=" + intPair.getLower() + ", upper=" + intPair.getUpper());
        // Output: lower=3, upper=99

        // String uses lexicographic order.
        SortedPair<String> wordPair = new SortedPair<>("zebra", "apple");
        System.out.println(wordPair);
        // Output: [apple, zebra]

        // Values already in order — no change.
        SortedPair<Double> doublePair = new SortedPair<>(1.5, 9.9);
        System.out.println(doublePair);
        // Output: [1.5, 9.9]

        // --- Exercise 5.2: ProductRepository ---

        System.out.println("\n=== Exercise 5.2: Generic Repository Interface ===");

        ProductRepository repo = new ProductRepository();

        repo.save(new Product(1L, "Keyboard", 49.99));
        repo.save(new Product(2L, "Mouse",    29.99));
        repo.save(new Product(3L, "Monitor", 249.99));

        System.out.println(repo.findById(1L));
        // Output: Product{id=1, name='Keyboard', price=49.99}

        System.out.println(repo.findById(2L));
        // Output: Product{id=2, name='Mouse', price=29.99}

        repo.delete(2L);
        System.out.println("After deleting id=2:");

        List<Product> remaining = repo.findAll();
        // HashMap does not guarantee insertion order; sort for deterministic output.
        remaining.sort((a, b) -> Long.compare(a.getId(), b.getId()));
        for (Product p : remaining) {
            System.out.println("  " + p);
        }
        // Output (after sort):
        //   Product{id=1, name='Keyboard', price=49.99}
        //   Product{id=3, name='Monitor', price=249.99}

        System.out.println("findById(2L) after delete: " + repo.findById(2L));
        // Output: findById(2L) after delete: null

        // --- Exercise 5.3: Iterable Stack ---

        System.out.println("\n=== Exercise 5.3: Iterable Stack<T> ===");

        Stack<String> taskStack = new Stack<>();
        taskStack.push("write tests");
        taskStack.push("implement feature");
        taskStack.push("code review");
        taskStack.push("deploy");

        System.out.println("Stack (top to bottom via enhanced for):");
        // The Iterable<T> implementation lets us use a standard for-each loop.
        for (String task : taskStack) {
            System.out.println("  " + task);
        }
        // Output:
        //   deploy
        //   code review
        //   implement feature
        //   write tests

        // Confirm the stack was not modified by iteration.
        System.out.println("Size after iteration: " + taskStack.size());
        // Output: Size after iteration: 4
    }
}
