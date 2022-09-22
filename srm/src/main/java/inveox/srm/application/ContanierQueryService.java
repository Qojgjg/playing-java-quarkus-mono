package inveox.srm.application;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import inveox.srm.application.dto.ContainerDataDto;
import inveox.srm.domain.model.Container;
import inveox.srm.domain.model.DigitalLabOrder;
import inveox.srm.repository.DigitalLabOrderRepository;

@ApplicationScoped
@Transactional
public class ContanierQueryService {

    private final DigitalLabOrderRepository dloRepo;

    public ContanierQueryService(DigitalLabOrderRepository dloRepo) {
        this.dloRepo = dloRepo;
    }

    public ContainerDataDto findContainerId(String containerId) throws Exception{

        DigitalLabOrder dlo = dloRepo.findByContainersContainerId(containerId);

        if (dlo==null) throw new Exception("Container Id not found");

        ContainerDataDto contDto = new ContainerDataDto();

        Container conta = dlo.getThisContainer(containerId);

        if (conta != null) {
            if (conta.getContainerId().equals(containerId)) {

                if (dlo.getBusinessId() != null) {
                    contDto.setBussinessId(dlo.getBusinessId());
                }
                if (conta.getContainerId() != null) {
                    contDto.setContainerId(conta.getContainerId());
                }
                if (dlo.getUuid() != null) {
                    contDto.setDloId(dlo.getUuid());
                }
                if (conta.getMainBodyPart() != null) {
                    contDto.setMainBodyPart(conta.getMainBodyPart());
                }
                contDto.setSamples(conta.getNum_samples());
                contDto.setStatus(conta.getStatus());
                contDto.setContainersInDlo(dlo.getContainers().size());
            }

        }
        return contDto;

    }

}
