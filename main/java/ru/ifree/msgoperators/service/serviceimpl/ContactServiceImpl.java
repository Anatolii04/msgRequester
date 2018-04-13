package ru.ifree.msgoperators.service.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.repository.ContactRepository;
import ru.ifree.msgoperators.service.ContactService;


import java.util.List;

import static ru.ifree.msgoperators.util.ValidationUtil.checkNotFoundWithId;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    @Qualifier(value = "jpaContactRepoImpl")
    private ContactRepository contactRepository;

    @Autowired
    @Qualifier(value = "esmeContactRepositoryJdbcImpl")
    private ContactRepository repositoryJdbc;

    @Override
    public Contact save(Contact contact, int connectorId) {
        Assert.notNull(contact, "contact must not be null");
        return contact.getCustom().equals(true)?contactRepository.save(contact,connectorId):repositoryJdbc.save(contact,connectorId);
    }

    @Override
    public void delete(int id, int connectorId,boolean isCustom) {
        boolean res = isCustom?contactRepository.delete(id,connectorId):repositoryJdbc.delete(id,connectorId);
        checkNotFoundWithId(res,id);
    }

    @Override
    public Contact get(int id, int connectorId,boolean isCustom) {
        Contact c = isCustom?contactRepository.get(id,connectorId):repositoryJdbc.get(id,connectorId);
        return checkNotFoundWithId(c,id);
    }


    @Override
    public Contact update(Contact contact, int connectorId){
        Assert.notNull(contact, "contact must not be null");
        Contact c = contact.getCustom()?contactRepository.save(contact,connectorId):repositoryJdbc.save(contact,connectorId);
        return checkNotFoundWithId(c,contact.getId());
    }

    @Override
    public List<Contact> getAll(int connectorId,boolean isCustom) {

        return isCustom?contactRepository.getAll(connectorId,isCustom):repositoryJdbc.getAll(connectorId,isCustom);
    }

    @Override
    public List<Contact> getWhole(boolean isCustom) {
        return isCustom?contactRepository.getWhole(isCustom):repositoryJdbc.getWhole(isCustom);
    }
}
