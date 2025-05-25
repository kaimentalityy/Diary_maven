package server.utils.exception.badrequest;

public class ConstraintViolationExceptionCustom extends CustomBadRequestException {
    public ConstraintViolationExceptionCustom(String message) {
        super(message);
    }
}