package model.persistence;

import model.user.User;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserDatabaseManager {
    private static final String DB_FILE = "data/users.ser";
    private static final Map<String, User> users = loadUsers();

    // Static initializer - loads all users immediately
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

    // Public API methods
    public static User getUser(String username) {
        return users.get(username);
    }

    public static boolean userExists(String username) {
        return users.containsKey(username);
    }

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

    public static void save() {
        try (FileOutputStream fs = new FileOutputStream(DB_FILE);
             ObjectOutputStream os = new ObjectOutputStream(fs)) {
            os.writeObject(users);
        } catch (IOException e) {
            System.err.println("Error: Could not write to file " + DB_FILE + ". Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

