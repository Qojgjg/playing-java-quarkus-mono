package inveox.exportservice.infrastructure.inbound.dlo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ContainerItemDto {
    
    BodyPartDto mainBodyPart;


    List<SamplesDto> samples;

}