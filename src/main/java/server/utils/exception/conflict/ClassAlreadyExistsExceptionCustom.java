package server.utils.exception.conflict;

public class ClassAlreadyExistsExceptionCustom extends CustomConflictException {
    public ClassAlreadyExistsExceptionCustom(Object id) {
        super("Class with Id: " + id + " already exists.");
    }
}
