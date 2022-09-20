package inveox.exportservice.infrastructure.inbound.patient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import inveox.exportservice.infrastructure.inbound.patient.dto.PatientDto;

@Path("/patient")
@RegisterRestClient
public interface PatientExtensionService {

    @GET
    @Path("/{pat_id}")
    PatientDto getPatient(@PathParam("pat_id") String pat_id);
    
}
