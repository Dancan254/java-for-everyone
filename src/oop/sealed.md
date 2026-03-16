# Sealed Classes and Pattern Matching

Modern Java gives you two powerful tools for modelling closed hierarchies: **sealed classes** restrict which classes can extend a type, and **pattern matching** lets you inspect and extract values from those types without boilerplate casts. Together they make exhaustive type-based logic safe, concise, and verifiable at compile time.

---

## 1. The Problem They Solve

```
Think of it like this:
  A traditional class hierarchy is an open door — anyone can add a subclass at any time.
  A sealed hierarchy is a locked safe with a fixed set of known compartments.
  When the set is closed and known in advance, the compiler can verify that you have
  handled every case, the same way a switch on an enum can be verified for completeness.
```

Without sealed classes, a method that switches on a type must include a default branch or risk missing new subclasses added later. With a sealed hierarchy, the compiler knows every permitted subtype and can warn — or in a switch expression, require — that every case is covered.

---

## 2. Sealed Classes (Java 17)

A sealed class uses the `sealed` modifier and a `permits` clause that lists every direct subtype. Each permitted subtype must itself be declared `final`, `sealed`, or `non-sealed`.

```java
// A payment method can only be one of three things — the list is closed.
public sealed interface Payment permits CashPayment, CardPayment, CryptoPayment {}

public record CashPayment(double amount) implements Payment {}
public record CardPayment(String cardNumber, double amount) implements Payment {}
public record CryptoPayment(String walletAddress, double amount, String currency) implements Payment {}
```

Rules for permitted subtypes:
- `final` — no further subclasses allowed. Most leaf types in a sealed hierarchy are final.
- `sealed` — further subclasses are allowed, but must also be listed in their own `permits` clause.
- `non-sealed` — opens the type back up; any class can extend it. Use this deliberately when one branch of the hierarchy should be extensible.

```java
// A sealed abstract class example.
public abstract sealed class Shape permits Circle, Rectangle, Triangle {}

public final class Circle    extends Shape { final double radius; Circle(double r) { radius = r; } }
public final class Rectangle extends Shape { final double w, h; Rectangle(double w, double h) { this.w = w; this.h = h; } }
public final class Triangle  extends Shape { final double base, height; Triangle(double b, double h) { base = b; height = h; } }
```

---

## 3. Pattern Matching for instanceof (Java 16)

Before Java 16, testing an object's type and using it required a cast:

```java
// Old style: verbose and fragile.
Object obj = "hello";
if (obj instanceof String) {
    String s = (String) obj; // Explicit cast required.
    System.out.println(s.toUpperCase());
}
```

Pattern matching for `instanceof` combines the type test and the cast into one step. The binding variable (`s` below) is in scope only where the test is guaranteed to be true.

```java
// New style: the cast is gone.
Object obj = "hello";
if (obj instanceof String s) {
    System.out.println(s.toUpperCase()); // Output: HELLO
}
```

The binding variable cannot be used outside the scope where the pattern is known to match:

```java
Object obj = "hello";
if (!(obj instanceof String s)) {
    return; // s is NOT in scope here — obj is not a String in this branch.
}
System.out.println(s.length()); // s IS in scope here — the non-String case returned early.
```

---

## 4. Pattern Matching in switch (Java 21)

Switch expressions and statements support type patterns, guarded patterns, and `null` handling. When the selector type is a sealed type, the compiler verifies that every permitted subtype is covered — no `default` branch required.

### Basic type patterns

```java
// Sealed hierarchy from Section 2.
static double calculateFee(Payment payment) {
    return switch (payment) {
        case CashPayment   cash   -> 0.0;               // No fee for cash.
        case CardPayment   card   -> card.amount() * 0.02; // 2% processing fee.
        case CryptoPayment crypto -> crypto.amount() * 0.01; // 1% network fee.
        // No default needed — the compiler knows these three cases cover all of Payment.
    };
}
```

### Guarded patterns — adding conditions with `when`

A `when` clause (Java 21) adds a boolean condition to a type pattern. If the condition is false the case is skipped and the next case is tried.

```java
static String describePayment(Payment payment) {
    return switch (payment) {
        case CashPayment cash when cash.amount() > 10_000 ->
                "Large cash payment — requires reporting.";
        case CashPayment cash ->
                "Cash payment of $" + cash.amount();
        case CardPayment card ->
                "Card ending in " + card.cardNumber().substring(card.cardNumber().length() - 4);
        case CryptoPayment crypto ->
                crypto.amount() + " " + crypto.currency() + " to " + crypto.walletAddress();
    };
}
```

### Null handling in switch

Traditional switch throws `NullPointerException` if the selector is `null`. Pattern switch can handle `null` explicitly:

```java
static String describe(Payment payment) {
    return switch (payment) {
        case null          -> "No payment provided.";
        case CashPayment c -> "Cash: $" + c.amount();
        case CardPayment c -> "Card: " + c.cardNumber();
        case CryptoPayment c -> "Crypto: " + c.currency();
    };
}
```

---

## 5. Deconstruction Patterns (Java 21 Preview / Java 22+)

Record patterns let you match a record type and destructure its components in one step.

```java
record Point(int x, int y) {}

static String describe(Object obj) {
    return switch (obj) {
        case Point(int x, int y) when x == 0 && y == 0 -> "Origin";
        case Point(int x, int y) when x == 0            -> "On Y-axis at " + y;
        case Point(int x, int y) when y == 0            -> "On X-axis at " + x;
        case Point(int x, int y)                        -> "Point(" + x + ", " + y + ")";
        default                                         -> "Not a point";
    };
}
```

The components `x` and `y` are bound directly without calling `point.x()` or `point.y()`.

---

## 6. Complete Example: Expression Tree

A classic use of sealed classes is an expression tree where every node is one of a closed set of types. The compiler guarantees that evaluation handles all cases.

```java
// Sealed expression hierarchy — every expression is one of these four types.
sealed interface Expr permits Num, Add, Mul, Neg {}

record Num(double value)       implements Expr {}
record Add(Expr left, Expr right) implements Expr {}
record Mul(Expr left, Expr right) implements Expr {}
record Neg(Expr expr)           implements Expr {}
```

```java
public class ExprEval {

    // Evaluate an expression to a double.
    // The switch is exhaustive — no default required — because Expr is sealed.
    static double eval(Expr expr) {
        return switch (expr) {
            case Num(double v)       -> v;
            case Add(Expr l, Expr r) -> eval(l) + eval(r);
            case Mul(Expr l, Expr r) -> eval(l) * eval(r);
            case Neg(Expr e)         -> -eval(e);
        };
    }

    // Pretty-print the expression.
    static String print(Expr expr) {
        return switch (expr) {
            case Num(double v)       -> String.valueOf(v);
            case Add(Expr l, Expr r) -> "(" + print(l) + " + " + print(r) + ")";
            case Mul(Expr l, Expr r) -> "(" + print(l) + " * " + print(r) + ")";
            case Neg(Expr e)         -> "(-" + print(e) + ")";
        };
    }

    public static void main(String[] args) {
        // Represents: (3 + 4) * (-2)
        Expr expression = new Mul(
                new Add(new Num(3), new Num(4)),
                new Neg(new Num(2))
        );

        System.out.println(print(expression));  // Output: ((3.0 + 4.0) * (-2.0))
        System.out.println(eval(expression));   // Output: -14.0
    }
}
```

---

## 7. Common Mistakes

### Missing cases in a sealed switch

If you add a new permitted subtype without updating every switch on that type, the compiler will report an error (for switch expressions) or a warning. This is the feature, not a bug — the compiler is protecting you from runtime surprises.

```java
// If BankTransfer is added to the permits list of Payment,
// every sealed switch over Payment immediately fails to compile until updated.
// public record BankTransfer(String iban, double amount) implements Payment {}
```

### Using default to suppress exhaustiveness checking

A `default` branch in a sealed switch silences the compiler's exhaustiveness check. If a new subtype is added later, the `default` branch silently handles it instead of alerting you.

```java
// Avoid: adding default defeats the purpose of sealing.
static double fee(Payment p) {
    return switch (p) {
        case CashPayment c  -> 0.0;
        case CardPayment c  -> c.amount() * 0.02;
        default             -> 0.0; // New CryptoPayment silently gets 0 fee — probably wrong.
    };
}
```

### Confusing non-sealed with non-final

`non-sealed` explicitly reopens a branch of the hierarchy for extension. It is a deliberate decision, not the same as forgetting to seal something. Use it only when one branch must be extensible by external code.

---

## 8. Key Takeaways

- A `sealed` class or interface restricts which classes can extend or implement it, using a `permits` clause
- Each permitted subtype must be `final` (no further extension), `sealed` (controlled extension), or `non-sealed` (open extension)
- Pattern matching for `instanceof` (Java 16) combines the type test and cast into one binding: `if (obj instanceof String s)`
- Switch pattern matching (Java 21) dispatches on types; when the selector is a sealed type the compiler verifies every case is covered
- `when` clauses add boolean guards to type patterns: `case CardPayment c when c.amount() > 1000`
- Record patterns (Java 21+) destructure records inline: `case Point(int x, int y)`
- Never add a `default` branch to a sealed switch unless you intentionally want new subtypes to fall through silently

---

Sealed classes and pattern matching work best together. Sealing defines a closed set of types; pattern matching in switch provides safe, exhaustive dispatch over that set. The combination makes type-based business logic as verifiable at compile time as enum-based logic has always been.
