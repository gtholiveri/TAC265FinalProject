package controller.ui.login;

import controller.ui.MenuOption;
import controller.ui.PageRankApp;
import view.Printer;

public class LoginOption extends MenuOption {


    public LoginOption(PageRankApp app) {
        super(app);
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
        Printer.println("Login to be implemented!");
        app.stall();
    }

    @Override
    public String getDisplay() {
        return "Login";
    }
}
