package ru.ifree.msgoperators.repository.Jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ifree.msgoperators.model.Connector;
import ru.ifree.msgoperators.model.Contact;
import ru.ifree.msgoperators.repository.ContactRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.ifree.msgoperators.repository.Jdbc.EsmeConnectorRepositoryJdbcImpl.ROW_MAPPER_CONNECTOR;


@Repository
public class EsmeContactRepositoryJdbcImpl implements ContactRepository{

     static final RowMapper<Contact> PROPERTY_ROW_MAPPER = BeanPropertyRowMapper.newInstance(Contact.class);
     static final RowMapper<HashMap<Integer,Contact>> ROW_MAPPER_CONTACT = (ResultSet r, int i)  ->
    {
        HashMap<Integer,Contact> m = new HashMap<>();
        Contact c = new Contact(r.getInt("id"),r.getString("contact"),r.getString("description"),r.getBoolean("custom"));
        m.put(r.getInt("connector_id"),c);
        return m;
    };

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertContact;

    @Autowired
    public EsmeContactRepositoryJdbcImpl(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate,JdbcTemplate jdbcTemplate) {
        this.insertContact = new SimpleJdbcInsert(dataSource)
                .withTableName("contacts")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    @Transactional
    public Contact save(Contact contact,int connectorId) {
        Map<String, Object> parameters = new HashMap<>(2);
        parameters.put("connector_id", connectorId);parameters.put("contact",contact.getContact());
        parameters.put("description",contact.getDescription());parameters.put("custom",contact.getCustom());
        parameters.put("id",contact.getId());
        if (contact.isNew()) {
            Number newKey = insertContact.executeAndReturnKey(parameters);
            contact.setId(newKey.intValue());
        } else {
            if(namedParameterJdbcTemplate.update(
                    "UPDATE contacts SET contact=:contact, description=:description WHERE id=:id AND custom=:custom", parameters)==0)
                        return null;
        }
        return contact;
    }

    @Override
    @Transactional
    public boolean delete(int id, int connectorId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id",id)
                .addValue("connectorId",connectorId);
        return namedParameterJdbcTemplate.update("DELETE FROM contacts WHERE id=:id AND connector_id=:connectorId",parameters) != 0;
    }

    @Override
    public Contact get(int id, int connectorId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id",id)
                .addValue("connectorId",connectorId);
        List<Contact> contact = namedParameterJdbcTemplate.query("SELECT * " +
                "FROM contacts WHERE id=:id AND connector_id=:connectorId",parameters, PROPERTY_ROW_MAPPER);
        return DataAccessUtils.singleResult(contact);
    }

    @Override
    public List<Contact> getAll(int connectorId,boolean custom) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("connectorId",connectorId)
                .addValue("custom", custom);
        Connector connector = DataAccessUtils.singleResult(jdbcTemplate.query("SELECT * FROM esme_connectors WHERE id=? ORDER BY name", ROW_MAPPER_CONNECTOR,connectorId));
        List<Contact> contacts = namedParameterJdbcTemplate.query(
                "SELECT * FROM contacts WHERE connector_id=:connectorId AND custom=:custom ORDER BY contact DESC",parameters, PROPERTY_ROW_MAPPER);
        contacts.forEach(e -> e.setConnector(connector));
        return contacts;
    }

    @Override
    public List<Contact> getWhole(boolean custom) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("custom",custom);
        List<Connector> connectors = jdbcTemplate.query("SELECT * FROM esme_connectors  ORDER BY name", ROW_MAPPER_CONNECTOR);
        List<HashMap<Integer,Contact>> contacts =  namedParameterJdbcTemplate.query(
                "SELECT * FROM contacts WHERE custom=:custom",parameters, ROW_MAPPER_CONTACT);
        List<Contact> contactsRes = new ArrayList<>();
        for (HashMap<Integer,Contact> cs: contacts) {
            for (Map.Entry<Integer,Contact> h: cs.entrySet()) {
                for (Connector n: connectors) {
                    if(n.getId().equals(h.getKey())){
                       Contact contact = h.getValue();
                       contact.setConnector(n);
                       contactsRes.add(contact);
                    }
                }
            }
        }
        return contactsRes;
    }
}
