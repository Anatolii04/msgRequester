package ru.ifree.msgoperators.repository.springdata;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.ifree.msgoperators.model.Contact;

import java.util.List;


public interface CrudContactRepo extends JpaRepository<Contact,Integer>{
    @Transactional
    @Modifying
    @Query("DELETE FROM Contact c WHERE c.id=:id AND c.connector.id=:connectorId")
    int delete(@Param("id") int id,@Param("connectorId") int connectorId);

    @Query("SELECT c FROM Contact c WHERE c.connector.id=:connectorId AND c.custom=:custom ORDER BY c.contact DESC")
    List<Contact> getAll(@Param("connectorId") int connectorId,@Param("custom")boolean isCustom);

    @Query("SELECT c FROM Contact c WHERE c.custom=:custom ORDER BY c.contact DESC")
    List<Contact> getWhole(@Param("custom")boolean custom);

}
