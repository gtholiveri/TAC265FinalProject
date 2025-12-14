package controller;

import com.googlecode.lanterna.gui2.Window;
import controller.exceptions.IncorrectPasswordException;
import controller.exceptions.NoSuchElementException;
import controller.exceptions.NameTakenException;
import model.persistence.GroupDatabaseManager;
import model.persistence.UserDatabaseManager;
import model.user.User;
import view.GUIManager;
import view.menu.MainMenuFactory;

/**
 * Main application controller. Used Singleton pattern which was great since I didn't
 * have to pass it around a bajillion times<br>
 * Manages holds and communicates between the window state, user state / auth, and data layers
 */
public class PageRankApp {
    public static void main(String[] args) {
    }

    private static PageRankApp instance = new PageRankApp();

    private User currentUser = new User("TestUser", "1");

    public GUIManager guiManager;

    private PageRankApp() {
        guiManager = new GUIManager();
        // Data is now loaded automatically by DatabaseManagers via static initialization
    }

    public void exit() {
        guiManager.stop();
    }

    public User getCurrentUser() {
        if (currentUser == null) {
            throw new RuntimeException("getCurrentUser() called when currentUser null");
        }
        return currentUser;
    }

    public void transitionTo(Window newWindow) {
        guiManager.transitionTo(newWindow);
    }

    public void save() {
        UserDatabaseManager.save();
        GroupDatabaseManager.save();
    }

    public static PageRankApp getInstance() {
        return instance;
    }

    public void execute() {
        guiManager.start();
    }

    public void logInUser(String username, String password) throws NoSuchElementException, IncorrectPasswordException {
        // nonexistent user
        if (!UserDatabaseManager.userExists(username)) {
            throw new NoSuchElementException("User does not exist: " + username);
        }

        // get user and check password
        User user = UserDatabaseManager.getUser(username);
        if (!user.checkPassword(password)) {
            throw new IncorrectPasswordException("Incorrect password for user: " + username);
        }

        // correct username and password: log them in
        this.currentUser = user;

        // also transition the screen
        transitionTo(MainMenuFactory.create());
    }

    /**
     * I felt like this method should be here instead of the database manager because it directly mutates the current user
     * But idk this is one of those points where separation of concerns gets kind of fuzzy to me
     * @throws NameTakenException If the user we're trying to add's username already exists
     */
    public void addUser(User newUser) throws NameTakenException {
        if (UserDatabaseManager.userExists(newUser.getUsername())) {
            throw new NameTakenException("User already exists: " + newUser.getUsername());
        }

        // valid username (and the username and password guaranteed to be non-empty by the action)
        UserDatabaseManager.addUser(newUser);
        this.currentUser = newUser;

        transitionTo(MainMenuFactory.create());
    }

}
