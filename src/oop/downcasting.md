# Downcasting & instanceof / Pattern Matching

When you store a subclass object in a parent-type reference you lose direct access to the subclass's own methods. **Downcasting** is how you get that access back. **instanceof** is the safety check that keeps downcasting from blowing up at runtime.

```
Think of it like this:
  Animal animal = new Dog("Rex", 5, "Labrador"); // upcasting (implicit, always safe)
  // animal only "sees" Animal methods right now.

  Dog dog = (Dog) animal;  // downcasting (explicit, can fail)
  dog.bark();              // Now Dog-specific methods are available again.
```

---

## 1. Quick Recap — Upcasting vs Downcasting

| Direction       | What happens                              | Safety     | Syntax         |
|-----------------|-------------------------------------------|------------|----------------|
| **Upcasting**   | Subclass → Parent reference               | Always safe | Implicit       |
| **Downcasting** | Parent reference → Subclass reference     | Can fail   | Explicit cast  |

```java
// --- Upcasting (implicit) ---
Animal animal = new Dog("Rex", 5, "Labrador");
// animal.bark();  // Compilation error — Animal doesn't declare bark()

// --- Downcasting (explicit) ---
Dog dog = (Dog) animal;
dog.bark();         // Output: Rex says: Woof!
```

---

## 2. Why Downcasting Can Fail

The parent reference must actually point to an object of the target subclass. If it doesn't, Java throws a **ClassCastException** at runtime.

```java
Animal animal = new Animal("Generic", 1);

Dog dog = (Dog) animal;  // Compiles fine — but crashes at runtime!
// java.lang.ClassCastException: Animal cannot be cast to Dog
```

```java
// Another trap: the reference holds a Cat, not a Dog
Animal animal = new Cat("Whiskers", 3);

Dog dog = (Dog) animal;  // Runtime crash — Cat is not a Dog
```

The compiler cannot always catch this. You need to guard the cast yourself.

---

## 3. The `instanceof` Operator

`instanceof` returns `true` if an object is an instance of the given class (or any of its subclasses). Use it **before** downcasting to keep your code safe.

```java
Animal animal = new Dog("Rex", 5, "Labrador");

if (animal instanceof Dog) {
    Dog dog = (Dog) animal;   // Safe — we confirmed the type first
    dog.bark();               // Output: Rex says: Woof!
}
```

```java
// instanceof also returns true through the hierarchy
Animal animal = new Dog("Rex", 5, "Labrador");

System.out.println(animal instanceof Dog);    // true
System.out.println(animal instanceof Animal); // true  (Dog IS-A Animal)
System.out.println(animal instanceof Object); // true  (everything IS-A Object)
```

```java
// When the types don't match
Animal animal = new Animal("Generic", 1);

System.out.println(animal instanceof Dog); // false — no ClassCastException, just false
```

### Checking multiple subclasses

```java
public class AnimalClinic {
    public static void treat(Animal animal) {
        if (animal instanceof Dog) {
            Dog dog = (Dog) animal;
            System.out.println("Treating dog: " + dog.getBreed());
            dog.bark();
        } else if (animal instanceof Cat) {
            Cat cat = (Cat) animal;
            System.out.println("Treating cat.");
            cat.purr();
        } else {
            System.out.println("Treating generic animal: " + animal.getName());
        }
    }
}
```

```java
AnimalClinic.treat(new Dog("Rex", 5, "Labrador"));
// Output:
// Treating dog: Labrador
// Rex says: Woof!

AnimalClinic.treat(new Animal("Generic", 2));
// Output:
// Treating generic animal: Generic
```

---

## 4. Pattern Matching for `instanceof` (Java 16+)

Java 16 introduced **pattern matching** for `instanceof`. It combines the type check and the cast into one step, eliminating the redundant explicit cast.

### Old style (before Java 16)
```java
if (animal instanceof Dog) {
    Dog dog = (Dog) animal;  // check AND cast — two lines
    dog.bark();
}
```

### New style (Java 16+)
```java
if (animal instanceof Dog dog) {  // check AND bind — one line
    dog.bark();                   // dog is already usable here
}
```

The variable `dog` is called a **pattern variable**. It is only in scope inside the `if` block where the condition is `true`.

### Pattern matching with a condition

You can add extra conditions on the same line with `&&`:

```java
if (animal instanceof Dog dog && dog.getBreed().equals("Labrador")) {
    System.out.println(dog.getName() + " is a Labrador!");
}
```

### Pattern matching in a multi-branch method

```java
public static void describe(Animal animal) {
    if (animal instanceof Dog dog) {
        System.out.println(dog.getName() + " is a " + dog.getBreed() + " dog.");
        dog.bark();
    } else if (animal instanceof Cat cat) {
        System.out.println(cat.getName() + " is a cat.");
        cat.purr();
    } else {
        System.out.println(animal.getName() + " is some animal.");
    }
}
```

```java
describe(new Dog("Rex", 5, "Labrador"));
// Output:
// Rex is a Labrador dog.
// Rex says: Woof!

describe(new Cat("Whiskers", 3));
// Output:
// Whiskers is a cat.
```

---

## 5. Pattern Matching with `switch` (Java 21+)

Java 21 added pattern matching directly inside `switch` expressions, making multi-type dispatch even cleaner.

```java
public static String getSound(Animal animal) {
    return switch (animal) {
        case Dog dog   -> dog.getName() + " says: Woof!";
        case Cat cat   -> cat.getName() + " says: Meow!";
        case Animal a  -> a.getName() + " makes a generic sound.";
    };
}
```

```java
System.out.println(getSound(new Dog("Rex", 5, "Labrador")));
// Output: Rex says: Woof!

System.out.println(getSound(new Cat("Whiskers", 3)));
// Output: Whiskers says: Meow!
```

Adding a guard (`when`) works the same way:

```java
return switch (animal) {
    case Dog dog when dog.getBreed().equals("Labrador") ->
            dog.getName() + " is a friendly Labrador!";
    case Dog dog ->
            dog.getName() + " is a dog.";
    default ->
            animal.getName() + " is something else.";
};
```

---

## 6. Complete Example

```java
public class Animal {
    private String name;
    private int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge()     { return age; }

    public void makeSound() {
        System.out.println(name + " makes a sound.");
    }
}
```

```java
public class Dog extends Animal {
    private String breed;

    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }

    public String getBreed() { return breed; }

    public void bark() {
        System.out.println(getName() + " says: Woof!");
    }
}
```

```java
public class Cat extends Animal {
    private boolean isIndoor;

    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }

    public boolean isIndoor() { return isIndoor; }

    public void purr() {
        System.out.println(getName() + " purrs.");
    }
}
```

```java
public class Main {
    // Classic instanceof guard (works in all Java versions)
    public static void handleClassic(Animal animal) {
        if (animal instanceof Dog) {
            Dog dog = (Dog) animal;
            System.out.println("[classic] Dog breed: " + dog.getBreed());
            dog.bark();
        } else if (animal instanceof Cat) {
            Cat cat = (Cat) animal;
            System.out.println("[classic] Indoor cat: " + cat.isIndoor());
            cat.purr();
        }
    }

    // Pattern matching (Java 16+)
    public static void handleModern(Animal animal) {
        if (animal instanceof Dog dog) {
            System.out.println("[modern] Dog breed: " + dog.getBreed());
            dog.bark();
        } else if (animal instanceof Cat cat) {
            System.out.println("[modern] Indoor cat: " + cat.isIndoor());
            cat.purr();
        }
    }

    public static void main(String[] args) {
        Animal[] animals = {
            new Dog("Rex", 5, "Labrador"),
            new Cat("Whiskers", 3, true),
            new Animal("Unknown", 1)
        };

        for (Animal a : animals) {
            handleClassic(a);
        }
        // Output:
        // [classic] Dog breed: Labrador
        // Rex says: Woof!
        // [classic] Indoor cat: true
        // Whiskers purrs.

        System.out.println("---");

        for (Animal a : animals) {
            handleModern(a);
        }
        // Output:
        // [modern] Dog breed: Labrador
        // Rex says: Woof!
        // [modern] Indoor cat: true
        // Whiskers purrs.
    }
}
```

---

## 7. Common Mistakes

### Casting without checking
```java
Animal animal = new Cat("Whiskers", 3, true);
Dog dog = (Dog) animal;  // Compiles, but crashes at runtime
dog.bark();
```
Always use `instanceof` before downcasting when the type is not guaranteed.

### Checking the wrong type
```java
if (animal instanceof Animal) {   // This is ALWAYS true for any Animal or subclass
    Dog dog = (Dog) animal;       // Still crashes if animal is actually a Cat
    dog.bark();
}
```
Check for the **specific subclass** you need, not just the parent.

### Forgetting that `instanceof` is false for `null`
```java
Animal animal = null;
System.out.println(animal instanceof Dog); // false — no NullPointerException
Dog dog = (Dog) animal;                    // NullPointerException at runtime!
```
`instanceof` safely returns `false` for `null`, but casting `null` to any type still throws a `NullPointerException` when you call a method on it.

---

## 8. Key Takeaways

- **Upcasting** (subclass → parent reference) is implicit and always safe
- **Downcasting** (parent reference → subclass reference) requires an explicit cast and can throw `ClassCastException`
- Always use `instanceof` to verify the type before downcasting
- **Pattern matching** (`instanceof Dog dog`) combines the check and the cast in one step — available since Java 16
- Pattern matching with `switch` (Java 21+) makes multi-type dispatch clean and concise
- `instanceof` returns `false` for `null` rather than throwing an exception

---

Downcasting is the tool you reach for when you need to access subclass-specific behavior through a parent-type reference. Used together with `instanceof` — or better yet, with pattern matching — it is both safe and expressive.
