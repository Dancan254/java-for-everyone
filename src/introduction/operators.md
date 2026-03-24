# Operators

## Learning Objectives

By the end of this topic, you should be able to:

- Use arithmetic and assignment operators to compute results
- Distinguish between pre- and post-increment/decrement
- Predict the result of an expression using operator precedence
- Avoid silent integer division when a fractional result is needed

---

## 1. Arithmetic Operators

| Operator | Name | Example | Result |
|----------|------|---------|--------|
| `+` | Addition | `5 + 3` | `8` |
| `-` | Subtraction | `5 - 3` | `2` |
| `*` | Multiplication | `5 * 3` | `15` |
| `/` | Division | `5 / 2` | `2` (integer division) |
| `%` | Modulus | `5 % 2` | `1` |

```java
int a = 10, b = 3;

int sum        = a + b;   // 13
int difference = a - b;   // 7
int product    = a * b;   // 30
int quotient   = a / b;   // 3 — fractional part discarded
int remainder  = a % b;   // 1

double precise = (double) a / b; // 3.3333... — cast before dividing
```

The modulus operator is useful for divisibility checks:

```java
if (number % 2 == 0) {
    System.out.println("Even");
}
```

---

## 2. Assignment Operators

| Operator | Meaning | Equivalent |
|----------|---------|------------|
| `=` | Assign | — |
| `+=` | Add and assign | `a = a + b` |
| `-=` | Subtract and assign | `a = a - b` |
| `*=` | Multiply and assign | `a = a * b` |
| `/=` | Divide and assign | `a = a / b` |
| `%=` | Modulus and assign | `a = a % b` |

```java
int score = 85;
score += 10;  // 95
score -= 5;   // 90
score *= 2;   // 180
score /= 3;   // 60
score %= 7;   // 4
```

---

## 3. Increment and Decrement

| Operator | Name | Behaviour |
|----------|------|-----------|
| `++a` | Prefix increment | Increment `a` first, then use the new value |
| `a++` | Postfix increment | Use the current value first, then increment `a` |
| `--a` | Prefix decrement | Decrement `a` first, then use the new value |
| `a--` | Postfix decrement | Use the current value first, then decrement `a` |

```java
int x = 5;
int y = ++x;  // x becomes 6, then y is assigned 6 → x=6, y=6

int a = 5;
int b = a++;  // b is assigned 5, then a becomes 6 → a=6, b=5
```

In a standalone statement where the result is not used, `++x` and `x++` are equivalent.

---

## 4. Operator Precedence

Operators are evaluated from highest to lowest precedence:

1. Postfix `++`, `--`
2. Prefix `++`, `--`, unary `+`, `-`
3. `*`, `/`, `%`
4. `+`, `-`
5. Assignment operators (`=`, `+=`, `-=`, etc.)

```java
int result = 10 + 5 * 2;    // 20 — multiplication runs before addition
int forced = (10 + 5) * 2;  // 30 — parentheses override precedence
```

When in doubt, use parentheses to make the intended order explicit.

---

## Common Mistakes

### Integer division when a decimal result is expected

Both operands are `int`, so `/` performs integer division. Storing the result in a `double` does not help — the division already happened as integer arithmetic.

```java
int a = 7, b = 2;
double result = a / b;          // 3.0, not 3.5 — integer division happened first

// Fix: cast at least one operand before dividing
double result = (double) a / b; // 3.5
double result = a / (double) b; // 3.5
double result = 7.0 / 2;        // 3.5 — double literal forces double arithmetic
```

### Division by zero

Integer division by zero throws an `ArithmeticException` at runtime. Floating-point division by zero produces `Infinity` or `NaN` — no exception, but the result is unusable.

```java
int result = 10 / 0; // ArithmeticException: / by zero at runtime

// Guard against it:
if (divisor != 0) {
    int result = dividend / divisor;
} else {
    System.out.println("Cannot divide by zero.");
}
```

### Confusing = (assignment) with == (comparison)

`=` assigns a value; `==` compares two values. Java will not compile `if (x = 10)` because an `int` expression is not a `boolean`, so this mistake is caught at compile time.

```java
int x = 5;
if (x = 10) { }   // Compile error: int cannot be converted to boolean
if (x == 10) { }  // Correct
```

---

## What's Next?

**Next:** [Type Casting](casting.md)
