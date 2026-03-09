# Loops

## Learning Objectives

By the end of this guide, you will be able to:

- Use all three loop types (`for`, `while`, `do-while`) in your programs
- Choose the right loop for different situations
- Control loops with `break` and `continue`
- Avoid common loop mistakes
- Build projects using loops

---

## What Are Loops?

Imagine you want to print numbers from 1 to 1000. You could write:

```java
System.out.println(1);
System.out.println(2);
System.out.println(3);
// ... this would take a very long time
```

Or you could use a loop:

```java
for (int i = 1; i <= 1000; i++) {
    System.out.println(i);
}
```

**Loops let you repeat code without writing it over and over again.**

---

## 1. For Loops

Use a `for` loop when you know exactly how many times you want to repeat something.

### Basic Structure

```java
for (initialization; condition; update) {
    // Code to repeat
}
```

### Example 1: Count to 10

```java
public class CountToTen {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
        }
    }
}
```

**How it works:**
1. `int i = 1` — start with `i = 1`
2. `i <= 10` — keep going while `i` is 10 or less
3. `i++` — increment `i` after each iteration
4. The loop runs exactly 10 times

### Example 2: Multiplication Table

```java
public class MultiplicationTable {
    public static void main(String[] args) {
        int number = 5;

        System.out.println("5 times table:");
        for (int i = 1; i <= 10; i++) {
            System.out.println(number + " x " + i + " = " + (number * i));
        }
    }
}
```

### Enhanced For Loop (For-Each)

Use the enhanced for loop to iterate over arrays or collections when you do not need the index:

```java
public class ForEachExample {
    public static void main(String[] args) {
        String[] fruits = {"apple", "banana", "orange", "grape"};

        for (String fruit : fruits) {
            System.out.println("- " + fruit);
        }
    }
}
```

### Nested Loops

```java
public class StarPattern {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {        // Outer loop: rows
            for (int j = 1; j <= i; j++) {    // Inner loop: stars per row
                System.out.print("* ");
            }
            System.out.println();              // New line after each row
        }
    }
}
```

**Output:**
```
*
* *
* * *
* * * *
* * * * *
```

---

## 2. While Loops

Use a `while` loop when you do not know exactly how many iterations you need, but you know the stopping condition.

### Basic Structure

```java
while (condition) {
    // Code to repeat
    // Update the condition variable inside the loop body
}
```

### Example 1: Countdown

```java
public class Countdown {
    public static void main(String[] args) {
        int count = 10;

        while (count > 0) {
            System.out.println(count);
            count--;  // This changes the condition — never forget it
        }
        System.out.println("Blast off!");
    }
}
```

### Example 2: Sum Until the User Stops

```java
import java.util.Scanner;

public class SumNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        int number;

        System.out.println("Enter numbers to add (enter 0 to stop):");

        while (true) {
            System.out.print("Enter a number: ");
            number = scanner.nextInt();

            if (number == 0) {
                break;
            }

            sum += number;
            System.out.println("Current sum: " + sum);
        }

        System.out.println("Final sum: " + sum);
        scanner.close();
    }
}
```

### Example 3: Input Validation

```java
import java.util.Scanner;

public class AgeValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int age;

        while (true) {
            System.out.print("Enter your age (1-120): ");
            age = scanner.nextInt();

            if (age >= 1 && age <= 120) {
                System.out.println("Thank you! Your age is " + age);
                break;
            } else {
                System.out.println("That does not seem right. Please try again.");
            }
        }

        scanner.close();
    }
}
```

---

## 3. Do-While Loops

Use a `do-while` loop when you want to execute the body **at least once**, then check the condition.

### Basic Structure

```java
do {
    // Code to repeat
    // This always runs at least once
} while (condition);
```

### Example 1: Menu System

```java
import java.util.Scanner;

public class SimpleMenu {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Tell a joke");
            System.out.println("2. Random number");
            System.out.println("3. Current time");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Why don't scientists trust atoms? Because they make up everything!");
                    break;
                case 2:
                    System.out.println("Random number: " + (int)(Math.random() * 100));
                    break;
                case 3:
                    System.out.println("Current time: " + java.time.LocalTime.now());
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}
```

### Example 2: Password Checker

```java
import java.util.Scanner;

public class PasswordChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String password;

        do {
            System.out.print("Create a password (at least 6 characters): ");
            password = scanner.nextLine();

            if (password.length() < 6) {
                System.out.println("Too short. Please make it longer.");
            }
        } while (password.length() < 6);

        System.out.println("Password accepted.");
        scanner.close();
    }
}
```

---

## 4. Loop Control — Break and Continue

### Break

`break` exits the loop immediately, regardless of the condition.

```java
public class BreakExample {
    public static void main(String[] args) {
        System.out.println("Finding the first number divisible by 7:");

        for (int i = 1; i <= 100; i++) {
            if (i % 7 == 0) {
                System.out.println("Found: " + i);
                break;
            }
        }
    }
}
```

### Continue

`continue` skips the rest of the current iteration and moves to the next one.

```java
public class ContinueExample {
    public static void main(String[] args) {
        System.out.println("Odd numbers from 1 to 10:");

        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue;  // Skip even numbers
            }
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
```

### Labeled Break and Continue

When you have nested loops and need to break out of the outer loop:

```java
public class LabeledBreak {
    public static void main(String[] args) {
        outerLoop: for (int i = 1; i <= 3; i++) {
            System.out.println("Outer: " + i);

            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    System.out.println("Breaking out of both loops.");
                    break outerLoop;
                }
                System.out.println("  Inner: " + j);
            }
        }
        System.out.println("Done.");
    }
}
```

---

## 5. Common Mistakes

### Infinite Loops

```java
// Problem: i never changes, so the condition is always true
int i = 1;
while (i <= 10) {
    System.out.println(i);
    // Missing: i++;
}

// Correct
int i = 1;
while (i <= 10) {
    System.out.println(i);
    i++;
}
```

### Off-by-One Errors

```java
// Prints 9 numbers (1 through 9) — likely not what was intended
for (int i = 1; i < 10; i++) {
    System.out.println(i);
}

// Prints 10 numbers (1 through 10)
for (int i = 1; i <= 10; i++) {
    System.out.println(i);
}
```

### Variable Scope

```java
for (int i = 0; i < 5; i++) {
    int doubled = i * 2;
    System.out.println(doubled);
}

// Both i and doubled are out of scope here — the code below will not compile
// System.out.println(i);
// System.out.println(doubled);
```

---

## 6. Choosing the Right Loop

| Situation | Best Loop |
|-----------|-----------|
| You know exactly how many iterations are needed | `for` |
| You want to go through every element in an array or list | `for-each` |
| You do not know how many times, but know the stopping condition | `while` |
| You need the body to run at least once before checking | `do-while` |
| Building a menu system | `do-while` |
| Validating user input | `while` or `do-while` |

---

## 7. Practice Exercises

### Exercise 1: Number Squares

Write a program that asks the user for a number `n` and prints each number from 1 to `n` alongside its square.

```java
import java.util.Scanner;

public class NumberSquares {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many numbers? ");
        int count = scanner.nextInt();

        System.out.println("\nNumber\tSquare");
        System.out.println("------\t------");

        for (int i = 1; i <= count; i++) {
            System.out.println(i + "\t" + (i * i));
        }

        scanner.close();
    }
}
```

### Exercise 2: Sum Until 100

Add consecutive integers (1, 2, 3, ...) until the running total exceeds 100.

```java
public class SumUntil100 {
    public static void main(String[] args) {
        int sum = 0;
        int number = 1;
        int count = 0;

        while (sum <= 100) {
            sum += number;
            count++;
            System.out.println("Added " + number + ", sum = " + sum);
            number++;
        }

        System.out.println("It took " + count + " numbers to exceed 100.");
    }
}
```

### Exercise 3: Grade Calculator

Keep asking the user for grades until they enter -1, then print the average.

```java
import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double sum = 0;
        int count = 0;
        double grade;

        System.out.println("Enter grades (enter -1 to finish):");

        do {
            System.out.print("Enter grade: ");
            grade = scanner.nextDouble();

            if (grade != -1) {
                if (grade >= 0 && grade <= 100) {
                    sum += grade;
                    count++;
                } else {
                    System.out.println("Please enter a grade between 0 and 100.");
                }
            }
        } while (grade != -1);

        if (count > 0) {
            System.out.println("Average grade: " + String.format("%.1f", sum / count));
        } else {
            System.out.println("No valid grades entered.");
        }

        scanner.close();
    }
}
```

---

## 8. Projects

### Project 1: Number Guessing Game

```java
import java.util.Scanner;
import java.util.Random;

public class GuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int secretNumber = random.nextInt(100) + 1;  // 1 to 100
        int attempts = 0;
        int maxAttempts = 7;
        boolean won = false;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I'm thinking of a number between 1 and 100.");
        System.out.println("You have " + maxAttempts + " attempts.");

        while (attempts < maxAttempts && !won) {
            attempts++;
            System.out.print("\nAttempt " + attempts + ": Enter your guess: ");
            int guess = scanner.nextInt();

            if (guess == secretNumber) {
                won = true;
                System.out.println("Correct! You got it in " + attempts + " tries.");
            } else if (guess < secretNumber) {
                System.out.println("Too low.");
                if (secretNumber - guess <= 10) {
                    System.out.println("(You are getting close!)");
                }
            } else {
                System.out.println("Too high.");
                if (guess - secretNumber <= 10) {
                    System.out.println("(You are getting close!)");
                }
            }

            if (!won && attempts < maxAttempts) {
                System.out.println("Attempts remaining: " + (maxAttempts - attempts));
            }
        }

        if (!won) {
            System.out.println("\nGame over. The number was " + secretNumber + ".");
        }

        scanner.close();
    }
}
```

### Project 2: Simple Calculator with Menu

```java
import java.util.Scanner;

public class SimpleCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Simple Calculator");

        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add");
            System.out.println("2. Subtract");
            System.out.println("3. Multiply");
            System.out.println("4. Divide");
            System.out.println("5. Multiplication table");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1: {
                    System.out.print("First number: ");
                    double a = scanner.nextDouble();
                    System.out.print("Second number: ");
                    double b = scanner.nextDouble();
                    System.out.println("Result: " + (a + b));
                    break;
                }
                case 2: {
                    System.out.print("First number: ");
                    double a = scanner.nextDouble();
                    System.out.print("Second number: ");
                    double b = scanner.nextDouble();
                    System.out.println("Result: " + (a - b));
                    break;
                }
                case 3: {
                    System.out.print("First number: ");
                    double a = scanner.nextDouble();
                    System.out.print("Second number: ");
                    double b = scanner.nextDouble();
                    System.out.println("Result: " + (a * b));
                    break;
                }
                case 4: {
                    System.out.print("First number: ");
                    double a = scanner.nextDouble();
                    System.out.print("Second number: ");
                    double b = scanner.nextDouble();
                    if (b != 0) {
                        System.out.println("Result: " + (a / b));
                    } else {
                        System.out.println("Error: Cannot divide by zero.");
                    }
                    break;
                }
                case 5: {
                    System.out.print("Number: ");
                    int n = scanner.nextInt();
                    System.out.print("Rows: ");
                    int rows = scanner.nextInt();
                    for (int i = 1; i <= rows; i++) {
                        System.out.println(n + " x " + i + " = " + (n * i));
                    }
                    break;
                }
                case 0:
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        scanner.close();
    }
}
```

### Project 3: Factorial Calculator

```java
import java.util.Scanner;

public class FactorialCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Factorial Calculator");
        System.out.print("Enter a non-negative integer: ");
        int number = scanner.nextInt();

        if (number < 0) {
            System.out.println("Factorial is not defined for negative numbers.");
        } else if (number == 0) {
            System.out.println("0! = 1");
        } else {
            long factorial = 1;
            for (int i = number; i >= 1; i--) {
                factorial *= i;
            }
            System.out.println(number + "! = " + factorial);

            System.out.println("\nStep-by-step:");
            long running = 1;
            for (int i = 1; i <= number; i++) {
                running *= i;
                System.out.println("Step " + i + ": " + running);
            }
        }

        scanner.close();
    }
}
```

---

## 9. Quick Review

1. **What loop would you use to ask a user for their password until they enter a valid one?**
   A `do-while` loop — it guarantees the prompt appears at least once.

2. **What is the difference between `break` and `continue`?**
   - `break` exits the loop entirely.
   - `continue` skips the remainder of the current iteration and moves to the next one.

3. **Fill in the blanks to count from 5 to 15:**
   ```java
   for (int i = 5; i <= 15; i++) {
       System.out.println(i);
   }
   ```

4. **What is wrong with this code?**
   ```java
   int i = 0;
   while (i < 5) {
       System.out.println("Hello");
   }
   ```
   `i` is never incremented, so the condition is always `true` — this is an infinite loop.

5. **When should you use a for-each loop?**
   When you need to visit every element in an array or collection and do not need the index.

---

## 10. What's Next?

**Next:** [Methods](../methods/methods.md)

### Ideas for Further Practice

**Beginner:**
- Build a times table generator for any number the user enters
- Write a program that finds all prime numbers up to 100
- Create a simple text menu that performs different actions

**Intermediate:**
- Build a student grade management system
- Create a program that draws various ASCII patterns (triangles, diamonds)
- Implement a basic text-based tic-tac-toe game

**Advanced:**
- Write a program that can solve basic math equations entered as strings
- Build a simple inventory management system using arrays and loops
