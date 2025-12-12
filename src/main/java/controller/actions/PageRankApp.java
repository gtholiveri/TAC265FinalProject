package controller.actions;

import com.googlecode.lanterna.gui2.Window;
import model.persistence.UserDatabaseManager;
import model.user.User;
import view.GUIManager;

import java.util.Map;

import static model.persistence.UserDatabaseManager.readAllUsers;


public class PageRankApp {
    public static void main(String[] args) {
    }

    private static PageRankApp instance = new PageRankApp();

    private Map<String, User> users;
    private User currentUser = null;

    private boolean shouldExit = false;

    public GUIManager gui;

    private PageRankApp() {
        gui = new GUIManager();

        users = readAllUsers();
    }





    public void exit() {
        shouldExit = true;
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    /**
     * 
     * @param username
     * @param password
     * @return
     */
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

    public void transitionTo(Window newWindow) {
        gui.transitionTo(newWindow);
    }

    public void save() {
        UserDatabaseManager.saveAllUsers(users);
        // we'll have more saving potentially going on when more things become involved (groups, libraries, etc)
    }



    public static PageRankApp getInstance() {
        return instance;
    }

    public void execute() {
        gui.start();
    }
}
