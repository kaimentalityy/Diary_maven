package server.utils.exception.notfound;

public class LessonNotFoundException extends NotFoundException {
    public LessonNotFoundException(Object id) {
        super("Lesson with Id: " + id + " not found.");
    }
}
