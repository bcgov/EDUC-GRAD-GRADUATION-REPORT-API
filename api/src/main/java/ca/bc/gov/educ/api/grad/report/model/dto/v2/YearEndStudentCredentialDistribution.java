package ca.bc.gov.educ.api.grad.report.model.dto.v2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class YearEndStudentCredentialDistribution {

    private UUID studentID;
    private String paperType;
    private String certificateTypeCode;
    private String reportingSchoolTypeCode;

}
