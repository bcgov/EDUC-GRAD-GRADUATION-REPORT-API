package ca.bc.gov.educ.api.grad.report.model.dto.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;


@Data
@EqualsAndHashCode(callSuper = true)
public class DistrictContact extends BaseModel {
    @Id
    private String districtContactId;
    private String districtId;
    private String districtContactTypeCode;
    private String phoneNumber;
    private String jobTitle;
    private String phoneExtension;
    private String alternatePhoneNumber;
    private String alternatePhoneExtension;
    private String email;
    private String firstName;
    private String lastName;
    private String effectiveDate;
    private String expiryDate;
}
