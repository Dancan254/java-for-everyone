package collections.solutions;

import java.util.*;

/**
 * Exercise 4.2 — Quiz Application
 *
 * A console-based multiple-choice quiz that uses an enum with a point-value
 * field, groups questions by difficulty, tracks scores per difficulty,
 * and prints a summary using a LinkedHashMap.
 */
public class QuizApplication {

    // ---- Difficulty enum ----
    enum Difficulty {
        EASY(1), MEDIUM(2), HARD(3);

        private final int pointValue;

        Difficulty(int pointValue) {
            this.pointValue = pointValue;
        }

        public int getPointValue() {
            return pointValue;
        }
    }

    // ---- Question class ----
    static class Question {
        private final String text;
        private final List<String> options;
        private final int correctIndex; // 0-3
        private final Difficulty difficulty;

        Question(String text, List<String> options, int correctIndex, Difficulty difficulty) {
            this.text = text;
            this.options = options;
            this.correctIndex = correctIndex;
            this.difficulty = difficulty;
        }

        public String getText() {
            return text;
        }

        public List<String> getOptions() {
            return options;
        }

        public int getCorrectIndex() {
            return correctIndex;
        }

        public Difficulty getDifficulty() {
            return difficulty;
        }
    }

    // ---- Build the question bank ----
    private static List<Question> buildQuestions() {
        List<Question> questions = new ArrayList<>();

        // --- EASY ---
        questions.add(new Question(
                "What keyword is used to define a class in Java?",
                Arrays.asList("function", "class", "define", "struct"),
                1, Difficulty.EASY));

        questions.add(new Question(
                "Which data type is used to store true/false values?",
                Arrays.asList("int", "String", "boolean", "char"),
                2, Difficulty.EASY));

        questions.add(new Question(
                "What does 'System.out.println' do?",
                Arrays.asList("Reads input", "Prints to console", "Declares a variable", "Imports a package"),
                1, Difficulty.EASY));

        // --- MEDIUM ---
        questions.add(new Question(
                "Which collection does NOT allow duplicate elements?",
                Arrays.asList("ArrayList", "LinkedList", "HashSet", "ArrayDeque"),
                2, Difficulty.MEDIUM));

        questions.add(new Question(
                "What is the default value of an int instance variable in Java?",
                Arrays.asList("null", "0", "1", "undefined"),
                1, Difficulty.MEDIUM));

        questions.add(new Question(
                "Which keyword prevents a class from being subclassed?",
                Arrays.asList("static", "abstract", "final", "private"),
                2, Difficulty.MEDIUM));

        // --- HARD ---
        questions.add(new Question(
                "What is the time complexity of HashMap.get() in the average case?",
                Arrays.asList("O(n)", "O(log n)", "O(1)", "O(n log n)"),
                2, Difficulty.HARD));

        questions.add(new Question(
                "Which interface must an enum implement to be used in a TreeSet without a Comparator?",
                Arrays.asList("Serializable", "Comparable", "Iterable", "Cloneable"),
                1, Difficulty.HARD));

        questions.add(new Question(
                "What happens when you call EnumSet.allOf(MyEnum.class)?",
                Arrays.asList(
                        "Throws UnsupportedOperationException",
                        "Returns an empty set",
                        "Returns a set containing all constants of MyEnum",
                        "Returns null"),
                2, Difficulty.HARD));

        return questions;
    }

    // ---- Main ----
    public static void main(String[] args) {

        List<Question> questions = buildQuestions();

        // Group by difficulty
        Map<Difficulty, List<Question>> byDifficulty = new EnumMap<>(Difficulty.class);
        for (Difficulty d : Difficulty.values()) {
            byDifficulty.put(d, new ArrayList<>());
        }
        for (Question q : questions) {
            byDifficulty.get(q.getDifficulty()).add(q);
        }

        // Scoring trackers
        int totalScore = 0;
        int maxScore = 0;
        Map<Difficulty, int[]> difficultyStats = new EnumMap<>(Difficulty.class);
        // int[0] = correct, int[1] = total
        for (Difficulty d : Difficulty.values()) {
            difficultyStats.put(d, new int[] { 0, 0 });
        }

        Scanner scanner = new Scanner(System.in);
        String[] labels = { "A", "B", "C", "D" };

        System.out.println("========================================");
        System.out.println("      JAVA KNOWLEDGE QUIZ");
        System.out.println("========================================\n");

        int questionNum = 1;
        for (Question q : questions) {
            int points = q.getDifficulty().getPointValue();
            maxScore += points;
            difficultyStats.get(q.getDifficulty())[1]++;

            System.out.println("Q" + questionNum + " [" + q.getDifficulty()
                    + " — " + points + " pt" + (points > 1 ? "s" : "") + "]: "
                    + q.getText());
            for (int i = 0; i < q.getOptions().size(); i++) {
                System.out.println("  " + labels[i] + ") " + q.getOptions().get(i));
            }

            System.out.print("Your answer (A/B/C/D): ");
            String input = scanner.nextLine().trim().toUpperCase();

            int answerIndex = -1;
            switch (input) {
                case "A":
                    answerIndex = 0;
                    break;
                case "B":
                    answerIndex = 1;
                    break;
                case "C":
                    answerIndex = 2;
                    break;
                case "D":
                    answerIndex = 3;
                    break;
            }

            if (answerIndex == q.getCorrectIndex()) {
                System.out.println("  ✓ Correct! (+" + points + ")\n");
                totalScore += points;
                difficultyStats.get(q.getDifficulty())[0]++;
            } else {
                System.out.println("  ✗ Wrong. The answer was "
                        + labels[q.getCorrectIndex()] + ") "
                        + q.getOptions().get(q.getCorrectIndex()) + "\n");
            }
            questionNum++;
        }

        // ---- Results Summary ----
        System.out.println("========================================");
        System.out.println("           RESULTS SUMMARY");
        System.out.println("========================================");

        // Using LinkedHashMap to preserve insertion order for display
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("Total Score", totalScore + " / " + maxScore);
        double percentage = (maxScore == 0) ? 0 : (totalScore * 100.0 / maxScore);
        summary.put("Percentage", String.format("%.1f%%", percentage));

        for (Difficulty d : Difficulty.values()) {
            int[] stats = difficultyStats.get(d);
            summary.put(d.name() + " correct", stats[0] + " / " + stats[1]);
        }

        for (Map.Entry<String, Object> entry : summary.entrySet()) {
            System.out.printf("  %-15s : %s%n", entry.getKey(), entry.getValue());
        }

        scanner.close();
    }
}
