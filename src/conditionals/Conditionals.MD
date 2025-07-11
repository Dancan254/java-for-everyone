# Session 3: Control Flow – Conditional Statements

## Table of Contents
1. [Introduction to Control Flow](#introduction-to-control-flow)
2. [Boolean Expressions and Operators](#boolean-expressions-and-operators)
3. [If Statements](#if-statements)
4. [If-Else Statements](#if-else-statements)
5. [Else-If Ladder](#else-if-ladder)
6. [Nested Conditionals](#nested-conditionals)
7. [Switch-Case Statements](#switch-case-statements)
8. [Ternary Operator](#ternary-operator)
9. [String Comparison](#string-comparison)
10. [Practical Examples](#practical-examples)
11. [Mini-Project: Grading System](#mini-project-grading-system)
12. [Common Mistakes and Best Practices](#common-mistakes-and-best-practices)
13. [Additional Resources](#additional-resources)

---

## Introduction to Control Flow

Control flow refers to the order in which statements are executed in a program. By default, Java executes statements sequentially from top to bottom. However, conditional statements allow us to alter this flow based on certain conditions.

Think of control flow like a flowchart where you make decisions at different points:
- "If it's raining, take an umbrella"
- "If the score is above 90, assign grade A"
- "If the user enters 1, show Monday"

### Why Control Flow Matters
- Makes programs dynamic and responsive
- Enables decision-making in code
- Allows programs to handle different scenarios
- Essential for creating interactive applications

---

## Boolean Expressions and Operators

### What are Boolean Expressions?
Boolean expressions evaluate to either `true` or `false`. They form the foundation of conditional statements.

```java
// Examples of boolean expressions
boolean isRaining = true;
boolean hasUmbrella = false;
int temperature = 25;
boolean isHot = temperature > 30;  // false
```

### Relational Operators
These operators compare two values and return a boolean result:

| Operator | Description | Example | Result |
|----------|-------------|---------|--------|
| `==` | Equal to | `5 == 5` | `true` |
| `!=` | Not equal to | `5 != 3` | `true` |
| `>` | Greater than | `8 > 5` | `true` |
| `<` | Less than | `3 < 7` | `true` |
| `>=` | Greater than or equal | `5 >= 5` | `true` |
| `<=` | Less than or equal | `4 <= 6` | `true` |

```java
// Practical examples
int age = 18;
boolean canVote = age >= 18;        // true
boolean isMinor = age < 18;         // false
boolean isExactly18 = age == 18;    // true
```

### Logical Operators
These operators combine boolean expressions:

| Operator | Description | Example | Result |
|----------|-------------|---------|--------|
| `&&` | Logical AND | `true && true` | `true` |
| `\|\|` | Logical OR | `true \|\| false` | `true` |
| `!` | Logical NOT | `!true` | `false` |

```java
// Practical examples
int score = 85;
boolean hasGoodGrade = score >= 80 && score <= 100;  // true
boolean needsHelp = score < 60 || score > 100;       // false
boolean isNotPassing = !(score >= 60);               // false
```

### Operator Precedence
Understanding the order of operations is crucial:

1. `!` (NOT) - highest precedence
2. `<, <=, >, >=` (relational operators)
3. `==, !=` (equality operators)
4. `&&` (AND)
5. `||` (OR) - lowest precedence

```java
// Example showing precedence
boolean result = 5 > 3 && 2 < 4 || !false;
// Evaluates as: ((5 > 3) && (2 < 4)) || (!false)
// Result: (true && true) || true = true
```

---

## If Statements

The `if` statement is the most basic conditional statement. It executes a block of code only if a condition is true.

### Syntax
```java
if (condition) {
    // code to execute if condition is true
}
```

### Single Statement (No Braces)
```java
if (temperature > 30)
    System.out.println("It's hot outside!");
```

### Multiple Statements (Braces Required)
```java
if (temperature > 30) {
    System.out.println("It's hot outside!");
    System.out.println("Remember to stay hydrated!");
}
```

### Complete Example
```java
public class IfStatementExample {
    public static void main(String[] args) {
        int number = 10;
        
        if (number > 0) {
            System.out.println(number + " is positive");
        }
        
        if (number % 2 == 0) {
            System.out.println(number + " is even");
        }
        
        System.out.println("Program continues...");
    }
}
```

**Output:**
```
10 is positive
10 is even
Program continues...
```

---

## If-Else Statements

The `if-else` statement provides an alternative path when the condition is false.

### Syntax
```java
if (condition) {
    // code to execute if condition is true
} else {
    // code to execute if condition is false
}
```

### Example: Even or Odd Checker
```java
import java.util.Scanner;

public class EvenOddChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();
        
        if (number % 2 == 0) {
            System.out.println(number + " is even");
        } else {
            System.out.println(number + " is odd");
        }
        
        scanner.close();
    }
}
```

### Example: Positive, Negative, or Zero
```java
import java.util.Scanner;

public class NumberChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a number: ");
        int number = scanner.nextInt();
        
        if (number > 0) {
            System.out.println(number + " is positive");
        } else if (number < 0) {
            System.out.println(number + " is negative");
        } else {
            System.out.println("The number is zero");
        }
        
        scanner.close();
    }
}
```

---

## Else-If Ladder

The else-if ladder allows you to test multiple conditions in sequence.

### Syntax
```java
if (condition1) {
    // code for condition1
} else if (condition2) {
    // code for condition2
} else if (condition3) {
    // code for condition3
} else {
    // default code if none of the above conditions are true
}
```

### Example: Grade Calculator
```java
import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your score (0-100): ");
        int score = scanner.nextInt();
        
        if (score >= 90) {
            System.out.println("Grade: A");
        } else if (score >= 80) {
            System.out.println("Grade: B");
        } else if (score >= 70) {
            System.out.println("Grade: C");
        } else if (score >= 60) {
            System.out.println("Grade: D");
        } else {
            System.out.println("Grade: F");
        }
        
        scanner.close();
    }
}
```

### Example: Age Group Classification
```java
import java.util.Scanner;

public class AgeGroupClassifier {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        
        if (age < 0) {
            System.out.println("Invalid age");
        } else if (age <= 2) {
            System.out.println("Baby");
        } else if (age <= 12) {
            System.out.println("Child");
        } else if (age <= 19) {
            System.out.println("Teenager");
        } else if (age <= 64) {
            System.out.println("Adult");
        } else {
            System.out.println("Senior");
        }
        
        scanner.close();
    }
}
```

---

## Nested Conditionals

Nested conditionals are if statements inside other if statements. They allow for more complex decision-making.

### Example: Login System
```java
import java.util.Scanner;

public class LoginSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        if (username.equals("admin")) {
            if (password.equals("password123")) {
                System.out.println("Welcome Admin!");
                System.out.println("Access granted to all features");
            } else {
                System.out.println("Invalid password for admin");
            }
        } else if (username.equals("user")) {
            if (password.equals("user123")) {
                System.out.println("Welcome User!");
                System.out.println("Limited access granted");
            } else {
                System.out.println("Invalid password for user");
            }
        } else {
            System.out.println("Unknown username");
        }
        
        scanner.close();
    }
}
```

### Example: Scholarship Eligibility
```java
import java.util.Scanner;

public class ScholarshipEligibility {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your GPA (0.0-4.0): ");
        double gpa = scanner.nextDouble();
        
        System.out.print("Enter your family income: ");
        double income = scanner.nextDouble();
        
        if (gpa >= 3.5) {
            if (income < 50000) {
                System.out.println("Eligible for full scholarship");
            } else if (income < 100000) {
                System.out.println("Eligible for partial scholarship");
            } else {
                System.out.println("Eligible for merit-based scholarship only");
            }
        } else if (gpa >= 3.0) {
            if (income < 30000) {
                System.out.println("Eligible for need-based scholarship");
            } else {
                System.out.println("Not eligible for scholarship");
            }
        } else {
            System.out.println("GPA too low for scholarship");
        }
        
        scanner.close();
    }
}
```

---

## Switch-Case Statements

The switch statement provides a cleaner alternative to multiple if-else statements when comparing a variable against several constant values.

### Syntax
```java
switch (variable) {
    case value1:
        // code for value1
        break;
    case value2:
        // code for value2
        break;
    case value3:
        // code for value3
        break;
    default:
        // code for default case
        break;
}
```

### Example: Day of the Week
```java
import java.util.Scanner;

public class DayOfWeek {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a number (1-7): ");
        int day = scanner.nextInt();
        
        switch (day) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            case 5:
                System.out.println("Friday");
                break;
            case 6:
                System.out.println("Saturday");
                break;
            case 7:
                System.out.println("Sunday");
                break;
            default:
                System.out.println("Invalid day number");
                break;
        }
        
        scanner.close();
    }
}
```

### Example: Calculator
```java
import java.util.Scanner;

public class SimpleCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        
        System.out.print("Enter operator (+, -, *, /): ");
        char operator = scanner.next().charAt(0);
        
        double result;
        
        switch (operator) {
            case '+':
                result = num1 + num2;
                System.out.println(num1 + " + " + num2 + " = " + result);
                break;
            case '-':
                result = num1 - num2;
                System.out.println(num1 + " - " + num2 + " = " + result);
                break;
            case '*':
                result = num1 * num2;
                System.out.println(num1 + " * " + num2 + " = " + result);
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                    System.out.println(num1 + " / " + num2 + " = " + result);
                } else {
                    System.out.println("Error: Division by zero");
                }
                break;
            default:
                System.out.println("Invalid operator");
                break;
        }
        
        scanner.close();
    }
}
```

### Fall-Through Behavior
When you omit `break` statements, the execution "falls through" to the next case:

```java
public class FallThroughExample {
    public static void main(String[] args) {
        int month = 3;
        
        switch (month) {
            case 12:
            case 1:
            case 2:
                System.out.println("Winter");
                break;
            case 3:
            case 4:
            case 5:
                System.out.println("Spring");
                break;
            case 6:
            case 7:
            case 8:
                System.out.println("Summer");
                break;
            case 9:
            case 10:
            case 11:
                System.out.println("Fall");
                break;
            default:
                System.out.println("Invalid month");
                break;
        }
    }
}
```

---

## Ternary Operator

The ternary operator is a shorthand for simple if-else statements.

### Syntax
```java
variable = (condition) ? value_if_true : value_if_false;
```

### Examples
```java
public class TernaryOperatorExamples {
    public static void main(String[] args) {
        int age = 18;
        
        // Simple example
        String status = (age >= 18) ? "Adult" : "Minor";
        System.out.println("Status: " + status);
        
        // Finding maximum of two numbers
        int a = 10, b = 20;
        int max = (a > b) ? a : b;
        System.out.println("Maximum: " + max);
        
        // Nested ternary (not recommended for readability)
        int score = 85;
        String grade = (score >= 90) ? "A" : (score >= 80) ? "B" : "C";
        System.out.println("Grade: " + grade);
        
        // Even or odd
        int number = 7;
        String evenOdd = (number % 2 == 0) ? "Even" : "Odd";
        System.out.println(number + " is " + evenOdd);
    }
}
```

---

## String Comparison

Comparing strings in Java requires special attention because strings are objects, not primitive types.

### Wrong Way (Using ==)
```java
String str1 = "hello";
String str2 = "hello";
String str3 = new String("hello");

// This might work due to string interning
if (str1 == str2) {
    System.out.println("Equal"); // This might print
}

// This will likely be false
if (str1 == str3) {
    System.out.println("Equal"); // This likely won't print
}
```

### Correct Way (Using .equals())
```java
String str1 = "hello";
String str2 = "hello";
String str3 = new String("hello");

if (str1.equals(str2)) {
    System.out.println("Equal"); // This will print
}

if (str1.equals(str3)) {
    System.out.println("Equal"); // This will print
}
```

### Case-Insensitive Comparison
```java
String str1 = "Hello";
String str2 = "HELLO";

if (str1.equalsIgnoreCase(str2)) {
    System.out.println("Equal (ignoring case)"); // This will print
}
```

### Handling Null Values
```java
String str1 = null;
String str2 = "hello";

// This will cause NullPointerException
// if (str1.equals(str2)) { ... }

// Safe way
if (str1 != null && str1.equals(str2)) {
    System.out.println("Equal");
}

// Or use Objects.equals() (Java 7+)
import java.util.Objects;
if (Objects.equals(str1, str2)) {
    System.out.println("Equal");
}
```

---

## Practical Examples

### Example 1: Number Comparison
```java
import java.util.Scanner;

public class NumberComparison {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter first number: ");
        int num1 = scanner.nextInt();
        
        System.out.print("Enter second number: ");
        int num2 = scanner.nextInt();
        
        if (num1 > num2) {
            System.out.println(num1 + " is greater than " + num2);
        } else if (num1 < num2) {
            System.out.println(num2 + " is greater than " + num1);
        } else {
            System.out.println("Both numbers are equal");
        }
        
        scanner.close();
    }
}
```

### Example 2: Leap Year Calculator
```java
import java.util.Scanner;

public class LeapYearCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter a year: ");
        int year = scanner.nextInt();
        
        boolean isLeapYear;
        
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    isLeapYear = true;
                } else {
                    isLeapYear = false;
                }
            } else {
                isLeapYear = true;
            }
        } else {
            isLeapYear = false;
        }
        
        if (isLeapYear) {
            System.out.println(year + " is a leap year");
        } else {
            System.out.println(year + " is not a leap year");
        }
        
        scanner.close();
    }
}
```

### Example 3: Temperature Converter with Validation
```java
import java.util.Scanner;

public class TemperatureConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Temperature Converter");
        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("2. Fahrenheit to Celsius");
        System.out.print("Choose an option (1 or 2): ");
        
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
                System.out.print("Enter temperature in Celsius: ");
                double celsius = scanner.nextDouble();
                double fahrenheit = (celsius * 9/5) + 32;
                System.out.println(celsius + "°C = " + fahrenheit + "°F");
                break;
                
            case 2:
                System.out.print("Enter temperature in Fahrenheit: ");
                fahrenheit = scanner.nextDouble();
                celsius = (fahrenheit - 32) * 5/9;
                System.out.println(fahrenheit + "°F = " + celsius + "°C");
                break;
                
            default:
                System.out.println("Invalid choice");
                break;
        }
        
        scanner.close();
    }
}
```

---

## Mini-Project: Grading System

Create a comprehensive grading system that takes a student's score and provides detailed feedback.

### Requirements:
1. Accept a score from 0-100
2. Validate input (handle invalid scores)
3. Assign grades based on score ranges
4. Provide additional feedback
5. Handle edge cases

### Solution 1: Using If-Else Statements
```java
import java.util.Scanner;

public class GradingSystemIfElse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Student Grading System ===");
        System.out.print("Enter student score (0-100): ");
        
        // Input validation
        if (!scanner.hasNextInt()) {
            System.out.println("Error: Please enter a valid number");
            scanner.close();
            return;
        }
        
        int score = scanner.nextInt();
        
        // Validate score range
        if (score < 0 || score > 100) {
            System.out.println("Error: Score must be between 0 and 100");
            scanner.close();
            return;
        }
        
        // Determine grade and feedback
        String grade;
        String feedback;
        
        if (score >= 90) {
            grade = "A";
            feedback = "Excellent work! Outstanding performance!";
        } else if (score >= 80) {
            grade = "B";
            feedback = "Good job! Above average performance.";
        } else if (score >= 70) {
            grade = "C";
            feedback = "Satisfactory. You're meeting expectations.";
        } else if (score >= 60) {
            grade = "D";
            feedback = "Below average. Consider additional study.";
        } else {
            grade = "F";
            feedback = "Failing grade. Please see instructor for help.";
        }
        
        // Display results
        System.out.println("\n=== Grade Report ===");
        System.out.println("Score: " + score);
        System.out.println("Grade: " + grade);
        System.out.println("Feedback: " + feedback);
        
        // Additional analysis
        if (score == 100) {
            System.out.println("Perfect score! Amazing!");
        } else if (score >= 95) {
            System.out.println("Nearly perfect! Great work!");
        } else if (score < 60) {
            System.out.println("Remedial action recommended.");
        }
        
        scanner.close();
    }
}
```

### Solution 2: Using Switch Statement
```java
import java.util.Scanner;

public class GradingSystemSwitch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Student Grading System (Switch Version) ===");
        System.out.print("Enter student score (0-100): ");
        
        // Input validation
        if (!scanner.hasNextInt()) {
            System.out.println("Error: Please enter a valid number");
            scanner.close();
            return;
        }
        
        int score = scanner.nextInt();
        
        // Validate score range
        if (score < 0 || score > 100) {
            System.out.println("Error: Score must be between 0 and 100");
            scanner.close();
            return;
        }
        
        // Convert score to grade level for switch
        int gradeLevel = score / 10;
        
        String grade;
        String feedback;
        
        switch (gradeLevel) {
            case 10:
            case 9:
                grade = "A";
                feedback = "Excellent work! Outstanding performance!";
                break;
            case 8:
                grade = "B";
                feedback = "Good job! Above average performance.";
                break;
            case 7:
                grade = "C";
                feedback = "Satisfactory. You're meeting expectations.";
                break;
            case 6:
                grade = "D";
                feedback = "Below average. Consider additional study.";
                break;
            default:
                grade = "F";
                feedback = "Failing grade. Please see instructor for help.";
                break;
        }
        
        // Display results
        System.out.println("\n=== Grade Report ===");
        System.out.println("Score: " + score);
        System.out.println("Grade: " + grade);
        System.out.println("Feedback: " + feedback);
        
        // Special cases
        switch (score) {
            case 100:
                System.out.println("Perfect score! Amazing!");
                break;
            case 0:
                System.out.println("No answers correct. Please review the material.");
                break;
            default:
                if (score >= 95) {
                    System.out.println("Nearly perfect! Great work!");
                } else if (score < 60) {
                    System.out.println("Remedial action recommended.");
                }
                break;
        }
        
        scanner.close();
    }
}
```

### Enhanced Version with Multiple Students
```java
import java.util.Scanner;

public class EnhancedGradingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== Enhanced Student Grading System ===");
        System.out.print("How many students do you want to grade? ");
        
        int numStudents = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        int totalScore = 0;
        int passCount = 0;
        
        for (int i = 1; i <= numStudents; i++) {
            System.out.println("\n--- Student " + i + " ---");
            System.out.print("Enter student name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter score (0-100): ");
            int score = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            // Validate score
            if (score < 0 || score > 100) {
                System.out.println("Invalid score for " + name);
                i--; // Retry this student
                continue;
            }
            
            // Calculate grade
            String grade = calculateGrade(score);
            String feedback = getFeedback(score);
            
            // Display individual results
            System.out.println("Name: " + name);
            System.out.println("Score: " + score);
            System.out.println("Grade: " + grade);
            System.out.println("Feedback: " + feedback);
            
            // Update statistics
            totalScore += score;
            if (score >= 60) {
                passCount++;
            }
        }
        
        // Display class statistics
        System.out.println("\n=== Class Statistics ===");
        double averageScore = (double) totalScore / numStudents;
        System.out.println("Average Score: " + String.format("%.2f", averageScore));
        System.out.println("Pass Rate: " + passCount + "/" + numStudents + 
                          " (" + String.format("%.1f", (double) passCount / numStudents * 100) + "%)");
        
        scanner.close();
    }
    
    public static String calculateGrade(int score) {
        if (score >= 90) return "A";
        else if (score >= 80) return "B";
        else if (score >= 70) return "C";
        else if (score >= 60) return "D";
        else return "F";
    }
    
    public static String getFeedback(int score) {
        if (score >= 90) return "Excellent work! Outstanding performance!";
        else if (score >= 80) return "Good job! Above average performance.";
        else if (score >= 70) return "Satisfactory. You're meeting expectations.";
        else if (score >= 60) return "Below average. Consider additional study.";
        else return "Failing grade. Please see instructor for help.";
    }
}
```

---

## Common Mistakes and Best Practices

### Common Mistakes

1. **Using = instead of ==**
```java
// Wrong
if (x = 5) { ... } // This is assignment, not comparison

// Correct
if (x == 5) { ... } // This is comparison
```

2. **Forgetting break in switch statements**
```java
// Wrong - will fall through
switch (day) {
    case 1:
        System.out.println("Monday");
    case 2:
        System.out.println("Tuesday");
}

// Correct
switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
}
```

3. **Comparing strings with ==**
```java
// Wrong
if (name == "John") { ... }

// Correct
if (name.equals("John")) { ... }
```

4. **Not handling edge cases**
```java
// Wrong - doesn't handle division by zero
if (a / b > 5) { ... }

// Correct
if (b != 0 && a / b > 5) { ... }
```

### Best Practices

1. **Always use braces for clarity**
```java
// Good practice even for single statements
if (condition) {
    doSomething();
}
```

2. **Use meaningful variable names**
```java
// Good
boolean isValidAge = age >= 18;

// Not as clear
boolean flag = age >= 18;
```

3. **Validate input early**
```java
if (score < 0 || score > 100) {
    System.out.println("Invalid score");
    return;
}
```

4. **Use switch for multiple discrete values**
```java
// Better with switch
switch (dayOfWeek) {
    case 1: return "Monday";
    case 2: return "Tuesday";
    // ...
}
```

5. **Keep conditions simple and readable**
```java
// Complex condition - break it down
boolean isEligibleForLoan = age >= 18 && income > 30000 && 
                           creditScore > 650 && hasCollateral;

if (isEligibleForLoan) {
    approveLoan();
}
```

---

## Additional Resources

### Online Resources
1. **Oracle Java Documentation**
    - [Control Flow Statements](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/flow.html)
    - [Operators](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/operators.html)

2. **Interactive Learning Platforms**
    - [Codecademy Java Course](https://www.codecademy.com/learn/learn-java)
    - [HackerRank Java Domain](https://www.hackerrank.com/domains/java)
    - [LeetCode Java Problems](https://leetcode.com/problemset/all/?difficulty=Easy&page=1&topicSlugs=java)

3. **Video Tutorials**
    - [Derek Banas Java Tutorial](https://www.youtube.com/watch?v=n-xAqcBCws4)
    - [Programming with Mosh - Java Tutorial](https://www.youtube.com/watch?v=eIrMbAQSU34)
    - [Cave of Programming](https://www.youtube.com/user/caveofprogramming)

4. **Practice Websites**
    - [CodingBat Java](https://codingbat.com/java)
    - [Edabit Java Challenges](https://edabit.com/challenges/java)
    - [GeeksforGeeks Java](https://www.geeksforgeeks.org/java/)

### Books for Further Reading
1. **"Head First Java" by Kathy Sierra & Bert Bates**
    - Excellent for beginners with engaging examples
    - Great visual explanations of concepts

2. **"Java: The Complete Reference" by Herbert Schildt**
    - Comprehensive coverage of Java fundamentals
    - Good reference book for detailed explanations

3. **"Effective Java" by Joshua Bloch**
    - Best practices and advanced techniques
    - Written by Java platform architect

### Practice Exercises

#### Exercise 1: Age Category Classifier
Create a program that categorizes people based on their age:
- 0-2: Baby
- 3-12: Child
- 13-19: Teenager
- 20-59: Adult
- 60+: Senior

**Challenge:** Add input validation and handle negative ages.

#### Exercise 2: BMI Calculator
Create a BMI calculator that:
- Takes height (in meters) and weight (in kg)
- Calculates BMI = weight / (height * height)
- Classifies the result:
    - Below 18.5: Underweight
    - 18.5-24.9: Normal weight
    - 25-29.9: Overweight
    - 30+: Obese

#### Exercise 3: Traffic Light System
Create a traffic light simulation:
- Input: current light color (Red, Yellow, Green)
- Output: next light color and action
- Red → Green: "Go"
- Green → Yellow: "Prepare to stop"
- Yellow → Red: "Stop"

#### Exercise 4: Rock, Paper, Scissors
Create a Rock, Paper, Scissors game:
- User inputs their choice
- Computer randomly generates a choice
- Determine winner using conditional logic
- Display results

#### Exercise 5: Quadratic Equation Solver
Create a program that solves quadratic equations (ax² + bx + c = 0):
- Input: coefficients a, b, c
- Calculate discriminant (b² - 4ac)
- Use conditionals to determine:
    - Two real solutions (discriminant > 0)
    - One real solution (discriminant = 0)
    - No real solutions (discriminant < 0)

### Sample Solutions for Practice Exercises

#### Exercise 1 Solution: Age Category Classifier
```java
import java.util.Scanner;

public class AgeCategoryClassifier {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your age: ");
        
        if (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number");
            return;
        }
        
        int age = scanner.nextInt();
        
        if (age < 0) {
            System.out.println("Age cannot be negative");
        } else if (age <= 2) {
            System.out.println("Category: Baby");
        } else if (age <= 12) {
            System.out.println("Category: Child");
        } else if (age <= 19) {
            System.out.println("Category: Teenager");
        } else if (age <= 59) {
            System.out.println("Category: Adult");
        } else {
            System.out.println("Category: Senior");
        }
        
        scanner.close();
    }
}
```

#### Exercise 2 Solution: BMI Calculator
```java
import java.util.Scanner;

public class BMICalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your weight (kg): ");
        double weight = scanner.nextDouble();
        
        System.out.print("Enter your height (m): ");
        double height = scanner.nextDouble();
        
        if (weight <= 0 || height <= 0) {
            System.out.println("Weight and height must be positive");
            return;
        }
        
        double bmi = weight / (height * height);
        
        System.out.printf("Your BMI is: %.2f%n", bmi);
        
        if (bmi < 18.5) {
            System.out.println("Category: Underweight");
        } else if (bmi < 25) {
            System.out.println("Category: Normal weight");
        } else if (bmi < 30) {
            System.out.println("Category: Overweight");
        } else {
            System.out.println("Category: Obese");
        }
        
        scanner.close();
    }
}
```

#### Exercise 3 Solution: Traffic Light System
```java
import java.util.Scanner;

public class TrafficLightSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter current light color (Red/Yellow/Green): ");
        String currentLight = scanner.nextLine().toLowerCase();
        
        switch (currentLight) {
            case "red":
                System.out.println("Next light: Green");
                System.out.println("Action: Go");
                break;
            case "green":
                System.out.println("Next light: Yellow");
                System.out.println("Action: Prepare to stop");
                break;
            case "yellow":
                System.out.println("Next light: Red");
                System.out.println("Action: Stop");
                break;
            default:
                System.out.println("Invalid light color");
                break;
        }
        
        scanner.close();
    }
}
```

### Assignment Ideas for Students

1. **Simple Calculator Enhancement**
    - Extend the basic calculator to handle more operations
    - Add input validation
    - Include a menu system for continuous operation

2. **Grade Book System**
    - Create a system that handles multiple subjects
    - Calculate overall GPA
    - Provide detailed reports

3. **Password Strength Checker**
    - Check password length, special characters, numbers
    - Provide feedback on password strength
    - Suggest improvements

4. **Temperature Alert System**
    - Monitor temperature readings
    - Provide alerts for extreme temperatures
    - Suggest appropriate clothing

5. **Simple Banking System**
    - Handle account balance checks
    - Process deposits and withdrawals
    - Implement basic security checks

### Debugging Tips

1. **Use System.out.println() for debugging**
```java
System.out.println("Debug: score = " + score);
System.out.println("Debug: condition result = " + (score >= 90));
```

2. **Test edge cases**
    - Minimum and maximum values
    - Zero values
    - Negative values
    - Boundary conditions

3. **Use logical operators carefully**
```java
// Make sure you understand the difference
if (age >= 18 && age <= 65) { ... }  // Both conditions must be true
if (age < 18 || age > 65) { ... }    // Either condition can be true
```

4. **Check for off-by-one errors**
```java
// Be careful with boundary conditions
if (score >= 90) { ... }  // Includes 90
if (score > 90) { ... }   // Excludes 90
```
### Final Project Ideas

1. **Student Management System**
    - Handle multiple students and subjects
    - Generate comprehensive reports
    - Include attendance tracking

2. **Simple Game Collection**
    - Number guessing game
    - Simple text-based adventure
    - Quiz application

3. **Utility Applications**
    - Unit converter (length, weight, temperature)
    - Tip calculator with bill splitting
    - Simple inventory system

---

## Summary

In this session, we've covered the fundamental concepts of control flow in Java:

- **Boolean expressions** form the foundation of decision-making
- **If statements** allow conditional execution of code
- **If-else statements** provide alternative paths
- **Else-if ladders** handle multiple conditions
- **Nested conditionals** enable complex decision trees
- **Switch statements** provide clean alternatives for multiple discrete values
- **Ternary operators** offer shorthand for simple conditions
- **String comparison** requires special attention with .equals()

### Key Takeaways:
1. Always validate input in real-world applications
2. Use appropriate conditional structures for different scenarios
3. Test edge cases and boundary conditions
4. Write readable, well-formatted code with meaningful variable names
5. Handle errors gracefully and provide helpful feedback to users

### Next Steps:
- Practice with the provided exercises
- Experiment with combining different conditional structures
- Try creating your own variations of the examples
- Prepare for the next session on loops and iteration

Remember: The best way to learn programming is through practice. Start with simple examples and gradually work your way up to more complex scenarios. Don't be afraid to make mistakes – they're an essential part of the learning process!