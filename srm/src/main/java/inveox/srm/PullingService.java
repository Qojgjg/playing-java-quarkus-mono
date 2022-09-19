
package inveox.srm;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import inveox.srm.domain.model.Container;
import inveox.srm.domain.model.DigitalLabOrder;
import inveox.srm.domain.model.enums.DigitalLabOrderStatus;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped 
public class PullingService{

    @Inject
    EntityManager em; 
    
    private AtomicInteger counter = new AtomicInteger();


    public int get() {  
        return counter.get();
    }

    @Transactional
    @Scheduled(every="10s")     
    void increment() {
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

        cont1.setContainerId("containerId"+"."+counter);
        cont1.setNum_samples(counter.get());
        cont1.setMainBodyPart("mainBodyPart");

        Container cont2=new Container();

        cont2.setContainerId("containerId"+"."+counter+1);
        cont2.setNum_samples(counter.get());
        cont1.setMainBodyPart("mainBodyPart2");
        
        Set<Container> conts=new HashSet<Container>();

        conts.add(cont1);
        conts.add(cont2);

        dlo.setContaniers(conts);
//        em.persist(cont1);
        em.persist(dlo);
    }

}