# Mini Projects

These projects are designed to be solved using only what has been covered in this course — from variables all the way through OOP, inheritance, polymorphism, and downcasting. Each project builds on the previous ones in complexity. Read the description, plan your classes on paper first, then start coding.

---

## Project 1 — Console Quiz Game

**Concepts:** variables, conditionals, loops, arrays, methods, Scanner

### Description
Build a multiple-choice quiz that runs entirely in the terminal. The program asks the player a series of questions, accepts their answers, and prints a final score report.

### Classes to build
- `Question` — holds the question text, four answer options, and the correct answer index
- `Quiz` — holds an array of `Question` objects and drives the game loop

### Required features
- Store at least 5 questions in an array
- Display each question with numbered choices (1–4)
- Accept user input with Scanner and validate it (reject anything outside 1–4)
- Track correct and incorrect answers throughout the game
- Print a summary at the end: total score, which questions were missed, and a pass/fail message based on a threshold of your choice
- A `getLetterGrade(int score, int total)` method that returns "A", "B", "C", "D", or "F"

### Bonus
- Shuffle the question order before the quiz starts (look up `Arrays` and index swapping)
- Allow the player to choose difficulty by filtering questions tagged with a difficulty level
- Keep a high-score in a static field so re-runs compare against the previous best

---

## Project 2 — Student Grade Manager

**Concepts:** OOP basics, encapsulation, arrays, methods, loops, conditionals

### Description
Build a system to manage a small classroom of students. Each student has a name and a set of grades. The system should compute statistics and print a formatted report.

### Classes to build
- `Student` — encapsulates a student's name and an array of double grades. All fields must be `private`
- `Classroom` — holds an array of `Student` objects and provides classroom-wide statistics

### Required features for `Student`
- Constructor that takes a name and an array of grades
- `getAverage()` — returns the average of all grades
- `getHighest()` and `getLowest()` — return the highest and lowest grade
- `getLetterGrade()` — maps the average to A/B/C/D/F
- `toString()` override that prints a neat one-line summary
- A setter for grades that rejects any value outside 0–100

### Required features for `Classroom`
- `addStudent(Student s)` — adds a student (handle a full roster gracefully)
- `getClassAverage()` — average across all students
- `getTopStudent()` — returns the `Student` with the highest average
- `printReport()` — loops through all students and prints each one

### Bonus
- Sort students by average (descending) before printing the report
- Count how many students are passing vs failing and display the ratio

---

## Project 3 — Bank Account System

**Concepts:** OOP basics, encapsulation, inheritance, polymorphism, method overriding

### Description
Model a simple bank with two types of accounts. Both share common behaviour defined in a parent class, but each has rules specific to its account type.

### Classes to build
- `BankAccount` (parent) — holds owner name, account number, and balance. All fields `private`
- `SavingsAccount extends BankAccount` — adds an interest rate; withdrawals are blocked if the balance would drop below a minimum balance
- `CheckingAccount extends BankAccount` — allows an overdraft up to a set limit; charges a small fee per withdrawal

### Required features for `BankAccount`
- `deposit(double amount)` — adds to balance; reject negative amounts
- `withdraw(double amount)` — deducts from balance; each subclass enforces its own rules by overriding this
- `getBalance()`, `getOwner()`, `getAccountNumber()`
- `toString()` override

### Required features for `SavingsAccount`
- `applyInterest()` — increases balance by the interest rate percentage
- Override `withdraw` to enforce the minimum balance rule

### Required features for `CheckingAccount`
- Override `withdraw` to allow overdraft up to the limit and subtract the fee
- `getOverdraftUsed()` — how far into overdraft the account currently is

### In `main`
- Create at least one of each account type stored in a `BankAccount[]` array
- Loop through the array calling `deposit` and `withdraw` polymorphically
- Print the final state of each account

### Bonus
- Add a `TransactionHistory` — a small array that records the last 10 transactions (type + amount) on any account
- Add a `transfer(BankAccount from, BankAccount to, double amount)` static utility method

---

## Project 4 — Shape Area Calculator

**Concepts:** abstraction (abstract classes), polymorphism, arrays, method overriding, encapsulation

### Description
Model a set of geometric shapes using an abstract base class. A runner class collects different shapes in one array and performs calculations across all of them without knowing the specific type of each shape.

### Classes to build
- `Shape` (abstract) — defines color and an abstract `getArea()` method, plus a concrete `displayInfo()` that prints type, color, and area
- `Circle extends Shape`
- `Rectangle extends Shape`
- `Triangle extends Shape`

### Required features for each shape
- A constructor that sets color and all dimension fields
- Override `getArea()` with the correct formula
- Override `toString()` to include dimensions

### Required features in `main`
- Create an array of at least 6 shapes (mix of all three types)
- A `printAllShapes(Shape[] shapes)` method — uses the polymorphic `displayInfo()` call
- A `getTotalArea(Shape[] shapes)` method — sums all areas
- A `findLargest(Shape[] shapes)` method — returns the `Shape` with the biggest area

### Bonus
- Add a `getPerimeter()` abstract method and implement it in each subclass
- Add a `Cylinder` class that has a `Circle` as a field (composition) and calculates volume
- Sort the array by area before printing

---

## Project 5 — Animal Kingdom Simulator

**Concepts:** inheritance, polymorphism, downcasting, instanceof / pattern matching, arrays

### Description
Simulate a day at a wildlife sanctuary. A caretaker has a mixed group of animals to look after. Each animal type has behaviours beyond what the base class knows about, so the caretaker must identify each animal's actual type before interacting with it.

### Classes to build
- `Animal` (parent) — name, age, `eat()`, `sleep()`, `makeSound()`
- `Dog extends Animal` — breed, `bark()`, `fetch()`
- `Cat extends Animal` — isIndoor, `purr()`, `scratch()`
- `Bird extends Animal` — canFly, `chirp()`, `fly()` (only if `canFly` is true)
- `Caretaker` — holds an `Animal[]` and has methods to interact with them

### Required features for `Caretaker`
- `feedAll()` — calls `eat()` on every animal polymorphically
- `morningRoutine()` — loops through all animals; use `instanceof` (or pattern matching) to call type-specific methods:
  - Dogs: call `bark()` then `fetch()`
  - Cats: call `purr()`
  - Birds: call `chirp()`, then call `fly()` only if `canFly` is true
- `findAnimalByName(String name)` — returns the matching `Animal` or `null`
- `countByType(String type)` — returns how many animals match "Dog", "Cat", or "Bird"

### In `main`
- Create a mixed `Animal[]` with at least 2 of each type
- Give the array to a `Caretaker` and run the morning routine
- Demonstrate a safe downcast: find an animal by name, confirm it is a `Dog` with `instanceof`, then call `fetch()` on it

### Bonus
- Add a `Wild` interface with a `hunt()` method. Create a `Wolf extends Animal implements Wild` class and handle it in the morning routine
- Add an `Adoptable` interface. Mark some animals as adoptable and print an adoption listing

---

## Project 6 — Library Management System (Capstone)

**Concepts:** all four OOP pillars, inheritance, polymorphism, abstraction, downcasting, instanceof, arrays, encapsulation, Scanner

### Description
Build a console-driven library system. Members can borrow and return books. The librarian can search the catalogue, view all borrowed books, and see which members have overdue items. This project ties together everything covered in the course.

### Classes to build
- `LibraryItem` (abstract) — title, author, year, itemId. Abstract method `getItemType()` returns a string like `"Book"` or `"Magazine"`
- `Book extends LibraryItem` — adds ISBN and genre
- `Magazine extends LibraryItem` — adds issue number and month
- `Member` — name, memberId, and an array of currently borrowed items (max 3 at a time)
- `Library` — holds an array of `LibraryItem` objects and an array of `Member` objects

### Required features for `Member`
- `borrowItem(LibraryItem item)` — adds item to their list; reject if already at limit or if item is unavailable
- `returnItem(LibraryItem item)` — removes item from their list
- `getBorrowedItems()` — prints all currently borrowed titles
- `toString()` override

### Required features for `Library`
- `searchByTitle(String title)` — returns the first matching item (case-insensitive)
- `searchByAuthor(String author)` — returns an array of all matches
- `listAvailable()` — prints all items not currently borrowed by anyone
- `listBorrowed()` — prints all items that are checked out, along with who has them
- `registerMember(Member m)` — adds a member
- `findMemberById(String id)` — returns the matching `Member` or `null`

### Use downcasting in at least one place
When printing the catalogue, check if each `LibraryItem` is a `Book` or a `Magazine` using `instanceof`, and print the extra fields (ISBN / issue number) only when appropriate.

### Menu system
Wrap everything in a `main` loop that shows the following options until the user exits:
```
1. Search for an item by title
2. Borrow an item
3. Return an item
4. View all available items
5. View all borrowed items
6. View a member's borrowed items
7. Exit
```

### Bonus
- Add a `due date` concept: each borrowed item gets a borrow date, and the library can list overdue items (borrowed more than 14 days ago)
- Add an `AudioBook extends Book` class with a narrator and duration in minutes; demonstrate polymorphic handling alongside regular books
- Track borrow history per member — a fixed-size log of the last 10 items ever borrowed

---

## Tips for all projects

- Plan your classes on paper before writing any code. Draw the fields, methods, and relationships
- Start with a simple version that compiles and runs, then add features one at a time
- Test each method independently before wiring everything together in `main`
- Use `toString()` on every class — it makes debugging much faster
- If a project feels too big, break it into phases: get one class working, then add the next
