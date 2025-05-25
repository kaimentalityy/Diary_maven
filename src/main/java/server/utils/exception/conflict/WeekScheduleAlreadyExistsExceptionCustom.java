package server.utils.exception.conflict;

public class WeekScheduleAlreadyExistsExceptionCustom extends CustomConflictException {
    public WeekScheduleAlreadyExistsExceptionCustom(Object id) {
        super("Week Schedule with Id: " + id + " already exists.");
    }
}
