package collections.solutions;

import java.util.*;

/**
 * Exercise 1.3 — Day Frequency Counter
 *
 * Demonstrates using a HashMap to count enum occurrences
 * and printing results sorted by ordinal.
 */
public class DayFrequencyCounter {

    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public static void main(String[] args) {

        // Two-week work schedule
        List<Day> schedule = Arrays.asList(
                Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY,
                Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY,
                Day.SATURDAY);

        // Count occurrences using a HashMap
        Map<Day, Integer> frequency = new HashMap<>();

        // Initialize all days to 0 so every day appears in output
        for (Day d : Day.values()) {
            frequency.put(d, 0);
        }

        // Tally up the schedule
        for (Day d : schedule) {
            frequency.put(d, frequency.get(d) + 1);
        }

        // Print sorted by ordinal (Day.values() is already in declaration order)
        for (Day d : Day.values()) {
            System.out.printf("%-9s : %d%n", d.name(), frequency.get(d));
        }
    }
}
