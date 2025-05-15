package server.utils.exception.notfound;

public class WeekScheduleNotFoundException extends NotFoundException {
    public WeekScheduleNotFoundException(Object id) {
        super("Week Schedule with Id: " + id + " not found.");
    }
}
