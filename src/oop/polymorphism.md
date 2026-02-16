# Polymorphism

Polymorphism means **"many forms."** It is the ability of a single interface or method call to behave differently depending on the actual type of the object it is operating on. In Java, polymorphism allows you to write code that works with a parent type but automatically executes the correct behavior for each subclass.

```
Think of it like this:
  You call makeSound() on an Animal reference.
  If the actual object is a Dog  -> "Woof!"
  If the actual object is a Cat  -> "Meow!"
  If the actual object is a Bird -> "Tweet!"
  Same method call, different behavior.
```

## 1. Types of Polymorphism

Java supports two types of polymorphism:

| Type                | Also Known As            | Resolved At    | Mechanism          |
|---------------------|--------------------------|----------------|--------------------|
| Compile-time        | Static polymorphism      | Compilation    | Method overloading |
| Runtime             | Dynamic polymorphism     | Execution      | Method overriding  |

## 2. Compile-Time Polymorphism (Method Overloading)

Method overloading allows multiple methods in the same class to share a name but differ in their parameter lists. The compiler determines which method to call based on the arguments provided.

```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }

    public String add(String a, String b) {
        return a + b; // String concatenation
    }
}
```

```java
Calculator calc = new Calculator();
System.out.println(calc.add(2, 3));           // Calls add(int, int) -> 5
System.out.println(calc.add(2.5, 3.5));       // Calls add(double, double) -> 6.0
System.out.println(calc.add(1, 2, 3));        // Calls add(int, int, int) -> 6
System.out.println(calc.add("Hello", " World")); // Calls add(String, String) -> Hello World
```

**Rules for Overloading:**
- Methods must have the same name
- Methods must differ in parameter type, number, or order
- Return type alone is not sufficient to distinguish overloaded methods

## 3. Runtime Polymorphism (Method Overriding)

Runtime polymorphism occurs when a subclass overrides a method from its parent class. The JVM determines which version to execute based on the **actual object type** at runtime, not the reference type.

```java
public class Animal {
    public void makeSound() {
        System.out.println("Some generic sound");
    }

    public void describe() {
        System.out.println("I am an animal.");
    }
}

public class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Woof! Woof!");
    }

    @Override
    public void describe() {
        System.out.println("I am a dog.");
    }
}

public class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }

    @Override
    public void describe() {
        System.out.println("I am a cat.");
    }
}
```

```java
// The reference type is Animal, but the actual objects are Dog and Cat
Animal myDog = new Dog();
Animal myCat = new Cat();

myDog.makeSound(); // Output: Woof! Woof!   (Dog's version)
myCat.makeSound(); // Output: Meow!          (Cat's version)
```

This is the essence of runtime polymorphism: the method that runs depends on the **object**, not the **variable type**.

## 4. Upcasting and Downcasting

### Upcasting
Assigning a subclass object to a parent class reference. This is done **implicitly** and is always safe.
```java
Animal animal = new Dog("Rex", 5, "Labrador"); // Upcasting (implicit)
animal.makeSound(); // Calls Dog's makeSound()
// animal.bark();   // Compilation error - Animal reference cannot see Dog-specific methods
```

After upcasting, you can only call methods defined in the parent class. However, if the method is overridden, the subclass version executes.

### Downcasting
Casting a parent class reference back to a subclass type. This must be done **explicitly** and can fail at runtime.
```java
Animal animal = new Dog("Rex", 5, "Labrador");

// Explicit downcast
Dog dog = (Dog) animal;
dog.bark(); // Now we can access Dog-specific methods

// Dangerous downcast - will throw ClassCastException at runtime
// Cat cat = (Cat) animal; // Runtime error: Dog cannot be cast to Cat
```

### Safe Downcasting with `instanceof`
Always check the type before downcasting.
```java
Animal animal = new Dog("Rex", 5, "Labrador");

if (animal instanceof Dog) {
    Dog dog = (Dog) animal;
    dog.bark(); // Safe
}

if (animal instanceof Cat) {
    Cat cat = (Cat) animal; // This block will not execute
    cat.purr();
}
```

### Pattern Matching for `instanceof` (Java 16+)
```java
if (animal instanceof Dog dog) {
    dog.bark(); // dog is already cast, no explicit cast needed
}
```

## 5. Polymorphism with Arrays and Collections

One of the most powerful uses of polymorphism is storing different subclass objects in a single array or collection of the parent type.

```java
Animal[] animals = new Animal[3];
animals[0] = new Dog("Rex", 5, "Labrador");
animals[1] = new Cat("Whiskers", 3, true);
animals[2] = new Bird("Tweety", 1, true);

// Same method call, different behavior for each object
for (Animal animal : animals) {
    animal.makeSound();
}
// Output:
// Woof! Woof!
// Meow!
// Tweet! Tweet!
```

## 6. Polymorphism with Method Parameters

Methods can accept a parent type as a parameter and work with any subclass.

```java
public class Veterinarian {
    // Accepts any Animal - Dog, Cat, Bird, etc.
    public void examine(Animal animal) {
        System.out.println("Examining: " + animal.getName());
        animal.makeSound();
    }
}
```

```java
Veterinarian vet = new Veterinarian();
vet.examine(new Dog("Rex", 5, "Labrador"));   // Works with Dog
vet.examine(new Cat("Whiskers", 3, true));     // Works with Cat
vet.examine(new Bird("Tweety", 1, true));      // Works with Bird
```

This means you can write methods that work with **any current or future subclass** of Animal without modifying the method.

## 7. Polymorphism with Return Types

Methods can return a parent type while actually returning subclass instances.

```java
public class AnimalFactory {
    public static Animal createAnimal(String type) {
        if (type.equals("dog")) {
            return new Dog("Default Dog", 1, "Mixed");
        } else if (type.equals("cat")) {
            return new Cat("Default Cat", 1, false);
        } else {
            return new Animal("Unknown", 0);
        }
    }
}
```

```java
Animal pet = AnimalFactory.createAnimal("dog");
pet.makeSound(); // Output: Woof! Woof!
```

## 8. The `@Override` Annotation

The `@Override` annotation tells the compiler that a method is intended to override a parent method. It is not required, but strongly recommended.

```java
public class Dog extends Animal {
    @Override
    public void makeSound() {     // Compiler verifies this overrides a parent method
        System.out.println("Woof!");
    }

    // @Override
    // public void makeSond() { }  // Compiler error - typo caught because of @Override
}
```

Without `@Override`, a typo in the method name would silently create a new method instead of overriding the intended one.

## 9. Complete Example

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

    public void displayInfo() {
        System.out.println("Shape: " + getClass().getSimpleName()
                + ", Color: " + color
                + ", Area: " + String.format("%.2f", getArea()));
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
}
```

```java
public class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(String color, double base, double height) {
        super(color);
        this.base = base;
        this.height = height;
    }

    @Override
    public double getArea() {
        return 0.5 * base * height;
    }
}
```

```java
// Polymorphism in action
public class Main {
    public static void printAllAreas(Shape[] shapes) {
        for (Shape shape : shapes) {
            shape.displayInfo(); // Each shape calculates its own area
        }
    }

    public static double totalArea(Shape[] shapes) {
        double total = 0;
        for (Shape shape : shapes) {
            total += shape.getArea();
        }
        return total;
    }

    public static void main(String[] args) {
        Shape[] shapes = {
            new Circle("Red", 5),
            new Rectangle("Blue", 4, 6),
            new Triangle("Green", 3, 8)
        };

        printAllAreas(shapes);
        // Output:
        // Shape: Circle, Color: Red, Area: 78.54
        // Shape: Rectangle, Color: Blue, Area: 24.00
        // Shape: Triangle, Color: Green, Area: 12.00

        System.out.println("Total area: " + String.format("%.2f", totalArea(shapes)));
        // Output: Total area: 114.54
    }
}
```

## 10. Key Takeaways

- Polymorphism allows one interface to serve multiple underlying forms
- **Compile-time polymorphism** (overloading) is resolved by the compiler based on method signatures
- **Runtime polymorphism** (overriding) is resolved by the JVM based on the actual object type
- Upcasting is implicit and safe; downcasting requires an explicit cast and should use `instanceof`
- Polymorphism enables writing flexible code that works with parent types and automatically handles any subclass
- Use `@Override` to catch errors and communicate intent
- Arrays and collections of a parent type can hold any mix of subclass objects

---

Polymorphism is what makes OOP truly powerful. It allows you to write extensible code that handles new subclasses without modification, which is the foundation of the Open/Closed Principle in software design.