package controller.actions.title;

import controller.PageRankApp;

/**
 * Action for when user wants to exit the application
 */
public class ExitAction implements Runnable {
    /**
     * Gracefully exit the application by calling the app's exit method<br>
     */
    @Override
    public void run() {
        PageRankApp.getInstance().exit();
    }

}
