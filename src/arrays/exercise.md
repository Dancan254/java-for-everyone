# Arrays Practice Exercises

Arrays are the simplest and most fundamental collection structure in Java. Every higher-level collection (ArrayList, HashMap, etc.) ultimately manages an array internally. Mastering arrays directly — indexing, traversal, searching, sorting — gives you the mental model you need to understand the whole Collections framework that comes later.

---

## Exercise Set 1: Array Fundamentals

### Exercise 1.1: Descriptive Statistics
Create a class `Statistics` that accepts an `int[]` and provides the following methods:
- `min()` — returns the smallest value.
- `max()` — returns the largest value.
- `sum()` — returns the total of all elements.
- `average()` — returns the mean as a `double`.
- `range()` — returns `max - min`.

Test it in `main` with the array `{14, 3, 88, 42, 7, 55, 21, 99, 6, 73}`.

### Exercise 1.2: Array Rotation
Write a method `rotateLeft(int[] arr, int positions)` that returns a new array with every element shifted left by `positions` places. Elements that fall off the left end wrap around to the right.

**Example:** `{1, 2, 3, 4, 5}` rotated left by 2 → `{3, 4, 5, 1, 2}`

Do not use `System.arraycopy` for the shift itself — implement the rotation using index arithmetic with the modulo operator.

### Exercise 1.3: Frequency Count
Write a method `frequencyCount(int[] arr)` that returns a new array where the value at index `i` is the number of times `i` appears in the input array. Assume all values in the input are in the range 0–99.

**Example:** `{3, 1, 3, 0, 2, 3}` → index 0: 1, index 1: 1, index 2: 1, index 3: 3

---

## Exercise Set 2: Searching and Sorting

### Exercise 2.1: Linear Search vs Binary Search
Implement both search algorithms as methods:
- `linearSearch(int[] arr, int target)` — searches from left to right, returns the index or -1.
- `binarySearch(int[] arr, int target)` — assumes the array is sorted, returns the index or -1.

Demonstrate both with the same sorted array `{5, 12, 17, 23, 31, 44, 56, 68, 75, 89}`. Show that both return the same index for a target that exists, and both return -1 for a target that does not exist.

Then add a comparison: count how many comparisons each algorithm makes for the same target, and print the comparison counts.

### Exercise 2.2: Selection Sort
Implement selection sort manually. In each pass, find the minimum element in the unsorted portion of the array and swap it into position.

Print the array after each pass to make the sorting process visible. Count the total number of swaps performed and print it at the end.

### Exercise 2.3: Merge Two Sorted Arrays
Write a method `merge(int[] a, int[] b)` that returns a new sorted array containing all elements from both input arrays. Both `a` and `b` are already sorted. Do this in O(n+m) time using two index pointers — do not sort after merging.

**Example:** `{1, 3, 5}` and `{2, 4, 6, 8}` → `{1, 2, 3, 4, 5, 6, 8}`

---

## Exercise Set 3: Array Transformations

### Exercise 3.1: Remove Duplicates
Write a method `removeDuplicates(int[] arr)` that returns a new array with all duplicate values removed. Preserve the original order of first appearances. Do not use any collection class — work with arrays only.

**Example:** `{4, 1, 2, 4, 3, 1, 5}` → `{4, 1, 2, 3, 5}`

Hint: allocate a temporary array of the same size, keep a count of unique elements found, then copy only the unique portion.

### Exercise 3.2: Array Intersection and Union
Write two methods:
- `intersection(int[] a, int[] b)` — returns an array of values present in both arrays (no duplicates).
- `union(int[] a, int[] b)` — returns an array of all values present in either array (no duplicates).

Demonstrate with `a = {1, 2, 3, 4, 5}` and `b = {3, 4, 5, 6, 7}`.

Expected:
- Intersection: `{3, 4, 5}`
- Union: `{1, 2, 3, 4, 5, 6, 7}`

### Exercise 3.3: Sliding Window Maximum
Write a method `slidingMax(int[] arr, int k)` that returns an array where each element is the maximum value in a window of size `k` sliding over the input array.

**Example:** `{1, 3, -1, -3, 5, 3, 6, 7}` with k=3 → `{3, 3, 5, 5, 6, 7}`

There are `arr.length - k + 1` windows. For each window starting at index `i`, find the max of `arr[i..i+k-1]`.

---

## Exercise Set 4: Two-Dimensional Arrays

### Exercise 4.1: Matrix Operations
Create a class `MatrixOps` with the following static methods. All methods should handle non-square matrices where applicable.
- `transpose(int[][] matrix)` — returns the transpose (rows become columns).
- `rowSums(int[][] matrix)` — returns a 1D array where each element is the sum of that row.
- `colSums(int[][] matrix)` — returns a 1D array where each element is the sum of that column.
- `printMatrix(int[][] matrix)` — prints the matrix in a readable grid format.

Test with a 3x4 matrix of your choice.

### Exercise 4.2: Diagonal Sum and Saddle Points
Given a square n×n matrix:
1. Compute and print the sum of the main diagonal (top-left to bottom-right).
2. Compute and print the sum of the anti-diagonal (top-right to bottom-left).
3. Find all **saddle points**: positions where the value is the minimum in its row AND the maximum in its column. Print each saddle point's value and coordinates, or print "No saddle points" if none exist.

### Exercise 4.3: Conway's Game of Life (One Step)
Implement a single step of Conway's Game of Life on a boolean 2D grid. The rules:
1. A living cell with fewer than 2 live neighbours dies (underpopulation).
2. A living cell with 2 or 3 live neighbours survives.
3. A living cell with more than 3 live neighbours dies (overpopulation).
4. A dead cell with exactly 3 live neighbours comes alive.

Write a method `nextGeneration(boolean[][] grid)` that returns the next grid state. Treat cells outside the grid boundaries as dead (do not wrap around).

Include a `printGrid` helper that prints `#` for living cells and `.` for dead cells.

Test with the "blinker" pattern (a 3x1 horizontal line) centred in a 5x5 grid. After one step it should become a 1x3 vertical line.

---

## Exercise Set 5: Classic Array Algorithms

### Exercise 5.1: Kadane's Algorithm — Maximum Subarray Sum
Given an array of integers (which may include negative values), find the contiguous subarray with the largest sum. Return the sum. Also return the start and end indices of the subarray.

Kadane's algorithm:
- Track `currentSum` (sum of the current subarray) and `maxSum` (best found so far).
- At each element: `currentSum = max(arr[i], currentSum + arr[i])`.
- Update `maxSum` whenever `currentSum` exceeds it.

**Example:** `{-2, 1, -3, 4, -1, 2, 1, -5, 4}` → max sum is 6 (subarray `{4, -1, 2, 1}`).

### Exercise 5.2: Two-Sum Problem
Given an array of integers and a target sum, find all pairs of elements that add up to the target. A pair (a, b) and (b, a) count as the same pair. Print each unique pair.

Implement two versions:
1. Brute force with nested loops — O(n²).
2. Sorted array + two-pointer approach — sort the array first, then use two pointers starting at both ends and moving inward.

**Example:** array `{2, 7, 11, 15, -3, 8}`, target `9` → pairs: `(2, 7)`, `(-3, 12)` — wait, 12 is not in the array. Correct pairs for target 9: `(2, 7)`, `(-3, 12)` — no. Use the example: array `{1, 5, 3, 4, 6, 2}`, target `7` → `(1, 6)`, `(3, 4)`, `(5, 2)`.

### Exercise 5.3: Stock Buy-Sell for Maximum Profit
Given an array of stock prices where `price[i]` is the price on day `i`, find the maximum profit you can make from a single buy followed by a single sell (you must buy before you sell). You may not hold more than one share at a time.

Write a method `maxProfit(int[] prices)` that returns the maximum profit. If no profit is possible, return 0.

**Example:** `{7, 1, 5, 3, 6, 4}` → buy at 1, sell at 6 → profit 5.

Do this in a single pass (O(n)): track the minimum price seen so far and the maximum profit achievable.

---

## Exercise Set 6: String and Char Arrays

### Exercise 6.1: Anagram Checker
Write a method `areAnagrams(String a, String b)` that returns `true` if the two strings are anagrams (same characters, same frequencies, regardless of order). Use a `char[]` converted to an `int[26]` frequency array — do not sort.

**Example:** `"listen"` and `"silent"` → true. `"hello"` and `"world"` → false.

### Exercise 6.2: Run-Length Encoding
Implement run-length encoding. Given a string, compress consecutive repeated characters into `count + character` pairs.

**Example:** `"aaabbbccddddee"` → `"3a3b2c4d2e"`

Write both a compression method and a decompression method. Demonstrate that decompressing the compressed string gives back the original.

### Exercise 6.3: Longest Common Prefix
Given an array of strings, find the longest common prefix shared by all strings.

**Example:** `{"flower", "flow", "flight"}` → `"fl"`
**Example:** `{"dog", "racecar", "car"}` → `""`

Use a two-loop approach: for each character position, check that all strings have the same character at that position.

---

## Mini-Project 1: Student Grade Book

Create a complete grade book system for a class of students.

**Data:** Store names in a `String[]` and grades in a `double[][]` (each row is one student, each column is one test).

**Requirements:**
1. Compute each student's average grade and letter grade (A: 90+, B: 80+, C: 70+, D: 60+, F: below 60).
2. Find the class average for each test.
3. Find the highest and lowest scoring student by average.
4. Print a formatted report table: student name, individual test scores, average, letter grade.
5. Count how many students are passing (grade >= 60.0).

Use at least 5 students and 4 tests. Hardcode the data.

---

## Mini-Project 2: Inventory Management

Build a simple inventory system for a small store using parallel arrays.

**Arrays:** `String[] productNames`, `int[] quantities`, `double[] prices`.

**Requirements:**
1. Display the full inventory in a formatted table.
2. Write a method `totalInventoryValue()` that computes `sum(quantity[i] * price[i])`.
3. Write a method `findProduct(String name)` that returns the index of a product by name (case-insensitive), or -1 if not found.
4. Write a method `restock(String name, int amount)` that adds `amount` to the quantity of the named product.
5. Write a method `lowStockReport(int threshold)` that prints all products whose quantity is below the threshold.
6. Demonstrate all operations in `main`.

---

## Common Mistakes

### ArrayIndexOutOfBoundsException from incorrect loop bounds
Valid indices are `0` to `array.length - 1`. Using `<=` instead of `<` in a loop condition accesses one element past the end.

```java
int[] arr = {10, 20, 30};

// Wrong: i reaches arr.length (3), which is out of bounds.
for (int i = 0; i <= arr.length; i++) {
    System.out.println(arr[i]); // Throws ArrayIndexOutOfBoundsException when i = 3
}

// Correct:
for (int i = 0; i < arr.length; i++) {
    System.out.println(arr[i]);
}
```

### Comparing arrays with ==
`==` on arrays checks whether the two variables point to the same object, not whether their contents are equal. Two separately created arrays with the same values are not `==`.

```java
int[] a = {1, 2, 3};
int[] b = {1, 2, 3};

System.out.println(a == b);              // false — different objects in memory
System.out.println(Arrays.equals(a, b)); // true  — same element values
```

### Treating an array print as a string representation
Printing an array directly with `System.out.println(arr)` prints a memory address such as `[I@7852e922`, not the contents.

```java
int[] numbers = {1, 2, 3};
System.out.println(numbers);               // [I@7852e922  — not useful
System.out.println(Arrays.toString(numbers)); // [1, 2, 3]   — correct
```

### Forgetting that assignment copies the reference, not the array
Assigning one array variable to another gives both variables a reference to the same underlying array. Modifying one affects the other.

```java
int[] original = {1, 2, 3};
int[] alias    = original;    // alias points to the SAME array as original

alias[0] = 99;
System.out.println(original[0]); // 99 — original was changed too

// To create an independent copy:
int[] copy = Arrays.copyOf(original, original.length);
```

### Enhanced for loop cannot modify elements
The loop variable in `for (int n : arr)` is a copy of the element. Writing to `n` has no effect on the array.

```java
int[] arr = {1, 2, 3};
for (int n : arr) {
    n *= 2;  // Changes only the local variable n; arr is unchanged.
}

// To modify elements, use an index-based loop:
for (int i = 0; i < arr.length; i++) {
    arr[i] *= 2;  // Modifies the actual array element.
}
```

### Attempting to resize an array
Arrays have a fixed size set at creation. You cannot add or remove elements. If you need a resizable collection, use `ArrayList`.

```java
int[] arr = new int[3];
// arr.length = 3; there is no way to change this.

// If you need a different size, create a new array:
arr = Arrays.copyOf(arr, arr.length + 1); // New array of size 4, original values copied.

// For dynamic size, use ArrayList instead:
// java.util.ArrayList<Integer> list = new ArrayList<>();
```

---

Solutions for these exercises are in the `solutions/` subfolder.
