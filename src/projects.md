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

## Project 7 — Contact Book

**Concepts:** records, interfaces, arrays, methods, encapsulation, Scanner

### Description
Build a command-line contact book that stores and manages personal contacts. All data objects must be modelled as records. The project focuses on practising records as immutable data carriers, compact constructor validation, static factory methods, and records implementing interfaces.

### Records to build
- `PhoneNumber(String countryCode, String number)` — compact constructor validates that both fields are non-blank and that `number` contains only digits and dashes; add an `isValid()` instance method
- `Address(String street, String city, String country)` — compact constructor strips and normalises all fields to trimmed values
- `Contact(String firstName, String lastName, PhoneNumber phone, Address address)` — compact constructor validates that first and last name are non-blank; add a `fullName()` method that returns first + last, and a static factory method `Contact.of(String firstName, String lastName, String countryCode, String number, String street, String city, String country)` that builds the nested records internally
- `SearchResult(Contact contact, String matchedOn)` — carries a found contact and a string describing which field matched (e.g. `"last name"`)

### Interface to define
- `Printable` with a single method `void printDetails()` — `Contact` must implement it and print all fields in a clean, readable format

### Required features
- Store contacts in a fixed-size `Contact[]` array (capacity of 20)
- `addContact(Contact c)` — adds to the array; reject duplicates by full name (case-insensitive)
- `findByLastName(String lastName)` — returns a `SearchResult` or `null`
- `findByCity(String city)` — returns an array of all `Contact` records whose address city matches
- `deleteContact(String fullName)` — removes a contact by full name (shift remaining elements)
- `listAll()` — calls `printDetails()` on every stored contact polymorphically via the `Printable` interface
- A menu loop with options: Add contact, Search by last name, Search by city, Delete contact, List all, Exit

### Bonus
- Add an `Email(String address)` record with validation and add it as a component to `Contact`
- Add a `ContactStats(int total, int citiesRepresented, String mostCommonCountry)` record and a method that computes and returns one
- Use a generic `Result<T>(T value, String errorMessage)` record as the return type for `addContact` and `deleteContact` to communicate success or failure without throwing exceptions

---

## Tips for all projects

- Plan your classes on paper before writing any code. Draw the fields, methods, and relationships
- Start with a simple version that compiles and runs, then add features one at a time
- Test each method independently before wiring everything together in `main`
- Use `toString()` on every class — it makes debugging much faster
- If a project feels too big, break it into phases: get one class working, then add the next
