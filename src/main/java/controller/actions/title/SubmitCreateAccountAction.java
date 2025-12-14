package controller.actions.title;

import com.googlecode.lanterna.gui2.TextBox;
import controller.PageRankApp;
import controller.exceptions.NameTakenException;
import model.user.User;
import view.menu.PopupFactory;

/**
 * Action for when the user hits the actual "create account" button in the create account menu to ship their entries for user/pw
 */
public class SubmitCreateAccountAction implements Runnable {
    TextBox usernameBox;
    TextBox passwordBox;

    /**
     * Mentioned elsewehere but must pass textboxes so we can call getText() at button fire not button initialization
     */
    public SubmitCreateAccountAction(TextBox usernameBox, TextBox passwordBox) {
        this.usernameBox = usernameBox;
        this.passwordBox = passwordBox;
    }

    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();

        String username = usernameBox.getText();
        String password = passwordBox.getText();

        // if either field is empty just do nothing
        // in this case I feel like this is nicer than a popup
        // also this was written before we even made the popup factory lol
        if (username.isEmpty() || password.isEmpty()) {
            return;
        }


        User newUser = new User(username, password);

        try {
            app.addUser(newUser);
        } catch (NameTakenException e) {
            PopupFactory.showPopup("Create Account Failed", "That username is taken. Try entering another one.");
            // created user without problems
            // in this case the app method itself takes care of the screen transition
        }
    }
}
