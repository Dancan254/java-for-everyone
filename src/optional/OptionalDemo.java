import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * OptionalDemo.java
 *
 * A self-contained demonstration of Optional<T> covering every concept in optional.md:
 * the three factory methods (of, ofNullable, empty), isPresent/isEmpty, all retrieval methods
 * (get, orElse, orElseGet, orElseThrow), map/flatMap/filter, ifPresent/ifPresentOrElse,
 * chaining with flatMap, and Optional::stream integration with the Streams API.
 */
public class OptionalDemo {

    // -------------------------------------------------------------------------
    // Supporting model classes used throughout the demo
    // -------------------------------------------------------------------------

    // User stores a bio as a nullable String internally and exposes it as Optional<String>.
    // Storing Optional as a field is wrong (not Serializable); wrapping at the return site is correct.
    static class User {
        private final int id;
        private final String username;
        private final String email;
        private final String bio; // Nullable — stored as null, exposed as Optional.

        public User(int id, String username, String email, String bio) {
            this.id       = id;
            this.username = username;
            this.email    = email;
            this.bio      = bio;
        }

        public int    getId()       { return id; }
        public String getUsername() { return username; }
        public String getEmail()    { return email; }

        // bio is optional — wrap at the return site, not as a field.
        public Optional<String> getBio() {
            return Optional.ofNullable(bio);
        }
    }

    // A simple in-memory user service that demonstrates Optional as a return type.
    static class UserService {
        private final Map<Integer, User> byId       = new HashMap<>();
        private final Map<String, User>  byUsername = new HashMap<>();

        public UserService() {
            User alice = new User(1, "alice", "alice@example.com", "Java developer.");
            User bob   = new User(2, "bob",   "bob@example.com",   null); // Bob has no bio.
            User carol = new User(3, "carol", "carol@example.com", "Loves streams and lambdas.");

            byId.put(1, alice);   byId.put(2, bob);   byId.put(3, carol);
            byUsername.put("alice", alice);
            byUsername.put("bob",   bob);
            byUsername.put("carol", carol);
        }

        // Returns Optional<User> — the caller is told upfront that the user may not exist.
        public Optional<User> findById(int id) {
            return Optional.ofNullable(byId.get(id));
        }

        public Optional<User> findByUsername(String username) {
            return Optional.ofNullable(byUsername.get(username));
        }

        // Chains two Optional lookups: find the user, then get their bio.
        // flatMap is used because getBio() itself returns Optional<String>.
        public Optional<String> getUserBio(int id) {
            return findById(id).flatMap(User::getBio);
        }

        // Returns a display name — falls back to "unknown-user" if the user does not exist.
        public String getDisplayName(int id) {
            return findById(id)
                    .map(User::getUsername)
                    .orElse("unknown-user");
        }
    }

    // -------------------------------------------------------------------------

    public static void main(String[] args) {

        // --- 1. The Three Factory Methods ---

        System.out.println("=== 1. Factory Methods ===");

        // Optional.of(value): wraps a non-null value.
        // Throws NullPointerException immediately if null is passed.
        Optional<String> present = Optional.of("Alice");
        System.out.println(present);
        // Output: Optional[Alice]

        // Optional.ofNullable(value): wraps the value if non-null, returns empty if null.
        // Use when the value's nullability is unknown — e.g., wrapping a legacy method result.
        String valueFromDb = null;
        Optional<String> nullWrapped = Optional.ofNullable(valueFromDb);
        System.out.println(nullWrapped);
        // Output: Optional.empty

        Optional<String> nonNullWrapped = Optional.ofNullable("Bob");
        System.out.println(nonNullWrapped);
        // Output: Optional[Bob]

        // Optional.empty(): creates an empty Optional explicitly.
        // Use in method bodies when you want to return the absence of a value.
        Optional<String> explicitly = Optional.empty();
        System.out.println(explicitly);
        // Output: Optional.empty

        // --- 2. isPresent and isEmpty ---

        System.out.println("\n=== 2. isPresent / isEmpty ===");

        Optional<String> name  = Optional.of("Alice");
        Optional<String> empty = Optional.empty();

        System.out.println("isPresent (Alice): " + name.isPresent());   // Output: true
        System.out.println("isPresent (empty): " + empty.isPresent());  // Output: false
        System.out.println("isEmpty   (Alice): " + name.isEmpty());     // Output: false
        System.out.println("isEmpty   (empty): " + empty.isEmpty());    // Output: true

        // --- 3. Retrieval Methods ---

        System.out.println("\n=== 3. Retrieval Methods ===");

        // get(): returns the value or throws NoSuchElementException if empty.
        // Only call this after confirming the Optional is not empty.
        Optional<String> safeName = Optional.of("Alice");
        if (safeName.isPresent()) {
            System.out.println("get(): " + safeName.get()); // Output: get(): Alice
        }

        // orElse(default): returns the value if present, otherwise the provided default.
        // The default expression is always evaluated, even when the Optional is not empty.
        Optional<String> absentName = Optional.empty();
        String result1 = absentName.orElse("Guest");
        System.out.println("orElse (empty):   " + result1); // Output: orElse (empty):   Guest

        String result2 = Optional.of("Alice").orElse("Guest");
        System.out.println("orElse (present): " + result2); // Output: orElse (present): Alice

        // orElseGet(Supplier): returns the value if present, otherwise invokes the Supplier.
        // The Supplier is ONLY invoked when the Optional is empty — this is the lazy alternative.
        String result3 = absentName.orElseGet(() -> "Generated-default");
        System.out.println("orElseGet (empty): " + result3); // Output: orElseGet (empty): Generated-default

        // Demonstration of the orElse vs orElseGet difference.
        // orElse: the argument is evaluated regardless of whether the Optional is present.
        // orElseGet: the Supplier is only called when the Optional is empty.
        // For expensive or side-effecting defaults, always prefer orElseGet.
        System.out.println("--- orElse always evaluates; orElseGet is lazy ---");

        // orElseThrow() (Java 10+): returns the value or throws NoSuchElementException.
        // Use when an empty Optional represents a violated precondition.
        try {
            Optional.empty().orElseThrow();
        } catch (java.util.NoSuchElementException e) {
            System.out.println("orElseThrow() threw: " + e.getClass().getSimpleName());
            // Output: orElseThrow() threw: NoSuchElementException
        }

        // orElseThrow(Supplier): throws the exception produced by the Supplier.
        // Use for meaningful, domain-specific exceptions.
        try {
            Optional<String> missingUser = Optional.empty();
            missingUser.orElseThrow(() -> new IllegalArgumentException("User not found"));
        } catch (IllegalArgumentException e) {
            System.out.println("orElseThrow(Supplier): " + e.getMessage());
            // Output: orElseThrow(Supplier): User not found
        }

        // --- 4. map, flatMap, filter ---

        System.out.println("\n=== 4. map / flatMap / filter ===");

        // map(Function): transforms the value if present; propagates empty if absent.
        Optional<String> lowerName = Optional.of("alice");
        Optional<String> upperName = lowerName.map(String::toUpperCase);
        System.out.println("map (present): " + upperName);
        // Output: map (present): Optional[ALICE]

        Optional<String> emptyMapped = Optional.<String>empty().map(String::toUpperCase);
        System.out.println("map (empty):   " + emptyMapped);
        // Output: map (empty):   Optional.empty

        // flatMap(Function): like map, but the mapping function itself returns an Optional.
        // Using map in that case would produce Optional<Optional<R>>; flatMap flattens it.
        Optional<String> lowerStr = Optional.of("  hello  ");
        Optional<String> trimmed = lowerStr.map(String::trim).flatMap(
                s -> s.isEmpty() ? Optional.empty() : Optional.of(s)
        );
        System.out.println("flatMap (present, non-empty after trim): " + trimmed);
        // Output: flatMap (present, non-empty after trim): Optional[hello]

        Optional<String> onlySpaces = Optional.of("   ");
        Optional<String> empty2 = onlySpaces.map(String::trim).flatMap(
                s -> s.isEmpty() ? Optional.empty() : Optional.of(s)
        );
        System.out.println("flatMap (present, empty after trim): " + empty2);
        // Output: flatMap (present, empty after trim): Optional.empty

        // filter(Predicate): keeps the value only if it satisfies the predicate.
        Optional<Integer> score = Optional.of(85);
        System.out.println("filter (>= 60): " + score.filter(s -> s >= 60));
        // Output: filter (>= 60): Optional[85]

        System.out.println("filter (>= 90): " + score.filter(s -> s >= 90));
        // Output: filter (>= 90): Optional.empty

        // --- 5. ifPresent and ifPresentOrElse ---

        System.out.println("\n=== 5. ifPresent / ifPresentOrElse ===");

        // ifPresent(Consumer): runs the Consumer if a value is present; does nothing otherwise.
        Optional<String> nameOpt = Optional.of("Alice");
        nameOpt.ifPresent(n -> System.out.println("ifPresent: Hello, " + n));
        // Output: ifPresent: Hello, Alice

        Optional<String> emptyOpt = Optional.empty();
        emptyOpt.ifPresent(n -> System.out.println("This should not print"));
        // (no output)

        // ifPresentOrElse (Java 9+): runs Consumer if present, Runnable if empty.
        // Cleanly replaces the if/else null-check pattern.
        nameOpt.ifPresentOrElse(
                n -> System.out.println("ifPresentOrElse: Welcome back, " + n),
                () -> System.out.println("ifPresentOrElse: Welcome, guest!")
        );
        // Output: ifPresentOrElse: Welcome back, Alice

        emptyOpt.ifPresentOrElse(
                n -> System.out.println("ifPresentOrElse: Welcome back, " + n),
                () -> System.out.println("ifPresentOrElse: Welcome, guest!")
        );
        // Output: ifPresentOrElse: Welcome, guest!

        // --- 6. Chaining Optionals ---

        System.out.println("\n=== 6. Chaining Optionals ===");

        UserService service = new UserService();

        // findById — chained with ifPresentOrElse.
        service.findById(1).ifPresentOrElse(
                u -> System.out.println("Found: " + u.getUsername()),
                () -> System.out.println("User not found")
        );
        // Output: Found: alice

        service.findById(99).ifPresentOrElse(
                u -> System.out.println("Found: " + u.getUsername()),
                () -> System.out.println("User not found")
        );
        // Output: User not found

        // findByUsername — chained to extract email.
        service.findByUsername("carol")
                .map(User::getEmail)
                .ifPresent(email -> System.out.println("Carol's email: " + email));
        // Output: Carol's email: carol@example.com

        // getUserBio — flatMap chains two Optional-returning methods into one clean result.
        System.out.println(service.getUserBio(1).orElse("No bio."));
        // Output: Java developer.

        System.out.println(service.getUserBio(2).orElse("No bio."));
        // Output: No bio.

        System.out.println(service.getUserBio(99).orElse("No bio."));
        // Output: No bio.

        // getDisplayName — map then orElse provides a safe default.
        System.out.println(service.getDisplayName(3));   // Output: carol
        System.out.println(service.getDisplayName(99));  // Output: unknown-user

        // orElseThrow to retrieve a mandatory value.
        User alice = service.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("User 1 must exist"));
        System.out.println("Retrieved via orElseThrow: " + alice.getUsername());
        // Output: Retrieved via orElseThrow: alice

        // --- 7. Optional::stream Integration ---

        System.out.println("\n=== 7. Optional::stream Integration ===");

        // Optional::stream (Java 9+) converts an Optional to a Stream of 0 or 1 elements.
        // Used inside Stream.flatMap to filter out empty Optionals from a list.
        List<Optional<String>> optionals = List.of(
                Optional.of("Alice"),
                Optional.empty(),
                Optional.of("Bob"),
                Optional.empty(),
                Optional.of("Charlie")
        );

        // flatMap(Optional::stream) turns each Optional into a 0- or 1-element stream,
        // and the outer flatMap collapses them all into a single flat stream of present values.
        List<String> presentValues = optionals.stream()
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        System.out.println("flatMap(Optional::stream): " + presentValues);
        // Output: flatMap(Optional::stream): [Alice, Bob, Charlie]

        // Practical pattern: look up user bios for a list of IDs, skip missing users.
        List<Integer> userIds = List.of(1, 99, 2, 3, 0);

        List<String> bios = userIds.stream()
                .map(service::findById)
                .flatMap(Optional::stream)     // discard Optionals that were empty
                .map(u -> u.getUsername() + ": " + u.getBio().orElse("no bio"))
                .collect(Collectors.toList());

        System.out.println("Bios for IDs [1,99,2,3,0]:");
        bios.forEach(b -> System.out.println("  " + b));
        // Output:
        // Bios for IDs [1,99,2,3,0]:
        //   alice: Java developer.
        //   bob: no bio
        //   carol: Loves streams and lambdas.
    }
}
