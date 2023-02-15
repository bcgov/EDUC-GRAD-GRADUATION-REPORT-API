package ca.bc.gov.educ.api.grad.report.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CERTIFICATE_TYPE_CODE")
public class GradCertificateTypesEntity extends BaseEntity {
   
	@Id
	@Column(name = "CERTIFICATE_TYPE_CODE", nullable = false)
    private String code;

	@Column(name = "LABEL", nullable = true)
	private String label;

	@Column(name = "LANGUAGE", nullable = true)
	private String language;

	@Column(name = "DESCRIPTION", nullable = true)
    private String description;

	@Column(name = "DISPLAY_ORDER", nullable = true)
	private Integer displayOrder;

	@Column(name = "EFFECTIVE_DATE", nullable = true)
	private Date effectiveDate;

	@Column(name = "EXPIRY_DATE", nullable = true)
	private Date expiryDate;

	@Column(name = "PAPER_TYPE", nullable = true)
	private String paperType;
	
}