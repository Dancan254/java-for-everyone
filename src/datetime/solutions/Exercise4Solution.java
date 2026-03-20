package datetime.solutions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Exercise4Solution.java
 *
 * Solutions for Exercise Set 4: DateTimeFormatter.
 * Covers: formatting dates with custom patterns, parsing date strings,
 * and formatting with different locales.
 */
public class Exercise4Solution {

    public static void main(String[] args) {
        exercise4_1();
        exercise4_2();
        exercise4_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 4.1 — Formatting Dates with Custom Patterns
    // -------------------------------------------------------------------------

    static void exercise4_1() {
        System.out.println("=== Exercise 4.1: Formatting LocalDate ===");

        LocalDate date = LocalDate.of(2024, 3, 15);

        DateTimeFormatter isoFormat     = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter euFormat      = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter longFormat    = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        DateTimeFormatter dayMonthYear  = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.ENGLISH);

        System.out.println("ISO:       " + date.format(isoFormat));      // Output: 2024-03-15
        System.out.println("EU:        " + date.format(euFormat));       // Output: 15/03/2024
        System.out.println("Long:      " + date.format(longFormat));     // Output: March 15, 2024
        System.out.println("DayMonth:  " + date.format(dayMonthYear));   // Output: Fri, 15 Mar 2024
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 4.2 — Parsing a Date String
    // -------------------------------------------------------------------------

    static void exercise4_2() {
        System.out.println("=== Exercise 4.2: Parsing Date Strings ===");

        // Parse using a custom formatter that matches the string's layout.
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate parsed = LocalDate.parse("15-03-2024", parser);
        System.out.println("Parsed date: " + parsed); // Output: 2024-03-15

        // ISO_LOCAL_DATE is the default; no formatter needed for "yyyy-MM-dd" strings.
        LocalDate isoDate = LocalDate.parse("2024-06-20");
        System.out.println("ISO parsed:  " + isoDate); // Output: 2024-06-20

        // Parsing a date-time string.
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dt = LocalDateTime.parse("15/03/2024 09:30", dtFormatter);
        System.out.println("Parsed DateTime: " + dt); // Output: 2024-03-15T09:30
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 4.3 — Formatting for Display
    // -------------------------------------------------------------------------

    static void exercise4_3() {
        System.out.println("=== Exercise 4.3: Display Formatting ===");

        LocalDateTime appointment = LocalDateTime.of(2024, 3, 15, 14, 30);

        // A human-readable appointment format.
        DateTimeFormatter display = DateTimeFormatter.ofPattern(
                "EEEE, MMMM d, yyyy 'at' h:mm a", Locale.ENGLISH);
        System.out.println("Appointment: " + appointment.format(display));
        // Output: Friday, March 15, 2024 at 2:30 PM

        // Timestamp for a log entry.
        DateTimeFormatter log = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Log entry:   " + appointment.format(log));
        // Output: 2024-03-15 14:30:00
        System.out.println();
    }
}
