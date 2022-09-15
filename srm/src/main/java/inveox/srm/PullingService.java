
package inveox.srm;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
        String accessionIdentifier = "";

        dlo.setUuid(accessionIdentifier+"."+counter);
        dlo.setLastStatusChanged(Instant.now());
        dlo.setLastStatusChanged(Instant.now());
        dlo.setBusinessId("businessId");
        dlo.setStatus(DigitalLabOrderStatus.IN_DELIVERY);

        em.persist(dlo);
    }

}