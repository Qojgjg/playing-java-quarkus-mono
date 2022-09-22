package inveox.srm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import inveox.srm.domain.model.SRM_Event;
import inveox.srm.domain.model.SRM_Process;

public interface SRM_ProcessRepository extends CrudRepository<SRM_Process, Long>{
    

    @Override
    List<SRM_Process> findAll();
     


    @Query("select p FROM SRM_Process p   WHERE p.containerId = ?1 ")
    List<SRM_Process> findByContainerId(String contId);

}
