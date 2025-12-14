package model.user;

import com.googlecode.lanterna.gui2.Window;
import model.Book;
import model.persistence.BookDatabaseManager;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String passwordHash;
    List<Book> books;
    // List<Group> groups;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        books = new ArrayList<>();
    }

    public void addBook(String title, File importFile) throws FileNotFoundException {
        // importBook() handles both the file management and saving of things
        // and the construction of the Book object
        books.add(BookDatabaseManager.importBook(title, importFile.getPath()));
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public boolean updatePassword(String oldPw, String newPw) {
        if (!BCrypt.checkpw(oldPw, passwordHash)) {
            return false;
        }
        passwordHash = BCrypt.hashpw(newPw, BCrypt.gensalt());
        return true;
    }

    public boolean checkPassword(String pw) {
        return BCrypt.checkpw(pw, passwordHash);
    }

    public List<Book> getBooks() {
        return books;
    }
}
