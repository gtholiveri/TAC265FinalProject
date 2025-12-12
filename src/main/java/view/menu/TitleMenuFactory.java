package view.menu;

import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialog;
import com.googlecode.lanterna.gui2.dialogs.ActionListDialogBuilder;
import controller.actions.title.CreateAccountAction;
import controller.actions.title.ExitAction;
import controller.actions.title.LoginAction;

public class TitleMenuFactory {

    public static Window create() {
        String title = "Welcome to PageRank!";
        String subtitle = "Log in or create an account to continue.";
        OptionListWindowBuilder b = new OptionListWindowBuilder(title, subtitle);

        b.addAction("Log In", new LoginAction());
        b.addAction("Create New Account", new CreateAccountAction());
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
