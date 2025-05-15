package server.utils.exception.notfound;

public class DayOfWeekNotFoundException extends NotFoundException {
    public DayOfWeekNotFoundException(Object id) {
        super("DayOfWeek not found for id: " + id);
    }
}
