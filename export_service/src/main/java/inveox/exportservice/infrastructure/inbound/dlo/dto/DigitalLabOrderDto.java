package inveox.exportservice.infrastructure.inbound.dlo.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import inveox.exportservice.domain.model.enums.DigitalLabOrderStatus;
import inveox.exportservice.domain.model.enums.PatientAdmissionStatus;
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

    private String businessId;

    private Instant lastStatusChanged;

    private LocalDate examinationDate;

    private DigitalLabOrderStatus status;

    private PatientAdmissionStatus patientAdmissionStatus;

    private List<ContainerDto> containers;

    private PatientDto patient;

    private  InsuranceDto insurance;

    private ExaminationSpecialistDto examinationSpecialist;
}
