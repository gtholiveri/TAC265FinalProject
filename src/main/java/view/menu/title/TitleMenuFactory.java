package view.menu.title;

import com.googlecode.lanterna.gui2.Window;
import controller.PageRankApp;
import view.menu.OptionListWindowBuilder;

public class TitleMenuFactory {

    public static Window create() {
        PageRankApp app = PageRankApp.getInstance();

        String title = "Welcome to PageRank!";
        String subtitle = "Log in or create an account to continue.";
        OptionListWindowBuilder b = new OptionListWindowBuilder(title, subtitle);

        b.addAction("Log In", () -> {
            app.transitionTo(LoginMenuFactory.create());
        });

        b.addAction("Create New Account", () -> {
            app.transitionTo(CreateAccountMenuFactory.create());
        });

        b.addAction("Exit", () -> {
            app.exit();
        });

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
