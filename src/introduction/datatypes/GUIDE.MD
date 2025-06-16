# Java Session 2: Data Types, Variables, and Operators

## 📚 Overview
In this session, we'll explore the fundamental building blocks of Java programming: data types, variables, and operators. These concepts form the foundation for storing, manipulating, and processing data in Java applications.

## 🎯 Learning Objectives
By the end of this session, students will be able to:
- Understand and distinguish between Java's primitive data types
- Declare, initialize, and use variables effectively
- Apply proper naming conventions for variables
- Use arithmetic and assignment operators to perform calculations
- Understand type casting and conversion
- Read user input using the Scanner class
- Write programs that process and manipulate data

---

## 1. 🔢 Java Data Types

### What are Data Types?
**Data types** specify the kind of data that can be stored in a variable. They define the size of memory allocated and the operations that can be performed on the data.

Java divides data types into two main categories: primitive types and reference types. Primitive types include boolean and numeric types, while reference types include class types, interface types, and array types.

### 1.1 Primitive Data Types

Java provides **8 primitive data types** that are built into the language:

#### 🔢 Numeric Types - Integers

| Type | Size | Range | Default Value | Description |
|------|------|-------|---------------|-------------|
| `byte` | 8 bits | -128 to 127 | 0 | Smallest integer type |
| `short` | 16 bits | -32,768 to 32,767 | 0 | Short integer |
| `int` | 32 bits | -2,147,483,648 to 2,147,483,647 | 0 | Most commonly used integer |
| `long` | 64 bits | -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 | 0L | Large integer values |

**Examples:**
```java
byte temperature = 25;           // Good for small numbers
short year = 2024;              // Good for moderate numbers
int population = 1000000;       // Most common choice
long distance = 150000000000L;  // Note the 'L' suffix for long literals
```

#### 🔢 Numeric Types - Floating Point

| Type | Size | Precision | Default Value | Description |
|------|------|-----------|---------------|-------------|
| `float` | 32 bits | ~7 decimal digits | 0.0f | Single-precision floating point |
| `double` | 64 bits | ~15 decimal digits | 0.0d | Double-precision floating point |

**Examples:**
```java
float price = 19.99f;           // Note the 'f' suffix
double piValue = 3.14159265359; // Higher precision
double scientificNotation = 1.23e10; // 12,300,000,000
```

#### 🔤 Character Type

| Type | Size | Range | Default Value | Description |
|------|------|-------|---------------|-------------|
| `char` | 16 bits | 0 to 65,535 (Unicode) | '\u0000' | Single Unicode character |

**Examples:**
```java
char grade = 'A';
char symbol = '$';
char unicodeChar = '\u0041'; // Represents 'A'
char newline = '\n';         // Escape sequence
```

#### ✅ Boolean Type

| Type | Size | Values | Default Value | Description |
|------|------|--------|---------------|-------------|
| `boolean` | 1 bit | `true` or `false` | false | Logical values |

**Examples:**
```java
boolean isStudent = true;
boolean isFinished = false;
boolean hasPermission = (age >= 18); // Result of comparison
```

### 1.2 Reference Types - Strings

**Strings** are objects in Java, not primitive types. They represent sequences of characters.

```java
String name = "John Doe";
String message = "Welcome to Java!";
String empty = "";              // Empty string
String nullString = null;       // No object reference
```

---

## 2. 📦 Variables

### What is a Variable?
A **variable** is a named storage location in memory that holds data. Think of it as a labeled container that can store different values of a specific type.

### 2.1 Variable Declaration and Initialization

#### Declaration Syntax:
```java
dataType variableName;
```

#### Initialization Syntax:
```java
variableName = value;
```

#### Declaration + Initialization (Recommended):
```java
dataType variableName = value;
```

### 2.2 Variable Examples

```java
// Integer variables
int studentCount = 30;
int maxScore = 100;

// Floating-point variables
double accountBalance = 1250.75;
float temperature = 98.6f;

// Character and boolean variables
char grade = 'B';
boolean isPassed = true;

// String variables
String courseName = "Java Programming";
String instructor = "Dr. Smith";

// Multiple variables of same type
int x = 10, y = 20, z = 30;
```

### 2.3 Variable Naming Conventions

Java follows **camelCase** convention for variable names:

#### ✅ Good Variable Names:
```java
int studentAge;
double accountBalance;
String firstName;
boolean isComplete;
char userChoice;
```

#### ❌ Poor Variable Names:
```java
int a;                    // Not descriptive
double student_age;       // Uses underscore (not camelCase)
String FirstName;         // Starts with uppercase
boolean 2ndChoice;        // Starts with number
char user-choice;         // Contains hyphen
```

#### 📋 Naming Rules:
1. Must start with a letter, underscore (_), or dollar sign ($)
2. Cannot start with a number
3. Cannot use Java keywords (int, class, public, etc.)
4. Case-sensitive
5. Should be descriptive and meaningful

---

## 3. 🔧 Operators

### What are Operators?
**Operators** are special symbols that perform operations on variables and values (operands).

### 3.1 Arithmetic Operators

| Operator | Name | Description | Example |
|----------|------|-------------|---------|
| `+` | Addition | Adds two operands | `a + b` |
| `-` | Subtraction | Subtracts right operand from left | `a - b` |
| `*` | Multiplication | Multiplies two operands | `a * b` |
| `/` | Division | Divides left operand by right | `a / b` |
| `%` | Modulus | Returns remainder of division | `a % b` |

**Examples:**
```java
int a = 10, b = 3;

int sum = a + b;        // 13
int difference = a - b; // 7
int product = a * b;    // 30
int quotient = a / b;   // 3 (integer division)
int remainder = a % b;  // 1

double preciseDiv = (double) a / b; // 3.3333... (casting to double)
```

### 3.2 Assignment Operators

| Operator | Name | Description | Example | Equivalent |
|----------|------|-------------|---------|------------|
| `=` | Assignment | Assigns right value to left variable | `a = 5` | - |
| `+=` | Addition assignment | Adds and assigns | `a += 3` | `a = a + 3` |
| `-=` | Subtraction assignment | Subtracts and assigns | `a -= 2` | `a = a - 2` |
| `*=` | Multiplication assignment | Multiplies and assigns | `a *= 4` | `a = a * 4` |
| `/=` | Division assignment | Divides and assigns | `a /= 2` | `a = a / 2` |
| `%=` | Modulus assignment | Modulus and assigns | `a %= 3` | `a = a % 3` |

**Examples:**
```java
int score = 85;
score += 10;    // score is now 95
score -= 5;     // score is now 90
score *= 2;     // score is now 180
score /= 3;     // score is now 60
score %= 7;     // score is now 4
```

### 3.3 Increment and Decrement Operators

| Operator | Name | Description | Example |
|----------|------|-------------|---------|
| `++` | Increment | Increases value by 1 | `++a` or `a++` |
| `--` | Decrement | Decreases value by 1 | `--a` or `a--` |

**Pre vs Post Increment/Decrement:**
```java
int x = 5;
int y = ++x;  // Pre-increment: x becomes 6, then y gets 6
// x = 6, y = 6

int a = 5;
int b = a++;  // Post-increment: b gets 5, then a becomes 6
// a = 6, b = 5
```

### 3.4 Operator Precedence

Operators are evaluated in a specific order (highest to lowest precedence):

1. `++`, `--` (postfix)
2. `++`, `--` (prefix), unary `+`, `-`
3. `*`, `/`, `%`
4. `+`, `-`
5. Assignment operators (`=`, `+=`, `-=`, etc.)

**Example:**
```java
int result = 10 + 5 * 2;  // Result is 20, not 30
// Multiplication happens first: 5 * 2 = 10
// Then addition: 10 + 10 = 20

int withParentheses = (10 + 5) * 2;  // Result is 30
// Parentheses force addition first: 10 + 5 = 15
// Then multiplication: 15 * 2 = 30
```

---

## 4. 🔄 Type Casting and Conversion

### What is Type Casting?
**Type casting** is the process of converting one data type to another.

### 4.1 Implicit Casting (Automatic)
Java automatically converts smaller data types to larger ones:

```java
byte byteValue = 42;
short shortValue = byteValue;  // byte to short
int intValue = shortValue;     // short to int
long longValue = intValue;     // int to long
float floatValue = longValue;  // long to float
double doubleValue = floatValue; // float to double
```

### 4.2 Explicit Casting (Manual)
For converting larger data types to smaller ones:

```java
double doubleValue = 9.78;
int intValue = (int) doubleValue;  // 9 (decimal part lost)

long longValue = 123456789L;
int intValue2 = (int) longValue;   // Possible data loss

float floatValue = 3.14f;
int intValue3 = (int) floatValue;  // 3
```

### 4.3 String Conversion

**Converting to String:**
```java
int age = 25;
String ageString = String.valueOf(age);  // "25"
String ageString2 = age + "";            // "25" (concatenation)
```

**Converting from String:**
```java
String numberString = "123";
int number = Integer.parseInt(numberString);      // 123
double decimal = Double.parseDouble("45.67");     // 45.67
boolean flag = Boolean.parseBoolean("true");      // true
```

---

## 5. Input/Output with Scanner

### What is the Scanner Class?
The Scanner class is widely used for reading user input in console applications and is part of the java.util package.

### 5.1 Setting up Scanner

```java
import java.util.Scanner;  // Import at top of file

public class InputExample {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Your code here
        
        scanner.close();  // Always close Scanner when done
    }
}
```

### 5.2 Scanner Methods

| Method | Description | Example |
|--------|-------------|---------|
| `nextInt()` | Reads an integer | `int age = scanner.nextInt();` |
| `nextDouble()` | Reads a double | `double price = scanner.nextDouble();` |
| `nextFloat()` | Reads a float | `float weight = scanner.nextFloat();` |
| `nextBoolean()` | Reads a boolean | `boolean flag = scanner.nextBoolean();` |
| `next()` | Reads a single word | `String word = scanner.next();` |
| `nextLine()` | Reads entire line | `String line = scanner.nextLine();` |

### 5.3 Complete Input Example

```java
import java.util.Scanner;

public class UserInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        
        System.out.print("Enter your GPA: ");
        double gpa = scanner.nextDouble();
        
        System.out.print("Are you a student? (true/false): ");
        boolean isStudent = scanner.nextBoolean();
        
        // Output
        System.out.println("\n--- Your Information ---");
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("GPA: " + gpa);
        System.out.println("Student: " + isStudent);
        
        scanner.close();
    }
}
```

---

## 6. 🔗 String Operations

### 6.1 String Concatenation

```java
String firstName = "John";
String lastName = "Doe";

// Using + operator
String fullName = firstName + " " + lastName;  // "John Doe"

// Using += operator
String greeting = "Hello, ";
greeting += firstName;  // "Hello, John"

// Concatenating with numbers
int age = 25;
String message = "I am " + age + " years old";  // "I am 25 years old"
```

### 6.2 Useful String Methods

```java
String text = "Java Programming";

int length = text.length();                    // 16
String upper = text.toUpperCase();             // "JAVA PROGRAMMING"
String lower = text.toLowerCase();             // "java programming"
boolean contains = text.contains("Java");      // true
String replaced = text.replace("Java", "C++"); // "C++ Programming"
```

---

## 7. 🛠️ Key Concepts Deep Dive

### 7.1 Memory and Variable Storage

**Stack Memory:**
- Stores primitive variables and method parameters
- Fast access
- Automatically managed

**Heap Memory:**
- Stores objects (including Strings)
- Slower access than stack
- Managed by Garbage Collector

### 7.2 Literal Values

**Numeric Literals:**
```java
int decimal = 42;           // Decimal
int binary = 0b101010;      // Binary (42 in decimal)
int octal = 052;            // Octal (42 in decimal)
int hex = 0x2A;             // Hexadecimal (42 in decimal)
```

**Boolean Literals:**
```java
boolean isTrue = true;
boolean isFalse = false;
```

**Character Literals:**
```java
char letter = 'A';
char digit = '7';
char tab = '\t';            // Tab character
char quote = '\'';          // Single quote
```

**String Literals:**
```java
String simple = "Hello World";
String withEscape = "He said, \"Hello!\"";
String multiLine = "Line 1\nLine 2\nLine 3";
```

---

## 8. 💻 Practice Activities

### Activity 1: Rectangle Area Calculator

**Objective:** Calculate the area of a rectangle using user input.

```java
import java.util.Scanner;

public class RectangleArea {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter rectangle length: ");
        double length = scanner.nextDouble();
        
        System.out.print("Enter rectangle width: ");
        double width = scanner.nextDouble();
        
        double area = length * width;
        double perimeter = 2 * (length + width);
        
        System.out.println("Rectangle Area: " + area);
        System.out.println("Rectangle Perimeter: " + perimeter);
        
        scanner.close();
    }
}
```

### Activity 2: Variable Swapping

**Objective:** Swap values of two variables.

```java
public class VariableSwap {
    public static void main(String[] args) {
        int a = 10;
        int b = 20;
        
        System.out.println("Before swap: a = " + a + ", b = " + b);
        
        // Method 1: Using temporary variable
        int temp = a;
        a = b;
        b = temp;
        
        System.out.println("After swap: a = " + a + ", b = " + b);
        
        // Method 2: Without temporary variable (for integers)
        // a = a + b;
        // b = a - b;
        // a = a - b;
    }
}
```

### Activity 3: Arithmetic Calculator

**Objective:** Perform basic arithmetic operations.

```java
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();
        
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();
        
        double sum = num1 + num2;
        double difference = num1 - num2;
        double product = num1 * num2;
        double quotient = num1 / num2;
        double remainder = num1 % num2;
        
        System.out.println("\n--- Results ---");
        System.out.println(num1 + " + " + num2 + " = " + sum);
        System.out.println(num1 + " - " + num2 + " = " + difference);
        System.out.println(num1 + " * " + num2 + " = " + product);
        System.out.println(num1 + " / " + num2 + " = " + quotient);
        System.out.println(num1 + " % " + num2 + " = " + remainder);
        
        scanner.close();
    }
}
```

---

## 9. 🎯 Mini-Project: Personal Information System

**Objective:** Create a program that collects user information and performs calculations.

```java
import java.util.Scanner;

public class PersonalInfo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Collect user information
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Enter your current age: ");
        int currentAge = scanner.nextInt();
        
        System.out.print("Enter your birth year: ");
        int birthYear = scanner.nextInt();
        
        System.out.print("Enter your favorite number: ");
        double favoriteNumber = scanner.nextDouble();
        
        // Calculations
        String fullName = firstName + " " + lastName;
        int ageNextYear = currentAge + 1;
        int currentYear = 2024; // You can make this dynamic
        int calculatedAge = currentYear - birthYear;
        double doubledFavorite = favoriteNumber * 2;
        double squaredFavorite = favoriteNumber * favoriteNumber;
        
        // Display results
        System.out.println("\n" + "=".repeat(40));
        System.out.println("         PERSONAL INFORMATION REPORT");
        System.out.println("=".repeat(40));
        System.out.println("Full Name: " + fullName);
        System.out.println("Current Age: " + currentAge + " years old");
        System.out.println("Age Next Year: " + ageNextYear + " years old");
        System.out.println("Calculated Age: " + calculatedAge + " years old");
        System.out.println("Favorite Number: " + favoriteNumber);
        System.out.println("Doubled Favorite: " + doubledFavorite);
        System.out.println("Squared Favorite: " + squaredFavorite);
        
        // Fun facts
        int daysLived = currentAge * 365;
        int hoursLived = daysLived * 24;
        
        System.out.println("\n--- Fun Calculations ---");
        System.out.println("Approximate days lived: " + daysLived);
        System.out.println("Approximate hours lived: " + hoursLived);
        
        // Age verification
        if (calculatedAge != currentAge) {
            System.out.println("\nNote: There's a difference between your stated age and calculated age.");
            System.out.println("This might be due to birthday timing within the year.");
        }
        
        scanner.close();
        
        System.out.println("\nThank you for using the Personal Information System!");
    }
}
```

---

## 10. 🚨 Common Mistakes and Troubleshooting

### 10.1 Type Mismatch Errors
```java
// ❌ Wrong
int number = 3.14;  // Cannot assign double to int

// ✅ Correct
double number = 3.14;
// OR
int number = (int) 3.14;  // Explicit casting
```

### 10.2 Scanner Input Issues
```java
// ❌ Problem: nextLine() after nextInt()
Scanner scanner = new Scanner(System.in);
int age = scanner.nextInt();
String name = scanner.nextLine();  // This will be empty!

// ✅ Solution: Add extra nextLine() to consume newline
Scanner scanner = new Scanner(System.in);
int age = scanner.nextInt();
scanner.nextLine();  // Consume the newline
String name = scanner.nextLine();
```

### 10.3 Division by Zero
```java
// ❌ Runtime error
int result = 10 / 0;  // ArithmeticException

// ✅ Check before dividing
int dividend = 10;
int divisor = 0;
if (divisor != 0) {
    int result = dividend / divisor;
} else {
    System.out.println("Cannot divide by zero!");
}
```

---

## 11. 📚 Additional Resources

### Official Documentation
- [Oracle Java Tutorials - Language Basics](https://docs.oracle.com/javase/tutorial/java/nutsandbolts/index.html)
- [Java SE API Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/)
- [Scanner Class Documentation](https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html)

### Best Practices
1. **Use meaningful variable names** - `studentCount` instead of `sc`
2. **Initialize variables when declaring** - `int age = 0;`
3. **Always close Scanner objects** - `scanner.close();`
4. **Use appropriate data types** - Don't use `long` for small numbers
5. **Handle user input validation** - Check for valid input ranges
6. **Use consistent formatting** - Follow Java naming conventions

### Next Steps
- Learn about conditional statements (if/else)
- Explore loops (for, while, do-while)
- Understand methods and functions
- Study arrays and collections
- Practice with more complex projects

---

## Summary

In this session, we covered:

✅ **Java's 8 primitive data types** and their characteristics  
✅ **Variable declaration, initialization, and naming conventions**  
✅ **Arithmetic and assignment operators** with precedence rules  
✅ **Type casting and conversion** between data types  
✅ **Scanner class** for reading user input  
✅ **String operations** and concatenation  
✅ **Memory concepts** and literal values  
✅ **Practical examples** and hands-on exercises

You now have a solid foundation in Java's fundamental data handling concepts. Practice with the provided exercises and experiment with different combinations of data types and operators to strengthen your understanding!

**Remember:** Programming is learned by doing. Don't just read the code - type it out, run it, modify it, and see what happens! 🚀