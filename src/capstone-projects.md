# Capstone Projects — Core Java Before Spring Boot

These projects are designed for students who have completed the full core Java curriculum — OOP, Collections, Streams, Lambdas, Exceptions, File I/O, and JDBC. Each project is a standalone console application that feels like real software. Build them in order: they increase in complexity and the later ones build on patterns introduced in the earlier ones.

The goal is not just to write code that works. Each project has a design constraint that forces you to think about architecture, separation of concerns, and the kind of decisions you will face daily as a Java developer.

---

## Project 1 — Task Manager CLI

**Topics:** Collections, File I/O, Streams, Lambdas, Enums, Date and Time API

### Description

Build a fully persistent command-line task manager. Tasks are saved to a CSV file so data survives between runs. The user interacts through a numbered menu.

### Domain model

```
Task
  - int id                     (auto-assigned)
  - String title
  - String description
  - Priority priority           (enum: HIGH, MEDIUM, LOW)
  - Status status               (enum: TODO, IN_PROGRESS, DONE)
  - LocalDate dueDate
  - LocalDateTime createdAt
```

### Required features

- **Add task** — prompt for title, description, priority, and due date (parse from `yyyy-MM-dd` string)
- **List tasks** — display all tasks in a table; support filtering by status and sorting by due date or priority
- **Update task** — change status (move a task from TODO to IN_PROGRESS to DONE)
- **Delete task** — remove by ID
- **Summary** — print how many tasks exist per status and how many are overdue (due date is in the past and status is not DONE)

### Persistence

Write tasks to `tasks.csv`. Load the file on startup. Handle the case where the file does not exist yet (first run).

```
id,title,description,priority,status,dueDate,createdAt
1,Buy groceries,Milk eggs bread,LOW,TODO,2024-12-01,2024-11-20T09:00:00
```

### Architecture constraint

Separate your code into three layers:

- `Task.java` — the domain object
- `TaskRepository.java` — reads and writes the CSV file; no business logic
- `TaskService.java` — validates input, applies business rules (e.g. cannot move directly from TODO to DONE), delegates storage to the repository
- `TaskApp.java` — the menu loop; calls `TaskService` only

### Bonus

- Export a filtered view (e.g. only DONE tasks) to a new CSV file
- Add a `search(String keyword)` method that finds tasks whose title or description contains the keyword (case-insensitive), using Streams
- Support sorting by multiple criteria: first by priority, then by due date

---

## Project 2 — Student Enrollment System with JDBC

**Topics:** JDBC, SQL, Collections, OOP, Exception handling

### Description

Build a console application to manage students, courses, and enrollments backed by a real SQL database. This project introduces you to relational data modelling and multi-table queries.

### Schema

```sql
CREATE TABLE students (
    id    INT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL
);

CREATE TABLE courses (
    id       INT PRIMARY KEY AUTO_INCREMENT,
    code     VARCHAR(20) UNIQUE NOT NULL,
    title    VARCHAR(150) NOT NULL,
    capacity INT NOT NULL
);

CREATE TABLE enrollments (
    student_id INT,
    course_id  INT,
    grade      DOUBLE,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES students(id),
    FOREIGN KEY (course_id)  REFERENCES courses(id)
);
```

### Required features

- **Student CRUD** — add, list, find by ID, delete (cannot delete a student who is enrolled)
- **Course CRUD** — add, list, find by code, delete (cannot delete a course with active enrollments)
- **Enroll** — enroll a student in a course; reject if the course is at capacity or the student is already enrolled
- **Grade** — assign a numeric grade (0–100) to an enrollment
- **Student report** — list all courses a student is enrolled in with their grade
- **Course report** — list all enrolled students with grades; show the class average
- **Top performers** — list students with a GPA (average across all courses) above a given threshold

### Architecture constraint

Use the DAO pattern. Create `StudentDAO`, `CourseDAO`, and `EnrollmentDAO`. Each DAO has its own `Connection` passed via the constructor. Business logic that spans multiple DAOs lives in a `EnrollmentService` class.

### Bonus

- Wrap the enroll + check capacity logic in a transaction so there is no race condition
- Add a `DatabaseInitializer` class that creates all three tables if they do not exist
- Export a course report to a CSV file

---

## Project 3 — Expense Tracker

**Topics:** JDBC, Date and Time API, Streams, Lambdas, Generics, File I/O

### Description

Build a personal finance tracker. Users record income and expense transactions, and the application produces monthly summaries and category breakdowns.

### Domain model

```
Transaction
  - int id
  - TransactionType type        (enum: INCOME, EXPENSE)
  - String category             (e.g. "Food", "Rent", "Salary")
  - double amount
  - String note
  - LocalDate date
```

### Required features

- **Add transaction** — prompt for type, category, amount, note, and date
- **List transactions** — display all transactions; filter by month, type, or category
- **Monthly summary** — given a year and month, print:
  - Total income
  - Total expenses
  - Net balance (income - expenses)
  - Breakdown of expenses by category (sorted by amount descending)
- **All-time stats** — total saved per month (as a `Map<YearMonth, Double>`), highest spending month, highest income month
- **Export** — write the current month's transactions to a CSV file

### Architecture constraint

`TransactionRepository` handles all JDBC calls. `ReportService` accepts a `List<Transaction>` (already fetched from the repo) and computes all statistics using Streams — it must not make any database calls. This separation makes `ReportService` easy to test independently.

### Bonus

- Implement a `Budget` feature: set a monthly spending limit per category; warn the user when they are within 10% of the limit or have exceeded it
- Add a generic `Result<T>` class: `Result.success(T value)` and `Result.failure(String message)`. Use it as the return type of `addTransaction` so callers can check for validation errors without try-catch

---

## Project 4 — Inventory Management System

**Topics:** JDBC, File I/O, Streams, OOP design, Exceptions

### Description

Build a warehouse inventory system for a small business. Products are stored in a database. Stock levels are adjusted through purchase orders (stock coming in) and sales orders (stock going out).

### Schema

```sql
CREATE TABLE products (
    id          INT PRIMARY KEY AUTO_INCREMENT,
    sku         VARCHAR(50) UNIQUE NOT NULL,
    name        VARCHAR(150) NOT NULL,
    category    VARCHAR(100),
    price       DOUBLE NOT NULL,
    stock       INT NOT NULL DEFAULT 0,
    reorder_at  INT NOT NULL DEFAULT 10    -- alert if stock drops to or below this
);

CREATE TABLE stock_movements (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    product_id   INT NOT NULL,
    movement     INT NOT NULL,             -- positive = in, negative = out
    reason       VARCHAR(100),             -- e.g. "purchase", "sale", "adjustment"
    moved_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

### Required features

- **Product CRUD** — add, update price/reorder level, deactivate (soft delete: set stock to 0 and mark inactive)
- **Receive stock** — increase stock for a product by a given quantity; record the movement
- **Sell** — decrease stock; reject if quantity exceeds current stock; record the movement
- **Low-stock alert** — list all products where `stock <= reorder_at`
- **Movement history** — show all movements for a product in reverse chronological order
- **Import products from CSV** — read a CSV file and bulk-insert products, skipping rows with missing or invalid data and logging the errors

### Architecture constraint

Introduce a custom exception hierarchy:

```
InventoryException (checked)
  ├── InsufficientStockException
  └── ProductNotFoundException
```

`ProductService` throws these; the menu layer catches them and prints a user-friendly message, never a stack trace.

### Bonus

- Wrap receive + movement insert in a single transaction
- Generate a low-stock report as a formatted text file (`low-stock-report-YYYY-MM-DD.txt`)
- Add a `StockValuation` report: total value of current inventory per category (`stock * price`)

---

## Project 5 — Multi-Client Chat Server

**Topics:** Sockets, Threads, Concurrency, I/O, Collections

### Description

Build a TCP chat application with a server that handles multiple simultaneous clients and a client that connects to it. Messages sent by one client are broadcast to all connected clients.

This project introduces you to `java.net.Socket`, `java.net.ServerSocket`, and Java's threading model — skills that underpin how frameworks like Spring handle HTTP requests.

### Architecture

```
ChatServer
  - listens on a port (e.g. 9090)
  - for each new connection, spawns a ClientHandler thread
  - maintains a thread-safe list of all active ClientHandlers
  - routes each incoming message to all other handlers (broadcast)

ClientHandler implements Runnable
  - owns one Socket
  - reads lines from the client in a loop
  - writes broadcast messages back to the client

ChatClient
  - connects to the server
  - one thread reads from the server and prints messages
  - the main thread reads from System.in and sends messages
```

### Required features

- Server accepts connections on a configurable port
- Each client provides a username on connect; the server announces joins and departures to all
- `/quit` disconnects the client cleanly
- The server logs connections, disconnections, and errors to the console with timestamps
- If a client disconnects unexpectedly (network error), the server removes them and notifies others

### Concurrency constraint

The list of connected clients must be thread-safe. Use `CopyOnWriteArrayList` or synchronize access manually. Understand why a plain `ArrayList` is not safe here.

### Bonus

- Add `/list` command: the server replies with the list of connected usernames
- Add private messaging: `/msg username hello` sends a message to one user only
- Add server-side message logging: write all messages to a `chat-log-YYYY-MM-DD.txt` file

---

## Project 6 — Job Board CLI (Full Capstone)

**Topics:** JDBC, Collections, Streams, File I/O, OOP design, Exceptions, Enums, Date and Time API

### Description

Build a command-line job board that serves two types of users — **employers** who post job listings and **applicants** who search and apply. This project combines everything from the curriculum into a single, realistic application.

### Schema

```sql
CREATE TABLE employers (
    id      INT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(150) NOT NULL,
    email   VARCHAR(150) UNIQUE NOT NULL,
    industry VARCHAR(100)
);

CREATE TABLE jobs (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    employer_id  INT NOT NULL,
    title        VARCHAR(150) NOT NULL,
    description  TEXT,
    location     VARCHAR(100),
    job_type     VARCHAR(20),    -- FULL_TIME, PART_TIME, CONTRACT, REMOTE
    salary_min   DOUBLE,
    salary_max   DOUBLE,
    posted_at    DATE NOT NULL,
    deadline     DATE,
    is_open      BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (employer_id) REFERENCES employers(id)
);

CREATE TABLE applicants (
    id    INT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    skills TEXT
);

CREATE TABLE applications (
    id           INT PRIMARY KEY AUTO_INCREMENT,
    job_id       INT NOT NULL,
    applicant_id INT NOT NULL,
    applied_at   DATE NOT NULL,
    status       VARCHAR(20) DEFAULT 'PENDING',    -- PENDING, SHORTLISTED, REJECTED
    UNIQUE (job_id, applicant_id),
    FOREIGN KEY (job_id)       REFERENCES jobs(id),
    FOREIGN KEY (applicant_id) REFERENCES applicants(id)
);
```

### Required features

**Employer flow:**
- Register / log in by email (no passwords — just email lookup for simplicity)
- Post a job listing
- View their own listings with application counts
- View applicants for a specific job
- Shortlist or reject an applicant
- Close a job listing (sets `is_open = false`)

**Applicant flow:**
- Register / log in by email
- Search open jobs by keyword (title or description), location, job type, or salary range
- Apply to a job (one application per job per applicant)
- View their application history and current status

**Reporting:**
- Most applied-to jobs (top 5 by application count)
- Jobs with no applicants that are still open
- Export all open jobs to a JSON-like flat text file

### Architecture constraint

Structure the project into clear packages:

```
jobboard/
  model/       Employer, Job, Applicant, Application (POJOs)
  dao/         EmployerDAO, JobDAO, ApplicantDAO, ApplicationDAO
  service/     JobService, ApplicationService
  ui/          EmployerMenu, ApplicantMenu, MainMenu
  exception/   JobBoardException, DuplicateApplicationException
  util/        DatabaseInitializer, CsvExporter
```

The `ui` layer calls `service`. The `service` layer calls `dao`. No layer reaches over its neighbor.

### Bonus

- Add pagination to job search results (show 5 at a time, allow next/previous)
- Add `Comparable` to `Job` (natural order by `posted_at` descending) and `Comparator` chains for sorting by salary or deadline
- Export the employer's applicant list to a CSV file (`applicants-job-{id}-{title}.csv`)

---

## General Guidance

### Before you write a single line of code

1. Read the full spec and draw the class diagram on paper.
2. Identify which classes own which data and which classes coordinate others.
3. Define the database schema (if applicable) and write the `CREATE TABLE` statements first.
4. Write the DAO layer and test each method independently before building the service or UI layers.

### Habits that separate good projects from great ones

- Every method does one thing. If a method has more than one level of abstraction, split it.
- Business rules live in the service layer, not in the menu or the DAO.
- Resources (`Connection`, `ResultSet`, `BufferedReader`) are always closed — use try-with-resources.
- User-facing error messages are friendly. Stack traces are for the developer (log them, do not print them).
- Input validation happens before any database call.

### When something feels too big

Break it into phases. Get one feature working end-to-end (model + DAO + service + menu) before building the next. A working subset is more valuable than a half-built system.
