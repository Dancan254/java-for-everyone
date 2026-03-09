# Java for Everyone

## Overview

**Java for Everyone** is an open-source educational resource designed to guide learners through Java programming from the ground up. The repository is organized as a self-contained curriculum — each topic builds on the previous one, so you can work through it sequentially and develop a solid, layered understanding of the language.

The content covers core Java syntax, object-oriented design, the standard library, and modern Java features (Java 16+). Each topic folder contains a markdown guide, code samples, and practice exercises.

---

## Getting Started

### Prerequisites

- Java 17 or later (download from [adoptium.net](https://adoptium.net))
- An IDE such as [IntelliJ IDEA](https://www.jetbrains.com/idea/) (Community Edition is free)

### Clone the Repository

```bash
git clone https://github.com/Dancan254/java-for-everyone.git
```

### Open in IntelliJ IDEA

1. Launch IntelliJ IDEA.
2. Select **File > Open** and navigate to the cloned repository folder.
3. IntelliJ will detect the project structure automatically.

### Run Code Samples

- Navigate to any topic folder under `src/`.
- Open a `.java` file and click the green **Run** arrow next to the `main` method.

No build tool (Maven/Gradle) is required. All code samples are standalone.

---

## Curriculum

Work through the topics in the order listed below. Each entry links to the markdown guide for that topic.

| # | Topic | Guide | Key Concepts |
|---|-------|-------|--------------|
| 1 | Introduction | [GUIDE.MD](src/introduction/GUIDE.MD) | Data types, variables, operators, type casting, Scanner, Strings |
| 2 | Conditionals | [Conditionals.MD](src/conditionals/Conditionals.MD) | if/else, switch, ternary operator, boolean expressions |
| 3 | Loops | [Loops.md](src/loops/Loops.md) | for, while, do-while, break, continue, nested loops |
| 4 | Methods | [methods.md](src/methods/methods.md) | Method syntax, overloading, recursion, access modifiers, scope |
| 5 | Arrays | [Arrays.md](src/arrays/Arrays.md) | One-dimensional and multi-dimensional arrays, sorting, copying |
| 6 | Object-Oriented Programming | [oop.md](src/oop/oop.md) | Classes, objects, constructors, fields, static vs instance |
| 7 | Encapsulation | [encapsulation.md](src/oop/encapsulation.md) | Access modifiers, getters/setters, information hiding |
| 8 | Inheritance | [inheritance.md](src/oop/inheritance.md) | extends, super, method overriding, constructor chaining |
| 9 | Polymorphism | [polymorphism.md](src/oop/polymorphism.md) | Compile-time and runtime polymorphism, method overriding |
| 10 | Abstraction | [abstraction.md](src/oop/abstraction.md) | Abstract classes, design with abstraction |
| 11 | Interfaces | [interfaces.md](src/oop/interfaces.md) | Interface contracts, default methods, functional interfaces |
| 12 | Upcasting & Downcasting | [downcasting.md](src/oop/downcasting.md) | Type casting, instanceof, pattern matching (Java 16+) |
| 13 | Records | [records.md](src/oop/records.md) | Immutable data classes, compact constructors (Java 16+) |
| 14 | Exception Handling | [exceptions.md](src/exceptions/exceptions.md) | try/catch/finally, checked vs unchecked, custom exceptions |
| 15 | Enums | [enums.md](src/enums/enums.md) | Enum fields and methods, EnumSet, EnumMap, singleton pattern |
| 16 | Collections Framework | [collections.md](src/collections/collections.md) | List, Set, Map, Queue implementations and trade-offs |
| 17 | Generics | [generics.md](src/generics/generics.md) | Generic classes/methods, bounded types, wildcards, type erasure |
| 18 | Lambdas | [lambdas.md](src/lambdas/lambdas.md) | Lambda syntax, functional interfaces, method references |
| 19 | Streams | [streams.md](src/streams/streams.md) | Stream pipeline, intermediate/terminal operations, collectors |
| 20 | Optional | [optional.md](src/optional/optional.md) | Optional creation, retrieval, transformation, chaining |

---

## Practice Exercises

Each topic section includes exercises. In addition, the following files contain dedicated practice sets:

- **Methods exercises:** [src/methods/exercise.md](src/methods/exercise.md)
- **Enums & Collections exercises:** [src/collections/exercise.md](src/collections/exercise.md)

---

## Mini Projects

After completing the core curriculum, apply your skills through the guided mini projects described in [src/projects.md](src/projects.md). Projects range from a basic number guessing game to a fully functional contact book with file persistence.

---

## Repository Structure

```
java-for-everyone/
├── src/
│   ├── introduction/       GUIDE.MD, Introduction.java
│   ├── conditionals/       Conditionals.MD, Conditionals.java
│   ├── loops/              Loops.md, Loops.java
│   ├── methods/            methods.md, exercise.md, Methods.java
│   ├── arrays/             Arrays.md, ArraysClassDemo.java
│   ├── oop/                oop.md, encapsulation.md, inheritance.md,
│   │                       polymorphism.md, abstraction.md, interfaces.md,
│   │                       downcasting.md, records.md, *.java
│   ├── exceptions/         exceptions.md
│   ├── enums/              enums.md, *.java
│   ├── collections/        collections.md, exercise.md, Student.java, solutions/
│   ├── generics/           generics.md
│   ├── lambdas/            lambdas.md
│   ├── streams/            streams.md
│   ├── optional/           optional.md
│   ├── bank/               bank mini project files
│   └── projects.md         mini project descriptions
└── README.MD
```

---

## Contributing

Contributions are welcome. To contribute:

1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Commit your changes:
   ```bash
   git commit -m "Add: description of your change"
   ```
4. Push to your fork and open a pull request.

Areas where contributions are especially valuable:
- Additional code samples and worked examples
- Practice exercises with solutions
- Corrections or clarifications in the markdown guides

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Contact

- **LinkedIn:** [Ian Dancan](https://www.linkedin.com/in/ian-dancan/)
- **Twitter:** [@your\_javaguy](https://x.com/your_javaguy)
