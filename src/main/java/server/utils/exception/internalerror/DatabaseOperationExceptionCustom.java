package server.utils.exception.internalerror;

public class DatabaseOperationExceptionCustom extends CustomInternalServerErrorException {
    public DatabaseOperationExceptionCustom(String message) {
        super(message);
    }
}
