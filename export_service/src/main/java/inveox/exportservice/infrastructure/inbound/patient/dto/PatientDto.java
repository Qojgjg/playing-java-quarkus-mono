package inveox.exportservice.infrastructure.inbound.patient.dto;

import java.util.List;

import inveox.exportservice.infrastructure.inbound.patient.dto.enums.GenderType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class PatientDto {

    public static  String NAME_SEPARATOR = " ";

    private  String id;

    private  Integer businessId;

    private  Integer externalBusinessId;

    private  String externalId;

    private  String givenNames;

    private  String familyName;

    private  String dateOfBirth;

    private  GenderType gender;

    private  String address;

    private  List<InsuranceDto> insurances;

    
}
