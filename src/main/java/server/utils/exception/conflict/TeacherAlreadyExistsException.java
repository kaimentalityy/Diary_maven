package server.utils.exception.conflict;

public class TeacherAlreadyExistsException extends ConflictException {
    public TeacherAlreadyExistsException(Object id) {
        super("Teacher with Id: " + id + " already exists.");
    }
}
