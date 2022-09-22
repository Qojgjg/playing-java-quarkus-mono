package inveox.srm.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import inveox.srm.domain.model.enums.ContainerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "containers")
public class Container { 

    @Id
    private String containerId;

    private String mainBodyPart;
    private Integer num_samples;
    private ContainerStatus status;
    
}
