package inveox.srm.application;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import inveox.srm.domain.model.Container;
import inveox.srm.domain.model.DigitalLabOrder;
import inveox.srm.domain.model.enums.ContainerStatus;
import inveox.srm.repository.DigitalLabOrderRepository;

@ApplicationScoped
@Transactional
public class ContanierProcessorService {


    private final DigitalLabOrderRepository dloRepo;

    public ContanierProcessorService(DigitalLabOrderRepository dloRepo) {
        this.dloRepo = dloRepo;
    }

    public void processContainer(String containerId) {

        DigitalLabOrder dlo=dloRepo.findByContainersContainerId(containerId);

        Container cont=dlo.getThisContainer(containerId);

        cont.setStatus(ContainerStatus.SCANNED_NO_ISSUES);

        dloRepo.save(dlo);

        System.out.println("PUT in "+ cont);

    }
    
}
