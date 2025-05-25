package server.utils.exception.unautharized;

public abstract class CustomUnauthorizedException extends RuntimeException {
    public CustomUnauthorizedException(String message) {
        super(message);
    }
}
