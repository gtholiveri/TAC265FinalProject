package view.menu.groups;

import com.googlecode.lanterna.gui2.*;
import controller.PageRankApp;
import controller.actions.groups.AddUserToGroupAction;
import controller.actions.groups.LeaveGroupAction;
import controller.actions.groups.OpenGroupReaderAction;
import model.Book;
import model.user.Group;
import model.user.User;
import view.menu.PopupFactory;

import java.util.Arrays;

/**
 * Factory class for the group detail menu
 */
public class GroupDetailMenuFactory {

    /**
     * @param group The group that we're actually displaying
     * @return The nicely laid out window
     */
    public static Window create(Group group) {
        PageRankApp app = PageRankApp.getInstance();

        BasicWindow window = new BasicWindow(group.getName());
        window.setHints(Arrays.asList(Window.Hint.EXPANDED));

        Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));

        // Display group information
        mainPanel.addComponent(new Label("Group: " + group.getName()));
        mainPanel.addComponent(new EmptySpace());


        // Display current book
        Book bookDisplay = group.getCurrentBook();
        mainPanel.addComponent(new Label("Current Book: " + bookDisplay));
        mainPanel.addComponent(new EmptySpace());

        // Display members
        mainPanel.addComponent(new Label("Members:"));
        for (User user : group.getMembers()) {
            String username = user.getUsername();
            if (group.isAdmin(user)) {
                username += " (Admin)";
            }
            mainPanel.addComponent(new Label("  - " + username));
        }
        mainPanel.addComponent(new EmptySpace());

        // Action buttons
        Panel buttonPanel = new Panel(new LinearLayout(Direction.VERTICAL));

        // View Progress button (stub)
        Button viewProgressButton = new Button("View Progress", () -> {
            PopupFactory.showPopup("Coming Soon", "Competitive Progress Tracking to be Implemented in Version 2!");
        });
        buttonPanel.addComponent(viewProgressButton);

        // Add User button (admin only)
        if (group.isAdmin(app.getCurrentUser())) {
            Button addUserButton = new Button("Add User", new AddUserToGroupAction(group));
            buttonPanel.addComponent(addUserButton);
        }

        // Read book button
        Button readBookButton = new Button("Read Book", new OpenGroupReaderAction(group));
        buttonPanel.addComponent(readBookButton);

        // Leave group button
        Button leaveGroupButton = new Button("Leave Group", new LeaveGroupAction(group));
        buttonPanel.addComponent(leaveGroupButton);

        // Back button
        Button backButton = new Button("Back", () -> {
            app.transitionTo(GroupsMenuFactory.create());
        });
        buttonPanel.addComponent(backButton);

        mainPanel.addComponent(buttonPanel);

        window.setComponent(mainPanel);
        return window;
    }
}
