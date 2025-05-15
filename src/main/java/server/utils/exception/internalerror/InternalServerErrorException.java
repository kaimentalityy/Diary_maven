package server.utils.exception.internalerror;


import java.sql.SQLException;

public abstract class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message,  Throwable cause) {
        super(message, cause);
    }
    public InternalServerErrorException(String message) {super(message);}
}
