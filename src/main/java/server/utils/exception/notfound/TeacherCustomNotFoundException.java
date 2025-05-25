package server.utils.exception.notfound;

public class TeacherCustomNotFoundException extends CustomNotFoundException {
    public TeacherCustomNotFoundException(Object id) {
        super("Teacher with Id: " + id + " not found.");
    }
}
