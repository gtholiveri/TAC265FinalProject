package controller.actions.main;

import controller.PageRankApp;
import view.menu.library.LibraryMenuFactory;

public class OpenLibraryAction implements Runnable {


    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();

        app.transitionTo(LibraryMenuFactory.create());
    }

}
