package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import java.util.Date;

public interface StudentReport extends Report {

    void setStudent(Student student);

    void setSchool(School school);

    void setSchool(School school, String logoCode);

    void setReportDate(Date date);
}
