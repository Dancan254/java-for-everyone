# Java for Everyone

## Overview

**Java for Everyone** is an open-source educational resource designed to guide learners through Java programming from the ground up. The repository is organized as a self-contained curriculum — each topic builds on the previous one, so you can work through it sequentially and develop a solid, layered understanding of the language.

The content covers core Java syntax, object-oriented design, the standard library, and modern Java features (Java 16+). Each topic folder contains a markdown guide, code samples, and practice exercises.

---

## Getting Started

### Prerequisites

- Java 21 or later (download from [Oracle](https://www.oracle.com/java/technologies/downloads/))
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

No build tool (Maven/Gradle) is required for running the code samples. All `.java` files are standalone.

---

## Curriculum

Work through the topics in the order listed below. Each entry links to the markdown guide for that topic.

### Core Language

| # | Topic | Guide | Key Concepts |
|---|-------|-------|--------------|
| 1 | Introduction | [GUIDE.MD](src/introduction/GUIDE.MD) | Data types, variables, operators, type casting, Scanner, Strings |
| 2 | Conditionals | [Conditionals.MD](src/conditionals/Conditionals.MD) | if/else, switch, ternary operator, boolean expressions |
| 3 | Loops | [Loops.md](src/loops/Loops.md) | for, while, do-while, break, continue, nested loops |
| 4 | Methods | [methods.md](src/methods/methods.md) | Method syntax, overloading, recursion, access modifiers, scope |
| 5 | Arrays | [Arrays.md](src/arrays/Arrays.md) | One-dimensional and multi-dimensional arrays, sorting, copying |

### Object-Oriented Programming

| # | Topic | Guide | Key Concepts |
|---|-------|-------|--------------|
| 6 | OOP Fundamentals | [oop.md](src/oop/oop.md) | Classes, objects, constructors, fields, static vs instance |
| 7 | Encapsulation | [encapsulation.md](src/oop/encapsulation.md) | Access modifiers, getters/setters, information hiding |
| 8 | Inheritance | [inheritance.md](src/oop/inheritance.md) | extends, super, method overriding, constructor chaining |
| 9 | Polymorphism | [polymorphism.md](src/oop/polymorphism.md) | Compile-time and runtime polymorphism, method overriding |
| 10 | Abstraction | [abstraction.md](src/oop/abstraction.md) | Abstract classes, design with abstraction |
| 11 | Interfaces | [interfaces.md](src/oop/interfaces.md) | Interface contracts, default methods, functional interfaces |
| 12 | Upcasting & Downcasting | [downcasting.md](src/oop/downcasting.md) | Type casting, instanceof, pattern matching (Java 16+) |
| 13 | Records | [records.md](src/oop/records.md) | Immutable data classes, compact constructors (Java 16+) |
| 14 | Sealed Classes | [sealed.md](src/oop/sealed.md) | Sealed hierarchies, pattern matching in switch (Java 21) |

### OOP Deep Dives

| Topic | Guide | Key Concepts |
|-------|-------|--------------|
| equals() and hashCode() | [equals-hashcode.md](src/oop/equals-hashcode.md) | Contract, Objects.hash(), HashMap correctness |
| toString() | [tostring.md](src/oop/tostring.md) | Override guidelines, String.format, circular reference danger |
| Immutability | [immutability.md](src/oop/immutability.md) | Defensive copying, List.copyOf(), Records as value types |

### Standard Library and Modern Java

| # | Topic | Guide | Key Concepts |
|---|-------|-------|--------------|
| 15 | Exception Handling | [exceptions.md](src/exceptions/exceptions.md) | try/catch/finally, checked vs unchecked, custom exceptions |
| 16 | Enums | [enums.md](src/enums/enums.md) | Enum fields and methods, EnumSet, EnumMap, singleton pattern |
| 17 | Collections Framework | [collections.md](src/collections/collections.md) | List, Set, Map, Queue implementations and trade-offs |
| 18 | Comparator and Comparable | [comparator.md](src/collections/comparator.md) | Natural ordering, Comparator factory methods, TreeSet/TreeMap |
| 19 | Iterators | [iterators.md](src/collections/iterators.md) | Iterator protocol, Iterable, safe removal, custom iterables |
| 20 | Generics | [generics.md](src/generics/generics.md) | Generic classes/methods, bounded types, wildcards, type erasure |
| 21 | Lambdas | [lambdas.md](src/lambdas/lambdas.md) | Lambda syntax, functional interfaces, method references |
| 22 | Streams | [streams.md](src/streams/streams.md) | Stream pipeline, intermediate/terminal operations, collectors |
| 23 | Optional | [optional.md](src/optional/optional.md) | Optional creation, retrieval, transformation, chaining |
| 24 | Date and Time | [datetime.md](src/datetime/datetime.md) | LocalDate/Time/DateTime, DateTimeFormatter, Period, Duration, Instant |
| 25 | I/O and File Handling | [io.md](src/io/io.md) | Path, Files, BufferedReader/Writer, try-with-resources |

### Introduction Supplements

| Topic | Guide | Key Concepts |
|-------|-------|--------------|
| The var Keyword | [var.md](src/introduction/var.md) | Local type inference, where var works, readability guidelines |
| Strings: Deep Dive | [strings-deep-dive.md](src/introduction/strings-deep-dive.md) | String pool, StringBuilder, format specifiers, text blocks, regex |

### Database

| # | Topic | Guide | Key Concepts |
|---|-------|-------|--------------|
| 26 | JDBC | [jdbc.md](src/jdbc/jdbc.md) | DriverManager, PreparedStatement, ResultSet, transactions, DAO pattern, connection pooling |

### Tooling

| Topic | Guide | Key Concepts |
|-------|-------|--------------|
| Build Tools | [build-tools.md](src/tooling/build-tools.md) | Maven, Gradle, pom.xml, build.gradle.kts, dependency management |

---

## Practice Exercises

The following exercise files contain dedicated practice sets with progressively challenging problems, mini-projects, and common-mistake sections.

| Topic | Exercises | Solutions |
|-------|-----------|-----------|
| Loops | [src/loops/exercise.md](src/loops/exercise.md) | [src/loops/solutions/](src/loops/solutions/) |
| Arrays | [src/arrays/exercise.md](src/arrays/exercise.md) | [src/arrays/solutions/](src/arrays/solutions/) |
| OOP | [src/oop/exercise.md](src/oop/exercise.md) | — |
| Methods | [src/methods/exercise.md](src/methods/exercise.md) | — |
| Enums & Collections | [src/collections/exercise.md](src/collections/exercise.md) | [src/collections/solutions/](src/collections/solutions/) |

---

## Mini Projects

After completing the core curriculum, apply your skills through the guided mini projects described in [src/projects.md](src/projects.md). Projects range from a console quiz game to a fully functional contact book using records.

## Capstone Projects

Once you are comfortable with the mini projects and have covered JDBC, work through the capstone projects in [src/capstone-projects.md](src/capstone-projects.md). These are full applications — persistent, multi-layered, and closer to what production Java code looks like. Complete them before starting Spring Boot.

---

## Repository Structure

```
java-for-everyone/
├── src/
│   ├── introduction/       GUIDE.MD, var.md, strings-deep-dive.md, *.java
│   ├── conditionals/       Conditionals.MD, Conditionals.java
│   ├── loops/              Loops.md, exercise.md, solutions/
│   ├── methods/            methods.md, exercise.md, *.java
│   ├── arrays/             Arrays.md, exercise.md, solutions/
│   ├── oop/                oop.md, encapsulation.md, inheritance.md,
│   │                       polymorphism.md, abstraction.md, interfaces.md,
│   │                       downcasting.md, records.md, sealed.md,
│   │                       equals-hashcode.md, tostring.md, immutability.md,
│   │                       exercise.md, *.java
│   ├── exceptions/         exceptions.md, *.java
│   ├── enums/              enums.md, *.java
│   ├── collections/        collections.md, comparator.md, iterators.md,
│   │                       exercise.md, Student.java, solutions/
│   ├── generics/           generics.md, GenericsDemo.java
│   ├── lambdas/            lambdas.md, LambdasDemo.java
│   ├── streams/            streams.md, StreamsDemo.java
│   ├── optional/           optional.md, OptionalDemo.java
│   ├── datetime/           datetime.md, DateTimeDemo.java
│   ├── io/                 io.md, FileReadingDemo.java, FileWritingDemo.java
│   ├── jdbc/               jdbc.md, DAO examples
│   ├── tooling/            build-tools.md
│   ├── bank/               bank mini project files
│   ├── projects.md         mini project descriptions (OOP-focused)
│   └── capstone-projects.md  full application projects (before Spring Boot)
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
