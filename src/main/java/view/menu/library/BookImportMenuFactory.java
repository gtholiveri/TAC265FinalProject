package view.menu.library;

import com.googlecode.lanterna.gui2.*;
import controller.PageRankApp;
import controller.actions.library.ChooseBookFileAction;
import view.menu.TextBoxListWindowBuilder;

/**
 * Factory for the book import form window
 */
public class BookImportMenuFactory {

    public static Window create() {
        PageRankApp app = PageRankApp.getInstance();

        TextBoxListWindowBuilder b = new TextBoxListWindowBuilder("Import Book");
        b.addTextBox("Book Name:", 30, false);
        b.addButton("Choose File", new ChooseBookFileAction(b.getTextBox(0)));
        b.addButton("Back", () -> {
            app.transitionTo(LibraryMenuFactory.create());
        });

        return b.build();
    }
}
