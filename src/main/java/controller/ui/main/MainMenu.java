package controller.ui.main;

import controller.ui.Menu;
import controller.ui.PageRankApp;

public class MainMenu extends Menu {


    public MainMenu(PageRankApp app) {
        super(app, "Main Menu");
    }

    @Override
    public String getLabel() {
        return label + " — Logged in as " + app.getCurrentUser().getUsername();
    }

    @Override
    protected void addOptions() {
//        options.add(...);
    }
}
