package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.District;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.School;
import ca.bc.gov.educ.api.grad.report.service.RESTService;
import ca.bc.gov.educ.api.grad.report.util.EducGradReportApiConstants;
import ca.bc.gov.educ.api.grad.report.util.JsonTransformer;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
public class InstituteService {
	EducGradReportApiConstants educGraduationApiConstants;
	RESTService restService;
	JsonTransformer jsonTransformer;
	private final WebClient graduationServiceWebClient;
	@Autowired
	public InstituteService(EducGradReportApiConstants educGraduationApiConstants, RESTService restService,
							JsonTransformer jsonTransformer, @Qualifier("graduationReportApiClient") WebClient graduationServiceWebClient) {
		this.educGraduationApiConstants = educGraduationApiConstants;
		this.restService = restService;
		this.jsonTransformer = jsonTransformer;
		this.graduationServiceWebClient = graduationServiceWebClient;
	}

	public School getSchool(UUID schoolId) {
		var response = this.restService.get(String.format(educGraduationApiConstants.getSchoolBySchoolIdUrl(), schoolId),
				School.class, graduationServiceWebClient);
		return jsonTransformer.convertValue(response, new TypeReference<>() {});
	}

	public District getDistrict(UUID districtId) {
		var response = this.restService.get(String.format(educGraduationApiConstants.getDistrictByDistrictIdUrl(), districtId),
				District.class, graduationServiceWebClient);
		return jsonTransformer.convertValue(response, new TypeReference<>() {});
	}
	public List<School> getAllSchools() {
		var response = this.restService.get(educGraduationApiConstants.getAllSchoolsUrl(), List.class, graduationServiceWebClient);
		return jsonTransformer.convertValue(response, new TypeReference<>() {});
	}
}

