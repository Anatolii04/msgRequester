package ru.ifree.msgoperators.util;


import ru.ifree.msgoperators.model.Connector;
import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.to.ConnectorTo;
import ru.ifree.msgoperators.to.ContactTo;

public class ToUtil {
    public static Connector connectorFromTo(ConnectorTo connectorTo){
        return new Connector(connectorTo.getId(),connectorTo.getName(),connectorTo.getSystemId()
                ,connectorTo.getPort(),Boolean.parseBoolean(connectorTo.getEnabled()),connectorTo.getSmscAddr(),connectorTo.getJurName());
    }
    public static Contact contactFromTo(ContactTo contactTo){
        return new Contact(contactTo.getId(),contactTo.getContact(),contactTo.getDescription(), contactTo.getCustom());
    }
}