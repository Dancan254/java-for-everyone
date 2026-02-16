# Introduction to Object-Oriented Programming (OOP)

Object-Oriented Programming is a programming paradigm that organizes software design around **objects** rather than functions and logic. An object is a self-contained unit that combines **data** (attributes) and **behavior** (methods) into a single entity. Java is a class-based, object-oriented language, meaning everything in Java revolves around classes and objects.

## 1. Classes and Objects

A **class** is a blueprint or template that defines the structure and behavior of objects. An **object** is a concrete instance of a class that exists in memory at runtime.

```
Think of it like this:
  Class  = Blueprint of a house
  Object = The actual house built from that blueprint
```

### Defining a Class
```java
public class Car {
    // Attributes (fields/instance variables)
    private String brand;
    private String model;
    private int year;

    // Constructor
    public Car(String brand, String model, int year) {
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    // Methods (behavior)
    public void start() {
        System.out.println(brand + " " + model + " is starting.");
    }

    public void stop() {
        System.out.println(brand + " " + model + " has stopped.");
    }
}
```

### Creating Objects
```java
public class Main {
    public static void main(String[] args) {
        // Creating objects from the Car class
        Car car1 = new Car("Toyota", "Corolla", 2020);
        Car car2 = new Car("Honda", "Civic", 2022);

        // Calling methods on objects
        car1.start();  // Output: Toyota Corolla is starting.
        car2.start();  // Output: Honda Civic is starting.
    }
}
```

## 2. Anatomy of a Class

A Java class typically consists of the following components:

```java
public class Student {
    // 1. Fields (instance variables) - the data
    private String name;
    private int age;
    private double gpa;

    // 2. Constructors - initialize the object
    public Student(String name, int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }

    // 3. Methods - the behavior
    public void study(String subject) {
        System.out.println(name + " is studying " + subject);
    }

    // 4. Getters and Setters - controlled access to fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 5. toString() - string representation of the object
    @Override
    public String toString() {
        return "Student{name='" + name + "', age=" + age + ", gpa=" + gpa + "}";
    }
}
```

## 3. Constructors

A **constructor** is a special method that is called when an object is created. It has the same name as the class and no return type.

### Default Constructor
If you do not define any constructor, Java provides a **default constructor** that initializes fields to their default values (0, null, false, etc.).
```java
public class Animal {
    private String name;
    private String color;

    // No-argument constructor
    public Animal() {
        name = "Unknown";
        color = "Unknown";
    }
}

// Usage
Animal a = new Animal();
System.out.println(a.getName()); // Output: Unknown
```

### Parameterized Constructor
Accepts arguments to initialize fields with specific values.
```java
public class Animal {
    private String name;
    private String color;

    public Animal(String name, String color) {
        this.name = name;
        this.color = color;
    }
}

// Usage
Animal dog = new Animal("Rex", "Brown");
```

### Constructor Overloading
A class can have multiple constructors with different parameter lists.
```java
public class Animal {
    private String name;
    private String color;

    public Animal() {
        this.name = "Unknown";
        this.color = "Unknown";
    }

    public Animal(String name) {
        this.name = name;
        this.color = "Unknown";
    }

    public Animal(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
```

### Constructor Chaining with `this()`
One constructor can call another constructor in the same class using `this()`.
```java
public class Animal {
    private String name;
    private String color;

    public Animal() {
        this("Unknown", "Unknown"); // Calls the two-argument constructor
    }

    public Animal(String name) {
        this(name, "Unknown"); // Calls the two-argument constructor
    }

    public Animal(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
```

## 4. The `this` Keyword

The `this` keyword refers to the current object instance. It is used to:

- **Distinguish** instance variables from parameters with the same name
- **Call** another constructor in the same class
- **Pass** the current object as an argument to a method

```java
public class Person {
    private String name;

    public Person(String name) {
        this.name = name; // this.name = instance variable, name = parameter
    }

    public void introduce() {
        System.out.println("Hi, I am " + this.name);
    }
}
```

## 5. Getters and Setters

**Getters** retrieve the value of a private field. **Setters** modify the value of a private field. Together they provide controlled access to an object's data.

```java
public class BankAccount {
    private String owner;
    private double balance;

    public BankAccount(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    // Getter
    public double getBalance() {
        return balance;
    }

    // Setter with validation
    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            System.out.println("Balance cannot be negative.");
        }
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
```

## 6. The `toString()` Method

Every class in Java inherits from `Object`, which provides a `toString()` method. By default, it returns the class name and memory address. Override it to provide a meaningful string representation.

```java
public class Book {
    private String title;
    private String author;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "'}";
    }
}

// Usage
Book book = new Book("Clean Code", "Robert Martin");
System.out.println(book); // Output: Book{title='Clean Code', author='Robert Martin'}
// Without override: Book@1b6d3586
```

## 7. Access Modifiers

Access modifiers control the visibility of classes, fields, and methods.

| Modifier    | Same Class | Same Package | Subclass | Everywhere |
|-------------|------------|--------------|----------|------------|
| `public`    | Yes        | Yes          | Yes      | Yes        |
| `protected` | Yes        | Yes          | Yes      | No         |
| default     | Yes        | Yes          | No       | No         |
| `private`   | Yes        | No           | No       | No         |

**Rule of thumb**: Make fields `private` and provide `public` getters and setters. This is the foundation of encapsulation.

## 8. Static vs Instance Members

### Instance Members
Belong to individual objects. Each object has its own copy.
```java
public class Student {
    private String name; // Each student has their own name

    public void setName(String name) {
        this.name = name;
    }
}
```

### Static Members
Belong to the class itself. Shared across all objects.
```java
public class Student {
    private String name;
    private static int studentCount = 0; // Shared across all Student objects

    public Student(String name) {
        this.name = name;
        studentCount++; // Incremented each time a Student is created
    }

    public static int getStudentCount() {
        return studentCount;
    }
}

// Usage
Student s1 = new Student("Alice");
Student s2 = new Student("Bob");
System.out.println(Student.getStudentCount()); // Output: 2
```

## 9. The Four Pillars of OOP

Object-Oriented Programming is built on four fundamental principles:

1. **Encapsulation** - Bundling data and methods together, restricting direct access to internal state
2. **Inheritance** - Creating new classes based on existing classes, inheriting their fields and methods
3. **Polymorphism** - The ability of objects to take on many forms, responding differently to the same method call
4. **Abstraction** - Hiding complex implementation details and exposing only the essential features

Each of these pillars is covered in detail in its own dedicated document.

## 10. Best Practices

- Use meaningful class names that represent real-world entities (nouns)
- Use meaningful method names that describe actions (verbs)
- Keep fields `private` and provide access through getters and setters
- Override `toString()` for every class to aid debugging
- Follow the Single Responsibility Principle: a class should have one reason to change
- Use constructor overloading to provide flexibility in object creation
- Prefer composition over inheritance when the relationship is "has-a" rather than "is-a"

---

This guide covers the foundational concepts of OOP in Java. Each of the four pillars is explored in depth in its own separate document.