package controller.actions.main;

import controller.PageRankApp;
import view.menu.title.TitleMenuFactory;

/**
 * Logout action (pretty self-explanatory)
 */
public class LogoutAction implements Runnable {

    /**
     * Transitions us to the title menu<br>
     * Funnily enough not necessary to reset the user to null since to get back in we'll always have to set it to something new
     */
    @Override
    public void run() {
        PageRankApp.getInstance().transitionTo(TitleMenuFactory.create());
    }
}
