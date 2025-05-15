package server.utils.exception.notfound;

public class SchoolClassNotFoundException extends NotFoundException {
    public SchoolClassNotFoundException(Object id) {
        super("Class with Id: " + id + " not found.");
    }
}
