# Date and Time API — Exercises

These exercises reinforce the core concepts from the Date and Time API guide. Work through each set in order. All classes should use `package datetime.solutions;` and import from `java.time.*`.

---

## Exercise Set 1: LocalDate Basics

**1.1** Create a `LocalDate` for March 15, 2024. Print its year, month (as a string), month value (as an integer), day of the month, and day of the week.

Expected output:
```
2024
MARCH
3
15
FRIDAY
```

**1.2** Using the date from 1.1, compute and print:
- 10 days later
- 3 months earlier
- 2 years later

Expected output:
```
2024-03-25
2023-12-15
2026-03-15
```

**1.3** Declare two dates: January 1, 2024, and June 15, 2024. Use `isBefore`, `isAfter`, and `isEqual` to compare them and print the results.

Expected output:
```
true
false
false
```

**1.4** Check whether 2024 is a leap year and whether 2023 is a leap year. Print the results. Then print the number of days in February 2024 and February 2023 using `lengthOfMonth()`.

Expected output:
```
2024 is a leap year: true
2023 is a leap year: false
February 2024 has 29 days
February 2023 has 28 days
```

---

## Exercise Set 2: LocalTime and LocalDateTime

**2.1** Create a `LocalTime` of 14:30:00. Print it, then print whether it is after noon (`LocalTime.NOON`) and whether it is before 18:00.

Expected output:
```
14:30
true
true
```

**2.2** Using the time from 2.1, compute:
- 90 minutes later
- 45 minutes earlier

Expected output:
```
16:00
13:45
```

**2.3** Combine `LocalDate.of(2024, 3, 15)` and `LocalTime.of(14, 30, 0)` into a `LocalDateTime`. Print the combined object, then extract and print the date part and the time part separately.

Expected output:
```
2024-03-15T14:30
2024-03-15
14:30
```

**2.4** Using `truncatedTo(ChronoUnit.HOURS)`, truncate the `LocalDateTime` from 2.3 to the nearest hour and print the result. Then check whether the original is after the truncated version.

Expected output:
```
2024-03-15T14:00
false
```

---

## Exercise Set 3: Period and Duration

**3.1** Calculate the age (in complete years) of someone born on June 20, 1995. Use a fixed "today" of March 15, 2024 so the output is deterministic. Print the years, months, and remaining days of the `Period`, then print the age in years.

Expected output:
```
Years: 28
Months: 8
Days: 23
Age: 28 years
```

**3.2** A workday starts at 09:00 and ends at 17:30. Calculate the `Duration` between these two times and print the total hours, total minutes, and total seconds.

Expected output:
```
Hours: 8
Minutes: 510
Seconds: 30600
```

**3.3** A flight departs at 08:15 and arrives at 19:45. Compute the travel time using `Duration.between`. Then add a 90-minute layover using `Duration.ofMinutes(90)` and print the total travel time in hours and minutes.

Expected output:
```
Flight duration: 11 hours 30 minutes
Total with layover: 13 hours 0 minutes
```

---

## Exercise Set 4: DateTimeFormatter

**4.1** Format `LocalDate.of(2024, 3, 15)` using three different patterns and print each result:
- `"yyyy-MM-dd"` (ISO-style)
- `"dd/MM/yyyy"` (European)
- `"MMMM d, yyyy"` (US long form, English locale)

Expected output:
```
2024-03-15
15/03/2024
March 15, 2024
```

**4.2** Parse the string `"15-03-2024"` into a `LocalDate` using a custom `DateTimeFormatter` with pattern `"dd-MM-yyyy"`. Print the parsed date, its year, and its month value.

Expected output:
```
2024-03-15
2024
3
```

**4.3** Format `LocalDate.of(2024, 3, 15)` using the pattern `"MMMM d, yyyy"` twice: once with `Locale.ENGLISH` and once with `Locale.GERMAN`. Print both results to illustrate how locale affects month name output.

Expected output:
```
March 15, 2024
März 15, 2024
```

**4.4** Format `LocalDateTime.of(2024, 3, 15, 14, 30, 0)` using the pattern `"dd MMM yyyy HH:mm"` with `Locale.ENGLISH`.

Expected output:
```
15 Mar 2024 14:30
```

---

## Exercise Set 5: Real-World Scenarios

**5.1 — Appointment conflict checker**

Given a list of appointments (represented as `LocalDateTime` objects), check whether a proposed new appointment conflicts with any existing one. Two appointments conflict if they fall on the same date and within 30 minutes of each other.

Use the following fixed data:
- Existing appointments: `2024-03-15T09:00`, `2024-03-15T11:00`, `2024-03-15T14:00`
- Proposed appointment: `2024-03-15T10:45`

Print whether a conflict exists and which existing appointment caused it.

Expected output:
```
Conflict detected with appointment at: 2024-03-15T11:00
```

**5.2 — Countdown to a deadline**

Calculate and print the number of days, hours remaining in the day, and total hours between a fixed "now" and a deadline.

Use:
- Now: `2024-03-15T09:00`
- Deadline: `2024-04-01T17:00`

Print the `Period` between the dates and the total hours via `ChronoUnit.HOURS.between`.

Expected output:
```
Days remaining: 17
Total hours until deadline: 416
```

**5.3 — Log timestamp formatting**

Write a method that accepts a `LocalDateTime` and returns a formatted log entry string in the pattern `"[yyyy-MM-dd HH:mm:ss]"`. Call it with `2024-03-15T14:30:45` and with `2024-03-15T09:05:07`, then print each result.

Expected output:
```
[2024-03-15 14:30:45]
[2024-03-15 09:05:07]
```

**5.4 — Weekday checker**

Given a list of dates, print each date followed by whether it is a weekday or a weekend day.

Use: `2024-03-15`, `2024-03-16`, `2024-03-17`.

Expected output:
```
2024-03-15 (FRIDAY): Weekday
2024-03-16 (SATURDAY): Weekend
2024-03-17 (SUNDAY): Weekend
```

---

## Common Mistakes

**Mutability misconception**

All `java.time` objects are immutable. Arithmetic methods return a new object — the original is never modified. The following code is a silent bug:

```java
LocalDate date = LocalDate.of(2024, 1, 1);
date.plusDays(30); // Result is discarded — date is still 2024-01-01.
```

Always assign the return value:

```java
date = date.plusDays(30); // Correct.
```

**Using `java.util.Date` or `java.util.Calendar`**

These legacy classes are mutable, not thread-safe, and error-prone. Use `java.time` exclusively for new code. If a legacy API requires `java.util.Date`, convert at the boundary using `Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())`.

**Month 0-indexing in the legacy API**

`java.util.Calendar` numbers months from 0 (January = 0, December = 11). `java.time` numbers months from 1. There is no off-by-one to remember with `java.time`.

---

Solutions for these exercises are in the `solutions/` subfolder.
