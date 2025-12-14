package controller.actions.groups;

import com.googlecode.lanterna.gui2.TextBox;
import controller.PageRankApp;
import controller.exceptions.NameTakenException;
import model.Book;
import model.persistence.GroupDatabaseManager;
import view.menu.PopupFactory;
import view.menu.groups.GroupsMenuFactory;

/**
 * Action for when the user hits the "Create Group" button to actually submit the group creation form
 */
public class SubmitCreateGroupAction implements Runnable {
    private TextBox groupNameBox;
    private Book selectedBook;

    /**
     * Pass in the TextBox so we can call getText() at button fire time, not at initialization time.
     * Also pass in the selected book since that's captured by the factory's instance state.
     */
    public SubmitCreateGroupAction(TextBox groupNameBox, Book selectedBook) {
        this.groupNameBox = groupNameBox;
        this.selectedBook = selectedBook;
    }

    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();
        String groupName = groupNameBox.getText().trim();

        // no group name entered: popup
        if (groupName.isEmpty()) {
            PopupFactory.showPopup("Error", "Please enter a group name.");
            return;
        }

        // no book selected: popup
        if (selectedBook == null) {
            PopupFactory.showPopup("Error", "Please select a book.");
            return;
        }

        try {
            GroupDatabaseManager.createGroup(groupName, selectedBook, app.getCurrentUser());
            PopupFactory.showPopup("Success", "Group '" + groupName + "' created successfully!");
            app.transitionTo(GroupsMenuFactory.create());
        } catch (NameTakenException e) {
            PopupFactory.showPopup("Error", "Group name is taken. Please select another.");
        }
    }
}
