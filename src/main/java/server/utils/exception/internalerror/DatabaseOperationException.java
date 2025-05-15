package server.utils.exception.internalerror;

public class DatabaseOperationException extends InternalServerErrorException {
    public DatabaseOperationException(String message) {
        super(message);
    }
    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
