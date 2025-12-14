package model.user;

import model.Book;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents a reading group
 */
public class Group implements Serializable {
    private String name;
    private User adminUser;
    private Book currentBook;
    private Map<String, User> members;

    /**
     *
     * @param name The name of the group
     * @param adminUser The User variable pointing to the admin
     * @param book The Book that the group is going to be reading
     * @param users
     */
    public Group(String name, User adminUser, Book book, Map<String, User> users) {
        this(name, adminUser, book);
        members.putAll(users);
    }

    public Group(String name, User adminUser, Book book) {
        this.name = name;
        this.adminUser = adminUser;
        this.members = new HashMap<>();
        this.members.put(adminUser.getUsername(), adminUser);
        this.currentBook = book;
    }

    public String getName() {
        return name;
    }

    public User getAdminUser() {
        return adminUser;
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
    }

    public List<User> getMembers() {

        return new ArrayList<>(members.values());
    }

    public void addMember(User user) {
        members.put(user.getUsername(), user);
    }

    public void removeMember(String username) {
        members.remove(username);
    }

    public boolean isMember(String username) {
        return members.containsKey(username);
    }

    public boolean isAdmin(User username) {
        return adminUser.equals(username);
    }
}
