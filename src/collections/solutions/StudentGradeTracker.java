package collections.solutions;

import java.util.*;

/**
 * Exercise 3.1 — Student Grade Tracker
 *
 * Demonstrates grouping objects by an enum key using a TreeMap,
 * iterating over grouped data, and computing an average from enum fields.
 */
public class StudentGradeTracker {

    // ---- Enum with GPA value ----
    enum Grade {
        A(4.0),
        B(3.0),
        C(2.0),
        D(1.0),
        F(0.0);

        private final double gpaValue;

        Grade(double gpaValue) {
            this.gpaValue = gpaValue;
        }

        public double getGpaValue() {
            return gpaValue;
        }
    }

    // ---- Simple Student class ----
    static class Student {
        private final String name;
        private final Grade grade;

        Student(String name, Grade grade) {
            this.name = name;
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public Grade getGrade() {
            return grade;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) {

        // 1. Create students
        List<Student> students = Arrays.asList(
                new Student("Alice", Grade.A),
                new Student("Bob", Grade.B),
                new Student("Carol", Grade.C),
                new Student("Dave", Grade.D),
                new Student("Eve", Grade.A),
                new Student("Frank", Grade.B),
                new Student("Grace", Grade.C),
                new Student("Hank", Grade.F));

        // 2. Group by grade using a TreeMap (sorted by ordinal)
        Map<Grade, List<Student>> gradeMap = new TreeMap<>();
        for (Grade g : Grade.values()) {
            gradeMap.put(g, new ArrayList<>());
        }
        for (Student s : students) {
            gradeMap.get(s.getGrade()).add(s);
        }

        // 3. Print each grade group
        System.out.println("--- Grade Report ---");
        for (Map.Entry<Grade, List<Student>> entry : gradeMap.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        // 4. Calculate class GPA average
        double totalGpa = 0;
        for (Student s : students) {
            totalGpa += s.getGrade().getGpaValue();
        }
        double averageGpa = totalGpa / students.size();
        System.out.printf("%nClass GPA: %.2f%n", averageGpa);
    }
}
