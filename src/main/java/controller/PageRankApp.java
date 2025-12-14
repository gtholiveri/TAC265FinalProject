package controller;

import com.googlecode.lanterna.gui2.Window;
import controller.exceptions.IncorrectPasswordException;
import controller.exceptions.NoSuchElementException;
import controller.exceptions.NameTakenException;
import model.persistence.UserDatabaseManager;
import model.user.User;
import view.GUIManager;
import view.menu.MainMenuFactory;

import java.util.Map;

import static model.persistence.UserDatabaseManager.readAllUsers;


public class PageRankApp {
    public static void main(String[] args) {
    }

    private static PageRankApp instance = new PageRankApp();

    private Map<String, User> users;
    private User currentUser = new User("TestUser", "1");

    public GUIManager guiManager;

    private PageRankApp() {
        guiManager = new GUIManager();

        users = readAllUsers();
    }


    public void exit() {
        guiManager.stop();
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public boolean checkPassword(String username, String password) {
        if (!userExists(username)) {
            throw new RuntimeException("This shouldn't be possible: login attempted for nonexistent user.");
        }
        User u = users.get(username);

        return u.checkPassword(password);
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            throw new RuntimeException("getCurrentUser() called when currentUser null");
        }

        return currentUser;
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public void transitionTo(Window newWindow) {
        guiManager.transitionTo(newWindow);
    }

    public void save() {
        UserDatabaseManager.saveAllUsers(users);
        // we'll have more saving potentially going on when more things become involved (groups, libraries, etc)
    }

    public static PageRankApp getInstance() {
        return instance;
    }

    public void execute() {
        guiManager.start();
    }

    public void logInUser(String username, String password) throws NoSuchElementException, IncorrectPasswordException {
        // nonexistent user
        if (!userExists(username)) {
            throw new NoSuchElementException("User does not exist: " + username);
        }

        // incorrect password
        if (!checkPassword(username, password)) {
            throw new IncorrectPasswordException("Incorrect password for user: " + username + " and password: " + password);
        }

        // correct username and password: log them in
        this.currentUser = users.get(username);

        // also transition the screen
        transitionTo(MainMenuFactory.create());
    }

    public void addUser(User newUser) throws NameTakenException {
        if (userExists(newUser.getUsername())) {
            throw new NameTakenException("User already exists: " + newUser.getUsername());
        }

        // valid username (and the username and password guaranteed to be non-empty by the action
        users.put(newUser.getUsername(), newUser);
        this.currentUser = newUser;
        save();

        transitionTo(MainMenuFactory.create());
    }

}
