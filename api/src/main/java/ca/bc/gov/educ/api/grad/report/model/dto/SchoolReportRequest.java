package ca.bc.gov.educ.api.grad.report.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class SchoolReportRequest {

	private List<GraduationStudentRecord> studentList;
}
