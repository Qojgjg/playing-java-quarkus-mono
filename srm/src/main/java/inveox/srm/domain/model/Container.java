package inveox.srm.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String containerId;
    private String mainBodyPart;
    private Integer num_samples;
    
}
