package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class StudentCredentialDistribution {

	private UUID id;
	private String credentialTypeCode;
	private UUID studentID;
	private String paperType;
	private String documentStatusCode;
	private Date distributionDate;

	public StudentCredentialDistribution(UUID id, String credentialTypeCode, UUID studentID,String paperType, String documentStatusCode, Date distributionDate) {
		this.id = id;
		this.credentialTypeCode = credentialTypeCode;
		this.studentID = studentID;
		this.paperType = paperType;
		this.documentStatusCode = documentStatusCode;
		this.distributionDate = distributionDate;
	}
}
