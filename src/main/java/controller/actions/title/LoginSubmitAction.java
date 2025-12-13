package controller.actions.title;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import controller.exceptions.IncorrectPasswordException;
import controller.exceptions.NoSuchUserException;
import controller.PageRankApp;

public class LoginSubmitAction implements Runnable {
    private final TextBox usernameBox;
    private final TextBox passwordBox;


    /**
     * It's vital that we pass in the boxes here rather than the result of calling getText() directly
     * because if we pass in the result, we're calling the method at window creation time and
     * the result will always be empty
     */
    public LoginSubmitAction(TextBox usernameBox, TextBox passwordBox) {
        this.usernameBox = usernameBox;
        this.passwordBox = passwordBox;
    }

    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();
        MultiWindowTextGUI gui = app.guiManager.getGUI();


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
        } catch (IncorrectPasswordException e) {
            MessageDialog.showMessageDialog(
                    gui,
                    "Login Failed",
                    "Incorrect password. Please try again.",
                    MessageDialogButton.OK
            );
        }

        // Login was successful
        // transition was taken care of by logInUser method

    }
}
