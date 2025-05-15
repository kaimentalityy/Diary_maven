package server.utils.exception.unautharized;

public abstract class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
