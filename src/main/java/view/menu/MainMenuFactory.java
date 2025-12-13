package view.menu;

import com.googlecode.lanterna.gui2.Window;
import controller.PageRankApp;
import controller.actions.main.LogoutAction;

public class MainMenuFactory {


    public static Window create() {
        PageRankApp app = PageRankApp.getInstance();

        String title = "Main Menu — Logged in as " + app.getCurrentUser().getUsername();
        OptionListWindowBuilder b = new OptionListWindowBuilder(title);
        b.addAction("BEING IMPLEMENTED", () -> {});
        b.addAction("Logout", new LogoutAction());

        return b.build();
    }

}
