package server.utils.exception.notfound;

public class TeacherNotFoundException extends NotFoundException {
    public TeacherNotFoundException(Object id) {
        super("Teacher with Id: " + id + " not found.");
    }
}
