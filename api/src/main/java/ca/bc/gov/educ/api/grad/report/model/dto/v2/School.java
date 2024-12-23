package ca.bc.gov.educ.api.grad.report.model.dto.v2;

import ca.bc.gov.educ.api.grad.report.model.dto.BaseModel;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class School extends BaseModel {
	private String schoolId;
	private String districtId;
	private String mincode;
	private String independentAuthorityId;
	private String schoolNumber;
	private String faxNumber;
	private String phoneNumber;
	private String email;
	private String website;
	private String displayName;
	private String displayNameNoSpecialChars;
	private String schoolReportingRequirementCode;
	private String schoolOrganizationCode;
	private String schoolCategoryCode;
	private String facilityTypeCode;
	private String openedDate;
	private String closedDate;
	private boolean canIssueTranscripts;
	private boolean canIssueCertificates;
}
