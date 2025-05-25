package server.utils.exception.conflict;

public class LessonAlreadyExistsExceptionCustom extends CustomConflictException {
    public LessonAlreadyExistsExceptionCustom(Object id) {
        super("Lesson with Id: " + id + " already exists.");
    }
}
