package inveox.exportservice.infrastructure.inbound.patient.dto;

import java.time.LocalDate;

import inveox.exportservice.infrastructure.inbound.patient.dto.enums.DmpMarking;
import inveox.exportservice.infrastructure.inbound.patient.dto.enums.InsuranceType;
import inveox.exportservice.infrastructure.inbound.patient.dto.enums.SpecialGroupsOfPeople;
import inveox.exportservice.infrastructure.inbound.patient.dto.enums.TypeOfInsured;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsuranceDto {
    
    private String id;

    private InsuranceType type;

    private String personalInsuranceNumber;

    private String providerName;

    private String providerNumber;

    private LocalDate expiryDate;

    private Boolean defaultFlag;

    private TypeOfInsured associationToInsurance;

    private SpecialGroupsOfPeople specialGroupsOfPeople;

    private DmpMarking dmpMarking;
}

