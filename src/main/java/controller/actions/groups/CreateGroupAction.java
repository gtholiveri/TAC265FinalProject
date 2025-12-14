package controller.actions.groups;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialog;
import controller.PageRankApp;
import model.user.Group;
import view.menu.PopupFactory;
import view.menu.groups.GroupCreationMenuFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Action for when the user wants to create a group
 */
public class CreateGroupAction implements Runnable {

    /**
     * Transitions the gui to a new group creation menu
     */
    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();
        app.transitionTo(new GroupCreationMenuFactory().create());
    }
}
