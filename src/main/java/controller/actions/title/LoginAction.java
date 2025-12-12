package controller.actions.title;

import controller.actions.PageRankApp;

public class LoginAction implements Runnable {

    /**
     * - Clears the screen<br>
     * - Prompts the user for username and password<br>
     * - If credentials are valid, transition to the main menu<br>
     * - Otherwise, display an error message and confirm before continuing
     */
    @Override
    public void run() {
        //TODO refactor this to switch to a login dialog
        PageRankApp app = PageRankApp.getInstance();

        app.transitionTo(LoginDialogBuilder.build());


        /*
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
        }*/
    }
}
