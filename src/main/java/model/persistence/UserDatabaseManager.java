package model.persistence;

import model.user.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the first database/persistence manager thing I brought in,
 * I copied a lot of Kendra's code from A12 to make it but ended up adding
 * some utility methods and changing a lot about it
 */
public class UserDatabaseManager {
    private static final String DB_FILE = "data/users/users.ser";
    // mentioned elsewhere but we chose to make this statically initialized and the single place where this is managed
    private static final Map<String, User> users = loadUsers();

    /**
     * The main loading method: deserializes whole map
     */
    private static Map<String, User> loadUsers() {
        Map<String, User> loadedUsers = new HashMap<>();
        try (FileInputStream fs = new FileInputStream(DB_FILE);
             ObjectInputStream objInStream = new ObjectInputStream(fs)) {
            Object obj = objInStream.readObject();
            if (obj instanceof Map) {
                //noinspection unchecked
                loadedUsers = (Map<String, User>) obj;
            }
        } catch (FileNotFoundException e) {
            // Can ignore - means no existing data file, return empty map
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error loading users from " + DB_FILE, e);
        }
        return loadedUsers;
    }


    /**
     * @return The User object associated with the given username, or null if no such user exists (precondition that it exists)
     */
    public static User getUser(String username) {
        return users.get(username);
    }

    /**
     * @return true if the user exists, false otherwise
     */
    public static boolean userExists(String username) {
        return users.containsKey(username);
    }

    /**
     * Chose Collection here since it's theoretically more flexible and it's what values() gives me
     */
    public static Collection<User> getAllUsers() {
        return users.values();
    }

    public static void addUser(User user) {
        users.put(user.getUsername(), user);
        save();
    }

    public static void removeUser(String username) {
        users.remove(username);
        save();
    }

    /**
     * Serializes the current map
     */
    public static void save() {
        // Create the directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(DB_FILE).getParent());
        } catch (IOException e) {
            throw new RuntimeException("Failed to create users directory", e);
        }

        try (FileOutputStream fs = new FileOutputStream(DB_FILE);
             ObjectOutputStream os = new ObjectOutputStream(fs)) {
            os.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error: Could not write to file " + DB_FILE + ". Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

