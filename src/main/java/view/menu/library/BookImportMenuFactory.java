package view.menu.library;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.FileDialogBuilder;
import controller.PageRankApp;
import view.menu.PopupFactory;
import view.menu.TextBoxListWindowBuilder;

import java.io.File;
import java.io.FileNotFoundException;

public class BookImportMenuFactory {

    public static Window create() {
        PageRankApp app = PageRankApp.getInstance();

        TextBoxListWindowBuilder b = new TextBoxListWindowBuilder("Import Book");
        b.addTextBox("Book Name:", 30, false);
        b.addButton("Choose File", () -> {
            TextBox bookNameBox = b.getTextBox(0);
            String bookName = bookNameBox.getText();

            // Validate book name is not empty
            if (bookName == null || bookName.trim().isEmpty()) {
                PopupFactory.showPopup("Error", "Please enter a book name.");
                return;
            }

            // Show file dialog
            File selectedFile = new FileDialogBuilder()
                    .setTitle("Choose a Text File")
                    .setDescription("Select a .txt file to import")
                    .setActionLabel("Import")
                    .build()
                    .showDialog(app.guiManager.getGUI());

            // If user selected a file, attempt to import it
            if (selectedFile != null) {
                try {
                    // Add book to current user and save
                    app.getCurrentUser().addBook(bookName.trim(), selectedFile);
                    app.save();

                    // Show success message
                    PopupFactory.showPopup("Success", "Book '" + bookName + "' imported successfully!");

                    // Go back to library menu
                    app.transitionTo(LibraryMenuFactory.create());

                } catch (FileNotFoundException e) {
                    PopupFactory.showPopup("Error", "File not found, please try again");

                } catch (IllegalArgumentException e) {
                    PopupFactory.showPopup("Error", e.getMessage());
                }
            }
        });
        b.addButton("Back", () -> {
            app.transitionTo(LibraryMenuFactory.create());
        });

        return b.build();
    }
}
