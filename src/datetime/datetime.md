# Date and Time API

Handling dates and times is one of those problems that looks simple and turns out to be full of edge cases: leap years, daylight saving time, time zones, calendar differences, and locale-specific formatting all conspire to make naive implementations fail. Java 8 introduced the `java.time` package — a complete, well-designed API that handles all of this correctly.

---

## 1. The Old API and Its Problems

Before Java 8, dates were handled by `java.util.Date` and `java.util.Calendar`. Both classes have serious design problems:

- `Date` is **mutable** — any code holding a reference can change the date.
- Month numbering in `Calendar` starts at 0 (January = 0, December = 11) — a constant source of off-by-one bugs.
- Both classes are not thread-safe — sharing them between threads requires external synchronisation.
- `Date` represents a point in time but was also used for date-only concepts, conflating two different ideas.
- `SimpleDateFormat` (the formatter) is not thread-safe and is verbose to use.

The `java.time` package replaced all of this. Unless you are working with legacy APIs that require `java.util.Date`, use `java.time` exclusively.

---

## 2. The java.time Package Overview

The three core classes cover the most common needs:

| Class | Represents | Example |
|---|---|---|
| `LocalDate` | A calendar date (year, month, day) with no time or time zone | `2024-03-15` |
| `LocalTime` | A time of day with no date or time zone | `14:30:00` |
| `LocalDateTime` | A date and time combined, with no time zone | `2024-03-15T14:30:00` |

The "Local" prefix means no time zone information is stored. For most business logic that operates in a single known time zone, this is exactly what you need.

Additional classes for specific needs:
- `ZonedDateTime` — date and time with a time zone.
- `Instant` — a point in time on the machine clock (nanosecond precision since epoch).
- `Period` — a date-based amount (e.g., "3 months and 5 days").
- `Duration` — a time-based amount (e.g., "2 hours and 30 minutes").
- `DateTimeFormatter` — for parsing and formatting.

---

## 3. LocalDate

`LocalDate` represents a date without any time component. It is immutable — all operations return new objects.

```java
import java.time.LocalDate;
import java.time.Month;

// Create a specific date.
LocalDate date = LocalDate.of(2024, 3, 15);
System.out.println(date); // Output: 2024-03-15

// Create using the Month enum (avoids the "is March 3 or 3 March?" ambiguity).
LocalDate same = LocalDate.of(2024, Month.MARCH, 15);
System.out.println(same); // Output: 2024-03-15

// Today's date.
LocalDate today = LocalDate.now();
System.out.println(today); // Output: (today's date, e.g., 2024-03-15)

// Access individual fields.
System.out.println(date.getYear());        // Output: 2024
System.out.println(date.getMonth());       // Output: MARCH
System.out.println(date.getMonthValue());  // Output: 3  (1-based — no off-by-one)
System.out.println(date.getDayOfMonth());  // Output: 15
System.out.println(date.getDayOfWeek());   // Output: FRIDAY

// Arithmetic — all operations return a NEW LocalDate.
LocalDate nextWeek   = date.plusDays(7);
LocalDate lastMonth  = date.minusMonths(1);
LocalDate nextYear   = date.plusYears(1);

System.out.println(nextWeek);   // Output: 2024-03-22
System.out.println(lastMonth);  // Output: 2024-02-15
System.out.println(nextYear);   // Output: 2025-03-15

// Comparison.
LocalDate a = LocalDate.of(2024, 1, 1);
LocalDate b = LocalDate.of(2024, 6, 15);

System.out.println(a.isBefore(b));  // Output: true
System.out.println(a.isAfter(b));   // Output: false
System.out.println(a.isEqual(b));   // Output: false
System.out.println(a.compareTo(b)); // Output: negative (a is before b)
```

---

## 4. LocalTime

`LocalTime` represents a time of day without any date or time zone. All operations return new objects (immutable).

```java
import java.time.LocalTime;

// Create a specific time.
LocalTime time = LocalTime.of(14, 30, 0); // 14:30:00
System.out.println(time); // Output: 14:30

// With seconds and nanoseconds.
LocalTime precise = LocalTime.of(9, 15, 30, 500_000_000); // 09:15:30.5
System.out.println(precise); // Output: 09:15:30.500

// Useful constants.
System.out.println(LocalTime.NOON);     // Output: 12:00
System.out.println(LocalTime.MIDNIGHT); // Output: 00:00

// Now.
LocalTime now = LocalTime.now();

// Arithmetic.
LocalTime oneHourLater = time.plusHours(1);
LocalTime thirtyMinEarlier = time.minusMinutes(30);

System.out.println(oneHourLater);      // Output: 15:30
System.out.println(thirtyMinEarlier); // Output: 14:00

// Comparison.
System.out.println(time.isAfter(LocalTime.NOON));  // Output: true
System.out.println(time.isBefore(LocalTime.of(18, 0))); // Output: true
```

---

## 5. LocalDateTime

`LocalDateTime` combines a date and a time in a single object. Use it when you need both, but do not need time zone awareness.

```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// Create from separate date and time.
LocalDate    date = LocalDate.of(2024, 3, 15);
LocalTime    time = LocalTime.of(14, 30, 0);
LocalDateTime dt  = LocalDateTime.of(date, time);
System.out.println(dt); // Output: 2024-03-15T14:30

// Create directly.
LocalDateTime dt2 = LocalDateTime.of(2024, 3, 15, 14, 30, 0);
System.out.println(dt2); // Output: 2024-03-15T14:30

// Now.
LocalDateTime now = LocalDateTime.now();

// Convert to components.
System.out.println(dt.toLocalDate()); // Output: 2024-03-15
System.out.println(dt.toLocalTime()); // Output: 14:30

// Arithmetic.
LocalDateTime later    = dt.plusHours(3).plusDays(1);
System.out.println(later); // Output: 2024-03-16T17:30

// Access fields.
System.out.println(dt.getYear());        // Output: 2024
System.out.println(dt.getHour());        // Output: 14
System.out.println(dt.getMinute());      // Output: 30
```

---

## 6. DateTimeFormatter

`DateTimeFormatter` converts between date/time objects and `String`. It is thread-safe (unlike the old `SimpleDateFormat`).

```java
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Predefined ISO formatters — no configuration needed.
LocalDate date = LocalDate.of(2024, 3, 15);
System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE)); // Output: 2024-03-15

// Custom patterns.
DateTimeFormatter ddmmyyyy = DateTimeFormatter.ofPattern("dd/MM/yyyy");
System.out.println(date.format(ddmmyyyy)); // Output: 15/03/2024

DateTimeFormatter usFormat = DateTimeFormatter.ofPattern("MMMM d, yyyy");
System.out.println(date.format(usFormat)); // Output: March 15, 2024

// Formatting LocalDateTime.
LocalDateTime dt = LocalDateTime.of(2024, 3, 15, 14, 30, 0);
DateTimeFormatter full = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
System.out.println(dt.format(full)); // Output: 15 Mar 2024 14:30

// Parsing a String into a date object.
String input = "15/03/2024";
LocalDate parsed = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
System.out.println(parsed);          // Output: 2024-03-15
System.out.println(parsed.getYear()); // Output: 2024
```

**Common pattern letters:**

| Letter | Meaning | Example |
|---|---|---|
| `yyyy` | 4-digit year | 2024 |
| `MM` | 2-digit month | 03 |
| `MMMM` | Full month name | March |
| `dd` | 2-digit day | 15 |
| `HH` | Hour (24h) | 14 |
| `hh` | Hour (12h) | 02 |
| `mm` | Minutes | 30 |
| `ss` | Seconds | 00 |

---

## 7. Period vs Duration

`Period` and `Duration` both represent amounts of time, but in different units.

**`Period`** measures calendar amounts: years, months, and days. Use it for date differences.

```java
import java.time.LocalDate;
import java.time.Period;

LocalDate start = LocalDate.of(2020, 1, 15);
LocalDate end   = LocalDate.of(2024, 3, 20);

Period period = Period.between(start, end);
System.out.println(period);                // Output: P4Y2M5D
System.out.println(period.getYears());    // Output: 4
System.out.println(period.getMonths());   // Output: 2
System.out.println(period.getDays());     // Output: 5

// Adding a Period to a date.
LocalDate future = start.plus(Period.ofMonths(6));
System.out.println(future); // Output: 2020-07-15
```

**`Duration`** measures time-based amounts: hours, minutes, seconds, nanoseconds. Use it for time differences.

```java
import java.time.LocalTime;
import java.time.Duration;

LocalTime start = LocalTime.of(9, 0);
LocalTime end   = LocalTime.of(17, 30);

Duration duration = Duration.between(start, end);
System.out.println(duration.toHours());   // Output: 8
System.out.println(duration.toMinutes()); // Output: 510
System.out.println(duration.toSeconds()); // Output: 30600
```

---

## 8. ZonedDateTime and ZoneId

When you need to work across time zones — for example, a meeting scheduled in UTC that users in different cities see in their local time — use `ZonedDateTime`.

```java
import java.time.ZonedDateTime;
import java.time.ZoneId;

// Current time in a specific time zone.
ZonedDateTime londonTime = ZonedDateTime.now(ZoneId.of("Europe/London"));
ZonedDateTime tokyoTime  = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
ZonedDateTime nyTime     = ZonedDateTime.now(ZoneId.of("America/New_York"));

System.out.println(londonTime); // Output: 2024-03-15T14:30:00+00:00[Europe/London]
System.out.println(tokyoTime);  // Output: 2024-03-15T23:30:00+09:00[Asia/Tokyo]

// List all available zone IDs.
// ZoneId.getAvailableZoneIds().stream().sorted().forEach(System.out::println);
```

---

## 9. Instant

`Instant` represents a point in time on the machine clock — a count of seconds (and nanoseconds) since the Unix epoch (1970-01-01T00:00:00Z). Use `Instant` for machine timestamps, measuring elapsed time, and interoperating with databases or APIs that work in UTC epoch time.

```java
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

// Current machine time.
Instant now = Instant.now();
System.out.println(now); // Output: 2024-03-15T14:30:00.000000000Z

// Convert Instant to LocalDateTime (requires a ZoneOffset).
LocalDateTime local = LocalDateTime.ofInstant(now, ZoneOffset.UTC);
System.out.println(local); // Output: 2024-03-15T14:30:00

// Convert LocalDateTime to Instant.
Instant back = local.toInstant(ZoneOffset.UTC);
System.out.println(back); // Output: 2024-03-15T14:30:00Z

// Measuring elapsed time.
Instant before = Instant.now();
// ... some operation ...
Instant after  = Instant.now();
long millis    = java.time.Duration.between(before, after).toMillis();
System.out.println("Elapsed: " + millis + " ms");
```

---

## 10. Practical Patterns

### Parsing user input dates

```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

String userInput = "15/03/2024";
DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

try {
    LocalDate date = LocalDate.parse(userInput, fmt);
    System.out.println("Parsed: " + date); // Output: Parsed: 2024-03-15
} catch (DateTimeParseException e) {
    System.err.println("Invalid date format: " + userInput);
}
```

### Calculating age

```java
import java.time.LocalDate;
import java.time.Period;

LocalDate birthday = LocalDate.of(1995, 6, 20);
LocalDate today    = LocalDate.now();

int age = Period.between(birthday, today).getYears();
System.out.println("Age: " + age); // Output: Age: 28 (varies with today's date)
```

### Days between two dates

```java
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

LocalDate start = LocalDate.of(2024, 1, 1);
LocalDate end   = LocalDate.of(2024, 12, 31);

long days = ChronoUnit.DAYS.between(start, end);
System.out.println("Days in 2024 between Jan 1 and Dec 31: " + days); // Output: 365
```

### Next occurrence (e.g., next Monday)

```java
import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;

LocalDate today      = LocalDate.now();
LocalDate nextMonday = today.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
System.out.println("Next Monday: " + nextMonday);
```

---

## 11. Common Mistakes

### Mutability confusion — operations return new objects

The most common mistake: treating `java.time` objects as mutable.

```java
LocalDate date = LocalDate.of(2024, 1, 1);
date.plusDays(30); // Returns a new object — the original is UNCHANGED.

// Correct: assign the result.
date = date.plusDays(30);
System.out.println(date); // Output: 2024-01-31
```

### Not specifying a Locale for month name formatting

`DateTimeFormatter.ofPattern("MMMM")` uses the JVM's default locale. On servers with a non-English locale, "March" becomes "März" or "三月". Always specify a locale when the output will be displayed to users.

```java
import java.util.Locale;
import java.time.format.DateTimeFormatter;

DateTimeFormatter english = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
DateTimeFormatter german  = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.GERMAN);

LocalDate date = LocalDate.of(2024, 3, 15);
System.out.println(date.format(english)); // Output: March 15, 2024
System.out.println(date.format(german));  // Output: März 15, 2024
```

### Mixing java.util.Date with java.time

Legacy APIs (JDBC, old libraries) may require `java.util.Date`. Convert explicitly:

```java
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

LocalDate localDate = LocalDate.of(2024, 3, 15);

// Convert LocalDate to java.util.Date.
Date legacyDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

// Convert java.util.Date back to LocalDate.
LocalDate back = legacyDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
```

---

## 12. Key Takeaways

- Use `java.time` (Java 8+) — not `java.util.Date` or `java.util.Calendar` — for all date/time work
- `LocalDate` is a date with no time; `LocalTime` is a time with no date; `LocalDateTime` is both, with no time zone
- All `java.time` objects are **immutable** — arithmetic methods return new objects; the original is never changed
- `DateTimeFormatter` is thread-safe; use it with `format()` and `parse()` for string conversion
- `Period` measures calendar-based amounts (years/months/days); `Duration` measures time-based amounts (hours/minutes/seconds)
- `ZonedDateTime` is for cross-timezone work; `Instant` is for machine timestamps and epoch time
- Always specify a `Locale` when formatting month or day names for user display

---

The `java.time` API appears throughout Spring Boot: `@Column` mappings for date fields, request body deserialization with Jackson, and response formatting all use `LocalDate`, `LocalDateTime`, or `Instant`. Fluency with this API is a prerequisite for production-quality backend work.
