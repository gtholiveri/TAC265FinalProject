package controller.actions.title;

import controller.PageRankApp;
import view.menu.title.CreateAccountMenuFactory;

/**
 * Action for transitioning to the create account menu
 */
public class OpenCreateAccountAction implements Runnable {


    @Override
    public void run() {
        PageRankApp.getInstance().transitionTo(CreateAccountMenuFactory.create());
    }
}
