package controller.actions.title;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import controller.actions.PageRankApp;
import model.user.User;
import view.menu.MainMenuFactory;

public class LoginSubmitAction implements Runnable {
    private final PageRankApp app = PageRankApp.getInstance();
    private final MultiWindowTextGUI gui = app.guiManager.getGUI();
    private final TextBox usernameBox;
    private final TextBox passwordBox;


    public LoginSubmitAction(TextBox usernameBox, TextBox passwordBox) {
        this.usernameBox = usernameBox;
        this.passwordBox = passwordBox;
    }

    @Override
    public void run() {
        String username = usernameBox.getText();
        String password = passwordBox.getText();

        // If either field is empty, do nothing
        if (username.isEmpty() || password.isEmpty()) {
            return;
        }

        try {
            app.logInUser(username, password);
        } catch (NoSuchUserException e) {
            MessageDialog.showMessageDialog(
                    gui,
                    "Login Failed",
                    "No user with that username. Please create an account or try again.",
                    MessageDialogButton.OK
            );
            return;
        } catch (IncorrectPasswordException e) {
            MessageDialog.showMessageDialog(
                    gui,
                    "Login Failed",
                    "Incorrect password. Please try again.",
                    MessageDialogButton.OK
            );
            return;
        }

        // Login was successful
        // transition was taken care of by logInUser method

    }
}
