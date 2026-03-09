# Arrays

An array is a **fixed-size**, **contiguous block** of memory that stores homogeneous elements (all the same type). In Java, arrays are objects, are dynamically created, and can be assigned to variables of type `Object`.

```java
// Think of it like numbered boxes in a row
// Index:  0    1    2    3    4
// Value: [10] [20] [30] [40] [50]
```

## Key Characteristics

- Fixed size — cannot grow or shrink after creation
- Zero-indexed — the first element is at index 0
- Homogeneous — all elements must be of the same type
- Stored in contiguous memory locations

---

## Declaration and Initialization

### Declare, Then Initialize

```java
// Declaration
int[] numbers;        // Preferred style
int numbers2[];       // Also valid (C-style, less common)

// Initialization
numbers = new int[5]; // Creates an array of 5 integers; all values default to 0

// Assign values
numbers[0] = 10;
numbers[1] = 20;
numbers[2] = 30;
numbers[3] = 40;
numbers[4] = 50;

System.out.println(numbers[0]); // Output: 10
```

### Declare and Initialize Together

```java
// Option A: Array literal
int[] numbers = {10, 20, 30, 40, 50};

// Option B: Using the new keyword
int[] numbers = new int[]{10, 20, 30, 40, 50};

// Other types
String[] names  = {"Alice", "Bob", "Charlie"};
double[] prices = {9.99, 19.99, 29.99};
boolean[] flags = {true, false, true};
```

### Anonymous Arrays

Pass an array inline without storing it in a variable:

```java
printArray(new int[]{1, 2, 4});

public static void printArray(int[] arr) {
    // method implementation
}
```

### Array Length and Indexing

```java
int[] numbers = {10, 20, 30, 40, 50};

// .length is a property, not a method — no parentheses
System.out.println(numbers.length);              // 5

// Valid indices: 0 to length - 1
System.out.println(numbers[0]);                  // First element: 10
System.out.println(numbers[4]);                  // Last element: 50
System.out.println(numbers[numbers.length - 1]); // Last element: 50
```

### Default Values

When you initialize an array with `new`, Java fills it with default values:

```java
int[]     numbers  = new int[3];     // [0, 0, 0]
double[]  decimals = new double[3];  // [0.0, 0.0, 0.0]
boolean[] flags    = new boolean[3]; // [false, false, false]
String[]  names    = new String[3];  // [null, null, null]
char[]    letters  = new char[3];    // ['\u0000', '\u0000', '\u0000']
```

---

## Iterating Through Arrays

### Traditional For Loop

```java
int[] scores = {85, 92, 78, 95, 88};

// Standard iteration
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

**Use when:** you need the index, or you need to modify elements.

### Enhanced For Loop (For-Each)

```java
int[] scores = {85, 92, 78, 95, 88};

for (int score : scores) {
    System.out.println("Score: " + score);
}

String[] names = {"Alice", "Bob", "Charlie"};
for (String name : names) {
    System.out.println("Hello, " + name);
}
```

**Use when:** you only need to read values (no index required).

**Limitation:** cannot modify the original array through the loop variable.

```java
int[] numbers = {1, 2, 3, 4, 5};

// This does NOT modify the array — num is a local copy
for (int num : numbers) {
    num = num * 2;
}
// numbers is still [1, 2, 3, 4, 5]

// To modify, use a traditional loop
for (int i = 0; i < numbers.length; i++) {
    numbers[i] = numbers[i] * 2;
}
// numbers is now [2, 4, 6, 8, 10]
```

### Using Arrays.toString()

```java
import java.util.Arrays;

int[] numbers = {1, 2, 3, 4, 5};

System.out.println(numbers);               // Prints memory address — not useful
System.out.println(Arrays.toString(numbers)); // Prints [1, 2, 3, 4, 5]

String[] fruits = {"Apple", "Banana", "Cherry"};
System.out.println(Arrays.toString(fruits));  // [Apple, Banana, Cherry]
```

---

## Common Array Operations

### Finding Elements

```java
static int findMin(int[] arr) {
    if (arr == null || arr.length == 0) {
        throw new IllegalArgumentException("Array is empty");
    }
    // TODO: implement
}

static int findMax(int[] arr) {
    if (arr == null || arr.length == 0) {
        throw new IllegalArgumentException("Array is empty");
    }
    // TODO: implement
}
```

### Linear Search

```java
static int linearSearch(int[] arr, int target) {
    // TODO: return the index of target, or -1 if not found
}
```

### Sum and Average

```java
static int sum(int[] arr) {
    // TODO: implement
}

static double average(int[] arr) {
    // TODO: implement
}
```

### Reversing an Array

```java
static void reverseArray(int[] arr) {
    // Hint: swap elements using two pointers — one from each end
}
```

---

## Copying Arrays

### Manual Copy

```java
// Create a new array of the same size and copy element by element
int[] original = {1, 2, 3, 4, 5};
int[] copy = new int[original.length];
for (int i = 0; i < original.length; i++) {
    copy[i] = original[i];
}
```

### Arrays.copyOf()

```java
import java.util.Arrays;

int[] original = {1, 2, 3, 4, 5};
int[] copy = Arrays.copyOf(original, original.length);
```

### System.arraycopy()

```java
int[] original = {1, 2, 3, 4, 5};
int[] copy = new int[original.length];
System.arraycopy(original, 0, copy, 0, original.length);
// System.arraycopy(source, srcPos, dest, destPos, length)
```

### clone()

```java
int[] original = {1, 2, 3, 4, 5};
int[] copy = original.clone();
```

### Comparing Arrays

```java
import java.util.Arrays;

int[] arr1 = {1, 2, 3, 4, 5};
int[] arr2 = {1, 2, 3, 4, 5};
int[] arr3 = {5, 4, 3, 2, 1};

// Comparing references — almost always false for separate arrays
System.out.println(arr1 == arr2);             // false

// Comparing contents
System.out.println(Arrays.equals(arr1, arr2)); // true
System.out.println(Arrays.equals(arr1, arr3)); // false
```

---

## Advanced Array Techniques

### Multi-Dimensional Arrays

#### 2D Arrays (Matrix)

```java
// Declaration and initialization
int[][] matrix = new int[3][4]; // 3 rows, 4 columns

// Initialize with values
int[][] grid = {
    {1,  2,  3,  4},
    {5,  6,  7,  8},
    {9, 10, 11, 12}
};

// Access elements
System.out.println(grid[0][0]); // 1 (row 0, col 0)
System.out.println(grid[1][2]); // 7 (row 1, col 2)

// Get dimensions
System.out.println("Rows: "    + grid.length);    // 3
System.out.println("Columns: " + grid[0].length); // 4
```

#### Iterating a 2D Array

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// Using nested index loops
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

// Print entire 2D array as a string
System.out.println(Arrays.deepToString(matrix));
// [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
```

### Jagged Arrays (Non-Rectangular)

Each row can have a different number of columns:

```java
int[][] jagged = new int[3][];
jagged[0] = new int[2]; // First row has 2 elements
jagged[1] = new int[4]; // Second row has 4 elements
jagged[2] = new int[3]; // Third row has 3 elements

// Initialize with values
int[][] triangle = {
    {1},
    {2, 3},
    {4, 5, 6},
    {7, 8, 9, 10}
};

// You must check each row's length individually
for (int i = 0; i < triangle.length; i++) {
    for (int j = 0; j < triangle[i].length; j++) {
        System.out.print(triangle[i][j] + " ");
    }
    System.out.println();
}
```

---

## Sorting Arrays

```java
import java.util.Arrays;

int[] numbers = {5, 2, 8, 1, 9};

// Sort in ascending order (in-place)
Arrays.sort(numbers);
// numbers is now [1, 2, 5, 8, 9]

// Sort a portion of the array
Arrays.sort(numbers, 1, 4); // sorts indices 1 to 3 (inclusive)

// Sort in descending order — requires an Integer[] (boxed type)
Integer[] boxed = {5, 2, 8, 1, 9};
Arrays.sort(boxed, (a, b) -> b - a);
// boxed is now [9, 8, 5, 2, 1]
```

---

## Filling Arrays

```java
import java.util.Arrays;

int[] numbers = new int[5];

// Fill entire array with a value
Arrays.fill(numbers, 7);
// numbers is now [7, 7, 7, 7, 7]

// Fill a range (startIndex inclusive, endIndex exclusive)
Arrays.fill(numbers, 1, 4, 0);
// numbers is now [7, 0, 0, 0, 7]
```

---

**Next:** [Object-Oriented Programming](../oop/oop.md)
