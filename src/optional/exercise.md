# Optional Exercises

These exercises reinforce the concepts covered in `optional.md`. Work through each set in order. Each set builds on the previous one.

Solutions for these exercises are in the `solutions/` subfolder.

---

## Exercise Set 1: Creating and Checking Optionals

Practice the three factory methods and the presence-checking API.

**1a.** Create three Optionals using each of the three factory methods:
- `Optional.of("hello")`
- `Optional.ofNullable(null)`
- `Optional.empty()`

Print each one and observe the output format.

**1b.** For each Optional from 1a, call both `isPresent()` and `isEmpty()`. Print the results and verify that they are always logical complements of each other.

**1c.** Given the Optional `Optional.of("Java 21")`, use `ifPresent` to print the value only if it is present. Then repeat with `Optional.empty()` and confirm that nothing is printed.

**1d.** Repeat 1c using `ifPresentOrElse`. For the empty case, print `"No value available"` from the else branch.

---

## Exercise Set 2: Retrieving Values

Practice all four retrieval approaches and understand when each is appropriate.

**2a.** Create `Optional<String> name = Optional.of("Alice")`. Retrieve the value using `get()` inside an `isPresent()` guard. Print the result. Then create `Optional.empty()` and call `get()` on it without a guard — catch the `NoSuchElementException` and print its class name. Explain in a comment why `get()` without a guard is unsafe.

**2b.** Using `Optional.empty()`, demonstrate `orElse("Guest")`. Then using `Optional.of("Alice")`, call `orElse("Guest")` again. Print both results and note that the default string is always evaluated regardless of whether the Optional is present.

**2c.** Add a helper method `computeDefault()` that prints `"computing default..."` and returns `"DefaultValue"`. Call it via `orElse` on a present Optional. Call it again via `orElseGet` on a present Optional. Observe which one actually executes the method body. Explain the lazy evaluation difference in a comment.

**2d.** Using `Optional.empty()`, call `orElseThrow()` and catch the resulting exception. Then call `orElseThrow(() -> new IllegalStateException("Value is required"))` and catch that one instead. Print the exception class name and message in each case.

---

## Exercise Set 3: Transforming with map, flatMap, and filter

Practice functional transformations without manually unwrapping the Optional.

**3a.** Create `Optional<String> name = Optional.of("alice")`. Use `map` to transform it to uppercase. Print the result. Then repeat with `Optional.empty()` and confirm that `map` does not throw — it simply returns an empty Optional.

**3b.** Using the same `name` Optional, chain `map` to get the string's length as an `Optional<Integer>`. Print the result. Observe that `map` can change the type inside the Optional.

**3c.** Write a method `Optional<String> toUpperIfLong(String s)` that returns `Optional.of(s.toUpperCase())` if the string's length is greater than 5, or `Optional.empty()` otherwise. Then create `Optional.of("hi")` and `Optional.of("greetings")` and use `flatMap` with that method on each. Print the results and explain in a comment why `flatMap` is needed instead of `map` here.

**3d.** Create `Optional<Integer> score = Optional.of(72)`. Use `filter` to keep the value only if it is greater than or equal to 60. Print the result. Then repeat with a score of 45. Finally, use a chain of `filter` and `map`: keep scores that pass, then map them to `"Pass: " + score`.

---

## Exercise Set 4: Real-World Patterns

Practice the patterns you will encounter most in production code.

**4a.** Write a method `Optional<String> findUserById(int id)` backed by a `Map<Integer, String>`. Populate it with a few entries. Return `Optional.ofNullable(map.get(id))`. In `main`, look up an existing ID and a missing ID. On the result, chain `map` to uppercase the name and `orElse("UNKNOWN")` to get a plain String. Print both results.

**4b.** Extend the method from 4a. Given a found user, simulate fetching their department by writing a second method `Optional<String> findDepartment(String username)`. Chain the two lookups using `flatMap` so that the result is `Optional<String>` (not `Optional<Optional<String>>`). Use `orElse("No department")` at the end. Print the result for a user who has a department and for one who does not.

**4c.** Create a `List<Optional<String>>` containing a mix of present and empty Optionals. Use `stream()` on the list, then `flatMap(Optional::stream)` to extract only the present values into a plain `List<String>`. Print the resulting list. Explain in a comment what `Optional::stream` does for each element.

**4d.** You have a method that currently returns `null` when no result is found. Rewrite it to return `Optional<String>` instead. In the caller, replace the null check `if (result != null)` with `ifPresentOrElse`. Contrast the before and after in comments.

---

## Common Mistakes

**Calling `get()` without checking first.** This trades a `NullPointerException` for a `NoSuchElementException`. Always guard with `isPresent()` or, better, use `orElse`/`orElseGet`/`orElseThrow` which handle the empty case declaratively.

```java
// Wrong — throws NoSuchElementException if empty
String value = optional.get();

// Right — handle the empty case explicitly
String value = optional.orElse("default");
```

**Using Optional as a class field.** `Optional` does not implement `Serializable`. Any class that stores an `Optional` field will break serialization frameworks. Store the raw value as a nullable field and wrap it in `Optional` only at the return site.

```java
// Wrong
private Optional<String> bio;

// Right
private String bio; // nullable
public Optional<String> getBio() { return Optional.ofNullable(bio); }
```

**Using `Optional` for collections.** An empty collection already signals the absence of results. Wrapping it in `Optional` forces the caller to double-unwrap and adds no information. Return an empty `List` or `Set` directly.

```java
// Wrong
public Optional<List<Order>> getOrders(int userId) { ... }

// Right
public List<Order> getOrders(int userId) {
    List<Order> orders = repository.query(userId);
    return orders != null ? orders : Collections.emptyList();
}
```

---

Solutions for these exercises are in the `solutions/` subfolder.
