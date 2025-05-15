package server.utils.exception.badrequest;

public class InvalidDateFormatException extends BadRequestException {
    public InvalidDateFormatException(String message) {
        super(message);
    }
}
