package inveox.exportservice.infrastructure.inbound.dlo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ExaminationSpecialistDto {

    private Object statusDetails;
//    public ArrayList<Organization> organizations;
    private String title;
    private String givenNames;
    private String familyName;
    private String doctorNumber;
    private String jobTitle;
    private String status;
    private String email;
   // public ArrayList<Role> roles;
    private String id;
    private boolean termsApproved;
    
}
