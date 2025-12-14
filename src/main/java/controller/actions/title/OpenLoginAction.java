package controller.actions.title;

import controller.PageRankApp;
import view.menu.title.LoginMenuFactory;

/**
 * Action for when the user wants to login
 */
public class OpenLoginAction implements Runnable {

    /**
     * Transitions to the login window
     */
    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();
        app.transitionTo(LoginMenuFactory.create());
    }
}
