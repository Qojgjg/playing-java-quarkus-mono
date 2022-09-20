package inveox.exportservice.web;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import inveox.exportservice.infrastructure.inbound.patient.PatientExtensionService;
import inveox.exportservice.infrastructure.inbound.patient.dto.PatientDto;


@Path("/export")
public class TestExport {

    @Inject
    @RestClient
    PatientExtensionService patientMock;

    @Path("/mock/patient1")
    @GET
    public PatientDto getPatient1() {
        PatientDto result = patientMock.getPatient1();

            System.out.println(result);


        return result;
    }
 
    @Path("/mock/patient2")
    @GET
    public PatientDto getAllFromMockPatient() {
        PatientDto result = patientMock.getPatient2();
        System.out.println(result);
        return result;
    }

}
