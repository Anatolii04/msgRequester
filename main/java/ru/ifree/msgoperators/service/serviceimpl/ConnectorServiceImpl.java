package ru.ifree.msgoperators.service.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.ifree.msgoperators.model.Connector;
import ru.ifree.msgoperators.repository.ConnectorRepository;
import ru.ifree.msgoperators.service.ConnectorService;
import ru.ifree.msgoperators.util.ValidationUtil;



import java.util.List;


@Service
public class ConnectorServiceImpl implements ConnectorService {

    @Autowired
    @Qualifier(value = "jpaConnectorRepoImpl")
    private ConnectorRepository jpaRepository;

    @Autowired
    @Qualifier(value = "esmeConnectorRepositoryJdbcImpl")
    private ConnectorRepository jdbcRepository;

    @Override
    public Connector save(Connector connector,boolean isCustom) {
        Assert.notNull(connector, "connector must not be null");
        return isCustom ? jpaRepository.save(connector):jdbcRepository.save(connector);
    }

    @Override
    public void delete(int id,boolean isCustom) {
        boolean found = isCustom ? jpaRepository.delete(id) : jdbcRepository.delete(id);
        ValidationUtil.checkNotFoundWithId(found, id);
    }

    @Override
    public Connector get(int id,boolean isCustom) {
        Connector connector = isCustom ? jpaRepository.get(id) : jdbcRepository.get(id);
        return ValidationUtil.checkNotFoundWithId(connector, id);
    }

//    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    @Override
    public List<Connector> getAll(boolean isCustom) {
        return isCustom ? jpaRepository.getAll() : jdbcRepository.getAll();
    }

    @Override
    public Connector update(Connector connector,boolean isCustom) {
        Assert.notNull(connector, "connector must not be null");
        Connector connector1 = isCustom ? jpaRepository.save(connector) : jdbcRepository.save(connector);
        return ValidationUtil.checkNotFoundWithId(connector1 ,connector.getId());
    }
}
