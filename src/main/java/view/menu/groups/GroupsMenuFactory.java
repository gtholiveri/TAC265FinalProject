package view.menu.groups;

import com.googlecode.lanterna.gui2.Window;
import controller.PageRankApp;
import controller.actions.groups.OpenCreateGroupAction;
import controller.actions.groups.ViewGroupsAction;
import view.menu.MainMenuFactory;
import view.menu.OptionListWindowBuilder;

/**
 * Factory class for the top-level groups menu
 */
public class GroupsMenuFactory {


    public static Window create() {
        PageRankApp app = PageRankApp.getInstance();

        String title = "Groups";
        // use the basic option list window builder we made
        OptionListWindowBuilder b = new OptionListWindowBuilder(title);

        b.addAction("Create Group", new OpenCreateGroupAction());

        b.addAction("My Groups", new ViewGroupsAction());

        b.addAction("Back", () -> {
            app.transitionTo(MainMenuFactory.create());
        });

        return b.build();
    }
}
