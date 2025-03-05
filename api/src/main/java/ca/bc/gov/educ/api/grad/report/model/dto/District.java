package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class District {

    private String districtNumber;
    private String displayName;

    public String getDistrictName() {
        return displayName;
    }
}
