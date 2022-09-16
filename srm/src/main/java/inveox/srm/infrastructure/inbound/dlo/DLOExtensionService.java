package inveox.srm.infrastructure.inbound.dlo;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import inveox.srm.infrastructure.inbound.dlo.dto.DigitalLabOrderDto;
import inveox.srm.infrastructure.inbound.dlo.dto.Example;

@Path("/thing")
@RegisterRestClient
public interface DLOExtensionService {

    @GET
    Set<DigitalLabOrderDto> getAll();

    @Path("/examples")
    @GET
    Set<Example> getAllExamples();
    
}
