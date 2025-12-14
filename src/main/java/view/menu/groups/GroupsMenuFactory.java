package view.menu.groups;

import com.googlecode.lanterna.gui2.Window;
import controller.PageRankApp;
import view.menu.MainMenuFactory;
import view.menu.OptionListWindowBuilder;
import view.menu.PopupFactory;

public class GroupsMenuFactory {

    public static Window create() {
        PageRankApp app = PageRankApp.getInstance();

        String title = "Groups";
        OptionListWindowBuilder b = new OptionListWindowBuilder(title);

        b.addAction("View Invites", () -> {
            PopupFactory.showPopup("View Invites", "TO BE IMPLEMENTED");
        });

        b.addAction("Create Group", () -> {
            PopupFactory.showPopup("Create Group", "TO BE IMPLEMENTED");
        });

        b.addAction("My Groups", () -> {
            PopupFactory.showPopup("My Groups", "TO BE IMPLEMENTED");
        });

        b.addAction("Back", () -> {
            app.transitionTo(MainMenuFactory.create());
        });

        return b.build();
    }
}
