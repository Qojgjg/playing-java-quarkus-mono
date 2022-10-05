package inveox.srm.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import inveox.srm.domain.model.Container;
import inveox.srm.domain.model.DigitalLabOrder;
import inveox.srm.domain.model.SRM_Event;
import inveox.srm.domain.model.SRM_Process;
import inveox.srm.domain.model.enums.Actor;
import inveox.srm.domain.model.enums.ContainerStatus;
import inveox.srm.domain.model.enums.SRM_Event_Status;
import inveox.srm.infrastructure.imageprocessing.dto.AnalyzerResponseDto;
import inveox.srm.repository.DigitalLabOrderRepository;
import inveox.srm.repository.SRM_ProcessRepository;

@ApplicationScoped
public class ContanierProcessorService {

    @Inject
    DigitalLabOrderRepository dloRepo;

    @Inject
    SRM_ProcessRepository processRepo;

    public void processContainer(String containerId) throws IOException, InterruptedException   {

        DigitalLabOrder dlo = dloRepo.findByContainersContainerId(containerId);

        if (dlo == null)
            throw new NoSuchElementException("Container Id not found");

        Container cont = dlo.getThisContainer(containerId);

        cont.setStatus(ContainerStatus.IN_PROCESS);
        System.out.println("PUT in " + cont);

        // Create SRM Process
        SRM_Process process = new SRM_Process();
        process.setContainerId(containerId);

        // Process Image Analisis

        Integer samplesFounded = countSamples();

        process.setImageAdquired(Instant.now());
        process.setCountingResult(samplesFounded);

        // Update Container Status
        if (cont.getNum_samples().equals(samplesFounded)) {
            // If all good.
            cont.setStatus(ContainerStatus.SCANNED_NO_ISSUES);
        } else {
            // Differents samples founded.
            cont.setStatus(ContainerStatus.SCANNED_COUNT_DIFF);
        }

        List<SRM_Event> events = new LinkedList<>();

        SRM_Event eventBestImage = new SRM_Event();
        eventBestImage.setContentReference("contentReference BEST_IMAGE_PENDING");
        eventBestImage.setStatus(SRM_Event_Status.BEST_IMAGE_PENDING);

        SRM_Event eventDLO = new SRM_Event();
        eventDLO.setContentReference("contentReference DLO_CONTANIER_RESULT_SENT_PENDING");
        eventDLO.setStatus(SRM_Event_Status.DLO_CONTANIER_RESULT_SENT_PENDING);

        SRM_Event eventMultiframe = new SRM_Event();
        eventMultiframe.setContentReference("contentReference MULTIFRAMES_IMAGES_PENDING");
        eventMultiframe.setStatus(SRM_Event_Status.MULTIFRAMES_IMAGES_PENDING);

        // processRepo.save(process);

        events.add(eventBestImage);
        events.add(eventDLO);
        events.add(eventMultiframe);

        process.setEvents(events);
        process.setCountedBy(Actor.SRM);

        dloRepo.save(dlo);
        processRepo.save(process);
        /*
         * private Instant imageAdquired;
         * private Instant countingResultModificationTimeByUser;
         * 
         * private Integer countingResultByUser;
         * private Integer countingResultBySRM;
         * private Integer countingResult;
         * 
         */

        // schedule events

    }

    private Integer countSamples() throws IOException, InterruptedException  {
        // ProcessBuilder processBuilder = new
        // ProcessBuilder("python3.10","/Users/sebastianscotti/python_ws/image_processing/../ppal.py");

        ProcessBuilder processBuilder = new ProcessBuilder();

        // processBuilder.command("python3.10", "--version");

        processBuilder.command("python3.10", "../../python_ws/image_processing/ppal.py",
                "../../python_ws/1_container_42_dataset_0_tissue_type_5/");

        processBuilder.redirectErrorStream(true);

        Process process;

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
            return res.getNum_samples();
        } else {
            throw new InterruptedException("Problem with script");
        }

    }

}
