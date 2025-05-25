package server.utils.exception.badrequest;

public class InvalidColumnNameExceptionCustom extends CustomBadRequestException {
    public InvalidColumnNameExceptionCustom(String message) {
        super(message);
    }
}
