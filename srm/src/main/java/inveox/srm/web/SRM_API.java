package inveox.srm.web;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import inveox.srm.application.ContanierProcessorService;
import inveox.srm.application.ContanierQueryService;
import inveox.srm.application.dto.ContainerDataDto;

@Path("/srm")
public class SRM_API {

    @Inject
    ContanierQueryService dloService;

    @Inject
    ContanierProcessorService containerProcessorService;

    @Path("/contanier/{containerId}")
    @GET
    public ContainerDataDto getInfoContainer(@PathParam("containerId") String containerId) {
        try {
            return dloService.findContainerId(containerId);
        } catch (Exception e) {
            throw new NotFoundException("Unknown container: " + containerId);
        }
    }

    @PUT
    @Path("/contanier/{containerId}")
    public void processContainer(@PathParam("containerId") String containerId) {
        try {
            containerProcessorService.processContainer(containerId);
        } catch (Exception e) {
            throw new NotFoundException("Unknown container: " + containerId);
        }
    }

}
