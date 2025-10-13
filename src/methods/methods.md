## 1. Introduction to Methods

A method is a block of code that performs a specific task and only runs when it is called. Methods are used to organize code, make it reusable, and improve readability.

## 2. Method Declaration and Syntax

The basic syntax for declaring a method is:

```java
accessModifier returnType methodName(parameters) {
    // method body
    return value; // if not void
}
```

**Components:**
- **Access Modifier**: Controls visibility (public, private, protected, default)
- **Return Type**: Data type of the value returned (or void if nothing is returned)
- **Method Name**: Identifier following camelCase convention
- **Parameters**: Input values the method accepts (optional)
- **Method Body**: Code that executes when method is called
- **Return Statement**: Returns a value to the caller (required for non-void methods)

**Example:**
```java
public int addNumbers(int a, int b) {
    int sum = a + b;
    return sum;
}
```

## 3. Method Invocation (Calling Methods)

To use a method, you need to call or invoke it. There are two ways depending on whether the method is static or instance-based.

**Calling Instance Methods:**
```java
ClassName objectName = new ClassName();
objectName.methodName(arguments);
```

**Calling Static Methods:**
```java
ClassName.methodName(arguments);
```

**Example:**
```java
public class Calculator {
    public int multiply(int x, int y) {
        return x * y;
    }
    
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        int result = calc.multiply(5, 3);
        System.out.println(result); // Output: 15
    }
}
```

## 4. Return Types

Methods can return a value or return nothing.

**Void Methods** - Don't return any value:
```java
public void printMessage() {
    System.out.println("Hello, World!");
}
```

**Methods with Return Values** - Must return a value matching the declared type:
```java
public String getMessage() {
    return "Hello, World!";
}

public boolean isEven(int number) {
    return number % 2 == 0;
}
```

## 5. Parameters and Arguments

**Parameters** are variables in the method declaration. **Arguments** are the actual values passed when calling the method.

**Single Parameter:**
```java
public void greet(String name) {
    System.out.println("Hello, " + name);
}
```

**Multiple Parameters:**
```java
public int calculate(int a, int b, int c) {
    return (a + b) * c;
}
```

**Variable-Length Arguments (Varargs):**
```java
public int sum(int... numbers) {
    int total = 0;
    for (int num : numbers) {
        total += num;
    }
    return total;
}

// Can be called with any number of arguments
sum(1, 2, 3);
sum(1, 2, 3, 4, 5);
```

## 6. Pass-by-Value in Java

Java is strictly pass-by-value. This means that when you pass a variable to a method, a copy of the value is passed, not the original variable.

**Primitive Types:**
```java
public void changeValue(int x) {
    x = 100; // Only changes the copy
}

int num = 5;
changeValue(num);
System.out.println(num); // Still prints 5
```

**Reference Types:**
```java
public void modifyArray(int[] arr) {
    arr[0] = 100; // Modifies the object the reference points to
}

int[] numbers = {1, 2, 3};
modifyArray(numbers);
System.out.println(numbers[0]); // Prints 100
```

## 7. Static vs Instance Methods

**Instance Methods** belong to an object and can access instance variables:
```java
public class Person {
    private String name;
    
    public void setName(String name) { // Instance method
        this.name = name;
    }
    
    public String getName() { // Instance method
        return this.name;
    }
}
```

**Static Methods** belong to the class and cannot access instance variables directly:
```java
public class MathUtils {
    public static int square(int num) { // Static method
        return num * num;
    }
}

// Called without creating an object
int result = MathUtils.square(5);
```

## 8. Method Overloading (Compile-Time Polymorphism)

Method overloading allows multiple methods with the same name but different parameters in the same class.

**Rules for Overloading:**
- Methods must have the same name
- Methods must have different parameter lists (number, type, or order)
- Return type can be different but is not sufficient alone

**Example:**
```java
public class Printer {
    public void print(int num) {
        System.out.println("Integer: " + num);
    }
    
    public void print(String text) {
        System.out.println("String: " + text);
    }
    
    public void print(int num, String text) {
        System.out.println(num + " " + text);
    }
}
```

## 9. Method Overriding (Runtime Polymorphism)

Method overriding occurs when a subclass provides a specific implementation of a method already defined in its parent class.

**Rules for Overriding:**
- Method must have the same name and parameters as in parent class
- Must have the same or compatible return type
- Access modifier cannot be more restrictive
- Cannot override final or static methods

**Example:**
```java
public class Animal {
    public void makeSound() {
        System.out.println("Some sound");
    }
}

public class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Bark!");
    }
}
```

## 10. Access Modifiers

Access modifiers control the visibility of methods:

- **public**: Accessible from anywhere
- **private**: Accessible only within the same class
- **protected**: Accessible within the same package and subclasses
- **default (no modifier)**: Accessible only within the same package

**Example:**
```java
public class Example {
    public void publicMethod() { }      // Accessible everywhere
    private void privateMethod() { }    // Only in this class
    protected void protectedMethod() { } // Package + subclasses
    void defaultMethod() { }            // Only in same package
}
```

## 11. Recursive Methods

A recursive method is one that calls itself to solve a problem by breaking it into smaller subproblems.

**Example:**
```java
public int factorial(int n) {
    if (n <= 1) {
        return 1; // Base case
    }
    return n * factorial(n - 1); // Recursive call
}
```

## 12. The 'this' Keyword

The `this` keyword refers to the current object instance. It's commonly used to distinguish between instance variables and parameters with the same name.

**Example:**
```java
public class Student {
    private String name;
    private int age;
    
    public Student(String name, int age) {
        this.name = name; // this.name refers to instance variable
        this.age = age;
    }
    
    public void printInfo() {
        System.out.println(this.name + " is " + this.age + " years old");
    }
}
```

## 13. Method Signature

A method signature consists of the method name and the parameter list. It does NOT include the return type or access modifiers. Method signatures must be unique within a class.

**Example:**
```java
// These have different signatures
public void calculate(int a, int b) { }
public void calculate(double a, double b) { }
public void calculate(int a, int b, int c) { }
```

## 14. Returning Objects and Arrays

Methods can return objects and arrays just like primitive types.

**Returning Arrays:**
```java
public int[] getNumbers() {
    return new int[]{1, 2, 3, 4, 5};
}
```

**Returning Objects:**
```java
public Person createPerson(String name, int age) {
    Person p = new Person();
    p.setName(name);
    p.setAge(age);
    return p;
}
```

## 15. Local Variables and Scope

Variables declared inside a method are called local variables. They exist only within the method and are destroyed when the method completes.

**Example:**
```java
public void exampleMethod() {
    int localVar = 10; // Local variable
    System.out.println(localVar);
} // localVar is destroyed here
```

## 16. Best Practices

- Use descriptive method names that indicate what the method does
- Keep methods short and focused on a single task
- Follow camelCase naming convention (e.g., `calculateTotal`, `getUserName`)
- Use appropriate access modifiers to enforce encapsulation
- Document complex methods with comments
- Avoid too many parameters (generally no more than 3-4)
- Return early when possible to reduce nesting

## 17. Common Examples

**Example 1: Basic Calculator**
```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public int subtract(int a, int b) {
        return a - b;
    }
    
    public double divide(double a, double b) {
        if (b == 0) {
            System.out.println("Cannot divide by zero");
            return 0;
        }
        return a / b;
    }
}
```

**Example 2: String Utilities**
```java
public class StringUtils {
    public static boolean isPalindrome(String str) {
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }
    
    public static int countVowels(String str) {
        int count = 0;
        String vowels = "aeiouAEIOU";
        for (char c : str.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }
}
```

**Example 3: Array Operations**
```java
public class ArrayHelper {
    public int findMax(int[] arr) {
        if (arr.length == 0) return 0;
        
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }
    
    public double average(int... numbers) {
        if (numbers.length == 0) return 0;
        
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        return (double) sum / numbers.length;
    }
}
```

---

This guide covers all the essential concepts about methods in Java. Practice writing different types of methods to solidify your understanding!

All the best Champs !!!
