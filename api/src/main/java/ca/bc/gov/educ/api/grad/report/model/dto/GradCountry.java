package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class GradCountry extends BaseModel {

	private String countryCode; 	
	private String countryName;	
	private String srbCountryCode;
	
	@Override
	public String toString() {
		return "GradCountry [countryCode=" + countryCode + ", countryName=" + countryName + ", srbCountryCode="
				+ srbCountryCode + "]";
	}
	
}
