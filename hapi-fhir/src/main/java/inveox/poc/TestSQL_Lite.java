package inveox.poc;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import inveox.poc.producer.SpecimenProducer;

@Path("/test")
public class TestSQL_Lite {

    @Inject
    EntityManager em; 

    @Inject
    SpecimenProducer producer;


    @Path("/all")
    @GET
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public String getAll() {
        
        Query lQuery =   em.createQuery("FROM Specimen");
        List<Specimen> result = lQuery.getResultList();
        for (Specimen c:result){
            System.out.println(c);
        }

        return "We have "+result.size() +" specimens stored";
    }

    @Path("/send/{id}")
    @GET
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public String sendToKafka(@PathParam("id") Long id) {
        
        Specimen spec=em.find(Specimen.class, id);

        producer.sendSpecimenToKafka(spec);

        return "We sent specimen "+id;
    }


//    https://hapi.fhir.org/baseR4/Specimen/1224226
}

