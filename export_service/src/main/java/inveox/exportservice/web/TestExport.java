package inveox.exportservice.web;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import inveox.exportservice.application.ExportService;
import inveox.exportservice.infrastructure.inbound.dlo.dto.DigitalLabOrderDto;
import inveox.exportservice.infrastructure.inbound.dlo.dto.PatientDto;


@Path("/exportservice/")
public class TestExport {

    @Inject
    ExportService exportService;

    @Path("laborder/mock/{dlo_id}")
    @GET
    public DigitalLabOrderDto getDLO(@PathParam("dlo_id") String dlo_id) {

        DigitalLabOrderDto dlo = exportService.getDigitalLabOrder(dlo_id);

        return dlo;
    }

    @Path("patient/mock/{pat_id}")
    @GET
    public PatientDto getPatient(@PathParam("pat_id") String pat_id) {

        PatientDto patientDto = exportService.getPatient(pat_id);

        return patientDto;
    }

    @Path("/message/laborder/{dlo_id}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String Map(@PathParam("dlo_id") String dlo_id) {

        String response=exportService.mapDLOtoORM_01(dlo_id).toString();

        return response;
    }
 

}
