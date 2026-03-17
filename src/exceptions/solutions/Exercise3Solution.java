package exceptions.solutions;

/**
 * Exercise3Solution.java
 *
 * Solutions for Exercise Set 3: Custom Exceptions.
 * Covers: custom unchecked exception with an extra field and accessor,
 * custom checked exception with multiple constructors, and a small exception
 * hierarchy with a shared base class and polymorphic catch.
 */
public class Exercise3Solution {

    // -------------------------------------------------------------------------
    // Exercise 3.1 — InsufficientFundsException (unchecked)
    // -------------------------------------------------------------------------

    /**
     * Thrown when a withdrawal cannot be fulfilled because the balance is too low.
     * Extends RuntimeException (unchecked) because this represents an invalid
     * operation on a domain object — the caller controls the amount and should
     * check the balance first.
     *
     * The extra field 'amount' lets callers retrieve the shortfall without having
     * to parse the exception message.
     */
    static class InsufficientFundsException extends RuntimeException {
        private final double amount;

        public InsufficientFundsException(double amount) {
            super("Insufficient funds. Attempted to withdraw: " + amount);
            this.amount = amount;
        }

        public double getAmount() {
            return amount;
        }
    }

    static class BankAccount {
        private double balance;

        public BankAccount(double balance) {
            this.balance = balance;
        }

        public void withdraw(double amount) {
            if (amount > balance) {
                throw new InsufficientFundsException(amount);
            }
            balance -= amount;
        }

        public double getBalance() { return balance; }
    }

    // -------------------------------------------------------------------------
    // Exercise 3.2 — InvalidAgeException (checked)
    // -------------------------------------------------------------------------

    /**
     * Thrown when an age value falls outside the acceptable range 0–150.
     * Extends Exception (checked) because age validation is part of the public
     * contract of setAge — the caller must explicitly handle or acknowledge that
     * the input might be rejected.
     *
     * Two constructors are provided: a raw-message constructor for flexibility
     * and a convenience constructor that builds the message from the bad age value.
     */
    static class InvalidAgeException extends Exception {
        public InvalidAgeException(String message) {
            super(message);
        }

        public InvalidAgeException(int age) {
            super("Age " + age + " is not valid (must be 0-150).");
        }
    }

    static class Person {
        private String name;
        private int    age;

        public Person(String name) {
            this.name = name;
        }

        /** Sets the age or throws InvalidAgeException if the value is out of range. */
        public void setAge(int age) throws InvalidAgeException {
            if (age < 0 || age > 150) {
                throw new InvalidAgeException(age);
            }
            this.age = age;
        }

        public int    getAge()  { return age;  }
        public String getName() { return name; }
    }

    // -------------------------------------------------------------------------
    // Exercise 3.3 — Exception Hierarchy
    // -------------------------------------------------------------------------

    /**
     * Base exception for all application-level errors.
     * Carries a 'code' string so callers can identify the failure type
     * programmatically without parsing the message.
     */
    static class AppException extends RuntimeException {
        private final String code;

        public AppException(String code, String message) {
            super(message);
            this.code = code;
        }

        public String getCode() { return code; }
    }

    /** Thrown when a user cannot be found by the given ID. */
    static class UserNotFoundException extends AppException {
        public UserNotFoundException(long userId) {
            super("USER_NOT_FOUND", "No user found with id=" + userId);
        }
    }

    /** Thrown when an email address is already registered. */
    static class DuplicateEmailException extends AppException {
        public DuplicateEmailException(String email) {
            super("DUPLICATE_EMAIL", "Email already registered: " + email);
        }
    }

    /** Demonstrates throwing from domain-level service methods. */
    static class UserService {
        public void findUser(long id) {
            // Simulate: only id=1 exists.
            if (id != 1L) {
                throw new UserNotFoundException(id);
            }
            System.out.println("Found user with id=" + id);
        }

        public void registerUser(String email) {
            // Simulate: "taken@example.com" is already registered.
            if ("taken@example.com".equals(email)) {
                throw new DuplicateEmailException(email);
            }
            System.out.println("Registered: " + email);
        }
    }

    // -------------------------------------------------------------------------
    // main
    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- Exercise 3.1: InsufficientFundsException ---

        System.out.println("=== Exercise 3.1: InsufficientFundsException ===");

        BankAccount account = new BankAccount(50.0);

        try {
            account.withdraw(200.0);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
            System.out.println("Shortfall amount: " + e.getAmount());
        }
        // Output:
        // Insufficient funds. Attempted to withdraw: 200.0
        // Shortfall amount: 200.0

        account.withdraw(30.0);
        System.out.println("Balance after valid withdrawal: " + account.getBalance());
        // Output: Balance after valid withdrawal: 20.0

        // --- Exercise 3.2: InvalidAgeException ---

        System.out.println("\n=== Exercise 3.2: InvalidAgeException ===");

        Person alice = new Person("Alice");

        try {
            alice.setAge(30);
            System.out.println(alice.getName() + " age set to " + alice.getAge());
            // Output: Alice age set to 30
        } catch (InvalidAgeException e) {
            System.out.println(e.getMessage());
        }

        try {
            alice.setAge(-5);
        } catch (InvalidAgeException e) {
            System.out.println("Caught: " + e.getMessage());
            // Output: Caught: Age -5 is not valid (must be 0-150).
        }

        try {
            alice.setAge(200);
        } catch (InvalidAgeException e) {
            System.out.println("Caught: " + e.getMessage());
            // Output: Caught: Age 200 is not valid (must be 0-150).
        }

        // Using the raw-message constructor directly.
        try {
            throw new InvalidAgeException("Custom validation message.");
        } catch (InvalidAgeException e) {
            System.out.println("Caught: " + e.getMessage());
            // Output: Caught: Custom validation message.
        }

        // --- Exercise 3.3: Exception Hierarchy ---

        System.out.println("\n=== Exercise 3.3: Exception Hierarchy ===");

        UserService service = new UserService();

        // Polymorphic catch: both UserNotFoundException and DuplicateEmailException
        // are AppException subclasses. One handler covers the entire hierarchy.
        String[] emails = {"new@example.com", "taken@example.com"};
        long[]   ids    = {1L, 42L};

        for (long id : ids) {
            try {
                service.findUser(id);
            } catch (AppException e) {
                System.out.println("code=" + e.getCode() + ", message=" + e.getMessage());
            }
        }
        // Output:
        // Found user with id=1
        // code=USER_NOT_FOUND, message=No user found with id=42

        for (String email : emails) {
            try {
                service.registerUser(email);
            } catch (AppException e) {
                System.out.println("code=" + e.getCode() + ", message=" + e.getMessage());
            }
        }
        // Output:
        // Registered: new@example.com
        // code=DUPLICATE_EMAIL, message=Email already registered: taken@example.com
    }
}
