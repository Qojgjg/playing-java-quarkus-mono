package inveox.srm.application;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import inveox.srm.domain.model.Container;
import inveox.srm.domain.model.DigitalLabOrder;
import inveox.srm.domain.model.SRM_Event;
import inveox.srm.domain.model.SRM_Process;
import inveox.srm.domain.model.enums.ContainerStatus;
import inveox.srm.domain.model.enums.SRM_Event_Status;
import inveox.srm.repository.DigitalLabOrderRepository;
import inveox.srm.repository.SRM_ProcessRepository;

@ApplicationScoped
public class ContanierProcessorService {

    @Inject
    DigitalLabOrderRepository dloRepo;

    @Inject
    SRM_ProcessRepository processRepo;
         
    public void processContainer(String containerId) throws Exception {

        DigitalLabOrder dlo=dloRepo.findByContainersContainerId(containerId);

        if (dlo==null) throw new Exception("Container Id not found");

        Container cont=dlo.getThisContainer(containerId);

        cont.setStatus(ContainerStatus.IN_PROCESS);
        System.out.println("PUT in "+ cont);


        //Create SRM Process
        SRM_Process process= new SRM_Process();
        process.setContainerId(containerId);

        //Process Image Analisis

        Integer samplesFounded=countSamples();

        process.setImageAdquired(Instant.now());
        process.setCountingResult(samplesFounded);

        //Update Container Status
        if (cont.getNum_samples().equals(samplesFounded)){
            //If all good. 
            cont.setStatus(ContainerStatus.SCANNED_NO_ISSUES);            
        }else {
            //Differents samples founded.
            cont.setStatus(ContainerStatus.SCANNED_COUNT_DIFF);            
        }

        List<SRM_Event> events= new LinkedList<>();

        SRM_Event eventBestImage=new SRM_Event();
        eventBestImage.setContentReference("contentReference BEST_IMAGE_PENDING");
        eventBestImage.setStatus(SRM_Event_Status.BEST_IMAGE_PENDING);

        SRM_Event eventDLO=new SRM_Event();
        eventDLO.setContentReference("contentReference DLO_CONTANIER_RESULT_SENT_PENDING");
        eventDLO.setStatus(SRM_Event_Status.DLO_CONTANIER_RESULT_SENT_PENDING);

        SRM_Event eventMultiframe=new SRM_Event();
        eventMultiframe.setContentReference("contentReference MULTIFRAMES_IMAGES_PENDING");
        eventMultiframe.setStatus(SRM_Event_Status.MULTIFRAMES_IMAGES_PENDING);

//        processRepo.save(process);
        
        events.add(eventBestImage);
        events.add(eventDLO);
        events.add(eventMultiframe);

        process.setEvents(events);

        dloRepo.save(dlo);
        processRepo.save(process);
        


        


/*
        private Instant imageAdquired;
        private Instant countingResultModificationTimeByUser;
    
        private Integer countingResultByUser;
        private Integer countingResultBySRM;
        private Integer countingResult;

         */

        //schedule events

        

    }

    private Integer countSamples() {
        return 3;
    }
    
}
