# What is an Array?
An array is a *fixed-size*, *contiguous block* of memory that stores homogenous elements(same type).
In the Java programming language, arrays are objects, are dynamically created, and may be assigned to variables of type Object.
```java
// Think of it like numbered boxes in a row
// Index:  0    1    2    3    4
// Value: [10] [20] [30] [40] [50]
```
## Key Characteristics
- Fixed size - cannot grow or shrink
- Zero-indexed - first element is at index 0
- Homogeneous - all elements are of the same type
- Stored in contiguous memory locations

## Declaration and Initialization
**Declare then Initialize**
```java
// Declaration
int[] numbers;              // Preferred style
int numbers2[];              // Also valid (C-style)

// Initialization
numbers = new int[5];       // Creates array of 5 integers
                           // All values initialized to 0

// Access and assign
numbers[0] = 10;
numbers[1] = 20;
numbers[2] = 30;
numbers[3] = 40;
numbers[4] = 50;

System.out.println(numbers[0]);  // Output: 10
```
**Declare and Initialize Together**
```java
// Option A: Array literal
int[] numbers = {10, 20, 30, 40, 50};

// Option B: Using new keyword
int[] numbers = new int[]{10, 20, 30, 40, 50};

// For different types
String[] names = {"Alice", "Bob", "Charlie"};
double[] prices = {9.99, 19.99, 29.99};
boolean[] flags = {true, false, true};
```
**Anonymous Arrays**    
```java
printArray(new int[]{1,2,4});

public static void printArray(int[] arr){
    //method implementation
}
```
**Array Length and Indexing**
```java
int[] numbers = {10, 20, 30, 40, 50};
//length - it is a property, not a method so not parentheses!!!
System.out.println(numbers.length); // output -> 5

//valid indices: starts from 0 to length - 1
System.out.println(numbers[0]);      // First element: 10
System.out.println(numbers[4]);      // Last element: 50
System.out.println(numbers[numbers.length - 1]);  // Last element: 50
```
**Default Values**
```Java
int[] numbers = new int[3];
// [0, 0, 0]

double[] decimals = new double[3];
// [0.0, 0.0, 0.0]

boolean[] flags = new boolean[3];
// [false, false, false]

String[] names = new String[3];
// [null, null, null]

char[] letters = new char[3];
// ['\u0000', '\u0000', '\u0000'] - null character
```
## Iterating Through Arrays
**Traditional For loop**
```java
int[] scores = {85, 92, 78, 95, 88};
//standard iteration
for (int i = 0; i < scores.length; i++) {
    System.out.println("Score " + i + ": " + scores[i]);
}

// Reverse iteration
for (int i = scores.length - 1; i >= 0; i--) {
    System.out.println(scores[i]);
}

// Step by 2
for (int i = 0; i < scores.length; i += 2) {
    System.out.println(scores[i]);
}
```
**When to use**: When you need the index or want to modify array elements

**Enhanced For Loop(For-Each)**
```java
int[] scores = {85, 92, 78, 95, 88};

// Cleaner syntax
for (int score : scores) {
    System.out.println("Score: " + score);
}

// Works with any array type
String[] names = {"Alice", "Bob", "Charlie"};
for (String name : names) {
    System.out.println("Hello, " + name);
}
```
**When to use**: When you just need to read values (no index needed)
**Limitation**: Cannot modify the original array
```java
int[] numbers = {1, 2, 3, 4, 5};

// This DOES NOT modify the array
for (int num : numbers) {
    num = num * 2;  // Only modifies local variable
}
// numbers is still [1, 2, 3, 4, 5]

// To modify, use traditional loop
for (int i = 0; i < numbers.length; i++) {
    numbers[i] = numbers[i] * 2;  // This works!
}
// numbers is now [2, 4, 6, 8, 10]
```

### Using Arrays.toString()
```java
import java.util.Arrays;

int[] numbers = {1, 2, 3, 4, 5};

// Quick printing
System.out.println(numbers);                    // ❌ Prints memory address
System.out.println(Arrays.toString(numbers));   // ✅ Prints [1, 2, 3, 4, 5]

// Works for all types
String[] fruits = {"Apple", "Banana", "Cherry"};
System.out.println(Arrays.toString(fruits));    // [Apple, Banana, Cherry]
```
### Common Arrays Operations
#### Finding elements
```java
 //find min
static int findMin(int[] arr){
    if (arr == null || arr.length == 0) {
        throw new IllegalArgumentException("Array is empty");
    }
    //Todo
}

//find max
static int findMax(int[] arr) {
    if (arr == null || arr.length == 0) {
        throw new IllegalArgumentException("Array is empty");
    }
    //Todo
}
```

#### Search for an element
```java
static int linearSearch(int[] arr, int target){
    //todo
}
```
#### Calc sum and Average
```java
static int sum(int[] arr){
    
}
static double average(int[] arr){
    
}
```

#### Reversing an Array
```java
static void reverseArray(int[] arr){
    //swap
    //increment/decrement your pointers
}
```
### Copying Arrays
**Manual Copy**
```java
//original array
//arrayCopy - with original array size
//assign the values
```
**Arrays.copyOf()**
```java
//original array
//create copy -> Arrays.copyOf(original, desiredLength)
```

**System.arrayCopy()**
```java
//original
//copy - with original's array size
//System.copy(original, source, copy, dest, length)
```
**clone()**
```java
//original
//copy -> original.clone();
```
**Comparing Arrays**
```java
import java.util.Arrays;

int[] arr1 = {1, 2, 3, 4, 5};
int[] arr2 = {1, 2, 3, 4, 5};
int[] arr3 = {5, 4, 3, 2, 1};

// Wrong way - compares references
System.out.println(arr1 == arr2);  // false

// Correct way - compares contents
System.out.println(Arrays.equals(arr1, arr2));  // true
System.out.println(Arrays.equals(arr1, arr3));  // false
```

# Advanced Array Techniques

## Multi-Dimensional Arrays

### 2D Arrays (Matrix)
```java
// Declaration and initialization
int[][] matrix = new int[3][4];  // 3 rows, 4 columns

// Initialize with values
int[][] grid = {
    {1, 2, 3, 4},
    {5, 6, 7, 8},
    {9, 10, 11, 12}
};

// Access elements
System.out.println(grid[0][0]);  // 1 (row 0, col 0)
System.out.println(grid[1][2]);  // 7 (row 1, col 2)

// Get dimensions
System.out.println("Rows: " + grid.length);           // 3
System.out.println("Columns: " + grid[0].length);     // 4
```
### Iterating
```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// Using nested loops
for (int i = 0; i < matrix.length; i++) {
    for (int j = 0; j < matrix[i].length; j++) {
        System.out.print(matrix[i][j] + " ");
    }
    System.out.println();
}

// Using enhanced for loops
for (int[] row : matrix) {
    for (int value : row) {
        System.out.print(value + " ");
    }
    System.out.println();
}

// Print entire 2D array
System.out.println(Arrays.deepToString(matrix));
// [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
```

### Jagged Arrays(Non-Rectangular)
```java
// Each row can have different lengths
int[][] jagged = new int[3][];
jagged[0] = new int[2];  // First row has 2 elements
jagged[1] = new int[4];  // Second row has 4 elements
jagged[2] = new int[3];  // Third row has 3 elements

// Initialize with values
int[][] triangle = {
    {1},
    {2, 3},
    {4, 5, 6},
    {7, 8, 9, 10}
};

// Must check length for each row
for (int i = 0; i < triangle.length; i++) {
    for (int j = 0; j < triangle[i].length; j++) {
        System.out.print(triangle[i][j] + " ");
    }
    System.out.println();
}
```

## Array Sorting
- Arrays.sort()
- Sort in descending order - pass a comparator
- Custom sort with comparators e.g sort by length, sort by length then alphabetically
## Filling Arrays
- Arrays.fill(array, value)
- Arrays.fill(array, startIndex, endIndex-1, value)