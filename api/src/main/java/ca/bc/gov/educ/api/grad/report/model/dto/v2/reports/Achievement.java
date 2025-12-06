package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;
import ca.bc.gov.educ.api.grad.report.constants.GraduationProgramCode;

import java.util.Date;
import java.util.List;

public interface Achievement extends DomainEntity {
    
    List<AchievementResult> getResults();

    List<AchievementResult> getResults(GraduationProgramCode graduationProgramCode);

    Date getIssueDate();

    boolean isEmpty();
}
