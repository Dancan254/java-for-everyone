package collections.solutions;

import java.util.*;

/**
 * Exercise 2.1 — Task Priority Board
 *
 * Demonstrates EnumMap with List values, adding/removing items,
 * and moving an item between priority levels.
 */
public class TaskPriorityBoard {

    enum Priority {
        LOW, MEDIUM, HIGH, CRITICAL
    }

    public static void main(String[] args) {

        // Build the board using EnumMap
        EnumMap<Priority, List<String>> board = new EnumMap<>(Priority.class);

        // Initialize each priority with an empty list, then add tasks
        board.put(Priority.LOW, new ArrayList<>(Arrays.asList(
                "Organize desk", "Update profile picture")));
        board.put(Priority.MEDIUM, new ArrayList<>(Arrays.asList(
                "Write unit tests", "Review documentation")));
        board.put(Priority.HIGH, new ArrayList<>(Arrays.asList(
                "Fix login bug", "Optimize database queries")));
        board.put(Priority.CRITICAL, new ArrayList<>(Arrays.asList(
                "Patch security vulnerability", "Restore backups")));

        // Print the board
        printBoard(board, "Task Board");

        // Move "Organize desk" from LOW -> HIGH
        String taskToMove = "Organize desk";
        board.get(Priority.LOW).remove(taskToMove);
        board.get(Priority.HIGH).add(taskToMove);
        System.out.println("Moving \"" + taskToMove + "\" from LOW -> HIGH\n");

        // Print again to verify
        printBoard(board, "Task Board (updated)");
    }

    private static void printBoard(EnumMap<Priority, List<String>> board, String title) {
        System.out.println("--- " + title + " ---");
        for (Map.Entry<Priority, List<String>> entry : board.entrySet()) {
            System.out.printf("%-8s : %s%n", entry.getKey(), entry.getValue());
        }
        System.out.println();
    }
}
