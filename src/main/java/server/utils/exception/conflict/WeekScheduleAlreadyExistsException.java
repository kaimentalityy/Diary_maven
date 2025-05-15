package server.utils.exception.conflict;

public class WeekScheduleAlreadyExistsException  extends ConflictException {
    public  WeekScheduleAlreadyExistsException(Object id) {
        super("Week Schedule with Id: " + id + " already exists.");
    }
}
