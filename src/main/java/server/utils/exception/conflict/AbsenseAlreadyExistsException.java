package server.utils.exception.conflict;

public class AbsenseAlreadyExistsException  extends ConflictException {
    public  AbsenseAlreadyExistsException(Object id) {
        super("Absense with Id: " + id + " already exists.");
    }
}
