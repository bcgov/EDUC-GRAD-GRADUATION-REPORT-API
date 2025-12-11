package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class GraduationMessages {
	private String gradProgram;
	private String gradMessage;
	private String honours;
	private String gpa;
	private boolean hasOptionalProgram;
	private boolean hasCareerProgram;
	private boolean hasCertificates;
	private List<CodeDTO> optionalProgram;
	private List<CodeDTO> careerProgram;
	private List<CodeDTO> certificateProgram;
	private List<GradRequirement> nonGradReasons;
}
