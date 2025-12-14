package controller.actions.library;

import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import controller.PageRankApp;
import view.menu.PopupFactory;
import view.menu.library.LibraryMenuFactory;

import java.io.File;
import java.io.FileNotFoundException;

public class ChooseBookFileAction implements Runnable {
    private final TextBox bookNameBox;

    /**
     * @param bookNameBox The TextBox into which the user is inputting their book title<br>
     *                    It's necessary to input the box and not the result of getText() since we *have* to be calling getText() only *after* the button actually fires, otherwise we're calling at instantiation and always get blank
     */
    public ChooseBookFileAction(TextBox bookNameBox) {
        this.bookNameBox = bookNameBox;
    }

    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();
        String bookName = bookNameBox.getText();

        // empty title: popup and return (so it does nothing) and
        // plops us back into the prev menu (in this case book import menu)
        if (bookName.trim().isEmpty()) {
            PopupFactory.showPopup("Error", "Please enter a book name.");
            return;
        }

        // Use FileDialogBuilder() to get a pretty file dialog
        // and capture user selection from it
        File selectedFile = new FileDialogBuilder()
                .setTitle("Choose a Text File")
                .setDescription("Select a .txt file to import")
                .setActionLabel("Import")
                .build()
                .showDialog(app.guiManager.getGUI());

        // We have to null check because they might've hit cancel
        if (selectedFile != null) {
            try {
                // Add book to current user and save
                app.getCurrentUser().addBook(bookName.trim(), selectedFile);
                app.save();

                // No exceptions chucked us out so we succeeded
                PopupFactory.showPopup("Success", "Book '" + bookName + "' imported successfully!");

                // Go back to library menu
                app.transitionTo(LibraryMenuFactory.create());

                // we chose to have BookDatabaseManager actually handle the
                // detection of malformed inputs here, and then have this actual action
                // decide what to do with them user-facing wise (felt a little more separated-concerns-y)
            } catch (FileNotFoundException e) {
                // this can happen if the field is left blank
                PopupFactory.showPopup("Error", "File not found, please try again");

            } catch (IllegalArgumentException e) {
                // this happens whenever it's not a txt
                PopupFactory.showPopup("Error", e.getMessage());
            }
        }
    }
}
