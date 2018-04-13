package ru.ifree.msgoperators.repository.Jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ifree.msgoperators.model.EsmeJurName;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class EsmeJurNamesRepository {
    private static final RowMapper<EsmeJurName> ROW_MAPPER = BeanPropertyRowMapper.newInstance(EsmeJurName.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EsmeJurNamesRepository(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EsmeJurName> getAllEsmeJurNames(){
        return jdbcTemplate.query(
                "SELECT connector_id AS connectorId,jur_person AS jurName FROM esme_jur_persons", ROW_MAPPER);
    }

    public EsmeJurName get(int connectorId){
        List<EsmeJurName> e = jdbcTemplate.query(
                "SELECT connector_id AS connectorId,jur_person AS jurName FROM esme_jur_persons WHERE connector_id=?",ROW_MAPPER,connectorId);
        return DataAccessUtils.singleResult(e);
    }

    @Transactional
    public boolean delete(int connectorId){
        return jdbcTemplate.update(
                "DELETE FROM esme_jur_persons WHERE  connector_id=?",connectorId) != 0;
    }

    @Transactional
    public boolean update(int connectorId, String jurName){
        return jdbcTemplate.update(
                "UPDATE esme_jur_persons SET jur_person=? WHERE connector_id=?",jurName,connectorId) != 0;
    }

    @Transactional
    public boolean save(int connectorId, String jurName){
        return jdbcTemplate.update(
                "INSERT INTO esme_jur_persons VALUES (?,?)",connectorId,jurName) != 0;
    }
}
