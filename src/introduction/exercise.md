# Introduction Practice Exercises

Every Java program is built on the same small set of primitives: values, operators, and input. These exercises build fluency with Java's eight primitive types, arithmetic and assignment operators, type casting, the String API, and the Scanner class. Work through each set in order before moving to the next.

---

## Exercise Set 1: Primitive Types and Variables

### Exercise 1.1: Declare All Eight Primitives

Declare one variable of each primitive type: `byte`, `short`, `int`, `long`, `float`, `double`, `char`, and `boolean`. Assign a meaningful value to each and print a labeled line for every variable, for example:

```
byte:    120
short:   30000
int:     2_000_000
long:    9_000_000_000
float:   3.14
double:  2.718281828
char:    J
boolean: true
```

Note how `long` literals require an `L` suffix and `float` literals require an `F` suffix. Include both in your declarations.

### Exercise 1.2: Integer Overflow

Declare a `byte` variable and assign it the value `127` (the maximum for `byte`). Then add `1` to it using a cast: `(byte)(value + 1)`. Print the result and explain in a comment why the output is `-128` rather than `128`.

Repeat the experiment with `int`: assign `Integer.MAX_VALUE`, add `1` using a cast, and print the result. Print both `Integer.MAX_VALUE` and the overflowed value on labeled lines.

### Exercise 1.3: Integer Division vs. Double Division

Declare `int a = 7` and `int b = 2`. Compute `a / b` and print the result. Then compute `(double) a / b` and print that result.

Explain in a comment why the first expression produces `3` instead of `3.5`, and how casting one operand to `double` before the division changes the result. Also compute `7.0 / 2` and `7 / 2.0` and confirm both yield `3.5`.

---

## Exercise Set 2: Arithmetic and Assignment Operators

### Exercise 2.1: Temperature Converter

Write a method `celsiusToFahrenheit(double celsius)` that returns the equivalent Fahrenheit temperature using the formula `F = (C * 9 / 5) + 32`. Call it from `main` with the following values and print labeled results:

- 0 C (freezing point of water)
- 100 C (boiling point of water)
- -40 C (the point where both scales meet)
- 37 C (normal human body temperature)

### Exercise 2.2: Simple Interest

Write a method `simpleInterest(double principal, double rate, int years)` that returns the total interest earned using the formula `I = P * R * T`. Call it from `main` with at least three combinations of principal, annual rate, and years. Print the principal, rate, years, and computed interest on labeled lines for each call.

### Exercise 2.3: Area and Perimeter of a Circle

Write a method `circleArea(double radius)` and a method `circlePerimeter(double radius)`. Use `Math.PI` for pi. Call both from `main` for radii of `5.0`, `10.0`, and `2.5`. Print radius, area, and perimeter for each, formatted to two decimal places using `String.format("%.2f", value)`.

---

## Exercise Set 3: Type Casting and Conversion

### Exercise 3.1: Widening Conversion

Declare an `int` variable with the value `42`. Assign it to a `long` variable without a cast. Assign that `long` to a `double` variable without a cast. Print all three values with their types labeled. Explain in a comment why no explicit cast is needed for widening conversions and what "widening" means.

### Exercise 3.2: Narrowing Conversion

Declare a `double` with the value `9.99`. Cast it to an `int` and print the result. Explain in a comment why the result is `9` and not `10` (truncation, not rounding).

Then declare a `double` with the value `300.75`. Cast it to a `byte` and print the result. Explain the two-step process in a comment: first truncation to `300`, then overflow wrapping because `300` exceeds `byte`'s maximum of `127`.

### Exercise 3.3: char and int Relationship

Declare `char letter = 'A'`. Print `letter` as a character and then as an integer using `(int) letter`. Verify the output is `65`.

Then add `1` to it with `(char)(letter + 1)` and print the result. Verify the output is `B`.

Write a short loop that prints the characters `A` through `Z` and their corresponding integer values side by side. Use `(char)('A' + i)` where `i` goes from `0` to `25`.

---

## Exercise Set 4: String Methods

### Exercise 4.1: Basic String Inspection

Declare `String sentence = "  The quick brown fox jumps over the lazy dog.  "` (with leading and trailing spaces).

Use the following methods and print each result on a labeled line:
- `trim()` — strip leading and trailing whitespace
- `length()` on the trimmed string
- `toUpperCase()` and `toLowerCase()` on the trimmed string
- `charAt(4)` on the trimmed string
- `indexOf("fox")` on the trimmed string
- `contains("cat")` — print whether the sentence contains the word "cat"

### Exercise 4.2: Substring and Replace

Using the trimmed sentence from Exercise 4.1:

1. Extract the word `"quick"` using `substring(startIndex, endIndex)`. Print it.
2. Extract everything from the word `"brown"` to the end using a single-argument `substring(startIndex)`. Print it.
3. Replace `"fox"` with `"wolf"` using `replace`. Print the result.
4. Replace every space with an underscore using `replace`. Print the result.

### Exercise 4.3: Split, Join, and Format

1. Declare `String csv = "Alice,30,Engineer,London"`. Split on `","` and store the result in a `String[]`. Print the length of the array and each element on its own line.
2. Join those same elements back into a single string using `String.join(" | ", parts)` and print the result.
3. Use `String.format` to build and print a formatted sentence: `"Name: Alice | Age: 30 | Job: Engineer | City: London"` using the array elements as arguments.

---

## Exercise Set 5: Scanner Input

### Exercise 5.1: Reading Name, Age, and Height

Write a program that prompts the user for:
1. Their full name (a `String`, read with `nextLine()`)
2. Their age in years (an `int`, read with `nextInt()`)
3. Their height in meters (a `double`, read with `nextDouble()`)

After reading all three values, print a summary:

```
Name:   Jane Doe
Age:    28
Height: 1.72 m
```

Pay attention to the `nextInt()` / `nextDouble()` and `nextLine()` ordering: call `scanner.nextLine()` after each numeric read to consume the trailing newline before reading the next line of text.

### Exercise 5.2: BMI Calculator

Extend the program from Exercise 5.1. After reading weight in kilograms (`double`) and height in meters (`double`), compute the Body Mass Index using the formula `BMI = weight / (height * height)`. Print the BMI formatted to one decimal place and the corresponding category:

| BMI range       | Category      |
|-----------------|---------------|
| Below 18.5      | Underweight   |
| 18.5 to 24.9    | Normal weight |
| 25.0 to 29.9    | Overweight    |
| 30.0 and above  | Obese         |

Use a series of `if / else if / else` statements to determine and print the category.

### Exercise 5.3: Reading Multiple Values on One Line

Write a program that prompts: `"Enter three integers separated by spaces: "`. Read the three integers using three consecutive calls to `scanner.nextInt()` on the same line. Print their sum, product, and average (as a `double`). Format the average to two decimal places.

Then repeat with `"Enter two words separated by a space: "`. Read both words with `nextLine()` after splitting — or read them separately with `next()`. Print them, their combined length, and which one comes first alphabetically using `compareTo`.

---

## Mini-Project 1: Unit Converter

Build a console program that converts a single measurement into several other units simultaneously.

**Requirements:**
- Prompt the user to enter a distance in metres as a `double`. Print the equivalent value in kilometres, centimetres, millimetres, miles (`m / 1609.344`), feet (`m * 3.28084`), and inches (`m * 39.3701`).
- Prompt the user to enter a temperature in Celsius as a `double`. Print the equivalent value in Fahrenheit (`F = (C * 9.0 / 5.0) + 32`) and Kelvin (`K = C + 273.15`).
- Prompt the user to enter a mass in kilograms as a `double`. Print the equivalent in grams, pounds (`kg * 2.20462`), and ounces (`kg * 35.274`).
- Format all output to three decimal places using `String.format("%.3f", value)`.
- Organise the output into labelled sections using a header line for each measurement type.

**Example output (for 1.5 m, 20 C, 2 kg):**
```
=== Distance ===
Metres:      1.500
Kilometres:  0.002
Centimetres: 150.000
Millimetres: 1500.000
Miles:       0.001
Feet:        4.921
Inches:      59.055

=== Temperature ===
Celsius:     20.000
Fahrenheit:  68.000
Kelvin:      293.150

=== Mass ===
Kilograms:   2.000
Grams:       2000.000
Pounds:      4.409
Ounces:      70.548
```

---

## Mini-Project 2: Shopping Receipt Generator

Build a console program that calculates a shopping receipt for three items.

**Requirements:**
- Prompt the user to enter the name and price of three items. Read each name with `nextLine()` and each price with `nextDouble()` (remember to consume the trailing newline after each `nextDouble()` before reading the next name).
- Compute the subtotal (sum of the three prices), a tax amount at 8.5% of the subtotal, and the final total.
- Print a formatted receipt that:
  - Displays each item name left-aligned in a 20-character field and its price right-aligned to two decimal places using `String.format("%-20s $%6.2f", name, price)`.
  - Displays the subtotal, tax, and total on separate labelled lines.
  - Uses a border of `=` characters at the top and bottom and a `—` divider above the totals.

**Example output:**
```
========================================
           SHOPPING RECEIPT
========================================
Coffee Beans            $ 12.99
Notebook                $  4.50
USB Cable               $ 15.00
----------------------------------------
Subtotal:               $ 32.49
Tax (8.5%):             $  2.76
Total:                  $ 35.25
========================================
```

---

## Common Mistakes

### Confusing integer division with floating-point division

When both operands are `int`, the `/` operator performs integer division and discards the remainder. Many beginners expect `7 / 2` to produce `3.5`. It produces `3`. Cast at least one operand to `double` before dividing when a fractional result is needed.

```java
// Produces 3, not 3.5 — both operands are int.
int result = 7 / 2;

// Produces 3.5 — the cast promotes the division to double.
double result = (double) 7 / 2;
```

### Integer overflow is silent

Java does not throw an exception when an integer overflows. The value wraps around silently. This means a loop that expects to reach `Integer.MAX_VALUE + 1` as a stopping condition may run forever, and an accumulator may silently produce a negative result.

```java
int max = Integer.MAX_VALUE;
int overflowed = max + 1; // No exception — silently becomes -2147483648.
System.out.println(overflowed); // -2147483648
```

Use `long` when values might exceed the `int` range. Use `Math.addExact` if you need overflow detection.

### Scanner input ordering: nextInt() leaves a newline

`nextInt()` reads the integer token but leaves the newline character `'\n'` in the buffer. The next call to `nextLine()` immediately reads that leftover newline and returns an empty string instead of waiting for the user to type.

```java
Scanner scanner = new Scanner(System.in);
int age = scanner.nextInt();
// The '\n' from pressing Enter is still in the buffer.
String name = scanner.nextLine(); // Reads "" instead of the user's name.
```

Always call `scanner.nextLine()` after `nextInt()` or `nextDouble()` to consume the leftover newline before reading the next line of text.

```java
int age = scanner.nextInt();
scanner.nextLine(); // Discard the trailing newline.
String name = scanner.nextLine(); // Now reads correctly.
```

### String comparison with == instead of .equals()

The `==` operator tests whether two references point to the same object in memory. It does not compare the content of the strings. Two `String` objects with identical characters will return `false` from `==` unless they happen to share the same memory address (which only occurs for string literals in the constant pool).

```java
String a = new String("hello");
String b = new String("hello");

System.out.println(a == b);       // false — different objects.
System.out.println(a.equals(b));  // true  — same content.
```

Always use `.equals()` to compare string contents and `.equalsIgnoreCase()` when case should not matter.

---

Solutions for these exercises are in the `solutions/` subfolder.
