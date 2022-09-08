
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
        Category cat = new Category();
        cat.setName("cat "+counter);
        cat.setDescription("description");
        em.persist(cat);
    }

}