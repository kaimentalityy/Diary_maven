package server.utils.exception.conflict;

public class GradeAlreadyExistsException extends ConflictException {
    public GradeAlreadyExistsException(Object id) {
        super("Grade with Id: " + id + " already exists.");
    }
}
