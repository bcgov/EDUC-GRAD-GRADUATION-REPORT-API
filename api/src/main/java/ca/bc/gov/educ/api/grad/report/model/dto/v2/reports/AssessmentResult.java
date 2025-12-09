package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

public interface AssessmentResult {

    public String getStudentNumber();

    public String getAssessmentCode();

    public String getProficiencyScore();

    public String getSessionDate();

    public String getAssessmentName();

    public String getGradReqMet();

    public String getSpecialCase();

    public String getExceededWriteFlag();

    public Boolean getProjected();

}
