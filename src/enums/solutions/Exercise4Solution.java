package enums.solutions;

/**
 * Exercise4Solution.java
 *
 * Solutions for Exercise Set 4: Enums in switch Expressions and Interfaces.
 * Covers: switch expressions with enums (exhaustive, no default needed),
 * and enums implementing interfaces.
 */
public class Exercise4Solution {

    // -------------------------------------------------------------------------
    // Exercise 4.1 — Season enum in a switch expression
    // -------------------------------------------------------------------------

    enum Season {
        SPRING, SUMMER, FALL, WINTER
    }

    // -------------------------------------------------------------------------
    // Exercise 4.2 — StatusCode enum implementing an interface
    // -------------------------------------------------------------------------

    interface Describable {
        String getDescription();
    }

    enum StatusCode implements Describable {
        OK(200),
        NOT_FOUND(404),
        INTERNAL_SERVER_ERROR(500),
        UNAUTHORIZED(401);

        private final int code;

        StatusCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        @Override
        public String getDescription() {
            return code + " " + name().replace('_', ' ');
        }
    }

    // -------------------------------------------------------------------------

    static void exercise4_1() {
        System.out.println("=== Exercise 4.1: Season switch Expression ===");

        for (Season s : Season.values()) {
            // The switch expression is exhaustive — all four constants are handled.
            // The compiler catches any missing case when a new constant is added.
            String activity = switch (s) {
                case SPRING -> "Plant seeds";
                case SUMMER -> "Go swimming";
                case FALL   -> "Rake leaves";
                case WINTER -> "Build a snowman";
            };
            System.out.println(s + " -> " + activity);
        }
        // Output:
        // SPRING -> Plant seeds
        // SUMMER -> Go swimming
        // FALL   -> Rake leaves
        // WINTER -> Build a snowman
        System.out.println();
    }

    static void exercise4_2() {
        System.out.println("=== Exercise 4.2: StatusCode Implementing Describable ===");

        for (StatusCode sc : StatusCode.values()) {
            System.out.println(sc.getDescription());
        }
        // Output:
        // 200 OK
        // 404 NOT FOUND
        // 500 INTERNAL SERVER ERROR
        // 401 UNAUTHORIZED

        System.out.println();

        // The enum can be used wherever a Describable is expected.
        printDescription(StatusCode.NOT_FOUND);
        System.out.println();
    }

    static void printDescription(Describable d) {
        System.out.println("Description: " + d.getDescription());
    }

    public static void main(String[] args) {
        exercise4_1();
        exercise4_2();
    }
}
