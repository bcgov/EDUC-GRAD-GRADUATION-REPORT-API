package ca.bc.gov.educ.api.grad.report.model.dto;

import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentTranscriptValidationKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GradStudentTranscriptValidation extends BaseModel {

	private GradStudentTranscriptValidationKey studentTranscriptValidationKey;
	private Long batchId;
    private String transcriptTypeCode;
	private String documentStatusCode;
	private String validationResult;

}