package server.utils.exception.badrequest;

public class InvalidGradeValueExceptionCustom extends CustomBadRequestException {
    public InvalidGradeValueExceptionCustom(String message) {
        super(message);
    }
}
