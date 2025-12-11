package ca.bc.gov.educ.api.grad.report.service.report;

import ca.bc.gov.educ.api.grad.report.constants.ReportApiConstants;
import ca.bc.gov.educ.api.grad.report.model.dto.StudentOptionalProgram;
import ca.bc.gov.educ.api.grad.report.service.RESTService;
import ca.bc.gov.educ.api.grad.report.util.JsonTransformer;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
public class OptionalProgramService {

	RESTService restService;
	JsonTransformer jsonTransformer;
	ReportApiConstants educGraduationApiConstants;
	WebClient graduationApiClient;
	WebClient educStudentApiClient;

	@Autowired
	public OptionalProgramService(RESTService restService, JsonTransformer jsonTransformer,
								  ReportApiConstants educGraduationApiConstants,
                                  @Qualifier("graduationReportApiClient") WebClient graduationApiClient,
                                  @Qualifier("gradReportEducStudentApiClient") WebClient educStudentApiClient) {
		this.restService = restService;
		this.jsonTransformer = jsonTransformer;
		this.educGraduationApiConstants = educGraduationApiConstants;
		this.graduationApiClient = graduationApiClient;
		this.educStudentApiClient = educStudentApiClient;
	}

	public List<StudentOptionalProgram> getStudentOptionalPrograms(UUID studentID) {
		var response = restService.get(String.format(educGraduationApiConstants.getStudentOptionalPrograms(),
				studentID), List.class, graduationApiClient);
		return jsonTransformer.convertValue(response, new TypeReference<>() {
        });
	}
}
