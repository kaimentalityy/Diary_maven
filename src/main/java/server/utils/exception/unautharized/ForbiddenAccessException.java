package server.utils.exception.unautharized;

public class ForbiddenAccessException extends UnauthorizedException {
    public ForbiddenAccessException(String message) {
        super(message);
    }
}
