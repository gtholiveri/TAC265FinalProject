package controller.actions.groups;

import com.googlecode.lanterna.gui2.dialogs.ListSelectDialogBuilder;
import controller.PageRankApp;
import model.persistence.GroupDatabaseManager;
import model.persistence.UserDatabaseManager;
import model.user.Group;
import model.user.User;
import view.menu.PopupFactory;
import view.menu.groups.GroupDetailMenuFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Runnable containing action for when an admin user wants to add a new user to a group.
 */
public class AddUserToGroupAction implements Runnable {
    private final Group group;

    /**
     * This constructor lets the caller specify to the Runnable which group exactly it's operating on
     * @param group The group currently in focus, to which the user will be added
     */
    public AddUserToGroupAction(Group group) {
        this.group = group;
    }

    /**
     * Prompts the current (admin) user to choose a single eligible user to add to the group
     */
    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();

        // filter the big list of all the users to get just the ones that
        // aren't already in the group
        List<User> eligibleUsers = new ArrayList<>();
        for (User user : UserDatabaseManager.getAllUsers()) {
            if (!group.isMember(user.getUsername())) {
                eligibleUsers.add(user);
            }
        }

        // no other users in system: error popup
        if (UserDatabaseManager.getAllUsers().size() == 1) {
            PopupFactory.showPopup("No Users", "No other users exist in the system.");
            return;
        }

        // all users already in group (since eligible list empty): error popup
        if (eligibleUsers.isEmpty()) {
            PopupFactory.showPopup("No Users", "All users are already members of this group.");
            return;
        }

        // Use this handy dandy Lanterna builder class to make a nice dialog
        ListSelectDialogBuilder<User> dialogBuilder = new ListSelectDialogBuilder<>();
        dialogBuilder.setTitle("Add User to Group");
            // chucking every user into the dialog
            // these dialogs are why we bother to define redundant-ish toString() methods (discovered them later)
        for (User user : eligibleUsers) {
            dialogBuilder.addListItem(user);
        }

        // show the dialog to the GUI and capture its selection
        User selectedUser = dialogBuilder.build().showDialog(app.guiManager.getGUI());

        // user may have canceled (returns null) so gotta null check
        if (selectedUser != null) {
            // add user to the group
            group.addMember(selectedUser);
            // save that (this is a preexisting group, so calling the static .save() will save the whole map,
            // which includes the mutated group
            GroupDatabaseManager.save();

            // basically just a screen refresh so the new user shows up
            app.transitionTo(GroupDetailMenuFactory.create(group));
        }
    }
}
