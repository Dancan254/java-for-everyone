package arrays;

public class ArrayHeapAnalysis {

    // Class fields to keep arrays alive
    private static int[] smallArray;
    private static int[] mediumArray;
    private static int[] largeArray;
    private static String[] stringArray;
    private static int[][] matrix2D;

    public static void main(String[] args) throws Exception {
        System.out.println("PID: " + ProcessHandle.current().pid());
        System.out.println("Attach VisualVM to this PID!");
        System.out.println("Press Enter after attaching VisualVM...");
        System.in.read();

        // Allocate different arrays
        System.out.println("Allocating arrays...");

        smallArray = new int[100];
        System.out.println("Small array allocated (100 ints = 400 bytes + header)");
        Thread.sleep(2000);

        mediumArray = new int[10_000];
        System.out.println("Medium array allocated (10K ints = 40KB + header)");
        Thread.sleep(2000);

        largeArray = new int[1_000_000];
        System.out.println("Large array allocated (1M ints = 4MB + header)");
        Thread.sleep(2000);

        stringArray = new String[1000];
        for (int i = 0; i < stringArray.length; i++) {
            stringArray[i] = "String_" + i;
        }
        System.out.println("String array allocated (1000 strings)");
        Thread.sleep(2000);

        matrix2D = new int[100][100];
        System.out.println("2D array allocated (100x100 = 10K ints)");
        Thread.sleep(2000);

        System.out.println("\nAll arrays allocated!");
        System.out.println("Now take a heap dump in VisualVM...");
        System.out.println("Press Enter to trigger GC...");
        System.in.read();

        // Clear references to see GC in action
        System.out.println("Clearing references...");
        smallArray = null;
        mediumArray = null;
        largeArray = null;
        stringArray = null;
        matrix2D = null;

        System.gc();
        System.out.println("GC triggered!");
        Thread.sleep(2000);

        System.out.println("Take another heap dump to see difference...");
        System.out.println("Press Enter to exit...");
        System.in.read();
    }
}
