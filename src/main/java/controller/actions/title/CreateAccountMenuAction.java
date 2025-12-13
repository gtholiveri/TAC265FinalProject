package controller.actions.title;

import controller.PageRankApp;
import view.menu.CreateAccountMenuFactory;

public class CreateAccountMenuAction implements Runnable {


    @Override
    public void run() {
        PageRankApp.getInstance().transitionTo(CreateAccountMenuFactory.create());
    }
}
