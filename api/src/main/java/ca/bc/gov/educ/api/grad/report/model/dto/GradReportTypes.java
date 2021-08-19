package ca.bc.gov.educ.api.grad.report.model.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class GradReportTypes extends BaseModel {

	private String code;
	private String label;
	private String description;
	private Integer displayOrder;
	private Date effectiveDate;
	private Date expiryDate;
	
	@Override
	public String toString() {
		return "GradReportTypes [code=" + code + ", label=" + label + ", description=" + description + ", displayOrder="
				+ displayOrder + ", effectiveDate=" + effectiveDate + ", expiryDate=" + expiryDate + "]";
	}	
}
