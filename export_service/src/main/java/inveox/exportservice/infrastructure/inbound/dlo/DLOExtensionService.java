package inveox.exportservice.infrastructure.inbound.dlo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import inveox.exportservice.infrastructure.inbound.dlo.dto.DigitalLabOrderDto;

@Path("/laborder")
@RegisterRestClient
public interface DLOExtensionService {


    @GET
    @Path("/{dlo_id}")
    DigitalLabOrderDto getDigitalLabOrderDto(@PathParam("dlo_id") String dlo_id);

    
}
