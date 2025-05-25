package server.utils.exception.notfound;

public class SchoolClassCustomNotFoundException extends CustomNotFoundException {
    public SchoolClassCustomNotFoundException(Object id) {
        super("Class with Id: " + id + " not found.");
    }
}
