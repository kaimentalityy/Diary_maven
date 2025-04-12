package server.utils.exception.badrequest;

public class ConstraintViolationException extends Exception {
    public ConstraintViolationException(String message) {
        super(message);
    }
}