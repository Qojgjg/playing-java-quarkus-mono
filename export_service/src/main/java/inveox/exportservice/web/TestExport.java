package inveox.exportservice.web;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import inveox.exportservice.infrastructure.inbound.dlo.DLOExtensionService;
import inveox.exportservice.infrastructure.inbound.dlo.dto.DigitalLabOrderDto;


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
    public String getAllFromMockPatient() {

        return "";

    }

}
