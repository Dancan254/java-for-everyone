# Inheritance

Inheritance is a mechanism that allows a new class (**subclass/child class**) to inherit the fields and methods of an existing class (**superclass/parent class**). It establishes an **"is-a" relationship** between classes and promotes code reuse.

```
Think of it like this:
  Animal (parent)
    ├── Dog (child) - is an Animal
    ├── Cat (child) - is an Animal
    └── Bird (child) - is an Animal
```

A Dog **is an** Animal. It has everything an Animal has, plus its own specific characteristics.

## 1. Basic Inheritance Syntax

In Java, inheritance is achieved using the `extends` keyword.

```java
// Parent class (superclass)
public class Animal {
    private String name;
    private int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void eat() {
        System.out.println(name + " is eating.");
    }

    public void sleep() {
        System.out.println(name + " is sleeping.");
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
```

```java
// Child class (subclass)
public class Dog extends Animal {
    private String breed;

    public Dog(String name, int age, String breed) {
        super(name, age); // Calls the parent constructor
        this.breed = breed;
    }

    // Dog-specific method
    public void bark() {
        System.out.println(getName() + " says: Woof!");
    }

    public String getBreed() {
        return breed;
    }
}
```

```java
// Usage
Dog dog = new Dog("Rex", 5, "German Shepherd");
dog.eat();   // Inherited from Animal
dog.sleep(); // Inherited from Animal
dog.bark();  // Defined in Dog
```

## 2. The `super` Keyword

The `super` keyword refers to the parent class. It is used to:

### Call the Parent Constructor
The first statement in a child constructor must be `super()` if you want to call the parent constructor explicitly.
```java
public class Cat extends Animal {
    private boolean isIndoor;

    public Cat(String name, int age, boolean isIndoor) {
        super(name, age); // Must be the first statement
        this.isIndoor = isIndoor;
    }
}
```

### Call a Parent Method
When you override a method but still want to use the parent's version.
```java
public class Dog extends Animal {
    private String breed;

    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }

    @Override
    public void eat() {
        super.eat(); // Call the parent's eat() method first
        System.out.println(getName() + " wags tail while eating.");
    }
}
```

## 3. What Is Inherited

| Inherited                          | Not Inherited              |
|------------------------------------|----------------------------|
| `public` methods                   | `private` fields (exist but not directly accessible) |
| `protected` methods                | `private` methods          |
| `public` fields (if any)           | Constructors               |
| `protected` fields                 |                            |

**Important**: Private fields are not accessible directly in the subclass, but they still exist in the object. Access them through inherited getters and setters.

```java
public class Animal {
    private String name; // Not directly accessible in Dog

    public String getName() { // This IS accessible in Dog
        return name;
    }
}

public class Dog extends Animal {
    public void printName() {
        // System.out.println(name);      // Compilation error - private
        System.out.println(getName());     // Works - using inherited public getter
    }
}
```

## 4. Method Overriding

A subclass can provide its own implementation of a method defined in the parent class. This is called **method overriding**.

**Rules for Overriding:**
- Method must have the same name, return type, and parameters
- Access modifier cannot be more restrictive than the parent
- Use the `@Override` annotation to signal intent (recommended)
- Cannot override `final` or `static` methods

```java
public class Animal {
    public void makeSound() {
        System.out.println("Some generic sound");
    }
}

public class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Woof! Woof!");
    }
}

public class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
}
```

```java
// Usage
Animal animal = new Animal();
Dog dog = new Dog();
Cat cat = new Cat();

animal.makeSound(); // Output: Some generic sound
dog.makeSound();    // Output: Woof! Woof!
cat.makeSound();    // Output: Meow!
```

## 5. The `protected` Access Modifier

The `protected` modifier is designed for inheritance. A `protected` member is accessible within the same package and by subclasses in any package.

```java
public class Vehicle {
    protected int speed;        // Accessible in subclasses
    private String engineType;  // Not accessible in subclasses

    public Vehicle(int speed, String engineType) {
        this.speed = speed;
        this.engineType = engineType;
    }

    protected void accelerate(int amount) {
        speed += amount;
    }
}

public class Car extends Vehicle {
    public Car(int speed, String engineType) {
        super(speed, engineType);
    }

    public void boost() {
        accelerate(50);                      // Can call protected method
        System.out.println("Speed: " + speed); // Can access protected field
    }
}
```

## 6. Constructor Chaining in Inheritance

When an object of a subclass is created, constructors execute from the top of the hierarchy down.

```java
public class Animal {
    public Animal() {
        System.out.println("Animal constructor");
    }
}

public class Dog extends Animal {
    public Dog() {
        super(); // Automatically inserted by Java if omitted
        System.out.println("Dog constructor");
    }
}

public class Puppy extends Dog {
    public Puppy() {
        super();
        System.out.println("Puppy constructor");
    }
}

// Creating a Puppy
Puppy p = new Puppy();
// Output:
// Animal constructor
// Dog constructor
// Puppy constructor
```

If the parent class has no no-argument constructor, the child class **must** explicitly call a parent constructor with `super(args)`.

## 7. The `final` Keyword in Inheritance

### Final Class
A `final` class cannot be extended.
```java
public final class MathConstants {
    public static final double PI = 3.14159;
    // No class can extend MathConstants
}
```

### Final Method
A `final` method cannot be overridden by subclasses.
```java
public class Account {
    public final void closeAccount() {
        // This implementation cannot be changed by subclasses
        System.out.println("Account closed.");
    }
}
```

## 8. The `Object` Class

Every class in Java implicitly extends `java.lang.Object`. This means every object has access to the methods defined in `Object`:

- `toString()` - String representation of the object
- `equals(Object obj)` - Checks equality
- `hashCode()` - Returns a hash code value
- `getClass()` - Returns the runtime class

```java
public class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return name.equals(person.name);
    }
}
```

## 9. Single Inheritance in Java

Java supports **single inheritance** only. A class can extend exactly one parent class.

```java
// Valid
public class Dog extends Animal { }

// Invalid - Java does not allow multiple inheritance of classes
// public class Dog extends Animal, Pet { }  // Compilation error
```

To achieve similar behavior, Java provides **interfaces**, which a class can implement multiple of.

## 10. Multilevel Inheritance

A class can inherit from a class that itself inherits from another class, forming a chain.

```java
public class Animal {
    public void breathe() {
        System.out.println("Breathing...");
    }
}

public class Mammal extends Animal {
    public void feedMilk() {
        System.out.println("Feeding milk...");
    }
}

public class Dog extends Mammal {
    public void bark() {
        System.out.println("Barking...");
    }
}

// Dog inherits from Mammal AND Animal
Dog dog = new Dog();
dog.breathe();   // From Animal
dog.feedMilk();  // From Mammal
dog.bark();      // From Dog
```

## 11. Complete Example

```java
public class Shape {
    private String color;

    public Shape(String color) {
        this.color = color;
    }

    public double getArea() {
        return 0;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Shape{color='" + color + "', area=" + getArea() + "}";
    }
}
```

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
    public String toString() {
        return "Circle{color='" + getColor() + "', radius=" + radius + ", area=" + getArea() + "}";
    }
}
```

```java
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
    public String toString() {
        return "Rectangle{color='" + getColor() + "', width=" + width
                + ", height=" + height + ", area=" + getArea() + "}";
    }
}
```

```java
// Usage
Shape circle = new Circle("Red", 5.0);
Shape rectangle = new Rectangle("Blue", 4.0, 6.0);

System.out.println(circle);
// Output: Circle{color='Red', radius=5.0, area=78.53981633974483}
System.out.println(rectangle);
// Output: Rectangle{color='Blue', width=4.0, height=6.0, area=24.0}
```

## 12. When to Use Inheritance

**Use inheritance when:**
- There is a clear "is-a" relationship (Dog is an Animal)
- Subclasses genuinely share behavior and state with the parent
- You want to leverage polymorphism

**Avoid inheritance when:**
- The relationship is "has-a" (a Car has an Engine, not a Car is an Engine)
- You only need to reuse a few methods (prefer composition)
- The parent class was not designed for extension

## 13. Key Takeaways

- Inheritance establishes an "is-a" relationship between classes
- Use `extends` to inherit from a parent class
- Use `super` to call parent constructors and methods
- Private fields are inherited but not directly accessible
- Use `@Override` annotation when overriding methods
- Java supports single inheritance only (one parent class)
- Every class implicitly extends `Object`
- Use `final` to prevent a class from being extended or a method from being overridden
- Prefer composition over inheritance when the relationship is not genuinely "is-a"

---

Inheritance works hand-in-hand with polymorphism. Once you understand how subclasses extend parent classes, the next step is understanding how Java decides which version of a method to call at runtime.