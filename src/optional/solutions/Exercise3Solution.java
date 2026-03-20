package optional.solutions;

import java.util.Optional;

/**
 * Exercise3Solution.java
 *
 * Solutions for Exercise Set 3: Transforming with map, flatMap, and filter.
 * Covers: map() to transform the wrapped value, flatMap() to avoid
 * Optional<Optional<T>>, and filter() to conditionally empty the Optional.
 */
public class Exercise3Solution {

    // Supporting model for flatMap chaining demo
    static class Address {
        private final String city;

        Address(String city) {
            this.city = city;
        }

        Optional<String> getCity() {
            return Optional.ofNullable(city);
        }
    }

    static class User {
        private final String name;
        private final Address address;

        User(String name, Address address) {
            this.name    = name;
            this.address = address;
        }

        Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }
    }

    public static void main(String[] args) {
        exercise3_1();
        exercise3_2();
        exercise3_3();
    }

    // -------------------------------------------------------------------------
    // Exercise 3.1 — map()
    // -------------------------------------------------------------------------

    static void exercise3_1() {
        System.out.println("=== Exercise 3.1: map() ===");

        Optional<String> name = Optional.of("Alice");

        // map() applies a function to the value if present; returns empty if absent.
        Optional<Integer> length = name.map(String::length);
        System.out.println("Name length: " + length.orElse(0)); // Output: 5

        // map() on an empty Optional returns empty without executing the function.
        Optional<String> noName = Optional.empty();
        Optional<Integer> noLength = noName.map(String::length);
        System.out.println("No name length: " + noLength.orElse(-1)); // Output: -1
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 3.2 — filter()
    // -------------------------------------------------------------------------

    static void exercise3_2() {
        System.out.println("=== Exercise 3.2: filter() ===");

        Optional<String> name = Optional.of("Alexander");

        // filter() returns the Optional unchanged if the predicate holds; empty otherwise.
        Optional<String> longName = name.filter(n -> n.length() > 5);
        System.out.println("Long name: " + longName.orElse("too short")); // Output: Alexander

        Optional<String> shortName = Optional.of("Bob");
        Optional<String> filtered  = shortName.filter(n -> n.length() > 5);
        System.out.println("Short name filtered: " + filtered.orElse("too short")); // Output: too short
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Exercise 3.3 — flatMap() for chaining Optional-returning methods
    // -------------------------------------------------------------------------

    static void exercise3_3() {
        System.out.println("=== Exercise 3.3: flatMap() ===");

        User userWithCity    = new User("Alice",  new Address("London"));
        User userWithNoCity  = new User("Bob",    new Address(null));
        User userWithNoAddr  = new User("Charlie", null);

        // flatMap avoids wrapping Optional inside another Optional.
        // user.getAddress() returns Optional<Address>
        // address.getCity() returns Optional<String>
        // Without flatMap: map would produce Optional<Optional<String>>
        // With flatMap:    it stays Optional<String>
        printCity(Optional.of(userWithCity));   // Output: London
        printCity(Optional.of(userWithNoCity)); // Output: City not available
        printCity(Optional.of(userWithNoAddr)); // Output: City not available
        System.out.println();
    }

    static void printCity(Optional<User> userOpt) {
        String city = userOpt
                .flatMap(User::getAddress)
                .flatMap(Address::getCity)
                .orElse("City not available");
        System.out.println("City: " + city);
    }
}
