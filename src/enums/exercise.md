# Enums Practice Exercises

These exercises cover every major aspect of Java enums, from basic declarations through abstract methods, interfaces, switch expressions, and the specialized `EnumSet` and `EnumMap` classes. Work through each set in order — later sets build on the patterns introduced in earlier ones.

---

## Exercise Set 1: Basic Enums

### Exercise 1.1: Declare a Simple Enum

Declare an enum `Color` with four constants: `RED`, `GREEN`, `BLUE`, `YELLOW`.

In `main`:
1. Assign `Color.GREEN` to a variable and print it.
2. Compare it to `Color.BLUE` using `==` and print whether they are equal.

### Exercise 1.2: values(), ordinal(), and name()

Using the `Color` enum from Exercise 1.1, write a for-each loop that iterates over `Color.values()` and prints each constant with its ordinal and name in this format:

```
0: RED
1: GREEN
2: BLUE
3: YELLOW
```

### Exercise 1.3: valueOf()

Using `Color.valueOf(String)`:
1. Look up `"BLUE"` and print the result.
2. Attempt to look up `"blue"` inside a try-catch and print the exception message when it is thrown. Explain in a comment why this happens.

### Exercise 1.4: Iterating with a Traditional for Loop

Using `Color.values()`, iterate over the array with a traditional indexed `for` loop and print each constant alongside its array index. The output should be identical to Exercise 1.2.

---

## Exercise Set 2: Enums with Fields and Constructors

### Exercise 2.1: Traffic Light with Duration

Declare an enum `TrafficLight` with three constants — `RED`, `YELLOW`, `GREEN` — each carrying an `int durationSeconds` field.

- `RED` lasts 45 seconds
- `YELLOW` lasts 5 seconds
- `GREEN` lasts 30 seconds

Add a constructor and a `getDuration()` accessor. In `main`, iterate over all constants and print each light with its duration:

```
RED    : 45 seconds
YELLOW : 5 seconds
GREEN  : 30 seconds
```

### Exercise 2.2: Planet Gravity Calculator

Declare an enum `Planet` with at least four constants (`MERCURY`, `VENUS`, `EARTH`, `MARS`). Each constant stores `double mass` (kg) and `double radius` (m).

Add:
- A static constant `G = 6.67300E-11` for the gravitational constant
- A method `surfaceGravity()` returning `G * mass / (radius * radius)`
- A method `surfaceWeight(double otherMass)` returning `otherMass * surfaceGravity()`

In `main`, assume a weight of 75.0 N on Earth. Compute the corresponding mass and print the weight on every planet:

```
Weight on MERCURY: 28.33 N
Weight on VENUS:   67.89 N
Weight on EARTH:   75.00 N
Weight on MARS:    28.46 N
```

---

## Exercise Set 3: Enum Methods

### Exercise 3.1: Abstract Methods per Constant

Declare an enum `MathOperation` with four constants: `PLUS`, `MINUS`, `MULTIPLY`, `DIVIDE`.

Add an abstract method `double apply(double x, double y)` and override it in each constant:
- `PLUS` returns `x + y`
- `MINUS` returns `x - y`
- `MULTIPLY` returns `x * y`
- `DIVIDE` returns `x / y` (throw `ArithmeticException` if `y == 0`)

In `main`, with `x = 10.0` and `y = 4.0`, iterate over all operations and print:

```
10.0 PLUS 4.0 = 14.0
10.0 MINUS 4.0 = 6.0
10.0 MULTIPLY 4.0 = 40.0
10.0 DIVIDE 4.0 = 2.5
```

### Exercise 3.2: Instance Methods Shared Across Constants

Declare an enum `Season` with constants `SPRING`, `SUMMER`, `FALL`, `WINTER`, each carrying a `String description` field.

Add a non-abstract instance method `printInfo()` that prints the constant name and its description in one line. Call it for every season.

Also add a static method `fromDescription(String desc)` that searches `values()` and returns the matching constant, or throws `IllegalArgumentException` if none is found. Demonstrate it by looking up a known description.

---

## Exercise Set 4: Enums in Switch Expressions and Implementing Interfaces

### Exercise 4.1: Switch Expression (Java 14+)

Using the `Season` enum from Exercise 3.2 (constants only — no fields required here), write a switch expression that assigns a `String activity` based on the season:

| Season | Activity        |
|--------|-----------------|
| SPRING | "Plant seeds"   |
| SUMMER | "Go swimming"   |
| FALL   | "Rake leaves"   |
| WINTER | "Build a snowman"|

Demonstrate with at least two different seasons. Note in a comment why a `default` branch is not required.

### Exercise 4.2: Enum Implementing an Interface

Declare an interface `Describable` with a single method `String getDescription()`.

Declare an enum `StatusCode` that implements `Describable` with these constants and fields:

| Constant               | code |
|------------------------|------|
| OK                     | 200  |
| NOT_FOUND              | 404  |
| INTERNAL_SERVER_ERROR  | 500  |
| UNAUTHORIZED           | 401  |

Add an `int code` field, a constructor, and implement `getDescription()` to return a string in the format `"200 OK"` (replace underscores in the name with spaces).

Write a helper method `static void print(Describable d)` that calls `getDescription()` through the interface type — not the enum type — and pass each constant to it.

---

## Exercise Set 5: EnumSet and EnumMap

### Exercise 5.1: EnumSet.of and Membership Testing

Using a `DayOfWeek` enum (seven constants: `MONDAY` through `SUNDAY`):

1. Create a workdays set using `EnumSet.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY)`.
2. Create a weekend set using `EnumSet.of(SATURDAY, SUNDAY)`.
3. For each day in `DayOfWeek.values()`, print whether it is a workday or a weekend day.

Expected output:

```
MONDAY    -> workday
TUESDAY   -> workday
WEDNESDAY -> workday
THURSDAY  -> workday
FRIDAY    -> workday
SATURDAY  -> weekend
SUNDAY    -> weekend
```

### Exercise 5.2: EnumSet.range

Using the same `DayOfWeek` enum:

1. Use `EnumSet.range(TUESDAY, THURSDAY)` to produce a midweek set.
2. Print the set.
3. Use `EnumSet.allOf(DayOfWeek.class)` and print its size to confirm it contains all seven constants.

### Exercise 5.3: EnumMap for Seasonal Activities

Using the `Season` enum (constants only: `SPRING`, `SUMMER`, `FALL`, `WINTER`):

1. Build an `EnumMap<Season, String>` that maps each season to a suggested activity.
2. Print all entries by iterating over `entrySet()`.
3. Retrieve and print the activity for a specific season using `get()`.
4. Update one season's activity using `put()` and print the map again to verify the change.

---

## Common Mistakes

### Comparing enum values with .equals() instead of ==

Both work, but `==` is preferred. It is null-safe — `.equals()` on a null reference throws `NullPointerException` — and it is equally correct because enum constants are singletons.

### Making the enum constructor public or protected

Enum constructors are implicitly private. Adding `public` or `protected` is a compile error. The constants are the only instances that can ever exist, and the JVM enforces this.

### Using ordinal() for storage or logic

Ordinals are fragile. If you insert a new constant or reorder existing ones, every stored ordinal silently refers to a different value. Store the constant's `name()` or a dedicated stable field instead.

### Calling valueOf() with the wrong case

`valueOf()` is case-sensitive. `Season.valueOf("spring")` throws `IllegalArgumentException` even though `SPRING` is a valid constant. Always match the exact name used in the declaration.

### Forgetting that switch expressions on enums are exhaustive

When you use a switch expression (Java 14+) on an enum without a `default`, the compiler requires every constant to be handled. This is a feature: adding a new constant later will produce a compile error at every switch expression that no longer covers all cases, rather than a silent runtime bug.

---

Solutions for these exercises are in the `solutions/` subfolder.
