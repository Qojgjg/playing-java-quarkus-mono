
package inveox.srm;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import inveox.srm.domain.model.Container;
import inveox.srm.domain.model.DigitalLabOrder;
import inveox.srm.domain.model.enums.ContainerStatus;
import inveox.srm.domain.model.enums.DigitalLabOrderStatus;
import inveox.srm.repository.DigitalLabOrderRepository;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped 
public class PullingService{

    @Inject
    EntityManager em; 
    
    private AtomicLong counter = new AtomicLong();

    private final DigitalLabOrderRepository dloRepo;

    public PullingService(DigitalLabOrderRepository dloRepo) {
        this.dloRepo = dloRepo;
    }


    @Transactional
    @Scheduled(every="10s")     
    void increment() {

        Long a=dloRepo.count();
        counter.set(a);

        counter.incrementAndGet(); 
        System.out.println(counter);

        DigitalLabOrder dlo = new DigitalLabOrder();
        String accessionIdentifier = "1.2.23.1";

        dlo.setUuid(accessionIdentifier+"."+counter);
        dlo.setLastStatusChanged(Instant.now());
        dlo.setLastStatusChanged(Instant.now());
        dlo.setBusinessId("businessId");
        dlo.setStatus(DigitalLabOrderStatus.IN_DELIVERY);

        Container cont1=new Container();

        cont1.setContainerId("containerId"+dlo.getUuid()+".1");
        cont1.setNum_samples(3);
        cont1.setMainBodyPart("mainBodyPart");
        cont1.setStatus(ContainerStatus.IN_PROCESS);

        Container cont2=new Container();

        cont2.setContainerId("containerId"+dlo.getUuid()+".2");
        cont2.setNum_samples(2);
        cont2.setMainBodyPart("mainBodyPart2");
        cont2.setStatus(ContainerStatus.IN_PROCESS);
        
        List<Container> conts=new LinkedList<Container>();

        conts.add(cont1);
        conts.add(cont2);

        dlo.setContainers(conts);
//        em.persist(cont1);
        em.persist(dlo);
    }

}