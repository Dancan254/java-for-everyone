package datetime.solutions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

/**
 * Exercise3Solution.java
 *
 * Solutions for Exercise Set 3: Period and Duration.
 * Covers: Period.between() for date-based differences, Duration.between()
 * for time-based differences, and duration arithmetic.
 */
public class Exercise3Solution {

    public static void main(String[] args) {
        exercise3_1();
        exercise3_2();
        exercise3_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 3.1 — Period: Age Calculator
    // -------------------------------------------------------------------------

    static void exercise3_1() {
        System.out.println("=== Exercise 3.1: Period — Age Calculator ===");

        LocalDate birthdate = LocalDate.of(1995, 8, 23);
        LocalDate today     = LocalDate.of(2024, 3, 15); // fixed for reproducible output

        Period age = Period.between(birthdate, today);

        System.out.println("Birthdate:  " + birthdate);
        System.out.println("Today:      " + today);
        System.out.printf("Age:        %d years, %d months, %d days%n",
                age.getYears(), age.getMonths(), age.getDays());
        // Output: 28 years, 6 months, 20 days

        // Period components are not normalized past months — check total days separately.
        long totalDays = birthdate.until(today, java.time.temporal.ChronoUnit.DAYS);
        System.out.println("Total days lived: " + totalDays);
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 3.2 — Duration: Time Difference
    // -------------------------------------------------------------------------

    static void exercise3_2() {
        System.out.println("=== Exercise 3.2: Duration — Time Difference ===");

        LocalTime start = LocalTime.of(9, 15, 0);
        LocalTime end   = LocalTime.of(17, 45, 30);

        Duration workday = Duration.between(start, end);

        System.out.println("Start:         " + start);
        System.out.println("End:           " + end);
        System.out.println("Duration:      " + workday);             // Output: PT8H30M30S
        System.out.println("Hours:         " + workday.toHours());   // Output: 8
        System.out.println("Minutes:       " + workday.toMinutesPart()); // Output: 30
        System.out.println("Seconds:       " + workday.toSecondsPart()); // Output: 30
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 3.3 — Duration Arithmetic
    // -------------------------------------------------------------------------

    static void exercise3_3() {
        System.out.println("=== Exercise 3.3: Duration Arithmetic ===");

        Duration flight = Duration.ofHours(11).plusMinutes(25);
        System.out.println("Flight duration: " + flight); // Output: PT11H25M

        Duration layover = Duration.ofHours(2).plusMinutes(10);
        Duration totalTravel = flight.plus(layover);
        System.out.println("Total travel:    " + totalTravel); // Output: PT13H35M

        // Comparing durations
        System.out.println("Flight longer than layover: " + flight.compareTo(layover) > 0);
        // Output: true

        // A negative duration — Duration.between() can be negative if start > end.
        Duration negative = Duration.between(LocalTime.of(18, 0), LocalTime.of(9, 0));
        System.out.println("Negative duration: " + negative); // Output: PT-9H (or similar)
        System.out.println("Is negative: " + negative.isNegative()); // Output: true
        System.out.println();
    }
}
