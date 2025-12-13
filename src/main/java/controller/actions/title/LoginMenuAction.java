package controller.actions.title;

import controller.actions.PageRankApp;
import view.menu.LoginMenuFactory;

public class LoginMenuAction implements Runnable {

    /**
     * - Transitions to the login window<br>
     * - Prompts the user for username and password<br>
     * - If credentials are valid, transition to the main menu<br>
     * - Otherwise, display an error message
     */
    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();
        app.transitionTo(LoginMenuFactory.create());
    }
}
