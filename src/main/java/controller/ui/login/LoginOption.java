package controller.ui.login;

import controller.ui.MenuOption;
import controller.ui.PageRankApp;
import controller.ui.main.MainMenu;
import view.Printer;

public class LoginOption extends MenuOption {


    public LoginOption(PageRankApp app) {
        super(app, "Log in");
    }

    /**
     * - Clears the screen<br>
     * - Prompts the user for username and password<br>
     * - If credentials are valid, transition to the main menu<br>
     * - Otherwise, display an error message and confirm before continuing
     */
    @Override
    public void fire() {
        // TODO: implement login fire() method
        Printer.clearPrintln("Enter username:");
        String username = app.ui.readStr("");

        if (!app.userExists(username)) {
            Printer.println("No users with that username. Try again or create an account.");
            app.stall();
            return;
        }

        String password = app.ui.readStr("Enter password: ");

        if (app.checkPassword(username, password)) {
            Printer.clearPrintln("Welcome, " + username);
            app.stall();
            app.transitionTo(new MainMenu(app));
        } else {
            Printer.println("Incorrect password. Try logging in again.");
        }
    }
}
