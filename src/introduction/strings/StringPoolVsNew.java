package introduction.strings;

public class StringPoolVsNew {

    public static void main(String[] args) {
        //for starters this might be a bit complex, don't worry you will understand with time
        Runtime runtime = Runtime.getRuntime();

        /// Without String Pool
        //trigger garbage collection
        runtime.gc();
        long memoryBeforeNew = runtime.totalMemory() - runtime.freeMemory();
        long startNew = System.nanoTime();

        String[] wordsNew = new String[1000];
        for (int i = 0; i < 1000; i++) {
            wordsNew[i] = new String("Hello World");
        }
        long endNew = System.nanoTime();
        long memoryAfterNew = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Time taken in nanoseconds to create 1000 strings using new: " + (endNew - startNew));
        System.out.println("Memory before creating 1000 strings using new: " + memoryBeforeNew + " bytes");
        System.out.println("Memory after creating 1000 strings using new: " + memoryAfterNew + " bytes");
        System.out.println("Memory used after creating 1000 strings using new: " + (memoryAfterNew - memoryBeforeNew) + " bytes");
        //memory in kbs
        System.out.println("Memory used after creating 1000 strings using new: " + ((memoryAfterNew - memoryBeforeNew) / 1024) + " kbs");

        System.out.println("-".repeat(20));
        System.out.println("With String Pool");
        //trigger garbage collection
        runtime.gc();
        long memoryBeforePool = runtime.totalMemory() - runtime.freeMemory();
        long startPool = System.nanoTime();
        String[] wordsPool = new String[1000];
        for (int i = 0; i < 1000; i++) {
            wordsPool[i] = "Hello World";
        }
        long endPool = System.nanoTime();
        long memoryAfterPool = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Time taken in nanoseconds to create 1000 strings using pool: " + (endPool - startPool) + "");
        System.out.println("Memory before creating 1000 strings using pool: " + memoryBeforePool + " bytes");
        System.out.println("Memory after creating 1000 strings using pool: " + memoryAfterPool + " bytes");
        System.out.println("Memory used after creating 1000 strings using pool: " + (memoryAfterPool - memoryBeforePool) + " bytes");
        System.out.println("Memory used in kbs after creating 1000 strings using pool: " + ((memoryAfterPool - memoryBeforePool) / 1024) + " kbs");
    }
}
