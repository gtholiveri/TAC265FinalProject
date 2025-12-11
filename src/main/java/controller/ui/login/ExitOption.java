package controller.ui.login;

import controller.ui.MenuOption;
import controller.ui.PageRankApp;
import view.Printer;

public class ExitOption extends MenuOption {
    public ExitOption(PageRankApp app) {
        super(app, "Exit application");
    }

    /**
     * - Gracefully exit the application by calling the app's exit method<br>
     * - In app, need to make sure to close connections, mainly the input hooks
     */
    @Override
    public void fire() {
        app.exit();
    }

}
