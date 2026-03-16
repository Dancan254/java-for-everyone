import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * LambdasDemo.java
 *
 * A self-contained demonstration of Java Lambdas covering every concept in lambdas.md:
 * lambda syntax, all 8 built-in functional interfaces, all 4 method reference forms,
 * variable capture, Predicate/Function chaining, and lambdas with collections.
 */
public class LambdasDemo {

    // -------------------------------------------------------------------------
    // A custom functional interface used in section 1.
    // @FunctionalInterface tells the compiler: this interface must have exactly one abstract method.
    @FunctionalInterface
    interface Transformer<T> {
        T transform(T input);
    }

    // A Counter class used to demonstrate that instance fields can be freely mutated in lambdas.
    static class Counter {
        int count = 0; // Instance field — no effectively-final restriction in lambdas.
    }

    // An Employee class used in the complete example at the end.
    static class Employee {
        private final String name;
        private final String department;
        private final double salary;
        private final int yearsExperience;

        public Employee(String name, String department, double salary, int yearsExperience) {
            this.name            = name;
            this.department      = department;
            this.salary          = salary;
            this.yearsExperience = yearsExperience;
        }

        public String getName()          { return name; }
        public String getDepartment()    { return department; }
        public double getSalary()        { return salary; }
        public int    getYearsExperience() { return yearsExperience; }

        @Override
        public String toString() {
            return name + " [" + department + ", $" + salary + ", " + yearsExperience + " yrs]";
        }
    }

    // -------------------------------------------------------------------------
    // Helper that filters a list using a Predicate — shows lambdas as method arguments.
    static List<Employee> filterEmployees(List<Employee> list, Predicate<Employee> filter) {
        List<Employee> result = new ArrayList<>();
        for (Employee e : list) {
            if (filter.test(e)) {
                result.add(e);
            }
        }
        return result;
    }

    // Helper that maps a list through a Function, returning a new list of the result type.
    static List<String> transformEmployees(List<Employee> list, Function<Employee, String> mapper) {
        List<String> result = new ArrayList<>();
        for (Employee e : list) {
            result.add(mapper.apply(e));
        }
        return result;
    }

    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- 1. Lambda Syntax and Functional Interfaces ---

        System.out.println("=== 1. Lambda Syntax ===");

        // No parameters: () -> expression
        Runnable greet = () -> System.out.println("Hello from a lambda!");
        greet.run();
        // Output: Hello from a lambda!

        // One parameter — parentheses are optional when there is exactly one parameter.
        Transformer<String> upper = name -> name.toUpperCase();
        System.out.println(upper.transform("alice"));
        // Output: ALICE

        // Two parameters — parentheses are required.
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
        System.out.println(add.apply(3, 4));
        // Output: 7

        // Multi-line body — requires braces and an explicit return statement.
        BiFunction<Integer, Integer, Integer> addVerbose = (a, b) -> {
            int sum = a + b;
            return sum;
        };
        System.out.println(addVerbose.apply(10, 20));
        // Output: 30

        // Explicit parameter types — rarely needed; the compiler infers them from the interface.
        BiFunction<String, Integer, String> repeat = (String str, int times) -> str.repeat(times);
        System.out.println(repeat.apply("ab", 3));
        // Output: ababab

        // --- 2. The 8 Built-In Functional Interfaces ---

        System.out.println("\n=== 2. Built-In Functional Interfaces ===");

        // --- Predicate<T> : T -> boolean ---
        // Tests whether a value satisfies a condition. Used for filtering.
        Predicate<Integer> isEven    = n -> n % 2 == 0;
        Predicate<String>  isNonBlank = s -> !s.isBlank();

        System.out.println(isEven.test(4));        // Output: true
        System.out.println(isEven.test(7));        // Output: false
        System.out.println(isNonBlank.test("hi")); // Output: true
        System.out.println(isNonBlank.test("  ")); // Output: false

        // --- Function<T, R> : T -> R ---
        // Transforms a value of type T into a value of type R.
        Function<String, Integer> getLength  = s -> s.length();
        Function<String, String>  toUpperStr = s -> s.toUpperCase();

        System.out.println(getLength.apply("lambda"));  // Output: 6
        System.out.println(toUpperStr.apply("hello"));  // Output: HELLO

        // --- Consumer<T> : T -> void ---
        // Consumes a value and produces no result. Used for side effects.
        Consumer<String> printIt = s -> System.out.println(s);
        printIt.accept("consumed!"); // Output: consumed!

        List<String> collected = new ArrayList<>();
        Consumer<String> saveToList = s -> collected.add(s);
        saveToList.accept("alpha");
        saveToList.accept("beta");
        System.out.println(collected); // Output: [alpha, beta]

        // --- Supplier<T> : () -> T ---
        // Takes no input and produces a value. Useful for lazy evaluation and defaults.
        Supplier<String> defaultName = () -> "Unknown";
        System.out.println(defaultName.get()); // Output: Unknown

        // The supplier is only invoked when the value is actually needed.
        Supplier<List<String>> listFactory = () -> new ArrayList<>();
        List<String> freshList = listFactory.get();
        freshList.add("test");
        System.out.println(freshList); // Output: [test]

        // --- BiFunction<T, U, R> : (T, U) -> R ---
        // Takes two inputs of potentially different types and produces a result.
        BiFunction<String, Integer, String> repeatStr = (str, n) -> str.repeat(n);
        System.out.println(repeatStr.apply("ha", 3)); // Output: hahaha

        // --- BiPredicate<T, U> : (T, U) -> boolean ---
        // Tests a condition over two inputs.
        BiPredicate<String, String> startsWith = (str, prefix) -> str.startsWith(prefix);
        System.out.println(startsWith.test("lambda", "lam")); // Output: true
        System.out.println(startsWith.test("lambda", "xyz")); // Output: false

        // --- UnaryOperator<T> : T -> T ---
        // A specialization of Function where input and output are the same type.
        UnaryOperator<Integer> doubleIt  = n -> n * 2;
        UnaryOperator<String>  reverseIt = s -> new StringBuilder(s).reverse().toString();

        System.out.println(doubleIt.apply(5));      // Output: 10
        System.out.println(reverseIt.apply("hello")); // Output: olleh

        // --- BinaryOperator<T> : (T, T) -> T ---
        // A specialization of BiFunction where both inputs and output are the same type.
        BinaryOperator<Integer> addInts  = (a, b) -> a + b;
        BinaryOperator<String>  concatStr = (a, b) -> a + b;

        System.out.println(addInts.apply(6, 4));             // Output: 10
        System.out.println(concatStr.apply("Hello, ", "World")); // Output: Hello, World

        // --- 3. Method References (all 4 forms) ---

        System.out.println("\n=== 3. Method References ===");

        // Form 1: Static method reference — ClassName::staticMethod
        // Use when the lambda just calls a static method with the same argument.
        Function<String, Integer> parseToInt = Integer::parseInt;
        System.out.println(parseToInt.apply("42")); // Output: 42

        Function<Integer, Integer> absVal = Math::abs;
        System.out.println(absVal.apply(-7)); // Output: 7

        // Form 2: Bound instance method reference — object::instanceMethod
        // The instance is known ahead of time; the lambda passes its argument to that instance.
        Consumer<String> print = System.out::println;
        print.accept("bound reference"); // Output: bound reference

        String prefix = "Hello, ";
        Function<String, String> greetFn = prefix::concat;
        System.out.println(greetFn.apply("Alice")); // Output: Hello, Alice

        // Form 3: Unbound instance method reference — ClassName::instanceMethod
        // The lambda receives an instance of the class as its first argument and calls the method on it.
        Function<String, String>  upperRef  = String::toUpperCase;
        Function<String, Integer> lengthRef = String::length;

        System.out.println(upperRef.apply("hello"));   // Output: HELLO
        System.out.println(lengthRef.apply("lambda")); // Output: 6

        // Form 4: Constructor reference — ClassName::new
        // Replaces a lambda that creates a new instance.
        Supplier<ArrayList<String>>     makeList    = ArrayList::new;
        Function<String, StringBuilder> makeBuilder = StringBuilder::new;

        ArrayList<String> newList = makeList.get();
        newList.add("created via constructor reference");
        System.out.println(newList); // Output: [created via constructor reference]

        System.out.println(makeBuilder.apply("hello")); // Output: hello

        // --- 4. Variable Capture ---

        System.out.println("\n=== 4. Variable Capture ===");

        // Local variables captured by a lambda must be effectively final —
        // meaning they are never reassigned after the point of capture.
        String greeting = "Hello";
        Consumer<String> greetPerson = name -> System.out.println(greeting + ", " + name + "!");

        greetPerson.accept("Alice"); // Output: Hello, Alice!
        greetPerson.accept("Bob");   // Output: Hello, Bob!

        // Instance fields have no such restriction — they live on the heap, not the stack.
        Counter counter = new Counter();
        Runnable increment = () -> counter.count++;

        increment.run();
        increment.run();
        increment.run();
        System.out.println("Count: " + counter.count); // Output: Count: 3

        // --- 5. Predicate Chaining ---

        System.out.println("\n=== 5. Predicate Chaining ===");

        Predicate<String> isNonNull  = s -> s != null;
        Predicate<String> notBlank   = s -> !s.isBlank();
        Predicate<String> longEnough = s -> s.length() >= 8;

        // and() — all conditions must pass.
        Predicate<String> isValidPassword = isNonNull.and(notBlank).and(longEnough);

        System.out.println(isValidPassword.test("secret99")); // Output: true
        System.out.println(isValidPassword.test("short"));    // Output: false
        System.out.println(isValidPassword.test(""));         // Output: false

        // or() — at least one condition must pass.
        Predicate<Integer> isNegative  = n -> n < 0;
        Predicate<Integer> isZero      = n -> n == 0;
        Predicate<Integer> isNonPositive = isNegative.or(isZero);

        System.out.println(isNonPositive.test(-3)); // Output: true
        System.out.println(isNonPositive.test(0));  // Output: true
        System.out.println(isNonPositive.test(5));  // Output: false

        // negate() — flips the result.
        Predicate<String> isBlank    = String::isBlank;
        Predicate<String> isNotBlank = isBlank.negate();

        System.out.println(isNotBlank.test("hello")); // Output: true
        System.out.println(isNotBlank.test("   "));   // Output: false

        // --- 6. Function Chaining ---

        System.out.println("\n=== 6. Function Chaining ===");

        Function<String, Integer>  parseInt  = Integer::parseInt;
        Function<Integer, Integer> doubleInt = n -> n * 2;
        Function<Integer, String>  label     = n -> "Result: " + n;

        // andThen(f) applies f after the current function.
        Function<String, String> pipeline = parseInt.andThen(doubleInt).andThen(label);
        System.out.println(pipeline.apply("21")); // Output: Result: 42

        // compose(f) applies f before the current function (reverse order to andThen).
        Function<Integer, Integer> addOne  = n -> n + 1;
        Function<Integer, Integer> triple  = n -> n * 3;

        // triple.compose(addOne): first addOne, then triple  => (4+1)*3 = 15
        Function<Integer, Integer> addThenTriple = triple.compose(addOne);
        System.out.println(addThenTriple.apply(4)); // Output: 15

        // triple.andThen(addOne): first triple, then addOne  => 4*3+1 = 13
        Function<Integer, Integer> tripleThenAdd = triple.andThen(addOne);
        System.out.println(tripleThenAdd.apply(4)); // Output: 13

        // --- 7. Lambdas with Collections ---

        System.out.println("\n=== 7. Lambdas with Collections ===");

        // List.sort() with a Comparator lambda.
        List<String> cities = new ArrayList<>(List.of("Chicago", "Berlin", "Tokyo", "Cairo"));

        cities.sort((a, b) -> a.compareTo(b));
        System.out.println(cities); // Output: [Berlin, Cairo, Chicago, Tokyo]

        // Sort by length, then alphabetically for ties.
        cities.sort((a, b) -> {
            int byLen = Integer.compare(a.length(), b.length());
            return byLen != 0 ? byLen : a.compareTo(b);
        });
        System.out.println(cities); // Output: [Cairo, Tokyo, Berlin, Chicago]

        // List.removeIf() with a Predicate lambda.
        List<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8));
        nums.removeIf(n -> n % 2 == 0);
        System.out.println(nums); // Output: [1, 3, 5, 7]

        // List.forEach() with a Consumer lambda.
        List<String> fruits = List.of("apple", "banana", "cherry");
        fruits.forEach(fruit -> System.out.println(fruit.toUpperCase()));
        // Output:
        // APPLE
        // BANANA
        // CHERRY

        // Map.forEach() with a BiConsumer lambda (two parameters, no return).
        Map<String, Integer> scores = new HashMap<>();
        scores.put("Alice", 95);
        scores.put("Bob", 82);

        scores.forEach((name, score) ->
            System.out.println(name + " scored " + score));
        // Output (order may vary):
        // Alice scored 95
        // Bob scored 82

        // Map.computeIfAbsent() with a Function lambda.
        // If the key is absent, the Function creates a new list for it.
        Map<String, List<String>> groups = new HashMap<>();
        groups.computeIfAbsent("fruits", key -> new ArrayList<>()).add("apple");
        groups.computeIfAbsent("fruits", key -> new ArrayList<>()).add("banana");
        groups.computeIfAbsent("veggies", key -> new ArrayList<>()).add("carrot");
        System.out.println(groups);
        // Output: {fruits=[apple, banana], veggies=[carrot]}

        // --- 8. Complete Example: Employee Filtering System ---

        System.out.println("\n=== 8. Complete Example: Employee Filtering System ===");

        List<Employee> employees = new ArrayList<>(List.of(
            new Employee("Alice",  "Engineering", 95000, 7),
            new Employee("Bob",    "Marketing",   62000, 3),
            new Employee("Carol",  "Engineering", 80000, 5),
            new Employee("David",  "HR",          58000, 2),
            new Employee("Eve",    "Engineering", 72000, 4),
            new Employee("Frank",  "Marketing",   74000, 6)
        ));

        // Predicate: belongs to Engineering department.
        Predicate<Employee> inEngineering = e -> e.getDepartment().equals("Engineering");
        List<Employee> engineers = filterEmployees(employees, inEngineering);

        System.out.println("Engineering team:");
        engineers.forEach(e -> System.out.println("  " + e));
        // Output:
        // Engineering team:
        //   Alice [Engineering, $95000.0, 7 yrs]
        //   Carol [Engineering, $80000.0, 5 yrs]
        //   Eve [Engineering, $72000.0, 4 yrs]

        // Predicate: salary over 70000.
        Predicate<Employee> highEarner = e -> e.getSalary() > 70000;

        // Combine predicates with and() — both conditions must be true.
        List<Employee> seniorEngineers = filterEmployees(employees, inEngineering.and(highEarner));

        System.out.println("\nSenior Engineers (Engineering + salary > 70000):");
        seniorEngineers.forEach(e -> System.out.println("  " + e));
        // Output:
        // Senior Engineers (Engineering + salary > 70000):
        //   Alice [Engineering, $95000.0, 7 yrs]
        //   Carol [Engineering, $80000.0, 5 yrs]
        //   Eve [Engineering, $72000.0, 4 yrs]

        // Function: map each Employee to their name.
        Function<Employee, String> toName = Employee::getName;
        List<String> allNames = transformEmployees(employees, toName);
        System.out.println("\nAll employee names: " + allNames);
        // Output:
        // All employee names: [Alice, Bob, Carol, David, Eve, Frank]

        // Consumer: print each employee on its own line via forEach.
        System.out.println("\nFull roster:");
        employees.forEach(e -> System.out.println("  " + e));
        // Output:
        // Full roster:
        //   Alice [Engineering, $95000.0, 7 yrs]
        //   Bob [Marketing, $62000.0, 3 yrs]
        //   Carol [Engineering, $80000.0, 5 yrs]
        //   David [HR, $58000.0, 2 yrs]
        //   Eve [Engineering, $72000.0, 4 yrs]
        //   Frank [Marketing, $74000.0, 6 yrs]
    }
}
