package enums.solutions;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

/**
 * Exercise5Solution.java
 *
 * Solutions for Exercise Set 5: EnumSet and EnumMap.
 * Covers: EnumSet.of(), EnumSet.allOf(), EnumSet.range(), EnumSet.complement(),
 * membership testing, and EnumMap for key-value associations.
 */
public class Exercise5Solution {

    enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

        public boolean isWeekend() {
            return this == SATURDAY || this == SUNDAY;
        }
    }

    enum Season {
        SPRING, SUMMER, FALL, WINTER
    }

    // -------------------------------------------------------------------------
    // Exercise 5.1 — EnumSet operations
    // -------------------------------------------------------------------------

    static void exercise5_1() {
        System.out.println("=== Exercise 5.1: EnumSet ===");

        // A set of specific constants
        EnumSet<DayOfWeek> workdays = EnumSet.of(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY
        );
        System.out.println("Workdays: " + workdays);
        // Output: [MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY]

        // All constants
        EnumSet<DayOfWeek> allDays = EnumSet.allOf(DayOfWeek.class);
        System.out.println("All days count: " + allDays.size()); // Output: 7

        // A contiguous range (inclusive on both ends)
        EnumSet<DayOfWeek> midweek = EnumSet.range(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY);
        System.out.println("Midweek: " + midweek);
        // Output: [TUESDAY, WEDNESDAY, THURSDAY]

        // Complement — everything NOT in workdays (i.e. the weekend)
        EnumSet<DayOfWeek> weekend = EnumSet.complementOf(workdays);
        System.out.println("Weekend: " + weekend);
        // Output: [SATURDAY, SUNDAY]

        // Membership test
        System.out.println("Workdays contains SATURDAY? " + workdays.contains(DayOfWeek.SATURDAY));
        // Output: false
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 5.2 — EnumMap for key-value associations
    // -------------------------------------------------------------------------

    static void exercise5_2() {
        System.out.println("=== Exercise 5.2: EnumMap ===");

        EnumMap<Season, String> activities = new EnumMap<>(Season.class);
        activities.put(Season.SPRING, "Gardening");
        activities.put(Season.SUMMER, "Swimming");
        activities.put(Season.FALL,   "Hiking");
        activities.put(Season.WINTER, "Skiing");

        System.out.println("Activity in FALL: " + activities.get(Season.FALL));
        // Output: Hiking

        System.out.println("\nAll season activities:");
        for (Map.Entry<Season, String> entry : activities.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        // Output (in declaration order, because EnumMap maintains enum order):
        // SPRING: Gardening
        // SUMMER: Swimming
        // FALL: Hiking
        // WINTER: Skiing
        System.out.println();
    }

    public static void main(String[] args) {
        exercise5_1();
        exercise5_2();
    }
}
