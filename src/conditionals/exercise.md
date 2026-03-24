# Conditionals Practice Exercises

Conditionals are the mechanism by which a program makes decisions. An `if/else` chain evaluates a boolean expression and branches accordingly; a `switch` statement dispatches on a discrete value. Getting conditionals right means being precise about boundaries, handling every possible case, and choosing the right construct for the problem. The exercises below cover the most common patterns you will encounter in practice.

---

## Exercise 1: Age Category Classifier

Write a program that reads a name and an integer age from the user, then prints which age category the person belongs to using the following boundaries:

| Age range     | Category  |
|---------------|-----------|
| Less than 2   | Baby      |
| 2 through 12  | Child     |
| 13 through 19 | Teenager  |
| 20 through 59 | Adult     |
| 60 and above  | Senior    |

Requirements:
- Prompt the user for their name first, then their age.
- If the user enters a non-integer value for age, discard the input and prompt again until a valid integer is received. Use `Scanner.hasNextInt()` for this.
- Print the category once a valid age is entered (e.g., `You're an adult`).
- Close the `Scanner` before the program exits.

Test with: `1` (baby), `10` (child), `16` (teenager), `35` (adult), `72` (senior), and a non-numeric string (should re-prompt).

---

## Exercise 2: BMI Calculator

Write a program that calculates a user's Body Mass Index (BMI) and prints the corresponding category. BMI is calculated as:

```
BMI = mass (kg) / height (m)^2
```

The categories are:

| BMI range      | Category      |
|----------------|---------------|
| Below 18.5     | Underweight   |
| 18.5 to 24.9   | Normal weight |
| 25.0 to 29.9   | Overweight    |
| 30.0 and above | Obese         |

Requirements:
- Prompt the user for their name, height in metres, and mass in kilograms.
- Height must be between `0.1` and `3.0`; mass must be between `1` and `300`. Loop until valid values are entered.
- Print a labelled summary showing name, mass, height, BMI (formatted to 2 decimal places), and the category.

Example output for height `1.75` and mass `70`:

```
=====BMI Calculator=====
Name: Alex
Mass: 70.0 kg
Height: 1.75 m
BMI: 22.86
Result: Normal weight
```

---

## Exercise 3: Quadratic Equation Solver

Write a program that reads three integer coefficients `a`, `b`, and `c` for the quadratic equation `ax^2 + bx + c = 0` and determines the number of real roots using the discriminant.

```
discriminant = b^2 - 4ac
```

| Discriminant    | Outcome                         |
|-----------------|---------------------------------|
| Greater than 0  | Two distinct real roots         |
| Equal to 0      | One real solution (double root) |
| Less than 0     | No real roots                   |

Requirements:
- Read `a`, `b`, and `c` as integers from the user.
- If `a` is `0`, print an error message — the equation is not quadratic.
- For two roots, compute and print both using the quadratic formula with `Math.sqrt()`, formatted to 4 decimal places.
- For a double root, compute and print the single root.

Test with: `(1, -5, 6)` → two roots 3.0 and 2.0; `(1, -2, 1)` → one root 1.0; `(1, 1, 1)` → no real roots.

---

## Exercise 4: Rock, Paper, Scissors

Write a two-player Rock, Paper, Scissors game played at the console. Each player enters their choice (`rock`, `paper`, or `scissors`) and the program determines the winner.

Requirements:
- Read each player's choice as a `String` and convert to lowercase before comparing.
- If either player enters an invalid choice, print an error and ask that player to re-enter.
- Use a `switch` statement or switch expression to determine the outcome.
- Handle the three outcomes: Player 1 wins, Player 2 wins, and Draw.
- Print the result clearly showing both choices and the winner.

Example output:

```
Player 1, enter your choice: rock
Player 2, enter your choice: scissors
Player 1 chose: rock
Player 2 chose: scissors
Player 1 wins!
```

---

## Exercise 5: Traffic Light System

Write a program that simulates a traffic light controller. Given a light colour as input, print what drivers should do.

Requirements:
- Read the light colour from the user as a `String` (accept case-insensitive input).
- Use a `switch` statement to handle `"red"`, `"yellow"`, and `"green"`. Print a descriptive action for each:
  - `red` → `Stop. Wait for the light to change.`
  - `yellow` → `Slow down and prepare to stop.`
  - `green` → `Proceed if the road is clear.`
- For any unrecognised input, print an appropriate error message.
- After displaying the action, ask the user if they want to check another light colour (yes/no). Loop until the user enters `no`.

---

## Common Mistakes

### Off-by-one errors in else-if chains

When classifying ranges, the boundary value must belong to exactly one branch. A common mistake is using `<` and `<=` inconsistently.

```java
// Wrong: age 12 matches both the first AND second branch (unreachable code)
if (age < 12) {
    System.out.println("Child");
} else if (age < 19) { // age 12 hits here
    System.out.println("Teenager");
}

// Correct: boundaries are consistent and non-overlapping
if (age <= 12) {
    System.out.println("Child");
} else if (age <= 19) {
    System.out.println("Teenager");
}
```

### Missing break in a switch statement

Without a `break`, execution falls through to the next case. This is rarely intentional.

```java
// Wrong: "green" falls through and also prints the yellow and red actions
switch (colour) {
    case "green":
        System.out.println("Go");      // No break — falls through.
    case "yellow":
        System.out.println("Slow");    // Also executes for "green".
    case "red":
        System.out.println("Stop");    // Also executes for "green".
}

// Correct
switch (colour) {
    case "green":
        System.out.println("Go");
        break;
    case "yellow":
        System.out.println("Slow");
        break;
    case "red":
        System.out.println("Stop");
        break;
}
```

### Using = instead of == for comparison

`=` is assignment; `==` is comparison. Using `=` inside a condition silently assigns the value and then evaluates it as a boolean, which either always evaluates to true (for non-zero) or causes a compile error.

```java
int x = 5;

// Wrong: assigns 10 to x, does not compare
if (x = 10) { } // Compile error in Java — int is not boolean.

// Correct
if (x == 10) { }
```

For `String` comparisons, use `.equals()` rather than `==`, which compares object references, not content.

```java
String input = new String("yes");

if (input == "yes") { }          // May be false even when content matches.
if (input.equals("yes")) { }     // Correct — compares content.
```

---

Solutions for these exercises are in the `solutions/` subfolder.
