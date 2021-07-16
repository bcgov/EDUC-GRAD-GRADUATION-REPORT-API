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
    	GradStudentReports gradStudentReports = modelMapper.map(gradStudentReportEntity, GradStudentReports.class);
        return gradStudentReports;
    }

    public GradStudentReports transformToDTO ( Optional<GradStudentReportsEntity> gradStudentReportEntity ) {
    	GradStudentReportsEntity cae = new GradStudentReportsEntity();
        if (gradStudentReportEntity.isPresent())
            cae = gradStudentReportEntity.get();

        GradStudentReports gradStudentReports = modelMapper.map(cae, GradStudentReports.class);
        return gradStudentReports;
    }

	public List<GradStudentReports> transformToDTO (Iterable<GradStudentReportsEntity> gradStudentReportsEntities ) {
		List<GradStudentReports> gradStudentReportsList = new ArrayList<GradStudentReports>();
        for (GradStudentReportsEntity gradStudentReportsEntity : gradStudentReportsEntities) {
        	GradStudentReports gradStudentReports = new GradStudentReports();
        	gradStudentReports = modelMapper.map(gradStudentReportsEntity, GradStudentReports.class);            
        	gradStudentReportsList.add(gradStudentReports);
        }
        return gradStudentReportsList;
    }

    public GradStudentReportsEntity transformToEntity(GradStudentReports gradStudentReports) {
        GradStudentReportsEntity gradStudentReportsEntity = modelMapper.map(gradStudentReports, GradStudentReportsEntity.class);
        return gradStudentReportsEntity;
    }
}
