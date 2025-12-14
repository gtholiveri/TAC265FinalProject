package model.persistence;

import controller.exceptions.NameTakenException;
import model.Book;
import model.user.Group;
import model.user.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Persistence manager for groups
 */
public class GroupDatabaseManager {
    private static final String DB_FILE = "data/groups/groups.ser";
    // kind of chose eventually to make this class the single source of truth for groups
    // since we were eventually having to like update things in multiple places and that was an antipattern and a half
    private static final Map<String, Group> groups = loadGroups();


    /**
     * The big loading method that deserializes the whole map of users
     */
    private static Map<String, Group> loadGroups() {
        Map<String, Group> loadedGroups = new HashMap<>();
        try (FileInputStream fs = new FileInputStream(DB_FILE);
             ObjectInputStream objInStream = new ObjectInputStream(fs)) {
            Object obj = objInStream.readObject();
            if (obj instanceof Map) {
                //noinspection unchecked
                loadedGroups = (Map<String, Group>) obj;
            }
        } catch (FileNotFoundException e) {
            // Can ignore, no existing data file: return empty map
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error loading groups from " + DB_FILE, e);
        }
        return loadedGroups;
    }

    /**
     * @return True if the group already exists, false otherwise
     */
    public static boolean groupExists(String name) {
        return groups.containsKey(name);
    }


    /**
     * Adds in a group and saves it
     */
    public static void addGroup(Group group) {
        groups.put(group.getName(), group);
        save();
    }

    public static void removeGroup(String name) {
        groups.remove(name);
        save();
    }

    public static Group createGroup(String name, Book book, User admin) throws NameTakenException {
        if (groupExists(name)) {
            throw new NameTakenException("Group name " + name + " is already taken.");
        }
        Group group = new Group(name, admin, book);
        addGroup(group);
        return group;
    }

    /**
     * Gets all groups that a particular user is a member of
     */
    public static List<Group> getGroupsForUser(User user) {
        List<Group> userGroups = new ArrayList<>();
        for (Group group : groups.values()) {
            if (group.isMember(user.getUsername())) {
                userGroups.add(group);
            }
        }
        return userGroups;
    }

    /**
     * Save the current map that's in memory to persistence
     */
    public static void save() {
        // Create the directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(DB_FILE).getParent());
        } catch (IOException e) {
            throw new RuntimeException("Failed to create groups directory", e);
        }

        try (FileOutputStream fs = new FileOutputStream(DB_FILE);
             ObjectOutputStream os = new ObjectOutputStream(fs)) {
            os.writeObject(groups);
        } catch (IOException e) {
            System.err.println("Error: Could not write to file " + DB_FILE + ". Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
