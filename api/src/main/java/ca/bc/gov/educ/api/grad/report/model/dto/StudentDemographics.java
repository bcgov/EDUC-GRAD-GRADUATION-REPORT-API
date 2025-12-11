package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StudentDemographics {    
    private String studentID;
	private String pen;
	private String legalFirstName;//change this in report
	private String legalMiddleNames;//change this in report
	private String legalLastName; //change this in report
	private String dob; //change this in report
	private String sexCode; //change this in report
	private String genderCode;
	private String usualFirstName;
	private String usualMiddleNames;
	private String usualLastName;
	private String email;
	private String emailVerified;
	private String deceasedDate;
	private String postalCode;
	private String mincode; //change this in report
	private String localID; //change this in report
	private String gradeCode;
	private String gradeYear;
	private String demogCode;
	private String statusCode;
	private String memo;
	private String trueStudentID;
	private String program; //change this in report
	private String schoolOfRecord;
	private String schoolOfRecordName; //Change this in report
	private String schoolOfRecordindependentAffiliation;
	private String studentGrade;
	private String studentStatus;
}
