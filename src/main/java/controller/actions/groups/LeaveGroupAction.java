package controller.actions.groups;

import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import controller.PageRankApp;
import model.persistence.GroupDatabaseManager;
import model.user.Group;
import model.user.User;
import view.menu.PopupFactory;
import view.menu.groups.GroupsMenuFactory;

/**
 * Action for when the user wants to leave a group
 */
public class LeaveGroupAction implements Runnable {
    private final Group group;

    /**
     * @param group The group this action is going to be mutating
     */
    public LeaveGroupAction(Group group) {
        this.group = group;
    }

    /**
     * Removes a user from the group. Important logic is that<br>
     * admins leaving -> delete group<br>
     * last user leavig -> delete group
     */
    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();
        User currentUser = app.getCurrentUser();
        String currentUsername = app.getCurrentUser().getUsername();

        // Confirming action using a MessageDialog
        // MessageDialogButton here is an enum with common button types that we can easily + readably
        // capture and check
        MessageDialogButton result = MessageDialog.showMessageDialog(
                app.guiManager.getGUI(),
                "Leave Group",
                "Are you sure you want to leave " + group.getName() + "?",
                MessageDialogButton.Yes,
                MessageDialogButton.No
        );

        // if they didn't want to, we just exit out of this action and we land back in the previous menu
        // without doing anything
        if (result != MessageDialogButton.Yes) {
            return;
        }

        // Remove the user from the group
        group.removeMember(currentUsername);

        if (group.getMembers().isEmpty()) {
            // group is now empty: delete + popup
            GroupDatabaseManager.removeGroup(group.getName());
            PopupFactory.showPopup("Success", "You left the group. The group has been deleted as it has no members.");
        } else if (group.isAdmin(currentUser)) {
            // the user we just removed was the admin: delete + popup
            GroupDatabaseManager.removeGroup(group.getName());
            PopupFactory.showPopup("Success", "You left the group. The group has been deleted as you were the admin.");
        } else {
            // the user was just a random user that left, so we save the mutated group and update with a popup
            GroupDatabaseManager.save();
            PopupFactory.showPopup("Success", "You have left the group.");
        }

        // Basically a screen refresh
        app.transitionTo(GroupsMenuFactory.create());
    }
}
