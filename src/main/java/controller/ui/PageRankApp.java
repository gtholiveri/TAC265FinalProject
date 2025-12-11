package controller.ui;

import controller.ui.login.LoginMenu;
import controller.ui.main.MainMenu;
import model.user.User;
import view.*;

import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static model.persistence.UserDatabaseManager.readAllUsers;
import static view.TextFormat.BOLD;
import static view.TextFormat.ITALIC;


public class PageRankApp {
    private Menu currMenu;

    private Map<String, User> users;
    private BlockingQueue<String> inputQueue;

    public UI ui = new TerminalUI();
    private boolean shouldExit = false;

    private User currentUser = null;
    private MenuRenderer renderer = new TerminalMenuRenderer();
    private InputListener inputListener;

    public static void main(String[] args) {
        PageRankApp app = new PageRankApp();
        app.execute();

    }

    public PageRankApp() {
        currMenu = new LoginMenu(this);
        inputListener = new InputListener(this);

        users = readAllUsers();
        inputQueue = new ArrayBlockingQueue<>(1);
    }

    /**
     * Executes the main loop of the application<br>
     * - Render the current menu<br>
     * - wait for user input<br>
     */
    public void execute() {
        Printer.printlnFormatted("Welcome to PageRank, the competitive reading app!", EnumSet.of(BOLD));
        stall();
        while (!shouldExit) {
            refreshScreen();
            waitForInput();
        }

        Printer.clearPrint("");
        Printer.printFormatted("Thanks for using PageRank!", EnumSet.of(BOLD, ITALIC));
    }


    public void refreshScreen() {
        renderer.render(currMenu);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void queueInput(String input) {
        inputQueue.offer(input);
    }

    private void waitForInput() {
        try {
            // block until we get some input that is valid
            String input = "";
            while (!currMenu.validInput(input)) {
                input = inputQueue.take();
            }

            // once we get valid input, fire the menu off of that
            // this might be fragile or there might be a cleaner way to do this but I think it's fairly readable
            currMenu.fire(Integer.parseInt(input));

        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for input.");
            throw new RuntimeException(e);
        }
    }

    public void stall() {
        inputQueue.clear(); // necessary to remove anything that already is in there
        // which would instantly skip us past the stall
        Printer.println("Press anything to continue.");

        try {
            // block until we get some input, doesn't matter what it is
            inputQueue.take();
        } catch (InterruptedException e) {
            System.err.println("Interrupted while stalling for confirmation key press.");
            throw new RuntimeException(e);
        }

    }

    public void exit() {
        shouldExit = true;
        inputListener.close();
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

    public void transitionTo(Menu newMenu) {
        currMenu = newMenu;
    }
}
