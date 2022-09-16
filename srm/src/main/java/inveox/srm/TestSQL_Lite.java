package inveox.srm;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import inveox.srm.domain.model.DigitalLabOrder;
import inveox.srm.infrastructure.DLOExtensionService;
import inveox.srm.infrastructure.dto.Example;

@Path("/test")
public class TestSQL_Lite {

    @Inject
    EntityManager em; 


    @Inject
    @RestClient
    DLOExtensionService dloService;
    
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

    @Path("/mock")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllFromMockService(){
        System.out.println("Tonces");
        Set<Example> result=    dloService.getAll();
        return "We have "+result.size() +" dlo pulled";
    }

}

