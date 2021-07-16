package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GradProvince extends BaseModel {

	private String provCode;	
	private String provName;	
	private String countryCode;
	
	@Override
	public String toString() {
		return "GradProvince [provCode=" + provCode + ", provName=" + provName + ", countryCode=" + countryCode + "]";
	}				
}
