package ru.ifree.msgoperators.repository.springdata;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.ifree.msgoperators.model.Connector;
import ru.ifree.msgoperators.repository.ConnectorRepository;

import java.util.List;

@Repository
public class JpaConnectorRepoImpl implements ConnectorRepository{

    @Autowired
    private CrudConnectorRepo crudConnectorRepo;

    @Override
    public Connector save(Connector connector) {
        if (!connector.isNew() && get(connector.getId()) == null) {
            return null;
        }
        return crudConnectorRepo.save(connector);
    }


    @Override
    public boolean delete(int id) {
        return crudConnectorRepo.delete(id)!=0;
    }

    @Override
    public Connector get(int id) {
        return crudConnectorRepo.findOne(id);
    }

    @Override
    public List<Connector> getAll() {
        return crudConnectorRepo.findAll();
    }
}
