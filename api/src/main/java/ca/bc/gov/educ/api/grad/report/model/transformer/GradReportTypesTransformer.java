package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradReportTypes;
import ca.bc.gov.educ.api.grad.report.model.entity.GradReportTypesEntity;
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

    public GradReportTypes transformToDTO (GradReportTypesEntity gradReportTypesEntity) {
    	GradReportTypes gradReportTypes = modelMapper.map(gradReportTypesEntity, GradReportTypes.class);
        return gradReportTypes;
    }

    public GradReportTypes transformToDTO ( Optional<GradReportTypesEntity> gradReportTypesEntity ) {
    	GradReportTypesEntity cae = new GradReportTypesEntity();
        if (gradReportTypesEntity.isPresent())
            cae = gradReportTypesEntity.get();

        GradReportTypes gradReportTypes = modelMapper.map(cae, GradReportTypes.class);
        return gradReportTypes;
    }

	public List<GradReportTypes> transformToDTO (Iterable<GradReportTypesEntity> gradReportTypesEntities ) {
		List<GradReportTypes> gradReportTypesList = new ArrayList<GradReportTypes>();
        for (GradReportTypesEntity gradReportTypesEntity : gradReportTypesEntities) {
        	GradReportTypes gradReportTypes = new GradReportTypes();
        	gradReportTypes = modelMapper.map(gradReportTypesEntity, GradReportTypes.class);            
        	gradReportTypesList.add(gradReportTypes);
        }
        return gradReportTypesList;
    }

    public GradReportTypesEntity transformToEntity(GradReportTypes gradReportTypes) {
        GradReportTypesEntity gradReportTypesEntity = modelMapper.map(gradReportTypes, GradReportTypesEntity.class);
        return gradReportTypesEntity;
    }
}
