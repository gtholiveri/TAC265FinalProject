package controller.actions.main;

import controller.PageRankApp;
import view.menu.title.TitleMenuFactory;

public class LogoutAction implements Runnable {

    @Override
    public void run() {
        PageRankApp.getInstance().transitionTo(TitleMenuFactory.create());
    }
}
