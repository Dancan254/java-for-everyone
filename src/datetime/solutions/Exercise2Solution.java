package datetime.solutions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Exercise2Solution.java
 *
 * Solutions for Exercise Set 2: LocalTime and LocalDateTime.
 * Covers: creating LocalTime, combining with LocalDate into LocalDateTime,
 * truncation, and temporal comparisons.
 */
public class Exercise2Solution {

    public static void main(String[] args) {
        exercise2_1();
        exercise2_2();
        exercise2_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 2.1 — LocalTime Basics
    // -------------------------------------------------------------------------

    static void exercise2_1() {
        System.out.println("=== Exercise 2.1: LocalTime ===");

        LocalTime time = LocalTime.of(14, 30, 45);
        System.out.println("Time:        " + time);              // Output: 14:30:45
        System.out.println("Hour:        " + time.getHour());    // Output: 14
        System.out.println("Minute:      " + time.getMinute());  // Output: 30
        System.out.println("Second:      " + time.getSecond());  // Output: 45

        System.out.println("+ 90 min:    " + time.plusMinutes(90));  // Output: 16:00:45
        System.out.println("- 2 hours:   " + time.minusHours(2));    // Output: 12:30:45

        System.out.println("isBefore noon: " + time.isBefore(LocalTime.NOON)); // Output: false
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 2.2 — Combining into LocalDateTime
    // -------------------------------------------------------------------------

    static void exercise2_2() {
        System.out.println("=== Exercise 2.2: LocalDateTime ===");

        LocalDate date = LocalDate.of(2024, 3, 15);
        LocalTime time = LocalTime.of(9, 0);

        // atTime() combines a LocalDate with a LocalTime.
        LocalDateTime dt = date.atTime(time);
        System.out.println("LocalDateTime:   " + dt);          // Output: 2024-03-15T09:00

        // Alternatively, use LocalDateTime.of() directly.
        LocalDateTime dt2 = LocalDateTime.of(2024, 3, 15, 14, 30, 0);
        System.out.println("LocalDateTime 2: " + dt2);          // Output: 2024-03-15T14:30

        // Extract the date and time parts back out.
        System.out.println("Date part:       " + dt2.toLocalDate()); // Output: 2024-03-15
        System.out.println("Time part:       " + dt2.toLocalTime()); // Output: 14:30
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 2.3 — Truncation and Arithmetic
    // -------------------------------------------------------------------------

    static void exercise2_3() {
        System.out.println("=== Exercise 2.3: Truncation and Arithmetic ===");

        LocalDateTime dt = LocalDateTime.of(2024, 3, 15, 14, 37, 52);
        System.out.println("Original:             " + dt);
        // Output: 2024-03-15T14:37:52

        // truncatedTo drops all fields smaller than the given unit.
        System.out.println("Truncated to hours:   " + dt.truncatedTo(ChronoUnit.HOURS));
        // Output: 2024-03-15T14:00

        System.out.println("Truncated to minutes: " + dt.truncatedTo(ChronoUnit.MINUTES));
        // Output: 2024-03-15T14:37

        System.out.println("+ 3 hours:            " + dt.plusHours(3));
        // Output: 2024-03-15T17:37:52

        System.out.println("isBefore midnight:    " +
                dt.isBefore(LocalDateTime.of(2024, 3, 15, 23, 59, 59)));
        // Output: true
        System.out.println();
    }
}
