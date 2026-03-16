import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

/**
 * DateTimeDemo — a self-contained demonstration of the java.time API.
 *
 * Topics covered:
 *   1. LocalDate — creation, field access, arithmetic, comparison
 *   2. LocalTime — creation, constants, arithmetic, comparison
 *   3. LocalDateTime — combining date and time, conversion, arithmetic
 *   4. DateTimeFormatter — predefined formatters, custom patterns, parsing
 *   5. Period — calendar-based amounts (years, months, days)
 *   6. Duration — time-based amounts (hours, minutes, seconds)
 *   7. ZonedDateTime — date and time with a time zone
 *   8. Instant — machine timestamp and elapsed-time measurement
 *   9. Practical patterns — parsing user input, age calculation,
 *      days between dates, next occurrence via TemporalAdjusters
 *  10. Common mistakes — immutability, Locale for formatting, legacy conversion
 *
 * Every section runs as a standalone block; output comments show expected results.
 * Run the class directly: javac DateTimeDemo.java && java DateTimeDemo
 */
public class DateTimeDemo {

    public static void main(String[] args) {
        localDateDemo();
        localTimeDemo();
        localDateTimeDemo();
        dateTimeFormatterDemo();
        periodDemo();
        durationDemo();
        zonedDateTimeDemo();
        instantDemo();
        practicalPatternsDemo();
        commonMistakesDemo();
    }

    // -------------------------------------------------------------------------
    // 1. LocalDate
    // -------------------------------------------------------------------------

    static void localDateDemo() {
        System.out.println("=== LocalDate ===");

        // Create a specific date using year, month (1-based), and day.
        LocalDate date = LocalDate.of(2024, 3, 15);
        System.out.println(date); // Output: 2024-03-15

        // The Month enum avoids any ambiguity about argument order.
        LocalDate sameDate = LocalDate.of(2024, Month.MARCH, 15);
        System.out.println(sameDate); // Output: 2024-03-15

        // LocalDate.now() returns today's date from the system clock.
        LocalDate today = LocalDate.now();
        System.out.println("Today: " + today); // Output: Today: (current date)

        // Field access — month value is 1-based (no off-by-one like Calendar).
        System.out.println(date.getYear());        // Output: 2024
        System.out.println(date.getMonth());       // Output: MARCH
        System.out.println(date.getMonthValue());  // Output: 3
        System.out.println(date.getDayOfMonth());  // Output: 15
        System.out.println(date.getDayOfWeek());   // Output: FRIDAY

        // Arithmetic — every operation returns a NEW LocalDate; the original is unchanged.
        LocalDate nextWeek  = date.plusDays(7);
        LocalDate lastMonth = date.minusMonths(1);
        LocalDate nextYear  = date.plusYears(1);

        System.out.println(nextWeek);  // Output: 2024-03-22
        System.out.println(lastMonth); // Output: 2024-02-15
        System.out.println(nextYear);  // Output: 2025-03-15

        // Comparison methods are more expressive than compareTo alone.
        LocalDate a = LocalDate.of(2024, 1, 1);
        LocalDate b = LocalDate.of(2024, 6, 15);

        System.out.println(a.isBefore(b));  // Output: true
        System.out.println(a.isAfter(b));   // Output: false
        System.out.println(a.isEqual(b));   // Output: false

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 2. LocalTime
    // -------------------------------------------------------------------------

    static void localTimeDemo() {
        System.out.println("=== LocalTime ===");

        // Create a time value. Seconds and nanoseconds default to zero when omitted.
        LocalTime time = LocalTime.of(14, 30, 0); // 14:30:00
        System.out.println(time); // Output: 14:30

        // Nanosecond precision is available when required.
        LocalTime precise = LocalTime.of(9, 15, 30, 500_000_000); // 09:15:30.5
        System.out.println(precise); // Output: 09:15:30.500

        // Built-in constants for the two most common reference points.
        System.out.println(LocalTime.NOON);     // Output: 12:00
        System.out.println(LocalTime.MIDNIGHT); // Output: 00:00

        // Arithmetic — the result wraps around midnight if necessary.
        LocalTime oneHourLater      = time.plusHours(1);
        LocalTime thirtyMinEarlier  = time.minusMinutes(30);

        System.out.println(oneHourLater);     // Output: 15:30
        System.out.println(thirtyMinEarlier); // Output: 14:00

        // Comparison with isBefore/isAfter.
        System.out.println(time.isAfter(LocalTime.NOON));          // Output: true
        System.out.println(time.isBefore(LocalTime.of(18, 0)));    // Output: true

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 3. LocalDateTime
    // -------------------------------------------------------------------------

    static void localDateTimeDemo() {
        System.out.println("=== LocalDateTime ===");

        // Build from separate LocalDate and LocalTime objects.
        LocalDate     date = LocalDate.of(2024, 3, 15);
        LocalTime     time = LocalTime.of(14, 30, 0);
        LocalDateTime dt   = LocalDateTime.of(date, time);
        System.out.println(dt); // Output: 2024-03-15T14:30

        // Build directly with all components.
        LocalDateTime dt2 = LocalDateTime.of(2024, 3, 15, 14, 30, 0);
        System.out.println(dt2); // Output: 2024-03-15T14:30

        // Extract the date part and the time part separately.
        System.out.println(dt.toLocalDate()); // Output: 2024-03-15
        System.out.println(dt.toLocalTime()); // Output: 14:30

        // Arithmetic chains — each call returns a new object.
        LocalDateTime later = dt.plusHours(3).plusDays(1);
        System.out.println(later); // Output: 2024-03-16T17:30

        // Individual field access works the same as LocalDate and LocalTime.
        System.out.println(dt.getYear());   // Output: 2024
        System.out.println(dt.getHour());   // Output: 14
        System.out.println(dt.getMinute()); // Output: 30

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 4. DateTimeFormatter
    // -------------------------------------------------------------------------

    static void dateTimeFormatterDemo() {
        System.out.println("=== DateTimeFormatter ===");

        LocalDate date = LocalDate.of(2024, 3, 15);

        // Predefined ISO formatter — no configuration needed.
        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // Output: 2024-03-15

        // Custom pattern: day/month/year with two-digit fields.
        DateTimeFormatter ddmmyyyy = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println(date.format(ddmmyyyy)); // Output: 15/03/2024

        // MMMM = full month name; locale must be specified for predictable output.
        DateTimeFormatter usFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        System.out.println(date.format(usFormat)); // Output: March 15, 2024

        // Formatting a LocalDateTime with hour and minute.
        LocalDateTime dt   = LocalDateTime.of(2024, 3, 15, 14, 30, 0);
        DateTimeFormatter full = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm", Locale.ENGLISH);
        System.out.println(dt.format(full)); // Output: 15 Mar 2024 14:30

        // Parsing a String back into a LocalDate.
        String input    = "15/03/2024";
        LocalDate parsed = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(parsed);           // Output: 2024-03-15
        System.out.println(parsed.getYear()); // Output: 2024

        // Locale matters: the same pattern produces different text for different locales.
        DateTimeFormatter german = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.GERMAN);
        System.out.println(date.format(usFormat)); // Output: March 15, 2024
        System.out.println(date.format(german));   // Output: März 15, 2024

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 5. Period — calendar-based amounts
    // -------------------------------------------------------------------------

    static void periodDemo() {
        System.out.println("=== Period ===");

        LocalDate start = LocalDate.of(2020, 1, 15);
        LocalDate end   = LocalDate.of(2024, 3, 20);

        // Period.between calculates years, months, and remaining days.
        Period period = Period.between(start, end);
        System.out.println(period);              // Output: P4Y2M5D
        System.out.println(period.getYears());   // Output: 4
        System.out.println(period.getMonths());  // Output: 2
        System.out.println(period.getDays());    // Output: 5

        // Adding a Period to a date.
        LocalDate future = start.plus(Period.ofMonths(6));
        System.out.println(future); // Output: 2020-07-15

        // Factory methods for common amounts.
        Period twoYears   = Period.ofYears(2);
        Period threeMonths = Period.ofMonths(3);
        System.out.println(LocalDate.of(2024, 1, 1).plus(twoYears));    // Output: 2026-01-01
        System.out.println(LocalDate.of(2024, 1, 1).plus(threeMonths)); // Output: 2024-04-01

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 6. Duration — time-based amounts
    // -------------------------------------------------------------------------

    static void durationDemo() {
        System.out.println("=== Duration ===");

        LocalTime start = LocalTime.of(9, 0);
        LocalTime end   = LocalTime.of(17, 30);

        // Duration.between measures the elapsed time between two time points.
        Duration duration = Duration.between(start, end);
        System.out.println(duration.toHours());   // Output: 8
        System.out.println(duration.toMinutes()); // Output: 510
        System.out.println(duration.toSeconds()); // Output: 30600

        // Duration can also be used with LocalDateTime and Instant.
        LocalDateTime dtStart = LocalDateTime.of(2024, 3, 15, 8, 0);
        LocalDateTime dtEnd   = LocalDateTime.of(2024, 3, 15, 20, 45);
        Duration workday = Duration.between(dtStart, dtEnd);
        System.out.println(workday.toHours());   // Output: 12
        System.out.println(workday.toMinutes()); // Output: 765

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 7. ZonedDateTime — date and time with a time zone
    // -------------------------------------------------------------------------

    static void zonedDateTimeDemo() {
        System.out.println("=== ZonedDateTime ===");

        // Create a ZonedDateTime for a fixed point in time in different zones.
        // Using a fixed LocalDateTime so the output is deterministic.
        LocalDateTime base = LocalDateTime.of(2024, 3, 15, 14, 30, 0);

        ZonedDateTime london  = base.atZone(ZoneId.of("Europe/London"));
        ZonedDateTime newYork = base.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime tokyo   = base.atZone(ZoneId.of("Asia/Tokyo"));

        // Each ZonedDateTime carries its offset from UTC.
        System.out.println(london);  // Output: 2024-03-15T14:30+00:00[Europe/London]
        System.out.println(newYork); // Output: 2024-03-15T14:30-04:00[America/New_York]
        System.out.println(tokyo);   // Output: 2024-03-15T14:30+09:00[Asia/Tokyo]

        // Convert between time zones using withZoneSameInstant.
        // This adjusts the clock reading to represent the same instant.
        ZonedDateTime londonNow   = ZonedDateTime.now(ZoneId.of("Europe/London"));
        ZonedDateTime tokyoSame   = londonNow.withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        System.out.println("London: " + londonNow.toLocalTime()); // Output: London: (current London time)
        System.out.println("Tokyo:  " + tokyoSame.toLocalTime()); // Output: Tokyo:  (9 hours ahead)

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 8. Instant — machine timestamp
    // -------------------------------------------------------------------------

    static void instantDemo() {
        System.out.println("=== Instant ===");

        // Instant.now() records the current moment at nanosecond precision.
        Instant now = Instant.now();
        System.out.println(now); // Output: (current UTC timestamp, e.g., 2024-03-15T14:30:00.000000000Z)

        // Convert an Instant to LocalDateTime using a UTC offset.
        LocalDateTime local = LocalDateTime.ofInstant(now, ZoneOffset.UTC);
        System.out.println(local); // Output: (same moment without the Z suffix)

        // Convert LocalDateTime back to an Instant.
        Instant back = local.toInstant(ZoneOffset.UTC);
        System.out.println(back); // Output: (same as 'now', within nanosecond rounding)

        // Measure elapsed time between two instants.
        Instant before = Instant.now();
        long sum = 0;
        for (int i = 0; i < 1_000_000; i++) {
            sum += i; // Perform some work to make elapsed time non-zero.
        }
        Instant after  = Instant.now();
        long millis    = Duration.between(before, after).toMillis();
        System.out.println("Sum:     " + sum);              // Output: Sum:     499999500000
        System.out.println("Elapsed: " + millis + " ms");   // Output: Elapsed: (a few ms)

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 9. Practical patterns
    // -------------------------------------------------------------------------

    static void practicalPatternsDemo() {
        System.out.println("=== Practical Patterns ===");

        // --- Parsing user input with error handling ---
        String userInput = "15/03/2024";
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(userInput, fmt);
            System.out.println("Parsed date: " + date); // Output: Parsed date: 2024-03-15
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + userInput);
        }

        // --- Calculating age ---
        // Period.between gives the full calendar difference; getYears() extracts the years component.
        LocalDate birthday = LocalDate.of(1995, 6, 20);
        LocalDate today    = LocalDate.of(2024, 3, 15); // Fixed date for deterministic output.
        int age = Period.between(birthday, today).getYears();
        System.out.println("Age: " + age); // Output: Age: 28

        // --- Days between two dates using ChronoUnit ---
        // ChronoUnit.DAYS.between returns a total day count, not decomposed years/months/days.
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end   = LocalDate.of(2024, 12, 31);
        long days = ChronoUnit.DAYS.between(start, end);
        System.out.println("Days in 2024 (Jan 1 to Dec 31): " + days); // Output: 365

        // --- Next occurrence of a specific weekday ---
        // TemporalAdjusters.next() finds the next date for the given day of week.
        LocalDate baseDate  = LocalDate.of(2024, 3, 15); // A Friday
        LocalDate nextMonday = baseDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        System.out.println("Base date:    " + baseDate);    // Output: Base date:    2024-03-15
        System.out.println("Next Monday:  " + nextMonday);  // Output: Next Monday:  2024-03-18

        // --- First day of the next month ---
        LocalDate firstOfNextMonth = baseDate.with(TemporalAdjusters.firstDayOfNextMonth());
        System.out.println("First of next month: " + firstOfNextMonth); // Output: First of next month: 2024-04-01

        // --- Last day of the current month ---
        LocalDate lastOfMonth = baseDate.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println("Last of month: " + lastOfMonth); // Output: Last of month: 2024-03-31

        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 10. Common mistakes
    // -------------------------------------------------------------------------

    static void commonMistakesDemo() {
        System.out.println("=== Common Mistakes ===");

        // --- Mistake 1: forgetting that arithmetic returns a NEW object ---
        LocalDate date = LocalDate.of(2024, 1, 1);
        date.plusDays(30); // Returns a new object — this result is DISCARDED.
        System.out.println("After discarded plusDays: " + date); // Output: 2024-01-01 (unchanged!)

        // Correct: always assign the result.
        date = date.plusDays(30);
        System.out.println("After assigned plusDays:  " + date); // Output: 2024-01-31

        // --- Mistake 2: no Locale for month name formatting ---
        DateTimeFormatter noLocale = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        DateTimeFormatter withLocale = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
        LocalDate march15 = LocalDate.of(2024, 3, 15);

        // Without a locale, the output depends on the JVM's default locale.
        // On a German system this would print "März 15, 2024".
        System.out.println("No locale:   " + march15.format(noLocale));    // Output: varies by JVM locale
        System.out.println("With locale: " + march15.format(withLocale));  // Output: March 15, 2024

        // --- Mistake 3: converting to/from legacy java.util.Date ---
        // When a legacy API requires java.util.Date, convert explicitly via Instant.
        LocalDate localDate = LocalDate.of(2024, 3, 15);

        // Convert LocalDate to java.util.Date via Instant (requires a ZoneId).
        java.util.Date legacyDate = java.util.Date.from(
                localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Convert java.util.Date back to LocalDate.
        LocalDate restored = legacyDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        System.out.println("Original:  " + localDate);  // Output: 2024-03-15
        System.out.println("Restored:  " + restored);   // Output: 2024-03-15
        System.out.println("Round-trip success: " + localDate.isEqual(restored)); // Output: true

        System.out.println();
        System.out.println("DateTimeDemo complete.");
    }
}
