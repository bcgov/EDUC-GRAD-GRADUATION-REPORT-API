package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SchoolStudentCredentialDistribution {

	private UUID id;
	private String credentialTypeCode;
	private UUID studentID;

	public SchoolStudentCredentialDistribution(UUID id, String credentialTypeCode, UUID studentID) {
		this.id = id;
		this.credentialTypeCode = credentialTypeCode;
		this.studentID = studentID;
	}
}
