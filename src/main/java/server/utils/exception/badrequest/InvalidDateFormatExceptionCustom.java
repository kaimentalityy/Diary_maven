package server.utils.exception.badrequest;

public class InvalidDateFormatExceptionCustom extends CustomBadRequestException {
    public InvalidDateFormatExceptionCustom(String message) {
        super(message);
    }
}
