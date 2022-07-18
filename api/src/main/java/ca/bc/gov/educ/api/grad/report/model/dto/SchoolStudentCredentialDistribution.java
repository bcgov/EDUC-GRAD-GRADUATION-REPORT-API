package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SchoolStudentCredentialDistribution {

	private UUID id;
	private String credentialTypeCode;
	private UUID studentID;
	private String documentStatusCode;

	public SchoolStudentCredentialDistribution(UUID id, String credentialTypeCode, UUID studentID,String documentStatusCode) {
		this.id = id;
		this.credentialTypeCode = credentialTypeCode;
		this.studentID = studentID;
		this.documentStatusCode=documentStatusCode;
	}
}
