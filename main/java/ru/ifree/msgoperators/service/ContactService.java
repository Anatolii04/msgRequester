package ru.ifree.msgoperators.service;

import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.util.exception.NotFoundException;

import java.util.List;


public interface ContactService {
    Contact save(Contact contact, int connectorId);

    Contact update(Contact contact, int connectorId) throws NotFoundException;

    void delete(int id, int connectorId,boolean isCustom) throws NotFoundException;

    Contact get(int id,int connectorId,boolean isCustom) throws NotFoundException;

    List<Contact> getAll(int connectorId,boolean isCustom);

    List<Contact> getWhole(boolean isCustom);
}
