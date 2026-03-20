package enums.solutions;

/**
 * Exercise2Solution.java
 *
 * Solutions for Exercise Set 2: Enums with Fields and Constructors.
 * Covers: enums with a single field (TrafficLight with duration),
 * and enums with multiple fields and computed methods (Planet gravity).
 */
public class Exercise2Solution {

    // -------------------------------------------------------------------------
    // Supporting enums
    // -------------------------------------------------------------------------

    enum TrafficLight {
        RED(45), YELLOW(5), GREEN(30);

        private final int durationSeconds;

        TrafficLight(int durationSeconds) {
            this.durationSeconds = durationSeconds;
        }

        public int getDuration() {
            return durationSeconds;
        }
    }

    enum Planet {
        MERCURY(3.303e+23, 2.4397e6),
        VENUS  (4.869e+24, 6.0518e6),
        EARTH  (5.976e+24, 6.37814e6),
        MARS   (6.421e+23, 3.3972e6);

        private final double mass;    // kilograms
        private final double radius;  // metres

        Planet(double mass, double radius) {
            this.mass   = mass;
            this.radius = radius;
        }

        static final double G = 6.67300E-11;

        public double surfaceGravity() {
            return G * mass / (radius * radius);
        }

        public double surfaceWeight(double otherMass) {
            return otherMass * surfaceGravity();
        }
    }

    // -------------------------------------------------------------------------
    // Exercise 2.1 — Traffic Light with Duration
    // -------------------------------------------------------------------------

    static void exercise2_1() {
        System.out.println("=== Exercise 2.1: Traffic Light with Duration ===");

        for (TrafficLight light : TrafficLight.values()) {
            System.out.printf("%-6s : %d seconds%n", light, light.getDuration());
        }
        // Output:
        // RED    : 45 seconds
        // YELLOW : 5 seconds
        // GREEN  : 30 seconds
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 2.2 — Planet Gravity Calculator
    // -------------------------------------------------------------------------

    static void exercise2_2() {
        System.out.println("=== Exercise 2.2: Planet Gravity Calculator ===");

        double earthWeight = 75.0;
        double mass        = earthWeight / Planet.EARTH.surfaceGravity();

        for (Planet p : Planet.values()) {
            System.out.printf("Weight on %-7s : %.2f N%n", p, p.surfaceWeight(mass));
        }
        // Output:
        // Weight on MERCURY : 28.33 N
        // Weight on VENUS   : 67.89 N
        // Weight on EARTH   : 75.00 N
        // Weight on MARS    : 28.46 N
        System.out.println();
    }

    public static void main(String[] args) {
        exercise2_1();
        exercise2_2();
    }
}
