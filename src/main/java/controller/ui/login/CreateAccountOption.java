package controller.ui.login;

import controller.ui.MenuOption;
import controller.ui.PageRankApp;
import view.Printer;

public class CreateAccountOption extends MenuOption {

    public CreateAccountOption(PageRankApp app) {
        super(app);
    }

    /**
     * - Clears the screen<br>
     * - Prompts the user for a new username<br>
     * - Validates that the username is not already taken<br>
     * - If it's already taken, display a message, stall, and ask for another username<br>
     * - Otherwise, prompt the user for a password<br>
     * - Display success message<br>
     * - Save the new user to the database<br>
     * - Transition to the main menu
     */
    @Override
    public void fire() {
        // TODO: implement create account fire() method
        Printer.println("Create account to be implemented!");
        app.stall();
    }

    @Override
    public String getDisplay() {
        return "Create new account";
    }
}
