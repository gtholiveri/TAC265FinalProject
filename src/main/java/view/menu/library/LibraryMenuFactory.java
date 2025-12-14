package view.menu.library;

import com.googlecode.lanterna.gui2.Window;
import controller.PageRankApp;
import model.Book;
import model.user.User;
import view.menu.MainMenuFactory;
import view.menu.OptionListWindowBuilder;

import java.util.List;

public class LibraryMenuFactory {

    public static Window create() {
        PageRankApp app = PageRankApp.getInstance();
        User currUser = app.getCurrentUser();
        List<Book> books = currUser.getBooks();

        String title = "Library";
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
