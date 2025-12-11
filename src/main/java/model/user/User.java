package model.user;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private String username;
    private String passwordHash;
//    Library library;
//    List<Group> groups;

    public User(String username, String password) {
        this.username = username;
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
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
}
