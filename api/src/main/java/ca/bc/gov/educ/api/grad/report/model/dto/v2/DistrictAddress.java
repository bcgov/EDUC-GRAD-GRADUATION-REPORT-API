package ca.bc.gov.educ.api.grad.report.model.dto.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DistrictAddress extends BaseModel {

    private String districtAddressId;
    private String districtId;
    private String addressTypeCode;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postal;
    private String provinceCode;
    private String countryCode;

}
