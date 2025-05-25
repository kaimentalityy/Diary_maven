package server.utils.exception.notfound;

public class GradeCustomNotFoundException extends CustomNotFoundException {
    public GradeCustomNotFoundException(Object id) {
        super("Grade with Id: " + id + " not found.");
    }
}
