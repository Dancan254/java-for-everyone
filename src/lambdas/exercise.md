# Lambda Expressions Practice Exercises

Lambda expressions are the mechanism Java uses to treat behavior as data. The exercises below progress from writing basic lambdas through composing functional pipelines, then applying them to real data structures. Work through each set in order before moving to the next — each set builds on the vocabulary introduced by the previous one.

---

## Exercise Set 1: Lambda Syntax

### Exercise 1.1: Runnable and Comparator Lambdas

Rewrite the following anonymous classes as single-line lambda expressions. Do not change any behavior.

```java
// Anonymous class version — rewrite both as lambdas.
Runnable printDate = new Runnable() {
    @Override
    public void run() {
        System.out.println("Today's date");
    }
};

Comparator<String> byLength = new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return Integer.compare(a.length(), b.length());
    }
};
```

After writing the lambdas, call `printDate.run()` and demonstrate `byLength` by sorting the list `["banana", "fig", "apple", "kiwi"]`.

**Expected output:**
```
Today's date
[fig, kiwi, apple, banana]
```

### Exercise 1.2: Multi-line Lambda Bodies

Write a `Comparator<String>` lambda that sorts strings first by length (shortest first), and when two strings have the same length, sorts them alphabetically. The body must use braces — it contains two statements.

Demonstrate it by sorting: `["pear", "kiwi", "fig", "date", "plum"]`.

**Expected output:**
```
[fig, date, kiwi, pear, plum]
```

### Exercise 1.3: Explicit Parameter Types and Custom Functional Interfaces

Define a functional interface named `Combiner<T>` with a single abstract method `T combine(T first, T second)`.

Write three lambdas that implement it:
1. A `Combiner<Integer>` that returns the larger of the two values.
2. A `Combiner<String>` that joins the two strings with a hyphen between them.
3. A `Combiner<String>` using explicit parameter type annotations (`(String a, String b) -> ...`) that returns whichever string comes first alphabetically.

Demonstrate each lambda by calling `combine` with two test values and printing the result.

**Expected output:**
```
max(8, 3) = 8
join(hello, world) = hello-world
first(banana, apple) = apple
```

---

## Exercise Set 2: Built-in Functional Interfaces

### Exercise 2.1: Predicate

Write the following `Predicate` instances and test each one:
- `isPositive` — true if an integer is greater than zero.
- `isPalindrome` — true if a string reads the same forwards and backwards (compare the string to its reverse using `StringBuilder`).
- `hasUpperCase` — true if a string contains at least one uppercase letter (use a loop or `chars()` with `Character.isUpperCase`).

Test values:
- `isPositive`: -5, 0, 12
- `isPalindrome`: "racecar", "level", "hello"
- `hasUpperCase`: "password", "Password", "PASSWORD"

**Expected output:**
```
isPositive(-5)    = false
isPositive(0)     = false
isPositive(12)    = true
isPalindrome(racecar) = true
isPalindrome(level)   = true
isPalindrome(hello)   = false
hasUpperCase(password) = false
hasUpperCase(Password) = true
hasUpperCase(PASSWORD) = true
```

### Exercise 2.2: Function and Consumer

Write the following:
- A `Function<String, String>` named `capitalize` that upper-cases the first character and lower-cases the rest.
- A `Function<Integer, String>` named `toBinary` that converts an integer to its binary string representation (use `Integer.toBinaryString`).
- A `Consumer<String>` named `printFormatted` that prints the string surrounded by square brackets, e.g., `[hello]`.
- A `Consumer<List<Integer>>` named `printSum` that prints the sum of all integers in the list.

Demonstrate each by calling it with appropriate test values.

**Expected output:**
```
capitalize(hELLO wORLD) = Hello world
toBinary(42) = 101010
[hello world]
sum([1, 2, 3, 4, 5]) = 15
```

### Exercise 2.3: Supplier and BiFunction

Write the following:
- A `Supplier<List<String>>` named `freshList` that returns a new empty `ArrayList<String>` each time it is called. Call it twice, add different elements to each result, and confirm both lists are independent.
- A `BiFunction<String, String, String>` named `formatEntry` that takes a key and a value and returns the string `"key=value"`.
- A `BiFunction<Integer, Integer, Integer>` named `power` that returns the first argument raised to the power of the second (use `Math.pow` and cast to `int`).

**Expected output:**
```
list1: [alpha]
list2: [beta]
entry: name=Alice
2^10 = 1024
```

---

## Exercise Set 3: Method References

### Exercise 3.1: Static Method References

Replace each lambda with an equivalent static method reference. Verify that the output is identical.

```java
Function<String, Integer> parseInt     = s -> Integer.parseInt(s);
Function<Double, Double>  absoluteVal  = d -> Math.abs(d);
Function<Integer, String> toBinaryStr  = n -> Integer.toBinaryString(n);
Consumer<String>          printLine    = s -> System.out.println(s);
```

Test values: `"255"` for `parseInt`, `-3.14` for `absoluteVal`, `255` for `toBinaryStr`, and `"method reference"` for `printLine`.

**Expected output:**
```
parseInt("255")    = 255
abs(-3.14)         = 3.14
toBinary(255)      = 11111111
method reference
```

### Exercise 3.2: Bound and Unbound Instance Method References

**Part A — Bound:** The instance is known ahead of time.

```java
String separator = "---";
// Write a Supplier<String> using a bound reference that returns separator.toUpperCase()
// Write a Function<String, Boolean> using a bound reference that tests whether
// a given string starts with "Java"
```

**Part B — Unbound:** The instance is the first argument to the lambda.

```java
// Write a Function<String, String>  using an unbound reference for String::toLowerCase
// Write a Function<String, Integer> using an unbound reference for String::length
// Write a Function<String, String>  using an unbound reference for String::trim
```

Demonstrate each with a test value.

**Expected output:**
```
separator upper: ---
startsWithJava("Java is great") = true
startsWithJava("Python is great") = false
lower("HELLO") = hello
length("lambda") = 6
trim("  hello  ") = hello
```

### Exercise 3.3: Constructor References

Write the following using constructor references (`ClassName::new`) instead of lambda expressions:
- A `Supplier<ArrayList<String>>` that creates a new empty `ArrayList<String>`.
- A `Function<String, StringBuilder>` that creates a `StringBuilder` initialized with the given string.
- A `Function<Integer, int[]>` that creates an `int[]` of the given length. (Hint: `int[]::new`.)

Add elements to the `ArrayList`, append to the `StringBuilder`, and verify the array length.

**Expected output:**
```
list: [hello, world]
builder: built
array length: 5
```

---

## Exercise Set 4: Composing Functions

### Exercise 4.1: Predicate Composition with and(), or(), negate()

Define the following base predicates for integers:
- `isEven` — divisible by 2.
- `isPositive` — greater than 0.
- `isMultipleOfFive` — divisible by 5.

Use composition to build:
- `isEvenAndPositive` using `and()`.
- `isEvenOrMultipleOfFive` using `or()`.
- `isOdd` using `negate()` on `isEven`.

Test each composite predicate against the values: -10, 0, 5, 6, 10, 15.

**Expected output:**
```
isEvenAndPositive:
  -10 -> false
    0 -> false
    5 -> false
    6 -> true
   10 -> true
   15 -> false
isEvenOrMultipleOfFive:
  -10 -> true
    0 -> true
    5 -> true
    6 -> true
   10 -> true
   15 -> true
isOdd:
  -10 -> false
    0 -> false
    5 -> true
    6 -> false
   10 -> false
   15 -> true
```

### Exercise 4.2: Function Composition with andThen() and compose()

Build the following individual `Function` instances:
- `trim` — removes leading and trailing whitespace from a string.
- `toLowerCase` — converts a string to lower case.
- `removeSpaces` — replaces all spaces with underscores.
- `addPrefix` — prepends the string `"tag_"`.

Chain them using `andThen()` to build a `Function<String, String>` named `normalize` that applies all four steps in the order listed above.

Then build the same pipeline using `compose()` in reverse order to confirm the result is identical.

Apply `normalize` to `"  Hello World  "`.

**Expected output:**
```
andThen result:  tag_hello_world
compose result:  tag_hello_world
```

### Exercise 4.3: Mixed Pipeline

Write a pipeline that:
1. Parses a `String` to an `Integer` (`Integer::parseInt`).
2. Multiplies by 3.
3. Subtracts 1.
4. Formats the result as `"value: N"`.

Compose steps 1–4 into a single `Function<String, String>` using `andThen()`.

Apply it to `"7"`, `"0"`, and `"-4"`.

**Expected output:**
```
pipeline("7")  = value: 20
pipeline("0")  = value: -1
pipeline("-4") = value: -13
```

---

## Exercise Set 5: Functional Interfaces with Collections

### Exercise 5.1: Sorting with Comparator Lambdas

Given the list of words: `["cherry", "banana", "fig", "date", "elderberry", "apple"]`

Sort it three times, printing the result of each sort:
1. Alphabetically (natural order).
2. By length, shortest first; ties broken alphabetically.
3. By length, longest first (reverse of sort 2).

**Expected output:**
```
alphabetical:       [apple, banana, cherry, date, elderberry, fig]
by length asc:      [fig, date, apple, banana, cherry, elderberry]
by length desc:     [elderberry, banana, cherry, apple, date, fig]
```

### Exercise 5.2: Filtering and Transforming Lists

Given this list of product names:
```java
List<String> products = List.of(
    "Widget Pro", "Gadget Mini", "Widget Basic", "Doohickey",
    "Gadget Max", "Thingamajig", "Widget Ultra"
);
```

Perform the following operations, each on the original list:
1. Use `removeIf` on a mutable copy to keep only products whose name starts with `"Widget"`. Print the result.
2. Use `forEach` to print each product name in upper case.
3. Use a manual loop with a `Function<String, String>` to build a new list where each product name has `" - ON SALE"` appended. Print the result.

**Expected output:**
```
widgets only: [Widget Pro, Widget Basic, Widget Ultra]
WIDGET PRO
GADGET MINI
WIDGET BASIC
DOOHICKEY
GADGET MAX
THINGAMAJIG
WIDGET ULTRA
on sale: [Widget Pro - ON SALE, Gadget Mini - ON SALE, Widget Basic - ON SALE, Doohickey - ON SALE, Gadget Max - ON SALE, Thingamajig - ON SALE, Widget Ultra - ON SALE]
```

### Exercise 5.3: Map Operations with Lambdas

Given a `Map<String, Integer>` representing student scores:
```java
Map<String, Integer> scores = new LinkedHashMap<>();
scores.put("Alice",   91);
scores.put("Bob",     74);
scores.put("Carol",   85);
scores.put("David",   60);
scores.put("Eve",     95);
```

Perform the following:
1. Use `forEach` to print each student and their grade letter: A (90+), B (80-89), C (70-79), D (60-69).
2. Use `computeIfAbsent` to add a default score of 0 for `"Frank"` if he is not already in the map. Print the updated map entry.
3. Use `replaceAll` with a `BiFunction<String, Integer, Integer>` to add 5 bonus points to every score (cap at 100 using `Math.min`). Print the map after the update.

**Expected output:**
```
Alice:   91 -> A
Bob:     74 -> C
Carol:   85 -> B
David:   60 -> D
Eve:     95 -> A
Frank default score: 0
after bonus: {Alice=96, Bob=79, Carol=90, David=65, Eve=100, Frank=5}
```

---

## Exercise Set 6: Custom Functional Interfaces

### Exercise 6.1: Define and Use @FunctionalInterface

Define each of the following custom functional interfaces in your solution file as nested interfaces:

```java
@FunctionalInterface
interface Validator<T> {
    boolean validate(T value);
}

@FunctionalInterface
interface Formatter<T> {
    String format(T value);
}

@FunctionalInterface
interface Reducer<T> {
    T reduce(T accumulator, T next);
}
```

Write the following lambdas:
- A `Validator<String>` that checks whether a string is a valid email address (contains exactly one `@` and at least one `.` after the `@`).
- A `Formatter<Double>` that formats a double as a currency string, e.g., `"$1,234.56"` (use `String.format("$%,.2f", ...)`).
- A `Reducer<Integer>` that returns the sum of its two arguments.

Demonstrate each, then use `Reducer<Integer>` in a loop to compute the sum of `[3, 7, 2, 9, 1]`.

**Expected output:**
```
validate(user@example.com) = true
validate(not-an-email)     = false
validate(bad@)             = false
format(1234.56) = $1,234.56
format(0.5)     = $0.50
reduce sum of [3, 7, 2, 9, 1] = 22
```

### Exercise 6.2: Generic Functional Interfaces

Define a generic functional interface:

```java
@FunctionalInterface
interface Transformer<A, B> {
    B transform(A input);
}
```

Write a static helper method:
```java
static <A, B> List<B> mapList(List<A> list, Transformer<A, B> transformer)
```

That applies `transformer` to every element and returns a new list of results.

Use `mapList` with the following transformers:
- `Transformer<String, Integer>` that returns the string's length.
- `Transformer<Integer, String>` that returns the integer in words if it is 1, 2, or 3, otherwise `"many"`.
- `Transformer<String, String>` that reverses the string.

Apply each to a suitable list and print the results.

**Expected output:**
```
lengths: [5, 6, 4, 6]
words:   [one, two, many, one, many]
reversed: [olleh, dlrow, avaj]
```

### Exercise 6.3: Chaining Custom Functional Interfaces

Using the `Transformer<A, B>` interface from Exercise 6.2, write a static method:

```java
static <A, B, C> Transformer<A, C> chain(Transformer<A, B> first, Transformer<B, C> second)
```

That returns a new `Transformer` which applies `first` then passes the result to `second`.

Demonstrate it by chaining:
1. `Transformer<String, Integer>` (parse to int) chained with `Transformer<Integer, Boolean>` (check if even).
2. `Transformer<String, String>` (trim) chained with `Transformer<String, String>` (upper case).
3. All three of the above steps chained: trim -> upper case -> length -> is even.

**Expected output:**
```
isEven("42") = true
isEven("7")  = false
trimAndUpper("  hello  ") = HELLO
trimUpperLengthEven("  hello  ") = false
trimUpperLengthEven("  hi  ") = true
```

---

## Common Mistakes

### Capturing mutable local variables

Lambda expressions can read variables from the enclosing scope, but those variables must be **effectively final** — they must never be reassigned after the lambda is created. Reassigning the variable after the lambda definition causes a compile error.

```java
// Wrong: count is reassigned after the lambda captures it.
int count = 0;
count = 1; // This reassignment breaks the effectively-final requirement.
Runnable r = () -> System.out.println(count); // Compile error.

// Correct option 1: never reassign the variable.
int count = 0;
Runnable r = () -> System.out.println(count); // count is effectively final.

// Correct option 2: if you need mutation, use an instance field or an array cell.
int[] count = {0};
Runnable r = () -> count[0]++;  // The array reference is final; its contents are not.
```

### Confusing Consumer with Function

`Consumer<T>` has a `void` return type — it produces no result. `Function<T, R>` returns a value of type `R`. The compile error you get when you mix them up can be misleading because both accept one argument.

```java
// Wrong: println returns void, so this cannot be a Function<String, ?>.
Function<String, ?> print = s -> System.out.println(s); // Compile error.

// Correct: println is a Consumer — it consumes a value and returns nothing.
Consumer<String> print = s -> System.out.println(s);

// Correct: use Function when you need the return value.
Function<String, Integer> length = s -> s.length();
```

### Method reference syntax errors

A method reference replaces a lambda whose entire body is a single method call with matching parameters. The most common mistakes are using the wrong form or providing the wrong number of arguments.

```java
// Wrong: toUpperCase takes no arguments — this static-reference form expects one.
// String::toUpperCase used as a BiFunction<String, String, String> will not compile
// because the method has no parameters matching the second argument.

// Correct unbound form: the instance IS the first (and only) lambda argument.
Function<String, String> upper = String::toUpperCase; // String -> String

// Wrong: parseInt is a static method, not an instance method of String.
Function<String, Integer> parse = String::parseInt; // Compile error — no such method.

// Correct static reference.
Function<String, Integer> parse = Integer::parseInt; // String -> Integer

// Wrong: constructor reference for a class that takes no matching constructor.
// Function<Integer, String> make = String::new; would need String(int) which
// constructs a character-array string, not what you usually want.
// Use an explicit lambda when the intent could be ambiguous.
Function<Integer, String> make = n -> String.valueOf(n); // Clear and explicit.
```

---

Solutions for these exercises are in the `solutions/` subfolder.
