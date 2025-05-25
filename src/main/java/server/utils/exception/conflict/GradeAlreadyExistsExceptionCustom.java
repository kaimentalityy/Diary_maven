package server.utils.exception.conflict;

public class GradeAlreadyExistsExceptionCustom extends CustomConflictException {
    public GradeAlreadyExistsExceptionCustom(Object id) {
        super("Grade with Id: " + id + " already exists.");
    }
}
