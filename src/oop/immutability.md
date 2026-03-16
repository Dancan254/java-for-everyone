# Immutability

An immutable object is one whose state cannot be changed after construction. Once it is created with certain values, those values are fixed for the lifetime of the object. Immutability is one of the most consistently valuable design decisions in Java, and understanding it makes you a significantly better programmer.

---

## 1. What Immutability Means

Immutability means that every field of an object is set in the constructor and never changed again. There are no setters, no methods that assign to fields, and no way for external code to modify the object's internal state.

```
Think of it like this:
  A mutable object is a whiteboard — anyone with access can erase and rewrite.
  An immutable object is a printed document — it can be read by anyone, but nobody can alter it.
  If you need a modified version, you print a new document.
```

---

## 2. Why Immutability Matters

**Thread safety.** An immutable object can be shared between threads without synchronisation. Since no thread can modify its state, there are no race conditions or data corruption risks. This is the primary reason `String`, `Integer`, and most value types in Java are immutable.

**Easier reasoning.** When reading code that uses an immutable object, you know that passing it to a method or storing it in a field cannot change it. There is no need to trace every code path to see whether something modified it.

**Safe as map keys and set members.** Hash-based collections (`HashMap`, `HashSet`) require that an object's hash code does not change after it is stored. Mutable objects used as keys are a frequent source of bugs (see the `equals-hashcode.md` guide). Immutable objects are always safe because their hash code never changes.

**Defensive programming.** Returning a reference to an internal mutable field exposes your object's internals to external modification. Returning an immutable object removes this risk entirely.

---

## 3. How to Make a Class Immutable

Five rules must all be followed:

1. **Declare the class `final`** — prevents subclasses from overriding methods and adding mutable state.
2. **Declare all fields `private` and `final`** — `private` prevents direct access; `final` prevents reassignment.
3. **Provide no setters** — no methods should assign to any field after construction.
4. **Initialize all fields in the constructor.**
5. **Defensive copy on input and output** — if a field is a mutable object (e.g., `List`, `Date`), copy it when storing and when returning.

---

## 4. Defensive Copying

Declaring a field `final` makes the reference immutable — you cannot reassign the field to point to a different object. But it does NOT make the object the field refers to immutable. If the field holds a `List`, external code can still call `.add()` on it.

```java
import java.util.ArrayList;
import java.util.List;

// A "naive" attempt at immutability — looks final but is not.
public final class NaiveImmutableOrder {
    private final List<String> items; // final means the reference can't change, not the List.

    public NaiveImmutableOrder(List<String> items) {
        this.items = items; // Wrong: stores the caller's reference directly.
    }

    public List<String> getItems() {
        return items; // Wrong: returns the internal mutable list directly.
    }
}
```

```java
List<String> original = new ArrayList<>();
original.add("Widget");

NaiveImmutableOrder order = new NaiveImmutableOrder(original);

// Modifying the original list modifies the order — even though the order looks immutable.
original.add("Gadget");
System.out.println(order.getItems().size()); // Output: 2 — the order was changed!

// External code can also modify the list through the getter.
order.getItems().add("Doohickey");
System.out.println(order.getItems().size()); // Output: 3 — still mutable!
```

**Fix: defensive copy on both input and output.**

```java
import java.util.ArrayList;
import java.util.List;

public final class ImmutableOrder {
    private final List<String> items;

    public ImmutableOrder(List<String> items) {
        // Defensive copy on input: create our own list from the caller's list.
        // Modifying the caller's list after this point has no effect on our order.
        this.items = new ArrayList<>(items);
    }

    public List<String> getItems() {
        // Defensive copy on output: return a copy, not the internal list.
        // Callers can modify the returned copy without affecting our order.
        return new ArrayList<>(items);
    }
}
```

```java
List<String> original = new ArrayList<>();
original.add("Widget");

ImmutableOrder order = new ImmutableOrder(original);

original.add("Gadget"); // Modifying the caller's list has no effect on order.
System.out.println(order.getItems().size()); // Output: 1 — correctly immutable

order.getItems().add("Doohickey"); // Modifying the returned copy has no effect.
System.out.println(order.getItems().size()); // Output: 1 — still protected
```

---

## 5. Collections.unmodifiableList() and List.copyOf()

Two convenient factory methods reduce defensive copy boilerplate.

**`List.copyOf(collection)`** — creates an unmodifiable shallow copy of the collection. Available since Java 10.

**`Collections.unmodifiableList(list)`** — returns an unmodifiable view of the given list. The view wraps the original; if the original is modified, the view reflects the change. This is a view, not a copy — use `List.copyOf()` when you need a true independent copy.

```java
import java.util.List;
import java.util.Collections;

public final class ImmutableOrderV2 {
    private final List<String> items;

    public ImmutableOrderV2(List<String> items) {
        // List.copyOf creates an unmodifiable copy — both copying and locking in one step.
        this.items = List.copyOf(items);
    }

    public List<String> getItems() {
        // The field is already unmodifiable — safe to return directly.
        return items;
    }
}
```

```java
List<String> mutable = new ArrayList<>(List.of("Widget", "Gadget"));
ImmutableOrderV2 order = new ImmutableOrderV2(mutable);

mutable.add("Doohickey");
System.out.println(order.getItems().size()); // Output: 2 — copy was made at construction

try {
    order.getItems().add("new item"); // Throws UnsupportedOperationException.
} catch (UnsupportedOperationException e) {
    System.out.println("Cannot modify: " + e.getClass().getSimpleName());
    // Output: Cannot modify: UnsupportedOperationException
}
```

---

## 6. Records as the Easiest Path to Immutability

Records (Java 16+) are immutable by design:
- All fields are `private` and `final` automatically.
- No setters are generated.
- The class is implicitly `final`.
- `equals()`, `hashCode()`, and `toString()` are auto-generated.

For simple value objects, a record replaces all the boilerplate of a hand-crafted immutable class.

```java
record Money(int amount, String currency) {}

Money price = new Money(999, "USD");
System.out.println(price);         // Output: Money[amount=999, currency=USD]
System.out.println(price.amount()); // Output: 999
```

For records that contain mutable fields (like a `List`), you still need a compact constructor to make a defensive copy:

```java
import java.util.List;

record ImmutableOrderRecord(String customer, List<String> items) {
    // Compact constructor: validates and defensively copies.
    ImmutableOrderRecord {
        if (customer == null || customer.isBlank()) {
            throw new IllegalArgumentException("Customer name is required.");
        }
        items = List.copyOf(items); // Replace the parameter with an unmodifiable copy.
    }
}
```

---

## 7. String is Immutable

`String` is the most-used immutable class in Java. Every operation that appears to "modify" a `String` actually creates a new `String` object.

```java
String s = "hello";
System.out.println(System.identityHashCode(s)); // Output: (some address, e.g., 366712642)

// toUpperCase() creates a NEW String; s is unchanged.
String upper = s.toUpperCase();
System.out.println(System.identityHashCode(upper)); // Output: (a different address)
System.out.println(s);     // Output: hello   — original is unchanged
System.out.println(upper); // Output: HELLO   — new String object

// String concatenation with + creates a new String.
String greeting = "Hello" + " " + "World";
// Each + creates an intermediate String. For building strings in a loop, use StringBuilder.
```

This is why string concatenation in a loop is O(n²): each iteration creates a new `String` that copies all the previous characters. Use `StringBuilder` instead.

---

## 8. Complete Example: ImmutableShoppingCart

```java
import java.util.ArrayList;
import java.util.List;

public final class ImmutableShoppingCart {
    private final String customer;
    private final List<String> items;

    public ImmutableShoppingCart(String customer, List<String> items) {
        if (customer == null || customer.isBlank()) {
            throw new IllegalArgumentException("Customer name must not be blank.");
        }
        this.customer = customer;
        // Defensive copy: we own our own list — changes to the caller's list do not affect us.
        this.items = List.copyOf(items);
    }

    public String getCustomer() {
        return customer; // String is immutable — safe to return directly.
    }

    public List<String> getItems() {
        // The list was stored as an unmodifiable copy — safe to return directly.
        return items;
    }

    public int getItemCount() {
        return items.size();
    }

    /**
     * Returns a new ImmutableShoppingCart with the given item added.
     * This is the immutable pattern: instead of modifying this cart,
     * create and return a new one with the updated state.
     */
    public ImmutableShoppingCart withItem(String newItem) {
        List<String> newItems = new ArrayList<>(items);
        newItems.add(newItem);
        return new ImmutableShoppingCart(customer, newItems);
    }

    @Override
    public String toString() {
        return "ShoppingCart{customer='" + customer + "', items=" + items + "}";
    }
}
```

```java
public class ShoppingCartDemo {
    public static void main(String[] args) {

        List<String> initialItems = new ArrayList<>();
        initialItems.add("Widget");

        ImmutableShoppingCart cart1 = new ImmutableShoppingCart("Alice", initialItems);
        System.out.println(cart1);
        // Output: ShoppingCart{customer='Alice', items=[Widget]}

        // Modifying the original list has no effect on cart1.
        initialItems.add("Gadget");
        System.out.println(cart1.getItemCount()); // Output: 1 — unchanged

        // Adding an item creates a new cart rather than modifying the existing one.
        ImmutableShoppingCart cart2 = cart1.withItem("Gadget");
        ImmutableShoppingCart cart3 = cart2.withItem("Doohickey");

        System.out.println(cart1); // Output: ShoppingCart{customer='Alice', items=[Widget]}
        System.out.println(cart2); // Output: ShoppingCart{customer='Alice', items=[Widget, Gadget]}
        System.out.println(cart3); // Output: ShoppingCart{customer='Alice', items=[Widget, Gadget, Doohickey]}

        // Attempt to modify the returned list — this throws UnsupportedOperationException.
        try {
            cart1.getItems().add("Unauthorized");
        } catch (UnsupportedOperationException e) {
            System.out.println("Cannot modify the cart externally: " + e.getClass().getSimpleName());
            // Output: Cannot modify the cart externally: UnsupportedOperationException
        }
    }
}
```

---

## 9. Common Mistakes

### Declaring a field final does not make the referenced object immutable
`final` on a field prevents the field from being reassigned to a different object. It does not prevent methods of the referenced object from mutating it.

```java
final List<String> items = new ArrayList<>();
items = new ArrayList<>(); // Compile error: cannot assign a value to a final variable
items.add("Widget");       // Compiles and works — the list itself is mutable
```

### Returning a direct reference to a mutable internal field
A getter that returns the internal `List` directly allows callers to modify the list, breaking immutability even if all other rules are followed.

```java
// Wrong: external code can modify the internal state via the returned reference.
public List<String> getItems() {
    return items; // Returns the actual internal list
}

// Correct: return a copy.
public List<String> getItems() {
    return new ArrayList<>(items);
}
// Or: if the field was stored with List.copyOf(), the unmodifiable list is safe to return directly.
```

### Forgetting to declare the class final
Without `final`, a subclass can override a method and store mutable state, defeating the immutability guarantee at the subclass level.

---

## 10. Key Takeaways

- An immutable object's state is set at construction and never changes — enforce this with `final` class, `final` fields, no setters, and defensive copying
- `final` on a field prevents reassignment of the reference but does not prevent mutation of the referenced object
- Defensive copy on input (in the constructor) and on output (in getters) protects mutable fields from external modification
- `List.copyOf()` creates an unmodifiable copy in one step and is the cleanest way to store a list defensively
- Records are immutable by design and handle all boilerplate automatically — prefer them for data classes
- `String` is the canonical example of an immutable class; "modifying" a `String` always creates a new object
- Immutable objects are inherently thread-safe, safe as map keys, and easier to reason about

---

Immutability is not a constraint — it is a design choice that makes code predictably safe. The Streams API, Optional, and `java.time` are all built around immutable value types, which is why they are so pleasant to work with.
