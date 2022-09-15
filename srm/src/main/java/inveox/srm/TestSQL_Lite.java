package inveox.srm;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import inveox.srm.domain.model.Container;
import inveox.srm.domain.model.DigitalLabOrder;

@Path("/test")
public class TestSQL_Lite {

    @Inject
    EntityManager em; 

    @Path("/all")
    @GET
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public String getAll() {
        
        Query lQuery =   em.createQuery("FROM DigitalLabOrder");
        List<DigitalLabOrder> result = lQuery.getResultList();
        for (DigitalLabOrder c:result){
            System.out.println(c);
            
        }

        return "We have "+result.size() +" dlo stored";
    }

}

