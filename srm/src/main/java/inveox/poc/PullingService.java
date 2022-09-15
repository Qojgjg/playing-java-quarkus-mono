
package inveox.poc;

import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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

        Specimen spec1 = new Specimen();
        String accessionIdentifier = "";
        String collection_bodySite ="";
        String collection_method="";
        String container_id="";
        String type="";

        spec1.setAccessionIdentifier(accessionIdentifier+"."+counter);
        spec1.setCollection_bodySite(collection_bodySite);
        spec1.setCollection_method(collection_method);
        spec1.setContainer_id(container_id);
        spec1.setType(type);

        em.persist(spec1);
    }

}