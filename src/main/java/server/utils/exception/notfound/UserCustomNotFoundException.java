package server.utils.exception.notfound;

public class UserCustomNotFoundException extends CustomNotFoundException {
    public UserCustomNotFoundException(Object id) {
        super("User with Id: " + id + " not found.");
    }
}
