package model;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

import static model.persistence.BookDatabaseManager.assertValidLoadPathname;

public class Book implements Serializable {
    private String persistentPath;
    private String title;
    private int position;
    /**
     * Method ultimately for loading in books saved to the persistent folder
     * Just takes in a filepath, verifies that there is in fact a .txt file
     * with that unique name in the data/books folder, and constructs the object
     * with that persistent path
     */
    public Book(String persistentPathname, String title) {
        Path path = Paths.get(persistentPathname);

        assertValidLoadPathname(persistentPathname);

        this.persistentPath = persistentPathname;
        this.title = title;
        position = 0;
    }


    public String getTitle() {
        return title;
    }

    public String getPersistentPath() {
        return persistentPath;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return title;
    }
}
