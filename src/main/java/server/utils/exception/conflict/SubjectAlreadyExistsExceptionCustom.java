package server.utils.exception.conflict;

public class SubjectAlreadyExistsExceptionCustom extends CustomConflictException {
    public SubjectAlreadyExistsExceptionCustom(Object id) {
        super(id + " already exists.");
    }
}
