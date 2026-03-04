# Enums & Collections Framework — Practice Exercises

These exercises combine enums with the Collections Framework. Each exercise builds on material from both topics, so make sure you have read through `enums.md` and `collections.md` before starting.

---

## Exercise Set 1: Enums in Collections

### Exercise 1.1: Season Tracker

Create an enum `Season` with constants `SPRING`, `SUMMER`, `FALL`, `WINTER`, each carrying an `averageTemp` field (in °F).

Write a program that:

1. Stores all four seasons in an `ArrayList<Season>`.
2. Iterates through the list and prints each season with its average temperature.
3. Finds and prints the hottest and coldest seasons using a loop (not `Collections.max`).

**Expected output (temperatures you choose):**

```
SPRING — 65°F
SUMMER — 85°F
FALL   — 55°F
WINTER — 30°F

Hottest: SUMMER (85°F)
Coldest: WINTER (30°F)
```

### Exercise 1.2: Unique Roles

Create an enum `Role` with constants `ADMIN`, `EDITOR`, `VIEWER`, `MODERATOR`.

Write a program that:

1. Creates a `List<Role>` containing duplicates: `ADMIN, VIEWER, EDITOR, VIEWER, ADMIN, MODERATOR, EDITOR`.
2. Converts the list into a `Set<Role>` to remove duplicates.
3. Prints the unique roles.
4. Checks whether the set contains `ADMIN` and whether it contains a non-existent role value (use `contains`).

### Exercise 1.3: Day Frequency Counter

Create an enum `Day` with seven constants (`MONDAY` through `SUNDAY`).

Given this list of days representing a two-week work schedule:

```java
List<Day> schedule = Arrays.asList(
    Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY,
    Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY,
    Day.SATURDAY
);
```

Write a program that uses a `HashMap<Day, Integer>` to count how many times each day appears, then prints the result sorted by day ordinal.

**Expected output:**

```
MONDAY    : 2
TUESDAY   : 2
WEDNESDAY : 2
THURSDAY  : 2
FRIDAY    : 2
SATURDAY  : 1
SUNDAY    : 0
```

---

## Exercise Set 2: EnumMap & EnumSet

### Exercise 2.1: Task Priority Board

Create an enum `Priority` with constants `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`.

Use an `EnumMap<Priority, List<String>>` to build a task board where each priority maps to a list of task descriptions.

**Requirements:**

1. Add at least two tasks to each priority level.
2. Print all tasks grouped by priority.
3. Move a task from `LOW` to `HIGH` (remove from one list, add to the other).
4. Print the board again to verify the move.

**Example output:**

```
--- Task Board ---
LOW      : [Organize desk, Update profile picture]
MEDIUM   : [Write unit tests, Review documentation]
HIGH     : [Fix login bug, Optimize database queries]
CRITICAL : [Patch security vulnerability, Restore backups]

Moving "Organize desk" from LOW -> HIGH

--- Task Board (updated) ---
LOW      : [Update profile picture]
MEDIUM   : [Write unit tests, Review documentation]
HIGH     : [Fix login bug, Optimize database queries, Organize desk]
CRITICAL : [Patch security vulnerability, Restore backups]
```

### Exercise 2.2: Permission System

Create an enum `Permission` with constants: `READ`, `WRITE`, `DELETE`, `EXECUTE`, `ADMIN`.

Use `EnumSet` to model permission sets for different user types:

```
Guest    : READ
Editor   : READ, WRITE
Developer: READ, WRITE, EXECUTE
Admin    : all permissions
```

**Requirements:**

1. Create each permission set using appropriate `EnumSet` factory methods (`of`, `allOf`, `range`, etc.).
2. Write a method `boolean hasAccess(EnumSet<Permission> userPerms, Permission required)` that checks whether a user has a specific permission.
3. Write a method `EnumSet<Permission> grant(EnumSet<Permission> current, Permission additional)` that returns a new set with the additional permission included.
4. Demonstrate granting `DELETE` to the `Editor` set and verify with `hasAccess`.

### Exercise 2.3: Enum Lookup Map

Create an enum `HttpStatus` with these constants and fields:

| Constant              | code | message               |
|-----------------------|------|-----------------------|
| OK                    | 200  | "OK"                  |
| CREATED               | 201  | "Created"             |
| BAD_REQUEST           | 400  | "Bad Request"         |
| UNAUTHORIZED          | 401  | "Unauthorized"        |
| NOT_FOUND             | 404  | "Not Found"           |
| INTERNAL_SERVER_ERROR | 500  | "Internal Server Error"|

**Requirements:**

1. Add `int code` and `String message` fields with a constructor.
2. Create a `private static final Map<Integer, HttpStatus>` inside the enum that maps each code to its constant (populate it in a static block using `values()`).
3. Add a public static method `fromCode(int code)` that looks up a status by its code and throws `IllegalArgumentException` if the code is unknown.
4. In `main`, look up codes 200, 404, and 999 (handle the exception for 999) and print the result.

---

## Exercise Set 3: Combining Enums with Multiple Collections

### Exercise 3.1: Student Grade Tracker

Create an enum `Grade` with constants `A`, `B`, `C`, `D`, `F`, each carrying a `double gpaValue` (4.0, 3.0, 2.0, 1.0, 0.0).

Create a simple `Student` class with `String name` and `Grade grade`.

**Requirements:**

1. Create a `List<Student>` with at least eight students covering all grade levels.
2. Build a `Map<Grade, List<Student>>` that groups students by their grade. Use a loop to populate it.
3. Print each grade group with student names.
4. Calculate and print the class GPA average using the `gpaValue` from the enum.
5. Use a `TreeMap` so grades print in sorted order (hint: enums implement `Comparable` by ordinal).

**Expected output (example):**

```
--- Grade Report ---
A : [Alice, Eve]
B : [Bob, Frank]
C : [Carol, Grace]
D : [Dave]
F : [Hank]

Class GPA: 2.38
```

### Exercise 3.2: Music Playlist Organizer

Create an enum `Genre` with constants: `POP`, `ROCK`, `JAZZ`, `CLASSICAL`, `HIP_HOP`, `ELECTRONIC`.

Create a `Song` class with `String title`, `String artist`, and `Genre genre`.

**Requirements:**

1. Create a `List<Song>` with at least 10 songs across multiple genres.
2. Build a `Map<Genre, List<Song>>` that groups songs by genre.
3. Create a `Set<String>` of unique artists from the entire playlist.
4. Write a method `List<Song> filterByGenre(List<Song> songs, Genre genre)` that returns only songs matching a given genre.
5. Write a method `Map<Genre, Integer> countByGenre(List<Song> songs)` that returns how many songs belong to each genre.
6. Print:
    - All songs grouped by genre
    - The total number of unique artists
    - The genre with the most songs

### Exercise 3.3: Inventory Management System

Create an enum `Category` with constants: `ELECTRONICS`, `CLOTHING`, `FOOD`, `BOOKS`, `TOYS`. Each constant should carry a `double taxRate` (e.g., Electronics 15%, Clothing 8%, Food 0%, Books 5%, Toys 10%).

Create a `Product` class with fields: `String id`, `String name`, `Category category`, `double price`, `int quantity`.

**Requirements:**

1. Store products in a `List<Product>`.
2. Build an index: `Map<String, Product>` mapping product ID to product for fast lookup.
3. Build a category index: `Map<Category, List<Product>>` grouping products by category.
4. Write these methods:
    - `double calculateTotalValue(List<Product> products)` — sum of `price × quantity` for all products.
    - `double calculateTaxForProduct(Product p)` — uses the category's tax rate to compute tax on `price × quantity`.
    - `Map<Category, Double> taxByCategory(List<Product> products)` — total tax owed per category.
    - `List<Product> lowStock(List<Product> products, int threshold)` — products with quantity below the threshold.
5. Print a formatted inventory report showing all products, total value, tax breakdown by category, and low-stock warnings.

---

## Exercise Set 4: Real-World Mini-Projects

### Exercise 4.1: Order Processing System

Build a small order processing simulation using enums and collections.

**Enums to create:**

```java
public enum OrderStatus {
    PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED;
}

public enum PaymentMethod {
    CREDIT_CARD, DEBIT_CARD, PAYPAL, BANK_TRANSFER;
}
```

**Classes to create:**

`Order` — with fields: `String orderId`, `String customerName`, `OrderStatus status`, `PaymentMethod payment`, `List<String> items`, `double total`.

**Requirements:**

1. Store all orders in a `List<Order>`.
2. Build a `Map<OrderStatus, List<Order>>` that groups orders by status.
3. Build a `Map<PaymentMethod, Double>` that tracks total revenue per payment method.
4. Write a method `boolean advanceStatus(Order order)` that moves an order to the next logical status (PENDING → CONFIRMED → SHIPPED → DELIVERED). Return `false` if the order is already DELIVERED or CANCELLED.
5. Write a method `void cancelOrder(Order order)` that sets the status to CANCELLED only if the order is PENDING or CONFIRMED.
6. Create at least six orders, advance some through the pipeline, cancel one, and print:
    - Orders grouped by status
    - Revenue per payment method
    - A count of orders in each status using `EnumMap<OrderStatus, Integer>`

### Exercise 4.2: Quiz Application

Build a console-based multiple-choice quiz.

**Enum to create:**

```java
public enum Difficulty {
    EASY(1), MEDIUM(2), HARD(3);

    private final int pointValue;

    Difficulty(int pointValue) {
        this.pointValue = pointValue;
    }

    public int getPointValue() { return pointValue; }
}
```

**Class to create:**

`Question` — with fields: `String text`, `List<String> options` (A through D), `int correctIndex` (0–3), `Difficulty difficulty`.

**Requirements:**

1. Store questions in a `List<Question>`.
2. Group questions by difficulty using a `Map<Difficulty, List<Question>>`.
3. Use a `Scanner` to present each question to the user and read their answer.
4. Track the score: each correct answer earns `difficulty.getPointValue()` points.
5. After the quiz, use a `LinkedHashMap<String, Object>` (or similar) to print a results summary:
    - Total score / maximum possible score
    - Number correct per difficulty level
    - Percentage score
6. Create at least nine questions (three per difficulty level).

### Exercise 4.3: Employee Directory (Capstone)

Build a fully functional employee directory that ties together enums, `List`, `Set`, `Map`, `EnumMap`, and `EnumSet`.

**Enums to create:**

```java
public enum Department {
    ENGINEERING, MARKETING, SALES, HR, FINANCE
}

public enum Skill {
    JAVA, PYTHON, SQL, EXCEL, COMMUNICATION,
    PROJECT_MANAGEMENT, DATA_ANALYSIS, DESIGN
}
```

**Class to create:**

`Employee` — with fields: `String id`, `String name`, `Department department`, `double salary`, `EnumSet<Skill> skills`.

**Requirements:**

1. Store employees in a `List<Employee>`.
2. Build a `Map<String, Employee>` for O(1) lookup by ID.
3. Build an `EnumMap<Department, List<Employee>>` grouping employees by department.
4. Write these methods:
    - `Set<Employee> findBySkill(Skill skill)` — returns all employees who have that skill.
    - `Map<Department, Double> averageSalaryByDepartment()` — average salary per department.
    - `EnumSet<Skill> teamSkills(Department dept)` — union of all skills across a department.
    - `Set<Employee> findBySkills(EnumSet<Skill> required)` — returns employees who have **all** of the required skills.
    - `Map<Skill, Integer> skillFrequency()` — how many employees have each skill.
5. Create at least 10 employees spread across departments with varying skills.
6. Print:
    - Full directory grouped by department
    - Average salary per department
    - Combined skill set of each department
    - Employees who have both `JAVA` and `SQL`
    - The most common skill across the company

---

## Tips for Completing These Exercises

1. **Start with the enum.** Define your constants, fields, and constructor before writing any collection logic.
2. **Program to the interface.** Declare variables as `List`, `Set`, `Map` — not `ArrayList`, `HashSet`, `HashMap`.
3. **Use `EnumMap` and `EnumSet`** whenever your keys are enum constants. They are faster and more memory-efficient than their general-purpose counterparts.
4. **Test edge cases:** empty collections, missing map keys (use `getOrDefault` or `computeIfAbsent`), and invalid enum lookups.
5. **Print intermediate results.** When building maps, print them right after population to verify correctness before moving on.
6. **Reuse enum methods.** If your enum has a field or method, use it rather than duplicating the logic in your main code.

---

## Common Mistakes to Avoid

### With Enums

- Comparing enums with `.equals()` instead of `==` — both work, but `==` is preferred (null-safe)
- Forgetting that enum constructors are implicitly `private` — never add `public`
- Using `ordinal()` for persistent storage — ordinals change when constants are reordered
- Calling `valueOf()` with the wrong case — it is case-sensitive and throws `IllegalArgumentException`

### With Collections

- Modifying a list while iterating with a for-each loop — use an `Iterator` or collect changes separately
- Forgetting that `HashSet` and `HashMap` do not preserve insertion order
- Using a mutable object as a `Map` key or `Set` element without properly overriding `hashCode()` and `equals()`
- Not initializing inner lists when building a `Map<K, List<V>>` — use `computeIfAbsent(key, k -> new ArrayList<>())`

---

Happy Coding!
