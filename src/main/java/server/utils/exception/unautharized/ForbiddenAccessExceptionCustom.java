package server.utils.exception.unautharized;

public class ForbiddenAccessExceptionCustom extends CustomUnauthorizedException {
    public ForbiddenAccessExceptionCustom(String message) {
        super(message);
    }
}
