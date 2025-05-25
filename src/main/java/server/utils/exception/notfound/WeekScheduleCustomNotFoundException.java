package server.utils.exception.notfound;

public class WeekScheduleCustomNotFoundException extends CustomNotFoundException {
    public WeekScheduleCustomNotFoundException(Object id) {
        super("Week Schedule with Id: " + id + " not found.");
    }
}
