package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class GradCertificateTypes extends BaseModel {

	private String code;
	private String label;
	private String language;
	private String description;
	private Integer displayOrder;
	private Date effectiveDate;
	private Date expiryDate;
	private String paperType;
	
	@Override
	public String toString() {
		return "GradReportTypes [code=" + code + ", label=" + label + ", language=" + language + ", description=" + description + ", displayOrder="
				+ displayOrder + ", effectiveDate=" + effectiveDate + ", expiryDate=" + expiryDate + "]";
	}
	
	
}
