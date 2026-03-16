# The var Keyword

`var` is a reserved type name introduced in Java 10 that lets the compiler infer the type of a local variable from the expression on the right-hand side of the assignment. It is not dynamic typing — Java is still statically typed — it is simply a way to avoid writing a type name that the compiler can already determine from context.

---

## 1. What var Is: Local Variable Type Inference

When you write `var x = someExpression`, the compiler examines `someExpression`, determines its type, and assigns that type to `x`. The type is fixed at compile time; `x` can never later hold a value of a different type.

```java
// With explicit type
String message = "Hello, Java 10!";

// With var — the compiler infers String from the right-hand side.
var greeting = "Hello, Java 10!";

// Both are identical at runtime. greeting is a String — always.
System.out.println(greeting.toUpperCase()); // Output: HELLO, JAVA 10!
```

The compiled bytecode is identical in both cases. `var` is purely a compile-time convenience that is erased before any code runs.

---

## 2. Where var Works and Where It Does NOT Work

`var` is restricted to **local variable declarations**. It cannot be used in any other position.

```java
// Works: local variable in a method body.
var count = 0;
var names = new java.util.ArrayList<String>();
var result = someMethod();

// Does NOT work: field declaration.
// var count = 0; // Compile error in a class body — var is not allowed for fields.

// Does NOT work: method parameter.
// public void process(var item) { } // Compile error

// Does NOT work: method return type.
// public var compute() { return 42; } // Compile error

// Does NOT work: lambda parameter with explicit inference
// (exception: var in lambda parameters was added in Java 11 for annotations — see below).
```

**Lambda parameters (Java 11):** `var` is allowed in lambda parameter lists when you need to apply an annotation to the parameter type, which is not possible with inferred types otherwise.

```java
// Useful only when you need an annotation on the parameter.
import java.util.function.Consumer;
Consumer<String> print = (@SuppressWarnings("unused") var s) -> System.out.println(s);
```

In practice, lambda parameters are almost always written without a type declaration at all.

---

## 3. When var Improves Readability

`var` is most valuable when the type name is long and the right-hand side already makes the type obvious.

**Long generic type names:**

```java
// Without var — the type is repeated on both sides; neither side adds information.
java.util.HashMap<String, java.util.List<Integer>> scoreMap = new java.util.HashMap<>();

// With var — no information is lost; the type is clear from the constructor call.
var scoreMap = new java.util.HashMap<String, java.util.List<Integer>>();
```

**Long right-hand side that already states the type:**

```java
// Without var — the return type of getBufferedReader() is stated twice.
java.io.BufferedReader reader = Files.newBufferedReader(Path.of("data.txt"));

// With var — the type is obvious from the method name; no repetition.
var reader = Files.newBufferedReader(Path.of("data.txt"));
```

**Iterator patterns and try-with-resources:**

```java
// Commonly seen in try-with-resources.
try (var reader = Files.newBufferedReader(path)) {
    var line = reader.readLine();
    // ...
}
```

---

## 4. When var Hurts Readability

`var` reduces readability when the type is not obvious from the right-hand side.

```java
// Bad: what type is result? The reader must look up getResult() to know.
var result = service.getResult();

// Bad: what type is x? -1, 0, or 1? Byte? Short? int? double?
var x = compute();

// Good: explicit type makes the intention clear.
Optional<User> result = service.getResult();
int x = compute();
```

**The readability test:** if a reader can immediately tell the type from the right-hand side without looking anything up, `var` is safe to use. If they cannot, use an explicit type.

---

## 5. var in For Loops and Enhanced For Loops

`var` works naturally in both traditional and enhanced for loops.

```java
import java.util.List;

List<String> cities = List.of("London", "Tokyo", "Cairo");

// Traditional for loop — i is inferred as int.
for (var i = 0; i < cities.size(); i++) {
    System.out.println(i + ": " + cities.get(i));
}

// Enhanced for loop — city is inferred as String.
for (var city : cities) {
    System.out.println(city.toUpperCase());
}
// Output:
// LONDON
// TOKYO
// CAIRO
```

The enhanced for loop benefit is modest since the type is usually short. The traditional for loop benefit is also modest since `int i` is already concise.

---

## 6. var with Anonymous Types (Advanced)

One case where `var` provides genuine power unavailable any other way: assigning an anonymous class instance to a `var` variable retains the anonymous type, allowing you to call methods defined on the anonymous class.

```java
// Without var, you can only access methods declared on the interface or class.
Runnable r = new Runnable() {
    public void run() { System.out.println("running"); }
    public void extra() { System.out.println("extra method"); }
};
// r.extra(); // Compile error — Runnable does not declare extra().

// With var, the anonymous type is retained and extra() is accessible.
var r2 = new Runnable() {
    public void run()   { System.out.println("running"); }
    public void extra() { System.out.println("extra method"); }
};
r2.run();   // Output: running
r2.extra(); // Output: extra method — accessible only through var!
```

This is a niche feature but demonstrates that `var` is not merely cosmetic.

---

## 7. var Is Not Dynamic Typing

`var` is a common source of confusion for developers coming from dynamically typed languages like Python or JavaScript. It is essential to understand the difference.

```java
var x = 42; // x is int — fixed at compile time.
x = "hello"; // Compile error: incompatible types — cannot assign String to int.
```

In a dynamically typed language, a variable can hold any type at any time. In Java with `var`, the type is inferred once at the point of declaration and then fixed permanently. The variable behaves exactly like an explicitly typed variable in every respect.

The compiler does not infer a broader type. `var x = 42` gives `x` the type `int`, not `Number` or `Object`.

---

## 8. Common Mistakes

### var x = null — does not compile
The compiler infers the type from the right-hand side. `null` has no type on its own, so the compiler cannot infer anything.

```java
var x = null; // Compile error: cannot infer type for local variable x
              //               (variable initializer is 'null')

// Correct: use an explicit type when initialising to null.
String x = null;
```

### var in fields — does not compile
`var` is only allowed inside method bodies (and constructors). Fields, method parameters, and return types require explicit types.

```java
public class Example {
    var count = 0; // Compile error: var is not allowed here (field declaration)

    public void process() {
        var count = 0; // Correct: local variable inside a method body
    }
}
```

### Overusing var when the type is not obvious
`var` should be used only when the type is clear from context. Using it everywhere reduces readability for anyone reading the code later.

```java
// Avoid: what is the type of config?
var config = loadConfig("app");
processConfig(config);

// Better: the type communicates important information.
ApplicationConfig config = loadConfig("app");
processConfig(config);
```

---

## 9. Key Takeaways

- `var` is local variable type inference — the compiler determines the type from the right-hand side expression
- `var` is not dynamic typing — the type is fixed at compile time and never changes; Java remains statically typed
- `var` only works for local variable declarations — not fields, method parameters, or return types
- Use `var` when the type is obvious from the right-hand side and writing it explicitly adds no information
- Avoid `var` when the type is not obvious — prefer explicit types to preserve readability
- `var x = null` is a compile error — the compiler cannot infer a type from `null`
- In enhanced for loops and try-with-resources, `var` is a reasonable but optional alternative to explicit types

---

`var` is a tool for reducing noise in code where the type is already obvious. Used judiciously, it makes code more concise without sacrificing clarity. Used carelessly, it makes code harder to understand. The guiding principle is: if a reader can immediately know the type without leaving the line, `var` is appropriate.
