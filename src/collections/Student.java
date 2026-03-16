package collections;

import java.util.*;

public class Student {
    public static void main(String[] args) {

        List<StudentInfo> students = new ArrayList<>();
        StudentInfo student = new StudentInfo("chalo", 34);

        //add the student to the list
        students.add(student);

        students.add(new StudentInfo("Mongs", 23));
        students.addFirst(new StudentInfo("Chalo", 34));
        //print
        System.out.println(students);

        Queue<StudentInfo> queue = new LinkedList<>();

        StudentInfo[] studentsArray = new StudentInfo[20];
        studentsArray[0] = new StudentInfo("Chalo", 34);

        List<StudentInfo> studentsList = new LinkedList<>();
        studentsList.add(new StudentInfo("Chalo", 34));

        System.out.println("Linkedlist");
        LinkedList<String> tasks = new LinkedList<>();

        tasks.add("Write tests");
        tasks.add("Review PR");
        tasks.add("Deploy");

        // LinkedList-specific methods for working at the ends
        tasks.addFirst("Plan sprint");      // inserts at the front
        tasks.addLast("Send report");       // inserts at the back
        tasks.contains("Write tests");

        System.out.println(tasks.peekFirst()); // Output: Plan sprint  (does not remove)
        System.out.println(tasks.peekLast());  // Output: Send report  (does not remove)

        tasks.removeFirst();  // removes and returns "Plan sprint"
        tasks.removeLast();   // removes and returns "Send report"

        System.out.println(tasks);

        List<String> words = List.of("Java", "Python", "Java", "Python");
        Set<String> uniqueWords = new HashSet<>(words);
        System.out.println(uniqueWords);
        System.out.println(uniqueWords);

        List<String> input = Arrays.asList("banana", "apple", "cherry", "apple", "date");

        // HashSet: no duplicates, unpredictable order
        Set<String> hashSet = new HashSet<>(input);

        // LinkedHashSet: no duplicates, insertion order preserved
        Set<String> linkedHashSet = new LinkedHashSet<>(input);

        // TreeSet: no duplicates, sorted alphabetically
        Set<String> treeSet = new TreeSet<>(input);
        System.out.println("HashSet:       " + hashSet);
        // Output: HashSet:       [banana, cherry, apple, date]  (order varies)

        System.out.println("LinkedHashSet: " + linkedHashSet);
        // Output: LinkedHashSet: [banana, apple, cherry, date]

        System.out.println("TreeSet:       " + treeSet);
        // Output: TreeSet:       [apple, banana, cherry, date]

        Map<String, Integer> scores = new HashMap<>();

// Adding and updating entries
        scores.put("Alice", 92);
        scores.put("Bob", 85);
        scores.put("Carol", 90);
        for(String key : scores.keySet()){
            System.out.println(key);
        }
        for(Integer value : scores.values()){
            System.out.println(value);
        }

        for(Map.Entry<String, Integer> entry : scores.entrySet()){
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue() + "");
        }

        scores.put("Alice", 95);    // overwrites the previous value for "Alice"

// Reading values
        System.out.println(scores.get("Bob"));          // Output: 85
        System.out.println(scores.get("Dave"));         // Output: null  (key does not exist)
        System.out.println(scores.getOrDefault("Chalo", 0)); // Output: 0

// Checking membership
        System.out.println(scores.containsKey("Carol"));    // Output: true
        System.out.println(scores.containsValue(85));        // Output: true

// Conditional insertion
        scores.putIfAbsent("Bob", 99);   // ignored because "Bob" already exists
        System.out.println(scores.get("Bob")); // Output: 85

// Removing an entry
        scores.remove("Carol");
        System.out.println(scores.size()); // Output: 2
    }
}

class StudentInfo{
    private String name;
    private int age;

    public StudentInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "StudentInfo{" + "name=" + name + ", age=" + age + '}';
    }
}
