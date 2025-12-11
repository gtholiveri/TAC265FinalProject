package controller.ui.login;

import controller.ui.Menu;
import controller.ui.PageRankApp;

public class LoginMenu extends Menu {


    public LoginMenu(PageRankApp app) {
        super(app, "Log in or create an account to continue");
    }

    @Override
    protected void addOptions() {
        options.add(new LoginOption(app));
        options.add(new CreateAccountOption(app));
        options.add(new ExitOption(app));
    }
}
