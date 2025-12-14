package view.menu.groups;

import com.googlecode.lanterna.gui2.Window;
import controller.PageRankApp;
import controller.actions.groups.CreateGroupAction;
import controller.actions.groups.ViewGroupsAction;
import view.menu.MainMenuFactory;
import view.menu.OptionListWindowBuilder;

public class GroupsMenuFactory {

    public static Window create() {
        PageRankApp app = PageRankApp.getInstance();

        String title = "Groups";
        OptionListWindowBuilder b = new OptionListWindowBuilder(title);

        b.addAction("Create Group", new CreateGroupAction());

        b.addAction("My Groups", new ViewGroupsAction());

        b.addAction("Back", () -> {
            app.transitionTo(MainMenuFactory.create());
        });

        return b.build();
    }
}
