package controller.actions.library;

import controller.PageRankApp;
import model.Book;
import view.menu.library.ReaderMenuFactory;

/**
 * Action for when the user wants to open a book for reading from the library
 */
public class OpenReaderAction implements Runnable {
    private Book book;

    public OpenReaderAction(Book book) {
        this.book = book;
    }

    /**
     * Transitions us to a reader menu for the given book
     */
    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();
        app.transitionTo(ReaderMenuFactory.create(book));
    }

}
