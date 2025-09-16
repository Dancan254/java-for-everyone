# Java Loops - Guide

## Learning Objectives
By the end of this session, you will be able to:
- Implement all three loop types (for, while, do-while) effectively
- Choose the appropriate loop construct for different scenarios
- Use loop control statements (break, continue) strategically
- Prevent infinite loops through proper condition management
- Understand variable scope within loop contexts
- Comprehend the formal specifications and evolution of loop constructs in Java

---

## 1. Historical Context and Evolution

### Java Loop History Timeline

**Java 1.0 (1996) - Original Loop Constructs:**
- **For loops:** Basic C-style for loops with three parts (init; condition; update)
- **While loops:** Pre-test loops inherited from C/C++
- **Do-while loops:** Post-test loops ensuring at least one execution
- **Break/Continue:** Loop control statements for early exit and iteration skipping

**Java 5 (J2SE 1.5, 2004) - Enhanced For Loop:**
- Enhanced for loops allow convenient iteration over collections, without the need for an explicitly defined iterator
- **JSR 201 specification:** Part of "Tiger" release major language enhancements
- **Motivation:** This reduces the need for boilerplate iteration code and the corresponding opportunities for errors
- **Alternative names:** "for-each" loop, enhanced for statement

### Language Design Rationale
Java's loop constructs were designed with several principles:
1. **Familiarity:** C/C++ style syntax for easy adoption
2. **Safety:** Strong typing and bounds checking
3. **Readability:** Clear semantics and predictable behavior
4. **Performance:** Optimizable by JVM compilers

---

## 2. Introduction to Loops

### What are Loops?
Loops are control structures that allow us to execute a block of code repeatedly based on a condition. They're essential for:
- Processing collections of data
- Performing repetitive calculations
- User input validation
- Algorithm implementation

### Why Use Loops?
```java
// Without loops - repetitive and error-prone
System.out.println(1);
System.out.println(2);
System.out.println(3);
// ... imagine doing this 1000 times!

// With loops - clean and efficient
for (int i = 1; i <= 1000; i++) {
    System.out.println(i);
}
```

---

## 3. For Loops

### Java Language Specification (JLS §14.14)

**From JLS §14.14:** "The for statement has two forms: BasicForStatement and EnhancedForStatement"

#### Basic For Statement Specification
According to JLS §14.14.1:
```java
BasicForStatement:
    for ( ForInitopt ; Expressionopt ; ForUpdateopt ) Statement

ForInit:
    StatementExpressionList
    LocalVariableDeclaration

ForUpdate:
    StatementExpressionList
```

**Key JLS Requirements:**
- The Expression (condition) must have type `boolean` or `Boolean`, or a compile-time error occurs
- The scope and shadowing of a local variable declared in the ForInit part follows §6.3 and §6.4
- If Expression is not present, the only way a for statement can complete normally is by use of a break statement

#### Execution Semantics (JLS §14.14.1.1)
The JLS defines precise execution order:
1. **ForInit code is executed first** (only once)
2. **For iteration step** is performed repeatedly:
    - Expression is evaluated (if present)
    - If true or absent: Statement is executed, then ForUpdate
    - If false: loop terminates normally

#### Variable Scope Rules
- Variables declared in ForInit have scope limited to the for statement
- Loop control variables are not accessible outside the loop body

### Enhanced For Loop (Java 5+) - JSR 201 Specification

Enhanced for loops allow convenient iteration over collections, without the need for an explicitly defined iterator. This reduces the need for boilerplate iteration code and the corresponding opportunities for errors.

#### JLS §14.14.2 Enhanced For Statement
```java
EnhancedForStatement:
    for ( FormalParameter : Expression ) Statement
```

**Formal Requirements:**
- The type of Expression must be `Iterable` or an array type, or a compile-time error occurs
- The enhanced for statement is translated into equivalent basic for statements by the compiler

#### Translation Rules from JLS

**For Iterable objects:**
```java
// Enhanced for
for (Type item : collection) {
    // statements
}

// JLS Translation equivalent
for (Iterator<Type> #i = collection.iterator(); #i.hasNext(); ) {
    Type item = #i.next();
    // statements
}
```

**For Arrays:**
```java
// Enhanced for
for (Type item : array) {
    // statements  
}

// JLS Translation equivalent
Type[] #a = array;
for (int #i = 0; #i < #a.length; #i++) {
    Type item = #a[#i];
    // statements
}
```

### Basic For Loop Syntax
```java
for (initialization; condition; update) {
    // Loop body
}
```

### Components Explained
1. **Initialization:** Executed once at the beginning
2. **Condition:** Checked before each iteration
3. **Update:** Executed after each iteration
4. **Loop Body:** Code to be repeated

### Example 1: Basic For Loop
```java
public class ForLoopExample {
    public static void main(String[] args) {
        // Print first 10 natural numbers
        System.out.println("First 10 natural numbers:");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println(); // New line
    }
}
```

### Example 2: For Loop with Squares
```java
public class SquaresExample {
    public static void main(String[] args) {
        System.out.println("First 10 natural numbers and their squares:");
        System.out.println("Number\tSquare");
        System.out.println("------\t------");
        
        for (int i = 1; i <= 10; i++) {
            int square = i * i;
            System.out.println(i + "\t" + square);
        }
    }
}
```

### Enhanced For Loop (For-Each)
```java
public class EnhancedForLoop {
    public static void main(String[] args) {
        int[] numbers = {10, 20, 30, 40, 50};
        
        System.out.println("Using enhanced for loop:");
        for (int num : numbers) {
            System.out.println("Number: " + num);
        }
    }
}
```

### Nested For Loops
```java
public class NestedLoops {
    public static void main(String[] args) {
        // Create a simple pattern
        System.out.println("Star pattern:");
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print("* ");
            }
            System.out.println();
        }
    }
}
```

---

---

## 4. While Loops

### Java Language Specification (JLS §14.12)

**From JLS §14.12:** "The while statement executes an Expression and a Statement repeatedly until the value of the Expression is false."

#### While Statement Specification
```java
WhileStatement:
    while ( Expression ) Statement

WhileStatementNoShortIf:
    while ( Expression ) StatementNoShortIf
```

**JLS Requirements:**
- The Expression must have type `boolean` or `Boolean`, or a compile-time error occurs
- If Expression evaluates to type `Boolean`, it is subject to unboxing conversion (§5.1.8)

#### Execution Semantics (JLS §14.12.1)
1. **Expression is evaluated first**
2. **If Expression is true:** contained Statement is executed, then loop repeats
3. **If Expression is false:** loop terminates normally
4. **Pre-test nature:** If Expression is false initially, Statement never executes

#### Abrupt Completion Handling (JLS §14.12.1)
- **break with no label:** while statement completes normally
- **continue with no label:** entire while statement is executed again
- **Other abrupt completion:** while statement completes abruptly for the same reason

### Theoretical Properties

### Basic While Loop Syntax
```java
while (condition) {
    // Loop body
    // Don't forget to update the condition variable!
}
```

### Key Characteristics
- **Pre-test loop:** Condition checked before execution
- **May execute zero times** if condition is initially false
- **Programmer responsible** for updating loop control variable

### Example 1: Basic While Loop
```java
public class WhileExample {
    public static void main(String[] args) {
        int count = 1;
        
        System.out.println("Counting with while loop:");
        while (count <= 5) {
            System.out.println("Count: " + count);
            count++; // Critical: update the control variable
        }
    }
}
```

### Example 2: Sum Until Condition
```java
import java.util.Scanner;

public class SumUntilCondition {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        int number;
        
        System.out.println("Enter numbers to sum (0 to stop):");
        
        while (true) {
            System.out.print("Enter number: ");
            number = scanner.nextInt();
            
            if (number == 0) {
                break; // Exit condition
            }
            
            sum += number;
            System.out.println("Current sum: " + sum);
            
            if (sum > 100) {
                System.out.println("Sum exceeded 100! Final sum: " + sum);
                break;
            }
        }
        
        scanner.close();
    }
}
```

### Example 3: Input Validation
```java
import java.util.Scanner;

public class InputValidation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int age;
        
        while (true) {
            System.out.print("Enter your age (1-120): ");
            age = scanner.nextInt();
            
            if (age >= 1 && age <= 120) {
                System.out.println("Valid age: " + age);
                break;
            } else {
                System.out.println("Invalid age! Please try again.");
            }
        }
        
        scanner.close();
    }
}
```

---

---

## 5. Do-While Loops

### Java Language Specification (JLS §14.13)

**From JLS §14.13:** "The do statement executes a Statement and an Expression repeatedly until the value of the Expression is false."

#### Do Statement Specification
```java
DoStatement:
    do Statement while ( Expression ) ;
```

**Critical JLS Note:** The semicolon after the while clause is **required** - this is a syntax requirement that distinguishes do-while from while loops.

**JLS Requirements:**
- The Expression must have type `boolean` or `Boolean`, or a compile-time error occurs
- **Guaranteed execution:** "Executing a do statement always executes the contained Statement at least once"

#### Execution Semantics (JLS §14.13.1)
1. **Statement is executed first** (unconditionally)
2. **Then Expression is evaluated**
3. **Post-test evaluation:** Decision made after first execution
4. **If true:** loop continues; **if false:** loop terminates

#### Abrupt Completion Handling (JLS §14.13.1)
- **break with no label:** do statement completes normally
- **continue with no label:** Expression is evaluated for next iteration decision
- **Other reasons:** do statement completes abruptly for the same reason

### Theoretical Properties

### Basic Do-While Syntax
```java
do {
    // Loop body
    // Update condition variable
} while (condition);
```

### Key Characteristics
- **Post-test loop:** Condition checked after execution
- **Always executes at least once**
- **Perfect for user input scenarios**

### Example 1: Basic Do-While
```java
public class DoWhileExample {
    public static void main(String[] args) {
        int i = 1;
        
        System.out.println("Do-while demonstration:");
        do {
            System.out.println("Iteration: " + i);
            i++;
        } while (i <= 5);
    }
}
```

### Example 2: Menu-Driven Program
```java
import java.util.Scanner;

public class MenuExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Say Hello");
            System.out.println("2. Current Time");
            System.out.println("3. Random Number");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.println("Hello, World!");
                    break;
                case 2:
                    System.out.println("Current time: " + 
                        java.time.LocalTime.now());
                    break;
                case 3:
                    System.out.println("Random number: " + 
                        (int)(Math.random() * 100));
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
        
        scanner.close();
    }
}
```

### Example 3: Input Validation with Do-While
```java
import java.util.Scanner;

public class ValidatedInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String password;
        
        do {
            System.out.print("Enter password (min 6 characters): ");
            password = scanner.nextLine();
            
            if (password.length() < 6) {
                System.out.println("Password too short! Try again.");
            }
        } while (password.length() < 6);
        
        System.out.println("Password accepted!");
        scanner.close();
    }
}
```

---

---

## 6. Loop Control Statements

### Java Language Specification for Break (JLS §14.15)

**From JLS §14.15:** "A break statement transfers control out of an enclosing statement."

#### Break Statement Specification
```java
BreakStatement:
    break Identifieropt ;
```

**JLS Semantic Rules:**
- **Unlabeled break:** "attempts to transfer control to the innermost enclosing switch, while, do, or for statement"
- **Labeled break:** "attempts to transfer control to the enclosing labeled statement that has the same Identifier"
- **Completion behavior:** "a break statement always completes abruptly"

#### Break Target Rules (JLS)
- Must refer to a label within the immediately enclosing method, constructor, or initializer
- No non-local jumps are permitted
- If no appropriate enclosing statement exists, compile-time error occurs

### Java Language Specification for Continue (JLS §14.16)

**From JLS §14.16:** "A continue statement may occur only in a while, do, or for statement; statements of these three kinds are called iteration statements."

#### Continue Statement Specification
```java
ContinueStatement:
    continue Identifieropt ;
```

**JLS Semantic Rules:**
- **Unlabeled continue:** transfers control to innermost enclosing while, do, or for statement
- **Labeled continue:** transfers to enclosing labeled iteration statement with matching Identifier
- **Loop continuation:** "Control passes to the loop-continuation point of an iteration statement"

#### Continue Target Requirements (JLS)
- Continue target must be a while, do, or for statement
- Must be within immediately enclosing method/constructor/initializer
- Compile-time error if no valid target exists

### Formal Completion Behavior

### Break Statement
The `break` statement immediately exits the current loop.

```java
public class BreakExample {
    public static void main(String[] args) {
        System.out.println("Finding first number divisible by 7:");
        
        for (int i = 1; i <= 100; i++) {
            if (i % 7 == 0) {
                System.out.println("Found: " + i);
                break; // Exit loop immediately
            }
        }
        
        System.out.println("Loop ended.");
    }
}
```

### Continue Statement
The `continue` statement skips the rest of the current iteration.

```java
public class ContinueExample {
    public static void main(String[] args) {
        System.out.println("Odd numbers from 1 to 10:");
        
        for (int i = 1; i <= 10; i++) {
            if (i % 2 == 0) {
                continue; // Skip even numbers
            }
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
```

### Labeled Break and Continue
```java
public class LabeledControl {
    public static void main(String[] args) {
        outer: for (int i = 1; i <= 3; i++) {
            System.out.println("Outer loop: " + i);
            
            for (int j = 1; j <= 3; j++) {
                if (i == 2 && j == 2) {
                    System.out.println("Breaking outer loop");
                    break outer; // Breaks out of outer loop
                }
                System.out.println("  Inner loop: " + j);
            }
        }
        System.out.println("Done!");
    }
}
```

---

---

## 7. Reachability and Loop Analysis (JLS §14.21)

### Compile-Time Reachability Rules

**From JLS §14.21:** "It is a compile-time error if a statement cannot be executed because it is unreachable."

#### While Loop Reachability (JLS §14.21.6)
- A while statement can complete normally iff:
    - The while statement is reachable AND the condition expression is not a constant expression with value `true`
    - OR there is a reachable break statement that exits the while statement

#### For Loop Reachability (JLS §14.21.8)
- A basic for statement can complete normally iff:
    - The for statement is reachable, there is a condition expression, and it's not a constant `true`
    - OR there is a reachable break statement that exits the for statement

#### Do-While Reachability (JLS §14.21.7)
- A do statement can complete normally iff:
    - The contained statement can complete normally AND condition is not constant `true`
    - OR there's a reachable continue with appropriate scope
    - OR there's a reachable break that exits the do statement

### Infinite Loop Detection
The JLS requires compilers to detect certain infinite loops:
```java
while (true) { 
    // statements without break - compiler must detect potential infinite loop
}

for (;;) { 
    // Classic infinite loop - no condition means always true
}
```

---

## 8. Common Pitfalls and Best Practices (10 minutes)

### 1. Infinite Loop Prevention
```java
// BAD - Infinite loop
int i = 1;
while (i <= 10) {
    System.out.println(i);
    // Forgot to increment i!
}

// GOOD - Proper increment
int i = 1;
while (i <= 10) {
    System.out.println(i);
    i++; // Don't forget this!
}
```

### 2. Off-by-One Errors
```java
// BAD - Only prints 9 numbers
for (int i = 1; i < 10; i++) {
    System.out.println(i);
}

// GOOD - Prints 10 numbers
for (int i = 1; i <= 10; i++) {
    System.out.println(i);
}
```

### 3. Variable Scope
```java
public class VariableScope {
    public static void main(String[] args) {
        // i is only accessible within the for loop
        for (int i = 0; i < 5; i++) {
            int temp = i * 2; // temp only exists in this iteration
            System.out.println(temp);
        }
        
        // System.out.println(i); // ERROR: i is out of scope
        // System.out.println(temp); // ERROR: temp is out of scope
    }
}
```

### 4. When to Use Each Loop Type - Formal Guidelines

| Loop Type | JLS Definition | Best Used When | Performance Notes |
|-----------|----------------|----------------|-------------------|
| **For** | "executes some initialization code, then executes an Expression, a Statement, and some update code repeatedly" | Known number of iterations, counting, array traversal | Most optimizable by JVM |
| **Enhanced For** | "convenient iteration over collections, without the need for an explicitly defined iterator" | Collection/array traversal where index not needed | Compiler optimized, Iterator overhead minimal |
| **While** | "executes an Expression and a Statement repeatedly until the value of the Expression is false" | Unknown iterations, condition-based repetition | Condition evaluated before each iteration |
| **Do-While** | "executes a Statement and an Expression repeatedly until the value of the Expression is false" | At least one execution needed, user input validation | Statement executed before condition check |

### JLS Compliance Notes
- All loop types must have boolean conditions (or Boolean with auto-unboxing)
- Enhanced for loops are syntax sugar - compiler translates to basic forms
- Break/continue statements have specific scoping rules defined in JLS §14.15 and §14.16
- Variable scope in loops follows standard Java scoping rules (JLS §6.3, §6.4)

---

## 9. Hands-On Activities

### Activity 1: Natural Numbers and Squares
```java
import java.util.Scanner;

public class NumberSquares {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter how many numbers: ");
        int count = scanner.nextInt();
        
        System.out.println("\nNumber\tSquare\tCube");
        System.out.println("------\t------\t----");
        
        for (int i = 1; i <= count; i++) {
            System.out.println(i + "\t" + (i*i) + "\t" + (i*i*i));
        }
        
        scanner.close();
    }
}
```

### Activity 2: Sum Until Condition
```java
import java.util.Scanner;

public class ConditionalSum {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        int count = 0;
        int number = 1; // Start with 1 to enter the loop
        
        System.out.println("Adding consecutive integers until sum > 100");
        
        while (sum <= 100) {
            sum += number;
            count++;
            System.out.println("Added " + number + ", sum = " + sum);
            number++;
        }
        
        System.out.println("\nFinal sum: " + sum);
        System.out.println("Numbers added: " + count);
        
        scanner.close();
    }
}
```

### Activity 3: Input Validation
```java
import java.util.Scanner;

public class GradeValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double grade;
        
        do {
            System.out.print("Enter a grade (0-100): ");
            grade = scanner.nextDouble();
            
            if (grade < 0 || grade > 100) {
                System.out.println("Invalid grade! Must be between 0 and 100.");
            }
        } while (grade < 0 || grade > 100);
        
        // Determine letter grade
        char letterGrade;
        if (grade >= 90) letterGrade = 'A';
        else if (grade >= 80) letterGrade = 'B';
        else if (grade >= 70) letterGrade = 'C';
        else if (grade >= 60) letterGrade = 'D';
        else letterGrade = 'F';
        
        System.out.println("Grade: " + grade + " = " + letterGrade);
        
        scanner.close();
    }
}
```

---

## 10. Mini-Projects

### Project 1: Multiplication Table Generator
```java
import java.util.Scanner;

public class MultiplicationTable {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a number for multiplication table: ");
        int number = scanner.nextInt();
        
        System.out.print("Enter the range (how many multiples): ");
        int range = scanner.nextInt();
        
        System.out.println("\nMultiplication Table for " + number + ":");
        System.out.println("================================");
        
        for (int i = 1; i <= range; i++) {
            int result = number * i;
            System.out.println(number + " × " + i + " = " + result);
        }
        
        // Bonus: Show formatted table
        System.out.println("\nFormatted Table:");
        for (int i = 1; i <= range; i++) {
            System.out.printf("%2d × %2d = %3d%n", number, i, number * i);
        }
        
        scanner.close();
    }
}
```

### Project 2: Factorial Calculator
```java
import java.util.Scanner;

public class FactorialCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a positive integer: ");
        int n = scanner.nextInt();
        
        if (n < 0) {
            System.out.println("Factorial is not defined for negative numbers.");
            return;
        }
        
        // Method 1: Using for loop
        long factorialFor = 1;
        System.out.print(n + "! = ");
        for (int i = 1; i <= n; i++) {
            factorialFor *= i;
            System.out.print(i);
            if (i < n) System.out.print(" × ");
        }
        System.out.println(" = " + factorialFor);
        
        // Method 2: Using while loop
        long factorialWhile = 1;
        int temp = n;
        while (temp > 0) {
            factorialWhile *= temp;
            temp--;
        }
        
        System.out.println("Verification (while loop): " + factorialWhile);
        
        // Show step by step calculation
        System.out.println("\nStep-by-step calculation:");
        long stepResult = 1;
        for (int i = 1; i <= n; i++) {
            stepResult *= i;
            System.out.println("Step " + i + ": " + stepResult);
        }
        
        scanner.close();
    }
}
```

### Project 3: Number Guessing Game
```java
import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        int secretNumber = random.nextInt(100) + 1; // 1-100
        int attempts = 0;
        int maxAttempts = 7;
        boolean hasWon = false;
        
        System.out.println("🎯 Number Guessing Game!");
        System.out.println("I'm thinking of a number between 1 and 100.");
        System.out.println("You have " + maxAttempts + " attempts to guess it!");
        
        while (attempts < maxAttempts && !hasWon) {
            attempts++;
            System.out.print("Attempt " + attempts + "/" + maxAttempts + " - Enter your guess: ");
            
            int guess = scanner.nextInt();
            
            if (guess == secretNumber) {
                hasWon = true;
                System.out.println("🎉 Congratulations! You guessed it in " + attempts + " attempts!");
            } else if (guess < secretNumber) {
                System.out.println("Too low! Try a higher number.");
            } else {
                System.out.println("Too high! Try a lower number.");
            }
            
            // Give hints based on how close they are
            if (!hasWon && attempts < maxAttempts) {
                int difference = Math.abs(guess - secretNumber);
                if (difference <= 5) {
                    System.out.println("🔥 Very close!");
                } else if (difference <= 15) {
                    System.out.println("🌡️ Getting warmer!");
                }
            }
        }
        
        if (!hasWon) {
            System.out.println("😔 Game over! The number was: " + secretNumber);
            System.out.println("Better luck next time!");
        }
        
        scanner.close();
    }
}
```

---

## 11. Assessment Questions

### Quick Quiz (5 minutes)
1. Which loop is guaranteed to execute at least once according to JLS §14.13?
2. What's the difference between `break` and `continue` in terms of JLS completion behavior?
3. When would you prefer a while loop over a for loop based on JLS semantics?
4. What happens if you forget to increment the counter in a while loop (reachability analysis)?
5. Can you use a break statement in an if statement outside a loop per JLS §14.15?

### JLS Specification Questions
6. What are the two forms of for statements defined in JLS §14.14?
7. Which JSR introduced enhanced for loops and in which Java version?
8. According to JLS §14.21, when is a while loop with `while(true)` considered reachable?
9. What is the formal translation of an enhanced for loop over an array?
10. Why does do-while require a semicolon after the while clause?

### Code Review Exercise
```java
// Find and fix the issues in this code
public class BuggyLoop {
    public static void main(String[] args) {
        int i = 0;
        while (i < 10); {  // Issue 1: Semicolon after while
            System.out.println(i);
            // Issue 2: Missing increment
        }
        
        for (int j = 10; j > 0; j++) {  // Issue 3: Infinite loop
            System.out.println(j);
        }
    }
}
```

---

## 12. Summary and Key Takeaways

### Loop Decision Matrix - JLS Based
```
Need to iterate a specific number of times? → Use BasicForStatement (JLS §14.14.1)
Condition-based repetition with unknown iterations? → Use WhileStatement (JLS §14.12)
Need at least one execution regardless of condition? → Use DoStatement (JLS §14.13)
Iterating over collections/arrays without index? → Use EnhancedForStatement (JLS §14.14.2)
```

### JLS Compliance Checklist
- ✅ Loop conditions must be boolean or Boolean (with auto-unboxing)
- ✅ Enhanced for expressions must be Iterable or array types
- ✅ Break/continue statements must target valid enclosing statements
- ✅ Variable declarations in for-init follow standard scoping rules
- ✅ Reachability analysis prevents unreachable code compilation errors
- ✅ Abrupt completion behavior follows JLS §14.12.1, §14.13.1, §14.14.1.3

### Evolution and Future
- **Java 1.0-1.4:** Basic loop constructs following C/C++ model
- **Java 5 (2004):** Enhanced for loops via JSR 201 "Tiger" release
- **Java 14 (2020):** Pattern matching enhancements affect loop iteration
- **Future:** Potential stream-based loop constructs and pattern matching integration

### Performance and JVM Optimization
- **For loops:** Most optimizable due to predictable structure
- **Enhanced for:** Compiler translates to optimal iterator or array access patterns
- **While loops:** JVM can optimize condition hoisting and loop unrolling
- **Do-while:** Slightly less optimizable due to post-test nature

---

## Additional Resources

### Official Specifications
- **Java Language Specification (JLS):** [Chapter 14 - Blocks and Statements](https://docs.oracle.com/javase/specs/jls/se21/html/jls-14.html)
- **JSR 201:** Enhanced for Loop specification
- **Java SE API Documentation:** Iterator and Iterable interfaces

### Practice Problems
1. Print all prime numbers between 1 and 100
2. Create a simple calculator with menu using do-while
3. Generate Fibonacci sequence using loops
4. Create a pattern printing program
5. Implement bubble sort using nested loops

---

*Remember: Practice makes perfect! Encourage students to experiment with different loop combinations and scenarios.*