package server.utils.exception.notfound;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Object id) {
        super("User with Id: " + id + " not found.");
    }
}
