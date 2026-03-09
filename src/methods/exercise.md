# Java Methods and Recursion - Practice Exercises

## Introduction to Recursion

Recursion is a powerful programming technique where a method solves a problem by calling itself with a simpler version of the same problem. Think of it like Russian nesting dolls - each doll contains a smaller version of itself until you reach the smallest doll.

### Key Components of Recursion

1. **Base Case**: The stopping condition that prevents infinite recursion
2. **Recursive Case**: The part where the method calls itself with a modified parameter
3. **Progress Toward Base Case**: Each recursive call must move closer to the base case

### How Recursion Works

When a method calls itself, each call is added to the call stack. The stack grows until the base case is reached, then each call returns its result and the stack unwinds.

**Example - Factorial:**
```java
public int factorial(int n) {
    if (n <= 1) {
        return 1; // Base case
    }
    return n * factorial(n - 1); // Recursive call
}
```

**Call Stack Visualization for factorial(4):**
```
factorial(4) = 4 * factorial(3)
               ↓
factorial(3) = 3 * factorial(2)
               ↓
factorial(2) = 2 * factorial(1)
               ↓
factorial(1) = 1 (base case)
               ↓
Returns: 1 → 2 → 6 → 24
```

### Recursion vs Iteration

- **Recursion**: More elegant and easier to read for certain problems, but uses more memory (call stack)
- **Iteration**: Generally faster and uses less memory, but can be more complex to write

### When to Use Recursion

- Tree or graph traversal
- Divide-and-conquer algorithms
- Problems with naturally recursive structure (factorial, Fibonacci, etc.)
- When the iterative solution is significantly more complex

---

## Exercise Set 1: Basic Method Creation

### Exercise 1.1: Simple Calculator Methods
Write a class `SimpleCalculator` with the following methods:
- `add(int a, int b)` - returns the sum
- `subtract(int a, int b)` - returns the difference
- `multiply(int a, int b)` - returns the product
- `divide(double a, double b)` - returns the quotient (handle division by zero)

Test all methods in the main method.

### Exercise 1.2: Number Checker
Create a class `NumberChecker` with these methods:
- `isEven(int num)` - returns true if the number is even
- `isOdd(int num)` - returns true if the number is odd
- `isPrime(int num)` - returns true if the number is prime
- `isPositive(int num)` - returns true if the number is positive

### Exercise 1.3: String Utilities
Create a class `StringHelper` with these methods:
- `reverseString(String str)` - returns the reversed string
- `countWords(String str)` - returns the number of words
- `toUpperCase(String str)` - converts string to uppercase
- `isPalindrome(String str)` - checks if string reads the same forwards and backwards

---

## Exercise Set 2: Method Overloading

### Exercise 2.1: Area Calculator
Create a class `AreaCalculator` with overloaded methods named `calculateArea`:
- `calculateArea(double radius)` - calculates area of a circle (π × r²)
- `calculateArea(double length, double width)` - calculates area of a rectangle
- `calculateArea(double base, double height, boolean isTriangle)` - calculates area of a triangle (½ × base × height)

### Exercise 2.2: Print Methods
Create a class `Printer` with overloaded `display` methods:
- `display(int num)` - prints "Integer: [num]"
- `display(double num)` - prints "Double: [num]"
- `display(String text)` - prints "String: [text]"
- `display(int num1, int num2)` - prints "Two integers: [num1], [num2]"

### Exercise 2.3: Temperature Converter
Create overloaded `convert` methods:
- `convert(double celsius)` - converts Celsius to Fahrenheit
- `convert(double temp, String unit)` - converts based on unit ("C" to "F" or "F" to "C")

---

## Exercise Set 3: Array Methods

### Exercise 3.1: Array Operations
Create a class `ArrayOperations` with these methods:
- `findMax(int[] arr)` - returns the maximum value
- `findMin(int[] arr)` - returns the minimum value
- `calculateAverage(int[] arr)` - returns the average as a double
- `sumArray(int[] arr)` - returns the sum of all elements

### Exercise 3.2: Array Transformation
Create methods that:
- `reverseArray(int[] arr)` - returns a new array with elements reversed
- `doubleElements(int[] arr)` - returns array with each element doubled
- `filterEven(int[] arr)` - returns array containing only even numbers

### Exercise 3.3: Varargs Practice
Write these methods using varargs:
- `findMaximum(int... numbers)` - finds the maximum among any number of integers
- `concatenate(String... words)` - concatenates all strings with spaces
- `sumAll(double... values)` - returns sum of all values

---

## Exercise Set 4: Recursive Methods - Basic

### Exercise 4.1: Basic Recursion
Implement these recursive methods:
- `factorial(int n)` - calculates n! (e.g., 5! = 5 × 4 × 3 × 2 × 1)
- `power(int base, int exponent)` - calculates base^exponent
- `sumN(int n)` - calculates sum of numbers from 1 to n (e.g., sum of 1+2+3+4+5)

### Exercise 4.2: Fibonacci Sequence
Write a recursive method `fibonacci(int n)` that returns the nth Fibonacci number.
- Fibonacci sequence: 0, 1, 1, 2, 3, 5, 8, 13, 21...
- fib(0) = 0, fib(1) = 1
- fib(n) = fib(n-1) + fib(n-2)

### Exercise 4.3: String Recursion
Implement these recursive methods:
- `reverseString(String str)` - reverses a string recursively
- `countCharacter(String str, char ch)` - counts occurrences of a character
- `isPalindrome(String str)` - checks if string is palindrome recursively

---

## Exercise Set 5: Recursive Methods - Intermediate

### Exercise 5.1: Digit Operations
Write recursive methods:
- `sumDigits(int n)` - returns sum of digits (e.g., 123 → 6)
- `countDigits(int n)` - returns number of digits
- `reverseNumber(int n)` - reverses the digits (e.g., 123 → 321)

### Exercise 5.2: Array Recursion
Implement recursively:
- `arraySum(int[] arr, int index)` - calculates sum of array elements
- `arrayMax(int[] arr, int index)` - finds maximum element
- `contains(int[] arr, int target, int index)` - checks if target exists in array

### Exercise 5.3: Pattern Printing
Write recursive methods to print:
- `printStars(int n)` - prints n stars on one line
- `printTriangle(int rows)` - prints triangle pattern:
  ```
  *
  **
  ***
  ****
  ```
- `printNumbers(int n)` - prints numbers from 1 to n, one per line

---

## Exercise Set 6: Advanced Challenges

### Exercise 6.1: GCD and LCM
Implement:
- `gcd(int a, int b)` - finds Greatest Common Divisor using Euclidean algorithm (recursive)
- `lcm(int a, int b)` - finds Least Common Multiple

### Exercise 6.2: Binary Conversion
Write a recursive method:
- `decimalToBinary(int n)` - converts decimal to binary string

### Exercise 6.3: Tower of Hanoi
Implement the classic Tower of Hanoi problem:
- `towerOfHanoi(int n, char from, char to, char aux)` - prints steps to move n disks

---

## Mini-Project 1: Menu-Driven Calculator

Create a calculator program with a menu system. Each operation should be in its own method:

**Requirements:**
- Display a menu with options: Add, Subtract, Multiply, Divide, Power, Square Root, Exit
- Use methods for each operation
- Use method overloading where appropriate
- Handle invalid input
- Loop until user chooses to exit

**Bonus Features:**
- Add a history feature that stores last 5 calculations
- Create a method that displays calculation history
- Add scientific functions (sin, cos, tan)

---

## Mini-Project 2: Recursive Problem Solver

Create a program that demonstrates various recursive solutions:

**Requirements:**
1. **Menu System** with options:
    - Calculate Factorial
    - Generate Fibonacci Sequence
    - Reverse a String
    - Sum of Digits
    - Tower of Hanoi
    - Exit

2. **For each option:**
    - Get necessary input from user
    - Call the appropriate recursive method
    - Display the result with explanation
    - Show the recursive call trace (optional challenge)

3. **Additional Features:**
    - Compare recursive vs iterative solutions
    - Display time taken for each method
    - Handle edge cases (negative numbers, empty strings, etc.)

---

## Tips for Completing Exercises

1. **Start Simple**: Begin with Exercise Set 1 and progress sequentially
2. **Test Thoroughly**: Test each method with multiple inputs, including edge cases
3. **Trace Recursion**: For recursive methods, trace through with small values on paper first
4. **Debug**: Use print statements to see recursive call flow
5. **Optimize**: After getting it working, think about efficiency
6. **Document**: Add comments explaining your logic

---

## Common Mistakes to Avoid

### In Methods:
- Forgetting to return a value in non-void methods
- Not matching return type with declared type
- Incorrect parameter order when calling methods
- Modifying primitive parameters expecting changes outside method

### In Recursion:
- Missing or incorrect base case (causes infinite recursion/StackOverflowError)
- Not progressing toward base case
- Not returning values properly in recursive calls
- Creating unnecessary complexity when iteration is simpler

---

## Extension Challenges

Once you complete the main exercises, try these:

1. **Method Chaining**: Create methods that return objects allowing chained calls
2. **Recursion Optimization**: Implement memoization for Fibonacci
3. **Multiple Recursion**: Solve problems requiring multiple recursive calls
4. **Tail Recursion**: Convert regular recursion to tail recursion
5. **Mutual Recursion**: Create two methods that call each other

Happy Coding!