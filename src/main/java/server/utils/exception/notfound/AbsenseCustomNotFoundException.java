package server.utils.exception.notfound;

public class AbsenseCustomNotFoundException extends CustomNotFoundException {
    public AbsenseCustomNotFoundException(Object id) {
        super("Absense with ID " + id + " not found.");
    }
}
