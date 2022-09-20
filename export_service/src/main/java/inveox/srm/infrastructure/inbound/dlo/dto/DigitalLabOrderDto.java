package inveox.srm.infrastructure.inbound.dlo.dto;

import java.time.Instant;
import java.util.List;

import inveox.srm.domain.model.enums.DigitalLabOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class DigitalLabOrderDto {


    private String id;

    private String bussinessId;

    private Instant lasStatusChanged;

    private DigitalLabOrderStatus status;

    private List<ContainerDto> containers;
}
