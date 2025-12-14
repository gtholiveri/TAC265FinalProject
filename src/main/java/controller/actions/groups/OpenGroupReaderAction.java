package controller.actions.groups;

import controller.PageRankApp;
import model.user.Group;
import view.menu.PopupFactory;
import view.menu.groups.GroupDetailMenuFactory;
import view.menu.library.ReaderMenuFactory;

/**
 * Action for when someone wants to
 */
public class OpenGroupReaderAction implements Runnable {
    private final Group group;

    public OpenGroupReaderAction(Group group) {
        this.group = group;
    }

    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();

        // Check if the group has a book set
        if (group.getCurrentBook() == null) {
            PopupFactory.showPopup("No Book", "This group does not have a book selected yet.");
            return;
        }

        // Open the reader with the group's book, with back button going to group details
        app.transitionTo(ReaderMenuFactory.create(
                group.getCurrentBook(),
                () -> app.transitionTo(GroupDetailMenuFactory.create(group))
        ));
    }
}
