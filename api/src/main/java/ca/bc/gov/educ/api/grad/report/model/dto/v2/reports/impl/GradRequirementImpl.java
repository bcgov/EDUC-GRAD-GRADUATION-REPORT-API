package ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.impl;

import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.AchievementCourse;
import ca.bc.gov.educ.api.grad.report.model.dto.v2.reports.GradRequirement;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@JsonSubTypes({
        @JsonSubTypes.Type(value = AchievementCourseImpl.class)
})
public class GradRequirementImpl implements GradRequirement {
    private String code;
    private String description;
    private List<AchievementCourse> courseDetails;
    private JRBeanCollectionDataSource courseDetailsdataSource;

    public GradRequirementImpl() {
    }

    @Override
    public JRBeanCollectionDataSource getCourseDetailsdataSource() {
        return new JRBeanCollectionDataSource(courseDetails, false);
    }
}
