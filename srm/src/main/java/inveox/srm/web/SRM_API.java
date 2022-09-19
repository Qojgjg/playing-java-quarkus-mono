package inveox.srm.web;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import inveox.srm.application.ContanierQueryService;
import inveox.srm.application.dto.ContainerDataDto;

@Path("/srm")
public class SRM_API {

@Inject
ContanierQueryService dloService;

    
    @Path("/contanier/{containerId}")
    @GET
    public ArrayList<ContainerDataDto> getInfoContainer(@PathParam("containerId") String containerId) {
       return dloService.fingContainerId(containerId);           
    }

}
