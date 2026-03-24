# Type Casting and Conversion

## Learning Objectives

By the end of this topic, you should be able to:

- Explain the difference between widening and narrowing conversions
- Write explicit narrowing casts and predict what data may be lost
- Convert between primitive types and String using the standard methods

---

## 1. What Is Type Casting?

**Type casting** converts a value from one type to another. Java handles this in two ways:

- **Widening** — automatic; no data is lost
- **Narrowing** — requires an explicit cast; data may be lost

---

## 2. Widening Conversion (Automatic)

Java converts a smaller type to a larger one automatically. No cast syntax is needed because no data can be lost.

```
byte → short → int → long → float → double
```

```java
byte   b = 42;
short  s = b;    // byte  → short:  automatic
int    i = s;    // short → int:    automatic
long   l = i;    // int   → long:   automatic
float  f = l;    // long  → float:  automatic
double d = f;    // float → double: automatic
```

---

## 3. Narrowing Conversion (Explicit)

Converting to a smaller type requires an explicit cast `(targetType)` because data may be lost. The compiler enforces this — code without the cast will not compile.

```java
double d  = 9.78;
int    i  = (int) d;    // 9 — decimal part is truncated, not rounded

long   l  = 123_456_789_000L;
int    n  = (int) l;    // data loss: high bits are discarded

float  f  = 3.14f;
int    fi = (int) f;    // 3
```

**Narrowing to byte — two-step process:**

```java
double d = 300.75;
byte   b = (byte) d;
// Step 1: truncate double → int → 300
// Step 2: 300 exceeds byte's max (127), so it overflows:
//         300 - 256 = 44
System.out.println(b); // 44
```

---

## 4. char and int

`char` and integer types are interchangeable through casting. Every `char` has a corresponding Unicode integer value.

```java
char letter = 'A';
int  code   = (int) letter;       // 65 — Unicode code point for 'A'
char next   = (char)(letter + 1); // 'B'

// Print A-Z with their Unicode values:
for (int i = 0; i < 26; i++) {
    char c = (char)('A' + i);
    System.out.printf("%c = %d%n", c, (int) c);
}
```

---

## 5. String Conversion

**Primitive to String:**

```java
int age = 25;
String s1 = String.valueOf(age);    // "25" — preferred
String s2 = Integer.toString(age);  // "25"
String s3 = age + "";               // "25" — works but less readable
```

**String to primitive:**

```java
int     i = Integer.parseInt("123");       // 123
double  d = Double.parseDouble("45.67");   // 45.67
boolean b = Boolean.parseBoolean("true");  // true
long    l = Long.parseLong("9000000000");  // 9000000000
```

If the String is not a valid number, the parse methods throw a `NumberFormatException`:

```java
int x = Integer.parseInt("hello"); // throws NumberFormatException at runtime
```

---

## Common Mistakes

### Expecting rounding instead of truncation

Casting a floating-point value to an integer type truncates — it drops the decimal part without rounding.

```java
int a = (int) 9.99;  // 9, not 10
int b = (int) 3.1;   // 3
```

Use `Math.round()` when rounding is needed:

```java
long rounded = Math.round(9.99); // 10
```

### Silent overflow in narrowing casts

There is no exception when a value does not fit in the target type. The high bits are simply discarded, producing a result that can appear completely unrelated to the original value.

```java
int  big = 300;
byte b   = (byte) big; // 44, not 300 — no exception, no warning
```

Always verify that the value is within the target type's range before narrowing.

### Parsing a non-numeric String

```java
int x = Integer.parseInt("12abc"); // NumberFormatException at runtime
```

Validate or sanitize String input before parsing.

---

## What's Next?

**Next:** [Scanner and User Input](../scanner/scanner.md)
