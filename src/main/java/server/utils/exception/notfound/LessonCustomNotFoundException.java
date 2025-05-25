package server.utils.exception.notfound;

public class LessonCustomNotFoundException extends CustomNotFoundException {
    public LessonCustomNotFoundException(Object id) {
        super("Lesson with Id: " + id + " not found.");
    }
}
