package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StudentCredentialDistribution {

	private UUID id;
	private String credentialTypeCode;
	private UUID studentID;
	private String paperType;

	public StudentCredentialDistribution(UUID id, String credentialTypeCode, UUID studentID,String paperType) {
		this.id = id;
		this.credentialTypeCode = credentialTypeCode;
		this.studentID = studentID;
		this.paperType = paperType;
	}
}
