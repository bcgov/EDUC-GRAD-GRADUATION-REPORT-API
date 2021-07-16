package ca.bc.gov.educ.api.grad.report.model.transformer;

import ca.bc.gov.educ.api.grad.report.model.dto.GradRequirementTypes;
import ca.bc.gov.educ.api.grad.report.model.entity.GradRequirementTypesEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class GradRequirementTypesTransformer {

    @Autowired
    ModelMapper modelMapper;

    public GradRequirementTypes transformToDTO (GradRequirementTypesEntity gradProgramEntity) {
    	GradRequirementTypes gradRequirementTypes = modelMapper.map(gradProgramEntity, GradRequirementTypes.class);
        return gradRequirementTypes;
    }

    public GradRequirementTypes transformToDTO ( Optional<GradRequirementTypesEntity> gradProgramEntity ) {
    	GradRequirementTypesEntity cae = new GradRequirementTypesEntity();
        if (gradProgramEntity.isPresent())
            cae = gradProgramEntity.get();

        GradRequirementTypes gradRequirementTypes = modelMapper.map(cae, GradRequirementTypes.class);
        return gradRequirementTypes;
    }

	public List<GradRequirementTypes> transformToDTO (Iterable<GradRequirementTypesEntity> gradRequirementTypesEntities ) {
		List<GradRequirementTypes> gradRequirementTypesList = new ArrayList<GradRequirementTypes>();
        for (GradRequirementTypesEntity gradRequirementTypesEntity : gradRequirementTypesEntities) {
        	GradRequirementTypes gradRequirementTypes = new GradRequirementTypes();
        	gradRequirementTypes = modelMapper.map(gradRequirementTypesEntity, GradRequirementTypes.class);            
        	gradRequirementTypesList.add(gradRequirementTypes);
        }
        return gradRequirementTypesList;
    }

    public GradRequirementTypesEntity transformToEntity(GradRequirementTypes gradRequirementTypes) {
        GradRequirementTypesEntity gradRequirementTypesEntity = modelMapper.map(gradRequirementTypes, GradRequirementTypesEntity.class);
        return gradRequirementTypesEntity;
    }
}
