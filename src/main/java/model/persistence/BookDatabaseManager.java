package model.persistence;

import model.Book;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BookDatabaseManager {
    private static final String BOOK_TEXT_DIR = "data/books/text";
    private static final String BOOK_OBJECT_DIR = "data/books/obj";
    private static final String BOOK_DB_FILE = BOOK_OBJECT_DIR + "/books.ser";


    public static void saveBook(Book b) {
        Map<String, Book> books = loadBooks();
        books.put(b.getTitle(), b);
        saveBooks(books);
    }

    public static boolean bookExists(String title) {
        return loadBooks().containsKey(title);
    }

    public static Book getBook(String title) {
        return loadBooks().get(title);
    }

    public static Book importBook(String title, String importPathname) throws FileNotFoundException {
        assertValidImportPathname(importPathname);

        Path path = Paths.get(importPathname);

        try {
            Files.createDirectories(Paths.get(BOOK_TEXT_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create books directory", e);
        }

        // create a unique filename for the newly imported book
        String uniqueFilename = UUID.randomUUID().toString() + ".txt";
        String persistentPathname = BOOK_TEXT_DIR + "/" + uniqueFilename;
        Path persistentPath = Paths.get(persistentPathname);

        try {
            Files.copy(path, persistentPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to copy file", e);
        }

        Book newBook = new Book(persistentPathname, title);
        saveBook(newBook);

        return newBook;
    }

    //<editor-fold> desc="Checking Validity"
    public static String assertValidImportPathname(String importPathname) throws FileNotFoundException {
        Path path = Paths.get(importPathname);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("File does not exist: " + importPathname);
        }

        if (!importPathname.endsWith(".txt")) {
            throw new IllegalArgumentException("File must be a .txt file: " + importPathname);
        }

        return importPathname;
    }

    public static String assertValidLoadPathname(String loadPathname) {
        if (!loadPathname.endsWith(".txt")) {
            throw new IllegalArgumentException("File must be a .txt file: " + loadPathname);
        }

        if (!loadPathname.startsWith(BOOK_TEXT_DIR)) {
            throw new IllegalArgumentException("File must be in the books directory: " + loadPathname);
        }

        if (!Files.exists(Paths.get(loadPathname))) {
            throw new IllegalArgumentException("File does not exist: " + loadPathname);
        }

        return loadPathname;
    }
    //</editor-fold>


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

    private static Map<String, Book> loadBooks() {
        Map<String, Book> books = new HashMap<>();
        try (FileInputStream fs = new FileInputStream(BOOK_DB_FILE);
             ObjectInputStream is = new ObjectInputStream(fs)) {
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
