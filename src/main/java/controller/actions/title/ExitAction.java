package controller.actions.title;

import controller.PageRankApp;

public class ExitAction implements Runnable {


    /**
     * - Gracefully exit the application by calling the app's exit method<br>
     * - In app, need to make sure to close connections, mainly the input hooks
     */
    @Override
    public void run() {
        PageRankApp.getInstance().exit();
    }

}
