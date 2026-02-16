# Encapsulation

Encapsulation is the practice of **bundling data (fields) and the methods that operate on that data into a single unit (class)**, while **restricting direct access** to the internal state of the object. It is the most fundamental pillar of OOP and the basis for writing secure, maintainable code.

The core idea is simple: an object should control how its data is accessed and modified. Outside code should never be able to put an object into an invalid state.

## 1. Why Encapsulation Matters

Without encapsulation:
```java
public class BankAccount {
    public double balance; // Anyone can modify this directly
}

// Dangerous - no validation, no control
BankAccount account = new BankAccount();
account.balance = -5000; // Invalid state, no one stopped it
```

With encapsulation:
```java
public class BankAccount {
    private double balance; // Only accessible through methods

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds or invalid amount.");
        }
    }

    public double getBalance() {
        return balance;
    }
}
```

Now the balance can never be set to a negative value. The class **controls its own state**.

## 2. How to Achieve Encapsulation

Encapsulation in Java follows a straightforward pattern:

1. Declare fields as `private`
2. Provide `public` getter methods to read field values
3. Provide `public` setter methods to modify field values (with validation if needed)

```java
public class Employee {
    // Step 1: Private fields
    private String name;
    private double salary;
    private String department;

    // Step 2: Constructor
    public Employee(String name, double salary, String department) {
        this.name = name;
        setSalary(salary);       // Use setter for validation even in constructor
        this.department = department;
    }

    // Step 3: Getters
    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    // Step 4: Setters with validation
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    public void setSalary(double salary) {
        if (salary >= 0) {
            this.salary = salary;
        } else {
            System.out.println("Salary cannot be negative.");
        }
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
```

## 3. Getters and Setters In Depth

### Read-Only Fields
Sometimes a field should be set once and never changed. Provide only a getter, no setter.
```java
public class Student {
    private final String studentId; // final ensures it cannot be reassigned
    private String name;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    // Getter only - no setter for studentId
    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

### Computed Getters
A getter does not need to return a field directly. It can compute a value.
```java
public class Rectangle {
    private double width;
    private double height;

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    // Computed getter - no corresponding field
    public double getArea() {
        return width * height;
    }

    // Computed getter
    public double getPerimeter() {
        return 2 * (width + height);
    }
}
```

### Defensive Copying
When a getter returns a mutable object (like an array or list), return a copy to prevent external code from modifying internal state.
```java
import java.util.Arrays;

public class Gradebook {
    private int[] grades;

    public Gradebook(int[] grades) {
        this.grades = Arrays.copyOf(grades, grades.length); // Defensive copy in
    }

    public int[] getGrades() {
        return Arrays.copyOf(grades, grades.length); // Defensive copy out
    }
}
```

## 4. Encapsulation with Behavior

Encapsulation is not just about getters and setters. It is about exposing **meaningful operations** rather than raw data access.

### Poor Encapsulation (Data-Centric)
```java
public class Timer {
    private int seconds;

    public int getSeconds() { return seconds; }
    public void setSeconds(int seconds) { this.seconds = seconds; }
}

// Client code must manage the logic
Timer timer = new Timer();
timer.setSeconds(timer.getSeconds() + 60); // Adding a minute
timer.setSeconds(timer.getSeconds() - 10); // Subtracting 10 seconds
```

### Good Encapsulation (Behavior-Centric)
```java
public class Timer {
    private int seconds;

    public int getSeconds() {
        return seconds;
    }

    public void addMinutes(int minutes) {
        if (minutes > 0) {
            seconds += minutes * 60;
        }
    }

    public void subtractSeconds(int amount) {
        if (amount > 0 && amount <= seconds) {
            seconds -= amount;
        }
    }

    public void reset() {
        seconds = 0;
    }

    public String getFormattedTime() {
        int mins = seconds / 60;
        int secs = seconds % 60;
        return mins + "m " + secs + "s";
    }
}

// Client code uses meaningful operations
Timer timer = new Timer();
timer.addMinutes(5);
timer.subtractSeconds(10);
System.out.println(timer.getFormattedTime()); // Output: 4m 50s
```

## 5. Access Modifier Levels

Encapsulation relies on access modifiers to enforce boundaries.

| Modifier    | Visibility                              | Use Case                          |
|-------------|-----------------------------------------|-----------------------------------|
| `private`   | Same class only                         | Fields, internal helper methods   |
| default     | Same package only                       | Package-internal utility classes  |
| `protected` | Same package + subclasses               | Fields/methods meant for subclasses |
| `public`    | Everywhere                              | API methods, constructors         |

**General rule**: Use the most restrictive access level that makes sense.

## 6. Complete Example

```java
public class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        setPrice(price);
        setQuantity(quantity);
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters with validation
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        }
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    // Behavior methods
    public double getTotalValue() {
        return price * quantity;
    }

    public void restock(int amount) {
        if (amount > 0) {
            quantity += amount;
        }
    }

    public boolean sell(int amount) {
        if (amount > 0 && amount <= quantity) {
            quantity -= amount;
            return true;
        }
        return false;
    }

    public void applyDiscount(double percentage) {
        if (percentage > 0 && percentage <= 100) {
            price -= price * (percentage / 100);
        }
    }

    @Override
    public String toString() {
        return "Product{name='" + name + "', price=" + price + ", quantity=" + quantity + "}";
    }
}
```

```java
// Usage
Product laptop = new Product("Laptop", 999.99, 50);
laptop.sell(3);
laptop.applyDiscount(10);
System.out.println(laptop);
// Output: Product{name='Laptop', price=899.991, quantity=47}
System.out.println("Total stock value: " + laptop.getTotalValue());
```

## 7. Key Takeaways

- Encapsulation protects the internal state of an object from uncontrolled access
- Always declare fields as `private`
- Provide getters and setters to control how fields are accessed and modified
- Add validation in setters to prevent invalid state
- Prefer exposing meaningful behavior over raw data access
- Use `final` for fields that should not change after construction
- Return copies of mutable objects to prevent external modification

---

Encapsulation is the foundation upon which the other three pillars are built. Without encapsulation, inheritance and polymorphism become unreliable because any code could corrupt an object's internal state.