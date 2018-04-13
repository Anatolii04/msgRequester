package ru.ifree.msgoperators.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ifree.msgoperators.model.Connector;
import ru.ifree.msgoperators.service.ConnectorService;

import java.util.List;

public abstract class AbstractConnectorController {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final ConnectorService connectorService;

    public AbstractConnectorController(ConnectorService connectorService){
        this.connectorService = connectorService;
    }

    public List<Connector> getAll(String type){
        Boolean isCustom = type.equalsIgnoreCase("custom");
        LOGGER.info("get All {}", type);
        return connectorService.getAll(isCustom);
    }

    public Connector get(int id,String type) {
        Boolean isCustom = type.equalsIgnoreCase("custom");
        LOGGER.info("get {} {}",type, id);
        return connectorService.get(id, isCustom);
    }

    public Connector create(Connector connector,String type) {

        Boolean isCustom = type.equalsIgnoreCase("custom");
        LOGGER.info("create {} {}",type, connector);
        Connector created;
        if(connector.isNew()){
            created = connectorService.save(connector,isCustom);
        }else throw new IllegalArgumentException(connector + "must be new (id=null)");
        return created;
    }

    public void update(Connector connector, int id,String type){
        Boolean isCustom = type.equalsIgnoreCase("custom");
        LOGGER.info("update {} {}",type, id);
        if(connector.isNew()){
            connector.setId(id);
        }else if (connector.getId()!=id){
            throw new IllegalArgumentException(connector + " must be with id=" + id);
        }
        connectorService.update(connector, isCustom);
    }

    public void delete(int id,String type){
        Boolean isCustom = type.equalsIgnoreCase("custom");
        LOGGER.info("delete {} {}",type, id);
        connectorService.delete(id, isCustom);
    }

}
