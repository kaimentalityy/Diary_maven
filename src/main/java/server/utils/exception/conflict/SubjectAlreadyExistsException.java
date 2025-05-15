package server.utils.exception.conflict;

public class SubjectAlreadyExistsException extends ConflictException {
    public SubjectAlreadyExistsException(Object id) {
        super(id + " already exists.");
    }
}
