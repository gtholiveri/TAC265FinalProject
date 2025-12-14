package controller.exceptions;

/**
 * Exception used when trying to login with a username for which no user exists
 */
public class NoSuchElementException extends RuntimeException {
    public NoSuchElementException(String message) {
        super(message);
    }
}
