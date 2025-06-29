package server.utils.exception.conflict;

public class AbsenseAlreadyExistsExceptionCustom extends CustomConflictException {
    public AbsenseAlreadyExistsExceptionCustom(Object id) {
        super("Attendance with Id: " + id + " already exists.");
    }
}
