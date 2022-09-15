package inveox.srm.domain.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import inveox.srm.domain.model.enums.SRM_Event_Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "srm_events")
public class SRM_Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    Instant sendTime;
    Integer num_retry;
    Instant last_retry;
    String contentReference;

    SRM_Event_Status status;

}
