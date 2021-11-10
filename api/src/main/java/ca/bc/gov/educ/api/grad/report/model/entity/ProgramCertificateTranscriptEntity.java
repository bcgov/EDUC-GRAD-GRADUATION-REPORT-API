package ca.bc.gov.educ.api.grad.report.model.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "PROGRAM_CERTIFICATE_TRANSCRIPT")
public class ProgramCertificateTranscriptEntity extends BaseEntity {
   
	@Id
	@Column(name = "PROGRAM_CERT_TRANS_ID", nullable = false)
    private UUID pcId;

	@Column(name = "GRADUATION_PROGRAM_CODE", nullable = true)
	private String graduationProgramCode;

	@Column(name = "SCHOOL_CATEGORY_CODE", nullable = true)
    private String schoolCategoryCode;

	@Column(name = "CERTIFICATE_TYPE_CODE", nullable = true)
	private String certificateTypeCode;

	@Column(name = "TRANSCRIPT_TYPE_CODE", nullable = true)
	private String transcriptTypeCode;

}