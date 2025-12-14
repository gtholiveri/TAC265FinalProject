package controller.actions.groups;

import controller.PageRankApp;
import view.menu.groups.GroupCreationMenuFactory;

/**
 * Action for when the user wants to create a group (opens the create group menu)
 */
public class OpenCreateGroupAction implements Runnable {

    /**
     * Transitions the gui to a new group creation menu
     */
    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();
        app.transitionTo(new GroupCreationMenuFactory().create());
    }
}
