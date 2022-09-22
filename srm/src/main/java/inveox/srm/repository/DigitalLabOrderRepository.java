package inveox.srm.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import inveox.srm.domain.model.DigitalLabOrder;

public interface DigitalLabOrderRepository extends CrudRepository<DigitalLabOrder, Long>{
    

    @Query("select dlo FROM DigitalLabOrder dlo  join dlo.containers conts WHERE conts.containerId = ?1 ")
    DigitalLabOrder findByContainersContainerId(String contId);

 


//    @Query("select c FROM Container c  WHERE containerId = ?1 ")
//    @Query("select conts FROM DigitalLabOrder dlo  join dlo.containers conts WHERE conts.containerId = ?1 ")
//    Container findContainerByContainersContainerId(String contId);
//findOneByDepartmentId
     
}
