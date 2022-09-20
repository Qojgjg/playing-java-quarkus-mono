package inveox.exportservice.infrastructure.inbound.dlo;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import inveox.exportservice.infrastructure.inbound.dlo.dto.DigitalLabOrderDto;

@Path("/thing")
@RegisterRestClient
public interface DLOExtensionService {

    @GET
    Set<DigitalLabOrderDto> getAll();

    
}
