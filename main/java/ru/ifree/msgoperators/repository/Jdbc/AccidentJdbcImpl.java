package ru.ifree.msgoperators.repository.Jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.ifree.msgoperators.model.ActualAccident;
import ru.ifree.msgoperators.repository.AccidentJdbc;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccidentJdbcImpl implements AccidentJdbc {
    static final RowMapper<ActualAccident> ROW_MAPPER_ACTUAL_QUERY = (ResultSet r, int i) -> {
        ActualAccident a = new ActualAccident(r.getInt("id"),r.getString("node"),r.getTimestamp("time_stamp"), r.getBoolean("actual"));
        return a;
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertActualQuery;

    @Autowired
    public AccidentJdbcImpl(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertActualQuery = new SimpleJdbcInsert(dataSource)
                .withTableName("actual_accidents")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    @Transactional
    public Boolean delete(Integer id) {
        return jdbcTemplate.update(
                "DELETE FROM actual_accidents WHERE  id=?",id) != 0;
    }

    @Override
    @Transactional
    public ActualAccident save(ActualAccident actualAccident) {
        if(actualAccident.isNew()) {
            Map<String, Object> parameters = new HashMap<>(4);
            parameters.put("id", actualAccident.getId());
            parameters.put("node", actualAccident.getNode());
            parameters.put("time_stamp", actualAccident.getTimestamp());
            parameters.put("actual", actualAccident.getActual());
            Number newKey = insertActualQuery.executeAndReturnKey(parameters);
            actualAccident.setId(newKey.intValue());
        } else {
            if(jdbcTemplate.update("UPDATE actual_accidents SET actual=? WHERE id=?",actualAccident.getActual(),actualAccident.getId())==0){
                return null;
            }
        }
        return actualAccident;
    }



    @Override
    public List<ActualAccident> get(String name) {
        return jdbcTemplate.query("SELECT * from actual_accidents WHERE node=?",ROW_MAPPER_ACTUAL_QUERY, name);

    }

    @Override
    public List<ActualAccident> getAll() {
        List<ActualAccident> actualQueries = jdbcTemplate.query("SELECT * from actual_accidents",ROW_MAPPER_ACTUAL_QUERY);
        return actualQueries;
    }
}
