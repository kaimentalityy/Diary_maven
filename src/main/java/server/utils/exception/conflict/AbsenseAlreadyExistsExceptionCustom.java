package server.utils.exception.conflict;

public class AbsenseAlreadyExistsExceptionCustom extends CustomConflictException {
    public AbsenseAlreadyExistsExceptionCustom(Object id) {
        super("Absense with Id: " + id + " already exists.");
    }
}
