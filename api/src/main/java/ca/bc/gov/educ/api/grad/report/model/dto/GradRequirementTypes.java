package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

@Data
@EqualsAndHashCode(callSuper = false)
@Component
public class GradRequirementTypes extends BaseModel {

	private String code;	
	private String description;
	@Override
	public String toString() {
		return "GradRequirementTypes [code=" + code + ", description=" + description + "]";
	}
	
	
}
