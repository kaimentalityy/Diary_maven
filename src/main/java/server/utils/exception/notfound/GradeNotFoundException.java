package server.utils.exception.notfound;

public class GradeNotFoundException extends NotFoundException {
    public GradeNotFoundException(Object id) {
        super("Grade with Id: " + id + " not found.");
    }
}
