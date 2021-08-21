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
@Table(name = "PROGRAM_CERTIFICATE")
public class ProgramCertificateEntity extends BaseEntity {
   
	@Id
	@Column(name = "PROGRAM_CERTIFICATE_ID", nullable = false)
    private UUID pcId;

	@Column(name = "PROGRAM_CODE", nullable = true)
	private String label;

	@Column(name = "SCHOOL_FUNDING_GROUP_CODE", nullable = true)
    private String schoolFundingGroupCode;

	@Column(name = "CERTIFICATE_TYPE_CODE", nullable = true)
	private String certificateTypeCode;

	@Column(name = "BC_MAIL_MEDIA_CODE", nullable = true)
	private String mediaCode;
	
}