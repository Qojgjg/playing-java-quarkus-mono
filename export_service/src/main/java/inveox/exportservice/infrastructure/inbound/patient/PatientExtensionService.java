package inveox.exportservice.infrastructure.inbound.patient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import inveox.exportservice.infrastructure.inbound.patient.dto.PatientDto;

@Path("/patient")
@RegisterRestClient
public interface PatientExtensionService {

    @GET
    @Path("/pat1")
    PatientDto getPatient1();

    @GET
    @Path("/pat2")
    PatientDto getPatient2();

    
}
