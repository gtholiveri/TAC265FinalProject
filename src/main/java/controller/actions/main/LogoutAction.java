package controller.actions.main;

import controller.actions.PageRankApp;
import model.persistence.UserDatabaseManager;
import view.menu.TitleMenuFactory;

public class LogoutAction implements Runnable {

    @Override
    public void run() {
        PageRankApp.getInstance().transitionTo(TitleMenuFactory.create());
    }
}
