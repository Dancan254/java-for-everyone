package collections.solutions;

import java.util.*;

/**
 * Exercise 1.2 — Unique Roles
 *
 * Demonstrates removing duplicates by converting a List of enums to a Set,
 * and using contains() for membership testing.
 */
public class UniqueRoles {

    enum Role {
        ADMIN, EDITOR, VIEWER, MODERATOR
    }

    public static void main(String[] args) {

        // 1. List with duplicates
        List<Role> rolesWithDuplicates = Arrays.asList(
                Role.ADMIN, Role.VIEWER, Role.EDITOR,
                Role.VIEWER, Role.ADMIN, Role.MODERATOR, Role.EDITOR);
        System.out.println("Original list : " + rolesWithDuplicates);

        // 2. Convert to a Set to remove duplicates
        Set<Role> uniqueRoles = new LinkedHashSet<>(rolesWithDuplicates);

        // 3. Print unique roles
        System.out.println("Unique roles  : " + uniqueRoles);

        // 4. Membership checks
        System.out.println("Contains ADMIN     : " + uniqueRoles.contains(Role.ADMIN));
        System.out.println("Contains MODERATOR : " + uniqueRoles.contains(Role.MODERATOR));

        // There is no way to check for a "non-existent" enum value at compile time,
        // but we can demonstrate that all four constants are present:
        for (Role r : Role.values()) {
            System.out.println("  " + r + " in set? " + uniqueRoles.contains(r));
        }
    }
}
