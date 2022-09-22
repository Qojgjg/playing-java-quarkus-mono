package inveox.srm.application.dto;

import inveox.srm.domain.model.enums.ContainerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ContainerDataDto {


    private String dloId;

    private String containerId;

    private ContainerStatus status;

    private String mainBodyPart;
 
    private Integer samples;

    private Integer containersInDlo;

    private String bussinessId;

}
