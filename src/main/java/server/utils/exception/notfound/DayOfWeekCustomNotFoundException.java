package server.utils.exception.notfound;

public class DayOfWeekCustomNotFoundException extends CustomNotFoundException {
    public DayOfWeekCustomNotFoundException(Object id) {
        super("DayOfWeek not found for id: " + id);
    }
}
