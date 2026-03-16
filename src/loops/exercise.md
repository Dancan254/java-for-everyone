# Loops Practice Exercises

Loops are the engine of repetition in Java. The exercises below progress from basic iteration through increasingly complex patterns: nested loops, loop control, algorithm implementation, and complete programs that combine multiple loop types. Work through each set in order before moving to the next.

---

## Exercise Set 1: Basic for Loops

### Exercise 1.1: Number Table
Write a program that prints a table of numbers from 1 to 20, showing each number, its square, and its cube.

**Expected output (first two rows):**
```
Number    Square    Cube
1         1         1
2         4         8
...
```

Use `printf` or `String.format` to align the columns.

### Exercise 1.2: Sum of Odds
Write a program that uses a `for` loop to compute the sum of all odd numbers from 1 to 99 inclusive. Print each odd number as it is added, then print the total at the end.

### Exercise 1.3: Multiplication Table Grid
Write a program that prints a full 10x10 multiplication table. Rows represent the first factor, columns represent the second. Align each cell so columns are consistent widths.

**Expected output (first two rows):**
```
   1   2   3   4   5   6   7   8   9  10
   2   4   6   8  10  12  14  16  18  20
...
```

---

## Exercise Set 2: While Loops and Input Validation

### Exercise 2.1: Collatz Sequence
The Collatz conjecture says: starting from any positive integer n, if n is even divide it by 2; if n is odd multiply it by 3 and add 1. Repeat until n reaches 1.

Write a program that accepts a starting number from the user and prints every step of the sequence. Also print how many steps it took.

**Example for starting value 6:** 6 → 3 → 10 → 5 → 16 → 8 → 4 → 2 → 1 (8 steps)

### Exercise 2.2: Digit Sum Loop
Write a program that accepts an integer from the user and uses a `while` loop to compute the sum of its digits. Do not convert the number to a String — use integer arithmetic (`% 10` to extract the last digit, `/ 10` to remove it).

**Example:** 4567 → 4 + 5 + 6 + 7 = 22

### Exercise 2.3: Running Average
Write a program that continuously reads doubles from the user, updating and printing a running average after each input. Stop when the user enters `-1`. At the end print the count of numbers entered and the final average.

Handle the edge case where no valid numbers were entered before `-1`.

---

## Exercise Set 3: Nested Loops and Patterns

### Exercise 3.1: Hollow Rectangle
Write a program that reads a width and height from the user and prints a hollow rectangle made of asterisks. Only the border cells contain `*`; interior cells are spaces.

**Example (width 6, height 4):**
```
* * * * * *
*         *
*         *
* * * * * *
```

### Exercise 3.2: Number Pyramid
Write a program that reads a number `n` from the user and prints a right-aligned number pyramid with `n` rows.

**Example (n = 5):**
```
        1
      1 2
    1 2 3
  1 2 3 4
1 2 3 4 5
```

### Exercise 3.3: Prime Sieve
Write a program that uses nested loops to find and print all prime numbers up to 200. For each prime, print it on the same line. Print a count of how many primes were found at the end.

Do not use `Math.sqrt` — instead, check all potential factors from 2 up to `n / 2`.

---

## Exercise Set 4: Break, Continue, and Labeled Loops

### Exercise 4.1: First Missing Number
Given the sequence 1, 2, 3, ..., n with one number missing somewhere in a shuffled array, use a loop with `break` to find and report the missing number.

Hardcode an array such as `{3, 7, 1, 2, 8, 4, 6}` (expected range 1–8, missing 5) and find the gap.

### Exercise 4.2: Skipping Multiples
Write a program that prints all integers from 1 to 50, but skips any number that is a multiple of 3 OR a multiple of 7. Use `continue` to implement the skipping. Print the count of numbers actually printed.

### Exercise 4.3: Labeled Break in a Matrix Search
Create a 5x5 2D array filled with random integers between 1 and 100. Write a program that searches for the first value greater than 90. Use a labeled `break` to exit both loops immediately when the value is found. Print the value and its row/column position. If no value greater than 90 exists, print an appropriate message.

---

## Exercise Set 5: Algorithm Implementation with Loops

### Exercise 5.1: Binary Search
Implement binary search manually using a `while` loop. Given a sorted array of integers and a target value, return the index of the target or -1 if not found. Demonstrate it with an array of 15 elements and three searches: one that finds the target, one that finds the boundary element, and one that returns -1.

Binary search algorithm:
1. Maintain `low` and `high` indices.
2. Compute `mid = (low + high) / 2`.
3. If `arr[mid] == target`, return `mid`.
4. If `arr[mid] < target`, set `low = mid + 1`.
5. If `arr[mid] > target`, set `high = mid - 1`.
6. Repeat until `low > high`.

### Exercise 5.2: Bubble Sort
Implement the bubble sort algorithm for an array of integers. Print the array after each full pass to make the sorting process visible. Include an optimization: if no swap occurred in a full pass, the array is already sorted — stop early and report how many passes were needed.

### Exercise 5.3: Fibonacci with Memoization
Write a program that computes and prints the first 30 Fibonacci numbers using a `for` loop and an array to store previously computed values. After printing the sequence, ask the user for an index n (0-based) and print `fib(n)` using the stored values in O(1) time.

---

## Exercise Set 6: Advanced Challenges

### Exercise 6.1: FizzBuzz Extended
The classic FizzBuzz: print numbers 1 to 100. Print "Fizz" if divisible by 3, "Buzz" if divisible by 5, "FizzBuzz" if divisible by both. Extend it: also print "Bang" if divisible by 7, combining with Fizz and Buzz as needed (e.g., 21 → "FizzBang", 35 → "BuzzBang", 105 → "FizzBuzzBang").

### Exercise 6.2: Pascal's Triangle
Print the first 10 rows of Pascal's Triangle. Each row `n` has `n+1` elements. The first and last element of each row are 1. Every other element is the sum of the two elements directly above it.

Store the triangle in a 2D array and compute it row by row.

### Exercise 6.3: Roman Numeral Converter
Write a program that converts a positive integer (1–3999) to its Roman numeral representation using a loop. Use two parallel arrays: one for decimal values and one for the corresponding Roman symbols. Repeatedly subtract the largest Roman value that fits and append its symbol.

**Symbols:** M=1000, CM=900, D=500, CD=400, C=100, XC=90, L=50, XL=40, X=10, IX=9, V=5, IV=4, I=1

---

## Mini-Project 1: Number Guessing Game with Statistics

Build a complete number guessing game. The program picks a random number between 1 and 100 and allows the player up to 7 guesses.

**Requirements:**
- Use a `do-while` loop for the main game loop.
- After each incorrect guess, tell the player whether to go higher or lower.
- When the player guesses correctly (or runs out of attempts), display how many guesses were used.
- After each completed game, ask the player if they want to play again.
- Track statistics across multiple games: total games played, total wins, and the best (fewest guesses) winning game. Print the stats when the player quits.

**Edge cases to handle:**
- Input outside the range 1–100 should not count as a guess.
- Track the number of remaining guesses and display it after each attempt.

---

## Mini-Project 2: Text Analyzer

Write a program that reads a sentence from the user and produces a detailed character analysis using loops.

**Requirements:**
1. Count the total number of characters (including spaces).
2. Count letters, digits, spaces, and other characters separately.
3. Count vowels and consonants (letters that are not vowels) separately.
4. Find the most frequently occurring letter (case-insensitive). Use an `int[26]` array indexed by letter position.
5. Print a frequency table showing each letter and how many times it appeared (only show letters that appeared at least once).

**Example input:** "Hello, World! 123"

Produce output organized in labeled sections.

---

## Common Mistakes

### Infinite loops from missing updates
The most common loop bug: the condition variable is never modified inside the loop body, so the condition never becomes false.

```java
// Wrong: i never changes — the loop runs forever.
int i = 0;
while (i < 10) {
    System.out.println(i);
    // Missing: i++;
}

// Correct: i is incremented on every iteration.
int i = 0;
while (i < 10) {
    System.out.println(i);
    i++;
}
```

### Off-by-one errors
Choosing `<` versus `<=` in the condition controls whether the boundary value is included or excluded.

```java
// Prints 1 through 9 — the number 10 is never printed.
for (int i = 1; i < 10; i++) {
    System.out.println(i);
}

// Prints 1 through 10 — boundary is inclusive.
for (int i = 1; i <= 10; i++) {
    System.out.println(i);
}
```

### Using the loop variable after the loop
The loop variable `i` is declared inside the `for` statement and is out of scope after the closing brace. Trying to read it afterward is a compile error.

```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
// System.out.println(i); // Compile error: i is not in scope here.
```

Declare the variable before the loop if you need its final value after the loop exits.

### Modifying the loop variable inside a for loop body
Modifying the loop variable inside the body alongside the `for` update clause produces confusing results and is almost always a logic error.

```java
// Wrong: i is incremented in both the update clause and the body.
for (int i = 0; i < 10; i++) {
    System.out.println(i);
    i++; // i advances by 2 each iteration — only even values are printed.
}
```

### Enhanced for loop cannot modify the array
The loop variable in an enhanced for loop is a copy of the element, not a reference to the original slot. Writing to it has no effect on the array.

```java
int[] numbers = {1, 2, 3};
for (int n : numbers) {
    n = n * 10; // This changes the local copy only; numbers is unchanged.
}
// numbers is still {1, 2, 3}.

// Use an index-based loop to modify elements:
for (int i = 0; i < numbers.length; i++) {
    numbers[i] = numbers[i] * 10; // Modifies the actual array slot.
}
```

### Forgetting that do-while always executes once
A `do-while` loop runs its body before checking the condition for the first time. If your logic requires the condition to be checked before the first execution, use `while` instead.

---

Solutions for these exercises are in the `solutions/` subfolder.
