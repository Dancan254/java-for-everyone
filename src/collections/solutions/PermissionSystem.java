package collections.solutions;

import java.util.EnumSet;

/**
 * Exercise 2.2 — Permission System
 *
 * Demonstrates EnumSet factory methods, membership testing,
 * and granting new permissions.
 */
public class PermissionSystem {

    enum Permission {
        READ, WRITE, DELETE, EXECUTE, ADMIN
    }

    // Check whether a user has a specific permission
    public static boolean hasAccess(EnumSet<Permission> userPerms, Permission required) {
        return userPerms.contains(required);
    }

    // Return a new permission set with the additional permission included
    public static EnumSet<Permission> grant(EnumSet<Permission> current, Permission additional) {
        EnumSet<Permission> updated = EnumSet.copyOf(current);
        updated.add(additional);
        return updated;
    }

    public static void main(String[] args) {

        // 1. Create permission sets using EnumSet factory methods
        EnumSet<Permission> guestPerms = EnumSet.of(Permission.READ);
        EnumSet<Permission> editorPerms = EnumSet.of(Permission.READ, Permission.WRITE);
        EnumSet<Permission> developerPerms = EnumSet.of(Permission.READ, Permission.WRITE, Permission.EXECUTE);
        EnumSet<Permission> adminPerms = EnumSet.allOf(Permission.class);

        System.out.println("Guest     : " + guestPerms);
        System.out.println("Editor    : " + editorPerms);
        System.out.println("Developer : " + developerPerms);
        System.out.println("Admin     : " + adminPerms);
        System.out.println();

        // 2. Check access
        System.out.println("Guest has READ?    " + hasAccess(guestPerms, Permission.READ));
        System.out.println("Guest has WRITE?   " + hasAccess(guestPerms, Permission.WRITE));
        System.out.println("Editor has WRITE?  " + hasAccess(editorPerms, Permission.WRITE));
        System.out.println("Editor has DELETE?  " + hasAccess(editorPerms, Permission.DELETE));
        System.out.println();

        // 3. Grant DELETE to Editor
        System.out.println("Granting DELETE to Editor...");
        EnumSet<Permission> upgradedEditor = grant(editorPerms, Permission.DELETE);
        System.out.println("Editor (updated) : " + upgradedEditor);
        System.out.println("Editor has DELETE? " + hasAccess(upgradedEditor, Permission.DELETE));
    }
}
