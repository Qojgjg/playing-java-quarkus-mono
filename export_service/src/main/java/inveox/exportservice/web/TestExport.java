package inveox.exportservice.web;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import inveox.exportservice.application.ExportService;
import inveox.exportservice.infrastructure.inbound.patient.PatientExtensionService;
import inveox.exportservice.infrastructure.inbound.patient.dto.PatientDto;


@Path("/export/patient")
public class TestExport {

    @Inject
    ExportService exportService;

    @Path("/mock/{pat_id}")
    @GET
    public PatientDto getPatient(@PathParam("pat_id") String pat_id) {

        PatientDto patientDto = exportService.getPatient(pat_id);

        System.out.println(patientDto);

        return patientDto;
    }

    @Path("/map/{pat_id}")
    @GET
    public String MapPatient(@PathParam("pat_id") String pat_id) {

        exportService.mapDLOtoORM_01(pat_id);

        return "Patient "+ pat_id + "mapped";
    }
 

}
