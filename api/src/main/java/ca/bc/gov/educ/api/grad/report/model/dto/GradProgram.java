package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data
@Component
public class GradProgram extends BaseModel {

	private String programCode; 
	private String programName; 
	private Date programStartDate;	
	private Date programEndDate;
	
	@Override
	public String toString() {
		return "GradProgram [programCode=" + programCode + ", programName=" + programName + ", programStartDate="
				+ programStartDate + ", programEndDate=" + programEndDate + "]";
	}	
}
