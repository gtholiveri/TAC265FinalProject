package view.menu.library;

import com.googlecode.lanterna.gui2.Window;
import controller.PageRankApp;
import model.Book;
import model.user.User;
import view.menu.MainMenuFactory;
import view.menu.OptionListWindowBuilder;

import java.util.List;

/**
 * Factory class for the library menu window
 */
public class LibraryMenuFactory {

    public static Window create() {
        // snag our various fields that we want easy access to
        PageRankApp app = PageRankApp.getInstance();
        User currUser = app.getCurrentUser();
        List<Book> books = currUser.getBooks();

        String title = "Library";
        // user our custom option list window builder
        // to add an option with the right action (open a reader for the
        // given book) for each book
        OptionListWindowBuilder b = new OptionListWindowBuilder(title);
        for (Book book : books) {
            b.addAction(book.getTitle(), () -> {
                app.transitionTo(ReaderMenuFactory.create(book));
            });
        }

        b.addAction("Import Book", () -> {
            app.transitionTo(BookImportMenuFactory.create());
        });

        b.addAction("Back", () -> {
            app.transitionTo(MainMenuFactory.create());
        });

        return b.build();
    }
}
