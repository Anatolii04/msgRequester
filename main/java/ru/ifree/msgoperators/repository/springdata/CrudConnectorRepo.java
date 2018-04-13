package ru.ifree.msgoperators.repository.springdata;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.ifree.msgoperators.model.Connector;


public interface CrudConnectorRepo extends JpaRepository<Connector,Integer>{
    @Transactional
    @Modifying
    @Query("DELETE FROM Connector c WHERE c.id=:id")
    int delete(@Param("id") int id);
}
