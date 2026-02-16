# Abstraction

Abstraction is the principle of **hiding complex implementation details and exposing only the essential features** of an object. It allows you to define **what** an object does without specifying **how** it does it. In Java, abstraction is achieved through **abstract classes** and **interfaces**.

```
Think of it like this:
  When you drive a car, you use the steering wheel, pedals, and gear shift.
  You do not need to know how the engine combustion cycle works.
  The car abstracts away the complexity and gives you a simple interface.
```

## 1. Abstract Classes

An **abstract class** is a class that cannot be instantiated on its own. It serves as a base class that defines a common structure for its subclasses. It can contain both **abstract methods** (no implementation) and **concrete methods** (with implementation).

### Declaring an Abstract Class
```java
public abstract class Shape {
    private String color;

    public Shape(String color) {
        this.color = color;
    }

    // Abstract method - no body, must be implemented by subclasses
    public abstract double getArea();

    // Abstract method
    public abstract double getPerimeter();

    // Concrete method - has implementation, inherited as-is
    public String getColor() {
        return color;
    }

    public void displayInfo() {
        System.out.println("Shape: " + getClass().getSimpleName());
        System.out.println("Color: " + color);
        System.out.println("Area: " + String.format("%.2f", getArea()));
        System.out.println("Perimeter: " + String.format("%.2f", getPerimeter()));
    }
}
```

### Implementing an Abstract Class
```java
public class Circle extends Shape {
    private double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
}

public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }
}
```

```java
// Usage
// Shape shape = new Shape("Red");  // Compilation error - cannot instantiate abstract class
Shape circle = new Circle("Red", 5);
Shape rectangle = new Rectangle("Blue", 4, 6);

circle.displayInfo();
// Output:
// Shape: Circle
// Color: Red
// Area: 78.54
// Perimeter: 31.42
```

### Rules for Abstract Classes
- Cannot be instantiated with `new`
- Can have constructors (called by subclasses via `super()`)
- Can have both abstract and concrete methods
- Can have fields (instance variables)
- A subclass **must** implement all abstract methods, or itself be declared abstract
- Can have `static` methods

## 2. Interfaces

An **interface** is a contract that defines a set of abstract methods a class must implement. It specifies **what** a class must do, but not **how** it does it. Interfaces define capabilities that can be shared across unrelated classes.

### Declaring an Interface
```java
public interface Drawable {
    void draw();         // Abstract by default (public abstract)
    void resize(double factor);
}
```

### Implementing an Interface
A class uses the `implements` keyword to implement an interface and must provide implementations for all its methods.
```java
public class Circle implements Drawable {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a circle with radius " + radius);
    }

    @Override
    public void resize(double factor) {
        radius *= factor;
    }
}
```

### Multiple Interface Implementation
Unlike classes, a class can implement **multiple interfaces**.
```java
public interface Drawable {
    void draw();
}

public interface Resizable {
    void resize(double factor);
}

public interface Printable {
    void print();
}

// A class can implement all three
public class Circle implements Drawable, Resizable, Printable {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing circle");
    }

    @Override
    public void resize(double factor) {
        radius *= factor;
    }

    @Override
    public void print() {
        System.out.println("Circle with radius: " + radius);
    }
}
```

## 3. Interface Features (Java 8+)

### Default Methods
Interfaces can provide a default implementation using the `default` keyword. Classes that implement the interface can use the default implementation or override it.
```java
public interface Loggable {
    void performAction();

    // Default method with implementation
    default void log(String message) {
        System.out.println("[LOG] " + message);
    }
}

public class UserService implements Loggable {
    @Override
    public void performAction() {
        log("Performing user action"); // Uses default implementation
    }
}
```

### Static Methods
Interfaces can have static utility methods.
```java
public interface MathOperations {
    double calculate(double a, double b);

    static double square(double value) {
        return value * value;
    }
}

// Usage
double result = MathOperations.square(5); // 25.0
```

### Constants
All fields in an interface are implicitly `public static final`.
```java
public interface GameConstants {
    int MAX_PLAYERS = 4;       // public static final by default
    int MAX_LEVEL = 100;
    String DEFAULT_NAME = "Player";
}
```

## 4. Abstract Class vs Interface

| Feature              | Abstract Class                        | Interface                            |
|----------------------|---------------------------------------|--------------------------------------|
| Instantiation        | Cannot be instantiated                | Cannot be instantiated               |
| Methods              | Abstract and concrete                 | Abstract, default, and static        |
| Fields               | Any type (private, protected, etc.)   | Only `public static final` constants |
| Constructors         | Yes                                   | No                                   |
| Inheritance          | Single (extends one class)            | Multiple (implements many)           |
| Access modifiers     | Any                                   | Methods are `public` by default      |
| Use case             | Shared state and behavior among related classes | Defining capabilities across unrelated classes |

### When to Use Which

**Use an abstract class when:**
- Subclasses share common state (fields) and behavior
- You want to provide a partial implementation
- The classes have a clear "is-a" relationship

**Use an interface when:**
- You want to define a capability that unrelated classes can share
- You need multiple inheritance of type
- You want to define a contract without any state

## 5. Combining Abstract Classes and Interfaces

A class can extend one abstract class and implement multiple interfaces simultaneously.

```java
public abstract class Animal {
    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public abstract void makeSound();

    public String getName() {
        return name;
    }
}

public interface Swimmable {
    void swim();
}

public interface Trainable {
    void learnTrick(String trick);
}

// Dog extends Animal AND implements two interfaces
public class Dog extends Animal implements Swimmable, Trainable {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Woof!");
    }

    @Override
    public void swim() {
        System.out.println(getName() + " is swimming.");
    }

    @Override
    public void learnTrick(String trick) {
        System.out.println(getName() + " learned: " + trick);
    }
}
```

```java
// Usage - polymorphism with interfaces
Dog dog = new Dog("Rex");
dog.makeSound();               // Woof!
dog.swim();                    // Rex is swimming.
dog.learnTrick("Shake hands"); // Rex learned: Shake hands

// Can be referenced by any of its types
Animal animal = dog;
Swimmable swimmer = dog;
Trainable trainable = dog;
```

## 6. Interface as a Type

Interfaces can be used as reference types, enabling polymorphism across unrelated classes.

```java
public interface Payable {
    double getPaymentAmount();
}

public class Employee implements Payable {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }

    @Override
    public double getPaymentAmount() {
        return salary;
    }
}

public class Invoice implements Payable {
    private String description;
    private double amount;

    public Invoice(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public double getPaymentAmount() {
        return amount;
    }
}
```

```java
// Employee and Invoice are unrelated but both are Payable
Payable[] payables = {
    new Employee("Alice", 5000),
    new Invoice("Office Supplies", 250),
    new Employee("Bob", 6000),
    new Invoice("Server Hosting", 1200)
};

double total = 0;
for (Payable p : payables) {
    total += p.getPaymentAmount();
}
System.out.println("Total payments: " + total); // Output: Total payments: 12450.0
```

## 7. Complete Example

```java
// Abstract class for common vehicle state and behavior
public abstract class Vehicle {
    private String brand;
    private int year;

    public Vehicle(String brand, int year) {
        this.brand = brand;
        this.year = year;
    }

    // Abstract methods - subclasses must implement
    public abstract void start();
    public abstract void stop();
    public abstract double getFuelEfficiency();

    // Concrete method
    public String getBrand() {
        return brand;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return brand + " (" + year + ")";
    }
}
```

```java
// Interface for electric vehicle capability
public interface Electric {
    double getBatteryLevel();
    void charge(double hours);
}

// Interface for GPS capability
public interface GPSEnabled {
    void navigateTo(String destination);
}
```

```java
public class ElectricCar extends Vehicle implements Electric, GPSEnabled {
    private double batteryLevel;

    public ElectricCar(String brand, int year) {
        super(brand, year);
        this.batteryLevel = 100.0;
    }

    @Override
    public void start() {
        System.out.println(getBrand() + " silently starts.");
    }

    @Override
    public void stop() {
        System.out.println(getBrand() + " stops.");
    }

    @Override
    public double getFuelEfficiency() {
        return 0; // Electric cars don't use fuel
    }

    @Override
    public double getBatteryLevel() {
        return batteryLevel;
    }

    @Override
    public void charge(double hours) {
        batteryLevel = Math.min(100, batteryLevel + (hours * 20));
        System.out.println(getBrand() + " charged to " + batteryLevel + "%");
    }

    @Override
    public void navigateTo(String destination) {
        System.out.println(getBrand() + " navigating to " + destination);
    }
}
```

```java
public class GasCar extends Vehicle implements GPSEnabled {
    private double fuelLevel;

    public GasCar(String brand, int year) {
        super(brand, year);
        this.fuelLevel = 100.0;
    }

    @Override
    public void start() {
        System.out.println(getBrand() + " engine roars to life.");
    }

    @Override
    public void stop() {
        System.out.println(getBrand() + " engine shuts off.");
    }

    @Override
    public double getFuelEfficiency() {
        return 12.5; // km per liter
    }

    @Override
    public void navigateTo(String destination) {
        System.out.println(getBrand() + " navigating to " + destination);
    }
}
```

```java
// Usage
public class Main {
    public static void main(String[] args) {
        Vehicle[] vehicles = {
            new ElectricCar("Tesla", 2024),
            new GasCar("Toyota", 2023)
        };

        for (Vehicle v : vehicles) {
            v.start();
        }
        // Output:
        // Tesla silently starts.
        // Toyota engine roars to life.

        // Using interface type
        GPSEnabled[] gpsDevices = {
            new ElectricCar("Tesla", 2024),
            new GasCar("Toyota", 2023)
        };

        for (GPSEnabled gps : gpsDevices) {
            gps.navigateTo("Nairobi");
        }
    }
}
```

## 8. Key Takeaways

- Abstraction hides complexity and exposes only what is necessary
- **Abstract classes** provide partial implementation and shared state for related classes
- **Interfaces** define contracts (capabilities) that unrelated classes can implement
- A class can extend one abstract class but implement multiple interfaces
- Abstract methods have no body and must be overridden by concrete subclasses
- Interfaces can have default and static methods (Java 8+)
- Use abstract classes for "is-a" relationships with shared state
- Use interfaces to define capabilities that cut across class hierarchies

---

Abstraction ties together with the other three pillars. Encapsulation hides internal data, inheritance shares structure, polymorphism enables flexible behavior, and abstraction defines clean contracts that make all of this work together.