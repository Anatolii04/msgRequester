package ru.ifree.msgoperators.service;

import ru.ifree.msgoperators.util.exception.NotFoundException;
import ru.ifree.msgoperators.model.Connector;

import java.util.List;


public interface ConnectorService {
    Connector save(Connector connector,boolean isCustom);

    void delete(int id,boolean isCustom) throws NotFoundException;

    Connector get(int id,boolean isCustom) throws NotFoundException;

    List<Connector> getAll(boolean isCustom);

    Connector update(Connector connector,boolean isCustom) throws NotFoundException;
}
