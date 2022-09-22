package inveox.exportservice.web;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import inveox.exportservice.application.ExportService;
import inveox.exportservice.infrastructure.inbound.dlo.dto.DigitalLabOrderDto;
import inveox.exportservice.infrastructure.inbound.patient.dto.PatientDto;


@Path("/export/")
public class TestExport {

    @Inject
    ExportService exportService;

    @Path("dlo/mock/{dlo_id}")
    @GET
    public DigitalLabOrderDto getDLO(@PathParam("dlo_id") String dlo_id) {

        DigitalLabOrderDto dlo = exportService.getDigitalLabOrder(dlo_id);

        System.out.println(dlo);

        return dlo;
    }

    @Path("patient/mock/{pat_id}")
    @GET
    public PatientDto getPatient(@PathParam("pat_id") String pat_id) {

        PatientDto patientDto = exportService.getPatient(pat_id);

        System.out.println(patientDto);

        return patientDto;
    }

    @Path("/map/{dlo_id}")
    @GET
    public String Map(@PathParam("dlo_id") String dlo_id) {

        exportService.mapDLOtoORM_01(dlo_id);

        return "Data lab order "+ dlo_id + " was mapped check export Folder";
    }
 

}
