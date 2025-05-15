package server.utils.exception.badrequest;

public class InvalidColumnNameException extends BadRequestException {
    public InvalidColumnNameException(String message) {
        super(message);
    }
}
