package server.utils.exception.notfound;

public class AbsenseCustomNotFoundException extends CustomNotFoundException {
    public AbsenseCustomNotFoundException(Object id) {
        super("Attendance with ID " + id + " not found.");
    }
}
