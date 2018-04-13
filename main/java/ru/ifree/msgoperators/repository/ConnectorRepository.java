package ru.ifree.msgoperators.repository;

import ru.ifree.msgoperators.model.Connector;

import java.util.List;


public interface ConnectorRepository {
    // null if updated Connector do not belong to userId
    Connector save(Connector connector);


    boolean delete(int id);

    // null if meal do not belong to userId
    Connector get(int id);

    // ORDERED dateTime
    List<Connector> getAll();
}
