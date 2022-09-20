package inveox.srm.infrastructure.inbound.dlo.dto;

import java.util.List;

import inveox.srm.domain.model.enums.ContainerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ContainerDto {


    private String containerId;

    private ContainerStatus status;

    String mainBodyPart;

    Integer samples;

}
