package inveox.srm.domain.model;

import java.time.Instant;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import inveox.srm.domain.model.enums.DigitalLabOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "laborders")
public class DigitalLabOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String uuid;
    private String businessId;
    private Instant lastStatusChanged;
    private DigitalLabOrderStatus status;


   // @OneToMany(mappedBy = "laborder", fetch = FetchType.LAZY, cascade =CascadeType.REMOVE)  
   @OneToMany
    private Set<Contanier> contaniers;

}
