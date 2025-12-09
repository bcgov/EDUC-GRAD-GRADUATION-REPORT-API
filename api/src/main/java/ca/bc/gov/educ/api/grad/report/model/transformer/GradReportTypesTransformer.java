package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradReportTypes;
import ca.bc.gov.educ.api.grad.report.model.entity.ReportTypeCodeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class GradReportTypesTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradReportTypes transformToDTO (ReportTypeCodeEntity reportTypeCodeEntity) {
    	return modelMapper.map(reportTypeCodeEntity, GradReportTypes.class);
    }

    public GradReportTypes transformToDTO ( Optional<ReportTypeCodeEntity> gradReportTypesEntity ) {
    	ReportTypeCodeEntity cae = new ReportTypeCodeEntity();
        if (gradReportTypesEntity.isPresent())
            cae = gradReportTypesEntity.get();
        return modelMapper.map(cae, GradReportTypes.class);
    }

	public List<GradReportTypes> transformToDTO (Iterable<ReportTypeCodeEntity> gradReportTypesEntities ) {
		List<GradReportTypes> gradReportTypesList = new ArrayList<>();
        for (ReportTypeCodeEntity reportTypeCodeEntity : gradReportTypesEntities) {
            GradReportTypes gradReportTypes = modelMapper.map(reportTypeCodeEntity, GradReportTypes.class);
        	gradReportTypesList.add(gradReportTypes);
        }
        return gradReportTypesList;
    }

    public ReportTypeCodeEntity transformToEntity(GradReportTypes gradReportTypes) {
        return modelMapper.map(gradReportTypes, ReportTypeCodeEntity.class);
    }
}
