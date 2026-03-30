# JDBC — Practice Exercises

These exercises cover the full JDBC lifecycle: connecting, executing DDL and DML, reading result sets, managing transactions, and organising code with the DAO pattern. Work through the sets in order — each one builds on the previous.

All exercises use **H2 in-memory** so no database installation is needed. Add the H2 dependency to your project before starting (see `jdbc.md` section 1).

---

## Exercise Set 1: Connection and Basic CRUD

### Exercise 1.1: First Connection

Write a program that:

1. Opens a connection to `jdbc:h2:mem:library;DB_CLOSE_DELAY=-1` using `DriverManager`.
2. Prints the database product name and version retrieved from `conn.getMetaData()`.
3. Closes the connection automatically using try-with-resources.

**Expected output:**
```
Database: H2
Version : 2.x.x
```

### Exercise 1.2: Create a Table

Extend the previous program to also create the following table using a `Statement`:

```sql
CREATE TABLE books (
    id      INT          PRIMARY KEY AUTO_INCREMENT,
    title   VARCHAR(200) NOT NULL,
    author  VARCHAR(150) NOT NULL,
    year    INT,
    price   DOUBLE
)
```

Print `"Table 'books' created."` after the statement succeeds.

### Exercise 1.3: Insert Rows with PreparedStatement

Using the `books` table from Exercise 1.2, insert the following five books using a single `PreparedStatement` (reuse it for each row — do not create a new one per insert):

| Title                        | Author          | Year | Price |
|------------------------------|-----------------|------|-------|
| Clean Code                   | Robert Martin   | 2008 | 35.99 |
| The Pragmatic Programmer     | Andrew Hunt     | 1999 | 42.50 |
| Effective Java               | Joshua Bloch    | 2018 | 49.95 |
| Design Patterns              | Gang of Four    | 1994 | 54.00 |
| Refactoring                  | Martin Fowler   | 2018 | 38.75 |

Print `"5 books inserted."` after all inserts complete.

### Exercise 1.4: Query and Display Results

Query all books from the table, ordered by year ascending. Iterate the `ResultSet` and print each row in a formatted table:

**Expected output:**
```
ID  Title                        Author           Year   Price
--------------------------------------------------------------
4   Design Patterns              Gang of Four     1994   54.00
2   The Pragmatic Programmer     Andrew Hunt      1999   42.50
1   Clean Code                   Robert Martin    2008   35.99
3   Effective Java               Joshua Bloch     2018   49.95
5   Refactoring                  Martin Fowler    2018   38.75
```

### Exercise 1.5: Update and Delete

1. Update the price of "Clean Code" to `39.99` using a `PreparedStatement`. Print `"Rows updated: 1"`.
2. Delete the book with `id = 4` ("Design Patterns"). Print `"Rows deleted: 1"`.
3. Query and print the remaining books to verify both changes.

---

## Exercise Set 2: Batch Inserts and Generated Keys

### Exercise 2.1: Batch Insert

Create a new table `authors`:

```sql
CREATE TABLE authors (
    id      INT          PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(150) NOT NULL,
    country VARCHAR(100)
)
```

Use `addBatch()` and `executeBatch()` to insert the following six authors in a single batch:

| Name              | Country        |
|-------------------|----------------|
| Robert Martin     | United States  |
| Andrew Hunt       | United States  |
| Joshua Bloch      | United States  |
| Martin Fowler     | United Kingdom |
| Erich Gamma       | Switzerland    |
| Bjarne Stroustrup | Denmark        |

Print `"Batch complete: 6 rows inserted."`.

### Exercise 2.2: Retrieve Generated Keys

Insert a single new author — `"Donald Knuth"` from `"United States"` — using `Statement.RETURN_GENERATED_KEYS`. After the insert, retrieve and print the auto-generated ID:

```
Inserted Donald Knuth with ID: 7
```

### Exercise 2.3: Count with a Scalar Query

Write a method `int countRows(Connection conn, String table)` that takes a connection and a table name and returns the total number of rows in that table. Use it to print:

```
books   : 4 rows
authors : 7 rows
```

Note: because the table name is fixed application code (not user input), a `Statement` is acceptable here. Explain in a comment why `PreparedStatement` cannot be used for the table name itself.

---

## Exercise Set 3: Transactions

### Exercise 3.1: Atomic Transfer

Create this table:

```sql
CREATE TABLE accounts (
    id      INT PRIMARY KEY,
    owner   VARCHAR(100),
    balance DOUBLE
)
```

Insert two accounts:

| ID | Owner | Balance  |
|----|-------|----------|
| 1  | Alice | 1000.00  |
| 2  | Bob   |  500.00  |

Write a method `void transfer(Connection conn, int fromId, int toId, double amount)` that:

1. Disables auto-commit.
2. Deducts `amount` from account `fromId`.
3. Adds `amount` to account `toId`.
4. Commits on success; rolls back on any `SQLException`.
5. Restores auto-commit in a `finally` block.

Call the method to transfer `250.00` from Alice to Bob. Print both balances before and after:

```
Before: Alice=1000.00  Bob=500.00
After:  Alice=750.00   Bob=750.00
```

### Exercise 3.2: Rollback on Error

Modify the `transfer` method so that it first checks whether the sending account has sufficient funds. If `balance < amount`, throw a custom exception `InsufficientFundsException` **inside** the transaction block so the rollback path is exercised.

Attempt a transfer of `2000.00` from Alice to Bob and demonstrate that the rollback leaves balances unchanged:

```
Transfer of 2000.00 failed: Insufficient funds. Balances unchanged.
Alice=750.00  Bob=750.00
```

### Exercise 3.3: Multi-Step Transaction

Create a table `orders` and a table `inventory`:

```sql
CREATE TABLE inventory (
    product_id  INT PRIMARY KEY,
    name        VARCHAR(100),
    stock       INT
)

CREATE TABLE orders (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    product_id  INT,
    quantity    INT,
    total_price DOUBLE
)
```

Seed `inventory` with two products (ID 1: "Widget", stock 100, price 9.99; ID 2: "Gadget", stock 5, price 24.99).

Write a method `void placeOrder(Connection conn, int productId, int quantity, double unitPrice)` that, within a single transaction:

1. Checks that `stock >= quantity` — if not, roll back and throw an exception.
2. Deducts `quantity` from `inventory`.
3. Inserts a row into `orders` with `total_price = quantity * unitPrice`.

Place two valid orders and one that exceeds stock. Print the inventory and orders table after each call.

---

## Exercise Set 4: The DAO Pattern

### Exercise 4.1: Product DAO

Create a `Product` class with fields: `int id`, `String name`, `String category`, `double price`, `int stock`.

Create a `ProductDAO` class with these methods:

| Method                                  | Description                                            |
|-----------------------------------------|--------------------------------------------------------|
| `void createTable()`                    | Creates the `products` table if it does not exist      |
| `int save(Product p)`                   | Inserts a product and returns the generated ID         |
| `Optional<Product> findById(int id)`    | Returns the product with that ID, or empty             |
| `List<Product> findAll()`               | Returns all products ordered by name                   |
| `List<Product> findByCategory(String)` | Returns products in a given category                   |
| `int update(Product p)`                 | Updates name, category, price, and stock by ID         |
| `int delete(int id)`                    | Deletes by ID; returns rows affected                   |

Write a `main` method that:

1. Creates the table.
2. Inserts five products across at least two categories.
3. Retrieves and prints a product by ID.
4. Retrieves and prints all products in one category.
5. Updates the price of one product, then retrieves it to confirm.
6. Deletes one product and prints the remaining list.

### Exercise 4.2: DAO with a Foreign Key

Create two DAOs: `AuthorDAO` and `BookDAO`.

Schema:

```sql
CREATE TABLE authors (
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(150) NOT NULL
)

CREATE TABLE books (
    id        INT PRIMARY KEY AUTO_INCREMENT,
    title     VARCHAR(200) NOT NULL,
    author_id INT REFERENCES authors(id),
    year      INT,
    price     DOUBLE
)
```

`BookDAO` must include:

- `List<Book> findByAuthor(int authorId)` — all books for a given author ID
- `List<String[]> findAllWithAuthorName()` — joins books and authors; returns rows of `[title, authorName, year]`

Write a `main` method that inserts three authors and at least six books (spread across authors), then prints all books alongside their author names using the join query.

### Exercise 4.3: Generic DAO Interface (Advanced)

Define a generic interface:

```java
public interface Dao<T, ID> {
    void createTable() throws SQLException;
    ID   save(T entity)               throws SQLException;
    Optional<T> findById(ID id)       throws SQLException;
    List<T>     findAll()             throws SQLException;
    int         update(T entity)      throws SQLException;
    int         delete(ID id)         throws SQLException;
}
```

Implement this interface with a `CustomerDAO` class for the following table:

```sql
CREATE TABLE customers (
    id      INT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(150),
    email   VARCHAR(200) UNIQUE,
    points  INT DEFAULT 0
)
```

Add one method beyond the interface: `List<Customer> findByMinPoints(int minPoints)` that returns customers whose points are at or above the threshold.

Demonstrate all seven methods in `main`, including `findByMinPoints`.

---

## Exercise Set 5: Real-World Mini-Projects

### Exercise 5.1: Library Management System

Build a console application for a small library using JDBC and the DAO pattern.

**Tables:**

```sql
CREATE TABLE members (
    id      INT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(150),
    email   VARCHAR(200) UNIQUE,
    joined  DATE
)

CREATE TABLE books (
    id        INT PRIMARY KEY AUTO_INCREMENT,
    title     VARCHAR(200),
    author    VARCHAR(150),
    available BOOLEAN DEFAULT TRUE
)

CREATE TABLE loans (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    member_id   INT REFERENCES members(id),
    book_id     INT REFERENCES books(id),
    loan_date   DATE,
    return_date DATE
)
```

**Requirements:**

1. Create a DAO for each table.
2. Write a method `void checkOut(Connection conn, int memberId, int bookId, LocalDate loanDate)` that, in a single transaction:
   - Verifies the book is available (`available = TRUE`).
   - Inserts a row into `loans` with `return_date = NULL`.
   - Sets the book's `available` to `FALSE`.
   - Rolls back if anything fails.
3. Write a method `void returnBook(Connection conn, int loanId, LocalDate returnDate)` that, in a transaction:
   - Sets `return_date` on the loan.
   - Sets the book's `available` back to `TRUE`.
4. Write a query method `List<String[]> activeLoans()` that joins all three tables and returns rows of `[memberName, bookTitle, loanDate]` for loans where `return_date IS NULL`.
5. Seed the database with three members and five books, perform two checkouts, one return, and print the active loans after each operation.

### Exercise 5.2: Simple Bank

Build a bank simulation with accounts and transactions.

**Tables:**

```sql
CREATE TABLE accounts (
    id       INT PRIMARY KEY AUTO_INCREMENT,
    owner    VARCHAR(150),
    type     VARCHAR(20),    -- CHECKING or SAVINGS
    balance  DOUBLE DEFAULT 0.0
)

CREATE TABLE transactions (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    account_id  INT REFERENCES accounts(id),
    type        VARCHAR(20),   -- DEPOSIT, WITHDRAWAL, TRANSFER
    amount      DOUBLE,
    description VARCHAR(200),
    ts          TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)
```

**Requirements:**

1. Create DAOs for both tables.
2. Write `void deposit(Connection conn, int accountId, double amount)` — within a transaction, adds to balance and inserts a DEPOSIT transaction record.
3. Write `void withdraw(Connection conn, int accountId, double amount)` — within a transaction, deducts from balance (reject if balance would go negative) and inserts a WITHDRAWAL record.
4. Write `void transfer(Connection conn, int fromId, int toId, double amount)` — within a single transaction, performs a withdrawal from one account and a deposit to another; both transaction records must be inserted.
5. Write `List<String[]> history(Connection conn, int accountId)` — returns all transaction records for an account ordered by timestamp.
6. Seed three accounts, perform a sequence of deposits, withdrawals, and a transfer, then print the transaction history for each account and the final balance for all accounts.

---

## Tips for Completing These Exercises

1. **Always use try-with-resources** for `Connection`, `Statement`, `PreparedStatement`, and `ResultSet`. Forgetting to close a `ResultSet` is a common resource leak.
2. **Never concatenate user input into SQL strings.** Every external value belongs in a `PreparedStatement` placeholder.
3. **Test your rollback paths deliberately.** After a forced failure, query the database and confirm the state is exactly what it was before the transaction started.
4. **Isolate each exercise.** Use a different in-memory database name per exercise (e.g., `jdbc:h2:mem:ex1`, `jdbc:h2:mem:ex2`) so tables from one exercise do not interfere with another.
5. **Map results in one place.** If you write a `mapRow` helper in your DAO, you only have to update it in one place when the schema changes.
6. **Check `executeUpdate()` return values.** If you expect one row to be affected and get zero, the `WHERE` clause found no match — this is a bug worth catching.

---

## Common Mistakes to Avoid

- Opening a `Connection` inside a loop — connections are expensive; open once, reuse across statements
- Forgetting to call `conn.setAutoCommit(true)` in the `finally` block after a manual transaction
- Using `Statement` for queries that include user-supplied values — always reach for `PreparedStatement`
- Reading a `ResultSet` after its `Statement` has been closed — keep the statement open until you are done with the result set
- Calling `rs.getInt("id")` when the column name in your query alias differs — column names in `get*` calls must match what the query returns, not necessarily the table column name
- Catching `SQLException` and printing the stack trace but not re-throwing — silent failures hide real problems; either handle them or propagate them
