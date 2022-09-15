package inveox.srm.domain.model;

import java.time.Instant;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import inveox.srm.domain.model.enums.Actor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "srm_processes")
public class SRM_Process {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Instant imageAdquired;
    private Instant countingResultModificationTimeByUser;

    private Integer countingResultByUser;
    private Integer countingResultBySRM;
    private Integer countingResult;

    //SRM or User
    private Actor countedBy;

    @OneToMany
    List<SRM_Event> events;


    
}
