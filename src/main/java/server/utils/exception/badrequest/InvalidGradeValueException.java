package server.utils.exception.badrequest;

public class InvalidGradeValueException extends BadRequestException {
    public InvalidGradeValueException(String message) {
        super(message);
    }
}
