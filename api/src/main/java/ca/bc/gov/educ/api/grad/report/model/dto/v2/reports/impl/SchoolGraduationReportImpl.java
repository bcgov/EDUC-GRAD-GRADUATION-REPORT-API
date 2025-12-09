package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.District;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.GraduationReport;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.School;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.Student;
import ca.bc.gov.educ.api.grad.report.reporting.jasper.impl.ReportImpl;

import java.util.List;

public class SchoolGraduationReportImpl extends ReportImpl implements GraduationReport {

    private List<Student> students;
    private List<School> schools;
    private School school;
    private District district;

    /**
     * Constructs a report implementation based with a report template name.
     *
     * @param name Report template name to run.
     */
    public SchoolGraduationReportImpl(String name) {
        super(name);
    }

    @Override
    public List<Student> getStudents() {
        return students;
    }

    @Override
    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

    @Override
    public List<School> getSchools() {
        return this.schools;
    }

    @Override
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public void setDistrict(District district) {
        this.district = district;
    }

    /**
     * Returns the main object used to fill the report. All the reports use a
     * single student or school instance populated with necessary data to fulfill the
     * report's data requirements.
     *
     * @return this.student
     */
    @Override
    public Object getDataSource() {
        return this.school;
    }

    @Override
    public boolean isPreview() {
        return false;
    }
}
