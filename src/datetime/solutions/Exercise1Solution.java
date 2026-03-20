package datetime.solutions;

import java.time.LocalDate;
import java.time.Month;

/**
 * Exercise1Solution.java
 *
 * Solutions for Exercise Set 1: LocalDate Basics.
 * Covers: creating LocalDate instances, accessing fields, date arithmetic,
 * comparisons, isLeapYear(), and lengthOfMonth().
 */
public class Exercise1Solution {

    public static void main(String[] args) {
        exercise1_1();
        exercise1_2();
        exercise1_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.1 — Create and Inspect a LocalDate
    // -------------------------------------------------------------------------

    static void exercise1_1() {
        System.out.println("=== Exercise 1.1: Create and Inspect LocalDate ===");

        LocalDate date = LocalDate.of(2024, Month.MARCH, 15);
        System.out.println("Date:         " + date);                // Output: 2024-03-15
        System.out.println("Year:         " + date.getYear());      // Output: 2024
        System.out.println("Month:        " + date.getMonth());     // Output: MARCH
        System.out.println("MonthValue:   " + date.getMonthValue()); // Output: 3  (1-based)
        System.out.println("DayOfMonth:   " + date.getDayOfMonth()); // Output: 15
        System.out.println("DayOfWeek:    " + date.getDayOfWeek());  // Output: FRIDAY
        System.out.println("DayOfYear:    " + date.getDayOfYear());  // Output: 75
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.2 — Date Arithmetic
    // -------------------------------------------------------------------------

    static void exercise1_2() {
        System.out.println("=== Exercise 1.2: Date Arithmetic ===");

        // LocalDate is immutable — every operation returns a new object.
        LocalDate base = LocalDate.of(2024, 3, 15);

        System.out.println("Base date:    " + base);                     // 2024-03-15
        System.out.println("+ 7 days:     " + base.plusDays(7));         // 2024-03-22
        System.out.println("- 1 month:    " + base.minusMonths(1));      // 2024-02-15
        System.out.println("+ 1 year:     " + base.plusYears(1));        // 2025-03-15
        System.out.println("+ 2 weeks:    " + base.plusWeeks(2));        // 2024-03-29
        System.out.println("- 100 days:   " + base.minusDays(100));      // 2023-12-06
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 1.3 — Comparisons, isLeapYear(), and lengthOfMonth()
    // -------------------------------------------------------------------------

    static void exercise1_3() {
        System.out.println("=== Exercise 1.3: Comparisons and Calendar Queries ===");

        LocalDate a = LocalDate.of(2024, 1, 1);
        LocalDate b = LocalDate.of(2024, 6, 15);

        System.out.println("a.isBefore(b): " + a.isBefore(b)); // Output: true
        System.out.println("a.isAfter(b):  " + a.isAfter(b));  // Output: false
        System.out.println("a.isEqual(b):  " + a.isEqual(b));  // Output: false

        // Leap year check — 2024 is divisible by 4 and not a century exception.
        System.out.println("2024 isLeapYear: " + LocalDate.of(2024, 1, 1).isLeapYear()); // true
        System.out.println("2023 isLeapYear: " + LocalDate.of(2023, 1, 1).isLeapYear()); // false

        // Days in each month
        System.out.println("Days in Feb 2024: " + LocalDate.of(2024, 2, 1).lengthOfMonth()); // 29
        System.out.println("Days in Feb 2023: " + LocalDate.of(2023, 2, 1).lengthOfMonth()); // 28
        System.out.println("Days in Jan 2024: " + LocalDate.of(2024, 1, 1).lengthOfMonth()); // 31
        System.out.println();
    }
}
