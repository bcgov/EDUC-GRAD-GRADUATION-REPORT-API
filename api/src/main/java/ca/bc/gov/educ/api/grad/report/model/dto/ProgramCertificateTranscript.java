package ca.bc.gov.educ.api.grad.report.model.dto;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProgramCertificateTranscript extends BaseModel {

	private UUID pcId;
	private String graduationProgramCode;
	private String schoolCategoryCode;
	private String certificateTypeCode;
	private String transcriptTypeCode;
}
