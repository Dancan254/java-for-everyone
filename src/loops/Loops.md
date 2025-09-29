# Java Loops - Beginner-Friendly Guide

## What You'll Learn
By the end of this guide, you will be able to:
- Use all three types of loops (for, while, do-while) in your programs
- Choose the right loop for different situations
- Control loops with break and continue statements
- Avoid common loop mistakes
- Build fun projects using loops!

---

## What Are Loops?

Imagine you want to print numbers from 1 to 1000. You could write:
```java
System.out.println(1);
System.out.println(2);
System.out.println(3);
// ... this would take forever!
```

Or you could use a loop:
```java
for (int i = 1; i <= 1000; i++) {
    System.out.println(i);
}
```

**Loops let you repeat code without writing it over and over again!**

---

## 1. For Loops - "I know how many times"

Use for loops when you know exactly how many times you want to repeat something.

### Basic Structure
```java
for (start; condition; update) {
    // Code to repeat
}
```

### Example 1: Count to 10
```java
public class CountToTen {
    public static void main(String[] args) {
        System.out.println("Counting to 10:");
        
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
        }
    }
}
```

**What happens:**
1. `int i = 1` - Start with i = 1
2. `i <= 10` - Keep going while i is 10 or less
3. `i++` - Add 1 to i after each round
4. The loop runs 10 times!

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
Perfect for going through arrays or lists!

```java
public class ForEachExample {
    public static void main(String[] args) {
        String[] fruits = {"apple", "banana", "orange", "grape"};
        
        System.out.println("My favorite fruits:");
        for (String fruit : fruits) {
            System.out.println("- " + fruit);
        }
    }
}
```

### Nested Loops - Loops inside loops!
```java
public class StarPattern {
    public static void main(String[] args) {
        System.out.println("Star pyramid:");
        
        for (int i = 1; i <= 5; i++) {           // Outer loop: rows
            for (int j = 1; j <= i; j++) {       // Inner loop: stars per row
                System.out.print("* ");
            }
            System.out.println();                // New line after each row
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

## 2. While Loops - "Keep going until..."

Use while loops when you don't know exactly how many times to repeat, but you know when to stop.

### Basic Structure
```java
while (condition) {
    // Code to repeat
    // Don't forget to change the condition!
}
```

### Example 1: Countdown
```java
public class Countdown {
    public static void main(String[] args) {
        int count = 10;
        
        System.out.println("Countdown:");
        while (count > 0) {
            System.out.println(count);
            count--;  // Very important! This changes the condition
        }
        System.out.println("Blast off! 🚀");
    }
}
```

### Example 2: Sum Until Limit
```java
import java.util.Scanner;

public class SumNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        int number;
        
        System.out.println("Enter numbers to add (enter 0 to stop):");
        
        while (true) {  // This runs forever until we break out
            System.out.print("Enter a number: ");
            number = scanner.nextInt();
            
            if (number == 0) {
                break;  // Stop the loop!
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
                break;  // Valid input, exit loop
            } else {
                System.out.println("That doesn't seem right. Try again!");
            }
        }
        
        scanner.close();
    }
}
```

---

## 3. Do-While Loops - "Do this, then check"

Use do-while loops when you want to do something at least once, then check if you should continue.

### Basic Structure
```java
do {
    // Code to repeat
    // This always runs at least once!
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
            System.out.println("\n--- Fun Menu ---");
            System.out.println("1. Tell a joke");
            System.out.println("2. Random number");
            System.out.println("3. Current time");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.println("Why don't scientists trust atoms? Because they make up everything! 😄");
                    break;
                case 2:
                    System.out.println("Random number: " + (int)(Math.random() * 100));
                    break;
                case 3:
                    System.out.println("Current time: " + java.time.LocalTime.now());
                    break;
                case 0:
                    System.out.println("Goodbye! 👋");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
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
                System.out.println("Too short! Make it longer.");
            }
        } while (password.length() < 6);
        
        System.out.println("Great password! ✅");
        scanner.close();
    }
}
```

---

## 4. Loop Control - Break and Continue

Sometimes you need to control your loops in special ways.

### Break - "Stop the loop right now!"
```java
public class BreakExample {
    public static void main(String[] args) {
        System.out.println("Finding the first number divisible by 7:");
        
        for (int i = 1; i <= 100; i++) {
            if (i % 7 == 0) {
                System.out.println("Found it: " + i);
                break;  // Stop looking, we found one!
            }
        }
    }
}
```

### Continue - "Skip this round, go to the next!"
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

### Advanced: Labeled Break and Continue
When you have nested loops and want to break out of the outer one:

```java
public class LabeledBreak {
    public static void main(String[] args) {
        outerLoop: for (int i = 1; i <= 3; i++) {
            System.out.println("Outer loop: " + i);
            
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    System.out.println("Breaking out of everything!");
                    break outerLoop;  // Break out of the outer loop
                }
                System.out.println("  Inner loop: " + j);
            }
        }
        System.out.println("All done!");
    }
}
```

---

## 5. Common Mistakes and How to Avoid Them

### 1. Infinite Loops 😱
```java
// BAD - This never stops!
int i = 1;
while (i <= 10) {
    System.out.println(i);
    // Forgot to change i!
}

// GOOD - This stops after 10 times
int i = 1;
while (i <= 10) {
    System.out.println(i);
    i++;  // Don't forget this!
}
```

### 2. Off-by-One Errors
```java
// BAD - Only prints 9 numbers (1 to 9)
for (int i = 1; i < 10; i++) {
    System.out.println(i);
}

// GOOD - Prints 10 numbers (1 to 10)
for (int i = 1; i <= 10; i++) {
    System.out.println(i);
}
```

### 3. Variable Scope
```java
public class VariableScope {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            int doubled = i * 2;
            System.out.println(doubled);
        }
        
        // These won't work - variables are gone after the loop!
        // System.out.println(i);       // Error!
        // System.out.println(doubled); // Error!
    }
}
```

---

## 6. Which Loop Should I Use?

| Situation | Best Loop | Why |
|-----------|-----------|-----|
| I know exactly how many times | **for** | Clear start and end |
| I want to go through an array/list | **for-each** | Simple and clean |
| I don't know how many times, but I know when to stop | **while** | Flexible condition |
| I need to do something at least once | **do-while** | Guarantees one execution |
| I'm making a menu system | **do-while** | User sees menu at least once |
| I'm validating user input | **while** or **do-while** | Keep asking until correct |

---

## 7. Practice Exercises

### Exercise 1: Number Squares
Write a program that asks the user for a number and prints that many numbers with their squares.

```java
import java.util.Scanner;

public class NumberSquares {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("How many numbers would you like? ");
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
Create a program that adds consecutive numbers (1, 2, 3, 4...) until the sum exceeds 100.

```java
public class SumUntil100 {
    public static void main(String[] args) {
        int sum = 0;
        int number = 1;
        int count = 0;
        
        System.out.println("Adding numbers until sum > 100:");
        
        while (sum <= 100) {
            sum += number;
            count++;
            System.out.println("Added " + number + ", sum = " + sum);
            number++;
        }
        
        System.out.println("\nIt took " + count + " numbers to exceed 100!");
    }
}
```

### Exercise 3: Grade Calculator
Make a program that keeps asking for grades until the user enters -1, then shows the average.

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
                    System.out.println("Please enter a grade between 0 and 100!");
                }
            }
        } while (grade != -1);
        
        if (count > 0) {
            double average = sum / count;
            System.out.println("Average grade: " + String.format("%.1f", average));
        } else {
            System.out.println("No valid grades entered.");
        }
        
        scanner.close();
    }
}
```

---

## 8. Fun Projects

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
        
        System.out.println("🎯 Welcome to the Number Guessing Game!");
        System.out.println("I'm thinking of a number between 1 and 100.");
        System.out.println("You have " + maxAttempts + " attempts. Good luck!");
        
        while (attempts < maxAttempts && !won) {
            attempts++;
            System.out.print("\nAttempt " + attempts + ": Enter your guess: ");
            int guess = scanner.nextInt();
            
            if (guess == secretNumber) {
                won = true;
                System.out.println("🎉 Amazing! You got it in " + attempts + " tries!");
            } else if (guess < secretNumber) {
                System.out.println("📈 Too low! Go higher!");
                
                // Give hints
                if (secretNumber - guess <= 10) {
                    System.out.println("💡 You're getting close!");
                }
            } else {
                System.out.println("📉 Too high! Go lower!");
                
                // Give hints
                if (guess - secretNumber <= 10) {
                    System.out.println("💡 You're getting close!");
                }
            }
            
            if (!won && attempts < maxAttempts) {
                int remaining = maxAttempts - attempts;
                System.out.println("You have " + remaining + " attempts left.");
            }
        }
        
        if (!won) {
            System.out.println("\n😔 Game over! The number was " + secretNumber);
            System.out.println("Don't worry, you'll get it next time!");
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
        
        System.out.println("🧮 Welcome to Simple Calculator!");
        
        do {
            System.out.println("\n--- Calculator Menu ---");
            System.out.println("1. Add two numbers");
            System.out.println("2. Subtract two numbers");
            System.out.println("3. Multiply two numbers");
            System.out.println("4. Divide two numbers");
            System.out.println("5. Create multiplication table");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter first number: ");
                    double add1 = scanner.nextDouble();
                    System.out.print("Enter second number: ");
                    double add2 = scanner.nextDouble();
                    System.out.println("Result: " + add1 + " + " + add2 + " = " + (add1 + add2));
                    break;
                    
                case 2:
                    System.out.print("Enter first number: ");
                    double sub1 = scanner.nextDouble();
                    System.out.print("Enter second number: ");
                    double sub2 = scanner.nextDouble();
                    System.out.println("Result: " + sub1 + " - " + sub2 + " = " + (sub1 - sub2));
                    break;
                    
                case 3:
                    System.out.print("Enter first number: ");
                    double mul1 = scanner.nextDouble();
                    System.out.print("Enter second number: ");
                    double mul2 = scanner.nextDouble();
                    System.out.println("Result: " + mul1 + " × " + mul2 + " = " + (mul1 * mul2));
                    break;
                    
                case 4:
                    System.out.print("Enter first number: ");
                    double div1 = scanner.nextDouble();
                    System.out.print("Enter second number: ");
                    double div2 = scanner.nextDouble();
                    
                    if (div2 != 0) {
                        System.out.println("Result: " + div1 + " ÷ " + div2 + " = " + (div1 / div2));
                    } else {
                        System.out.println("Error: Cannot divide by zero!");
                    }
                    break;
                    
                case 5:
                    System.out.print("Enter a number for multiplication table: ");
                    int tableNum = scanner.nextInt();
                    System.out.print("How many rows? ");
                    int rows = scanner.nextInt();
                    
                    System.out.println("\nMultiplication Table for " + tableNum + ":");
                    for (int i = 1; i <= rows; i++) {
                        System.out.println(tableNum + " × " + i + " = " + (tableNum * i));
                    }
                    break;
                    
                case 0:
                    System.out.println("Thanks for using the calculator! Goodbye! 👋");
                    break;
                    
                default:
                    System.out.println("Invalid choice! Please try again.");
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
        
        System.out.println("🔢 Factorial Calculator!");
        System.out.println("(Factorial of n = n × (n-1) × (n-2) × ... × 1)");
        
        System.out.print("\nEnter a positive number: ");
        int number = scanner.nextInt();
        
        if (number < 0) {
            System.out.println("Factorial is not defined for negative numbers!");
        } else if (number == 0) {
            System.out.println("0! = 1");
        } else {
            long factorial = 1;
            
            System.out.print(number + "! = ");
            
            // Show the calculation step by step
            for (int i = number; i >= 1; i--) {
                System.out.print(i);
                if (i > 1) {
                    System.out.print(" × ");
                }
                factorial *= i;
            }
            
            System.out.println(" = " + factorial);
            
            // Show step-by-step calculation
            System.out.println("\nStep-by-step:");
            long stepResult = 1;
            for (int i = 1; i <= number; i++) {
                stepResult *= i;
                System.out.println("Step " + i + ": " + stepResult);
            }
        }
        
        scanner.close();
    }
}
```

---

## 9. Quick Review Quiz

Test your understanding! Try to answer these before looking at the solutions:

1. **What type of loop would you use if you want to ask a user for their password and keep asking until they get it right?**
   <details>
   <summary>Answer</summary>

   do-while loop or while loop. do-while guarantees you ask at least once.
   </details>

2. **What's the difference between break and continue?**
   <details>
   <summary>Answer</summary>

    - `break`: Stops the entire loop
    - `continue`: Skips the rest of this round and goes to the next iteration
   </details>

3. **Fill in the blanks to count from 5 to 15:**
   ```java
   for (int i = ____; i <= ____; i____) {
       System.out.println(i);
   }
   ```
   <details>
   <summary>Answer</summary>

   ```java
   for (int i = 5; i <= 15; i++) {
       System.out.println(i);
   }
   ```
   </details>

4. **What's wrong with this code?**
   ```java
   int i = 0;
   while (i < 5) {
       System.out.println("Hello");
   }
   ```
   <details>
   <summary>Answer</summary>

   It's an infinite loop! We never increment `i`, so it stays 0 forever.
   </details>

5. **When should you use a for-each loop?**
   <details>
   <summary>Answer</summary>

   When you want to go through every item in an array or collection and you don't need to know the index position.
   </details>

---

## 10. What's Next?

Congratulations! You now know how to use loops in Java. Here are some ideas for further practice:

### Easy Projects
- Create a times table generator for any number
- Make a simple text-based adventure game with menus
- Build a program that finds all prime numbers up to 100

### Medium Projects
- Create a student grade management system
- Build a simple text-based tic-tac-toe game
- Make a program that draws ASCII art patterns

### Challenge Projects
- Create a text-based RPG with combat system
- Build a simple inventory management system
- Make a program that can solve basic math equations

### Keep Learning
- Learn about arrays and how loops work with them
- Explore methods (functions) and how to use loops inside them
- Study object-oriented programming and loops in classes

---

## Tips for Success

1. **Start small** - Begin with simple counting loops before trying complex projects
2. **Draw it out** - Sketch what you want your loop to do before coding
3. **Test frequently** - Run your code often to catch mistakes early
4. **Use descriptive variable names** - `counter` is better than `i` for beginners
5. **Comment your code** - Explain what each loop does
6. **Practice daily** - Even 15 minutes of loop practice helps!

Remember: Every programmer started where you are now. Keep practicing, stay curious, and don't be afraid to make mistakes - that's how you learn! 🚀

Happy coding! 🎉