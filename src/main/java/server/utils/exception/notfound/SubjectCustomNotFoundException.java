package server.utils.exception.notfound;

public class SubjectCustomNotFoundException extends CustomNotFoundException {
    public SubjectCustomNotFoundException(Object id) {
        super("Subject with Id: " + id + " not found.");
    }
}
