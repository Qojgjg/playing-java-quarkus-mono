package inveox.srm.application;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import inveox.srm.application.dto.ContainerDataDto;
import inveox.srm.domain.model.Container;
import inveox.srm.domain.model.DigitalLabOrder;

@ApplicationScoped
@Transactional
public class ContanierQueryService {

    @Inject
    EntityManager em;

    public ArrayList<ContainerDataDto> fingContainerId(String containerId) {

        TypedQuery<DigitalLabOrder> query = em
                .createQuery(
                        "select dlo FROM DigitalLabOrder dlo  join dlo.contaniers conts WHERE conts.containerId = :p1 ",
                        DigitalLabOrder.class)
                .setParameter("p1", containerId);
        List<DigitalLabOrder> result = query.getResultList();

        ArrayList<ContainerDataDto> containersData = new ArrayList<>();

        for (DigitalLabOrder dlo : result) {
            for (Container conta : dlo.getContaniers()) {
                if (conta.getContainerId() != null) {
                    if (conta.getContainerId().equals(containerId)) {
                        ContainerDataDto contDto = new ContainerDataDto();
                        if (dlo.getBusinessId()!=null){
                            contDto.setBussinessId(dlo.getBusinessId());
                        }
                        if (conta.getContainerId()!=null){
                            contDto.setContainerId(conta.getContainerId());
                        }
                        if (dlo.getUuid()!=null){
                            contDto.setDloId(dlo.getUuid());
                        }
                        if (conta.getMainBodyPart()!=null){
                            contDto.setMainBodyPart(conta.getMainBodyPart());
                        }
                        contDto.setSamples(conta.getNum_samples());
                        contDto.setStatus(conta.getStatus());
                        containersData.add(contDto);
                    }
                }
            }
        }
        return containersData;

    }

}
