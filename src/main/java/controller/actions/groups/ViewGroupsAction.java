package controller.actions.groups;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import controller.PageRankApp;
import model.persistence.GroupDatabaseManager;
import model.user.Group;
import view.menu.PopupFactory;
import view.menu.groups.GroupDetailMenuFactory;

import java.util.Set;

/**
 * Action for whe a user wants to view their groups. Done as a popup because frankly it's easier than defining yet another menu factory
 */
public class ViewGroupsAction implements Runnable {

    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();
        MultiWindowTextGUI gui = app.guiManager.getGUI();

        // Get all groups that the user is a member of
        Set<Group> userGroups = GroupDatabaseManager.getGroupsForUser(app.getCurrentUser());

        // they have no groups: popup
        if (userGroups.isEmpty()) {
            PopupFactory.showPopup("My Groups", "You are not a member of any groups yet.");
            return;
        }

        // Use the ActionListDialogBuilder again to make a dialog full of
        // button actions that when fired will display the correct group detail menu
        ActionListDialogBuilder builder = new ActionListDialogBuilder()
                .setTitle("My Groups")
                .setDescription("Select a group to view:");

        for (Group group : userGroups) {
            builder.addAction(group.getName(), () -> {
                app.transitionTo(GroupDetailMenuFactory.create(group));
            });
        }


        // build the dialog and pop it on the gui
        builder.build().showDialog(gui);
    }
}
