package server.utils.exception.conflict;

public class UserAlreadyExistsExceptionCustom extends CustomConflictException {
    public UserAlreadyExistsExceptionCustom(Object id) {
        super("User with Id: " + id + " already exists.");
    }
}
