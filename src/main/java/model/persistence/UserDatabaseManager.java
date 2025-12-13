package model.persistence;

import model.user.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("unused")
public class UserDatabaseManager {
    public static final String DB_FILE = "data/users.ser";

    public static void saveAllUsers(Map<String, User> users) {
        writeUsersTo(users, DB_FILE);
    }

    public static Map<String, User> readAllUsers() {
        return readUsersFrom(DB_FILE);
    }

    private static Map<String, User> readUsersFrom(String filename) {

        Map<String, User> users = new HashMap<>();
        try (FileInputStream fs = new FileInputStream(filename);
             ObjectInputStream objInStream = new ObjectInputStream(fs)) {
            Object obj = objInStream.readObject();
            if (obj instanceof Map) {
                //noinspection unchecked
                users = (Map<String, User>) obj; // if the thing we read was in fact a map, cast it and store it
            }
        } catch (FileNotFoundException e) {
            //can ignore exception if no file found, since that just means we'll return a blank map
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        return users;
    }

    public static void writeUsersTo(Map<String, User> users, String fileName) {
        //for this to work, the users MUST (implement) Serializable, which means it can be written to a file
        // anything with a Scanner or BFF as instance variable can not be serializable.

        try (FileOutputStream fs = new FileOutputStream(fileName)) {
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(users); //write the users to a file in a machine-readable way
            os.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not find file " + fileName);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Error: Could not write to file " + fileName + ". Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

