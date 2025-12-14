package controller.actions.title;

import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import controller.PageRankApp;
import controller.exceptions.NameTakenException;
import model.user.User;

public class SubmitCreateAccountAction implements Runnable {
    TextBox usernameBox;
    TextBox passwordBox;

    public SubmitCreateAccountAction(TextBox usernameBox, TextBox passwordBox) {
        this.usernameBox = usernameBox;
        this.passwordBox = passwordBox;
    }

    /**
     * - Clears the screen<br>
     * - Prompts the user for a new username<br>
     * - Validates that the username is not already taken<br>
     * - If it's already taken, display a message, stall, and ask for another username<br>
     * - Otherwise, prompt the user for a password<br>
     * - Display success message<br>
     * - Save the new user to the database<br>
     * - Transition to the main menu
     */
    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();

        // TODO: implement create account run() method
        String username = usernameBox.getText();
        String password = passwordBox.getText();

        if (username.isEmpty() || password.isEmpty()) {
            return;
        }

        User newUser = new User(username, password);

        try {
            app.addUser(newUser);
        } catch (NameTakenException e) {
            MessageDialog.showMessageDialog(
                    app.guiManager.getGUI(),
                    "Create Account Failed",
                    "That username is taken. Try entering another one.",
                    MessageDialogButton.OK
            );
            return;
        }
    }
}
