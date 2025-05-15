package server.utils.exception.conflict;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException(Object id) {
        super("User with Id: " + id + " already exists.");
    }
}
