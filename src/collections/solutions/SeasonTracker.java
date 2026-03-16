package collections.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Exercise 1.1 — Season Tracker
 *
 * Demonstrates storing enums in an ArrayList, iterating,
 * and finding the hottest / coldest season manually.
 */
public class SeasonTracker {

    // ---- Enum with a field and constructor ----
    enum Season {
        SPRING(65),
        SUMMER(85),
        FALL(55),
        WINTER(30);

        private final int averageTemp;

        Season(int averageTemp) {
            this.averageTemp = averageTemp;
        }

        public int getAverageTemp() {
            return averageTemp;
        }
    }

    public static void main(String[] args) {

        // 1. Store all seasons in an ArrayList
        List<Season> seasons = new ArrayList<>(Arrays.asList(Season.values()));

        // 2. Print each season with its average temperature
        for (Season s : seasons) {
            System.out.printf("%-6s — %d°F%n", s.name(), s.getAverageTemp());
        }

        // 3. Find hottest and coldest using a loop
        Season hottest = seasons.get(0);
        Season coldest = seasons.get(0);

        for (Season s : seasons) {
            if (s.getAverageTemp() > hottest.getAverageTemp()) {
                hottest = s;
            }
            if (s.getAverageTemp() < coldest.getAverageTemp()) {
                coldest = s;
            }
        }

        System.out.println();
        System.out.println("Hottest: " + hottest + " (" + hottest.getAverageTemp() + "°F)");
        System.out.println("Coldest: " + coldest + " (" + coldest.getAverageTemp() + "°F)");
    }
}
