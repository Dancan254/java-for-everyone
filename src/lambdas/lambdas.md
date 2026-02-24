# Lambdas

A lambda expression is a concise way to represent an anonymous function — a block of behavior that can be stored in a variable, passed to a method, or returned from a method. Lambdas did not replace anything in Java; they gave you a shorter syntax for something the language already supported through anonymous classes. Understanding that connection is the key to understanding lambdas completely.

```
Think of it like this:
  You need to tell a sorting method HOW to compare two strings.
  Old way: write an entire anonymous class with a single method — five lines of ceremony for one line of logic.
  Lambda way: (a, b) -> a.compareTo(b)
  Same instruction, fraction of the noise.
```

## 1. The Problem Lambdas Solve

Before lambdas, passing behavior to a method required an anonymous class. The syntax was verbose for something conceptually simple.

```java
// Passing behavior with an anonymous class — Runnable
Runnable printHello = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello from a thread!");
    }
};

// Passing behavior with an anonymous class — Comparator<String>
Comparator<String> byLength = new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return Integer.compare(a.length(), b.length());
    }
};
```

Five lines of boilerplate to express one line of logic. The anonymous class syntax forces you to name the interface, override the method, open and close braces — all before reaching the actual behavior. Lambdas strip all of that away.

```java
// The same two expressions as lambdas
Runnable printHello = () -> System.out.println("Hello from a thread!");

Comparator<String> byLength = (a, b) -> Integer.compare(a.length(), b.length());
```

The behavior is identical. The lambda is not a different mechanism — it compiles to the same thing. It is simply a shorter way to write it.

## 2. Functional Interfaces

A **functional interface** is any interface that has exactly one abstract method. That single method is what the lambda implements. The `@FunctionalInterface` annotation asks the compiler to enforce this constraint and will produce an error if you accidentally add a second abstract method.

```java
@FunctionalInterface
public interface Transformer<T> {
    T transform(T input);
}
```

`Transformer<T>` has one abstract method: `transform`. You can implement it three ways, all of which produce an object of type `Transformer<String>`.

**Anonymous class:**
```java
Transformer<String> shout = new Transformer<String>() {
    @Override
    public String transform(String input) {
        return input.toUpperCase();
    }
};
```

**Lambda:**
```java
Transformer<String> shout = input -> input.toUpperCase();
```

**Method reference:**
```java
Transformer<String> shout = String::toUpperCase;
```

All three produce a `Transformer<String>` object. A lambda is not something new — it is an instance of a functional interface written in a shorter form. When you write `input -> input.toUpperCase()`, Java creates an object that implements `Transformer` and whose `transform` method executes that body.

```java
System.out.println(shout.transform("hello"));
// Output: HELLO
```

## 3. Lambda Syntax

Lambda syntax scales from a single expression to a full method body. Every variation is shown below.

**No parameters:**
```java
Runnable greet = () -> System.out.println("Hello");
greet.run();
// Output: Hello
```

**One parameter — parentheses are optional:**
```java
Transformer<String> upper = name -> name.toUpperCase();
System.out.println(upper.transform("alice"));
// Output: ALICE
```

**One parameter — with parentheses:**
```java
Transformer<String> upper = (name) -> name.toUpperCase();
```

**Two parameters:**
```java
// Using a standard functional interface from java.util.function
java.util.function.BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
System.out.println(add.apply(3, 4));
// Output: 7
```

**Multi-line body — requires braces and an explicit return:**
```java
java.util.function.BiFunction<Integer, Integer, Integer> add = (a, b) -> {
    int sum = a + b;
    return sum;
};
System.out.println(add.apply(10, 20));
// Output: 30
```

**Explicit parameter types:**
```java
java.util.function.BiFunction<String, Integer, String> repeat = (String s, int n) -> s.repeat(n);
System.out.println(repeat.apply("ab", 3));
// Output: ababab
```

The rule: if the body is a single expression, omit the braces and `return`. If the body is multiple statements, use braces and `return` explicitly. Parameter types are always optional because the compiler infers them from the functional interface.

## 4. Built-in Functional Interfaces (java.util.function)

Java ships a set of general-purpose functional interfaces in `java.util.function`. You will use these constantly. Learning them means you rarely need to define your own.

| Interface            | Signature          | What it does                    |
|----------------------|--------------------|---------------------------------|
| `Predicate<T>`       | `T -> boolean`     | Tests a condition               |
| `Function<T, R>`     | `T -> R`           | Transforms a value              |
| `Consumer<T>`        | `T -> void`        | Consumes a value, no return     |
| `Supplier<T>`        | `() -> T`          | Produces a value, no input      |
| `BiFunction<T,U,R>`  | `(T, U) -> R`      | Transforms two values into one  |
| `BiPredicate<T,U>`   | `(T, U) -> boolean`| Tests two values                |
| `UnaryOperator<T>`   | `T -> T`           | Transforms a value of same type |
| `BinaryOperator<T>`  | `(T, T) -> T`      | Combines two values of same type|

**Predicate<T> — T -> boolean**

Tests whether a value satisfies a condition. Used for filtering.

```java
import java.util.function.Predicate;

Predicate<Integer> isEven = n -> n % 2 == 0;
System.out.println(isEven.test(4));   // Output: true
System.out.println(isEven.test(7));   // Output: false

Predicate<String> isNonBlank = s -> !s.isBlank();
System.out.println(isNonBlank.test("hello"));  // Output: true
System.out.println(isNonBlank.test("   "));    // Output: false
```

**Function<T, R> — T -> R**

Transforms a value of type T into a value of type R.

```java
import java.util.function.Function;

Function<String, Integer> getLength = s -> s.length();
System.out.println(getLength.apply("lambda"));  // Output: 6

Function<String, String> toUpperCase = s -> s.toUpperCase();
System.out.println(toUpperCase.apply("hello")); // Output: HELLO
```

**Consumer<T> — T -> void**

Consumes a value and produces no result. Used for side effects like printing or writing.

```java
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;

Consumer<String> print = s -> System.out.println(s);
print.accept("hello");  // Output: hello

List<String> collected = new ArrayList<>();
Consumer<String> saveToList = s -> collected.add(s);
saveToList.accept("alpha");
saveToList.accept("beta");
System.out.println(collected); // Output: [alpha, beta]
```

**Supplier<T> — () -> T**

Takes no input and produces a value. Used for lazy evaluation or default values.

```java
import java.util.function.Supplier;
import java.util.Random;

Supplier<String> defaultName = () -> "Unknown";
System.out.println(defaultName.get()); // Output: Unknown

Supplier<Integer> randomInt = () -> new Random().nextInt(100);
System.out.println(randomInt.get()); // Output: (a random number 0–99)
```

**BiFunction<T, U, R> — (T, U) -> R**

Takes two inputs of potentially different types and produces a result.

```java
import java.util.function.BiFunction;

BiFunction<String, Integer, String> repeat = (s, n) -> s.repeat(n);
System.out.println(repeat.apply("ha", 3)); // Output: hahaha
```

**BiPredicate<T, U> — (T, U) -> boolean**

Tests a condition over two inputs.

```java
import java.util.function.BiPredicate;

BiPredicate<String, String> startsWith = (s, prefix) -> s.startsWith(prefix);
System.out.println(startsWith.test("lambda", "lam")); // Output: true
System.out.println(startsWith.test("lambda", "xyz")); // Output: false
```

**UnaryOperator<T> — T -> T**

A specialization of `Function` where the input and output type are the same.

```java
import java.util.function.UnaryOperator;

UnaryOperator<Integer> doubleIt = n -> n * 2;
System.out.println(doubleIt.apply(5)); // Output: 10

UnaryOperator<String> reverse = s -> new StringBuilder(s).reverse().toString();
System.out.println(reverse.apply("hello")); // Output: olleh
```

**BinaryOperator<T> — (T, T) -> T**

A specialization of `BiFunction` where both inputs and the output are the same type.

```java
import java.util.function.BinaryOperator;

BinaryOperator<Integer> add = (a, b) -> a + b;
System.out.println(add.apply(6, 4)); // Output: 10

BinaryOperator<String> concat = (a, b) -> a + b;
System.out.println(concat.apply("Hello, ", "World")); // Output: Hello, World
```

## 5. Method References

A method reference is a shorthand for a lambda that does nothing but call an existing method. The syntax `ClassName::methodName` or `object::methodName` replaces a lambda whose entire body is a single method call. There are four forms.

**Static method reference — `ClassName::staticMethod`**

Use when the lambda just calls a static method, passing through its argument.

```java
import java.util.function.Function;

// Lambda version
Function<String, Integer> parse = s -> Integer.parseInt(s);

// Method reference version
Function<String, Integer> parse = Integer::parseInt;

System.out.println(parse.apply("42")); // Output: 42

// Another example
Function<Integer, Integer> abs = n -> Math.abs(n);
Function<Integer, Integer> abs = Math::abs;
System.out.println(abs.apply(-7)); // Output: 7
```

**Bound instance method reference — `object::instanceMethod`**

Use when the lambda calls a method on a specific, already-known instance.

```java
import java.util.function.Consumer;

// Lambda version
Consumer<String> print = s -> System.out.println(s);

// Method reference version
Consumer<String> print = System.out::println;

print.accept("bound reference"); // Output: bound reference

// Another example
String prefix = "Hello, ";
Function<String, String> greet = name -> prefix.concat(name);
Function<String, String> greet = prefix::concat;
System.out.println(greet.apply("Alice")); // Output: Hello, Alice
```

**Unbound instance method reference — `ClassName::instanceMethod`**

Use when the lambda takes an instance as its first argument and calls a method on it. The instance is not fixed ahead of time.

```java
import java.util.function.Function;

// Lambda version
Function<String, String> upper = s -> s.toUpperCase();

// Method reference version
Function<String, String> upper = String::toUpperCase;

System.out.println(upper.apply("hello")); // Output: HELLO

Function<String, Integer> length = s -> s.length();
Function<String, Integer> length = String::length;
System.out.println(length.apply("lambda")); // Output: 6
```

**Constructor reference — `ClassName::new`**

Use when the lambda creates a new instance.

```java
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.ArrayList;

// Lambda version
Supplier<ArrayList<String>> makeList = () -> new ArrayList<>();

// Method reference version
Supplier<ArrayList<String>> makeList = ArrayList::new;

ArrayList<String> list = makeList.get();
list.add("test");
System.out.println(list); // Output: [test]

// Constructor reference with an argument
Function<String, StringBuilder> makeBuilder = s -> new StringBuilder(s);
Function<String, StringBuilder> makeBuilder = StringBuilder::new;
System.out.println(makeBuilder.apply("hello")); // Output: hello
```

The decision rule: if a lambda body is exactly `args -> SomeMethod(args)`, replace it with a method reference. If the lambda has any additional logic, keep it as a lambda.

## 6. Variable Capture

Lambdas can read variables from the enclosing scope. This is called **capturing** a variable.

```java
String greeting = "Hello";
Consumer<String> greet = name -> System.out.println(greeting + ", " + name + "!");

greet.accept("Alice"); // Output: Hello, Alice!
greet.accept("Bob");   // Output: Hello, Bob!
```

The lambda captured `greeting` from the surrounding scope. The rule is that captured local variables must be **effectively final** — they do not need the `final` keyword explicitly, but they must never be reassigned after the point they are captured. The compiler enforces this.

```java
String greeting = "Hello";
greeting = "Hi"; // This reassignment makes greeting NOT effectively final

// Compiler error on the line below:
// Variable used in lambda expression should be final or effectively final
Consumer<String> greet = name -> System.out.println(greeting + ", " + name);
```

This restriction exists because lambdas may execute later or in a different thread, and allowing the variable to change would create unpredictable behavior.

**Instance fields and static fields have no such restriction.** They can be freely read and written inside a lambda because they live on the heap, not on the stack.

```java
public class Counter {
    private int count = 0; // instance field — no restriction

    public void run() {
        Runnable increment = () -> count++; // reading and writing instance field is fine
        increment.run();
        increment.run();
        System.out.println(count); // Output: 2
    }
}
```

## 7. Chaining Functional Interfaces

Predicate and Function both provide default methods for composing multiple operations into a pipeline.

**Predicate chaining — `and()`, `or()`, `negate()`**

```java
import java.util.function.Predicate;

Predicate<String> isNonNull  = s -> s != null;
Predicate<String> isNonBlank = s -> !s.isBlank();
Predicate<String> isLongEnough = s -> s.length() >= 8;

// All three conditions must pass
Predicate<String> isValidPassword = isNonNull.and(isNonBlank).and(isLongEnough);

System.out.println(isValidPassword.test("secret99"));  // Output: true
System.out.println(isValidPassword.test("short"));     // Output: false
System.out.println(isValidPassword.test(""));          // Output: false

// or() — either condition is enough
Predicate<Integer> isNegative = n -> n < 0;
Predicate<Integer> isZero = n -> n == 0;
Predicate<Integer> isNonPositive = isNegative.or(isZero);

System.out.println(isNonPositive.test(-3)); // Output: true
System.out.println(isNonPositive.test(0));  // Output: true
System.out.println(isNonPositive.test(5));  // Output: false

// negate() — flip the result
Predicate<String> isBlank = String::isBlank;
Predicate<String> isNotBlank = isBlank.negate();

System.out.println(isNotBlank.test("hello")); // Output: true
System.out.println(isNotBlank.test("   "));   // Output: false
```

**Function chaining — `andThen()`, `compose()`**

`andThen(f)` applies `f` after the current function. `compose(f)` applies `f` before the current function.

```java
import java.util.function.Function;

Function<String, Integer> parseToInt = Integer::parseInt;
Function<Integer, Integer> doubleIt  = n -> n * 2;
Function<Integer, String>  toString  = n -> "Result: " + n;

// Pipeline: parse -> double -> label
Function<String, String> pipeline = parseToInt.andThen(doubleIt).andThen(toString);

System.out.println(pipeline.apply("21")); // Output: Result: 42

// compose() applies the argument BEFORE the receiver
Function<Integer, Integer> addOne   = n -> n + 1;
Function<Integer, Integer> triple   = n -> n * 3;

// triple.compose(addOne) means: first addOne, then triple
Function<Integer, Integer> addThenTriple = triple.compose(addOne);
System.out.println(addThenTriple.apply(4)); // Output: 15  (4+1=5, 5*3=15)

// triple.andThen(addOne) means: first triple, then addOne
Function<Integer, Integer> tripleThenAdd = triple.andThen(addOne);
System.out.println(tripleThenAdd.apply(4)); // Output: 13  (4*3=12, 12+1=13)
```

## 8. Lambdas with Collections

This is where lambdas pay off immediately. The standard collection methods accept functional interfaces directly.

**`List.sort()` with a Comparator lambda**

```java
import java.util.ArrayList;
import java.util.List;

List<String> names = new ArrayList<>(List.of("Charlie", "Alice", "Bob", "Diana"));

// Sort alphabetically
names.sort((a, b) -> a.compareTo(b));
System.out.println(names); // Output: [Alice, Bob, Charlie, Diana]

// Sort by length, then alphabetically for ties
names.sort((a, b) -> {
    int byLength = Integer.compare(a.length(), b.length());
    return byLength != 0 ? byLength : a.compareTo(b);
});
System.out.println(names); // Output: [Bob, Alice, Diana, Charlie]
```

**`List.removeIf()` with a Predicate lambda**

```java
List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8));

numbers.removeIf(n -> n % 2 == 0);  // Remove all even numbers
System.out.println(numbers); // Output: [1, 3, 5, 7]
```

**`List.forEach()` with a Consumer lambda**

```java
List<String> items = List.of("apple", "banana", "cherry");

items.forEach(item -> System.out.println(item.toUpperCase()));
// Output:
// APPLE
// BANANA
// CHERRY
```

**`Map.forEach()` with a BiConsumer lambda**

```java
import java.util.Map;

Map<String, Integer> scores = Map.of("Alice", 95, "Bob", 82, "Carol", 78);

scores.forEach((name, score) -> System.out.println(name + " scored " + score));
// Output (order may vary):
// Alice scored 95
// Bob scored 82
// Carol scored 78
```

**`Map.computeIfAbsent()` with a Function lambda**

```java
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

Map<String, List<String>> groups = new HashMap<>();

// If the key is absent, the Function creates a new list for it
groups.computeIfAbsent("fruits", key -> new ArrayList<>()).add("apple");
groups.computeIfAbsent("fruits", key -> new ArrayList<>()).add("banana");
groups.computeIfAbsent("veggies", key -> new ArrayList<>()).add("carrot");

System.out.println(groups);
// Output: {fruits=[apple, banana], veggies=[carrot]}
```

## 9. Complete Example

An employee filtering and reporting system built entirely with lambdas and functional interfaces — no Streams.

```java
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String name;
    private String department;
    private double salary;
    private int yearsExperience;

    public Employee(String name, String department, double salary, int yearsExperience) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.yearsExperience = yearsExperience;
    }

    public String getName()         { return name; }
    public String getDepartment()   { return department; }
    public double getSalary()       { return salary; }
    public int getYearsExperience() { return yearsExperience; }

    @Override
    public String toString() {
        return name + " [" + department + ", $" + salary + ", " + yearsExperience + " yrs]";
    }
}
```

```java
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeReport {

    public static List<Employee> filterEmployees(List<Employee> list, Predicate<Employee> filter) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : list) {
            if (filter.test(e)) {
                result.add(e);
            }
        }
        return result;
    }

    public static List<String> transformEmployees(List<Employee> list, Function<Employee, String> mapper) {
        List<String> result = new ArrayList<>();
        for (Employee e : list) {
            result.add(mapper.apply(e));
        }
        return result;
    }

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>(List.of(
            new Employee("Alice",   "Engineering", 95000, 7),
            new Employee("Bob",     "Marketing",   62000, 3),
            new Employee("Carol",   "Engineering", 80000, 5),
            new Employee("David",   "HR",          58000, 2),
            new Employee("Eve",     "Engineering", 72000, 4),
            new Employee("Frank",   "Marketing",   74000, 6)
        ));

        // Find all employees in Engineering
        Predicate<Employee> inEngineering = e -> e.getDepartment().equals("Engineering");
        List<Employee> engineers = filterEmployees(employees, inEngineering);
        System.out.println("Engineering team:");
        engineers.forEach(e -> System.out.println("  " + e));
        // Output:
        // Engineering team:
        //   Alice [Engineering, $95000.0, 7 yrs]
        //   Carol [Engineering, $80000.0, 5 yrs]
        //   Eve [Engineering, $72000.0, 4 yrs]

        // Find employees earning over 70000
        Predicate<Employee> highEarner = e -> e.getSalary() > 70000;
        List<Employee> highEarners = filterEmployees(employees, highEarner);
        System.out.println("\nHigh earners (salary > 70000):");
        highEarners.forEach(e -> System.out.println("  " + e));
        // Output:
        // High earners (salary > 70000):
        //   Alice [Engineering, $95000.0, 7 yrs]
        //   Carol [Engineering, $80000.0, 5 yrs]
        //   Eve [Engineering, $72000.0, 4 yrs]
        //   Frank [Marketing, $74000.0, 6 yrs]

        // Combine predicates: Engineering AND high earner
        List<Employee> seniorEngineers = filterEmployees(employees, inEngineering.and(highEarner));
        System.out.println("\nSenior Engineers (Engineering + salary > 70000):");
        seniorEngineers.forEach(e -> System.out.println("  " + e));
        // Output:
        // Senior Engineers (Engineering + salary > 70000):
        //   Alice [Engineering, $95000.0, 7 yrs]
        //   Carol [Engineering, $80000.0, 5 yrs]
        //   Eve [Engineering, $72000.0, 4 yrs]

        // Get a list of all names
        Function<Employee, String> toName = e -> e.getName();
        List<String> names = transformEmployees(employees, toName);
        System.out.println("\nAll employee names: " + names);
        // Output:
        // All employee names: [Alice, Bob, Carol, David, Eve, Frank]

        // Get formatted summary strings
        Function<Employee, String> toSummary = e ->
            e.getName() + " earns $" + e.getSalary() + " with " + e.getYearsExperience() + " years of experience";
        List<String> summaries = transformEmployees(employees, toSummary);
        System.out.println("\nEmployee summaries:");
        summaries.forEach(s -> System.out.println("  " + s));
        // Output:
        // Employee summaries:
        //   Alice earns $95000.0 with 7 years of experience
        //   Bob earns $62000.0 with 3 years of experience
        //   Carol earns $80000.0 with 5 years of experience
        //   David earns $58000.0 with 2 years of experience
        //   Eve earns $72000.0 with 4 years of experience
        //   Frank earns $74000.0 with 6 years of experience

        // Print all employees using forEach with a Consumer lambda
        System.out.println("\nFull employee roster:");
        employees.forEach(e -> System.out.println("  " + e));
        // Output:
        // Full employee roster:
        //   Alice [Engineering, $95000.0, 7 yrs]
        //   Bob [Marketing, $62000.0, 3 yrs]
        //   Carol [Engineering, $80000.0, 5 yrs]
        //   David [HR, $58000.0, 2 yrs]
        //   Eve [Engineering, $72000.0, 4 yrs]
        //   Frank [Marketing, $74000.0, 6 yrs]
    }
}
```

## 10. Key Takeaways

- A lambda is an instance of a functional interface — it is not a new concept, it is a shorter syntax for an anonymous class with one method
- A functional interface has exactly one abstract method; `@FunctionalInterface` makes the compiler enforce this
- Lambda syntax scales from `() -> expression` up to `(Type a, Type b) -> { multiple; statements; return value; }`
- The eight interfaces in `java.util.function` — `Predicate`, `Function`, `Consumer`, `Supplier`, `BiFunction`, `BiPredicate`, `UnaryOperator`, `BinaryOperator` — cover the vast majority of use cases
- Method references (`Class::method`, `object::method`, `Class::new`) are shorthand for lambdas whose entire body is a single method call
- Captured local variables must be effectively final; instance fields and static fields have no such restriction
- `Predicate.and()`, `or()`, `negate()` and `Function.andThen()`, `compose()` let you build readable pipelines without nesting
- `List.sort()`, `List.removeIf()`, `List.forEach()`, `Map.forEach()`, and `Map.computeIfAbsent()` all accept functional interfaces directly — lambdas slot in immediately

---

Lambdas are the foundation that makes Streams, the Optional API, and Spring's functional-style configurations readable — once you are fluent with lambdas and the `java.util.function` interfaces, those higher-level APIs become a natural extension of the same ideas.
