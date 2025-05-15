package server.utils.exception.badrequest;

public class ConstraintViolationException extends BadRequestException {
    public ConstraintViolationException(String message) {
        super(message);
    }
}