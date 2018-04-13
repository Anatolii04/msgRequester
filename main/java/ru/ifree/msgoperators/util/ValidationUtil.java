package ru.ifree.msgoperators.util;



import ru.ifree.msgoperators.model.Connector;
import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.util.exception.NotFoundException;


public class ValidationUtil {
    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNew(Connector connector) {
        if (!connector.isNew()) {
            throw new IllegalArgumentException(connector + " must be new (id=null)");
        }
    }

    public static void checkIdConsistent(Connector connector, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (connector.isNew()) {
            connector.setId(id);
        } else if (connector.getId() != id) {
            throw new IllegalArgumentException(connector + " must be with id=" + id);
        }
    }

    public static void checkNew(Contact contact) {
        if (!contact.isNew()) {
            throw new IllegalArgumentException(contact + " must be new (id=null)");
        }
    }

    public static void checkIdConsistent(Contact contact, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (contact.isNew()) {
            contact.setId(id);
        } else if (contact.getId() != id) {
            throw new IllegalArgumentException(contact + " must be with id=" + id);
        }
    }
}
