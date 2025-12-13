package view.menu;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import controller.actions.PageRankApp;
import controller.actions.title.LoginSubmitAction;
import model.user.User;

import java.util.Arrays;

public class LoginMenuFactory {

    public static Window create() {
        PageRankApp app = PageRankApp.getInstance();

        BasicWindow window = new BasicWindow("Log In");
        window.setHints(Arrays.asList(Window.Hint.EXPANDED));

        Panel mainPanel = new Panel(new LinearLayout(Direction.VERTICAL));

        //<editor-fold> desc="Input Panel"
        // Use a Panel with a GridLayout
        Panel inputPanel = new Panel(new GridLayout(2));

        // username row
        inputPanel.addComponent(new Label("Username:"));
        TextBox usernameBox = new TextBox(new TerminalSize(20, 1));
        inputPanel.addComponent(usernameBox);

        // password row
        inputPanel.addComponent(new Label("Password:"));
        TextBox passwordBox = new TextBox(new TerminalSize(20, 1)).setMask('*');
        inputPanel.addComponent(passwordBox);

        mainPanel.addComponent(inputPanel);
        //</editor-fold>

        //<editor-fold> desc="Button Panel"
        // Button panel, just lay them out vertically
        Panel buttonPanel = new Panel(new LinearLayout(Direction.VERTICAL));

        Button loginButton = new Button("Log In", new LoginSubmitAction(usernameBox, passwordBox));
        buttonPanel.addComponent(loginButton);

        // Back button
        Button backButton = new Button("Back", () -> {
            app.transitionTo(TitleMenuFactory.create());
        });
        buttonPanel.addComponent(backButton);

        mainPanel.addComponent(buttonPanel);
        //</editor-fold>

        window.setComponent(mainPanel);
        return window;
    }
}
