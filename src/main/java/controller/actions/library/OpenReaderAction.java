package controller.actions.library;

import controller.PageRankApp;
import model.Book;
import view.menu.library.ReaderMenuFactory;

public class OpenReaderAction implements Runnable {
    private Book book;

    public OpenReaderAction(Book book) {
        this.book = book;
    }

    @Override
    public void run() {
        PageRankApp app = PageRankApp.getInstance();
        app.transitionTo(ReaderMenuFactory.create(book));
    }
}
