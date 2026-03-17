# JDBC — Java Database Connectivity

JDBC is the standard Java API for connecting to relational databases and executing SQL. It ships with every JDK — no additional library is needed beyond a database-specific driver.

---

## How JDBC Works

JDBC sits between your Java code and the database. The flow is always the same:

```
Your Java Code
      |
   JDBC API   (java.sql.*)
      |
 JDBC Driver  (database-specific JAR)
      |
   Database   (MySQL, PostgreSQL, SQLite, H2 …)
```

You write against the JDBC interfaces (`Connection`, `Statement`, `ResultSet`). The driver translates those calls into the wire protocol the database understands.

---

## 1. Setting Up a Driver

JDBC drivers are distributed as JAR files. Add the appropriate one to your project's classpath.

| Database   | Artifact (Maven)                         | Driver Class                          |
|------------|------------------------------------------|---------------------------------------|
| H2 (embedded) | `com.h2database:h2`                  | `org.h2.Driver`                       |
| SQLite     | `org.xerial:sqlite-jdbc`                 | `org.sqlite.JDBC`                     |
| MySQL      | `com.mysql:mysql-connector-j`            | `com.mysql.cj.jdbc.Driver`            |
| PostgreSQL | `org.postgresql:postgresql`              | `org.postgresql.Driver`               |

For learning, **H2** is recommended — it runs entirely in memory, requires no installation, and is reset on every run.

### Maven dependency (H2)

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>2.2.224</version>
</dependency>
```

### Without a build tool

Download the H2 JAR and compile/run with it on the classpath:

```bash
javac -cp h2-2.2.224.jar MyApp.java
java  -cp .:h2-2.2.224.jar MyApp
```

---

## 2. Connecting to a Database

`DriverManager.getConnection(url, user, password)` opens a connection.
The **URL** format is driver-specific:

```
jdbc:<subprotocol>://<host>:<port>/<database>
```

| Database   | Example URL                                             |
|------------|---------------------------------------------------------|
| H2 in-memory | `jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1`              |
| H2 file    | `jdbc:h2:./mydb`                                        |
| SQLite     | `jdbc:sqlite:mydb.db`                                   |
| MySQL      | `jdbc:mysql://localhost:3306/mydb`                      |
| PostgreSQL | `jdbc:postgresql://localhost:5432/mydb`                 |

Always close resources in a `finally` block or use **try-with-resources** (preferred):

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDemo {

    public static void main(String[] args) {
        String url  = "jdbc:h2:mem:demo;DB_CLOSE_DELAY=-1";
        String user = "sa";
        String pass = "";

        // try-with-resources closes the connection automatically.
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            System.out.println("Connected: " + conn.getMetaData().getDatabaseProductName());
            // Output: Connected: H2
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
```

A `Connection` represents one session with the database. It is not thread-safe — each thread should have its own.

---

## 3. Creating Tables — Statement

`Statement` executes SQL that has no parameters. Use it for DDL (CREATE, DROP, ALTER) and one-off queries.

```java
import java.sql.*;

public class CreateTableDemo {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:h2:mem:school;DB_CLOSE_DELAY=-1";

        try (Connection conn = DriverManager.getConnection(url, "sa", "")) {

            // Statement is also AutoCloseable.
            try (Statement stmt = conn.createStatement()) {

                stmt.execute("""
                    CREATE TABLE students (
                        id      INT          PRIMARY KEY AUTO_INCREMENT,
                        name    VARCHAR(100) NOT NULL,
                        email   VARCHAR(150) UNIQUE,
                        grade   DOUBLE
                    )
                """);

                System.out.println("Table created.");
            }
        }
    }
}
```

---

## 4. Inserting Data — PreparedStatement

`PreparedStatement` is parameterised SQL. Always use it for any query that includes user-supplied values.

**Why PreparedStatement over Statement:**
- Prevents SQL injection attacks
- Lets the database cache and reuse the query plan
- Handles type conversion automatically (dates, decimals, etc.)

```java
import java.sql.*;

public class InsertDemo {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:h2:mem:school;DB_CLOSE_DELAY=-1";

        try (Connection conn = DriverManager.getConnection(url, "sa", "")) {

            // Create table first.
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("""
                    CREATE TABLE students (
                        id    INT PRIMARY KEY AUTO_INCREMENT,
                        name  VARCHAR(100),
                        email VARCHAR(150),
                        grade DOUBLE
                    )
                """);
            }

            // The ? placeholders are filled in by the set* methods.
            String sql = "INSERT INTO students (name, email, grade) VALUES (?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                // Insert first student.
                ps.setString(1, "Alice");
                ps.setString(2, "alice@example.com");
                ps.setDouble(3, 91.5);
                int rows = ps.executeUpdate();     // returns number of rows affected
                System.out.println("Rows inserted: " + rows); // Output: Rows inserted: 1

                // Reuse the same PreparedStatement for a second row.
                ps.setString(1, "Bob");
                ps.setString(2, "bob@example.com");
                ps.setDouble(3, 78.0);
                ps.executeUpdate();

                ps.setString(1, "Carol");
                ps.setString(2, "carol@example.com");
                ps.setDouble(3, 85.5);
                ps.executeUpdate();

                System.out.println("All students inserted.");
            }
        }
    }
}
```

### Batch Inserts

For inserting many rows at once, use `addBatch()` / `executeBatch()`:

```java
String sql = "INSERT INTO students (name, email, grade) VALUES (?, ?, ?)";

try (PreparedStatement ps = conn.prepareStatement(sql)) {
    String[][] data = {
        {"Dave",  "dave@example.com",  "88.0"},
        {"Eve",   "eve@example.com",   "95.0"},
        {"Frank", "frank@example.com", "72.5"}
    };

    for (String[] row : data) {
        ps.setString(1, row[0]);
        ps.setString(2, row[1]);
        ps.setDouble(3, Double.parseDouble(row[2]));
        ps.addBatch();               // queue the row
    }

    int[] results = ps.executeBatch();   // send all rows in one round-trip
    System.out.println("Batch inserted: " + results.length + " rows");
}
```

---

## 5. Reading Data — ResultSet

`executeQuery()` returns a `ResultSet` — a cursor that moves row by row through the results.

```java
import java.sql.*;

public class QueryDemo {

    public static void main(String[] args) throws SQLException {
        // (assume table already exists and is populated)
        String url = "jdbc:h2:mem:school;DB_CLOSE_DELAY=-1";

        try (Connection conn = DriverManager.getConnection(url, "sa", "")) {

            String sql = "SELECT id, name, email, grade FROM students ORDER BY grade DESC";

            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                System.out.printf("%-5s %-10s %-25s %-6s%n", "ID", "Name", "Email", "Grade");
                System.out.println("-".repeat(50));

                // rs.next() advances the cursor; returns false when no more rows.
                while (rs.next()) {
                    int    id    = rs.getInt("id");
                    String name  = rs.getString("name");
                    String email = rs.getString("email");
                    double grade = rs.getDouble("grade");

                    System.out.printf("%-5d %-10s %-25s %.1f%n", id, name, email, grade);
                }
            }
        }
    }
}
```

**Output:**
```
ID    Name       Email                     Grade
--------------------------------------------------
1     Alice      alice@example.com         91.5
3     Carol      carol@example.com         85.5
2     Bob        bob@example.com           78.0
```

### Handling NULL values

If a column can be `NULL`, check with `rs.wasNull()` after reading:

```java
double grade = rs.getDouble("grade");
if (rs.wasNull()) {
    System.out.println("Grade not recorded");
} else {
    System.out.println("Grade: " + grade);
}
```

---

## 6. Updating and Deleting Data

```java
// UPDATE
String updateSql = "UPDATE students SET grade = ? WHERE name = ?";
try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
    ps.setDouble(1, 94.0);
    ps.setString(2, "Bob");
    int affected = ps.executeUpdate();
    System.out.println("Rows updated: " + affected); // Output: Rows updated: 1
}

// DELETE
String deleteSql = "DELETE FROM students WHERE id = ?";
try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
    ps.setInt(1, 2);
    int affected = ps.executeUpdate();
    System.out.println("Rows deleted: " + affected); // Output: Rows deleted: 1
}
```

---

## 7. Transactions

By default, JDBC runs in **auto-commit** mode — every statement is committed immediately. For multi-step operations that must succeed or fail together, disable auto-commit and manage the transaction manually.

```java
Connection conn = DriverManager.getConnection(url, user, pass);

try {
    conn.setAutoCommit(false);   // start transaction

    String debit  = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
    String credit = "UPDATE accounts SET balance = balance + ? WHERE id = ?";

    try (PreparedStatement debitPs  = conn.prepareStatement(debit);
         PreparedStatement creditPs = conn.prepareStatement(credit)) {

        debitPs.setDouble(1, 500.0);
        debitPs.setInt(2, 1);          // deduct from account 1
        debitPs.executeUpdate();

        creditPs.setDouble(1, 500.0);
        creditPs.setInt(2, 2);         // credit to account 2
        creditPs.executeUpdate();
    }

    conn.commit();                     // both updates succeed: persist
    System.out.println("Transfer complete.");

} catch (SQLException e) {
    conn.rollback();                   // any failure: undo everything
    System.err.println("Transfer failed, rolled back: " + e.getMessage());
} finally {
    conn.setAutoCommit(true);          // restore default
    conn.close();
}
```

**Rule:** if any step in the transaction throws, call `rollback()` so the database is left in a consistent state.

---

## 8. Retrieving Generated Keys

When a table uses an auto-increment primary key, retrieve the generated ID after an insert:

```java
String sql = "INSERT INTO students (name, email, grade) VALUES (?, ?, ?)";

try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
    ps.setString(1, "Grace");
    ps.setString(2, "grace@example.com");
    ps.setDouble(3, 88.0);
    ps.executeUpdate();

    try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) {
            int newId = keys.getInt(1);
            System.out.println("New student ID: " + newId);
        }
    }
}
```

---

## 9. The DAO Pattern

For anything beyond a quick demo, organise database code using the **Data Access Object (DAO)** pattern. A DAO class encapsulates all SQL for one table, keeping it out of business logic.

```java
// Student.java — plain Java object (POJO)
public class Student {
    private int    id;
    private String name;
    private String email;
    private double grade;

    public Student(int id, String name, String email, double grade) {
        this.id    = id;
        this.name  = name;
        this.email = email;
        this.grade = grade;
    }

    // Getters …
    public int    getId()    { return id; }
    public String getName()  { return name; }
    public String getEmail() { return email; }
    public double getGrade() { return grade; }

    @Override
    public String toString() {
        return String.format("Student{id=%d, name='%s', grade=%.1f}", id, name, grade);
    }
}
```

```java
// StudentDAO.java — all SQL lives here
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAO {

    private final Connection conn;

    public StudentDAO(Connection conn) {
        this.conn = conn;
    }

    public void createTable() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS students (
                    id    INT PRIMARY KEY AUTO_INCREMENT,
                    name  VARCHAR(100),
                    email VARCHAR(150) UNIQUE,
                    grade DOUBLE
                )
            """);
        }
    }

    public int save(Student s) throws SQLException {
        String sql = "INSERT INTO students (name, email, grade) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setDouble(3, s.getGrade());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        }
    }

    public Optional<Student> findById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }
        }
        return Optional.empty();
    }

    public List<Student> findAll() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY name";
        try (Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    public int update(Student s) throws SQLException {
        String sql = "UPDATE students SET name = ?, email = ?, grade = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setDouble(3, s.getGrade());
            ps.setInt(4, s.getId());
            return ps.executeUpdate();
        }
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getDouble("grade")
        );
    }
}
```

```java
// Main.java — clean business logic, no SQL
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:h2:mem:school;DB_CLOSE_DELAY=-1";

        try (Connection conn = DriverManager.getConnection(url, "sa", "")) {
            StudentDAO dao = new StudentDAO(conn);
            dao.createTable();

            int id1 = dao.save(new Student(0, "Alice", "alice@example.com", 91.5));
            int id2 = dao.save(new Student(0, "Bob",   "bob@example.com",   78.0));

            System.out.println("Saved with IDs: " + id1 + ", " + id2);

            dao.findAll().forEach(System.out::println);

            dao.findById(id1).ifPresent(s -> System.out.println("Found: " + s));

            dao.delete(id2);
            System.out.println("After delete:");
            dao.findAll().forEach(System.out::println);
        }
    }
}
```

---

## 10. Connection Pooling

Opening a `Connection` is expensive — it involves a network round-trip and authentication. In any application that handles multiple requests, **connection pooling** reuses connections rather than creating new ones each time.

The most commonly used pool in the Java ecosystem is **HikariCP**:

```xml
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>5.1.0</version>
</dependency>
```

```java
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.*;

public class PoolDemo {

    public static void main(String[] args) throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/mydb");
        config.setUsername("root");
        config.setPassword("secret");
        config.setMaximumPoolSize(10);           // max 10 simultaneous connections

        try (HikariDataSource ds = new HikariDataSource(config)) {
            // Borrow a connection from the pool.
            try (Connection conn = ds.getConnection();
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery("SELECT COUNT(*) FROM students")) {
                if (rs.next()) System.out.println("Total students: " + rs.getInt(1));
            }
            // Connection is returned to the pool, not closed.
        }
    }
}
```

For learning with a single-threaded console app, `DriverManager` is sufficient. Switch to a pool when you build multi-user or web-facing applications.

---

## 11. SQL Injection — What It Is and How to Prevent It

SQL injection is the most common database vulnerability. It occurs when user input is concatenated directly into a SQL string.

**Vulnerable code (never do this):**

```java
// If the user types: ' OR '1'='1
// The query becomes: SELECT * FROM users WHERE name = '' OR '1'='1'
// This returns ALL rows in the table.
String name = userInput;
String sql  = "SELECT * FROM users WHERE name = '" + name + "'";
Statement stmt = conn.createStatement();
ResultSet rs   = stmt.executeQuery(sql);
```

**Safe code:**

```java
String sql = "SELECT * FROM users WHERE name = ?";
try (PreparedStatement ps = conn.prepareStatement(sql)) {
    ps.setString(1, userInput);   // the driver escapes the value safely
    try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) { /* process */ }
    }
}
```

The rule is simple: **always use `PreparedStatement` for any query that includes external input.** Never build SQL strings by concatenation.

---

## 12. Best Practices

| Practice | Why |
|----------|-----|
| Always use try-with-resources for `Connection`, `Statement`, and `ResultSet` | Guarantees resources are released even when exceptions occur |
| Use `PreparedStatement` for all parameterised queries | Prevents SQL injection; improves performance through query plan caching |
| Keep SQL in a DAO, not in business logic | Makes the code testable, readable, and easier to swap databases |
| Disable auto-commit only when you need a multi-step transaction | Auto-commit is simpler and sufficient for single-statement operations |
| Fetch only the columns you need | `SELECT *` is convenient but wasteful; name columns explicitly |
| Use a connection pool in real applications | `DriverManager` opens a new connection every call, which is slow under load |
| Log and translate `SQLException` at the DAO boundary | Let callers work with domain exceptions, not JDBC internals |

---

## What Comes Next

JDBC is the foundation. When you move to Spring Boot, you will use:
- **Spring JDBC / JdbcTemplate** — eliminates repetitive boilerplate while staying close to SQL
- **Spring Data JPA / Hibernate** — maps Java objects to tables so you rarely write SQL by hand

Understanding raw JDBC first makes those higher-level abstractions far easier to debug and reason about.
