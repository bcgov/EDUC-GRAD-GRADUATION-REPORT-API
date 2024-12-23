package ca.bc.gov.educ.api.grad.report.model.dto.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Note extends BaseModel {
    private String noteId;
    private String schoolId;
    private String districtId;
    private String independentAuthorityId;
    private String content;
}
