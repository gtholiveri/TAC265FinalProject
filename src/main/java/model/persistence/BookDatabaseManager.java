package model.persistence;

import model.Book;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * This is a hefty one. Manages probably the most complex I/O operations in the program, those dealing with books
 */
public class BookDatabaseManager {
    private static final String BOOK_TEXT_DIR = "data/books/text";
    private static final String BOOK_OBJECT_DIR = "data/books/obj";
    private static final String BOOK_DB_FILE = BOOK_OBJECT_DIR + "/books.ser";


    /**
     * Basically a convenience method for saving a single new book<br>
     * Loads in the map, puts in the new one, saves it back
     */
    public static void saveBook(Book b) {
        Map<String, Book> books = loadBooks();
        books.put(b.getTitle(), b);
        saveBooks(books);
    }

    /**
     * @return true if a Book with the given title exists in the map, false otherwise
     */
    public static boolean bookExists(String title) {
        return loadBooks().containsKey(title);
    }

    /**
     * Basically how the importing works: We take a title and a .txt file, we copy its contents over to some new .txt file
     * with a unique name, and then we construct and return the title and the path to the now safely-tucked-away text as a Book object
     *
     * @param title          the title of the book we're importing
     * @param importPathname the pathname of the source file with the text we want to import as a book (gotta be .txt)
     * @return a Book object containing newly imported book
     * @throws FileNotFoundException    if the file at the given importPathname does not exist
     * @throws IllegalArgumentException if the file at the given importPathname isn't a txt
     * @throws RuntimeException         if some other terrible io error happens
     */
    public static Book importBook(String title, String importPathname) throws FileNotFoundException {
        assertValidImportPathname(importPathname);

        Path path = Paths.get(importPathname);

        // set up the folders if they're not there for some reason
        // just to be safe
        try {
            Files.createDirectories(Paths.get(BOOK_TEXT_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create books directory", e);
        }

        // create a unique filename for the newly imported book
        // UUID is cool, it's like a hash function so it relies on
        // astronomically small collision probability to "guarantee" that
        // the name is unique
        String uniqueFilename = UUID.randomUUID().toString() + ".txt";
        // the file we're now going to slot our text into
        String persistentPathname = BOOK_TEXT_DIR + "/" + uniqueFilename;
        Path persistentPath = Paths.get(persistentPathname);

        try {
            // copy ts over
            Files.copy(path, persistentPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy file", e);
        }

        Book newBook = new Book(persistentPathname, title);
        // important: actually save it to the .ser
        saveBook(newBook);

        return newBook;
    }

    //<editor-fold> desc="Checking Validity"


    /**
     * Checks that a particular pathname to a file we are *importing* a book from is valid
     *
     * @throws FileNotFoundException If the file isn't valid, ie it doesn't exist OR it's not a txt
     */
    public static void assertValidImportPathname(String importPathname) throws FileNotFoundException {
        Path path = Paths.get(importPathname);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("File does not exist: " + importPathname);
        }

        if (!importPathname.endsWith(".txt")) {
            throw new IllegalArgumentException("File must be a .txt file: " + importPathname);
        }

    }

    /**
     * Checks that a pathname to a file we are loading *from persistence* is valid
     *
     * @throws IllegalArgumentException If the file isn't valid ie it isn't a txt, it isn't in the right data directory, or it doesn't exist
     */
    public static void assertValidLoadPathname(String loadPathname) throws IllegalArgumentException {
        if (!loadPathname.endsWith(".txt")) {
            throw new IllegalArgumentException("File must be a .txt file: " + loadPathname);
        }

        if (!loadPathname.startsWith(BOOK_TEXT_DIR)) {
            throw new IllegalArgumentException("File must be in the books directory: " + loadPathname);
        }

        if (!Files.exists(Paths.get(loadPathname))) {
            throw new IllegalArgumentException("File does not exist: " + loadPathname);
        }

    }
    //</editor-fold>

    /**
     * Saves all the books in the provided map to persistence
     */
    private static void saveBooks(Map<String, Book> books) {
        // Make sure the directory exists
        try {
            Files.createDirectories(Paths.get(BOOK_OBJECT_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create books obj directory", e);
        }

        // Serialize ts
        try (FileOutputStream fs = new FileOutputStream(BOOK_DB_FILE);
             ObjectOutputStream os = new ObjectOutputStream(fs)) {
            os.writeObject(books);
        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not find file " + BOOK_DB_FILE);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Error: Could not write to file " + BOOK_DB_FILE + ". Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * @return The map with all of the books deserialized from persistence
     */
    private static Map<String, Book> loadBooks() {
        Map<String, Book> books = new HashMap<>();
        try (FileInputStream fs = new FileInputStream(BOOK_DB_FILE); ObjectInputStream is = new ObjectInputStream(fs)) {
            Object obj = is.readObject();
            if (obj instanceof Map) {
                //noinspection unchecked
                books = (Map<String, Book>) obj;
            }
        } catch (FileNotFoundException e) {
            // No file yet — return empty map
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return books;

    }


}
