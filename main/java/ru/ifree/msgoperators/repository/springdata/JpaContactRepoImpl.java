package ru.ifree.msgoperators.repository.springdata;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.repository.ContactRepository;

import java.util.List;

@Repository
public class JpaContactRepoImpl implements ContactRepository{

    @Autowired
    private CrudContactRepo crudContactRepo;

    @Autowired
    private CrudConnectorRepo crudConnectorRepo;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Contact save(Contact contact,int connectorId) {
        if (!contact.isNew() && get(contact.getId(), connectorId) == null) {
            return null;
        }
        contact.setConnector(crudConnectorRepo.getOne(connectorId));
        return crudContactRepo.save(contact);

    }

    @Override
    public boolean delete(int id,int connectorId) {
        return crudContactRepo.delete(id,connectorId)!=0;
    }

    @Override
    public Contact get(int id,int connectorId) {
        Contact contact = crudContactRepo.findOne(id);
        return contact != null && contact.getConnector().getId()==connectorId ? contact : null;
    }

    @Override
    public List<Contact> getAll(int connectorId,boolean isCustom) {
        return crudContactRepo.getAll(connectorId,isCustom);
    }

    @Override
    public List<Contact> getWhole(boolean isCustom){
        return crudContactRepo.getWhole(isCustom);
    }
}
