package ca.bc.gov.educ.api.grad.report.model.dto.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.BaseModel;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class District extends BaseModel {
    private String districtId;
    private String districtNumber;
    private String faxNumber;
    private String phoneNumber;
    private String email;
    private String website;
    private String displayName;
    private String districtRegionCode;
    private String districtStatusCode;
    private List<DistrictContact> contacts;
    private List<DistrictAddress> addresses;
    private List<Note> notes;
}
