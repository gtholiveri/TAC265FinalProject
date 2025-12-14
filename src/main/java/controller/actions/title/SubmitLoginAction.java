package controller.actions.title;

import com.googlecode.lanterna.gui2.TextBox;
import controller.exceptions.IncorrectPasswordException;
import controller.exceptions.NoSuchElementException;
import controller.PageRankApp;
import view.menu.PopupFactory;

/**
 * Action for hitting the actual login button that ships the username and password
 */
public class SubmitLoginAction implements Runnable {
    private final TextBox usernameBox;
    private final TextBox passwordBox;


    /**
     * It's vital that we pass in the boxes here rather than the result of calling getText() directly
     * because if we pass in the result, we're calling the method at window creation time and
     * the result will always be empty
     */
    public SubmitLoginAction(TextBox usernameBox, TextBox passwordBox) {
        this.usernameBox = usernameBox;
        this.passwordBox = passwordBox;
    }

    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();

        String username = usernameBox.getText();
        String password = passwordBox.getText();

        // If either field is empty, just do nothing
        if (username.isEmpty() || password.isEmpty()) {
            return;
        }

        try {
            // this method handles heavy lifting of verification,
            // then we deal with different errors here
            app.logInUser(username, password);
        } catch (NoSuchElementException e) {
            PopupFactory.showPopup("Login Failed", "No user with that username. Please create an account or try again.");
        } catch (IncorrectPasswordException e) {
            PopupFactory.showPopup("Login Failed", "Incorrect password. Please try again.");
        }

        // if we actually logged in, the screen transition was taken care of by logInUser method

    }
}
