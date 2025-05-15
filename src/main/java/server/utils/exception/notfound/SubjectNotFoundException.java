package server.utils.exception.notfound;

public class SubjectNotFoundException extends NotFoundException {
    public SubjectNotFoundException(Object id) {
        super("Subject with Id: " + id + " not found.");
    }
}
