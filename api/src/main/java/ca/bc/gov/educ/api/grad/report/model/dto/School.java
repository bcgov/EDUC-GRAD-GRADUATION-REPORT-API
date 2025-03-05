package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class School {

	private String minCode;
	private String schoolId;
	private String schoolName;
	private String districtId;
	private String districtName;
	private String transcriptEligibility;
	private String certificateEligibility;
	private String address1;
	private String address2;
	private String city;
	private String provCode;
	private String countryCode;
	private String postal;
	private String openFlag;
	private String schoolCategoryCode;
	private String schoolCategoryLegacyCode;

	@Override
	public String toString() {
		return "School [minCode=" + minCode + ", schoolId=" + schoolId + ", schoolName=" + schoolName + ", schoolCategoryCode=" + schoolCategoryCode + ", schoolCategoryLegacyCode=" + schoolCategoryLegacyCode
				+ ", districtId=" + districtId + ", districtName=" + districtName + ", transcriptEligibility=" + transcriptEligibility + ", certificateEligibility=" + certificateEligibility
				+ ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", provCode=" + provCode + ", countryCode=" + countryCode + ", postal=" + postal + ", openFlag=" + openFlag
				+ "]";
	}
}
