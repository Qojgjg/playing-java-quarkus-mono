package inveox.exportservice.infrastructure.inbound.dlo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class AddressDto {
    
 //   @ApiModelProperty(example = "Schumacher Strasse 3")
    String line;

//    @ApiModelProperty(example = "House number")
    String line2;

//    @ApiModelProperty(example = "33444")
    String postalCode;

 //   @ApiModelProperty(example = "Muenchen")
    String city;

//    @Iso3CountryCode
//    @ApiModelProperty(example = "DEU", required = true)
    String country;

}
