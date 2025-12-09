package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public interface GradRequirement {

    JRBeanCollectionDataSource getCourseDetailsdataSource();

    String getCode();

    String getDescription();

    java.util.List<AchievementCourse> getCourseDetails();

    void setCode(String code);

    void setDescription(String description);

    void setCourseDetails(java.util.List<AchievementCourse> courseDetails);

    void setCourseDetailsdataSource(JRBeanCollectionDataSource courseDetailsdataSource);
}
