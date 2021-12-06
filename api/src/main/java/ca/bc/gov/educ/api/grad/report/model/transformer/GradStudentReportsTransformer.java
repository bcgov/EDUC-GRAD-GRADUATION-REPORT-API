package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradStudentReports;
import ca.bc.gov.educ.api.grad.report.model.entity.GradStudentReportsEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class GradStudentReportsTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradStudentReports transformToDTO (GradStudentReportsEntity gradStudentReportEntity) {
    	return modelMapper.map(gradStudentReportEntity, GradStudentReports.class);
    }

    public GradStudentReports transformToDTO ( Optional<GradStudentReportsEntity> gradStudentReportEntity ) {
    	GradStudentReportsEntity cae = new GradStudentReportsEntity();
        if (gradStudentReportEntity.isPresent())
            cae = gradStudentReportEntity.get();
        return modelMapper.map(cae, GradStudentReports.class);
    }

	public List<GradStudentReports> transformToDTO (Iterable<GradStudentReportsEntity> gradStudentReportsEntities ) {
		List<GradStudentReports> gradStudentReportsList = new ArrayList<>();
        for (GradStudentReportsEntity gradStudentReportsEntity : gradStudentReportsEntities) {
        	GradStudentReports gradStudentReports = modelMapper.map(gradStudentReportsEntity, GradStudentReports.class);
        	gradStudentReportsList.add(gradStudentReports);
        }
        return gradStudentReportsList;
    }

    public GradStudentReportsEntity transformToEntity(GradStudentReports gradStudentReports) {
        return modelMapper.map(gradStudentReports, GradStudentReportsEntity.class);
    }
}
