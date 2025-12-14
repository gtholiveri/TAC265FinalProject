package model.persistence;

import controller.exceptions.NameTakenException;
import model.Book;
import model.user.Group;
import model.user.User;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupDatabaseManager {
    private static final String DB_FILE = "data/groups.ser";
    private static final Map<String, Group> groups = loadGroups();

    // Static initializer - loads all groups immediately
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
            // Can ignore - means no existing data file, return empty map
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Error loading groups from " + DB_FILE, e);
        }
        return loadedGroups;
    }

    // Public API methods
    public static Group getGroup(String name) {
        return groups.get(name);
    }

    public static boolean groupExists(String name) {
        return groups.containsKey(name);
    }

    public static Collection<Group> getAllGroups() {
        return groups.values();
    }

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

    public static Set<Group> getGroupsForUser(User user) {
        return groups.values().stream()
                .filter(group -> group.isMember(user.getUsername()))
                .collect(Collectors.toSet());
    }

    public static void save() {
        try (FileOutputStream fs = new FileOutputStream(DB_FILE);
             ObjectOutputStream os = new ObjectOutputStream(fs)) {
            os.writeObject(groups);
        } catch (IOException e) {
            System.err.println("Error: Could not write to file " + DB_FILE + ". Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
