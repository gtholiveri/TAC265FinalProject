package view.menu;

import com.googlecode.lanterna.gui2.Window;
import controller.actions.title.CreateAccountMenuAction;
import controller.actions.title.CreateAccountSubmitAction;
import controller.actions.title.ExitAction;
import controller.actions.title.LoginMenuAction;

public class TitleMenuFactory {

    public static Window create() {
        String title = "Welcome to PageRank!";
        String subtitle = "Log in or create an account to continue.";
        OptionListWindowBuilder b = new OptionListWindowBuilder(title, subtitle);

        b.addAction("Log In", new LoginMenuAction());
        b.addAction("Create New Account", new CreateAccountMenuAction());
        b.addAction("Exit", new ExitAction());

        return b.build();
    }
    /*
    @Override
    protected void addOptions() {
        options.add(new LoginAction(app));
        options.add(new CreateAccountOption(app));
        options.add(new ExitAction(app));
    }
    */
}
