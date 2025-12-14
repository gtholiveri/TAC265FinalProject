package view.menu.title;

import com.googlecode.lanterna.gui2.*;
import controller.PageRankApp;
import controller.actions.title.SubmitLoginAction;
import view.menu.TextBoxListWindowBuilder;

public class LoginMenuFactory {


    public static Window create() {
        PageRankApp app = PageRankApp.getInstance();

        TextBoxListWindowBuilder b = new TextBoxListWindowBuilder("Log In");
        b.addTextBox("Username:", 20, false);
        b.addTextBox("Password:", 20, true);


        TextBox usernameBox = b.getTextBox(0);
        TextBox passwordBox = b.getTextBox(1);

        b.addButton("Log In", new SubmitLoginAction(usernameBox, passwordBox));
        b.addButton("Back", () -> {
            app.transitionTo(TitleMenuFactory.create());
        });

        return b.build();
    }
}
