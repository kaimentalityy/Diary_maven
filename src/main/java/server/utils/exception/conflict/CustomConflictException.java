package server.utils.exception.conflict;

public abstract class CustomConflictException extends RuntimeException {
    public CustomConflictException(String message) {
        super(message);
    }
}
