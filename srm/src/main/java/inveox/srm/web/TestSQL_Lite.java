package inveox.srm.web;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import inveox.srm.domain.model.Container;
import inveox.srm.domain.model.DigitalLabOrder;
import inveox.srm.domain.model.SRM_Process;
import inveox.srm.infrastructure.inbound.dlo.DLOExtensionService;
import inveox.srm.infrastructure.inbound.dlo.dto.DigitalLabOrderDto;
import inveox.srm.infrastructure.inbound.dlo.dto.Example;
import inveox.srm.repository.SRM_ProcessRepository;

@Path("/test")
public class TestSQL_Lite {

    @Inject
    EntityManager em;

    @Inject
    @RestClient
    DLOExtensionService dloService;

    @Inject
    SRM_ProcessRepository processRepo;

    @Path("/all")
    @GET
    @Transactional
    @Produces(MediaType.TEXT_PLAIN)
    public String getAll() {

        Query lQuery = em.createQuery("FROM DigitalLabOrder");
        List<DigitalLabOrder> result = lQuery.getResultList();
        for (DigitalLabOrder c : result) {
            System.out.println(c);

        }

        return "We have " + result.size() + " dlo stored";
    }

    @Path("/container/all")
    @GET
    public List<Container> getAllContainers() {

        Query lQuery = em.createQuery("select c FROM Container c");

        List<Container> result = lQuery.getResultList();

        return result;
    }

    /*
    @Path("/startCapture")
    @GET
    public Integer startCapture() {

        // ProcessBuilder processBuilder = new
        // ProcessBuilder("python3.10","/Users/sebastianscotti/python_ws/image_processing/../ppal.py");

        ProcessBuilder processBuilder = new ProcessBuilder();

        // processBuilder.command("python3.10", "--version");

        processBuilder.command("python3.10", "../../python_ws/image_processing/ppal.py",
                "../../python_ws/1_container_42_dataset_0_tissue_type_5/");

        processBuilder.redirectErrorStream(true);

        Process process;
        try {
            process = processBuilder.start();

            // List<String> results = readOutput(process.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
                sb.append(line);
            String jsonLine = sb.toString();

            Integer exitCode = process.waitFor();

            System.out.println("\nExited with error code : " + exitCode);

            if (exitCode == 0) {

                ObjectMapper mapper = new ObjectMapper();
                AnalyzerResponseDto res = mapper.readValue(jsonLine, AnalyzerResponseDto.class);
                System.out.println("\nThe result: " + res);
            }

            return exitCode;

        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return -1;

        }

    }

     */

     
    @Path("/contanier/{containerId}")
    @GET
    public List<Container> getInfoContainer(@PathParam("containerId") String contId) {
        TypedQuery<Container> query = em
                .createQuery("select c FROM Container c WHERE c.containerId = :p1 ", Container.class)
                .setParameter("p1", contId);
        List<Container> result = query.getResultList();
        return result;
    }

    @Path("/mock")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllFromMockService() {
        Set<DigitalLabOrderDto> result = dloService.getAll();
        for (DigitalLabOrderDto c : result) {
            System.out.println(c);

        }
        return "We have " + result.size() + " dlo pulled";
    }

    @Path("/mock/examples")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllFromMockExamplesService() {
        Set<Example> result = dloService.getAllExamples();
        return "We have " + result.size() + " examples pulled";
    }

    @Path("/process/all")
    @GET
    public List<SRM_Process> getAllProcess() {

        return processRepo.findAll();

    }

    @Path("/process/{containerId}")
    @GET
    public List<SRM_Process> getAllProcess(@PathParam("containerId") String containerId) {

        return processRepo.findByContainerId(containerId);

    }

}
