package server.utils.exception.conflict;

public class TeacherAlreadyExistsExceptionCustom extends CustomConflictException {
    public TeacherAlreadyExistsExceptionCustom(Object id) {
        super("Teacher with Id: " + id + " already exists.");
    }
}
