# Variables

## Learning Objectives

By the end of this topic, you should be able to:

- Declare and initialize variables of any primitive or reference type
- Follow Java's naming conventions consistently
- Understand variable scope in a method body
- Use `final` to declare constants and `var` for type inference

---

## 1. What Is a Variable?

A **variable** is a named storage location in memory that holds a value of a specific type. Think of it as a labeled container — the label is the name, the container size is determined by the type, and the contents are the value.

```java
int age = 25;  // type: int | name: age | value: 25
```

---

## 2. Declaration and Initialization

**Declaration** introduces a variable name and its type:

```java
int count;
String name;
```

**Initialization** assigns a value for the first time:

```java
count = 10;
name  = "Alice";
```

**Combined (recommended):**

```java
int    count = 10;
String name  = "Alice";
```

A local variable must be initialized before it is read. The compiler rejects code that might read an unassigned variable.

```java
int total;
System.out.println(total); // Compile error: variable total might not have been initialized
```

---

## 3. Multiple Declarations

You can declare multiple variables of the same type on one line when they are closely related:

```java
int x = 10, y = 20, z = 30;
```

---

## 4. Naming Conventions

Java uses **camelCase** for variable names.

**Good names:**

```java
int    studentAge;
double accountBalance;
String firstName;
boolean isComplete;
char   userChoice;
```

**Avoid:**

```java
int a;               // not descriptive
double student_age;  // underscore style (used for constants, not variables)
String FirstName;    // starts with uppercase — that convention is for class names
boolean 2ndChoice;   // starts with a number — compile error
char user-choice;    // contains a hyphen — compile error
```

**Compiler-enforced rules:**
1. Must start with a letter, `_`, or `$`
2. Cannot start with a number
3. Cannot be a Java keyword (`int`, `class`, `if`, etc.)
4. Case-sensitive — `age` and `Age` are different variables

**Team conventions:**
- camelCase: `firstName`, `accountBalance`
- Descriptive names that explain the purpose, not the type
- Boolean variables often start with `is`, `has`, or `can`: `isValid`, `hasPermission`

---

## 5. Constants

Use `final` to declare a variable whose value cannot change after initialization. Constants use `UPPER_SNAKE_CASE` by convention.

```java
final double PI          = 3.14159265359;
final int    MAX_RETRIES = 3;
final String APP_NAME    = "MyApp";
```

Attempting to reassign a `final` variable is a compile error:

```java
final int MAX = 10;
MAX = 20; // Compile error: cannot assign a value to final variable MAX
```

---

## 6. Variable Scope

A variable is only accessible within the block `{}` where it is declared. Attempting to read it outside that block is a compile error.

```java
if (true) {
    int result = 42;
    System.out.println(result); // works
}
System.out.println(result); // Compile error: cannot find symbol 'result'
```

Declare a variable before a block if you need its value after the block ends:

```java
int result;
if (condition) {
    result = 42;
} else {
    result = 0;
}
System.out.println(result); // accessible here
```

---

## 7. The var Keyword

Java 10 introduced `var`, which lets the compiler infer the type of a local variable from the right-hand side of the assignment:

```java
var message = "Hello";    // inferred as String
var count   = 0;          // inferred as int
var price   = 9.99;       // inferred as double
```

`var` is not dynamic typing — the inferred type is fixed at compile time. The variable behaves identically to one with an explicit type.

Use `var` when the type is obvious from context. Avoid it when the right-hand side does not make the type clear.

```java
var list = new ArrayList<String>(); // clear — type is stated in the constructor
var result = compute();             // unclear — reader must look up compute()
```

See [var.md](var.md) for a full reference including restrictions, edge cases, and when to prefer explicit types.

---

## Common Mistakes

### Reading an uninitialized local variable

```java
int total;
// ... some code that might or might not assign total ...
System.out.println(total); // Compile error if the compiler cannot prove total was assigned

// Fix: initialize at the point of declaration
int total = 0;
```

### Declaring the same variable name twice in the same scope

```java
int x = 10;
int x = 20; // Compile error: variable x is already defined in this scope

// Fix: assign a new value without re-declaring
int x = 10;
x = 20;
```

---

## What's Next?

**Next:** [Operators](operators.md)
