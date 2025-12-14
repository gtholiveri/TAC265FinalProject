package view.menu;

import com.googlecode.lanterna.gui2.Window;
import controller.PageRankApp;
import view.menu.groups.GroupsMenuFactory;
import view.menu.library.LibraryMenuFactory;
import view.menu.title.TitleMenuFactory;

public class MainMenuFactory {


    public static Window create() {
        PageRankApp app = PageRankApp.getInstance();

        String title = "Main Menu — Logged in as " + app.getCurrentUser().getUsername();
        OptionListWindowBuilder b = new OptionListWindowBuilder(title);

        b.addAction("Library", () -> {
            app.transitionTo(LibraryMenuFactory.create());
        });

        b.addAction("Groups", () -> {
            app.transitionTo(GroupsMenuFactory.create());
        });

        b.addAction("Account", () -> {
            PopupFactory.showPopup("Account", "Account management to be implemented in version 2!");
        });

        b.addAction("Logout", () -> {
            app.transitionTo(TitleMenuFactory.create());
        });

        return b.build();
    }

}
