# Data Types

## Learning Objectives

By the end of this topic, you should be able to:

- Name Java's eight primitive types and select the right one for a given value
- Distinguish primitive types from reference types
- Explain how primitives and objects are stored in memory
- Read and write numeric, character, boolean, and String literals correctly

---

## 1. What Are Data Types?

**Data types** specify the kind of data that can be stored in a variable. They define the size of memory allocated and the operations that can be performed on the data.

Java divides data types into two main categories: **primitive types** and **reference types**. Primitive types are built into the language; reference types are class-based and include `String`, arrays, and all user-defined classes.

---

## 2. Primitive Data Types

Java provides **8 primitive data types**.

### 2.1 Integer Types

| Type | Size | Range | Default | Description |
|------|------|-------|---------|-------------|
| `byte` | 8 bits | -128 to 127 | 0 | Smallest integer type |
| `short` | 16 bits | -32,768 to 32,767 | 0 | Short integer |
| `int` | 32 bits | -2,147,483,648 to 2,147,483,647 | 0 | Most commonly used integer |
| `long` | 64 bits | -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 | 0L | Large integer values |

```java
byte  temperature = 25;
short year        = 2024;
int   population  = 1_000_000;        // most common choice
long  distance    = 150_000_000_000L; // L suffix required for long literals
```

### 2.2 Floating-Point Types

| Type | Size | Precision | Default | Description |
|------|------|-----------|---------|-------------|
| `float` | 32 bits | ~7 decimal digits | 0.0f | Single-precision |
| `double` | 64 bits | ~15 decimal digits | 0.0d | Double-precision (preferred) |

```java
float  price      = 19.99f;     // f suffix required for float literals
double pi         = 3.14159265359;
double scientific = 1.23e10;    // 12,300,000,000
```

Prefer `double` over `float` in most cases — it has more precision and is the default for decimal literals.

### 2.3 Character Type

| Type | Size | Range | Default | Description |
|------|------|-------|---------|-------------|
| `char` | 16 bits | 0 to 65,535 (Unicode) | '\u0000' | Single Unicode character |

```java
char grade   = 'A';
char symbol  = '$';
char unicode = '\u0041'; // 'A' — using its Unicode code point
char newline = '\n';     // escape sequence for newline
```

### 2.4 Boolean Type

| Type | Values | Default | Description |
|------|--------|---------|-------------|
| `boolean` | `true` or `false` | `false` | Logical values |

```java
boolean isStudent    = true;
boolean isFinished   = false;
boolean hasPermission = (age >= 18); // result of a comparison expression
```

---

## 3. Reference Types — String

`String` is a class, not a primitive type. A String holds a sequence of characters and lives on the heap.

```java
String name    = "John Doe";
String message = "Welcome to Java!";
String empty   = "";    // empty string — not null
String none    = null;  // holds no reference to any object
```

See [strings.md](../strings/strings.md) for concatenation and the most useful String methods, and [strings-deep-dive.md](../strings/strings-deep-dive.md) for the String pool, immutability, and `StringBuilder`.

---

## 4. Memory: Stack vs Heap

**Stack memory** stores primitive variables and the reference variables (not the objects themselves) declared inside methods. It is fast and automatically reclaimed when the method returns.

**Heap memory** stores all objects — including `String` instances and arrays. The Garbage Collector reclaims heap memory when no references point to an object.

```java
int    age  = 30;           // primitive — value lives on the stack
String name = "Alice";      // object lives on the heap;
                            // 'name' on the stack holds a reference to it
```

---

## 5. Literal Values

A **literal** is a fixed value written directly in source code.

### 5.1 Integer Literals

```java
int decimal = 42;          // base 10 — default
int binary  = 0b101010;    // base 2  — 0b prefix
int octal   = 052;         // base 8  — 0 prefix
int hex     = 0x2A;        // base 16 — 0x prefix
long big    = 9_000_000L;  // underscore separators improve readability; L suffix for long
```

### 5.2 Floating-Point Literals

```java
double d = 3.14;    // double by default
float  f = 3.14f;   // f suffix makes it a float literal
double e = 1.5e3;   // scientific notation: 1500.0
```

### 5.3 Character and Boolean Literals

```java
char letter = 'A';    // single quotes
char tab    = '\t';   // tab escape sequence
char quote  = '\'';   // escaped single quote
boolean yes = true;   // only true or false
```

### 5.4 String Literals

```java
String simple    = "Hello World";
String escaped   = "He said, \"Hello!\"";
String multiLine = "Line 1\nLine 2\nLine 3";
```

---

## Common Mistakes

### Forgetting the L suffix on long literals

```java
// Compile error: integer literal 10000000000 is out of int range
long big = 10000000000;

// Fix: add the L suffix
long big = 10_000_000_000L;
```

### Forgetting the f suffix on float literals

```java
// Compile error: cannot convert double to float without a cast
float price = 19.99;

// Fix: add the f suffix
float price = 19.99f;
```

### Assigning a double literal directly to an int

```java
// Compile error: possible lossy conversion from double to int
int number = 3.14;

// Fix: use the correct type, or cast explicitly (decimal part is discarded)
double number = 3.14;
int truncated = (int) 3.14; // 3
```

---

## What's Next?

**Next:** [Variables](../variables.md)
