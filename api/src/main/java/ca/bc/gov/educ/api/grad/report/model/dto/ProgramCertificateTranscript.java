package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProgramCertificateTranscript extends BaseModel {

	private UUID pcId;
	private String graduationProgramCode;
	private String schoolCategoryCode;
	private String certificateTypeCode;
	private String transcriptTypeCode;
}
