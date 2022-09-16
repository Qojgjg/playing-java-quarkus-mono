package inveox.srm.infrastructure;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import inveox.srm.infrastructure.dto.DigitalLabOrderDto;
import inveox.srm.infrastructure.dto.Example;

@Path("/thing")
@RegisterRestClient
public interface DLOExtensionService {

    @GET
    Set<DigitalLabOrderDto> getAll();

    @Path("/examples")
    @GET
    Set<Example> getAllExamples();
    
}
