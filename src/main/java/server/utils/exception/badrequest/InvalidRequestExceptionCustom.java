package server.utils.exception.badrequest;

public class InvalidRequestExceptionCustom extends CustomBadRequestException {
    public InvalidRequestExceptionCustom(String message) {
        super(message);
    }
}
