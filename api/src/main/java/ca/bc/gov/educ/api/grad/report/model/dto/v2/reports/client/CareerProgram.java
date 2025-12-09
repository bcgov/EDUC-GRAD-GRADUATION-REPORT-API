package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.client;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class CareerProgram implements Serializable {

	private String careerProgramCode;	
	private String careerProgramName;
	
}
