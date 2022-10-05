package inveox.srm.infrastructure.imageprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class AnalyzerResponseDto {

    private Integer num_samples;
    
}
