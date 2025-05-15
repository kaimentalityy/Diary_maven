package server.utils.exception.notfound;

public class AbsenseNotFoundException extends NotFoundException {
    public AbsenseNotFoundException(Object id) {
        super("Absense with ID " + id + " not found.");
    }
}
