package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.GraduationStatus;
import ca.bc.gov.educ.api.grad.report.util.DateUtils;
import lombok.Data;

@Data
public class GraduationStatusImpl implements GraduationStatus {
    private String programCompletionDate;
    private String honours;
    private String gpa;
    private String studentGrade;
    private String studentStatus;
    private String studentStatusName;
    private String schoolAtGrad;
    private String schoolOfRecord;
    private String certificates;
    private String graduationMessage;
    private String programName;

    public String getProgramCompletionDate() {
        return DateUtils.formatProgramCompletionDate(programCompletionDate);
    }
}
