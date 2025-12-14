package view.menu.library;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import controller.PageRankApp;
import model.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class ReaderMenuFactory {

    public static Window create(Book book) {
        PageRankApp app = PageRankApp.getInstance();
        return create(book, () -> app.transitionTo(LibraryMenuFactory.create()));
    }

    public static Window create(Book book, Runnable backAction) {
        PageRankApp app = PageRankApp.getInstance();

        // Load book text
        String bookText;
        try {
            bookText = Files.readString(Paths.get(book.getPersistentPath()));
        } catch (IOException e) {
            bookText = "Error loading book: " + e.getMessage();
        }

        // Create window
        BasicWindow window = new BasicWindow(book.getTitle());
        window.setHints(Arrays.asList(Window.Hint.EXPANDED));

        // Create panel with vertical layout
        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

        // Create scrollable, read-only text box with the book content
        TextBox textBox = new TextBox(new TerminalSize(80, 20), bookText, TextBox.Style.MULTI_LINE);
        textBox.setReadOnly(true);
        textBox.setVerticalFocusSwitching(false);
        textBox.setHorizontalFocusSwitching(false);
        textBox.setLayoutData(LinearLayout.createLayoutData(
                LinearLayout.Alignment.Fill,
                LinearLayout.GrowPolicy.CanGrow
        ));

        panel.addComponent(textBox);

        // Add Back button
        Button backButton = genBackButton(backAction);
        panel.addComponent(backButton);

        window.setComponent(panel);
        return window;
    }

    public static Button genBackButton() {
        PageRankApp app = PageRankApp.getInstance();
        return genBackButton(() -> app.transitionTo(LibraryMenuFactory.create()));
    }

    public static Button genBackButton(Runnable backAction) {
        return new Button("Back", backAction);
    }
}
