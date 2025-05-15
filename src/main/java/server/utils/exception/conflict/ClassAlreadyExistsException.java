package server.utils.exception.conflict;

public class ClassAlreadyExistsException extends ConflictException {
    public ClassAlreadyExistsException(Object id) {
        super("Class with Id: " + id + " already exists.");
    }
}
