# Migration Java 21 → Java 25 (LTS)

Guide pratique, étape par étape, pour migrer un projet Java 21 vers Java 25.

---

## 0. Prérequis

- [ ] JDK 25 installé et disponible dans le `PATH`
- [ ] Build tool à jour (Maven / Gradle)
- [ ] Tests verts sur Java 21
- [ ] Sauvegarde / branche Git dédiée (`feat/migrate-java-25`)

---

## 1. Installer et vérifier JDK 25

```bash
# Vérifier la version
java -version

# Exemple de sortie attendue
# java version "25" 2025-09-16 LTS
```

> Si nécessaire, configurez `JAVA_HOME` vers le JDK 25.

---

## 2. Mettre à jour le build tool

### Maven

```xml
<!-- pom.xml -->
<properties>
  <maven.compiler.release>25</maven.compiler.release>
  <maven.compiler.source>25</maven.compiler.source>
  <maven.compiler.target>25</maven.compiler.target>
</properties>
```

Ou via CLI :

```bash
./mvnw versions:set-property -Dproperty=maven.compiler.release -DnewVersion=25
./mvnw versions:set-property -Dproperty=maven.compiler.source -DnewVersion=25
./mvnw versions:set-property -Dproperty=maven.compiler.target -DnewVersion=25
```

Mettez à jour les plugins critiques :

- `maven-compiler-plugin`
- `maven-surefire-plugin`
- `maven-failsafe-plugin`
- `maven-shade-plugin` (si utilisé)

### Gradle (Kotlin DSL)

```kotlin
// build.gradle.kts
java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

tasks.withType<JavaCompile>().configureEach {
  options.release = 25
}
```

Ou Groovy :

```groovy
java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(25)
  }
}

tasks.withType(JavaCompile).configureEach {
  options.release = 25
}
```

---

## 3. Mettre à jour les dépendances critiques

Vérifiez et mettez à jour :

- [ ] Lombok (version compatible JDK 25)
- [ ] ASM / ByteBuddy / cglib (si génération de bytecode)
- [ ] Plugins de build (compiler, surefire, shade, etc.)
- [ ] JAXB / JAX-WS / Activation (si utilisés → dépendances explicites)

Exemple JAXB :

```xml
<!-- Maven -->
<dependency>
  <groupId>jakarta.xml.bind</groupId>
  <artifactId>jakarta.xml.bind-api</artifactId>
  <version>4.0.2</version>
</dependency>
<dependency>
  <groupId>org.glassfish.jaxb</groupId>
  <artifactId>jaxb-runtime</artifactId>
  <version>4.0.5</version>
</dependency>
```

---

## 4. Compiler et tester

```bash
# Maven
./mvnw clean verify

# Gradle
./gradlew clean build
```

Corrigez les erreurs :

- Accès à `sun.*`, `Unsafe`, ou internals JDK → utilisez `VarHandle`, FFM API, ou ajoutez des `--add-opens`/`--add-exports` minimaux.
- Modules Java EE retirés → ajoutez les dépendances manquantes.

---

## 5. (Optionnel) Migration automatisée avec OpenRewrite

### Maven

```bash
mvn -U org.openrewrite.maven:rewrite-maven-plugin:6.49.0:run \
  -Drewrite.recipeArtifactCoordinates=org.openrewrite.recipe:rewrite-migrate-java:RELEASE \
  -Drewrite.activeRecipes=org.openrewrite.java.migrate.UpgradeToJava25 \
  -Drewrite.exportDatatables=true
```

### Gradle

1. Ajoutez le plugin OpenRewrite.
2. Configurez la recette `UpgradeToJava25`.
3. Lancez :

```bash
gradle rewriteRun
```

Relisez le diff avant de merger.

---

## 6. Documentation : JavaDoc HTML → Markdown (JEP 467)

Java 25 standardise les commentaires de documentation en Markdown.

### Syntaxe de base

- Utilisez `///` pour démarrer un commentaire de documentation en Markdown.
- Remplacements courants :
    - `<p>` → ligne vide
    - `<b>text</b>` → `**text**`
    - `<i>text</i>` → `_text_`
    - `<code>...</code>` → `` `...` ``
    - Listes HTML → listes Markdown (`- item`)
    - `<a href="url">text</a>` → `[text](url)`

### Exemple

```java
// Avant (JavaDoc HTML)
/**
 * <p>Calcule la somme.</p>
 * <ul>
 *   <li>Supporte les négatifs</li>
 *   <li>Thread-safe</li>
 * </ul>
 * @see java.lang.Math
 */
public int sum(int a, int b) { ... }

// Après (Markdown, Java 25)
/// Calcule la somme.
///
/// - Supporte les négatifs
/// - Thread-safe
///
/// @see java.lang.Math
public int sum(int a, int b) { ... }
```

### IntelliJ IDEA

- Placez le curseur sur un JavaDoc.
- `Alt+Enter` → **Convert to Markdown documentation comment**.

---

## 7. Fonctionnalités nouvelles (adoption progressive)

À activer uniquement si nécessaire et en connaissance de cause :

- **Scoped Values** (remplacement de `ThreadLocal` dans certains cas)
- **Module import declarations**
- **Flexible constructor bodies**
- **Stream gatherers**
- **Structured concurrency** (souvent en preview)

Pour les fonctionnalités en preview :

```bash
# Maven
-maven.compiler.compilerArgs=--enable-preview

# Gradle
tasks.withType<JavaCompile>().configureEach {
  options.compilerArgs.add("--enable-preview")
}
```

---

## 8. Points de vigilance en production

- **Virtual Threads** : vérifiez l’absence de pinning via JFR (`jdk.VirtualThreadPinned`).
- **GC / mémoire** : testez avec les nouveaux defaults (ZGC, compact object headers).
- **AOT** (si pertinent) :
    - Enregistrement : `-XX:AOTMode=record`
    - Utilisation : `-XX:AOTMode=on`

---

## 9. Checklist finale

- [ ] Build OK sur JDK 25
- [ ] Tests unitaires et d’intégration verts
- [ ] Performance acceptable (latence, mémoire, startup)
- [ ] Documentation convertie en Markdown (au moins pour les APIs publiques)
- [ ] CI/CD mise à jour pour utiliser JDK 25

---

## 10. Rollback (si nécessaire)

- Revenez à la branche précédente.
- Restaurez les propriétés `release/source/target` à 21.
- Revenez aux versions de dépendances compatibles Java 21.

---

## Ressources

- OpenRewrite – Migrate to Java 25 : https://docs.openrewrite.org/running-recipes/popular-recipe-guides/migrate-to-java-25
- Java 25 – Azul Glossary : https://www.azul.com/glossary/java-25/
- Upgrade to Java 25 (breaking changes & migration guide) : https://versions.dev/modernize/java/upgrade-to-java-25