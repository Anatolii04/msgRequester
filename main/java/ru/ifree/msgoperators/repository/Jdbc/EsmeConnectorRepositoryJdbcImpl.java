package ru.ifree.msgoperators.repository.Jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ifree.msgoperators.model.Connector;
import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.repository.ConnectorRepository;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


import static ru.ifree.msgoperators.repository.Jdbc.EsmeContactRepositoryJdbcImpl.ROW_MAPPER_CONTACT;


@Repository
public class EsmeConnectorRepositoryJdbcImpl implements ConnectorRepository {
    static final RowMapper<Connector> ROW_MAPPER_CONNECTOR = (ResultSet r, int i) -> {
        Connector connector = new Connector(r.getInt("id"),
                r.getString("name"),r.getString("system_id"),
                r.getInt("port"), r.getBoolean("enabled"),
                r.getString("smsc_addr"),r.getString("jur_person"));
        return connector;
    };




    private final JdbcTemplate jdbcTemplate;

    private final EsmeJurNamesRepository esmeJurNamesRepository;


    @Autowired
    public EsmeConnectorRepositoryJdbcImpl(DataSource dataSource, JdbcTemplate jdbcTemplate,EsmeJurNamesRepository esmeJurNamesRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.esmeJurNamesRepository = esmeJurNamesRepository;
    }

    @Override
    @Transactional
    public Connector save(Connector connector) {
        if (esmeJurNamesRepository.get(connector.getId())==null) {
            esmeJurNamesRepository.save(connector.getId(),connector.getJurName());
        } else {
            if(!esmeJurNamesRepository.update(connector.getId(),connector.getJurName()))
                return null;
        }
        return connector;
    }

    @Override
    @Transactional
    public boolean delete(int connectorId) {
        return esmeJurNamesRepository.delete(connectorId);
    }

    @Override
    public Connector get(int id) {
        List<Connector> connector = jdbcTemplate.query("SELECT * FROM esme_connectors WHERE id=?", ROW_MAPPER_CONNECTOR,id);
        return DataAccessUtils.singleResult(connector);
    }

    @Override
    public List<Connector> getAll() {
        List<Connector> connectors = jdbcTemplate.query("SELECT * FROM esme_connectors  ORDER BY name", ROW_MAPPER_CONNECTOR);
        List<HashMap<Integer,Contact>> listContacts = jdbcTemplate.query("SELECT * FROM contacts WHERE custom=false ORDER BY contact", ROW_MAPPER_CONTACT);
        for (Connector n : connectors) {
            HashSet<Contact> contacts = new HashSet<>();
            for (int i = 0; i < listContacts.size(); i++) {
                for (Map.Entry<Integer, Contact> m: listContacts.get(i).entrySet()){
                    if(m.getKey().equals(n.getId())){
                        contacts.add(m.getValue());
                    }
                }
            }
            n.setContacts(contacts);
        }
        return connectors;
    }

    public Integer getId(String name) {
        Integer res;
        try {
             res = jdbcTemplate.queryForObject("SELECT id from esme_connectors WHERE name=? AND enabled='true'", Integer.class, name);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
        return res;
    }
}
