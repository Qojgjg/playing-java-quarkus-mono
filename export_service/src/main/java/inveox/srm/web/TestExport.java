package inveox.srm.web;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import inveox.srm.domain.model.Container;
import inveox.srm.domain.model.DigitalLabOrder;
import inveox.srm.infrastructure.inbound.dlo.DLOExtensionService;
import inveox.srm.infrastructure.inbound.dlo.dto.DigitalLabOrderDto;
import inveox.srm.infrastructure.inbound.dlo.dto.Example;

@Path("/export")
public class TestExport {

    @Inject
    @RestClient
    DLOExtensionService dloService;

    @Path("/mock/laborder")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllFromMockService() {
        Set<DigitalLabOrderDto> result = dloService.getAll();
        for (DigitalLabOrderDto c : result) {
            System.out.println(c);

        }
        return "We have " + result.size() + " dlo pulled";
    }

    @Path("/mock/patient")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllFromMockExamplesService() {
        Set<Example> result = dloService.getAllExamples();
        return "We have " + result.size() + " examples pulled";
    }

}
