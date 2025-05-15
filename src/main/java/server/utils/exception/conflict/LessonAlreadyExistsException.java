package server.utils.exception.conflict;

public class LessonAlreadyExistsException extends ConflictException {
    public LessonAlreadyExistsException(Object id) {
        super("Lesson with Id: " + id + " already exists.");
    }
}
