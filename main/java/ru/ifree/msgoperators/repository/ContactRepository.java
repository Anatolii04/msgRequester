package ru.ifree.msgoperators.repository;



import ru.ifree.msgoperators.model.Contact;

import java.util.List;


public interface ContactRepository {
    // null if updated contact do not belong to connectorId
    Contact save(Contact contact,int connectorId);

    // false if contact do not belong to connectorId
    boolean delete(int id, int connectorId);

    // null if contact do not belong to userId
    Contact get(int id,int connectorId);

    //get all from one connector
    List<Contact> getAll(int connectorId,boolean isCustom);

    List<Contact> getWhole(boolean isCustom);
}
