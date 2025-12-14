package controller.exceptions;

public class NameTakenException extends RuntimeException {
    public NameTakenException(String message) {
        super(message);
    }
}
