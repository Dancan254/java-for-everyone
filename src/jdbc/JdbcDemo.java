package jdbc;

import java.sql.*;

/**
 * Full CRUD demo using JDBC and an H2 in-memory database.
 *
 * Each database operation is isolated in its own method so the
 * flow in main() reads like a plain-English description of what
 * the program does.
 *
 * Dependency: add H2 to your project pom.xml file under dependencies before running.
 *   Maven:
 *     <dependency>
 *       <groupId>com.h2database</groupId>
 *       <artifactId>h2</artifactId>
 *       <version>2.2.224</version>
 *     </dependency>
 */
public class JdbcDemo {

    static final String URL      = "jdbc:h2:mem:studentdb;DB_CLOSE_DELAY=-1";
    static final String USERNAME = "sa";
    static final String PASSWORD = "";

    // ------------------------------------------------------------------ main

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to: " + conn.getMetaData().getDatabaseProductName());

            createTable(conn);
            insertStudents(conn);

            System.out.println("\n--- All students (initial) ---");
            readStudents(conn);

            updateGrade(conn, 2, 97.0);
            System.out.println("\n--- After updating student 2's grade to 97.0 ---");
            readStudents(conn);

            deleteStudent(conn, 3);
            System.out.println("\n--- After deleting student 3 ---");
            readStudents(conn);

            System.out.println("\n--- Students with grade above 85 ---");
            readStudentsAboveGrade(conn, 85.0);

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    // --------------------------------------------------------- DDL: create

    static void createTable(Connection conn) throws SQLException {
        String sql = """
                CREATE TABLE students (
                    id    INT          PRIMARY KEY AUTO_INCREMENT,
                    name  VARCHAR(100) NOT NULL,
                    email VARCHAR(150) UNIQUE,
                    grade DOUBLE
                )
                """;
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
            System.out.println("Table 'students' created.");
        }
    }

    // --------------------------------------------------------- DML: insert

    static void insertStudents(Connection conn) throws SQLException {
        String sql = "INSERT INTO students (name, email, grade) VALUES (?, ?, ?)";

        Object[][] rows = {
            { "Alice",   "alice@example.com",   91.5 },
            { "Bob",     "bob@example.com",      78.0 },
            { "Charlie", "charlie@example.com",  85.5 },
            { "Diana",   "diana@example.com",    95.0 },
            { "Evan",    "evan@example.com",     62.0 },
        };

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Object[] row : rows) {
                ps.setString(1, (String) row[0]);
                ps.setString(2, (String) row[1]);
                ps.setDouble(3, (double)  row[2]);
                ps.addBatch();
            }
            int[] counts = ps.executeBatch();
            System.out.println(counts.length + " students inserted.");
        }
    }

    // --------------------------------------------------------- DML: select

    static void readStudents(Connection conn) throws SQLException {
        String sql = "SELECT id, name, email, grade FROM students ORDER BY grade DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-4s  %-10s  %-25s  %s%n", "ID", "Name", "Email", "Grade");
            System.out.println("-".repeat(55));

            while (rs.next()) {
                System.out.printf("%-4d  %-10s  %-25s  %.1f%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getDouble("grade"));
            }
        }
    }

    static void readStudentsAboveGrade(Connection conn, double threshold) throws SQLException {
        String sql = "SELECT id, name, grade FROM students WHERE grade > ? ORDER BY grade DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, threshold);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("  %s (%.1f)%n",
                            rs.getString("name"),
                            rs.getDouble("grade"));
                }
            }
        }
    }

    // --------------------------------------------------------- DML: update

    static void updateGrade(Connection conn, int studentId, double newGrade) throws SQLException {
        String sql = "UPDATE students SET grade = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, newGrade);
            ps.setInt(2, studentId);
            int affected = ps.executeUpdate();
            System.out.printf("Grade updated — %d row(s) affected.%n", affected);
        }
    }

    // --------------------------------------------------------- DML: delete

    static void deleteStudent(Connection conn, int studentId) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            int affected = ps.executeUpdate();
            System.out.printf("Student deleted — %d row(s) affected.%n", affected);
        }
    }
}
