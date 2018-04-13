package ru.ifree.msgoperators.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.service.ContactService;


import java.util.List;

public abstract class AbstractContactController {

    private final ContactService contactService;

    public AbstractContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractContactController.class);


    public List<Contact> getAll(int connectorId,String type){
        Boolean isCustom = type.equalsIgnoreCase("custom");
        LOGGER.info("getAll contacts");
        return contactService.getAll(connectorId,isCustom);
    }

    public Contact get(int id, int connectorId,String type) {
        Boolean isCustom = type.equalsIgnoreCase("custom");
        LOGGER.info("get {}", id);
        return contactService.get(id,connectorId,isCustom);
    }

    public Contact create(Contact contact, int connectorId){
        LOGGER.info("create new contact {},connectorId={}", contact.getContact(),connectorId);
        Contact created;
        if(contact.isNew()){
            created = contactService.save(contact,connectorId);
        }else throw new IllegalArgumentException(contact + "must be new (id=null)");
        return created;
    }

    public void delete(int connectorId, int id,String type) {
        Boolean isCustom = type.equalsIgnoreCase("custom");
        LOGGER.info("delete {}", id);
        contactService.delete(id,connectorId,isCustom);
    }

    public void update( Contact contact, int id, int connectorId) {
        LOGGER.info("update {}", id);
        if(contact.isNew()){
            contact.setId(id);
        }else if (contact.getId()!=id){
            throw new IllegalArgumentException(contact + " must be with id=" + id);
        }
        contactService.update(contact,connectorId);
    }
}
