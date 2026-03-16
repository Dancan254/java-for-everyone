# Build Tools: Maven and Gradle

Every production Java project uses a build tool. A build tool automates compiling source code, managing dependencies, running tests, and packaging the application into a deliverable file. Without one, you would manage every library JAR manually and run `javac` on hundreds of files by hand. This guide introduces Maven and Gradle — the two tools you will encounter in every professional Java environment.

---

## 1. Why Build Tools Exist

```
Think of it like this:
  A build tool is like a recipe book for your project.
  It specifies every ingredient (dependency), every cooking step (compile, test, package),
  and the exact version of each ingredient needed.
  Anyone with the recipe book can reproduce the same dish on any machine.
```

Core problems build tools solve:

- **Dependency management** — you declare what library you need and the tool downloads the correct version, along with everything that library itself depends on.
- **Reproducibility** — the build is described in a configuration file committed to version control; every developer and CI server builds identically.
- **Lifecycle automation** — one command (`mvn package` or `./gradlew build`) compiles, tests, and packages your application.
- **Plugin ecosystem** — code coverage, static analysis, Docker image generation, and deployment are all available as plugins.

---

## 2. Maven

Maven is the older and more widely adopted of the two tools. It uses an XML configuration file called `pom.xml` (Project Object Model). Maven defines a strict, opinionated lifecycle; most projects follow the same structure.

### Standard project layout

```
my-project/
├── pom.xml                   ← Build configuration
└── src/
    ├── main/
    │   └── java/
    │       └── com/example/
    │           └── App.java  ← Production code
    └── test/
        └── java/
            └── com/example/
                └── AppTest.java  ← Test code
```

Maven requires this exact layout by convention. If you follow it, almost no additional configuration is needed.

### pom.xml basics

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
             http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <!-- Project coordinates — uniquely identify this project in the Maven ecosystem. -->
    <groupId>com.example</groupId>
    <artifactId>my-project</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <properties>
        <!-- Tell Maven to compile with Java 21 source and target. -->
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>

        <!-- A dependency from Maven Central. -->
        <!-- scope: compile (default) — available at compile and runtime. -->
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>33.0.0-jre</version>
        </dependency>

        <!-- scope: test — only available when compiling and running tests. -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.1</version>
            <scope>test</scope>
        </dependency>

    </dependencies>

</project>
```

### Common Maven commands

| Command | What it does |
|---|---|
| `mvn compile` | Compile production source code |
| `mvn test` | Compile and run all tests |
| `mvn package` | Compile, test, and produce `target/my-project-1.0.0.jar` |
| `mvn clean` | Delete the `target/` directory |
| `mvn clean package` | Clean, then build from scratch |
| `mvn dependency:tree` | Print the full dependency tree (useful for diagnosing version conflicts) |
| `mvn install` | Build and install the artifact into your local `~/.m2` repository |

### Dependency scopes

| Scope | Available at compile? | Available at runtime? | Available in tests? |
|---|---|---|---|
| `compile` (default) | Yes | Yes | Yes |
| `test` | No | No | Yes |
| `provided` | Yes | No (provided by container/JVM) | Yes |
| `runtime` | No | Yes | Yes |

---

## 3. Gradle

Gradle uses a Groovy or Kotlin DSL build script (`build.gradle` or `build.gradle.kts`). It is faster than Maven for large projects because it supports incremental builds and build caching. Spring Boot and Android both use Gradle by default.

### Standard project layout

Gradle follows the same `src/main/java` and `src/test/java` convention as Maven when the `java` plugin is applied.

```
my-project/
├── build.gradle.kts       ← Build configuration (Kotlin DSL)
├── settings.gradle.kts    ← Project name and multi-project settings
└── src/
    ├── main/java/
    │   └── com/example/App.java
    └── test/java/
        └── com/example/AppTest.java
```

### build.gradle.kts basics

```kotlin
// settings.gradle.kts
rootProject.name = "my-project"
```

```kotlin
// build.gradle.kts
plugins {
    java                  // Applies the Java plugin: compile, test, jar tasks.
}

group   = "com.example"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21) // Use Java 21.
    }
}

repositories {
    mavenCentral() // Download dependencies from Maven Central.
}

dependencies {
    // Implementation: available at compile and runtime.
    implementation("com.google.guava:guava:33.0.0-jre")

    // testImplementation: only available during tests.
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform() // Tell Gradle to use JUnit 5 to discover and run tests.
}
```

### Common Gradle commands

| Command | What it does |
|---|---|
| `./gradlew build` | Compile, test, and package (equivalent to `mvn clean package`) |
| `./gradlew test` | Compile and run all tests |
| `./gradlew clean` | Delete the `build/` directory |
| `./gradlew jar` | Produce `build/libs/my-project-1.0.0.jar` |
| `./gradlew dependencies` | Print the full dependency tree |
| `./gradlew tasks` | List all available tasks |

The `./gradlew` wrapper script (checked into version control) downloads and runs the exact version of Gradle specified in `gradle/wrapper/gradle-wrapper.properties`. You never need Gradle installed globally.

---

## 4. Maven vs Gradle — When to Use Which

| | Maven | Gradle |
|---|---|---|
| Configuration format | XML (`pom.xml`) | Groovy or Kotlin DSL |
| Build speed | Slower for large projects | Faster (incremental builds, caching) |
| Convention vs configuration | Highly opinionated | More flexible |
| Learning curve | Lower (XML is explicit) | Higher (DSL requires understanding) |
| Ecosystem support | Universal | Strong; required for Android and Spring Boot defaults |
| Multi-project builds | Supported but verbose | First-class, concise |

**When starting out:** Maven is fine and its XML is explicit. When working on Android, large multi-module projects, or Spring Boot projects that scaffold with Gradle, learn Gradle.

---

## 5. Where Dependencies Come From: Maven Central

Both Maven and Gradle download dependencies from **Maven Central** (`https://repo1.maven.org/maven2`), the central repository for the Java ecosystem. Every public library specifies its coordinates as `groupId:artifactId:version`.

To find the right coordinates:
1. Visit [https://mvnrepository.com](https://mvnrepository.com) and search for the library.
2. Click the version you want.
3. Copy the Maven `<dependency>` block or Gradle `implementation(...)` line.

The first time you build, the tool downloads the required JARs into a local cache (`~/.m2` for Maven, `~/.gradle/caches` for Gradle). Subsequent builds use the cache — no re-downloading unless versions change.

---

## 6. The Build Lifecycle

Maven and Gradle both define a sequence of phases that always run in order.

**Maven lifecycle (simplified):**

```
validate → compile → test → package → verify → install → deploy
```

Running `mvn package` automatically runs all earlier phases first: validate, compile, and test. Running `mvn test` automatically runs compile first.

**Gradle task graph:**

Gradle does not have a fixed lifecycle. Instead, tasks declare dependencies on other tasks. `build` depends on `test`, which depends on `compileTestJava`, which depends on `compileJava`. Gradle runs only the tasks that need to run, skipping those whose inputs have not changed since the last build (incremental builds).

---

## 7. Running a Packaged Application

After `mvn package` or `./gradlew jar`, the output is a JAR file. To run it, the JAR must either contain a manifest with a `Main-Class` attribute, or you must specify the class on the command line.

```bash
# Run a JAR with a specified main class.
java -cp target/my-project-1.0.0.jar com.example.App

# Run an executable JAR (requires Main-Class in the manifest).
java -jar target/my-project-1.0.0.jar
```

For applications with multiple dependencies, a common approach is the Maven Shade plugin or Gradle Shadow plugin, which produces a "fat JAR" (also called an "uber JAR") containing your code and all dependencies in a single file.

---

## 8. Key Takeaways

- A build tool automates compiling, testing, packaging, and dependency management — you should never manage JAR files manually
- Both Maven and Gradle download dependencies by `groupId:artifactId:version` from Maven Central; [mvnrepository.com](https://mvnrepository.com) is the canonical search engine
- Maven uses `pom.xml` with a fixed lifecycle; Gradle uses a DSL with a task graph and incremental builds
- The standard source layout (`src/main/java`, `src/test/java`) is shared by both tools
- `mvn clean package` and `./gradlew build` are the two commands you will type most often
- Gradle is faster for large projects and is the default for Spring Boot and Android; Maven is simpler to learn initially
- The Gradle wrapper (`./gradlew`) pins the exact Gradle version and requires no global installation

---

In every professional team environment, the build tool configuration file is as important as the source code. It is the single source of truth for what the project needs and how it is assembled. Being able to read and modify a `pom.xml` or `build.gradle.kts` is a foundational skill for any Java developer.
