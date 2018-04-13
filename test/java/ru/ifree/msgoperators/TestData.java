package ru.ifree.msgoperators;


import ru.ifree.msgoperators.model.Connector;
import ru.ifree.msgoperators.model.Contact;

import java.util.Arrays;
import java.util.List;


public class TestData {
    private TestData(){}

    public final static String AJAX_CONNECTORS_CUSTOM = "/ajax/connectors/custom/";
    public final static String AJAX_CONNECTORS_ESME = "/ajax/connectors/esme/";
    public final static String AJAX_CONTACT_CUSTOM = "/ajax/contacts/custom/";
    public final static String AJAX_CONTACT_ESME = "/ajax/contacts/esme/";

    public final static boolean CUSTOM = true;
    public final static boolean ESME = false;

    public final static int ID_MTS = 1;
    public final static int ID_MF = 2;
    public final static int ID_T2 = 3;
    public final static int ID_GHOST = 666666;
    public final static int ID_A_MOBILE =1449;
    public final static int ID_AKOC =1619;

    public final static int CONTACT_MTS_ID = 1;
    public final static int CONTACT_A_MOBILE_ID = 2;

    public final static Connector CONNECTOR_AKOC = new Connector(ID_AKOC,"AKOC", "befree", 3200, false,"86.102.97.55","BeFree");
    public final static Connector CONNECTOR_A_MOBILE = new Connector(ID_A_MOBILE,"A-mobile", "ifree", 2000, false,"172.21.100.59","i-Free");
    public final static Connector CONNECTOR_MTS = new Connector(ID_MTS,"MTS", "systemId-MTS", 9090, true,"mts.ru","i-Free");
    public final static Connector CONNECTOR_MF = new Connector(ID_MF,"Megafon", "systemId-Megafon",  7895, false, "megafon.ru","i-Free");
    public final static Connector CONNECTOR_T2 = new Connector(ID_T2,"Tele2", "sysId-T2",  133, true, "tele2.ru","i-Free");
    public final static Connector CONNECTOR_GHOST = new Connector(ID_GHOST,"Tele2", "sysId-T2",  133, true, "tele2.ru","i-Free");
    public final static Contact CONTACT_MTS = new Contact(1,"79114567111","phone some manager",true);
    public final static Contact CONTACT_A_MOBILE = new Contact(2,"email@a-mobile.ru", "email tech support",false);
//    public final static Contact CONTACT_MTS2 = new Contact(3,"79114567890", "phone some manager",true);
    public final static Contact CONTACT_MF = new Contact(3,"email@megafon.ru", "email tech support",true);
    public final static Contact CONTACT_AKOC = new Contact(4,"79254567891", "phone some manager",false);


    public final static Connector CONNECTOR_AKOC_UPDATE = new Connector(ID_AKOC,"AKOC", "befree", 3200, false,"86.102.97.55","China");
    public final static Connector CONNECTOR_ALTEL = new Connector(1448,"Altel", "Jamango4441", 900, true,"217.76.66.119","China");
    public final static Connector CONNECTOR_MTS_UPDATE = new Connector(ID_MTS,"MTS-update", "systemId-MTS-up", 9090, true,"mts.ru-up","i-Free");
    public final static Connector CONNECTOR_T2_CREATE = new Connector(null,"Tele2", "sysId-T2",  133, true, "tele2.ru","i-Free");
    public final static Contact CONTACT_MTS_NEW = new Contact(null,"79114567890","phone some manager",true);
    public final static Contact CONTACT_AKOC_NEW = new Contact(null,"79114567890","test text",false);
    public final static Contact CONTACT_AKOC_UPDATE = new Contact(null,"79114567890","test update",false);
    public final static Contact CONTACT_MTS_UPDATE = new Contact(1,"79114567444","phone some manager update",true);



    public static Connector createConnector(){
        return CONNECTOR_T2_CREATE;
    }

    public static List<Connector> listConnectors(){
        return Arrays.asList(CONNECTOR_MTS, CONNECTOR_MF, CONNECTOR_T2);
    }

    public static List<Connector> connectorsDb(){
        return Arrays.asList(CONNECTOR_MTS, CONNECTOR_MF);

    }

//    public static List<Contact> listContacts(){
//        return Arrays.asList(CONTACT_MTS,CONTACT_MTS1,CONTACT_MTS2,CONTACT_MF,CONTACT_MF1);
//    }
//
//    public static List<Contact> listContactsMTS(){
//        return Arrays.asList(CONTACT_MTS,CONTACT_MTS1,CONTACT_MTS2);
//    }
}
