package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

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
