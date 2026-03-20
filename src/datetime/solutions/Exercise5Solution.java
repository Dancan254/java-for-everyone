package datetime.solutions;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Exercise5Solution.java
 *
 * Solutions for Exercise Set 5: Real-World Date Scenarios.
 * Covers: calculating days between dates, weekday/weekend detection,
 * and a simple deadline checker.
 */
public class Exercise5Solution {

    public static void main(String[] args) {
        exercise5_1();
        exercise5_2();
        exercise5_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 5.1 — Days Between Two Dates
    // -------------------------------------------------------------------------

    static void exercise5_1() {
        System.out.println("=== Exercise 5.1: Days Between Two Dates ===");

        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end   = LocalDate.of(2024, 12, 31);

        long daysBetween = ChronoUnit.DAYS.between(start, end);
        System.out.println("Days in 2024:   " + daysBetween); // Output: 365

        LocalDate projectStart = LocalDate.of(2024, 3, 1);
        LocalDate projectEnd   = LocalDate.of(2024, 6, 30);
        long projectDays = ChronoUnit.DAYS.between(projectStart, projectEnd);
        System.out.println("Project days:   " + projectDays); // Output: 121
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 5.2 — Weekday vs. Weekend Detection
    // -------------------------------------------------------------------------

    static boolean isWeekday(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }

    static void exercise5_2() {
        System.out.println("=== Exercise 5.2: Weekday Detection ===");

        LocalDate monday  = LocalDate.of(2024, 3, 11); // Monday
        LocalDate friday  = LocalDate.of(2024, 3, 15); // Friday
        LocalDate saturday = LocalDate.of(2024, 3, 16); // Saturday

        System.out.println(monday   + " is weekday: " + isWeekday(monday));   // true
        System.out.println(friday   + " is weekday: " + isWeekday(friday));   // true
        System.out.println(saturday + " is weekday: " + isWeekday(saturday)); // false

        // Count business days in March 2024.
        LocalDate marchStart = LocalDate.of(2024, 3, 1);
        LocalDate marchEnd   = LocalDate.of(2024, 3, 31);
        long businessDays = marchStart.datesUntil(marchEnd.plusDays(1))
                .filter(Exercise5Solution::isWeekday)
                .count();
        System.out.println("Business days in March 2024: " + businessDays); // Output: 21
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 5.3 — Deadline Checker
    // -------------------------------------------------------------------------

    static void exercise5_3() {
        System.out.println("=== Exercise 5.3: Deadline Checker ===");

        LocalDate today    = LocalDate.of(2024, 3, 15);
        LocalDate deadline = LocalDate.of(2024, 4, 1);

        long daysRemaining = ChronoUnit.DAYS.between(today, deadline);

        if (daysRemaining < 0) {
            System.out.println("Deadline has passed by " + Math.abs(daysRemaining) + " day(s).");
        } else if (daysRemaining == 0) {
            System.out.println("The deadline is today!");
        } else if (daysRemaining <= 7) {
            System.out.println("Warning: deadline in " + daysRemaining + " day(s).");
        } else {
            System.out.println("Deadline: " + deadline + " (" + daysRemaining + " days remaining)");
        }
        // Output: Deadline: 2024-04-01 (17 days remaining)

        // Next occurrence of a specific weekday (e.g., next Friday).
        LocalDate nextFriday = today.with(
                java.time.temporal.TemporalAdjusters.next(DayOfWeek.FRIDAY));
        System.out.println("Next Friday: " + nextFriday); // Output: 2024-03-22
        System.out.println();
    }
}
