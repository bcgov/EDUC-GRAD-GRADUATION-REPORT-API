package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GradCareerProgram extends BaseModel {
	
	private String code; 
	private String description; 
	private String startDate; 
	private String endDate;
	
	public String getCode() {
    	return code != null ? code.trim():null;
    }
	
	public String getDescription() {
		return description != null ? description.trim(): null;
	}
	
	@Override
	public String toString() {
		return "GradCareerProgram [code=" + code + ", description=" + description + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
	
	
	
}
