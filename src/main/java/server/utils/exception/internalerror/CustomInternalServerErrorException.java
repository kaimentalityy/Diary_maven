package server.utils.exception.internalerror;


public abstract class CustomInternalServerErrorException extends RuntimeException {
    public CustomInternalServerErrorException(String message) {super(message);}
}
