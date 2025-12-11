package ca.bc.gov.educ.api.grad.report.service.v2;

import ca.bc.gov.educ.api.grad.report.constants.ReportApiConstants;
import ca.bc.gov.educ.api.grad.report.constants.SchoolCategoryCodes;
import ca.bc.gov.educ.api.grad.report.model.dto.SchoolClob;
import ca.bc.gov.educ.api.grad.report.model.dto.institute.School;
import ca.bc.gov.educ.api.grad.report.service.RESTService;
import ca.bc.gov.educ.api.grad.report.util.JsonTransformer;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
public class SchoolService {
	ReportApiConstants educGraduationApiConstants;
	RESTService restService;
	JsonTransformer jsonTransformer;
	WebClient graduationApiClient;
	@Autowired
	public SchoolService(ReportApiConstants educGraduationApiConstants, RESTService restService, JsonTransformer jsonTransformer,
						 @Qualifier("graduationReportApiClient") WebClient graduationApiClient) {
		this.educGraduationApiConstants = educGraduationApiConstants;
		this.restService = restService;
		this.jsonTransformer = jsonTransformer;
		this.graduationApiClient = graduationApiClient;
	}

	public boolean isIndependentSchool(School school) {
		return List.of(SchoolCategoryCodes.INDEPEND.getCode(), SchoolCategoryCodes.INDP_FNS.getCode())
				.contains(school.getSchoolCategoryCode());
	}

	public SchoolClob getSchoolClob(String schoolId) {
		if (StringUtils.isBlank(schoolId)) return null;
		return getSchoolClob(UUID.fromString(schoolId));
	}

	public SchoolClob getSchoolClob(UUID schoolId) {
		if (schoolId == null) return null;
		return this.restService.get(String.format(educGraduationApiConstants.getSchoolClobBySchoolIdUrl(),schoolId),
				SchoolClob.class, graduationApiClient);
	}
}

